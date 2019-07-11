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
import jaist.ac.jp.NodeFinder.echonet.object.groupCode06.eAudio;
import jaist.ac.jp.NodeFinder.echonet.object.groupCode06.eDisplay;
import jaist.ac.jp.NodeFinder.echonet.object.groupCode06.eNetworkCamera;
import jaist.ac.jp.NodeFinder.echonet.object.groupCode06.eTelevision;

public class GroupCode06Mapper {
	private static final Logger logger = Logger.getLogger(GroupCode04Mapper.class.getName());
	
	public static eDataObject createDisplay(Node node, EOJ eoj) {
		logger.info("Creating Display from ECHONET Lite frame...");
		return new eDisplay(node, eoj);
	}
	public static eDataObject createTelevision(Node node, EOJ eoj) {
		logger.info("Creating Television from ECHONET Lite frame...");
		return new eTelevision(node, eoj);
	}
	public static eDataObject createAudio(Node node, EOJ eoj) {
		logger.info("Creating Audio from ECHONET Lite frame...");
		return new eAudio(node, eoj);
	}
	public static eDataObject createNetworkCamera(Node node, EOJ eoj) {
		logger.info("Creating NetworkCamera from ECHONET Lite frame...");
		return new eNetworkCamera(node, eoj);
	}

}
