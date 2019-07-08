package jaist.ac.jp.NodeFinder.echonet.object;



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
import jaist.ac.jp.NodeFinder.util.EDTParser;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eGeneralLighting extends eDataObject{
	private static final Logger logger = Logger.getLogger(eGeneralLighting.class.getName());
	/**
	 * EPC: 0xE0 Measured temperature value in units of 0.1 Celcius Value
	 * between: 0xF554–0x7FFE (-2732–32766)~(-273.2–3276.6 Celcius)
	 */
	private Timer timer;
	private int illuminanceLevel;
	

	public eGeneralLighting(Node node,EOJ eoj) {
		super(node, eoj);
		this.groupCode= (byte) 0x02;
		this.classCode = (byte) 0x90;
		this.instanceCode = eoj.getInstanceCode();
		setDeviceID("Lighting"+getDeviceID());	
	}
	
	// Provided Services	
	public boolean setDeviceBrightness(int brightness) {
		boolean rs = false;
		if(this.getIlluminanceLevel() == brightness) {
			logger.info(String.format("Light brightness is already set to %d ! nothing to do", brightness));
			rs = true;
		} else {
			if(EDTParser.executeCommand(App.echonetService,this.node,this.eoj,EPC.xB0, new ObjectData(new Integer(brightness).byteValue()))) {
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
		final Node node = this.node;
		final EOJ eoj = this.eoj;
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
			service.doGet(node, eoj, epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xB0:
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
}
