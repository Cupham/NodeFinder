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

public class eWaterFlowRateSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eWaterFlowRateSensor.class.getName());
	private Timer timer;
	
	private long cumulativeFlowRate;
	private long flowRate;
	
	
	public eWaterFlowRateSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x25);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.WaterFlowRateSensor);
	}
	
	// Provided Services			
	// Device Property Monitoring
	public void	refreshCumulativeFlowRate(long val) {
		if(val != this.getCumulativeFlowRate()) {
			logger.info(String.format("CumulativeFlowRate changed from %d to %d",getCumulativeFlowRate(),val));
			setCumulativeFlowRate(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshFlowRate(long val) {
		if(val != this.getFlowRate()) {
			logger.info(String.format("FlowRate changed from %d to %d",getFlowRate(),val));
			setFlowRate(val);
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
							refreshCumulativeFlowRate(EchonetDataConverter.dataToLong(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={CumulativeFlowRate:%d cm3}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getCumulativeFlowRate()));
						break;
						
						case xE2:
							refreshFlowRate(EchonetDataConverter.dataToShort(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE2, EDT: 0x%02X}=={FlowRate:%d cm3/min}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getFlowRate()));
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

	public long getCumulativeFlowRate() {
		return cumulativeFlowRate;
	}

	public void setCumulativeFlowRate(long cumulativeFlowRate) {
		this.cumulativeFlowRate = cumulativeFlowRate;
	}

	public long getFlowRate() {
		return flowRate;
	}

	public void setFlowRate(long flowRate) {
		this.flowRate = flowRate;
	}
	

}
