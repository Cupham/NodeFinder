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
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.WaterTemperature;

public class eColdHotWaterHeatSourceEquipment extends eDataObject{
	private static final Logger logger = Logger.getLogger(eColdHotWaterHeatSourceEquipment.class.getName());
	private Timer timer;
	private int waterTemperature1;
	private WaterTemperature waterTemperature2;
	
	public eColdHotWaterHeatSourceEquipment(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x7A);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.ColdHotWaterHeatSourceEquipment);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshWaterTemperature1(int val) {
		if(val != getWaterTemperature1()) {
			logger.info(String.format("WaterTemperature1 changed from %s to %s",getWaterTemperature1(),val));
			setWaterTemperature1(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshWaterTemperature2(WaterTemperature val) {
		if(val != getWaterTemperature2()) {
			logger.info(String.format("WaterTemperature2 changed from %d to %d",getWaterTemperature2(),val));
			setWaterTemperature2(val);
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
						case xE1:
							refreshWaterTemperature1(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={WaterTemperature1:%d degree celcius}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getWaterTemperature1()));
						break;
						case xE2:
							refreshWaterTemperature2(eConverter.WaterTemperature.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE2, EDT: 0x%02X}=={WaterTemperature2:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getWaterTemperature2()));
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

	public int getWaterTemperature1() {
		return waterTemperature1;
	}

	public void setWaterTemperature1(int waterTemperature1) {
		this.waterTemperature1 = waterTemperature1;
	}

	public WaterTemperature getWaterTemperature2() {
		return waterTemperature2;
	}

	public void setWaterTemperature2(WaterTemperature waterTemperature2) {
		this.waterTemperature2 = waterTemperature2;
	}
	
	
}
