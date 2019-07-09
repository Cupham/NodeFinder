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

public class eMicroMotionSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eMicroMotionSensor.class.getName());
	private Timer timer;
	private boolean micromotionDetectionStatus;
	private String detectionThresholdLevel;
	private int detectionCounter;
	private int samplingCount;
	private int samplingCycle;

	public eMicroMotionSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x26);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.MicroMotionSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshDetectionThresholdLevel(String level) {
		if(!level.equals(getDetectionThresholdLevel())) {
			logger.info(String.format("MicromotionDetectionThresholdLevel changed from %s to %s",getDetectionThresholdLevel(),level));
			setDetectionThresholdLevel(level);
			//TODO: More task can be added here
		}
	}
	
	public void	refreshMicromotionDetection (boolean status) {
		if(this.isMicromotionDetectionStatus() != status) {
			logger.info(String.format("MicromotionDetectionStatus changed from %b to %b",isMicromotionDetectionStatus(),status));
			setMicromotionDetectionStatus(status);
			//TODO: More task can be added here
		}
	}
	public void	refreshSamplingCount(int val) {
		if(this.getSamplingCount() != val) {
			logger.info(String.format("SamplingCount changed from %d to %d",getSamplingCount(),val));
			setSamplingCount(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshSamplingCycle(int val) {
		if(this.getSamplingCycle() != val) {
			logger.info(String.format("SamplingCycle changed from %d to %d",getSamplingCycle(),val));
			setSamplingCycle(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshDetectionCounter (int val) {
		if(this.getDetectionCounter() != val) {
			logger.info(String.format("DetectionCounter changed from %d to %d",getDetectionCounter(),val));
			setDetectionCounter(val);
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
		epcs.add(EPC.xB2);
		epcs.add(EPC.xBC);
		epcs.add(EPC.xBD);
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
								refreshMicromotionDetection(true);
							} else {
								refreshMicromotionDetection(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={MicromotionDetectionDetect:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isMicromotionDetectionStatus()));
						break;
						case xB0:
							refreshDetectionThresholdLevel(EchonetDataConverter.dataToLevel(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={MicromotionDetectionDetectThresholdLevel:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getDetectionThresholdLevel()));
						break;
						case xB2:
							refreshDetectionCounter(EchonetDataConverter.dataToShort(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB2, EDT: 0x%02X}=={DetectionCounter:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getDetectionCounter()));
						break;
						case xBC:
							refreshSamplingCount(EchonetDataConverter.dataToShort(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xBC, EDT: 0x%02X}=={SamplingCount:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSamplingCount()));
						break;
						case xBD:
							refreshSamplingCycle(EchonetDataConverter.dataToShort(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xBD, EDT: 0x%02X}=={SamplingCycle:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSamplingCycle()));
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

	public boolean isMicromotionDetectionStatus() {
		return micromotionDetectionStatus;
	}

	public void setMicromotionDetectionStatus(boolean micromotionDetectionStatus) {
		this.micromotionDetectionStatus = micromotionDetectionStatus;
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
							refreshMicromotionDetection(true);
						} else {
							refreshMicromotionDetection(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={MicromotionDetectionDetect:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isMicromotionDetectionStatus()));
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

	public int getDetectionCounter() {
		return detectionCounter;
	}

	public void setDetectionCounter(int detectionCounter) {
		this.detectionCounter = detectionCounter;
	}

	public int getSamplingCount() {
		return samplingCount;
	}

	public void setSamplingCount(int samplingCount) {
		this.samplingCount = samplingCount;
	}

	public int getSamplingCycle() {
		return samplingCycle;
	}

	public void setSamplingCycle(int samplingCycle) {
		this.samplingCycle = samplingCycle;
	}

}
