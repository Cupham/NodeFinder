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
package jaist.ac.jp.NodeFinder.echonet.object.groupCode00;



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
import echowand.service.result.ObserveListener;
import echowand.service.result.ObserveResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eDeviceType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter;
import jaist.ac.jp.NodeFinder.echonet.object.utils.SampleConstants;

public class eBathWaterLevelSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eBathWaterLevelSensor.class.getName());
	private Timer timer;
	private int overBathWaterLevelValue;
	private int measuredBathWaterLevelValue;
	private boolean overBathWaterLevelDetectionStatus;
	
	
	public eBathWaterLevelSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x15);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.BathWaterLevelSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshOverBathWaterLevelValue(int overBathWaterLevelValueInCm) {
		if(this.getOverBathWaterLevelValue() != overBathWaterLevelValueInCm) {
			logger.info(String.format("OverBathWaterLevelValue changed from %d cm to %d cm",getOverBathWaterLevelValue(),overBathWaterLevelValueInCm));
			setOverBathWaterLevelValue(overBathWaterLevelValueInCm);
			//TODO: More task can be added here
		}
	}
	public void	refreshMeasuredBathWaterLevelValue(int measureWaterLevelValueInCm) {
		if(this.getMeasuredBathWaterLevelValue() != measureWaterLevelValueInCm) {
			logger.info(String.format("bathWaterLevelValue changed from %d cm to %d cm",getMeasuredBathWaterLevelValue(),measureWaterLevelValueInCm));
			setMeasuredBathWaterLevelValue(measureWaterLevelValueInCm);
			//TODO: More task can be added here
		}
	}
	public void	refreshOverBathWaterLevelDetectionStatus(boolean waterOverflow) {
		if(this.isOverBathWaterLevelDetectionStatus() != waterOverflow) {
			logger.info(String.format("OverBathWaterLevelDetectionStatus changed from %b  to %b ",isOverBathWaterLevelDetectionStatus(),waterOverflow));
			setOverBathWaterLevelDetectionStatus(waterOverflow);
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
		epcs.add(EPC.xB1);
		epcs.add(EPC.xE0);
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
							refreshOverBathWaterLevelValue(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={OverBathWaterLevelValue (cm):%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOverBathWaterLevelValue()));
						break;
						case xB1:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshOverBathWaterLevelDetectionStatus(true);
							} else {
								refreshOverBathWaterLevelDetectionStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={OverBathWaterLevelDetect:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isOverBathWaterLevelDetectionStatus()));
						break;	
						case xE0:
							refreshMeasuredBathWaterLevelValue(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={MeasuredBathWaterLevelValue (cm):%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredBathWaterLevelValue()));
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
		epcs.add(EPC.xB1);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xB1:
						if(eConverter.dataToInteger(resultData) == 65) {
							refreshOverBathWaterLevelDetectionStatus(true);
						} else {
							refreshOverBathWaterLevelDetectionStatus(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={OverBathWaterLevelDetect:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isOverBathWaterLevelDetectionStatus()));
					break;	
					default:
						break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE,ex.toString());
			
		}
		
	}

	public int getOverBathWaterLevelValue() {
		return overBathWaterLevelValue;
	}

	public void setOverBathWaterLevelValue(int overBathWaterLevelValue) {
		this.overBathWaterLevelValue = overBathWaterLevelValue;
	}

	public int getMeasuredBathWaterLevelValue() {
		return measuredBathWaterLevelValue;
	}

	public void setMeasuredBathWaterLevelValue(int measuredBathWaterLevelValue) {
		this.measuredBathWaterLevelValue = measuredBathWaterLevelValue;
	}

	public boolean isOverBathWaterLevelDetectionStatus() {
		return overBathWaterLevelDetectionStatus;
	}

	public void setOverBathWaterLevelDetectionStatus(boolean overBathWaterLevelDetectionStatus) {
		this.overBathWaterLevelDetectionStatus = overBathWaterLevelDetectionStatus;
	}

}
