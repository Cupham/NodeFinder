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
package jaist.ac.jp.NodeFinder.echonet.object.utils;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.groupCode05.eController;
import jaist.ac.jp.NodeFinder.echonet.object.groupCode05.eDREventController;
import jaist.ac.jp.NodeFinder.echonet.object.groupCode05.eParallelProcessingCombinationTypePowerControl;
import jaist.ac.jp.NodeFinder.echonet.object.groupCode05.eSwitch;

public class GroupCode05Mapper {
	private static final Logger logger = Logger.getLogger(GroupCode04Mapper.class.getName());
	
	public static eDataObject createParallelProcessingCombinationTypePowerControl(Node node, EOJ eoj) {
		logger.info("Creating ParallelProcessingCombinationTypePowerControl from ECHONET Lite frame...");
		return new eParallelProcessingCombinationTypePowerControl(node, eoj);
	}
	public static eDataObject createDREventController(Node node, EOJ eoj) {
		logger.info("Creating DREventController from ECHONET Lite frame...");
		return new eDREventController(node, eoj);
	}
	public static eDataObject createSecureCommunicationSharedKeySetupNode(Node node, EOJ eoj) {
		logger.warning("SecureCommunicationSharedKeySetupNode specification has not been released");
		return null;
	}
	public static eDataObject createSwitch(Node node, EOJ eoj) {
		logger.info("Creating Switch from ECHONET Lite frame...");
		return new eSwitch(node, eoj);
	}
	public static eDataObject createPortableTerminal(Node node, EOJ eoj) {
		logger.warning("PortableTerminal specification has not been released");
		return null;
	}
	public static eDataObject createController(Node node, EOJ eoj) {
		logger.info("Creating Controller from ECHONET Lite frame...");
		return new eController(node, eoj);
	}
}
