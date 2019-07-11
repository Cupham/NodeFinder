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
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.AirconditionerOperationMode;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eCommercialAirConditionerIndoorUnit extends eDataObject{
	private static final Logger logger = Logger.getLogger(eCommercialAirConditionerIndoorUnit.class.getName());
	private Timer timer;
	private AirconditionerOperationMode operationModeSetting;
	private int temperatureSetting;
	private boolean thermostatState;
	private AirconditionerOperationMode currentFunction;
	private String groupInfor;
	
	

	public eCommercialAirConditionerIndoorUnit(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x01);
		this.setClassCode((byte) 0x56);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.CommercialAirconditionerIndoorUnit);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshGroupInfor(String val) {
		if(val.equals(this.getGroupInfor())) {
			logger.info(String.format("GroupInfor changed from %s to %s",getGroupInfor(),val));
			setGroupInfor(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshCurrentFunction(AirconditionerOperationMode val) {
		if(val != getCurrentFunction()) {
			logger.info(String.format("CurrentFunction changed from %s to %s",getCurrentFunction(),val));
			setCurrentFunction(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshThermostatState(boolean val) {
		if(val != isThermostatState()) {
			logger.info(String.format("ThermostatState changed from %s to %s",isThermostatState(),val));
			setThermostatState(val);
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
	public void	refreshTemperatureSetting(int val) {
		if(val != getTemperatureSetting()) {
			logger.info(String.format("TemperatureSetting changed from %d to %d",getTemperatureSetting(),val));
			setTemperatureSetting(val);
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
		epcs.add(EPC.xAC);
		epcs.add(EPC.xAE);
		epcs.add(EPC.xCA);
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
							refreshOperationModeSetting(EchonetDataConverter.AirconditionerOperationMode.
									fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={OperationModeSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOperationModeSetting()));
						break;
						case xB3:
							refreshTemperatureSetting(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB3, EDT: 0x%02X}=={TemperatureSetting:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getTemperatureSetting()));
						break;
						case xAC:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshThermostatState(true);
							} else {
								refreshThermostatState(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xAC, EDT: 0x%02X}=={ThermostatState:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isThermostatState()));
						break;
						case xAE:
							refreshCurrentFunction(EchonetDataConverter.AirconditionerOperationMode.
									fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xAE, EDT: 0x%02X}=={refreshCurrentFunction:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getCurrentFunction()));
						break;
						case xCA:
							refreshGroupInfor(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xCA, EDT: 0x%02X}=={GroupInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getGroupInfor()));
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
		epcs.add(EPC.xB3);
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
							refreshOperationModeSetting(EchonetDataConverter.AirconditionerOperationMode.
									fromCode(resultData.toBytes()[0]));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={OperationModeSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOperationModeSetting()));
						break;
						case xB3:
							refreshTemperatureSetting(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB3, EDT: 0x%02X}=={TemperatureSetting:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getTemperatureSetting()));
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

	public int getTemperatureSetting() {
		return temperatureSetting;
	}

	public void setTemperatureSetting(int temperatureSetting) {
		this.temperatureSetting = temperatureSetting;
	}

	public boolean isThermostatState() {
		return thermostatState;
	}

	public void setThermostatState(boolean thermostatState) {
		this.thermostatState = thermostatState;
	}

	public AirconditionerOperationMode getCurrentFunction() {
		return currentFunction;
	}

	public void setCurrentFunction(AirconditionerOperationMode currentFunction) {
		this.currentFunction = currentFunction;
	}

	public String getGroupInfor() {
		return groupInfor;
	}

	public void setGroupInfor(String groupInfor) {
		this.groupInfor = groupInfor;
	}
}
