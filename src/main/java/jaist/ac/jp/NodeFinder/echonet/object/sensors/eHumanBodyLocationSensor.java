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
import echowand.service.Service;
import echowand.service.result.GetListener;
import echowand.service.result.GetResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eHumanBodyLocationSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eHumanBodyLocationSensor.class.getName());
	private Timer timer;
	private String humanBodyDetectionLocation1;
	private String humanBodyDetectionLocation2;
	private int maxNumberofHumanBodyID;
	private byte humanBodyExistenceInfor;

	public eHumanBodyLocationSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x2B);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.HumanBodyLocationSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshHumanBodyDetectionLocation1(String level) {
		if(!level.equals(getHumanBodyDetectionLocation1())) {
			logger.info(String.format("HumanBodyDetectionLocation1 changed from %s to %s",getHumanBodyDetectionLocation1(),level));
			setHumanBodyDetectionLocation1(level);
			//TODO: More task can be added here
		}
	}
	public void	refreshHumanBodyDetectionLocation2(String level) {
		if(!level.equals(getHumanBodyDetectionLocation2())) {
			logger.info(String.format("HumanBodyDetectionLocation2 changed from %s to %s",getHumanBodyDetectionLocation2(),level));
			setHumanBodyDetectionLocation2(level);
			//TODO: More task can be added here
		}
	}
	public void	refreshMaxNumberofHumanBodyID(int val) {
		if(val != this.getMaxNumberofHumanBodyID()) {
			logger.info(String.format("MaxNumberofHumanBodyID changed from %s to %s",getMaxNumberofHumanBodyID(),val));
			setMaxNumberofHumanBodyID(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshHumanBodyExistenceInfor(byte val) {
		if(val !=getHumanBodyExistenceInfor()) {
			logger.info(String.format("HumanBodyExistenceInfor changed from %s to %s",getHumanBodyExistenceInfor(),val));
			setHumanBodyExistenceInfor(val);
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
		epcs.add(EPC.xE1);
		epcs.add(EPC.xE2);
		epcs.add(EPC.xE3);
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
							refreshHumanBodyDetectionLocation1(EchonetDataConverter.dataToCoordinator(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={HumanBodyDetectionLocation1:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getHumanBodyDetectionLocation1()));
						break;
						case xE1:
							refreshMaxNumberofHumanBodyID(EchonetDataConverter.dataToShort(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={hMaxNumberofHumanBodyID:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMaxNumberofHumanBodyID()));
						break;
						case xE2:
							refreshHumanBodyDetectionLocation2(EchonetDataConverter.dataToCoordinator(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE2, EDT: 0x%02X}=={HumanBodyDetectionLocation2:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getHumanBodyDetectionLocation2()));
						break;
						case xE3:
							refreshHumanBodyExistenceInfor(EchonetDataConverter.dataToByte(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE3, EDT: 0x%02X}=={HumanBodyExistenceInfor:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getHumanBodyExistenceInfor()));
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

	public String getHumanBodyDetectionLocation1() {
		return humanBodyDetectionLocation1;
	}

	public void setHumanBodyDetectionLocation1(String humanBodyDetectionLocation1) {
		this.humanBodyDetectionLocation1 = humanBodyDetectionLocation1;
	}

	public String getHumanBodyDetectionLocation2() {
		return humanBodyDetectionLocation2;
	}

	public void setHumanBodyDetectionLocation2(String humanBodyDetectionLocation2) {
		this.humanBodyDetectionLocation2 = humanBodyDetectionLocation2;
	}

	public int getMaxNumberofHumanBodyID() {
		return maxNumberofHumanBodyID;
	}

	public void setMaxNumberofHumanBodyID(int maxNumberofHumanBodyID) {
		this.maxNumberofHumanBodyID = maxNumberofHumanBodyID;
	}

	public byte getHumanBodyExistenceInfor() {
		return humanBodyExistenceInfor;
	}

	public void setHumanBodyExistenceInfor(byte humanBodyExistenceInfor) {
		this.humanBodyExistenceInfor = humanBodyExistenceInfor;
	}

}
