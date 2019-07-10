package jaist.ac.jp.NodeFinder.echonet.object.cooking;



import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.common.EPC;
import echowand.net.Node;
import echowand.net.SubnetException;
import echowand.object.ObjectData;
import echowand.service.Service;
import echowand.service.result.GetListener;
import echowand.service.result.GetResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.App;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eCookingHeater extends eDataObject{
	private static final Logger logger = Logger.getLogger(eCookingHeater.class.getName());
	private Timer timer;
	private boolean heatingStatus;
	
	

	public eCookingHeater(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x03);
		this.setClassCode((byte) 0xB9);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.CookingHeater);
	}
	
	// Provided Services	
	public boolean setALLSTOP() {
		boolean rs = false;
		if(App.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xB3, new ObjectData((byte)0x40))) {
			rs= true;
		} else {
			rs = false;
		}
		return rs;
	}
	// Device Property Monitoring
	public void	refreshheatingStatus(boolean val) {
		if(val != isheatingStatus()) {
			logger.info(String.format("heatingStatus changed from %s to %s",isheatingStatus(),val));
			setheatingStatus(val);
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
						case xB1:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshheatingStatus(true);
							} else {
								refreshheatingStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={heatingStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isheatingStatus()));
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

	public boolean isheatingStatus() {
		return heatingStatus;
	}

	public void setheatingStatus(boolean heatingStatus) {
		this.heatingStatus = heatingStatus;
	}

}
