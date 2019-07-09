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

public class eOpenCloseSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eOpenCloseSensor.class.getName());
	private Timer timer;
	private String openingDegree;
	private String detectionThresholdLevel;
	private boolean openingStatus;

	public eOpenCloseSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x29);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.OpenCloseSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshDetectionThresholdLevel(String level) {
		if(!level.equals(getDetectionThresholdLevel())) {
			logger.info(String.format("OpeningDetectionThresholdLevel changed from %s to %s",getDetectionThresholdLevel(),level));
			setDetectionThresholdLevel(level);
			//TODO: More task can be added here
		}
	}
	
	public void	refreshOpeningDegree(String degree) {
		if(!degree.equals(getOpeningDegree())) {
			logger.info(String.format("OpenningDegree changed from %s to %s",getOpeningDegree(),degree));
			setOpenningDegree(degree);
			//TODO: More task can be added here
		}
	}
	public void	refreshOpeningStatusDetection(boolean val) {
		if(this.isOpeningStatus()!= val) {
			logger.info(String.format("OpeningStatusDetection changed from %d to %d",isOpeningStatus(),val));
			setOpeningStatus(val);
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
		epcs.add(EPC.xB1);
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
							refreshOpeningDegree(EchonetDataConverter.dataToOpeningLevel(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={OpeningDegree:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpeningDegree()));
						break;
						case xB0:
							refreshDetectionThresholdLevel(EchonetDataConverter.dataToLevel(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={PassageDetectionDetectThresholdLevel:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getDetectionThresholdLevel()));
						break;
						case xB1:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshOpeningStatusDetection(true);
							} else {
								refreshOpeningStatusDetection(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={OpeningStatusDetection:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isOpeningStatus()));
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
							refreshOpeningStatusDetection(true);
						} else {
							refreshOpeningStatusDetection(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={OpeningStatusDetection:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isOpeningStatus()));
					default:
						break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE,ex.toString());
			
		}
		
	}

	public String getOpeningDegree() {
		return openingDegree;
	}

	public void setOpenningDegree(String openningDegree) {
		this.openingDegree = openningDegree;
	}

	public boolean isOpeningStatus() {
		return openingStatus;
	}

	public void setOpeningStatus(boolean openningStatus) {
		this.openingStatus = openningStatus;
	}

}
