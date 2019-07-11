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

import echowand.common.EOJ;
import echowand.net.Node;
import echowand.object.EchonetObjectException;
import echowand.service.Service;

/**
 * @author Cu Pham
 *
 */
public abstract class eDataObject extends eSuperObject{
	public eDataObject() {
		super();
	}
	public eDataObject(Node node, EOJ eoj) {
		super(node,eoj);
	}

	/**
	 * TODO: get data from EPC
	 * @param rObj
	 * @throws EchonetObjectException 
	 */
	public abstract void dataFromEOJ(Service service);
	
	/**
	 * TODO: observe data from EPC
	 * @param rObj
	 * @throws EchonetObjectException 
	 */
	public abstract void observeSpecificData(Service service);
	/**
	 * TODO: parse this data to string
	 * @return
	 */
//	public abstract String TouAALReponse();
	
}
