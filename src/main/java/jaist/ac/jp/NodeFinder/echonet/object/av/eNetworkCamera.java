package jaist.ac.jp.NodeFinder.echonet.object.av;



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
import echowand.service.result.ObserveListener;
import echowand.service.result.ObserveResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.App;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eNetworkCamera extends eDataObject{
	private static final Logger logger = Logger.getLogger(eNetworkCamera.class.getName());
	private Timer timer;
	private boolean imageSettingAcceptanceStatus;
	private String transferSetting;

	
	

	public eNetworkCamera(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x06);
		this.setClassCode((byte) 0x04);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.NetworkCamera);
	}
	
	// Provided Services	
	public boolean enableImageSetting() {
		boolean rs = false;
		if(App.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xC1, new ObjectData((byte)0x30))) {
			rs= true;
		} else {
			rs = false;
		}
		return rs;
	}
		
	// Device Property Monitoring
	public void	refreshImageSettingAcceptanceStatus(boolean val) {
		if(val != isImageSettingAcceptanceStatus()) {
			logger.info(String.format("ImageSettingAcceptanceStatus changed from %s to %s",isImageSettingAcceptanceStatus()?"Ready":"Busy",val?"Ready":"Busy"));
			setImageSettingAcceptanceStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshTransferSetting(String val) {
		if(!val.equals(this.getTransferSetting())) {
			logger.info(String.format("TransferSetting changed from %s to %s",getTransferSetting(),val));
			setTransferSetting(val);
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
		epcs.add(EPC.xD0);
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
							if(EchonetDataConverter.dataToInteger(resultData) == 48) {
								refreshImageSettingAcceptanceStatus(true);
							} else {
								refreshImageSettingAcceptanceStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={ImageSettingAcceptanceStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isImageSettingAcceptanceStatus()?"Ready":"Busy"));
						break;
						case xD0:
							refreshTransferSetting(EchonetDataConverter.dataTo4BitMapcode(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB2, EDT: 0x%02X}=={hTransferSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getTransferSetting()));
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
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xD0);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xD0:
						refreshTransferSetting(EchonetDataConverter.dataTo4BitMapcode(resultData));
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB2, EDT: 0x%02X}=={hTransferSetting:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getTransferSetting()));
					break;
					default:
						break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE,ex.toString());
			
		}
		
	}

	public boolean isImageSettingAcceptanceStatus() {
		return imageSettingAcceptanceStatus;
	}

	public void setImageSettingAcceptanceStatus(boolean imageSettingAcceptanceStatus) {
		this.imageSettingAcceptanceStatus = imageSettingAcceptanceStatus;
	}

	public String getTransferSetting() {
		return transferSetting;
	}

	public void setTransferSetting(String transferSetting) {
		this.transferSetting = transferSetting;
	}
}
