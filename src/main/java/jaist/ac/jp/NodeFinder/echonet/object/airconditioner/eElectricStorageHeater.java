package jaist.ac.jp.NodeFinder.echonet.object.airconditioner;



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
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.FanOperation;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eElectricStorageHeater extends eDataObject{
	private static final Logger logger = Logger.getLogger(eElectricStorageHeater.class.getName());
	private Timer timer;
	private FanOperation fanOperationStatus;
	private boolean heatStorageOperationStatus;
	private int heatStorageTemperature;
	private int storedHeatTemperature;
	private boolean daytimeHeatStorageAbility;
	private int midnightPowerDurationSetting;
	private int midnightStartTimeSetting;
	private boolean radiationMethodWithFan;
	private boolean childLockSetting;
	
	

	public eElectricStorageHeater(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x01);
		this.setClassCode((byte) 0x55);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.ElectricStorageHeater);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshChildLockSetting(boolean val) {
		if(val != isChildLockSetting()) {
			logger.info(String.format("ChildLock changed from %s to %s",isChildLockSetting(),val));
			setChildLockSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshRadiationMethodWithFan(boolean val) {
		if(val != isRadiationMethodWithFan()) {
			logger.info(String.format("RadiationMethodWithFan changed from %s to %s",isRadiationMethodWithFan(),val));
			setRadiationMethodWithFan(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshMidnightStartTimeSetting(int val) {
		if(val != getMidnightStartTimeSetting()) {
			logger.info(String.format("MidnightStartTimeSetting changed from %d to %d",getMidnightStartTimeSetting(),val));
			setMidnightStartTimeSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshMidnightPowerDurationSetting(int val) {
		if(val != getMidnightPowerDurationSetting()) {
			logger.info(String.format("MidnightPowerDurationSetting changed from %d to %d",getMidnightPowerDurationSetting(),val));
			setMidnightPowerDurationSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshDaytimeHeatStorageAbility(boolean val) {
		if(val != isDaytimeHeatStorageAbility()) {
			logger.info(String.format("DaytimeHeatStorageAbility changed from %s to %s",isDaytimeHeatStorageAbility(),val));
			setDaytimeHeatStorageAbility(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshStoredHeatTemperature(int val) {
		if(val != getStoredHeatTemperature()) {
			logger.info(String.format("StoredHeatTemperature changed from %d to %d",getStoredHeatTemperature(),val));
			setStoredHeatTemperature(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshHeatStorageTemperatureSetting(int val) {
		if(val != getHeatStorageTemperature()) {
			logger.info(String.format("HeatStorageTemperatureSetting changed from %d to %d",getHeatStorageTemperature(),val));
			setHeatStorageTemperature(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshHeatStorageOperationStatus(boolean val) {
		if(val != isHeatStorageOperationStatus()) {
			logger.info(String.format("HeatStorageOperationStatus changed from %s to %s",isHeatStorageOperationStatus(),val));
			setHeatStorageOperationStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshFanOperationStatus(FanOperation val) {
		if(val != getFanOperationStatus()) {
			logger.info(String.format("FanOperationStatus changed from %s to %s",getFanOperationStatus().name(),val));
			setFanOperationStatus(val);
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
		epcs.add(EPC.xA1);
		epcs.add(EPC.xC0);
		epcs.add(EPC.xC1);
		epcs.add(EPC.xC2);
		epcs.add(EPC.xC4);
		epcs.add(EPC.xC5);
		epcs.add(EPC.xC6);
		epcs.add(EPC.xC7);
		epcs.add(EPC.xC8);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xA1:
							refreshFanOperationStatus(EchonetDataConverter.FanOperation.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xA1, EDT: 0x%02X}=={FanOperationStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getFanOperationStatus()));
						break;
						case xC0:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshHeatStorageOperationStatus(true);
							} else {
								refreshHeatStorageOperationStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC0, EDT: 0x%02X}=={HeatStorageOperationStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isHeatStorageOperationStatus()));
						break;
						case xC1:
							refreshHeatStorageTemperatureSetting(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC1, EDT: 0x%02X}=={HeatStorageTemperatureSetting:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getHeatStorageTemperature()));
						break;
						case xC2:
							refreshStoredHeatTemperature(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC2, EDT: 0x%02X}=={StoredHeatTemperature:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getStoredHeatTemperature()));
						break;
						case xC4:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshDaytimeHeatStorageAbility(true);
							} else {
								refreshDaytimeHeatStorageAbility(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC4, EDT: 0x%02X}=={DaytimeHeatStorageAbility:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isDaytimeHeatStorageAbility()));
						break;
						case xC5:
							refreshMidnightPowerDurationSetting(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC5, EDT: 0x%02X}=={MidnightPowerDurationSetting:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMidnightPowerDurationSetting()));
						break;
						case xC6:
							refreshMidnightStartTimeSetting(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC6, EDT: 0x%02X}=={MidnightStartTimeSetting:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMidnightStartTimeSetting()));
						break;
						case xC7:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshRadiationMethodWithFan(true);
							} else {
								refreshRadiationMethodWithFan(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC7, EDT: 0x%02X}=={RadiationMethodWithFan:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isRadiationMethodWithFan()));
						break;
						case xC8:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshChildLockSetting(true);
							} else {
								refreshChildLockSetting(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC8, EDT: 0x%02X}=={ChildLockSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isChildLockSetting()?"ON":"OFF"));
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
		epcs.add(EPC.xA1);
		epcs.add(EPC.xC0);
		epcs.add(EPC.xC4);
		epcs.add(EPC.xC8);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xA1:
						refreshFanOperationStatus(EchonetDataConverter.FanOperation.fromCode(resultData.toBytes()[0]));
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xA1, EDT: 0x%02X}=={FanOperationStatus:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getFanOperationStatus()));
					break;
					case xC0:
						if(EchonetDataConverter.dataToInteger(resultData) == 48) {
							refreshHeatStorageOperationStatus(true);
						} else {
							refreshHeatStorageOperationStatus(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xC0, EDT: 0x%02X}=={HeatStorageOperationStatus:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isHeatStorageOperationStatus()));
					break;
					case xC4:
						if(EchonetDataConverter.dataToInteger(resultData) == 48) {
							refreshDaytimeHeatStorageAbility(true);
						} else {
							refreshDaytimeHeatStorageAbility(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xC4, EDT: 0x%02X}=={DaytimeHeatStorageAbility:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isDaytimeHeatStorageAbility()));
					break;
					case xC8:
						if(EchonetDataConverter.dataToInteger(resultData) == 48) {
							refreshChildLockSetting(true);
						} else {
							refreshChildLockSetting(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xC8, EDT: 0x%02X}=={ChildLockSetting:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isChildLockSetting()?"ON":"OFF"));
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

	public FanOperation getFanOperationStatus() {
		return fanOperationStatus;
	}

	public void setFanOperationStatus(FanOperation fanOperationStatus ) {
		this.fanOperationStatus = fanOperationStatus;
	}

	public boolean isHeatStorageOperationStatus() {
		return heatStorageOperationStatus;
	}

	public void setHeatStorageOperationStatus(boolean heatStorageOperationStatus) {
		this.heatStorageOperationStatus = heatStorageOperationStatus;
	}

	public int getHeatStorageTemperature() {
		return heatStorageTemperature;
	}

	public void setHeatStorageTemperature(int heatStorageTemperature) {
		this.heatStorageTemperature = heatStorageTemperature;
	}

	public int getStoredHeatTemperature() {
		return storedHeatTemperature;
	}

	public void setStoredHeatTemperature(int storedHeatTemperature) {
		this.storedHeatTemperature = storedHeatTemperature;
	}

	public boolean isDaytimeHeatStorageAbility() {
		return daytimeHeatStorageAbility;
	}

	public void setDaytimeHeatStorageAbility(boolean daytimeHeatStorageAbility) {
		this.daytimeHeatStorageAbility = daytimeHeatStorageAbility;
	}

	public int getMidnightPowerDurationSetting() {
		return midnightPowerDurationSetting;
	}

	public void setMidnightPowerDurationSetting(int midnightPowerDurationSetting) {
		this.midnightPowerDurationSetting = midnightPowerDurationSetting;
	}

	public int getMidnightStartTimeSetting() {
		return midnightStartTimeSetting;
	}

	public void setMidnightStartTimeSetting(int midnightStartTimeSetting) {
		this.midnightStartTimeSetting = midnightStartTimeSetting;
	}

	public boolean isRadiationMethodWithFan() {
		return radiationMethodWithFan;
	}

	public void setRadiationMethodWithFan(boolean radiationMethod) {
		this.radiationMethodWithFan = radiationMethod;
	}

	public boolean isChildLockSetting() {
		return childLockSetting;
	}

	public void setChildLockSetting(boolean childLockSetting) {
		this.childLockSetting = childLockSetting;
	}
	

}
