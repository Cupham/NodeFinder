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

public class eGeneralLighting extends eDataObject{
	private static final Logger logger = Logger.getLogger(eGeneralLighting.class.getName());
	private Timer timer;
	private int illuminanceLevel;
	

	public eGeneralLighting(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x90);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.GeneralLight);
	}
	
	// Provided Services	
	public boolean setDeviceBrightness(int brightness) {
		boolean rs = false;
		if(this.getIlluminanceLevel() == brightness) {
			logger.info(String.format("Light brightness is already set to %d ! nothing to do", brightness));
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xB0, new ObjectData(new Integer(brightness).byteValue()))) {
				refreshIlluminanceLevel(brightness);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
		
	// Device Property Monitoring
	public void	refreshIlluminanceLevel (int illuminance) {
		if(this.getIlluminanceLevel() != illuminance) {
			this.illuminanceLevel = illuminance;
		}
	}
	
	// Override functions
	@Override
	public void dataFromEOJ(Service s){
		final Service service = s;
		final Node node = this.getNode();
		final EOJ eoj = this.getEoj();
		observeSuperData(service, node, eoj);
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				getSpecificData(service);
				getSuperData(service, node, eoj);
				
			}
		}, SampleConstants.getDelayInterval(), SampleConstants.getRefreshInterval());	
	}
	public int getIlluminanceLevel() {
		return illuminanceLevel;
	}
	public void setIlluminanceLevel(int illuminanceLevel) {
		this.illuminanceLevel = illuminanceLevel;
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
							int illuminance = EchonetDataConverter.dataToInteger(resultData);
							refreshIlluminanceLevel(illuminance);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={IlluminanceLevel:%d}",
									 getNode().getNodeInfo().toString(),resultData.toBytes()[0],resultData.toBytes()[1],getIlluminanceLevel()));	
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
		// TODO Auto-generated method stub
		
	}
}
