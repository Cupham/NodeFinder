package jaist.ac.jp.NodeFinder.echonet.object.housing;




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
import jaist.ac.jp.NodeFinder.util.SampleConstants;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.OpenCloseStatus;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.OpenCloseStop;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.SelectiveOperation;

public class eElectricBlind extends eDataObject{
	private static final Logger logger = Logger.getLogger(eElectricBlind.class.getName());
	private Timer timer;
	private boolean timerEnable;
	private boolean windDetectionStatus;
	private boolean sunlightDetectionStatus;
	private boolean automaticOperationSetting;
	private OpenCloseStop openCloseSetting;
	private int openingLevel;
	private boolean remoteOperatingEnable;
	private SelectiveOperation selectiveOpeningOperationSetting;
	private OpenCloseStatus openCloseStatus;
	
	public eElectricBlind(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x60);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.ElectricBlind);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshOpenCloseStatus(OpenCloseStatus val) {
		if(val != getOpenCloseStatus()) {
			logger.info(String.format("OpenCloseStatus changed from %s to %s",getOpenCloseStatus(),val));
			setOpenCloseStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshSelectiveOpeningOperationSetting(SelectiveOperation val) {
		if(val != getSelectiveOpeningOperationSetting()) {
			logger.info(String.format("SelectiveOpeningOperationSetting changed from %s to %s",getSelectiveOpeningOperationSetting(),val));
			setSelectiveOpeningOperationSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshRemoteOperatingEnable(boolean val) {
		if(val != isRemoteOperatingEnable()) {
			logger.info(String.format("RemoteOperatingEnable changed from %s to %s",isRemoteOperatingEnable(),val));
			setRemoteOperatingEnable(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshOpeningLevel(int val) {
		if(val != getOpeningLevel()) {
			logger.info(String.format("OpeningLevel changed from %d to %d percent",getOpeningLevel(),val));
			setOpeningLevel(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshOpenCloseSetting(OpenCloseStop val) {
		if(val != getOpenCloseSetting()) {
			logger.info(String.format("OpenCloseSetting changed from %s to %s",getOpenCloseSetting(),val));
			setOpenCloseSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshAutomaticOperationSetting(boolean val) {
		if(val != isAutomaticOperationSetting()) {
			logger.info(String.format("AutomaticOperationSetting changed from %s to %s",isAutomaticOperationSetting(),val));
			setAutomaticOperationSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshSunlightDetectionStatus(boolean val) {
		if(val != isSunlightDetectionStatus()) {
			logger.info(String.format("SunlightDetectionStatus changed from %s to %s",isSunlightDetectionStatus(),val));
			setSunlightDetectionStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshWindDetectionStatus(boolean val) {
		if(val != isWindDetectionStatus()) {
			logger.info(String.format("WindDetectionStatus changed from %s to %s",isWindDetectionStatus(),val));
			setWindDetectionStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshTimerEnable(boolean val) {
		if(val != isTimerEnable()) {
			logger.info(String.format("TimerEnable changed from %s to %s",isTimerEnable(),val));
			setTimerEnable(val);
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
		epcs.add(EPC.x90);
		epcs.add(EPC.xC2);
		epcs.add(EPC.xC3);
		epcs.add(EPC.xD4);
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE1);
		epcs.add(EPC.xE8);
		epcs.add(EPC.xE9);
		epcs.add(EPC.xEA);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case x90:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshTimerEnable(true);
							} else {
								refreshTimerEnable(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0x90, EDT: 0x%02X}=={TimerEnable:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isTimerEnable()));
						break;
						case xC2:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshWindDetectionStatus(true);
							} else {
								refreshWindDetectionStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC2, EDT: 0x%02X}=={WindDetectionStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isWindDetectionStatus()));
						break;
						case xC3:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshSunlightDetectionStatus(true);
							} else {
								refreshSunlightDetectionStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC3, EDT: 0x%02X}=={SunlightDetectionStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isSunlightDetectionStatus()));
						break;
						case xD4:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshAutomaticOperationSetting(true);
							} else {
								refreshAutomaticOperationSetting(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD4, EDT: 0x%02X}=={AutomaticOperationSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isAutomaticOperationSetting()));
						break;
						case xE0:
							refreshOpenCloseSetting(EchonetDataConverter.OpenCloseStop.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={OpenCloseSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpenCloseSetting()));
						break;
						case xE1:
							refreshOpeningLevel(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={OpeningLevel:%d percent}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpeningLevel()));
						break;
						case xE8:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshRemoteOperatingEnable(true);
							} else {
								refreshRemoteOperatingEnable(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE8, EDT: 0x%02X}=={RemoteOperatingEnable:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isRemoteOperatingEnable()));
						break;
						case xE9:
							refreshSelectiveOpeningOperationSetting(EchonetDataConverter.SelectiveOperation.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE9, EDT: 0x%02X}=={SelectiveOpeningOperationSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSelectiveOpeningOperationSetting()));
						break;
						case xEA:
							refreshOpenCloseStatus(EchonetDataConverter.OpenCloseStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xEA, EDT: 0x%02X}=={OpenCloseStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpenCloseStatus()));
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
		epcs.add(EPC.x90);
		epcs.add(EPC.xC2);
		epcs.add(EPC.xC3);
		epcs.add(EPC.xD4);
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE8);
		epcs.add(EPC.xE9);
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
						case x90:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshTimerEnable(true);
							} else {
								refreshTimerEnable(false);
							}
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0x90, EDT: 0x%02X}=={TimerEnable:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isTimerEnable()));
						break;
						case xC2:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshWindDetectionStatus(true);
							} else {
								refreshWindDetectionStatus(false);
							}
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xC2, EDT: 0x%02X}=={WindDetectionStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isWindDetectionStatus()));
						break;
						case xC3:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshSunlightDetectionStatus(true);
							} else {
								refreshSunlightDetectionStatus(false);
							}
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xC3, EDT: 0x%02X}=={SunlightDetectionStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isSunlightDetectionStatus()));
						break;
						case xD4:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshAutomaticOperationSetting(true);
							} else {
								refreshAutomaticOperationSetting(false);
							}
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xD4, EDT: 0x%02X}=={AutomaticOperationSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isAutomaticOperationSetting()));
						break;
						case xE0:
							refreshOpenCloseSetting(EchonetDataConverter.OpenCloseStop.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={OpenCloseSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpenCloseSetting()));
						break;
						case xE8:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshRemoteOperatingEnable(true);
							} else {
								refreshRemoteOperatingEnable(false);
							}
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE8, EDT: 0x%02X}=={RemoteOperatingEnable:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isRemoteOperatingEnable()));
						break;
						case xE9:
							refreshSelectiveOpeningOperationSetting(EchonetDataConverter.SelectiveOperation.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE9, EDT: 0x%02X}=={SelectiveOpeningOperationSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSelectiveOpeningOperationSetting()));
						break;
						case xEA:
							refreshOpenCloseStatus(EchonetDataConverter.OpenCloseStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xEA, EDT: 0x%02X}=={OpenCloseStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpenCloseStatus()));
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

	public boolean isTimerEnable() {
		return timerEnable;
	}

	public void setTimerEnable(boolean timerEnable) {
		this.timerEnable = timerEnable;
	}

	public boolean isWindDetectionStatus() {
		return windDetectionStatus;
	}

	public void setWindDetectionStatus(boolean windDetectionStatus) {
		this.windDetectionStatus = windDetectionStatus;
	}

	public boolean isSunlightDetectionStatus() {
		return sunlightDetectionStatus;
	}

	public void setSunlightDetectionStatus(boolean sunlightDetectionStatus) {
		this.sunlightDetectionStatus = sunlightDetectionStatus;
	}

	public boolean isAutomaticOperationSetting() {
		return automaticOperationSetting;
	}

	public void setAutomaticOperationSetting(boolean automaticOperationSetting) {
		this.automaticOperationSetting = automaticOperationSetting;
	}

	public OpenCloseStop getOpenCloseSetting() {
		return openCloseSetting;
	}

	public void setOpenCloseSetting(OpenCloseStop openCloseSetting) {
		this.openCloseSetting = openCloseSetting;
	}

	public int getOpeningLevel() {
		return openingLevel;
	}

	public void setOpeningLevel(int openingLevel) {
		this.openingLevel = openingLevel;
	}

	public boolean isRemoteOperatingEnable() {
		return remoteOperatingEnable;
	}

	public void setRemoteOperatingEnable(boolean remoteOperatingEnable) {
		this.remoteOperatingEnable = remoteOperatingEnable;
	}

	public SelectiveOperation getSelectiveOpeningOperationSetting() {
		return selectiveOpeningOperationSetting;
	}

	public void setSelectiveOpeningOperationSetting(SelectiveOperation selectiveOpeningOperationSetting) {
		this.selectiveOpeningOperationSetting = selectiveOpeningOperationSetting;
	}

	public OpenCloseStatus getOpenCloseStatus() {
		return openCloseStatus;
	}

	public void setOpenCloseStatus(OpenCloseStatus openCloseStatus) {
		this.openCloseStatus = openCloseStatus;
	}
	
	
}
