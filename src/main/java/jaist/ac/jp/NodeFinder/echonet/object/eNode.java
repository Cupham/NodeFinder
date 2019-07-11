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

import java.util.ArrayList;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import echowand.object.EchonetObjectException;
import echowand.service.Service;
import jaist.ac.jp.NodeFinder.echonet.object.utils.CodeMapper;

public class eNode {
	private static final Logger logger = Logger.getLogger(eNode.class.getName());
	
	private eNodeProfile profileObj;
	private Node node;
	private ArrayList<eDataObject> dataObjList;
	public eNode() {
		this.dataObjList = new ArrayList<eDataObject>();
		this.profileObj = null;
		this.node = null;
	}
	public eNode(Node node) {
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
		eDataObject dataObj = CodeMapper.dataObjectFromCode(node,eoj);		
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
	
	public eNodeProfile getProfileObj() {
		return profileObj;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public void setProfileObj(eNodeProfile profileObj) {
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
