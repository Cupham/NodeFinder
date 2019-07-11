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

public class eAirSpeedSensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eAirSpeedSensor.class.getName());
	private Timer timer;
	private float measuredAirSpeed;
	private int airFlowDirection;

	public eAirSpeedSensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x1F);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.AirSpeedSensor);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshMeasuredAirSpeed(float val) {
		if(getMeasuredAirSpeed() != val) {
			logger.info(String.format("MeasuredAirSpeed changed from %f to %f",getMeasuredAirSpeed(),val));
			setMeasuredAirSpeed(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshAirFlowDirection(int val) {
		if(getAirFlowDirection() != val) {
			logger.info(String.format("AirFlowDirection changed from %f to %f",getAirFlowDirection(),val));
			setAirFlowDirection(val);
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
							refreshMeasuredAirSpeed(eConverter.dataToShort(resultData)*0.01f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={AirSpeed:%f m/sec}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredAirSpeed()));
						break;
						case xE1:
							refreshAirFlowDirection(eConverter.dataToShort(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={AirSpeed:%d degree}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getAirFlowDirection()));
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
		//no extra observable attribute
		
	}

	public float getMeasuredAirSpeed() {
		return measuredAirSpeed;
	}

	public void setMeasuredAirSpeed(float measuredAirSpeed) {
		this.measuredAirSpeed = measuredAirSpeed;
	}

	public int getAirFlowDirection() {
		return airFlowDirection;
	}

	public void setAirFlowDirection(int airFlowDirection) {
		this.airFlowDirection = airFlowDirection;
	}

}
