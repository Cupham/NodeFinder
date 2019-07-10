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

public class eHotWaterPot extends eDataObject{
	private static final Logger logger = Logger.getLogger(eHotWaterPot.class.getName());
	private Timer timer;
	private boolean noWaterWarningStatus;
	private boolean hotWaterDischargeStatus;
	
	

	public eHotWaterPot(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x03);
		this.setClassCode((byte) 0xB2);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.HotWaterPot);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshNoWaterWarningStatus(boolean val) {
		if(val != isNoWaterWarningStatus()) {
			logger.info(String.format("NoWaterWarningStatus changed from %s to %s",isNoWaterWarningStatus(),val));
			setNoWaterWarningStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshHotWaterDischargeStatus(boolean val) {
		if(val != isHotWaterDischargeStatus()) {
			logger.info(String.format("HotWaterDischargeStatus changed from %s to %s",isHotWaterDischargeStatus(),val));
			setHotWaterDischargeStatus(val);
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
		epcs.add(EPC.xE2);
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
								refreshNoWaterWarningStatus(true);
							} else {
								refreshNoWaterWarningStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={NoWaterWarningStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isNoWaterWarningStatus()));
						break;
						case xE2:
							if(EchonetDataConverter.dataToInteger(resultData) == 65) {
								refreshHotWaterDischargeStatus(true);
							} else {
								refreshHotWaterDischargeStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE2, EDT: 0x%02X}=={HotWaterDischargeStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isHotWaterDischargeStatus()));
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
		epcs.add(EPC.xB1);
		epcs.add(EPC.xE2);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xB1:
						if(EchonetDataConverter.dataToInteger(resultData) == 65) {
							refreshNoWaterWarningStatus(true);
						} else {
							refreshNoWaterWarningStatus(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={NoWaterWarningStatus:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isNoWaterWarningStatus()));
					break;
					case xE2:
						if(EchonetDataConverter.dataToInteger(resultData) == 65) {
							refreshHotWaterDischargeStatus(true);
						} else {
							refreshHotWaterDischargeStatus(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE2, EDT: 0x%02X}=={HotWaterDischargeStatus:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isHotWaterDischargeStatus()));
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

	public boolean isNoWaterWarningStatus() {
		return noWaterWarningStatus;
	}

	public void setNoWaterWarningStatus(boolean noWaterWarningStatus) {
		this.noWaterWarningStatus = noWaterWarningStatus;
	}

	public boolean isHotWaterDischargeStatus() {
		return hotWaterDischargeStatus;
	}

	public void setHotWaterDischargeStatus(boolean hotWaterDischargeStatus) {
		this.hotWaterDischargeStatus = hotWaterDischargeStatus;
	}

}
