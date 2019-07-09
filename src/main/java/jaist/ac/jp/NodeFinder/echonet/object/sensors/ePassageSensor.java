package jaist.ac.jp.NodeFinder.echonet.object.sensors;



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
import jaist.ac.jp.NodeFinder.echonet.objectmapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class ePassageSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(ePassageSensor.class.getName());
	private Timer timer;
	private String passageDetectionDirection;
	private String detectionThresholdLevel;
	private int passageDetectionHoldingTime;

	public ePassageSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x27);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.PassageSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshDetectionThresholdLevel(String level) {
		if(!level.equals(getDetectionThresholdLevel())) {
			logger.info(String.format("PassageDetectionThresholdLevel changed from %s to %s",getDetectionThresholdLevel(),level));
			setDetectionThresholdLevel(level);
			//TODO: More task can be added here
		}
	}
	
	public void	refreshPassageDetectionDirection(String direction) {
		if(!direction.equals(getPassageDetectionDirection())) {
			logger.info(String.format("PassageDetectionDirection changed from %s to %s",getPassageDetectionDirection(),direction));
			setPassageDetectionDirection(direction);
			//TODO: More task can be added here
		}
	}
	public void	refreshPassageDetectionHoldingTime (int time) {
		if(this.getPassageDetectionHoldingTime() != time) {
			logger.info(String.format("PassageDetectionHoldingTime changed from %d to %d",getPassageDetectionHoldingTime(),time));
			setPassageDetectionHoldingTime(time);
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
		epcs.add(EPC.xE0);
		epcs.add(EPC.xBE);
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
							refreshPassageDetectionDirection(EchonetDataConverter.dataToDirection(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={PassageDetectionDetect:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getPassageDetectionDirection()));
						break;
						case xB0:
							refreshDetectionThresholdLevel(EchonetDataConverter.dataToLevel(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={PassageDetectionDetectThresholdLevel:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getDetectionThresholdLevel()));
						break;
						case xBE:
							refreshPassageDetectionHoldingTime(EchonetDataConverter.dataToInteger(resultData) * 10);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xBE, EDT: 0x%02X}=={PassageDetectionHoldingTime:%d ms}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getPassageDetectionHoldingTime()));
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

	public String getDetectionThresholdLevel() {
		return detectionThresholdLevel;
	}

	public void setDetectionThresholdLevel(String detectionThresholdLevel) {
		this.detectionThresholdLevel = detectionThresholdLevel;
	}

	@Override
	public void observeSpecificData(Service service) {
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xE0);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xE0:
						refreshPassageDetectionDirection(EchonetDataConverter.dataToDirection(resultData));
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={PassageDetectionDetect:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getPassageDetectionDirection()));
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

	public int getPassageDetectionHoldingTime() {
		return passageDetectionHoldingTime;
	}

	public void setPassageDetectionHoldingTime(int passageDetectionHoldingTime) {
		this.passageDetectionHoldingTime = passageDetectionHoldingTime;
	}

	public String getPassageDetectionDirection() {
		return passageDetectionDirection;
	}

	public void setPassageDetectionDirection(String passageDetectionDirection) {
		this.passageDetectionDirection = passageDetectionDirection;
	}

}
