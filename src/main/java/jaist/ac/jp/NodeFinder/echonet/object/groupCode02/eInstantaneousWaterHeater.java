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
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.BathOperationStatus;

public class eInstantaneousWaterHeater extends eDataObject{
	private static final Logger logger = Logger.getLogger(eInstantaneousWaterHeater.class.getName());
	private Timer timer;
	private boolean hotWaterHeating;
	private boolean bathWaterHeating;
	private boolean bathAutoModeSetting;
	private BathOperationStatus bathOperationStatus;
	
	public eInstantaneousWaterHeater(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x72);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.InstantaneousWaterHeater);
	}
	
	// Provided Services	
	// Device Property Monitoring

	public void	refreshBathOperationStatus(BathOperationStatus val) {
		if(val != getBathOperationStatus()) {
			logger.info(String.format("AlarmStatus changed from %s to %s",getBathOperationStatus(),val));
			setBathOperationStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshBathAutoModeSetting(boolean val) {
		if(val != isBathAutoModeSetting()) {
			logger.info(String.format("BathAutoModeSetting changed from %s to %s",isBathAutoModeSetting(),val));
			setBathAutoModeSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshBathWaterHeating(boolean val) {
		if(val != isBathWaterHeating()) {
			logger.info(String.format("BathWaterHeating changed from %s to %s",isBathWaterHeating(),val));
			setBathWaterHeating(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshHotWaterHeating(boolean val) {
		if(val != isHotWaterHeating()) {
			logger.info(String.format("HotWaterHeating changed from %s to %s",isHotWaterHeating(),val));
			setHotWaterHeating(val);
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
		epcs.add(EPC.xD0);
		epcs.add(EPC.xE2);
		epcs.add(EPC.xE3);
		epcs.add(EPC.xEF);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xD0:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshHotWaterHeating(true);
							} else {
								refreshHotWaterHeating(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD0, EDT: 0x%02X}=={HotWaterHeating:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isHotWaterHeating()));
						break;
						case xE2:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshBathWaterHeating(true);
							} else {
								refreshBathWaterHeating(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE2, EDT: 0x%02X}=={BathWaterHeating:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isBathWaterHeating()));
						break;
						case xE3:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshBathAutoModeSetting(true);
							} else {
								refreshBathAutoModeSetting(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE3, EDT: 0x%02X}=={BathAutoModeSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isBathAutoModeSetting()));
						break;
						case xEF:
							refreshBathOperationStatus(eConverter.BathOperationStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE5, EDT: 0x%02X}=={BathOperationStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getBathOperationStatus()));
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
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xEF);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{	
						case xEF:
							refreshBathOperationStatus(eConverter.BathOperationStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE5, EDT: 0x%02X}=={BathOperationStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getBathOperationStatus()));
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

	public boolean isHotWaterHeating() {
		return hotWaterHeating;
	}

	public void setHotWaterHeating(boolean hotWaterHeating) {
		this.hotWaterHeating = hotWaterHeating;
	}

	public boolean isBathWaterHeating() {
		return bathWaterHeating;
	}

	public void setBathWaterHeating(boolean bathWaterHeating) {
		this.bathWaterHeating = bathWaterHeating;
	}

	public boolean isBathAutoModeSetting() {
		return bathAutoModeSetting;
	}

	public void setBathAutoModeSetting(boolean bathAutoModeSetting) {
		this.bathAutoModeSetting = bathAutoModeSetting;
	}

	public BathOperationStatus getBathOperationStatus() {
		return bathOperationStatus;
	}

	public void setBathOperationStatus(BathOperationStatus bathOperationStatus) {
		this.bathOperationStatus = bathOperationStatus;
	}
	
}
