package jaist.ac.jp.NodeFinder.echonet.object.housing;




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

public class eBidetEquippedToilet extends eDataObject{
	private static final Logger logger = Logger.getLogger(eBidetEquippedToilet.class.getName());
	private Timer timer;
	private boolean toiletSeatHeaterSetting;
	
	public eBidetEquippedToilet(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x6E);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.BidetEquippedToilet);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshToiletSeatHeaterSetting(boolean val) {
		if(val != isToiletSeatHeaterSetting()) {
			logger.info(String.format("ToiletSeatHeaterSetting changed from %s to %s",isToiletSeatHeaterSetting(),val));
			setToiletSeatHeaterSetting(val);
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
		epcs.add(EPC.xE1);

		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xE1:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshToiletSeatHeaterSetting(true);
							} else {
								refreshToiletSeatHeaterSetting(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={ToiletSeatHeaterSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isToiletSeatHeaterSetting()));
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

	public boolean isToiletSeatHeaterSetting() {
		return toiletSeatHeaterSetting;
	}

	public void setToiletSeatHeaterSetting(boolean toiletSeatHeaterSetting) {
		this.toiletSeatHeaterSetting = toiletSeatHeaterSetting;
	}

	
	
}
