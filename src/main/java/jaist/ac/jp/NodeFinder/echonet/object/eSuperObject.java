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
package jaist.ac.jp.NodeFinder.echonet.object;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.common.EPC;
import echowand.net.Node;
import echowand.net.SubnetException;
import echowand.object.EchonetObjectException;
import echowand.object.ObjectData;
import echowand.service.Service;
import echowand.service.result.GetListener;
import echowand.service.result.GetResult;
import echowand.service.result.ObserveListener;
import echowand.service.result.ObserveResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.App;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x80Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x81Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x82Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x83Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x84Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x85Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x86Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x87Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x88Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x89Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x8AHandler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x8BHandler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x8CHandler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x8DHandler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x8EHandler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x8FHandler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x93Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x97Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x98Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x99Handler;
import jaist.ac.jp.NodeFinder.echonet.object.epchandlers.EPC0x9AHandler;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eDeviceType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter;
import jaist.ac.jp.NodeFinder.echonet.object.utils.SampleConstants;

public class eSuperObject {
	private static final Logger logger = Logger.getLogger(eSuperObject.class.getName());
	private Timer timer;
	private String deviceID;
	private eDeviceType type;
	public boolean observeEnabled;
	/**
	 * Device IP
	 */
	private Node node;
	/**
	 * Class group code
	 */
	private byte groupCode;
	/**
	 * Class code
	 */
	private byte classCode;
	/**
	 * Instance code
	 */
	private byte instanceCode;

	private EOJ eoj;

	/**
	 * EPC: 0x80 ON: 0x30, OFF: 0x31
	 */
	private boolean operationStatus;
	/**
	 * EPC: 0x81
	 */
	private String installLocation;
	/**
	 * EPC: 0x82 the release number of the corresponding Appendix with fixed 4
	 * bytes
	 */
	private String standardVersionInfo;
	/**
	 * EPC: 0x83 Unique identification number fixed 9 bytes
	 */
	private String identificationNumber;
	/**
	 * EPC: 0x84 Instantaneous power consumption of the device in watts Value
	 * between: 0x0000–0xFFFD(0–65533W)
	 */
	private short instantaneousPower;
	/**
	 * EPC: 0x85 Cumulative power consumption of the device in increments of
	 * 0.001kWh Value between: 0x00000000–0x3B9AC9FF (0–999,999.999kWh)
	 */
	private long cumulativePower;
	/**
	 * EPC: 0x86 Manufacturer-defined fault code
	 */
	private String manufacturerFaultCode;
	/**
	 * EPC: 0x87 Current limit setting Value betwee: 0x00–0x64 (=0–100%)
	 */
	private int currentLimitSetting;
	/**
	 * EPC: 0x88 whether a fault (e.g. a sensor trouble) has occurred or not.
	 * Fault occurred: 0x41, No fault has occurred: 0x42
	 */
	private boolean faultStatus;
	/**
	 * EPC: 0x89 Describes the fault
	 */
	private String faultDescription;
	/**
	 * EPC:0x8A 3-byte manufacturer code unsigned (Defined by the ECHONET
	 * Consortium)
	 */
	private String manufacturerCode;
	/**
	 * EPC: 0x8B 3-byte business facility code (Defined by each manufacturer)
	 */
	private String businessFacilityCode;
	/**
	 * EPC: 0x8C Identifies the product using ASCII code (Defined by each
	 * manufacturer)
	 */
	private String productCode;
	/**
	 * EPC: 0x8D Production number using ASCII code (Defined by each
	 * manufacturer)
	 */
	private String productNumber;
	/**
	 * EPC: 0x8E 4-byte production date code, YYMD format
	 */
	private Date productDate;
	/**
	 * EPC: 0x8F Device is operating in power-saving mode Operating in
	 * power-saving mode: 0x41, Operating in normal operation mode: 0x42
	 */
	private boolean powerSaving;
	/**
	 * EPC: 0x93 Whether remote control is through a public network or not Not
	 * through a public network: 0x41 Through a public network: 0x42
	 */
	private boolean throughPublicNetwork;
	/**
	 * EPC: 0x97 Current time (HH: MM format), 0x00–0x17: 0x00–0x3B (=0–23):
	 * (=0–59)
	 */
	private String currentTimeSetting;
	/**
	 * EPC: 0x98 Current date (YYYY: MM: DD format),1–0x270F : 1–0x0C : 1–0x1F
	 * (=1–9999) : (=1–12) : (=1–31)
	 */
	private Date currentDateSetting;
	/**
	 * EPC: 0x99 Power limit setting in watts 0x0000–0xFFFF(0–65535W)
	 */
	private int powerLimit;
	/**
	 * EPC: 0x9A Cumulative operating time
	 */
	private String cumulativeTime;
	public eSuperObject() {
		super();
	}

	/**
	 * Constructor
	 *
	 * @param ip
	 * @param name
	 */
	public eSuperObject(Node node, EOJ eoj) {
		eSuperObject.this.node = node;
		eSuperObject.this.eoj = eoj;
		eSuperObject.this.deviceID = node.getNodeInfo().toString().replace(".", "_")+"_"+eoj.getInstanceCode();
		eSuperObject.this.type = eDeviceType.SuperObject;
		this.observeEnabled = App.observe;
	}
	public void getSuperData(Service service, Node n, EOJ e){
		final EOJ eoj = e;
		final Node node = n;
		LinkedList<EPC> epcs = new LinkedList<EPC>();
		epcs.addAll(SampleConstants.defaultEPCs);
		try {
			service.doGet(node,eoj,epcs,5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) {
					case x80:
						if(eConverter.dataToInteger(resultData) == 48) {
							EPC0x80Handler.refreshOperationStatus(eSuperObject.this, true);
						} else {
							EPC0x80Handler.refreshOperationStatus(eSuperObject.this, false);
						}
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x80, EDT: 0x%02X}=={OperationStatus:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getOperationStatus()));

						break;
					case x81:
						String rsLocation = eConverter.dataToInstallLocation(resultData);
						if (rsLocation == null) {
							rsLocation = " The installation location has not been set";
						}
						EPC0x81Handler.refreshInstallLocation(eSuperObject.this,rsLocation);

						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x81, EDT: 0x%02X}=={InstallationLocation:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getInstallLocation()));
						break;
					case x82:
						EPC0x82Handler.refreshStandardVersionInfo(eSuperObject.this,eConverter.dataToVersion(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x82, EDT: 0x%02X}=={Version Information:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getStandardVersionInfo()));
						break;
					case x83:
						EPC0x83Handler.refreshIdentificationNumber(eSuperObject.this,eConverter.dataToIdentifiCationNumber(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x83, EDT: 0x%02X}=={Identification Number:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getIdentificationNumber()));
						break;
					case x84:
						EPC0x84Handler.refreshInstantaneousPower(eSuperObject.this,eConverter.dataToShort(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x84, EDT: 0x%02X}=={Instantaneous Power Consumption:%d}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getInstantaneousPower()));
						break;
					case x85:
						EPC0x85Handler.refreshCumulativePower(eSuperObject.this,eConverter.dataToLong(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x85, EDT: 0x%02X}=={Cumulative Power Consumption:%d}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getCumulativePower()));
						break;
					case x86:
						EPC0x86Handler.refreshManufacturerFaultCode(eSuperObject.this,eConverter.dataToFaultCode(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x86, EDT: 0x%02X}=={Manufacturer Fault Code:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getManufacturerFaultCode()));
						break;
					case x87:
						EPC0x87Handler.refreshCurrentLimitSetting(eSuperObject.this,eConverter.dataToInteger(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x87, EDT: 0x%02X}=={Current Limit Setting:%d}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getCurrentLimitSetting()));
						break;
					case x88:
						if(eConverter.dataToInteger(resultData) == 65) {
							EPC0x88Handler.refreshFaultStatus(eSuperObject.this,true);
						} else {
							EPC0x88Handler.refreshFaultStatus(eSuperObject.this,false);
						}
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x88, EDT: 0x%02X}=={Fault Status:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],isFaultStatus()));
						break;
					case x89:
						if (isFaultStatus()) {
							try {
								EPC0x89Handler.refreshFaultDescription(eSuperObject.this,eConverter.getFaultDetail(resultData));
							} catch (EchonetObjectException e) {
								e.printStackTrace();
							}
						} else {
							EPC0x89Handler.refreshFaultDescription(eSuperObject.this,"NO FAULT");
						}
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x89, EDT: 0x%02X}=={Fault Description:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getFaultDescription()));
						break;
					case x8A:
						EPC0x8AHandler.refreshManufacturerCode(eSuperObject.this,eConverter.dataToString(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x8A, EDT: 0x%02X}=={Manufacturer Code:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getManufacturerCode()));
						break;
					case x8B:
						EPC0x8BHandler.refreshBusinessFacilityCode(eSuperObject.this,eConverter.dataToString(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x8B, EDT: 0x%02X}=={Business Facility Code:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getBusinessFacilityCode()));
						break;
					case x8C:
						EPC0x8CHandler.refreshProductCode(eSuperObject.this,eConverter.dataToString(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x8C, EDT: 0x%02X}=={Product Code:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getProductCode()));
						break;
					case x8D:
						EPC0x8DHandler.refreshProductNumber(eSuperObject.this,eConverter.dataToString(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x8D, EDT: 0x%02X}=={Product Number:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getProductNumber()));
						break;
					case x8E:
						EPC0x8EHandler.refreshProductDate(eSuperObject.this,eConverter.dataToDate(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x8E, EDT: 0x%02X}=={Production Date:%tA}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getProductDate()));
						break;
					case x8F:
						if(eConverter.dataToInteger(resultData) == 65) {
							EPC0x8FHandler.refreshPowerSaving(eSuperObject.this,true);
						} else {
							EPC0x8FHandler.refreshPowerSaving(eSuperObject.this,false);
						}
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x8F, EDT: 0x%02X}=={PowerSaving Mode:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getPowerSaving()));
						break;
					case x93:
						if(eConverter.dataToInteger(resultData) == 65) {
							EPC0x93Handler.refreshThroughPublicNetwork(eSuperObject.this,false);
						} else {
							EPC0x93Handler.refreshThroughPublicNetwork(eSuperObject.this,true);
						}
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x93, EDT: 0x%02X}=={isThrough Public Network:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getThroughPublicNetwork()));
						break;
					case x97:
						EPC0x97Handler.refreshCurrentTimeSetting(eSuperObject.this,eConverter.dataToTime(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x97, EDT: 0x%02X}=={Current Time Setting:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getCurrentTimeSetting()));
						break;
					case x98:
						EPC0x98Handler.refreshCurrentDateSetting(eSuperObject.this,eConverter.dataToDate(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x98, EDT: 0x%02X}=={Current Date Setting:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getCurrentDateSetting()));
						break;
					case x99:
						EPC0x99Handler.refreshPowerLimit(eSuperObject.this,eConverter.dataToShort(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x99, EDT: 0x%02X}=={Power Limit:%d}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getPowerLimit()));
						break;
					case x9A:
						EPC0x9AHandler.refreshCumulativeTime(eSuperObject.this,eConverter.dataToCummalativeTime(resultData));
						logger.info(String.format("Node:%s@EOJ:%s {EPC:0x9A, EDT: 0x%02X}=={Up Time:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getCumulativeTime()));
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

	public void profileObjectFromEPC(Service service) {
		final Service sv = service;
		final Node node = eSuperObject.this.node;
		final EOJ eoj = eSuperObject.this.eoj;
		timer = new Timer(true);
		if(observeEnabled) {
			observeSuperData(service, node, eoj);
		}
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				getSuperData(sv,node,eoj);
			}
		},SampleConstants.getDelayInterval(), SampleConstants.getRefreshInterval());
	}




	@Override
	public String toString() {
		StringBuilder rs = new StringBuilder();
		rs.append(" Device IP: " + eSuperObject.this.node.toString() + ",");
		rs.append(" Device ID: " + eSuperObject.this.deviceID + ",");
		rs.append(" Operation status: " + ((eSuperObject.this.operationStatus) ? "ON" : "OFF") + ",");
		rs.append(" Installation location: " + eSuperObject.this.installLocation + ",");
		rs.append(" Standard version information: " + eSuperObject.this.standardVersionInfo + ",");
		rs.append(" Identify number: " + eSuperObject.this.identificationNumber + ",");
		rs.append(" Measure instantaneous power consumption: " + eSuperObject.this.instantaneousPower + "W,");
		rs.append(" Measured cumulative power consumption: " + (eSuperObject.this.cumulativePower / 1000) + "kWh,");
		rs.append(" Manufacturer’s fault code: " + eSuperObject.this.manufacturerFaultCode + ",");
		rs.append(" Current limit setting: " + eSuperObject.this.currentLimitSetting + "%,");
		rs.append(" Fault status: " + ((eSuperObject.this.faultStatus) ? "Fault occurred" : "No fault has occurred") + ",");
		rs.append(" Fault description: " + eSuperObject.this.faultDescription + ",");
		rs.append(" Manufacturer code: " + eSuperObject.this.manufacturerCode + ",");
		rs.append(" Business facility code: " + eSuperObject.this.businessFacilityCode + ",");
		rs.append(" Product code: " + eSuperObject.this.productCode + ",");
		rs.append(" Production number: " + eSuperObject.this.productNumber + ",");
		rs.append(" Production date: "
				+ ((eSuperObject.this.productDate != null) ? (new SimpleDateFormat("yyyy-MM-dd").format(eSuperObject.this.productDate)) : "NULL")
				+ ",");
		rs.append(" Power-saving operation setting: "
				+ ((eSuperObject.this.powerSaving) ? "Power saving mode" : "Normal operation mode") + ",");
		rs.append(" Remote control setting: "
				+ ((eSuperObject.this.throughPublicNetwork) ? "Not through a public network" : "Through a public network") + ",");
		rs.append(" Current time setting: " + eSuperObject.this.currentTimeSetting + ",");
		rs.append(" Current date setting: " + ((eSuperObject.this.currentDateSetting != null)
				? (new SimpleDateFormat("yyyy-MM-dd").format(eSuperObject.this.currentDateSetting)) : "NULL") + ",");
		rs.append(" Power limit setting: " + eSuperObject.this.powerLimit + "W,");
		rs.append(" Cumulative operating time: " + eSuperObject.this.cumulativeTime);
		return rs.toString();
	}


	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceName) {
		eSuperObject.this.deviceID = deviceName;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		eSuperObject.this.node = node;
	}

	public byte getGroupCode() {
		return groupCode;
	}
	public void setOperationStatus(boolean operationStatus) {
		this.operationStatus = operationStatus;
	}

	public void setInstallLocation(String installLocation) {
		this.installLocation = installLocation;
	}

	public void setStandardVersionInfo(String standardVersionInfo) {
		this.standardVersionInfo = standardVersionInfo;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public void setInstantaneousPower(short instantaneousPower) {
		this.instantaneousPower = instantaneousPower;
	}

	public void setCumulativePower(long cumulativePower) {
		this.cumulativePower = cumulativePower;
	}

	public void setManufacturerFaultCode(String manufacturerFaultCode) {
		this.manufacturerFaultCode = manufacturerFaultCode;
	}

	public void setCurrentLimitSetting(int currentLimitSetting) {
		this.currentLimitSetting = currentLimitSetting;
	}

	public void setFaultStatus(boolean faultStatus) {
		this.faultStatus = faultStatus;
	}

	public void setFaultDescription(String faultDescription) {
		this.faultDescription = faultDescription;
	}

	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}

	public void setBusinessFacilityCode(String businessFacilityCode) {
		this.businessFacilityCode = businessFacilityCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public void setPowerSaving(boolean powerSaving) {
		this.powerSaving = powerSaving;
	}

	public void setThroughPublicNetwork(boolean throughPublicNetwork) {
		this.throughPublicNetwork = throughPublicNetwork;
	}

	public void setCurrentTimeSetting(String currentTimeSetting) {
		this.currentTimeSetting = currentTimeSetting;
	}

	public void setCurrentDateSetting(Date currentDateSetting) {
		this.currentDateSetting = currentDateSetting;
	}

	public void setPowerLimit(int powerLimit) {
		this.powerLimit = powerLimit;
	}

	public void setCumulativeTime(String cumulativeTime) {
		this.cumulativeTime = cumulativeTime;
	}

	public void setGroupCode(byte groupCode) {
		eSuperObject.this.groupCode = groupCode;
	}
	public byte getClassCode() {
		return classCode;
	}
	public void setClassCode(byte classCode) {
		eSuperObject.this.classCode = classCode;
	}
	public byte getInstanceCode() {
		return instanceCode;
	}
	public void setInstanceCode(byte instanceCode) {
		eSuperObject.this.instanceCode = instanceCode;
	}
	public boolean getOperationStatus() {
		return operationStatus;
	}
	public String getInstallLocation() {
		return installLocation;
	}
	public String getStandardVersionInfo() {
		return standardVersionInfo;
	}
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public short getInstantaneousPower() {
		return instantaneousPower;
	}

	public long getCumulativePower() {
		return cumulativePower;
	}

	public String getManufacturerFaultCode() {
		return manufacturerFaultCode;
	}

	public int getCurrentLimitSetting() {
		return currentLimitSetting;
	}

	public boolean isFaultStatus() {
		return faultStatus;
	}

	public String getFaultDescription() {
		return faultDescription;
	}

	public String getManufacturerCode() {
		return manufacturerCode;
	}

	public String getBusinessFacilityCode() {
		return businessFacilityCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getProductNumber() {
		return productNumber;
	}
	public Date getProductDate() {
		return productDate;
	}

	public boolean getPowerSaving() {
		return powerSaving;
	}

	public boolean getThroughPublicNetwork() {
		return throughPublicNetwork;
	}

	public String getCurrentTimeSetting() {
		return currentTimeSetting;
	}

	public Date getCurrentDateSetting() {
		return currentDateSetting;
	}

	public int getPowerLimit() {
		return powerLimit;
	}

	public String getCumulativeTime() {
		return cumulativeTime;
	}

	public EOJ getEoj() {
		return eoj;
	}

	public void setEoj(EOJ eoj) {
		eSuperObject.this.eoj = eoj;
	}

	// Command executor
	public void observeSuperData(Service service, Node n, EOJ e) {
		final EOJ eoj = e;
		final Node node = n;
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.x80);
		epcs.add(EPC.x81);
		epcs.add(EPC.x88);
		try {
			service.doObserve(node, eoj, epcs, new ObserveListener() {
				@Override
			    public void receive(ObserveResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC())
					{
					case x80:
						if(eConverter.dataToInteger(resultData) == 48) {
							EPC0x80Handler.refreshOperationStatus(eSuperObject.this, true);
						} else {
							EPC0x80Handler.refreshOperationStatus(eSuperObject.this, false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0x80, EDT: 0x%02X}=={OperationStatus:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getOperationStatus()));
						break;
					case x81:
						String rsLocation = eConverter.dataToInstallLocation(resultData);
						if (rsLocation == null) {
							rsLocation = "The installation location has not been set";
						}
						EPC0x81Handler.refreshInstallLocation(eSuperObject.this,rsLocation);

						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0x81, EDT: 0x%02X}=={InstallationLocation:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],getInstallLocation()));
						break;
					case x88:
						if(eConverter.dataToInteger(resultData) == 65) {
							EPC0x88Handler.refreshFaultStatus(eSuperObject.this,true);
						} else {
							EPC0x88Handler.refreshFaultStatus(eSuperObject.this,false);
						}
						logger.info(String.format("OBSERVER: Node:%s@EOJ:%s {EPC:0x88, EDT: 0x%02X}=={Fault Status:%s}",
								 getNode().getNodeInfo().toString(),eoj.toString(),resultData.toBytes()[0],isFaultStatus()));
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

	public boolean setCurrentLimitSetting(Node node, EOJ eoj, int limitSetting) {
		boolean rs = false;
		if(getCurrentLimitSetting() == limitSetting) {
			logger.info(String.format("LimitSetting is already set to %d ! nothing to do", limitSetting));
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(node,eoj,EPC.x87, new ObjectData(new Integer(limitSetting).byteValue()))) {
				EPC0x87Handler.refreshCurrentLimitSetting(eSuperObject.this,limitSetting);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
	public boolean setpowerSavingMode(Node node, EOJ eoj) {
		boolean rs = false;
		if(getPowerSaving()) {
			logger.info("It is operating in power-saving mode! nothing to do");
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(node,eoj,EPC.x8F, new ObjectData((byte) 0x41))) {
				EPC0x8FHandler.refreshPowerSaving(eSuperObject.this,true);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}

	public boolean turnOffpowerSavingMode(Node node, EOJ eoj) {
		boolean rs = false;
		if(!getPowerSaving()) {
			logger.info("It is not operating in power-saving mode! nothing to do");
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(node,eoj,EPC.x8F, new ObjectData((byte) 0x42))) {
				EPC0x8FHandler.refreshPowerSaving(eSuperObject.this,false);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}

	public boolean setThroughPublicNetwork(Node node, EOJ eoj) {
		boolean rs = false;
		if(getThroughPublicNetwork()) {
			logger.info("It is accessed via public network! nothing to do");
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(node,eoj,EPC.x93, new ObjectData((byte) 0x42))) {
				EPC0x93Handler.refreshThroughPublicNetwork(eSuperObject.this,true);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}

	public boolean setNotThroughPublicNetwork(Node node, EOJ eoj) {
		boolean rs = false;
		if(!getThroughPublicNetwork()) {
			logger.info("It is not accessed via public network! nothing to do");
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(node,eoj,EPC.x93, new ObjectData((byte) 0x41))) {
				EPC0x93Handler.refreshThroughPublicNetwork(eSuperObject.this,false);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}

	public boolean setPowerLimitSetting(Node node, EOJ eoj,short powerLimitSetting) {
		boolean rs = false;
		if(getPowerLimit() == powerLimitSetting) {
			logger.info(String.format("PowerLimitSetting is already set to %d ! nothing to do", powerLimitSetting));
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(node,eoj,EPC.x99, new ObjectData(new Integer(powerLimitSetting).byteValue()))) {
				EPC0x99Handler.refreshPowerLimit(eSuperObject.this,powerLimitSetting);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
	public boolean setOn(Node node, EOJ eoj) {
		boolean rs = false;
		if(getOperationStatus()) {
			logger.info("it is already ON! nothing to do");
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(node,eoj,EPC.x80, new ObjectData((byte) 0x30))) {
				EPC0x80Handler.refreshOperationStatus(eSuperObject.this,true);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}
	public boolean setOff(Node node, EOJ eoj) {
		boolean rs = false;
		if(!getOperationStatus()) {
			logger.info("it is already OFF! nothing to do");
			rs = true;
		} else {
			if(App.cmdExecutor.executeCommand(node,eoj,EPC.x80, new ObjectData((byte) 0x31))) {
				EPC0x80Handler.refreshOperationStatus(eSuperObject.this,false);
				rs= true;
			} else {
				rs = false;
			}
		}
		return rs;
	}

	public eDeviceType getType() {
		return type;
	}

	public void setType(eDeviceType type) {
		this.type = type;
	}


}
