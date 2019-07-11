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
package jaist.ac.jp.NodeFinder.echonet.object.groupCode06;



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

public class eDisplay extends eDataObject{
	private static final Logger logger = Logger.getLogger(eDisplay.class.getName());
	private Timer timer;
	private boolean displayControlSetting;
	private boolean stringCharacterSettingAcceptanceStatus;
	private String supportedCharacterCodes;
	private String stringCharacterPresentToUser;
	private int acceptedStringCharacterLength;
	
	

	public eDisplay(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x06);
		this.setClassCode((byte) 0x01);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.Display);
	}
	
	// Provided Services	
	public boolean setStringCharacterPresentToUser(byte StringCode) {
		boolean rs = false;
		if(getStringCharacterPresentToUser().equals(eConverter.dataToStringCode(StringCode))) {
			logger.info(String.format("The StringCharacterPresentToUser is already set to %s ! Nothing todo!",this.getStringCharacterPresentToUser()));
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(this.getNode(),this.getEoj(),EPC.xB3, new ObjectData(StringCode))) {
				refreshStringCharacterPresentToUser(StringCode);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
		
	// Device Property Monitoring
	public void	refreshStringCharacterPresentToUser(byte strCode) {
		String code = eConverter.dataToStringCode(strCode);
		if(!code.equals(getStringCharacterPresentToUser())) {
			logger.info(String.format("StringCharacterPresentToUser changed from %s to %s",getStringCharacterPresentToUser(),code));
			setStringCharacterPresentToUser(code);
			//TODO: More task can be added here
		}
	}
	public void	refreshSupportedCharacterCodes(String code) {
		if(!code.equals(getSupportedCharacterCodes())) {
			logger.info(String.format("SupportedCharacterCodes changed from %s to %s",getSupportedCharacterCodes(),code));
			setSupportedCharacterCodes(code);
			//TODO: More task can be added here
		}
	}
	public void	refreshDisplayControlSetting(boolean val) {
		if(val != isDisplayControlSetting()) {
			logger.info(String.format("DisplayControlSetting changed from %s to %s",isDisplayControlSetting(),val));
			setDisplayControlSetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshStringCharacterSettingAcceptanceStatus(boolean val) {
		if(val != isStringCharacterSettingAcceptanceStatus()) {
			logger.info(String.format("StringCharacterSettingAcceptanceStatus changed from %s to %s",isDisplayControlSetting()?"Ready":"Busy",val?"Ready":"Busy"));
			setStringCharacterSettingAcceptanceStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshAcceptedStringCharacterLength(int val) {
		if(val != getAcceptedStringCharacterLength()) {
			logger.info(String.format("AcceptedStringCharacterLength changed from %d to %d",getAcceptedStringCharacterLength(),val));
			setAcceptedStringCharacterLength(val);
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
		epcs.add(EPC.xB2);
		epcs.add(EPC.xB3);
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
							if(eConverter.dataToInteger(resultData) == 48) {
								refreshDisplayControlSetting(true);
							} else {
								refreshDisplayControlSetting(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB0, EDT: 0x%02X}=={DisplayControlSetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isDisplayControlSetting()?"Enabled":"Disabled"));
						break;
						case xB1:
							if(eConverter.dataToInteger(resultData) == 48) {
								refreshStringCharacterSettingAcceptanceStatus(true);
							} else {
								refreshStringCharacterSettingAcceptanceStatus(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={StringCharacterSettingAcceptanceStatus:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isStringCharacterSettingAcceptanceStatus()?"Ready":"Busy"));
						break;
						case xB2:
							refreshSupportedCharacterCodes(eConverter.dataTo8BitMapcode(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB2, EDT: 0x%02X}=={SupportedCharacterCodes:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSupportedCharacterCodes()));
						break;
						case xB3:
							refreshStringCharacterPresentToUser(resultData.toBytes()[1]);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB3, EDT: 0x%02X}=={StringCharacterPresentToUser:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[1],getStringCharacterPresentToUser()));
						break;
						case xB4:
							refreshAcceptedStringCharacterLength(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xB4, EDT: 0x%02X}=={AcceptedStringCharacterLength:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getAcceptedStringCharacterLength()));
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
						if(eConverter.dataToInteger(resultData) == 48) {
							refreshStringCharacterSettingAcceptanceStatus(true);
						} else {
							refreshStringCharacterSettingAcceptanceStatus(false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0xB1, EDT: 0x%02X}=={StringCharacterSettingAcceptanceStatus:%s}",
								 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isStringCharacterSettingAcceptanceStatus()?"Ready":"Busy"));
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

	public boolean isDisplayControlSetting() {
		return displayControlSetting;
	}

	public void setDisplayControlSetting(boolean displayControlSetting) {
		this.displayControlSetting = displayControlSetting;
	}

	public boolean isStringCharacterSettingAcceptanceStatus() {
		return stringCharacterSettingAcceptanceStatus;
	}

	public void setStringCharacterSettingAcceptanceStatus(boolean stringCharacterSettingAcceptanceStatus) {
		this.stringCharacterSettingAcceptanceStatus = stringCharacterSettingAcceptanceStatus;
	}

	public String getSupportedCharacterCodes() {
		return supportedCharacterCodes;
	}

	public void setSupportedCharacterCodes(String supportedCharacterCodes) {
		this.supportedCharacterCodes = supportedCharacterCodes;
	}

	public String getStringCharacterPresentToUser() {
		return stringCharacterPresentToUser;
	}

	public void setStringCharacterPresentToUser(String stringCharacterPresentToUser) {
		this.stringCharacterPresentToUser = stringCharacterPresentToUser;
	}

	public int getAcceptedStringCharacterLength() {
		return acceptedStringCharacterLength;
	}

	public void setAcceptedStringCharacterLength(int acceptedStringCharacterLength) {
		this.acceptedStringCharacterLength = acceptedStringCharacterLength;
	}

}
