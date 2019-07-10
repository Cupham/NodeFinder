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
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eWaterLevelSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eWaterLevelSensor.class.getName());
	private Timer timer;
	private int overWaterLevelValue;
	private int measuredWaterLevelValue;
	private boolean overWaterLevelDetectionStatus;
	
	
	public eWaterLevelSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x14);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.WaterLevelSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshOverWaterLevelValue(int overWaterLevelValueInCm) {
		if(this.getOverWaterLevelValue() != overWaterLevelValueInCm) {
			logger.info(String.format("OverWaterLevelValue changed from %d cm to %d cm",getOverWaterLevelValue(),overWaterLevelValueInCm));
			setOverWaterLevelValue(overWaterLevelValueInCm);
			//TODO: More task can be added here
		}
	}
	public void	refreshMeasuredWaterLevelValue(int measureWaterLevelValueInCm) {
		if(this.getMeasuredWaterLevelValue() != measureWaterLevelValueInCm) {
			logger.info(String.format("WaterLevelValue changed from %d cm to %d cm",getMeasuredWaterLevelValue(),measureWaterLevelValueInCm));
			setMeasuredWaterLevelValue(measureWaterLevelValueInCm);
			//TODO: More task can be added here
		}
	}
	public void	refreshOverWaterLevelDetectionStatus(boolean waterOverflow) {
		if(this.isOverWaterLevelDetectionStatus() != waterOverflow) {
			logger.info(String.format("rWaterLevelDetectionStatus changed from %b  to %b ",isOverWaterLevelDetectionStatus(),waterOverflow));
			setOverWaterLevelDetectionStatus(waterOverflow);
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
		epcs.add(EPC.xE0);
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
							refreshOverWaterLevelValue(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={OverWaterLevelValue (cm):%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOverWaterLevelValue()));
						break;
						case xB1:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshOverWaterLevelDetectionStatus(true);
							} else {
								refreshOverWaterLevelDetectionStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={OverWaterLevelDetect:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isOverWaterLevelDetectionStatus()));
						break;	
						case xE0:
							refreshMeasuredWaterLevelValue(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={MeasuredWaterLevelValue (cm):%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredWaterLevelValue()));
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
							refreshOverWaterLevelDetectionStatus(true);
						} else {
							refreshOverWaterLevelDetectionStatus(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={OverWaterLevelDetect:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isOverWaterLevelDetectionStatus()));
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

	public int getOverWaterLevelValue() {
		return overWaterLevelValue;
	}

	public void setOverWaterLevelValue(int overWaterLevelValue) {
		this.overWaterLevelValue = overWaterLevelValue;
	}

	public int getMeasuredWaterLevelValue() {
		return measuredWaterLevelValue;
	}

	public void setMeasuredWaterLevelValue(int measuredWaterLevelValue) {
		this.measuredWaterLevelValue = measuredWaterLevelValue;
	}

	public boolean isOverWaterLevelDetectionStatus() {
		return overWaterLevelDetectionStatus;
	}

	public void setOverWaterLevelDetectionStatus(boolean overWaterLevelDetectionStatus) {
		this.overWaterLevelDetectionStatus = overWaterLevelDetectionStatus;
	}

}
