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
package jaist.ac.jp.NodeFinder.echonet.object.groupCode03;



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
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.RiceCookingStatus;

public class eRiceCooker extends eDataObject{
	private static final Logger logger = Logger.getLogger(eRiceCooker.class.getName());
	private Timer timer;
	private boolean coverOpenCloseStatus;
	private RiceCookingStatus riceCookingStatus;
	private boolean riceCookingControlSetting;
	
	

	public eRiceCooker(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x03);
		this.setClassCode((byte) 0xBB);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.RiceCooker);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshCoverOpenCloseStatus(boolean val) {
		if(val != isCoverOpenCloseStatus()) {
			logger.info(String.format("CoverOpenCloseStatus changed from %s to %s",isCoverOpenCloseStatus(),val));
			setCoverOpenCloseStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshRiceCookingControlSetting(boolean val) {
		if(val != getRiceCookingControlSetting()) {
			logger.info(String.format("RiceCookingControlSetting changed from %s to %s",
					getRiceCookingControlSetting()?"Rice cooking start/restart":"Rice cooking suspension",
							val?"Rice cooking start/restart":"Rice cooking suspension"));
			setRiceCookingControlSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshRiceCookingStatus(RiceCookingStatus val) {
		if(!val.equals(getRiceCookingStatus())) {
			logger.info(String.format("RiceCookingStatus changed from %s to %s",getRiceCookingStatus(),val));
			setRiceCookingStatus(val);
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
		epcs.add(EPC.xB1);
		epcs.add(EPC.xB2);
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
							refreshRiceCookingStatus(eConverter.RiceCookingStatus.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={RiceCookingStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getRiceCookingStatus()));
						break;
						case xB2:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshRiceCookingControlSetting(true);
							} else {
								refreshRiceCookingControlSetting(false);
								
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB2, EDT: 0x%02X}=={RiceCookingControlSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],
									 getRiceCookingControlSetting()?"Rice cooking start/restart":"Rice cooking suspension"));
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

	public boolean isCoverOpenCloseStatus() {
		return coverOpenCloseStatus;
	}

	public void setCoverOpenCloseStatus(boolean coverOpenCloseStatus) {
		this.coverOpenCloseStatus = coverOpenCloseStatus;
	}

	public RiceCookingStatus getRiceCookingStatus() {
		return riceCookingStatus;
	}

	public void setRiceCookingStatus(RiceCookingStatus riceCookingStatus) {
		this.riceCookingStatus = riceCookingStatus;
	}

	public boolean getRiceCookingControlSetting() {
		return riceCookingControlSetting;
	}

	public void setRiceCookingControlSetting(boolean riceCookingControlSetting) {
		this.riceCookingControlSetting = riceCookingControlSetting;
	}

}
