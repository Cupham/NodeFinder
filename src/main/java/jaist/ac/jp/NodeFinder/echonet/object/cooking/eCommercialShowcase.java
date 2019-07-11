package jaist.ac.jp.NodeFinder.echonet.object.cooking;



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
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.ShowcaseOperationMode;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.ShowcaseShape;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.ShowcaseType;

public class eCommercialShowcase extends eDataObject{
	private static final Logger logger = Logger.getLogger(eCommercialShowcase.class.getName());
	private Timer timer;
	private ShowcaseOperationMode operationModeSetting;
	private String groupInfor;
	private boolean internalLightingStatus; 
	private boolean externalLightingStatus; 
	private int internalTemperature;
	private boolean heaterStatus; 
	private int insideCaseSettingTemperature;
	private ShowcaseType showcaseTypeInfor;
	private boolean doorStatus; 
	private boolean showcaseConfigInfor; 
	private ShowcaseShape showcaseShapeInfor;
	private boolean insideCaseTemperatureRange;
	
	

	public eCommercialShowcase(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x03);
		this.setClassCode((byte) 0xCE);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.CommercialShowcase);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshInsideCaseTemperatureRange(boolean val) {
		if(val != isInsideCaseTemperatureRange()) {
			logger.info(String.format("ShowcaseConfigInfor changed from %s to %s",
					isInsideCaseTemperatureRange()?"Refrigeration":"Freezing",val?"Refrigeration":"Freezing"));
			setInsideCaseTemperatureRange(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshShowcaseShapeInfor(ShowcaseShape val) {
		if(val != getShowcaseShapeInfor()) {
			logger.info(String.format("ShowcaseShapeInfor changed from %s to %s",getShowcaseShapeInfor(),val));
			setShowcaseShapeInfor(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshShowcaseConfigInfor(boolean val) {
		if(val != isShowcaseConfigInfor()) {
			logger.info(String.format("ShowcaseConfigInfor changed from %s to %s",
					isShowcaseConfigInfor()?"SeperateType":"BuiltInType",val?"SeperateType":"BuiltInType"));
			setShowcaseConfigInfor(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshDoorStatus(boolean val) {
		if(val != isDoorStatus()) {
			logger.info(String.format("DoorStatus changed from %s to %s",isDoorStatus()?"OPEN":"CLOSE",val?"OPEN":"CLOSE"));
			setDoorStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshShowcaseTypeInfor(ShowcaseType val) {
		if(val != getShowcaseTypeInfor()) {
			logger.info(String.format("ShowcaseTypeInfor changed from %s to %s",getShowcaseTypeInfor(),val));
			setShowcaseTypeInfor(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshInsideCaseSettingTemperature(int val) {
		if(val != getInsideCaseSettingTemperature()) {
			logger.info(String.format("InsideCaseSettingTemperature changed from %d to %d",getInsideCaseSettingTemperature(),val));
			setInsideCaseSettingTemperature(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshHeaterStatus(boolean val) {
		if(val != isHeaterStatus()) {
			logger.info(String.format("refreshHeaterStatus changed from %s to %s",isHeaterStatus()?"ON":"OFF",val?"ON":"OFF"));
			setHeaterStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshInternalTemperature(int val) {
		if(val != getInternalTemperature()) {
			logger.info(String.format("InternalTemperature changed from %d to %d",getInternalTemperature(),val));
			setInternalTemperature(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshExternalLightingStatus(boolean val) {
		if(val != isExternalLightingStatus()) {
			logger.info(String.format("ExternalLightingStatus changed from %s to %s",isExternalLightingStatus()?"ON":"OFF",val?"ON":"OFF"));
			setExternalLightingStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshInternalLightingStatus(boolean val) {
		if(val != isInternalLightingStatus()) {
			logger.info(String.format("InternalLightingStatus changed from %s to %s",isInternalLightingStatus()?"ON":"OFF",val?"ON":"OFF"));
			setInternalLightingStatus(val);
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
	public void	refreshOperationModeSetting(ShowcaseOperationMode val) {
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
		epcs.add(EPC.xCA);
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE1);
		epcs.add(EPC.xE3);
		epcs.add(EPC.xE7);
		epcs.add(EPC.xEF);
		epcs.add(EPC.xD0);
		epcs.add(EPC.xD1);
		epcs.add(EPC.xD2);
		epcs.add(EPC.xD3);
		epcs.add(EPC.xD4);
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
							refreshOperationModeSetting(EchonetDataConverter.ShowcaseOperationMode.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={OperationModeSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOperationModeSetting()));
						break;
						case xCA:
							refreshGroupInfor(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xCA, EDT: 0x%02X}=={GroupInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getGroupInfor()));
						break;
						case xE0:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshInternalLightingStatus(true);
							} else {
								refreshInternalLightingStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={InternalLightingStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isInternalLightingStatus()?"ON":"OFF"));
						break;
						case xE1:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshExternalLightingStatus(true);
							} else {
								refreshExternalLightingStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={ExternalLightingStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isExternalLightingStatus()?"ON":"OFF"));
						break;
						case xE3:
							refreshInternalTemperature(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE3, EDT: 0x%02X}=={InternalTemperature:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getInternalTemperature()));
						break;
						case xE7:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshHeaterStatus(true);
							} else {
								refreshHeaterStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE7, EDT: 0x%02X}=={HeaterStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isHeaterStatus()?"ON":"OFF"));
						break;
						case xEF:
							refreshInsideCaseSettingTemperature(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xEF, EDT: 0x%02X}=={InsideCaseSettingTemperature:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getInsideCaseSettingTemperature()));
						break;
						case xD0:
							refreshShowcaseTypeInfor(EchonetDataConverter.ShowcaseType.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD0, EDT: 0x%02X}=={ShowcaseTypeInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getShowcaseTypeInfor()));
						break;
						case xD1:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshDoorStatus(true);
							} else {
								refreshDoorStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD1, EDT: 0x%02X}=={DoorStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isDoorStatus()?"OPEN":"CLOSE"));
						break;
						case xD2:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshShowcaseConfigInfor(true);
							} else {
								refreshShowcaseConfigInfor(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD2, EDT: 0x%02X}=={ShowcaseConfigInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isShowcaseConfigInfor()?"SeperateType":"BuiltInType"));
						break;
						case xD3:
							refreshShowcaseShapeInfor(EchonetDataConverter.ShowcaseShape.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD3, EDT: 0x%02X}=={ShowcaseShapeInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getShowcaseShapeInfor()));
						break;
						case xD4:
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshInsideCaseTemperatureRange(true);
							} else {
								refreshInsideCaseTemperatureRange(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD4, EDT: 0x%02X}=={InsideCaseTemperatureRange:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isInsideCaseTemperatureRange()?"Refrigeration":"Freezing"));
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
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE1);
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
								refreshOperationModeSetting(EchonetDataConverter.ShowcaseOperationMode.fromCode(resultData.toBytes()[0]));
								logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={OperationModeSetting:%s}",
										 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOperationModeSetting()));
							break;
							case xE0:
								if(EchonetDataConverter.dataToInteger(resultData) == 48) {
									refreshInternalLightingStatus(true);
								} else {
									refreshInternalLightingStatus(false);
								}
								logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={InternalLightingStatus:%s}",
										 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isInternalLightingStatus()?"ON":"OFF"));
							break;
							case xE1:
								if(EchonetDataConverter.dataToInteger(resultData) == 48) {
									refreshExternalLightingStatus(true);
								} else {
									refreshExternalLightingStatus(false);
								}
								logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={ExternalLightingStatus:%s}",
										 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isExternalLightingStatus()?"ON":"OFF"));
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

	public ShowcaseOperationMode getOperationModeSetting() {
		return operationModeSetting;
	}

	public void setOperationModeSetting(ShowcaseOperationMode operationModeSetting) {
		this.operationModeSetting = operationModeSetting;
	}

	public String getGroupInfor() {
		return groupInfor;
	}

	public void setGroupInfor(String groupInfor) {
		this.groupInfor = groupInfor;
	}

	public boolean isInternalLightingStatus() {
		return internalLightingStatus;
	}

	public void setInternalLightingStatus(boolean internalLightingStatus) {
		this.internalLightingStatus = internalLightingStatus;
	}

	public boolean isExternalLightingStatus() {
		return externalLightingStatus;
	}

	public void setExternalLightingStatus(boolean externalLightingStatus) {
		this.externalLightingStatus = externalLightingStatus;
	}

	public int getInternalTemperature() {
		return internalTemperature;
	}

	public void setInternalTemperature(int internalTemperature) {
		this.internalTemperature = internalTemperature;
	}

	public boolean isHeaterStatus() {
		return heaterStatus;
	}

	public void setHeaterStatus(boolean heaterStatus) {
		this.heaterStatus = heaterStatus;
	}

	public int getInsideCaseSettingTemperature() {
		return insideCaseSettingTemperature;
	}

	public void setInsideCaseSettingTemperature(int insideCaseSettingTemperature) {
		this.insideCaseSettingTemperature = insideCaseSettingTemperature;
	}

	public ShowcaseType getShowcaseTypeInfor() {
		return showcaseTypeInfor;
	}

	public void setShowcaseTypeInfor(ShowcaseType showcaseTypeInfor) {
		this.showcaseTypeInfor = showcaseTypeInfor;
	}

	public boolean isDoorStatus() {
		return doorStatus;
	}

	public void setDoorStatus(boolean doorStatus) {
		this.doorStatus = doorStatus;
	}

	public boolean isShowcaseConfigInfor() {
		return showcaseConfigInfor;
	}

	public void setShowcaseConfigInfor(boolean showcaseConfigInfor) {
		this.showcaseConfigInfor = showcaseConfigInfor;
	}

	public ShowcaseShape getShowcaseShapeInfor() {
		return showcaseShapeInfor;
	}

	public void setShowcaseShapeInfor(ShowcaseShape showcaseShapeInfor) {
		this.showcaseShapeInfor = showcaseShapeInfor;
	}

	public boolean isInsideCaseTemperatureRange() {
		return insideCaseTemperatureRange;
	}

	public void setInsideCaseTemperatureRange(boolean insideCaseTemperatureRange) {
		this.insideCaseTemperatureRange = insideCaseTemperatureRange;
	}


}
