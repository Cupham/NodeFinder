package jaist.ac.jp.NodeFinder.echonet.object.management;



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
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter.DRProgramType;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eDREventController extends eDataObject{
	private static final Logger logger = Logger.getLogger(eDREventController.class.getName());
	private Timer timer;
	private String businessID;
	private DRProgramType drProgramType;
	private String programID;
	private String currentValidEventInfor;
	private String nextValidEventInfor;
	private String futureEventInforNotificationIDList;
	private String pastEventInforNotificationIDList;
	private String newestReceivedEventNotificationID;
	private String oldestReceivedEventNotificationID;
	private String notificationIDDesignation;
	private String eventInfor;
	
	
	public eDREventController(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x05);
		this.setClassCode((byte) 0xFB);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.DREventController);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshEventInfor(String val) {
		if(val.equals(this.getEventInfor())) {
			logger.info(String.format("EventInfor changed from %s to %s",getEventInfor(),val));
			setEventInfor(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshNotificationIDDesignation(String val) {
		if(val.equals(this.getNotificationIDDesignation())) {
			logger.info(String.format("NotificationIDDesignation changed from %s to %s",getNotificationIDDesignation(),val));
			setNotificationIDDesignation(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshOldestReceivedEventNotificationID(String val) {
		if(val.equals(this.getOldestReceivedEventNotificationID())) {
			logger.info(String.format("OldestReceivedEventNotificationID changed from %s to %s",getOldestReceivedEventNotificationID(),val));
			setOldestReceivedEventNotificationID(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshNewestReceivedEventNotificationID(String val) {
		if(val.equals(this.getNewestReceivedEventNotificationID())) {
			logger.info(String.format("NewestReceivedEventNotificationID changed from %s to %s",getNewestReceivedEventNotificationID(),val));
			setNewestReceivedEventNotificationID(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshPastEventInforNotificationIDList(String val) {
		if(val.equals(this.getPastEventInforNotificationIDList())) {
			logger.info(String.format("PastEventInforNotificationIDList changed from %s to %s",getPastEventInforNotificationIDList(),val));
			setPastEventInforNotificationIDList(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshFutureEventInforNotificationIDList(String val) {
		if(val.equals(this.getFutureEventInforNotificationIDList())) {
			logger.info(String.format("FutureEventInforNotificationIDList changed from %s to %s",getFutureEventInforNotificationIDList(),val));
			setFutureEventInforNotificationIDList(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshNextValidEventInfor(String val) {
		if(val.equals(this.getNextValidEventInfor())) {
			logger.info(String.format("NextValidEventInfor changed from %s to %s",getNextValidEventInfor(),val));
			setNextValidEventInfor(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshCurrentValidEventInfor(String val) {
		if(val.equals(this.getCurrentValidEventInfor())) {
			logger.info(String.format("CurrentValidEventInfor changed from %s to %s",getCurrentValidEventInfor(),val));
			setCurrentValidEventInfor(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshProgramID(String val) {
		if(val.equals(this.getProgramID())) {
			logger.info(String.format("ProgramID changed from %s to %s",getProgramID(),val));
			setProgramID(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshDRProgramType(DRProgramType val) {
		if(val.equals(this.getDrProgramType())) {
			logger.info(String.format("DRProgramType changed from %s to %s",getDrProgramType(),val));
			setDrProgramType(val);
			//TODO: More task can be added here
		}
	}
	
	public void	refreshBusinessID(String val) {
		if(val.equals(this.getBusinessID())) {
			logger.info(String.format("BusinessID changed from %s to %s",getBusinessID(),val));
			setBusinessID(val);
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
		epcs.add(EPC.xD0);
		epcs.add(EPC.xD1);
		epcs.add(EPC.xD2);
		epcs.add(EPC.xD3);
		epcs.add(EPC.xD4);
		epcs.add(EPC.xD5);
		epcs.add(EPC.xD6);
		epcs.add(EPC.xD7);
		epcs.add(EPC.xD8);
		epcs.add(EPC.xD9);
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
						case xD0:
							refreshBusinessID(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD0, EDT: 0x%02X}=={BusinessID:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getBusinessID()));
						break;
						case xD1:
							refreshDRProgramType(EchonetDataConverter.DRProgramType.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD1, EDT: 0x%02X}=={DRProgramType:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getDrProgramType()));
						break;
						case xD2:
							refreshProgramID(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD2, EDT: 0x%02X}=={ProgramID:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getProgramID()));
						break;
						case xD3:
							refreshCurrentValidEventInfor(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD3, EDT: 0x%02X}=={CurrentValidEventInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getCurrentValidEventInfor()));
						break;
						case xD4:
							refreshNextValidEventInfor(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD4, EDT: 0x%02X}=={NextValidEventInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getNextValidEventInfor()));
						break;
						case xD5:
							refreshFutureEventInforNotificationIDList(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD5, EDT: 0x%02X}=={FutureEventInforNotificationIDList:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getFutureEventInforNotificationIDList()));
						break;
						case xD6:
							refreshPastEventInforNotificationIDList(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD6, EDT: 0x%02X}=={PastEventInforNotificationIDList:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getPastEventInforNotificationIDList()));
						break;
						case xD7:
							refreshNewestReceivedEventNotificationID(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD7, EDT: 0x%02X}=={NewestReceivedEventNotificationID:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getNewestReceivedEventNotificationID()));
						break;
						case xD8:
							refreshOldestReceivedEventNotificationID(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD8, EDT: 0x%02X}=={OldestReceivedEventNotificationID:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOldestReceivedEventNotificationID()));
						break;
						case xD9:
							refreshNotificationIDDesignation(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD2, EDT: 0x%02X}=={NotificationIDDesignation:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getNotificationIDDesignation()));
						break;
						case xE0:
							refreshEventInfor(EchonetDataConverter.dataToString(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD2, EDT: 0x%02X}=={EventInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getEventInfor()));
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

	public String getBusinessID() {
		return businessID;
	}

	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}

	public DRProgramType getDrProgramType() {
		return drProgramType;
	}

	public void setDrProgramType(DRProgramType drProgramType) {
		this.drProgramType = drProgramType;
	}

	public String getProgramID() {
		return programID;
	}

	public void setProgramID(String programID) {
		this.programID = programID;
	}

	public String getCurrentValidEventInfor() {
		return currentValidEventInfor;
	}

	public void setCurrentValidEventInfor(String currentValidEventInfor) {
		this.currentValidEventInfor = currentValidEventInfor;
	}

	public String getNextValidEventInfor() {
		return nextValidEventInfor;
	}

	public void setNextValidEventInfor(String nextValidEventInfor) {
		this.nextValidEventInfor = nextValidEventInfor;
	}

	public String getFutureEventInforNotificationIDList() {
		return futureEventInforNotificationIDList;
	}

	public void setFutureEventInforNotificationIDList(String futureEventInforNotificationIDList) {
		this.futureEventInforNotificationIDList = futureEventInforNotificationIDList;
	}

	public String getPastEventInforNotificationIDList() {
		return pastEventInforNotificationIDList;
	}

	public void setPastEventInforNotificationIDList(String pastEventInforNotificationIDList) {
		this.pastEventInforNotificationIDList = pastEventInforNotificationIDList;
	}

	public String getNewestReceivedEventNotificationID() {
		return newestReceivedEventNotificationID;
	}

	public void setNewestReceivedEventNotificationID(String newestReceivedEventNotificationID) {
		this.newestReceivedEventNotificationID = newestReceivedEventNotificationID;
	}

	public String getOldestReceivedEventNotificationID() {
		return oldestReceivedEventNotificationID;
	}

	public void setOldestReceivedEventNotificationID(String oldestReceivedEventNotificationID) {
		this.oldestReceivedEventNotificationID = oldestReceivedEventNotificationID;
	}

	public String getNotificationIDDesignation() {
		return notificationIDDesignation;
	}

	public void setNotificationIDDesignation(String notificationIDDesignation) {
		this.notificationIDDesignation = notificationIDDesignation;
	}

	public String getEventInfor() {
		return eventInfor;
	}

	public void setEventInfor(String eventInfor) {
		this.eventInfor = eventInfor;
	}

	@Override
	public void observeSpecificData(Service service) {
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xD7);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xD7:
						refreshNewestReceivedEventNotificationID(EchonetDataConverter.dataToString(resultData));
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xD7, EDT: 0x%02X}=={NewestReceivedEventNotificationID:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getNewestReceivedEventNotificationID()));
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

}
