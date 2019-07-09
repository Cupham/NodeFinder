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
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.objectmapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eCurrentValueSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eCurrentValueSensor.class.getName());
	private Timer timer;
	private long measuredCurrent1;
	private long measuredCurrent2;
	private float ratedVoltageToBeMeasured;
	
	
	public eCurrentValueSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x23);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.CurrentValueSensor);
	}
	
	// Provided Services			
	// Device Property Monitoring
	public void	refreshMeasuredCurrent1(long val) {
		if(val != this.getMeasuredCurrent1()) {
			logger.info(String.format("MeasuredCurrent1 changed from %d to %d",getMeasuredCurrent1(),val));
			setMeasuredCurrent1(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshMeasuredCurrent2(long val) {
		if(val != this.getMeasuredCurrent2()) {
			logger.info(String.format("MeasuredCurrent2 changed from %d to %d",getMeasuredCurrent2(),val));
			setMeasuredCurrent2(val);
			//TODO: More task can be added here
		}
	}
	
	public void	refreshRatedVoltageToBeMeasured(int val) {
		if(val != this.getRatedVoltageToBeMeasured()) {
			logger.info(String.format("RatedVoltageToBeMeasured changed from %d to %d",getRatedVoltageToBeMeasured(),val));
			setRatedVoltageToBeMeasured(val);
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
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE1);
		epcs.add(EPC.xE2);
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
							refreshMeasuredCurrent1(EchonetDataConverter.dataToLong(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={MeasuredCurrent1:%d mA}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredCurrent1()));
						break;
						case xE1:
							refreshRatedVoltageToBeMeasured(EchonetDataConverter.dataToShort(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={RatedVoltageToBeMeasured:%d V}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getRatedVoltageToBeMeasured()));
						break;
						case xE2:
							refreshMeasuredCurrent2(EchonetDataConverter.dataToLong(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE2, EDT: 0x%02X}=={hMeasuredCurrent2:%d mA}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredCurrent2()));
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
	}

	public long getMeasuredCurrent1() {
		return measuredCurrent1;
	}

	public void setMeasuredCurrent1(long measuredCurrent1) {
		this.measuredCurrent1 = measuredCurrent1;
	}

	public long getMeasuredCurrent2() {
		return measuredCurrent2;
	}

	public void setMeasuredCurrent2(long measuredCurrent2) {
		this.measuredCurrent2 = measuredCurrent2;
	}

	public float getRatedVoltageToBeMeasured() {
		return ratedVoltageToBeMeasured;
	}

	public void setRatedVoltageToBeMeasured(float ratedVoltageToBeMeasured) {
		this.ratedVoltageToBeMeasured = ratedVoltageToBeMeasured;
	}

	

}
