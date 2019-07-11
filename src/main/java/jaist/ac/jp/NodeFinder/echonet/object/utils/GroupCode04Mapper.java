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
import jaist.ac.jp.NodeFinder.echonet.object.groupCode04.eWeighingMachine;

public class GroupCode04Mapper {
	private static final Logger logger = Logger.getLogger(GroupCode04Mapper.class.getName());
	
	public static eDataObject createWeighingMachine(Node node, EOJ eoj) {
		logger.info("Creating WeighingMachine from ECHONET Lite frame...");
		return new eWeighingMachine(node, eoj);
	}
	public static eDataObject createClinicalThermoMeter(Node node, EOJ eoj) {
		logger.warning("ClinicalThermoMeter specification has not been released");
		return null;
	}
	public static eDataObject createBloodPressuremeter(Node node, EOJ eoj) {
		logger.warning("BloodPressuremeter specification has not been released");
		return null;
	}
	public static eDataObject createBloodSugarmeter(Node node, EOJ eoj) {
		logger.warning("BloodSugarmeter specification has not been released");
		return null;
	}
	public static eDataObject createBodyFatmeter(Node node, EOJ eoj) {
		logger.warning("BodyFatmeter specification has not been released");
		return null;
	}


}
