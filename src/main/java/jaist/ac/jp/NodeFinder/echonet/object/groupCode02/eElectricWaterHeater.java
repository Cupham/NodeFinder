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
import echowand.service.result.ObserveListener;
import echowand.service.result.ObserveResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eDeviceType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter;
import jaist.ac.jp.NodeFinder.echonet.object.utils.SampleConstants;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.BathOperationStatus;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.HeatingShiftTime;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.WaterHeaterSetting;

public class eElectricWaterHeater extends eDataObject{
	private static final Logger logger = Logger.getLogger(eElectricWaterHeater.class.getName());
	private Timer timer;
	private WaterHeaterSetting automaticHeatingSetting;
	private boolean waterHeatingStatus;
	private boolean daytimeReheatingPermitted;
	private String alarmStatus;
	private boolean hotWaterSupplyStatus;
	private boolean automaticBathWaterHeatingModeSetting;
	private BathOperationStatus bathOperationStatus;
	private boolean energyShiftParticipation;
	private HeatingShiftTime heatingStartingTime;
	private int  numberOfShift;
	private HeatingShiftTime daytimeHeatingShiftTime1;
	private String expetectedEnergyOfDaytimeHeatingShift1;
	private String electricConsumptionPerHour1;
	private HeatingShiftTime daytimeHeatingShiftTime2;
	private String expetectedEnergyOfDaytimeHeatingShift2;
	private String electricConsumptionPerHour2;
	
	public eElectricWaterHeater(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x6B);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.ElectricWaterHeater);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshElectricConsumptionPerHour1(int tenAM, int onePM, int threePM, int fivePM) {
		String val = String.format("10:00 %d Wh, 13:00 %d Wh, 15:00 %d Wh, 17:00 %d Wh", tenAM,onePM,threePM,fivePM);
		if(!val.equals(getElectricConsumptionPerHour1())) {
			logger.info(String.format("ElectricConsumptionPerHour1 changed from %s to %s",getElectricConsumptionPerHour1(),val));
			setElectricConsumptionPerHour1(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshElectricConsumptionPerHour2( int onePM, int threePM, int fivePM) {
		String val = String.format("13:00 %d Wh, 15:00 %d Wh, 17:00 %d Wh",onePM,threePM,fivePM);
		if(!val.equals(getElectricConsumptionPerHour2())) {
			logger.info(String.format("ElectricConsumptionPerHour1 changed from %s to %s",getElectricConsumptionPerHour2(),val));
			setElectricConsumptionPerHour2(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshExpetectedEnergyOfDaytimeHeatingShift1(long tenAM, long onePM, long threePM, long fivePM) {
		String val = String.format("10:00 %d Wh, 13:00 %d Wh, 15:00 %d Wh, 17:00 %d Wh", tenAM,onePM,threePM,fivePM);
		if(!val.equals(getExpetectedEnergyOfDaytimeHeatingShift1())) {
			logger.info(String.format("ExpetectedEnergyOfDaytimeHeatingShift1 changed from %s to %s",getExpetectedEnergyOfDaytimeHeatingShift1(),val));
			setExpetectedEnergyOfDaytimeHeatingShift1(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshExpetectedEnergyOfDaytimeHeatingShift2(long onePM, long threePM, long fivePM) {
		String val = String.format("13:00 %d Wh, 15:00 %d Wh, 17:00 %d Wh",onePM,threePM,fivePM);
		if(!val.equals(getExpetectedEnergyOfDaytimeHeatingShift2())) {
			logger.info(String.format("ExpetectedEnergyOfDaytimeHeatingShift2 changed from %s to %s",getExpetectedEnergyOfDaytimeHeatingShift2(),val));
			setExpetectedEnergyOfDaytimeHeatingShift2(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshDaytimeHeatingShiftTime1(HeatingShiftTime val) {
		if(val != getDaytimeHeatingShiftTime1()) {
			logger.info(String.format("DaytimeHeatingShiftTime1 changed from %s to %s",getDaytimeHeatingShiftTime1(),val));
			setDaytimeHeatingShiftTime1(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshDaytimeHeatingShiftTime2(HeatingShiftTime val) {
		if(val != getDaytimeHeatingShiftTime2()) {
			logger.info(String.format("DaytimeHeatingShiftTime2 changed from %s to %s",getDaytimeHeatingShiftTime2(),val));
			setDaytimeHeatingShiftTime2(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshNumberOfShift(int val) {
		if(val!= 1 || val != 2) {
			logger.severe(String.format("NumberOfShift (%d) is invalid",val));
		} else if(val != getNumberOfShift()) {
			logger.info(String.format("NumberOfShift changed from %d to %d",getNumberOfShift(),val));
			setNumberOfShift(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshHeatingStartingTime(HeatingShiftTime val) {
		if(val != getHeatingStartingTime()) {
			logger.info(String.format("HeatingStartingTime changed from %s to %s",getHeatingStartingTime(),val));
			setHeatingStartingTime(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshEnergyShiftParticipation(boolean val) {
		if(val != isEnergyShiftParticipation()) {
			logger.info(String.format("EnergyShiftParticipation changed from %s to %s",isEnergyShiftParticipation(),val));
			setEnergyShiftParticipation(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshBathOperationStatus(BathOperationStatus val) {
		if(val != getBathOperationStatus()) {
			logger.info(String.format("BathOperationStatus changed from %s to %s",getBathOperationStatus(),val));
			setBathOperationStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshAutomaticBathWaterHeatingModeSetting(boolean val) {
		if(val != isAutomaticBathWaterHeatingModeSetting()) {
			logger.info(String.format("AutomaticBathWaterHeatingModeSetting changed from %s to %s",isAutomaticBathWaterHeatingModeSetting(),val));
			setAutomaticBathWaterHeatingModeSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshHotWaterSupplyStatus(boolean val) {
		if(val != isHotWaterSupplyStatus()) {
			logger.info(String.format("HotWaterSupplyStatus changed from %s to %s",isHotWaterSupplyStatus(),val));
			setHotWaterSupplyStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshAlarmStatus(String val) {
		if(!val.equals(this.getAlarmStatus())) {
			logger.info(String.format("AlarmStatus changed from %s to %s",getAlarmStatus(),val));
			setAlarmStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshDaytimeReheatingPermitted(boolean val) {
		if(val != isDaytimeReheatingPermitted()) {
			logger.info(String.format("DaytimeReheatingPermitted changed from %s to %s",isDaytimeReheatingPermitted(),val));
			setDaytimeReheatingPermitted(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshWaterHeatingStatus(boolean val) {
		if(val != isWaterHeatingStatus()) {
			logger.info(String.format("WaterHeatingStatus changed from %s to %s",isWaterHeatingStatus(),val));
			setWaterHeatingStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshAutomaticHeatingSetting(WaterHeaterSetting val) {
		if(val != getAutomaticHeatingSetting()) {
			logger.info(String.format("AutomaticHeatingSetting changed from %s to %s",getAutomaticHeatingSetting(),val));
			setAutomaticHeatingSetting(val);
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
		epcs.add(EPC.xB0);
		epcs.add(EPC.xB2);
		epcs.add(EPC.xC0);
		epcs.add(EPC.xC2);
		epcs.add(EPC.xC3);
		epcs.add(EPC.xE3);
		epcs.add(EPC.xEA);
		epcs.add(EPC.xC7);
		epcs.add(EPC.xC8);
		epcs.add(EPC.xC9);
		epcs.add(EPC.xCA);
		epcs.add(EPC.xCB);
		epcs.add(EPC.xCC);
		epcs.add(EPC.xCD);
		epcs.add(EPC.xCE);
		epcs.add(EPC.xCF);
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
							refreshAutomaticHeatingSetting(eConverter.WaterHeaterSetting.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={AutomaticHeatingSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getAutomaticHeatingSetting()));
						break;
						case xB2:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshWaterHeatingStatus(true);
							} else {
								refreshWaterHeatingStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB2, EDT: 0x%02X}=={WaterHeatingStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isWaterHeatingStatus()));
						break;
						case xC0:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshDaytimeReheatingPermitted(true);
							} else {
								refreshDaytimeReheatingPermitted(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC0, EDT: 0x%02X}=={DaytimeReheatingPermitted:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isDaytimeReheatingPermitted()));
						break;
						case xC2:
							refreshAlarmStatus(eConverter.dataTo4BitMapcode(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC2, EDT: 0x%02X}=={AlarmStatusCode:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getAlarmStatus()));
						break;
						case xC3:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshHotWaterSupplyStatus(true);
							} else {
								refreshHotWaterSupplyStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC3, EDT: 0x%02X}=={HotWaterSupplyStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isHotWaterSupplyStatus()));
						break;
						case xE3:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshAutomaticBathWaterHeatingModeSetting(true);
							} else {
								refreshAutomaticBathWaterHeatingModeSetting(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE3, EDT: 0x%02X}=={AutomaticBathWaterHeatingModeSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isAutomaticBathWaterHeatingModeSetting()));
						break;
						case xEA:
							refreshBathOperationStatus(eConverter.BathOperationStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xEA, EDT: 0x%02X}=={BathOperationStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getBathOperationStatus()));
						break;
						case xC7:
							if(eConverter.dataToInteger(resultData) == 1) {
								refreshEnergyShiftParticipation(true);
							} else {
								refreshEnergyShiftParticipation(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC7, EDT: 0x%02X}=={EnergyShiftParticipation:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isEnergyShiftParticipation()));
						break;
						case xC8:
							refreshHeatingStartingTime(eConverter.HeatingShiftTime.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC8, EDT: 0x%02X}=={HeatingStartingTime:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getHeatingStartingTime()));
						break;
						case xC9:
							refreshNumberOfShift(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC9, EDT: 0x%02X}=={NumberOfShift:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getNumberOfShift()));
						break;
						case xCA:
							refreshDaytimeHeatingShiftTime1(eConverter.HeatingShiftTime.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xCA, EDT: 0x%02X}=={DaytimeHeatingShiftTime1:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getDaytimeHeatingShiftTime1()));
						break;
						case xCB:
							long b1 = eConverter.longFromByte(resultData.toBytes()[0], resultData.toBytes()[1],
																		resultData.toBytes()[3], resultData.toBytes()[4]);
							long b2 = eConverter.longFromByte(resultData.toBytes()[5], resultData.toBytes()[6],
																		resultData.toBytes()[7], resultData.toBytes()[8]);
							long b3 = eConverter.longFromByte(resultData.toBytes()[9], resultData.toBytes()[10],
																		resultData.toBytes()[11], resultData.toBytes()[12]);
							long b4 = eConverter.longFromByte(resultData.toBytes()[13], resultData.toBytes()[14],
																		resultData.toBytes()[15], resultData.toBytes()[16]);

							refreshExpetectedEnergyOfDaytimeHeatingShift1(b1, b2, b3, b4);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xCB, EDT: 0x%02X}=={ExpetectedEnergyOfDaytimeHeatingShift1:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getExpetectedEnergyOfDaytimeHeatingShift1()));
						break;
						case xCC:
							short v1 = eConverter.shortFromByte(resultData.toBytes()[0], resultData.toBytes()[1]);
							short v2 = eConverter.shortFromByte(resultData.toBytes()[2], resultData.toBytes()[3]);
							short v3 = eConverter.shortFromByte(resultData.toBytes()[4], resultData.toBytes()[5]);
							short v4 = eConverter.shortFromByte(resultData.toBytes()[6], resultData.toBytes()[7]);
							refreshElectricConsumptionPerHour1(v1, v2, v3, v4);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xCC, EDT: 0x%02X}=={ElectricConsumptionPerHour1:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getElectricConsumptionPerHour1()));
						break;
						case xCD:
							refreshDaytimeHeatingShiftTime2(eConverter.HeatingShiftTime.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xCD, EDT: 0x%02X}=={DaytimeHeatingShiftTime2:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getDaytimeHeatingShiftTime2()));
						break;
						case xCE:
							long b12 = eConverter.longFromByte(resultData.toBytes()[0], resultData.toBytes()[1],
																		resultData.toBytes()[3], resultData.toBytes()[4]);
							long b22 = eConverter.longFromByte(resultData.toBytes()[5], resultData.toBytes()[6],
																		resultData.toBytes()[7], resultData.toBytes()[8]);
							long b32 = eConverter.longFromByte(resultData.toBytes()[9], resultData.toBytes()[10],
																		resultData.toBytes()[11], resultData.toBytes()[12]);
							refreshExpetectedEnergyOfDaytimeHeatingShift2(b12, b22, b32);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xCE, EDT: 0x%02X}=={refreshExpetectedEnergyOfDaytimeHeatingShift2:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getExpetectedEnergyOfDaytimeHeatingShift2()));
						break;
						case xCF:
							short v12 = eConverter.shortFromByte(resultData.toBytes()[0], resultData.toBytes()[1]);
							short v22 = eConverter.shortFromByte(resultData.toBytes()[2], resultData.toBytes()[3]);
							short v32 = eConverter.shortFromByte(resultData.toBytes()[4], resultData.toBytes()[5]);
							refreshElectricConsumptionPerHour2(v12, v22, v32);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xCF, EDT: 0x%02X}=={ElectricConsumptionPerHour2:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getElectricConsumptionPerHour2()));
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
		epcs.add(EPC.xC2);
		epcs.add(EPC.xC3);
		epcs.add(EPC.xEA);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xC2:
							refreshAlarmStatus(eConverter.dataTo4BitMapcode(resultData));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xC2, EDT: 0x%02X}=={AlarmStatusCode:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getAlarmStatus()));
						break;
						case xC3:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshHotWaterSupplyStatus(true);
							} else {
								refreshHotWaterSupplyStatus(false);
							}
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xC3, EDT: 0x%02X}=={HotWaterSupplyStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isHotWaterSupplyStatus()));
						break;
						case xEA:
							refreshBathOperationStatus(eConverter.BathOperationStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xEA, EDT: 0x%02X}=={BathOperationStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getBathOperationStatus()));
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

	public WaterHeaterSetting getAutomaticHeatingSetting() {
		return automaticHeatingSetting;
	}

	public void setAutomaticHeatingSetting(WaterHeaterSetting automaticHeatingSetting) {
		this.automaticHeatingSetting = automaticHeatingSetting;
	}

	public boolean isWaterHeatingStatus() {
		return waterHeatingStatus;
	}

	public void setWaterHeatingStatus(boolean waterHeatingStatus) {
		this.waterHeatingStatus = waterHeatingStatus;
	}

	public boolean isDaytimeReheatingPermitted() {
		return daytimeReheatingPermitted;
	}

	public void setDaytimeReheatingPermitted(boolean daytimeReheatingPermitted) {
		this.daytimeReheatingPermitted = daytimeReheatingPermitted;
	}

	public String getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public boolean isHotWaterSupplyStatus() {
		return hotWaterSupplyStatus;
	}

	public void setHotWaterSupplyStatus(boolean hotWaterSupplyStatus) {
		this.hotWaterSupplyStatus = hotWaterSupplyStatus;
	}

	public boolean isAutomaticBathWaterHeatingModeSetting() {
		return automaticBathWaterHeatingModeSetting;
	}

	public void setAutomaticBathWaterHeatingModeSetting(boolean automaticBathWaterHeatingModeSetting) {
		this.automaticBathWaterHeatingModeSetting = automaticBathWaterHeatingModeSetting;
	}

	public BathOperationStatus getBathOperationStatus() {
		return bathOperationStatus;
	}

	public void setBathOperationStatus(BathOperationStatus bathOperationStatus) {
		this.bathOperationStatus = bathOperationStatus;
	}

	public boolean isEnergyShiftParticipation() {
		return energyShiftParticipation;
	}

	public void setEnergyShiftParticipation(boolean energyShiftParticipation) {
		this.energyShiftParticipation = energyShiftParticipation;
	}

	public HeatingShiftTime getHeatingStartingTime() {
		return heatingStartingTime;
	}

	public void setHeatingStartingTime(HeatingShiftTime heatingStartingTime) {
		this.heatingStartingTime = heatingStartingTime;
	}

	public int getNumberOfShift() {
		return numberOfShift;
	}

	public void setNumberOfShift(int numberOfShift) {
		this.numberOfShift = numberOfShift;
	}

	public HeatingShiftTime getDaytimeHeatingShiftTime1() {
		return daytimeHeatingShiftTime1;
	}

	public void setDaytimeHeatingShiftTime1(HeatingShiftTime daytimeHeatingShiftTime1) {
		this.daytimeHeatingShiftTime1 = daytimeHeatingShiftTime1;
	}

	public String getExpetectedEnergyOfDaytimeHeatingShift1() {
		return expetectedEnergyOfDaytimeHeatingShift1;
	}

	public void setExpetectedEnergyOfDaytimeHeatingShift1(String expetectedEnergyOfDaytimeHeatingShift1) {
		this.expetectedEnergyOfDaytimeHeatingShift1 = expetectedEnergyOfDaytimeHeatingShift1;
	}

	public String getElectricConsumptionPerHour1() {
		return electricConsumptionPerHour1;
	}

	public void setElectricConsumptionPerHour1(String electricConsumptionPerHour1) {
		this.electricConsumptionPerHour1 = electricConsumptionPerHour1;
	}

	public HeatingShiftTime getDaytimeHeatingShiftTime2() {
		return daytimeHeatingShiftTime2;
	}

	public void setDaytimeHeatingShiftTime2(HeatingShiftTime daytimeHeatingShiftTime2) {
		this.daytimeHeatingShiftTime2 = daytimeHeatingShiftTime2;
	}

	public String getExpetectedEnergyOfDaytimeHeatingShift2() {
		return expetectedEnergyOfDaytimeHeatingShift2;
	}

	public void setExpetectedEnergyOfDaytimeHeatingShift2(String expetectedEnergyOfDaytimeHeatingShift2) {
		this.expetectedEnergyOfDaytimeHeatingShift2 = expetectedEnergyOfDaytimeHeatingShift2;
	}

	public String getElectricConsumptionPerHour2() {
		return electricConsumptionPerHour2;
	}

	public void setElectricConsumptionPerHour2(String electricConsumptionPerHour2) {
		this.electricConsumptionPerHour2 = electricConsumptionPerHour2;
	}
	
}
