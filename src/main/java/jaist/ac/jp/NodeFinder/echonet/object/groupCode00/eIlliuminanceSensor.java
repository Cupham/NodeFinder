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

public class eIlliuminanceSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eIlliuminanceSensor.class.getName());
	private Timer timer;
	private int measuredIlluminance_lux;
	private int measuredIlluminance_klux;

	public eIlliuminanceSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x0D);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.IlluminanceSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshmeasuredIlluminance_lux(int lux) {
		if(this.getMeasuredIlluminance_lux() != lux) {
			logger.info(String.format("IllumianceSensor changed from %d lux to %d lux",getMeasuredIlluminance_lux(),lux));
			setMeasuredIlluminance_lux(lux);
			//TODO: More task can be added here
		}
	}
	public void	refreshmeasuredIlluminance_klux(int klux) {
		if(this.getMeasuredIlluminance_lux() != klux) {
			logger.info(String.format("IllumianceSensor changed from %d klux to %d klux",getMeasuredIlluminance_klux(),klux));
			setMeasuredIlluminance_klux(klux);
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
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE1);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xE0:
							refreshmeasuredIlluminance_lux(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={MeasuredIlluminance (lux):%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredIlluminance_lux()));
						break;
						case xE1:
							refreshmeasuredIlluminance_klux(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={MeasuredIlluminance (klux):%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredIlluminance_klux()));
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
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE1);
		try {
			service.doObserve(getNode(), getEoj(), epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
					case xE0:
						refreshmeasuredIlluminance_lux(eConverter.dataToInteger(resultData));
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={MeasuredIlluminance (lux):%d}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredIlluminance_lux()));
					break;
					case xE1:
						refreshmeasuredIlluminance_klux(eConverter.dataToInteger(resultData));
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={MeasuredIlluminance (klux):%d}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredIlluminance_klux()));
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


	public int getMeasuredIlluminance_lux() {
		return measuredIlluminance_lux;
	}

	public void setMeasuredIlluminance_lux(int measuredIlluminance_lux) {
		this.measuredIlluminance_lux = measuredIlluminance_lux;
	}

	public int getMeasuredIlluminance_klux() {
		return measuredIlluminance_klux;
	}

	public void setMeasuredIlluminance_klux(int measuredIlluminance_klux) {
		this.measuredIlluminance_klux = measuredIlluminance_klux;
	}

}
