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

public class eOxygenSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eOxygenSensor.class.getName());
	private Timer timer;
	private float measuredOxygenConcentration;

	public eOxygenSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x0C);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.OxygenSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshMeasuredOxygenConcentration(float val) {
		if(getMeasuredOxygenConcentration() != val) {
			logger.info(String.format("MeasuredOxygenConcentration changed from %f to %f",getMeasuredOxygenConcentration(),val));
			setMeasuredOxygenConcentration(val);
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
							refreshMeasuredOxygenConcentration(EchonetDataConverter.dataToShort(resultData)*0.01f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={OxygenConcentration:%f percent}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredOxygenConcentration()));
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
		//no extra observable attribute
		
	}

	public float getMeasuredOxygenConcentration() {
		return measuredOxygenConcentration;
	}

	public void setMeasuredOxygenConcentration(float measuredOxygenConcentration) {
		this.measuredOxygenConcentration = measuredOxygenConcentration;
	}

}
