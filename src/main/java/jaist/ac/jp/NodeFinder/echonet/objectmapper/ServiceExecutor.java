package jaist.ac.jp.NodeFinder.echonet.objectmapper;
import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.common.EPC;
import echowand.net.Node;
import echowand.object.EchonetObjectException;
import echowand.object.ObjectData;
import echowand.object.RemoteObject;
import echowand.service.Service;

public class ServiceExecutor {
	Logger LOGGER = Logger.getLogger(ServiceExecutor.class.getName());
	private Service service;
	public void setEchonetLiteService(Service service) {
		this.service = service;
		
	}
	public ServiceExecutor() {
		
	}
	public ServiceExecutor(Service service) {
		this.service = service;
		
	}
	public boolean executeCommand(Node node, EOJ eoj, EPC epc, ObjectData data) {
		boolean rs = false;
		service.registerRemoteEOJ(node, eoj);
		RemoteObject remoteObject = service.getRemoteObject(node, eoj);
		System.out.println(String.format("Execute command [IP:%s, EOJ:%s, Data:%s]",node.getNodeInfo().toString(),eoj,data));
		LOGGER.info(String.format("Execute command [IP:%s, EOJ:%s, Data:%s]",node.getNodeInfo().toString(),eoj,data));
		try {
			if (remoteObject.setData(epc, data)) {
				rs= true;
				System.out.println(String.format("Completed: [IP:%s, EOJ:%s, Data:%s]",node.getNodeInfo().toString(),eoj,data));
				LOGGER.info(String.format("Completed: [IP:%s, EOJ:%s, Data:%s]",node.getNodeInfo().toString(),eoj,data));
			}
		} catch (EchonetObjectException e) {
			System.out.println("Can not find object: " +e.toString());
			LOGGER.log(Level.SEVERE, "Can not find object: " +e.toString());
			rs= false;
		}
		return rs;
	
	}

}
