/*******************************************************************************
 * Copyright 2019 Cu Pham
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package jaist.ac.jp.NodeFinder.echonet.object.groupCode02;




import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.common.EPC;
import echowand.net.Node;
import echowand.net.SubnetException;
import echowand.service.Service;
import echowand.service.result.GetListener;
import echowand.service.result.GetResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eDeviceType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter;
import jaist.ac.jp.NodeFinder.echonet.object.utils.SampleConstants;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.AlarmStatus;

public class eElectricLock extends eDataObject{
	private static final Logger logger = Logger.getLogger(eElectricLock.class.getName());
	private Timer timer;
	private boolean lockSetting;
	private AlarmStatus alarmStatus;
	private boolean batteryReplacementNeeded;
	
	public eElectricLock(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x6F);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.ElectricLock);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshBatteryReplacementNeeded(boolean val) {
		if(val != isBatteryReplacementNeeded()) {
			logger.info(String.format("BatteryReplacementNeeded changed from %s to %s",isBatteryReplacementNeeded(),val));
			setBatteryReplacementNeeded(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshAlarmStatus(AlarmStatus val) {
		if(val != getAlarmStatus()) {
			logger.info(String.format("AlarmStatus changed from %s to %s",getAlarmStatus(),val));
			setAlarmStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshLockSetting(boolean val) {
		if(val != isLockSetting()) {
			logger.info(String.format("LockSetting changed from %s to %s",isLockSetting(),val));
			setLockSetting(val);
			//TODO: More task can be added here
		}
	}
	// Override functions
	@Override
	public void dataFromEOJ(Service s){
		final Service service = s;
		final Node node = this.getNode();
		final EOJ eoj = this.getEoj();
		if(observeEnabled) {
			observeSuperData(service, node, eoj);
			observeSpecificData(service);
		}
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				getSpecificData(service);
				getSuperData(service, node, eoj);
				
			}
		}, SampleConstants.getDelayInterval(), SampleConstants.getRefreshInterval());	
	}
	
	
 	private void getSpecificData(Service service){
 		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE5);
		epcs.add(EPC.xE6);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xE0:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshLockSetting(true);
							} else {
								refreshLockSetting(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={LockSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isLockSetting()));
						break;
						case xE5:
							refreshAlarmStatus(eConverter.AlarmStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE5, EDT: 0x%02X}=={AlarmStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getAlarmStatus()));
						break;
						case xE6:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshBatteryReplacementNeeded(true);
							} else {
								refreshBatteryReplacementNeeded(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE6, EDT: 0x%02X}=={BatteryReplacementNeeded:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isBatteryReplacementNeeded()));
						break;
						default:
						break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE, ex.toString());
		}
	}

	@Override
	public void observeSpecificData(Service service) {
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE5);
		epcs.add(EPC.xE6);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xE0:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshLockSetting(true);
							} else {
								refreshLockSetting(false);
							}
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={LockSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isLockSetting()));
						break;
						case xE5:
							refreshAlarmStatus(eConverter.AlarmStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE5, EDT: 0x%02X}=={AlarmStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getAlarmStatus()));
						break;
						case xE6:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshBatteryReplacementNeeded(true);
							} else {
								refreshBatteryReplacementNeeded(false);
							}
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE6, EDT: 0x%02X}=={BatteryReplacementNeeded:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isBatteryReplacementNeeded()));
						break;
						default:
						break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE, ex.toString());
		}
	}

	public boolean isLockSetting() {
		return lockSetting;
	}

	public void setLockSetting(boolean lockSetting) {
		this.lockSetting = lockSetting;
	}

	public AlarmStatus getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(AlarmStatus alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public boolean isBatteryReplacementNeeded() {
		return batteryReplacementNeeded;
	}

	public void setBatteryReplacementNeeded(boolean batteryReplacementNeeded) {
		this.batteryReplacementNeeded = batteryReplacementNeeded;
	}

	
	
}
