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
package jaist.ac.jp.NodeFinder.echonet.object.groupCode01;



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

public class eCommercialAirConditionerOutdoorUnit extends eDataObject{
	private static final Logger logger = Logger.getLogger(eCommercialAirConditionerOutdoorUnit.class.getName());
	private Timer timer;
	private String groupInfor;
	private long measuredPowerConsumption;
	private long possiblePowerSaving;
	private long restrictingPowerConsumptionSetting;
	
	

	public eCommercialAirConditionerOutdoorUnit(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x01);
		this.setClassCode((byte) 0x57);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.CommercialAirconditionerOutdoorUnit);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshRestrictingPowerConsumptionSetting(long val) {
		if(val != getRestrictingPowerConsumptionSetting()) {
			logger.info(String.format("RestrictingPowerConsumptionSetting changed from %d to %d",getRestrictingPowerConsumptionSetting(),val));
			setRestrictingPowerConsumptionSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshPossiblePowerSaving(long val) {
		if(val != getPossiblePowerSaving()) {
			logger.info(String.format("PossiblePowerSaving changed from %d to %d",getPossiblePowerSaving(),val));
			setPossiblePowerSaving(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshMeasuredPowerConsumption(long val) {
		if(val != getMeasuredPowerConsumption()) {
			logger.info(String.format("MeasuredPowerConsumption changed from %d to %d",getMeasuredPowerConsumption(),val));
			setMeasuredPowerConsumption(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshGroupInfor(String val) {
		if(val.equals(this.getGroupInfor())) {
			logger.info(String.format("GroupInfor changed from %s to %s",getGroupInfor(),val));
			setGroupInfor(val);
			//TODO: More task can be added here
		}
	}
	// Override functions
	@Override
	public void dataFromEOJ(Service s){
		final Service service = s;
		final Node node = this.getNode();
		final EOJ eoj = this.getEoj();
		observeSuperData(service, node, eoj);
		observeSpecificData(service);
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
		epcs.add(EPC.xCA);
		epcs.add(EPC.xDB);
		epcs.add(EPC.xDD);
		epcs.add(EPC.xDE);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xCA:
							refreshGroupInfor(eConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xCA, EDT: 0x%02X}=={GroupInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getGroupInfor()));
						break;
						case xDB:
							refreshMeasuredPowerConsumption(eConverter.dataToLong(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xDB, EDT: 0x%02X}=={MeasuredPowerConsumption:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredPowerConsumption()));
						break;
						case xDD:
							refreshPossiblePowerSaving(eConverter.dataToLong(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xDD, EDT: 0x%02X}=={PossiblePowerSaving:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getPossiblePowerSaving()));
						break;
						case xDE:
							refreshRestrictingPowerConsumptionSetting(eConverter.dataToLong(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xDE, EDT: 0x%02X}=={RestrictingPowerConsumptionSetting:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getRestrictingPowerConsumptionSetting()));
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

	}


	public String getGroupInfor() {
		return groupInfor;
	}

	public void setGroupInfor(String groupInfor) {
		this.groupInfor = groupInfor;
	}

	public long getMeasuredPowerConsumption() {
		return measuredPowerConsumption;
	}

	public void setMeasuredPowerConsumption(long measuredPowerConsumption) {
		this.measuredPowerConsumption = measuredPowerConsumption;
	}

	public long getPossiblePowerSaving() {
		return possiblePowerSaving;
	}

	public void setPossiblePowerSaving(long possiblePowerSaving) {
		this.possiblePowerSaving = possiblePowerSaving;
	}

	public long getRestrictingPowerConsumptionSetting() {
		return restrictingPowerConsumptionSetting;
	}

	public void setRestrictingPowerConsumptionSetting(long restrictingPowerConsumptionSetting) {
		this.restrictingPowerConsumptionSetting = restrictingPowerConsumptionSetting;
	}
	
}
