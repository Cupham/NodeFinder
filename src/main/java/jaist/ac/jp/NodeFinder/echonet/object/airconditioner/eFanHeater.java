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
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eFanHeater extends eDataObject{
	private static final Logger logger = Logger.getLogger(eFanHeater.class.getName());
	private Timer timer;
	private int settingTemperature;
	
	

	public eFanHeater(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x01);
		this.setClassCode((byte) 0x43);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.FanHeater);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshSettingTemperature(int val) {
		if(this.getSettingTemperature() != val) {
			logger.info(String.format("SettingTemperature changed from %d to %d",getSettingTemperature(),val));
			setSettingTemperature(val);
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
		epcs.add(EPC.xB3);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xB3:
							refreshSettingTemperature(EchonetDataConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={SettingTemperature:%d Celcius Degree}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSettingTemperature()));
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

	public int getSettingTemperature() {
		return settingTemperature;
	}

	public void setSettingTemperature(int settingTemperature) {
		this.settingTemperature = settingTemperature;
	}
}
