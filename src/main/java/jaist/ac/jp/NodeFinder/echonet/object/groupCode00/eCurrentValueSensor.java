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
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eDeviceType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter;
import jaist.ac.jp.NodeFinder.echonet.object.utils.SampleConstants;

public class eCurrentValueSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eCurrentValueSensor.class.getName());
	private Timer timer;
	private long measuredCurrent1;
	private long measuredCurrent2;
	private float ratedVoltageToBeMeasured;
	
	
	public eCurrentValueSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x23);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.CurrentValueSensor);
	}
	
	// Provided Services			
	// Device Property Monitoring
	public void	refreshMeasuredCurrent1(long val) {
		if(val != this.getMeasuredCurrent1()) {
			logger.info(String.format("MeasuredCurrent1 changed from %d to %d",getMeasuredCurrent1(),val));
			setMeasuredCurrent1(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshMeasuredCurrent2(long val) {
		if(val != this.getMeasuredCurrent2()) {
			logger.info(String.format("MeasuredCurrent2 changed from %d to %d",getMeasuredCurrent2(),val));
			setMeasuredCurrent2(val);
			//TODO: More task can be added here
		}
	}
	
	public void	refreshRatedVoltageToBeMeasured(int val) {
		if(val != this.getRatedVoltageToBeMeasured()) {
			logger.info(String.format("RatedVoltageToBeMeasured changed from %d to %d",getRatedVoltageToBeMeasured(),val));
			setRatedVoltageToBeMeasured(val);
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
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE1);
		epcs.add(EPC.xE2);
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
							refreshMeasuredCurrent1(eConverter.dataToLong(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={MeasuredCurrent1:%d mA}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredCurrent1()));
						break;
						case xE1:
							refreshRatedVoltageToBeMeasured(eConverter.dataToShort(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={RatedVoltageToBeMeasured:%d V}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getRatedVoltageToBeMeasured()));
						break;
						case xE2:
							refreshMeasuredCurrent2(eConverter.dataToLong(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE2, EDT: 0x%02X}=={hMeasuredCurrent2:%d mA}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredCurrent2()));
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

	public long getMeasuredCurrent1() {
		return measuredCurrent1;
	}

	public void setMeasuredCurrent1(long measuredCurrent1) {
		this.measuredCurrent1 = measuredCurrent1;
	}

	public long getMeasuredCurrent2() {
		return measuredCurrent2;
	}

	public void setMeasuredCurrent2(long measuredCurrent2) {
		this.measuredCurrent2 = measuredCurrent2;
	}

	public float getRatedVoltageToBeMeasured() {
		return ratedVoltageToBeMeasured;
	}

	public void setRatedVoltageToBeMeasured(float ratedVoltageToBeMeasured) {
		this.ratedVoltageToBeMeasured = ratedVoltageToBeMeasured;
	}

	

}
