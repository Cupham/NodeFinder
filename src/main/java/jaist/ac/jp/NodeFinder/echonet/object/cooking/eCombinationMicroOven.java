package jaist.ac.jp.NodeFinder.echonet.object.cooking;




import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import echowand.service.Service;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eCombinationMicroOven extends eDataObject{
	private static final Logger logger = Logger.getLogger(eCombinationMicroOven.class.getName());
	private Timer timer;
	
	

	public eCombinationMicroOven(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x03);
		this.setClassCode((byte) 0xB8);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.MicrowaveOven);
	}
	
	// Provided Services	
	// Device Property Monitoring
	
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
	}

	@Override
	public void observeSpecificData(Service service) {
	}

}