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
import echowand.service.result.ObserveListener;
import echowand.service.result.ObserveResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.OpenCloseStatus;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.OpenCloseStop;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.SelectiveOperation;

public class eElectricGate extends eDataObject{
	private static final Logger logger = Logger.getLogger(eElectricGate.class.getName());
	private Timer timer;
	private OpenCloseStop openCloseSetting;
	private boolean remoteOperatingEnable;
	private SelectiveOperation selectiveOpeningOperationSetting;
	private OpenCloseStatus openCloseStatus;
	
	public eElectricGate(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x64);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.ElectricGate);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshOpenCloseStatus(OpenCloseStatus val) {
		if(val != getOpenCloseStatus()) {
			logger.info(String.format("OpenCloseStatus changed from %s to %s",getOpenCloseStatus(),val));
			setOpenCloseStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshSelectiveOpeningOperationSetting(SelectiveOperation val) {
		if(val != getSelectiveOpeningOperationSetting()) {
			logger.info(String.format("SelectiveOpeningOperationSetting changed from %s to %s",getSelectiveOpeningOperationSetting(),val));
			setSelectiveOpeningOperationSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshRemoteOperatingEnable(boolean val) {
		if(val != isRemoteOperatingEnable()) {
			logger.info(String.format("RemoteOperatingEnable changed from %s to %s",isRemoteOperatingEnable(),val));
			setRemoteOperatingEnable(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshOpenCloseSetting(OpenCloseStop val) {
		if(val != getOpenCloseSetting()) {
			logger.info(String.format("OpenCloseSetting changed from %s to %s",getOpenCloseSetting(),val));
			setOpenCloseSetting(val);
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
		epcs.add(EPC.xE8);
		epcs.add(EPC.xE9);
		epcs.add(EPC.xEA);
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
							refreshOpenCloseSetting(EchonetDataConverter.OpenCloseStop.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={OpenCloseSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpenCloseSetting()));
						break;
						case xE8:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshRemoteOperatingEnable(true);
							} else {
								refreshRemoteOperatingEnable(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE8, EDT: 0x%02X}=={RemoteOperatingEnable:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isRemoteOperatingEnable()));
						break;
						case xE9:
							refreshSelectiveOpeningOperationSetting(EchonetDataConverter.SelectiveOperation.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE9, EDT: 0x%02X}=={SelectiveOpeningOperationSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSelectiveOpeningOperationSetting()));
						break;
						case xEA:
							refreshOpenCloseStatus(EchonetDataConverter.OpenCloseStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xEA, EDT: 0x%02X}=={OpenCloseStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpenCloseStatus()));
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
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE8);
		epcs.add(EPC.xE9);
		epcs.add(EPC.xEA);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xE0:
							refreshOpenCloseSetting(EchonetDataConverter.OpenCloseStop.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={OpenCloseSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpenCloseSetting()));
						break;
						case xE8:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshRemoteOperatingEnable(true);
							} else {
								refreshRemoteOperatingEnable(false);
							}
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE8, EDT: 0x%02X}=={RemoteOperatingEnable:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isRemoteOperatingEnable()));
						break;
						case xE9:
							refreshSelectiveOpeningOperationSetting(EchonetDataConverter.SelectiveOperation.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE9, EDT: 0x%02X}=={SelectiveOpeningOperationSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSelectiveOpeningOperationSetting()));
						break;
						case xEA:
							refreshOpenCloseStatus(EchonetDataConverter.OpenCloseStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xEA, EDT: 0x%02X}=={OpenCloseStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOpenCloseStatus()));
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


	public OpenCloseStop getOpenCloseSetting() {
		return openCloseSetting;
	}

	public void setOpenCloseSetting(OpenCloseStop openCloseSetting) {
		this.openCloseSetting = openCloseSetting;
	}

	public boolean isRemoteOperatingEnable() {
		return remoteOperatingEnable;
	}

	public void setRemoteOperatingEnable(boolean remoteOperatingEnable) {
		this.remoteOperatingEnable = remoteOperatingEnable;
	}

	public SelectiveOperation getSelectiveOpeningOperationSetting() {
		return selectiveOpeningOperationSetting;
	}

	public void setSelectiveOpeningOperationSetting(SelectiveOperation selectiveOpeningOperationSetting) {
		this.selectiveOpeningOperationSetting = selectiveOpeningOperationSetting;
	}

	public OpenCloseStatus getOpenCloseStatus() {
		return openCloseStatus;
	}

	public void setOpenCloseStatus(OpenCloseStatus openCloseStatus) {
		this.openCloseStatus = openCloseStatus;
	}
	
	
}