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
package jaist.ac.jp.NodeFinder.echonet.object.groupCode02;




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
import jaist.ac.jp.NodeFinder.echonet.object.utils.eDeviceType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter;
import jaist.ac.jp.NodeFinder.echonet.object.utils.SampleConstants;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.DryerOperation;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.OperationSetting;

public class eBathRoomDryer extends eDataObject{
	private static final Logger logger = Logger.getLogger(eBathRoomDryer.class.getName());
	private Timer timer;
	private DryerOperation operationSetting;
	private OperationSetting bathroomDryerOperationSetting;
	
	public eBathRoomDryer(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x73);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.BathRoomDryer);
	}
	
	// Provided Services	
	// Device Property Monitoring

	public void	refreshOperationSetting(DryerOperation val) {
		if(val != getOperationSetting()) {
			logger.info(String.format("OperationSetting changed from %s to %s",getOperationSetting(),val));
			setOperationSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshBathroomDryerOperationSetting(OperationSetting val) {
		if(val != getBathroomDryerOperationSetting()) {
			logger.info(String.format("OperationSetting changed from %s to %s",getBathroomDryerOperationSetting(),val));
			setBathroomDryerOperationSetting(val);
			//TODO: More task can be added here
		}
	}
	
	// Override functions
	@Override
	public void dataFromEOJ(Service s){
		final Service service = s;
		final Node node = this.getNode();
		final EOJ eoj = this.getEoj();
		if(observeEnabled) {
			observeSuperData(service, node, eoj);
			observeSpecificData(service);
		}
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
		epcs.add(EPC.xB0);
		epcs.add(EPC.xB4);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						
						case xB0:
							refreshOperationSetting(eConverter.DryerOperation.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={OperationSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOperationSetting()));
						break;
						case xB4:
							refreshBathroomDryerOperationSetting(eConverter.OperationSetting.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB4, EDT: 0x%02X}=={OperationSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getBathroomDryerOperationSetting()));
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

	public DryerOperation getOperationSetting() {
		return operationSetting;
	}

	public void setOperationSetting(DryerOperation operationSetting) {
		this.operationSetting = operationSetting;
	}

	public OperationSetting getBathroomDryerOperationSetting() {
		return bathroomDryerOperationSetting;
	}

	public void setBathroomDryerOperationSetting(OperationSetting bathroomDryerOperationSetting) {
		this.bathroomDryerOperationSetting = bathroomDryerOperationSetting;
	}

}
