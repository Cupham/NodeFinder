/*******************************************************************************
 * Copyright 2019 Cu Pham
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package jaist.ac.jp.NodeFinder.echonet.object;
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
