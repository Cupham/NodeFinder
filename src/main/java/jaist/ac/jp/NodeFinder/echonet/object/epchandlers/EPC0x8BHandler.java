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
package jaist.ac.jp.NodeFinder.echonet.object.epchandlers;

import java.util.logging.Logger;

import jaist.ac.jp.NodeFinder.echonet.object.eSuperObject;
import jaist.ac.jp.NodeFinder.echonet.object.eTrigger;

public class EPC0x8BHandler {
	private static final Logger logger = Logger.getLogger(EPC0x8BHandler.class.getName());
	public static void refreshBusinessFacilityCode(Object obj, String newVal) {
		if(obj instanceof eSuperObject) {
			eSuperObject dev = (eSuperObject) obj;
			if(!newVal.equals(dev.getBusinessFacilityCode())) {
				dev.setBusinessFacilityCode(newVal);
				eTrigger.businessFacilityCodeChanged(dev);
			}
		}else {
			logger.severe(String.format("=============INVALID OBJECT DETECTED=============\n "
					+ "					 =============%s =============", obj.toString()));
		}
	}

}
