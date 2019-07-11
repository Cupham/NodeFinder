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
import echowand.service.result.ObserveListener;
import echowand.service.result.ObserveResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eDeviceType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter;
import jaist.ac.jp.NodeFinder.echonet.object.utils.SampleConstants;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.AirFlowRate;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.AirconditionerOperationMode;

public class eHomeAirConditioner extends eDataObject{
	private static final Logger logger = Logger.getLogger(eHomeAirConditioner.class.getName());
	private Timer timer;
	private AirconditionerOperationMode operationModeSetting;
	private int settingTemperature;
	private int roomTemperature;
	private AirFlowRate airFlowRateSetting;
	
	

	public eHomeAirConditioner(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x01);
		this.setClassCode((byte) 0x30);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.HomeAirconditioner);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshAirFlowRateSetting(AirFlowRate val) {
		if(val != getAirFlowRateSetting()) {
			logger.info(String.format("AirFlowRateSetting changed from %s to %s",getAirFlowRateSetting(),val));
			setAirFlowRateSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshRoomTemperature(int val) {
		if(val != getRoomTemperature()) {
			logger.info(String.format("RoomTemperature changed from %d to %d",getRoomTemperature(),val));
			setRoomTemperature(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshSettingTemperature(int val) {
		if(val != getSettingTemperature()) {
			logger.info(String.format("SettingTemperature changed from %d to %d",getSettingTemperature(),val));
			setSettingTemperature(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshOperationModeSetting(AirconditionerOperationMode val) {
		if(val != getOperationModeSetting()) {
			logger.info(String.format("OperationModeSetting changed from %s to %s",getOperationModeSetting(),val));
			setOperationModeSetting(val);
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
		epcs.add(EPC.xB0);
		epcs.add(EPC.xB3);
		epcs.add(EPC.xBB);
		epcs.add(EPC.xA0);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xB0:
							refreshOperationModeSetting(eConverter.AirconditionerOperationMode.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={OperationModeSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOperationModeSetting()));
						break;
						case xB3:
							refreshSettingTemperature(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB3, EDT: 0x%02X}=={SettingTemperature:%d Celcius Degree}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSettingTemperature()));
						break;
						case xBB:
							refreshRoomTemperature(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xBB, EDT: 0x%02X}=={RoomTemperature:%d Celcius Degree}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getRoomTemperature()));
						break;
						case xA0:
							refreshAirFlowRateSetting(eConverter.AirFlowRate.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xA0, EDT: 0x%02X}=={AirFlowRateSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getAirFlowRateSetting()));
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
		epcs.add(EPC.xB0);
		epcs.add(EPC.xA0);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xB0:
						refreshOperationModeSetting(eConverter.AirconditionerOperationMode.fromCode(resultData.toBytes()[0]));
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={OperationModeSetting:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOperationModeSetting()));
					break;
					case xA0:
						refreshAirFlowRateSetting(eConverter.AirFlowRate.fromCode(resultData.toBytes()[0]));
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xA0, EDT: 0x%02X}=={AirFlowRateSetting:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getAirFlowRateSetting()));
					break;
					default:
						break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE,ex.toString());
			
		}
	}

	public AirconditionerOperationMode getOperationModeSetting() {
		return operationModeSetting;
	}

	public void setOperationModeSetting(AirconditionerOperationMode operationModeSetting) {
		this.operationModeSetting = operationModeSetting;
	}

	public int getSettingTemperature() {
		return settingTemperature;
	}

	public void setSettingTemperature(int settingTemperature) {
		this.settingTemperature = settingTemperature;
	}

	public int getRoomTemperature() {
		return roomTemperature;
	}

	public void setRoomTemperature(int roomTemperature) {
		this.roomTemperature = roomTemperature;
	}

	public AirFlowRate getAirFlowRateSetting() {
		return airFlowRateSetting;
	}

	public void setAirFlowRateSetting(AirFlowRate airFlowRateSetting) {
		this.airFlowRateSetting = airFlowRateSetting;
	}


}
