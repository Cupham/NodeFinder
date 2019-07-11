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
import echowand.object.ObjectData;
import echowand.service.Service;
import echowand.service.result.GetListener;
import echowand.service.result.GetResult;
import echowand.service.result.ObserveListener;
import echowand.service.result.ObserveResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.App;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eDeviceType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter;
import jaist.ac.jp.NodeFinder.echonet.object.utils.SampleConstants;

public class eEarthquakeSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eEarthquakeSensor.class.getName());
	private Timer timer;
	private boolean earthquakeOccurrenceStatus;
	private String detectionThresholdLevel;
	private float siValue;
	private boolean collapseOccurrenceStatus;
	

	public eEarthquakeSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x05);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.EarthquakeSensor);
	}
	
	// Provided Services	
	public boolean resetEarthquakeOccurrenceStatus() {
		boolean rs = false;
		if(!isEarthquakeOccurrenceStatus()) {
			logger.info(String.format("There is not an earthquake Occurence situation at sensor %s ! Nothing todo!",this.getNode()));
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xBF, new ObjectData((byte)0x00))) {
				refreshEarthquakeOccurrenceStatus(false);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
	
	public boolean resetSIValue() {
		boolean rs = false;
		if(getSiValue() == 0) {
			logger.info(String.format("SI value has been resetted at sensor %s ! Nothing todo!",this.getNode()));
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xC1, new ObjectData((byte)0x00))) {
				refreshSiValue(0.0f);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
	
	public boolean resetCollapseOccurrenceStatus() {
		boolean rs = false;
		if(!isCollapseOccurrenceStatus()) {
			logger.info(String.format("There is not an Collapse Occurence situation at sensor %s ! Nothing todo!",this.getNode()));
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xC3, new ObjectData((byte)0x00))) {
				refreshECollapseOccurrenceStatus(false);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
		
	// Device Property Monitoring
	public void	refreshDetectionThresholdLevel(String level) {
		if(!level.equals(getDetectionThresholdLevel())) {
			logger.info(String.format("EarthquakeDetectionThresholdLevel changed from %s to %s",getDetectionThresholdLevel(),level));
			setDetectionThresholdLevel(level);
			//TODO: More task can be added here
		}
	}
	public void	refreshSiValue(float value) {
		if(value != getSiValue()) {
			logger.info(String.format("EarthquakeDetection SI value changed from %f to %f",getSiValue(),value));
			setSiValue(value);
			//TODO: More task can be added here
		}
	}
	public void	refreshECollapseOccurrenceStatus (boolean status) {
		if(this.isCollapseOccurrenceStatus() != status) {
			logger.info(String.format("CollapseOccurenceStatus changed from %b to %b",isCollapseOccurrenceStatus(),status));
			setCollapseOccurrenceStatus(status);
			//TODO: More task can be added here
		}
	}
	
	public void	refreshEarthquakeOccurrenceStatus (boolean status) {
		if(this.isEarthquakeOccurrenceStatus() != status) {
			logger.info(String.format("EarthquakeOccurenceStatus changed from %b to %b",isEarthquakeOccurrenceStatus(),status));
			setEarthquakeOccurrenceStatus(status);
			//TODO: More task can be added here
		}
	}
	
	// Override functions
	@Override
	public void dataFromEOJ(Service s){
		final Service service = s;
		final Node node = this.getNode();
		final EOJ eoj = this.getEoj();
		observeSuperData(service, node, eoj);
		observeSpecificData(service);
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
		epcs.add(EPC.xC0);
		epcs.add(EPC.xC2);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xB1:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshEarthquakeOccurrenceStatus(true);
							} else {
								refreshEarthquakeOccurrenceStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={EarthquakeDetect:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isEarthquakeOccurrenceStatus()));
						break;
						case xB0:
							refreshDetectionThresholdLevel(eConverter.dataToLevel(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={EarthquakeDetectionThresholdLevel:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getDetectionThresholdLevel()));
						break;
						case xC0:
							refreshSiValue(eConverter.dataToInteger(resultData)*0.1f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC0, EDT: 0x%02X}=={SI Value:%f}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSiValue()));
						break;
						case xC2:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshECollapseOccurrenceStatus(true);
							} else {
								refreshECollapseOccurrenceStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC2, EDT: 0x%02X}=={CollapseOccurence:%b}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isCollapseOccurrenceStatus()));
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


	public String getDetectionThresholdLevel() {
		return detectionThresholdLevel;
	}

	public void setDetectionThresholdLevel(String detectionThresholdLevel) {
		this.detectionThresholdLevel = detectionThresholdLevel;
	}

	@Override
	public void observeSpecificData(Service service) {
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xB1);
		epcs.add(EPC.xC0);
		epcs.add(EPC.xC2);
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
							refreshEarthquakeOccurrenceStatus(true);
						} else {
							refreshEarthquakeOccurrenceStatus(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={EarthquakeDetect:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isEarthquakeOccurrenceStatus()));
					break;
					case xC0:
						refreshSiValue(eConverter.dataToInteger(resultData)*0.1f);
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xC0, EDT: 0x%02X}=={SI Value:%f}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSiValue()));
					break;
					case xC2:
						if(eConverter.dataToInteger(resultData) == 65) {
							refreshECollapseOccurrenceStatus(true);
						} else {
							refreshECollapseOccurrenceStatus(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xC2, EDT: 0x%02X}=={CollapseOccurence:%b}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isCollapseOccurrenceStatus()));
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

	public boolean isEarthquakeOccurrenceStatus() {
		return earthquakeOccurrenceStatus;
	}

	public void setEarthquakeOccurrenceStatus(boolean earthquakeOccurrenceStatus) {
		this.earthquakeOccurrenceStatus = earthquakeOccurrenceStatus;
	}

	public float getSiValue() {
		return siValue;
	}

	public void setSiValue(float siValue) {
		this.siValue = siValue;
	}

	public boolean isCollapseOccurrenceStatus() {
		return collapseOccurrenceStatus;
	}

	public void setCollapseOccurrenceStatus(boolean collapseOccurrenceStatus) {
		this.collapseOccurrenceStatus = collapseOccurrenceStatus;
	}

}
