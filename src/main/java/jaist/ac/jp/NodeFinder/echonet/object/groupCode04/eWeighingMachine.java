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
package jaist.ac.jp.NodeFinder.echonet.object.groupCode04;



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

public class eWeighingMachine extends eDataObject{
	private static final Logger logger = Logger.getLogger(eWeighingMachine.class.getName());
	private Timer timer;
	private float measuredBodyWeight;
	private float measuredBodyFat;


	public eWeighingMachine(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x04);
		this.setClassCode((byte) 0x01);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.WeighingMachine);
	}
	
	// Provided Services	
		
	// Device Property Monitoring
	public void	refreshMeasuredBodyWeight(float val) {
		if(getMeasuredBodyWeight() != val) {
			logger.info(String.format("MeasuredBodyWeight changed from %f kg to %f kg",getMeasuredBodyWeight(),val));
			setMeasuredBodyWeight(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshMeasuredBodyFat(float val) {
		if(getMeasuredBodyFat() != val) {
			logger.info(String.format("MeasuredBodyFat changed from %f percent to %f percent",getMeasuredBodyFat(),val));
			setMeasuredBodyFat(val);
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
							refreshMeasuredBodyWeight(eConverter.dataToShort(resultData)*0.1f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={BodyWeight:%f kg}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredBodyWeight()));
						break;
						case xE1:
							refreshMeasuredBodyWeight(eConverter.dataToShort(resultData)*0.1f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={BodyFat:%f percent}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMeasuredBodyFat()));
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

	public float getMeasuredBodyWeight() {
		return measuredBodyWeight;
	}

	public void setMeasuredBodyWeight(float measuredBodyWeight) {
		this.measuredBodyWeight = measuredBodyWeight;
	}

	public float getMeasuredBodyFat() {
		return measuredBodyFat;
	}

	public void setMeasuredBodyFat(float measuredBodyFat) {
		this.measuredBodyFat = measuredBodyFat;
	}

}
