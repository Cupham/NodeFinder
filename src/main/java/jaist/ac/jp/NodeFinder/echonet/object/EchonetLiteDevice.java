package jaist.ac.jp.NodeFinder.echonet.object;

import java.util.ArrayList;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import echowand.object.EchonetObjectException;
import echowand.service.Service;
import jaist.ac.jp.NodeFinder.echonet.objectmapper.CodeMapper;

public class EchonetLiteDevice {
	private static final Logger logger = Logger.getLogger(EchonetLiteDevice.class.getName());
	
	private NodeProfileObject profileObj;
	private Node node;
	private ArrayList<eDataObject> dataObjList;
	public EchonetLiteDevice() {
		this.dataObjList = new ArrayList<eDataObject>();
		this.profileObj = null;
		this.node = null;
	}
	public EchonetLiteDevice(Node node) {
		this.dataObjList = new ArrayList<eDataObject>();
		this.profileObj = null;
		this.node = node;
	}
	
	public boolean addDataObject(eDataObject dataObj) throws EchonetObjectException {
		if(this.dataObjList == null) {
			this.dataObjList = new ArrayList<eDataObject>();
		}
		this.dataObjList.add(dataObj);
		return true;
	}
	public boolean parseDataObject(EOJ eoj, Node node, Service service) throws EchonetObjectException{
		boolean rs = false;
		byte classGroupCode = eoj.getClassGroupCode();
		byte classCode = eoj.getClassCode();
		logger.info("Mapping GroupCode:" + classGroupCode +" and ClassCode:" + classCode +" to device name" );
		eDataObject dataObj = CodeMapper.dataObjectFromCode(classGroupCode, classCode,node,eoj);		
		if(dataObj != null) {
			dataObj.dataFromEOJ(service);
			//dataObj.profileObjectFromEPC(service);
			this.addDataObject(dataObj);
			rs = true;
		} else {
			logger.info("This device is not supported");
		}
		return rs;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder rs = new StringBuilder();
		rs.append("\r\n*********************************************");
		rs.append("\r\n>Node IP: " + this.node.getNodeInfo().toString());
		rs.append("\r\n>Node Profile Object: \r\n");
		rs.append(this.profileObj.toString());
		rs.append("\r\n>Data Object: "+dataObjList.size()+" devices\r\n");

		for (eDataObject dataObj : dataObjList) {
			rs.append("\r\n\t####################\r\n");
			rs.append("\t"+ dataObj.toString()+"\r\n");
			rs.append("\t####################\r\n");
		}
		rs.append("*********************************************\r\n");
		return rs.toString();
	}
	// getter setter
	
	public NodeProfileObject getProfileObj() {
		return profileObj;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public void setProfileObj(NodeProfileObject profileObj) {
		if(!profileObj.equals(this.profileObj)) {
			this.profileObj = profileObj;
		}
		
	}
	public ArrayList<eDataObject> getDataObjList() {
		return dataObjList;
	}
	public void setDataObjList(ArrayList<eDataObject> dataObjList) {
		this.dataObjList = dataObjList;
	}
	public void addDataObjList(eDataObject dataObj) {
		ArrayList<eDataObject> objList = new ArrayList<eDataObject>();
		objList.add(dataObj);
		this.dataObjList = objList;
	}
}
