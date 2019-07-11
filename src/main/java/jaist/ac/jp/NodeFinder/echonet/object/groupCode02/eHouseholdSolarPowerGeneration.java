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
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.FITContractType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.OutputPowerRestraint;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.SelfConsumptionType;
import jaist.ac.jp.NodeFinder.echonet.object.utils.eConverter.SystemInterconnectedType;

public class eHouseholdSolarPowerGeneration extends eDataObject{
	private static final Logger logger = Logger.getLogger(eHouseholdSolarPowerGeneration.class.getName());
	private Timer timer;
	private int outputPowerControlSetting1;
	private int outputPowerControlSetting2;
	private boolean purchaseSurplusElectricitySetting;
	private FITContractType FITContractType;;
	private SelfConsumptionType selfConsumptionType;
	private int capacityApprovedByEquipment;
	private int conversionCoefficient;
	private SystemInterconnectedType systemInterconnectedType;
	private OutputPowerRestraint outputPowerRestraintStatus;
	private int instantaneousEletrictAmountGenerated;
	private float cumulativeEletrictAmountGenerated;
	private int ratedPowerGenerationOutput;
	
	public eHouseholdSolarPowerGeneration(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x02);
		this.setClassCode((byte) 0x79);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(eDeviceType.HomeSolarPowerGeneration);
	}
	
	// Provided Services	
	// Device Property Monitoring
	public void	refreshRatedPowerGenerationOutput(int val) {
		if(val != getRatedPowerGenerationOutput()) {
			logger.info(String.format("ratedPowerGenerationOutput changed from %d to %d W",getRatedPowerGenerationOutput(),val));
			setRatedPowerGenerationOutput(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshCumulativeEletrictAmountGenerated(float val) {
		if(val != getCumulativeEletrictAmountGenerated()) {
			logger.info(String.format("CumulativeEletrictAmountGenerated changed from %f to %f kWh",getCumulativeEletrictAmountGenerated(),val));
			setCumulativeEletrictAmountGenerated(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshInstantaneousEletrictAmountGenerated(int val) {
		if(val != getInstantaneousEletrictAmountGenerated()) {
			logger.info(String.format("instantaneousEletrictAmountGenerated changed from %d to %d W",getInstantaneousEletrictAmountGenerated(),val));
			setInstantaneousEletrictAmountGenerated(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshOutputPowerRestraint(OutputPowerRestraint val) {
		if(val != getOutputPowerRestraintStatus()) {
			logger.info(String.format("OutputPowerRestraint changed from %s to %s",getOutputPowerRestraintStatus(),val));
			setOutputPowerRestraintStatus(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshSystemInterconnectedType(SystemInterconnectedType val) {
		if(val != getSystemInterconnectedType()) {
			logger.info(String.format("SystemInterconnectedType changed from %s to %s",getSystemInterconnectedType(),val));
			setSystemInterconnectedType(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshConversionCoefficient(int val) {
		if(val != getConversionCoefficient()) {
			logger.info(String.format("conversionCoefficient changed from %d to %d",getConversionCoefficient(),val));
			setConversionCoefficient(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshCapacityApprovedByEquipment(int val) {
		if(val != getCapacityApprovedByEquipment()) {
			logger.info(String.format("capacityApprovedByEquipment changed from %d to %d",getCapacityApprovedByEquipment(),val));
			setCapacityApprovedByEquipment(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshSelfConsumptionType(SelfConsumptionType val) {
		if(val != getSelfConsumptionType()) {
			logger.info(String.format("selfConsumptionType changed from %s to %s",getSelfConsumptionType(),val));
			setSelfConsumptionType(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshFITContractType(FITContractType val) {
		if(val != getFITContractType()) {
			logger.info(String.format("FITContractType changed from %s to %s",getFITContractType(),val));
			setFITContractType(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshPurchaseSurplusElectricitySetting(boolean val) {
		if(val != isPurchaseSurplusElectricitySetting()) {
			logger.info(String.format("PurchaseSurplusElectricitySetting changed from %s to %s",isPurchaseSurplusElectricitySetting(),val));
			setPurchaseSurplusElectricitySetting(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshOutputPowerControlSetting2(int val) {
		if(val != getOutputPowerControlSetting2()) {
			logger.info(String.format("OutputPowerControlSetting2 changed from %d to %d W",getOutputPowerControlSetting2(),val));

			setOutputPowerControlSetting2(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshOutputPowerControlSetting1(int val) {
		if(val != getOutputPowerControlSetting1()) {
			logger.info(String.format("OutputPowerControlSetting1 changed from %d to %d percent",getOutputPowerControlSetting1(),val));

			setOutputPowerControlSetting1(val);
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
		epcs.add(EPC.xA0);
		epcs.add(EPC.xA1);
		epcs.add(EPC.xA2);
		epcs.add(EPC.xE3);
		epcs.add(EPC.xC1);
		epcs.add(EPC.xC2);
		epcs.add(EPC.xC3);
		epcs.add(EPC.xC4);
		epcs.add(EPC.xD0);
		epcs.add(EPC.xD1);
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE1);
		epcs.add(EPC.xE8);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xA0:
							refreshOutputPowerControlSetting1(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xA0, EDT: 0x%02X}=={OutputPowerControlSetting1:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOutputPowerControlSetting1()));
						break;
						case xA1:
							refreshOutputPowerControlSetting2(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xA1, EDT: 0x%02X}=={OutputPowerControlSetting2:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOutputPowerControlSetting2()));
						break;
						case xA2:
							if(eConverter.dataToInteger(resultData) == 65) {
								refreshPurchaseSurplusElectricitySetting(true);
							} else {
								refreshPurchaseSurplusElectricitySetting(false);
							}
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD0, EDT: 0x%02X}=={PurchaseSurplusElectricitySetting:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],isPurchaseSurplusElectricitySetting()));
						break;
						case xC1:
							refreshFITContractType(eConverter.FITContractType.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC1, EDT: 0x%02X}=={FITContractType:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getFITContractType()));
						break;
						case xC2:
							refreshSelfConsumptionType(eConverter.SelfConsumptionType.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC2, EDT: 0x%02X}=={SelfConsumptionType:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSelfConsumptionType()));
						break;
						case xC3:
							refreshCapacityApprovedByEquipment(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC3, EDT: 0x%02X}=={CapacityApprovedByEquipment:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getCapacityApprovedByEquipment()));
						break;
						case xC4:
							refreshConversionCoefficient(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xC4, EDT: 0x%02X}=={ConversionCoefficient:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getConversionCoefficient()));
						break;
						case xD0:
							refreshSystemInterconnectedType(eConverter.SystemInterconnectedType.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD0, EDT: 0x%02X}=={SystemInterconnectedType:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSystemInterconnectedType()));
						break;
						case xD1:
							refreshOutputPowerRestraint(eConverter.OutputPowerRestraint.fromCode(resultData.toBytes()[0]));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xD1, EDT: 0x%02X}=={OutputPowerRestraint:%s}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getOutputPowerRestraintStatus()));
						break;
						case xE0:
							refreshInstantaneousEletrictAmountGenerated(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={InstantaneousEletrictAmountGenerated:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getInstantaneousEletrictAmountGenerated()));
						break;
						case xE1:
							refreshCumulativeEletrictAmountGenerated(eConverter.dataToLong(resultData)*0.001f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={InstantaneousEletrictAmountGenerated:%f}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getCumulativeEletrictAmountGenerated()));
						break;
						case xE8:
							refreshRatedPowerGenerationOutput(eConverter.dataToInteger(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE8, EDT: 0x%02X}=={RatedPowerGenerationOutput:%d}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getRatedPowerGenerationOutput()));
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

	public int getOutputPowerControlSetting1() {
		return outputPowerControlSetting1;
	}

	public void setOutputPowerControlSetting1(int outputPowerControlSetting1) {
		this.outputPowerControlSetting1 = outputPowerControlSetting1;
	}

	public int getOutputPowerControlSetting2() {
		return outputPowerControlSetting2;
	}

	public void setOutputPowerControlSetting2(int outputPowerControlSetting2) {
		this.outputPowerControlSetting2 = outputPowerControlSetting2;
	}

	public boolean isPurchaseSurplusElectricitySetting() {
		return purchaseSurplusElectricitySetting;
	}

	public void setPurchaseSurplusElectricitySetting(boolean purchaseSurplusElectricitySetting) {
		this.purchaseSurplusElectricitySetting = purchaseSurplusElectricitySetting;
	}

	public FITContractType getFITContractType() {
		return FITContractType;
	}

	public void setFITContractType(FITContractType fITContractType) {
		FITContractType = fITContractType;
	}

	public SelfConsumptionType getSelfConsumptionType() {
		return selfConsumptionType;
	}

	public void setSelfConsumptionType(SelfConsumptionType selfConsumptionType) {
		this.selfConsumptionType = selfConsumptionType;
	}

	public int getCapacityApprovedByEquipment() {
		return capacityApprovedByEquipment;
	}

	public void setCapacityApprovedByEquipment(int capacityApprovedByEquipment) {
		this.capacityApprovedByEquipment = capacityApprovedByEquipment;
	}

	public int getConversionCoefficient() {
		return conversionCoefficient;
	}

	public void setConversionCoefficient(int conversionCoefficient) {
		this.conversionCoefficient = conversionCoefficient;
	}

	public SystemInterconnectedType getSystemInterconnectedType() {
		return systemInterconnectedType;
	}

	public void setSystemInterconnectedType(SystemInterconnectedType systemInterconnectedType) {
		this.systemInterconnectedType = systemInterconnectedType;
	}

	public OutputPowerRestraint getOutputPowerRestraintStatus() {
		return outputPowerRestraintStatus;
	}

	public void setOutputPowerRestraintStatus(OutputPowerRestraint outputPowerRestraintStatus) {
		this.outputPowerRestraintStatus = outputPowerRestraintStatus;
	}

	public int getInstantaneousEletrictAmountGenerated() {
		return instantaneousEletrictAmountGenerated;
	}

	public void setInstantaneousEletrictAmountGenerated(int instantaneousEletrictAmountGenerated) {
		this.instantaneousEletrictAmountGenerated = instantaneousEletrictAmountGenerated;
	}

	public float getCumulativeEletrictAmountGenerated() {
		return cumulativeEletrictAmountGenerated;
	}

	public void setCumulativeEletrictAmountGenerated(float cumulativeEletrictAmountGenerated) {
		this.cumulativeEletrictAmountGenerated = cumulativeEletrictAmountGenerated;
	}

	public int getRatedPowerGenerationOutput() {
		return ratedPowerGenerationOutput;
	}

	public void setRatedPowerGenerationOutput(int ratedPowerGenerationOutput) {
		this.ratedPowerGenerationOutput = ratedPowerGenerationOutput;
	}	
}
