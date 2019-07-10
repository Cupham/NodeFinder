package jaist.ac.jp.NodeFinder.echonet.object.airconditioner;




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
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.HumidifyingSetting;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eHumidifier extends eDataObject{
	private static final Logger logger = Logger.getLogger(eHumidifier.class.getName());
	private Timer timer;
	private String humidifyingSetting1;
	private HumidifyingSetting humidifyingSetting2;
	
	

	public eHumidifier(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x01);
		this.setClassCode((byte) 0x39);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.Humidifier);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshHumidifyingSetting1 (String val) {
		if(val.equals(this.getHumidifyingSetting1())) {
			logger.info(String.format("HumidifyingSetting1 changed from %s to %s",getHumidifyingSetting1(),val));
			setHumidifyingSetting1(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshHumidifyingSetting2 (HumidifyingSetting val) {
		if(this.getHumidifyingSetting2() != val) {
			logger.info(String.format("HumidifyingSetting2 changed from %s to %s",getHumidifyingSetting2(),val));
			setHumidifyingSetting2(val);
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
		epcs.add(EPC.xC0);
		epcs.add(EPC.xC1);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xC0:
							String val = "";
							if(resultData.toBytes()[0] >=(byte)0x00 && resultData.toBytes()[0] <=(byte)0x64) {
								val = EchonetDataConverter.dataToInteger(resultData) + " %";
							} else {
								val = EchonetDataConverter.HumidifyingSetting.retrieveByCode(resultData.toBytes()[0]).name();
							}
							refreshHumidifyingSetting1(val);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC0, EDT: 0x%02X}=={refreshHumidifyingSetting1:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getHumidifyingSetting1()));
						break;
						case xC1:
							refreshHumidifyingSetting2(EchonetDataConverter.HumidifyingSetting.retrieveByCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={CallDetectThresholdLevel:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getHumidifyingSetting2()));
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

	public String getHumidifyingSetting1() {
		return humidifyingSetting1;
	}

	public void setHumidifyingSetting1(String humidifyingSetting1) {
		this.humidifyingSetting1 = humidifyingSetting1;
	}

	public HumidifyingSetting getHumidifyingSetting2() {
		return humidifyingSetting2;
	}

	public void setHumidifyingSetting2(HumidifyingSetting humidifyingSetting2) {
		this.humidifyingSetting2 = humidifyingSetting2;
	}
}
