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

public class eVisitorSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eVisitorSensor.class.getName());
	private Timer timer;
	private boolean visitorDetectionStatus;
	private String detectionThresholdLevel;
	private int visitorHoldingTime;

	public eVisitorSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x08);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.VisitorDetectionSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshDetectionThresholdLevel(String level) {
		if(!level.equals(getDetectionThresholdLevel())) {
			logger.info(String.format("VisitorDetectionThresholdLevel changed from %s to %s",getDetectionThresholdLevel(),level));
			setDetectionThresholdLevel(level);
			//TODO: More task can be added here
		}
	}
	
	public void	refreshVisitorDetection (boolean status) {
		if(this.isVisitorDetectionStatus() != status) {
			logger.info(String.format("VisitorDetectionStatus changed from %b to %b",isVisitorDetectionStatus(),status));
			setVisitorDetectionStatus(status);
			//TODO: More task can be added here
		}
	}
	public void	refreshVisitorHoldingTime (int time) {
		if(this.getVisitorHoldingTime() != time) {
			logger.info(String.format("VisitorHoldingTime changed from %b to %b",getVisitorHoldingTime(),time));
			setVisitorHoldingTime(time);
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
		epcs.add(EPC.xB1);
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
						case xB1:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshVisitorDetection(true);
							} else {
								refreshVisitorDetection(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={VisitorDetect:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isVisitorDetectionStatus()));
						break;
						case xB0:
							refreshDetectionThresholdLevel(EchonetDataConverter.dataToLevel(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={VisitorDetectThresholdLevel:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getDetectionThresholdLevel()));
						break;
						case xBE:
							refreshVisitorHoldingTime(EchonetDataConverter.dataToInteger(resultData) * 10);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xBE, EDT: 0x%02X}=={VisitorHoldingTime:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getVisitorHoldingTime()));
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

	public boolean isVisitorDetectionStatus() {
		return visitorDetectionStatus;
	}

	public void setVisitorDetectionStatus(boolean visitorDetectionStatus) {
		this.visitorDetectionStatus = visitorDetectionStatus;
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
		epcs.add(EPC.xB1);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xB1:
						if(EchonetDataConverter.dataToInteger(resultData) == 65) {
							refreshVisitorDetection(true);
						} else {
							refreshVisitorDetection(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={VisitorDetect:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isVisitorDetectionStatus()));
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

	public int getVisitorHoldingTime() {
		return visitorHoldingTime;
	}

	public void setVisitorHoldingTime(int visitorHoldingTime) {
		this.visitorHoldingTime = visitorHoldingTime;
	}

}
