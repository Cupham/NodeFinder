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
package jaist.ac.jp.NodeFinder.echonet.object.utils;



import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;

public class CodeMapper {
	private static final Logger logger = Logger.getLogger(CodeMapper.class.getName());
	
	public static eDataObject dataObjectFromCode(Node node, EOJ eoj) {
		eDataObject obj = null;
		byte gc =eoj.getClassGroupCode();
		byte cc = eoj.getClassCode();
		switch (gc) {		
			case (byte) (0x00): 
				switch(cc) {
					case (byte) (0x01):
						obj = GroupCode00Mapper.createGasLeakedSensor(node, eoj);
					break;
					case (byte) (0x02):
						obj = GroupCode00Mapper.createCrimePreventionSensor(node, eoj);
					break;
					case (byte) (0x03):
						obj = GroupCode00Mapper.createEmergencyButton(node, eoj);
					break;
					case (byte) (0x04):
						obj = GroupCode00Mapper.createFirstAIDSensor(node, eoj);
					break;
					case (byte) (0x05):
						obj = GroupCode00Mapper.createEarthQuakeSensor(node, eoj);
					break;
					case (byte) (0x06):
						obj = GroupCode00Mapper.createElectricLeakSensor(node, eoj);
					break;
					case (byte) (0x07):
						obj = GroupCode00Mapper.createHumanDetectionSensor(node, eoj);
					break;
					case (byte) (0x08):
						obj = GroupCode00Mapper.createVisitorSensor(node, eoj);
					break;
					case (byte) (0x09):
						obj = GroupCode00Mapper.createCallSensor(node, eoj);
					break;
					case (byte) (0x0A):
						obj = GroupCode00Mapper.createCondensationSensor(node, eoj);
					break;
					case (byte) (0x0B):
						obj = GroupCode00Mapper.createAirPollutionSensor(node, eoj);
					break;
					case (byte) (0x0C):
						obj = GroupCode00Mapper.createOxygenSensor(node, eoj);
					break;
					case (byte) (0x0D):
						obj = GroupCode00Mapper.createIlluminanceSensor(node, eoj);
					break;
					case (byte) (0x0E):
						obj = GroupCode00Mapper.createSoundSensor(node, eoj);
					break;
					case (byte) (0x0F):
						obj = GroupCode00Mapper.createMailingSensor(node, eoj);
					break;
					case (byte) (0x10):
						obj = GroupCode00Mapper.createWeightSensor(node, eoj);
					break;
					case (byte) (0x11):
						obj = GroupCode00Mapper.createTemperatureSensor(node, eoj);
					break;
					case (byte) (0x12):
						obj = GroupCode00Mapper.createHumiditySensor(node, eoj);
					break;
					case (byte) (0x13):
						obj = GroupCode00Mapper.createRainSensor(node, eoj);
					break;
					case (byte) (0x14):
						obj = GroupCode00Mapper.createWaterLevelSensor(node, eoj);
					break;
					case (byte) (0x15):
						obj = GroupCode00Mapper.createBathWaterLevelSensor(node, eoj);
					break;
					case (byte) (0x16):
						obj = GroupCode00Mapper.createBathHeatingStatusSensor(node, eoj);
					break;
					case (byte) (0x17):
						obj = GroupCode00Mapper.createWaterLeakSensor(node, eoj);
					break;
					case (byte) (0x18):
						obj = GroupCode00Mapper.createWaterOverFlowSensor(node, eoj);
					break;
					case (byte) (0x19):
						obj = GroupCode00Mapper.createFireSensor(node, eoj);
					break;
					case (byte) (0x1A):
						obj = GroupCode00Mapper.createCigaretteSmokeSensor(node, eoj);
					break;
					case (byte) (0x1B):
						obj = GroupCode00Mapper.createCO2Sensor(node, eoj);
					break;
					case (byte) (0x1C):
						obj = GroupCode00Mapper.createGasSensor(node, eoj);
					break;
					case (byte) (0x1D):
						obj = GroupCode00Mapper.createVOCSensor(node, eoj);
					break;
					case (byte) (0x1E):
						obj = GroupCode00Mapper.createDifferentialPressureSensor(node, eoj);
					break;
					case (byte) (0x1F):
						obj = GroupCode00Mapper.createAirSpeedSensor(node, eoj);
					break;
					case (byte) (0x20):
						obj = GroupCode00Mapper.createOdorSensor(node, eoj);
					break;
					case (byte) (0x21):
						obj = GroupCode00Mapper.createFlameSensor(node, eoj);
					break;
					case (byte) (0x22):
						obj = GroupCode00Mapper.createElectricEnergySensor(node, eoj);
					break;
					case (byte) (0x23):
						obj = GroupCode00Mapper.createCurrentValueSensor(node, eoj);
					break;
					case (byte) (0x24):
						obj = GroupCode00Mapper.createDaylightSensor(node, eoj);
					break;
					case (byte) (0x25):
						obj = GroupCode00Mapper.createWaterFlowRateSensor(node, eoj);
					break;
					case (byte) (0x26):
						obj = GroupCode00Mapper.createMicroMotionSensor(node, eoj);
					break;
					case (byte) (0x27):
						obj = GroupCode00Mapper.createPassageSensor(node, eoj);
					break;
					case (byte) (0x28):
						obj = GroupCode00Mapper.createBedPresenceSensor(node, eoj);
					break;
					case (byte) (0x29):
						obj = GroupCode00Mapper.createOpen_CloseSensor(node, eoj);
					break;
					case (byte) (0x2A):
						obj = GroupCode00Mapper.createActivityMountSensor(node, eoj);
					break;
					case (byte) (0x2B):
						obj = GroupCode00Mapper.createHumanBodyLocationSensor(node, eoj);
					break;
					case (byte) (0x2C):
						obj = GroupCode00Mapper.createSnowSensor(node, eoj);
					break;
					case (byte) (0x2D):
						obj = GroupCode00Mapper.createAirPressureSensor(node, eoj);
					break;
					default:
						obj = null;
					break;
				}
			break;
			case (byte) (0x01):
			{
				switch(cc) {
					case (byte) (0x30):
						obj = GroupCode01Mapper.createHomeAirConditioner(node, eoj);
					break;
					case (byte) (0x31):
						obj = GroupCode01Mapper.createColdBlaster(node, eoj);
					break;
					case (byte) (0x32):
						obj = GroupCode01Mapper.createElectricFan(node, eoj);
					break;
					case (byte) (0x33):
						obj = GroupCode01Mapper.createVentilationFan(node, eoj);
					break;
					case (byte) (0x34):
						obj = GroupCode01Mapper.createAirconWithVentilationFan(node, eoj);
					break;
					case (byte) (0x35):
						obj = GroupCode01Mapper.createAirCleaner(node, eoj);
					break;
					case (byte) (0x36):
						obj = GroupCode01Mapper.createColdBlastFan(node, eoj);
					break;
					case (byte) (0x37):
						obj = GroupCode01Mapper.createCirculator(node, eoj);
					break;
					case (byte) (0x38):
						obj = GroupCode01Mapper.createDehumidifier(node, eoj);
					break;
					case (byte) (0x39):
						obj = GroupCode01Mapper.createHumidifier(node, eoj);
					break;
					case (byte) (0x40):
						obj = GroupCode01Mapper.createElectricCarpet(node, eoj);
					break;
					case (byte) (0x41):
						obj = GroupCode01Mapper.createFloorHeater(node, eoj);
					break;
					case (byte) (0x42):
						obj = GroupCode01Mapper.createElectricHeater(node, eoj);
					break;
					case (byte) (0x43):
						obj = GroupCode01Mapper.createFanHeater(node, eoj);
					break;
					case (byte) (0x44):
						obj = GroupCode01Mapper.createBatteryCharger(node, eoj);
					break;
					case (byte) (0x47):
						obj = GroupCode01Mapper.createCommercialAirconThermalStorageUnit(node, eoj);
					break;
					case (byte) (0x48):
						obj = GroupCode01Mapper.createCommercialFanCoilUnit(node, eoj);
					break;
					case (byte) (0x49):
						obj = GroupCode01Mapper.createCommercialAirConColdSource(node, eoj);
					break;
					case (byte) (0x50):
						obj = GroupCode01Mapper.createCommercialAirConHotSource(node, eoj);
					break;
					case (byte) (0x51):
						obj = GroupCode01Mapper.createCommercialAirConVAV(node, eoj);
					break;
					case (byte) (0x52):
						obj = GroupCode01Mapper.createCommercialAirHandlingUnit(node, eoj);
					break;
					case (byte) (0x53):
						obj = GroupCode01Mapper.createUnitCooler(node, eoj);
					break;
					case (byte) (0x54):
						obj = GroupCode01Mapper.createCommercialCondensingUnit(node, eoj);
					break;
					case (byte) (0x55):
						obj = GroupCode01Mapper.createElectricStorageHeater(node, eoj);
					break;
					case (byte) (0x56):
						obj = GroupCode01Mapper.createCommercialAirConIndoorUnit(node, eoj);
					break;
					case (byte) (0x57):
						obj = GroupCode01Mapper.createCommercialAirConOutdoorUnit(node, eoj);
					break;
					case (byte) (0x58):
						obj = GroupCode01Mapper.createCommercialGasHeatAirConIndoorUnit(node, eoj);
					break;
					case (byte) (0x59):
						obj = GroupCode01Mapper.createCommercialGasHeatAirConOutdoorUnit(node, eoj);
					break;
					default:
						obj = null;
					break;
				}
			}
			break;
			case (byte) (0x02):
			{
				switch(cc) {
					case (byte) (0x60):
						obj = GroupCode02Mapper.createElectricBlind(node, eoj);
					break;
					case (byte) (0x61):
						obj = GroupCode02Mapper.createElectricShutter(node, eoj);
					break;
					case (byte) (0x62):
						obj = GroupCode02Mapper.createElectricCurtain(node, eoj);
					break;
					case (byte) (0x63):
						obj = GroupCode02Mapper.createElectricRainSlidingDoor(node, eoj);
					break;
					case (byte) (0x64):
						obj = GroupCode02Mapper.createElectricGate(node, eoj);
					break;
					case (byte) (0x65):
						obj = GroupCode02Mapper.createElectricWindow(node, eoj);
					break;
					case (byte) (0x66):
						obj = GroupCode02Mapper.createElectricEntranceDoor(node, eoj);
					break;
					case (byte) (0x67):
						obj = GroupCode02Mapper.createGardenSprinkler(node, eoj);
					break;
					case (byte) (0x68):
						obj = GroupCode02Mapper.createFireSprinkler(node, eoj);
					break;
					case (byte) (0x69):
						obj = GroupCode02Mapper.createFountain(node, eoj);
					break;
					case (byte) (0x6A):
						obj = GroupCode02Mapper.createInstantaneousWaterHeater(node, eoj);
					break;
					case (byte) (0x6B):
						obj = GroupCode02Mapper.createElectricWaterHeater(node, eoj);
					break;
					case (byte) (0x6C):
						obj = GroupCode02Mapper.createSolarWaterHeater(node, eoj);
					break;
					case (byte) (0x6D):
						obj = GroupCode02Mapper.createCirculationPump(node, eoj);
					break;
					case (byte) (0x6E):
						obj = GroupCode02Mapper.createBidetEquippedToilet(node, eoj);
					break;
					case (byte) (0x6F):
						obj = GroupCode02Mapper.createElectricLock(node, eoj);
					break;
					case (byte) (0x70):
						obj = GroupCode02Mapper.createGasLineValve(node, eoj);
					break;
					case (byte) (0x71):
						obj = GroupCode02Mapper.createHomeSauna(node, eoj);
					break;
					case (byte) (0x72):
						obj = GroupCode02Mapper.createHotWaterGenerator(node, eoj);
					break;
					case (byte) (0x73):
						obj = GroupCode02Mapper.createBathRoomDryer(node, eoj);
					break;
					case (byte) (0x74):
						obj = GroupCode02Mapper.createHomeElevator(node, eoj);
					break;
					case (byte) (0x75):
						obj = GroupCode02Mapper.createElectricRoomDivider(node, eoj);
					break;
					case (byte) (0x76):
						obj = GroupCode02Mapper.createHorizontalTransfer(node, eoj);
					break;
					case (byte) (0x77):
						obj = GroupCode02Mapper.createElectricallyClothesDryingPole(node, eoj);
					break;
					case (byte) (0x78):
						obj = GroupCode02Mapper.createSepticTank(node, eoj);
					break;
					case (byte) (0x79):
						obj = GroupCode02Mapper.createHomeSolarPowerGeneration(node, eoj);
					break;
					case (byte) (0x7A):
						obj = GroupCode02Mapper.createColdHotWaterHeatSourceEquipment(node, eoj);
					break;
					case (byte) (0x7B):
						obj = GroupCode02Mapper.createFloorHeater(node, eoj);
					break;
					case (byte) (0x7C):
						obj = GroupCode02Mapper.createFuelCell(node, eoj);
					break;
					case (byte) (0x7D):
						obj = GroupCode02Mapper.createStorageBattery(node, eoj);
					break;
					case (byte) (0x7E):
						obj = GroupCode02Mapper.createChargerForElectricVehicle(node, eoj);
					break;
					case (byte) (0x7F):
						obj = GroupCode02Mapper.createEngineCogeneration(node, eoj);
					break;
					case (byte) (0x80):
						obj = GroupCode02Mapper.createElectricEnergyMeter(node, eoj);
					break;
					case (byte) (0x81):
						obj = GroupCode02Mapper.createWaterFlowMeter(node, eoj);
					break;
					case (byte) (0x82):
						obj = GroupCode02Mapper.createGasMeter(node, eoj);
					break;
					case (byte) (0x83):
						obj = GroupCode02Mapper.createLPGasMeter(node, eoj);
					break;
					case (byte) (0x84):
						obj = GroupCode02Mapper.createClock(node, eoj);
					break;
					case (byte) (0x85):
						obj = GroupCode02Mapper.createAutomaticDoor(node, eoj);
					break;
					case (byte) (0x86):
						obj = GroupCode02Mapper.createCommercialElevator(node, eoj);
					break;
					case (byte) (0x87):
						obj = GroupCode02Mapper.createDistributionPanelMetering(node, eoj);
					break;
					case (byte) (0x88):
						obj = GroupCode02Mapper.createLowVoltageSmartElectricEnergyMeter(node, eoj);
					break;
					case (byte) (0x89):
						obj = GroupCode02Mapper.createSmartGasMeter(node, eoj);
					break;
					case (byte) (0x8A):
						obj = GroupCode02Mapper.createHighVoltageSmartElectricEnergyMeter(node, eoj);
					break;
					case (byte) (0x8B):
						obj = GroupCode02Mapper.createKeroseneMeter(node, eoj);
					break;
					case (byte) (0x8C):
						obj = GroupCode02Mapper.createSmartKeroseneMeter(node, eoj);
					break;
					case (byte) (0x90):
						obj = GroupCode02Mapper.createGeneralLightingFamily(node, eoj);
					break;
					case (byte) (0x91):
						obj = GroupCode02Mapper.createSingleFunctionLighting(node, eoj);
					break;
					case (byte) (0x92):
						obj = GroupCode02Mapper.createLightingForSolidLightEmittingSource(node, eoj);
					break;
					case (byte) (0x99):
						obj = GroupCode02Mapper.createEmergencyLighting(node, eoj);
					break;
					case (byte) (0x9D):
						obj = GroupCode02Mapper.createEquipmentLight(node, eoj);
					break;	
					case (byte) (0xA0):
						obj = GroupCode02Mapper.createBuzzer(node, eoj);
					break;
					case (byte) (0xA1):
						obj = GroupCode02Mapper.createChargerForElectricVehicle(node, eoj);
					break;
					case (byte) (0xA2):
						obj = GroupCode02Mapper.createHouseholdSmallWindTurbinePowerGeneration(node, eoj);
					break;
					case (byte) (0xA3):
						obj = GroupCode02Mapper.createLightingSystem(node, eoj);
					break;
					case (byte) (0xA4):
						obj = GroupCode02Mapper.createExtendedLightingSystem(node, eoj);
					break;
					case (byte) (0xA5):
						obj = GroupCode02Mapper.createMultipleInputPCS(node, eoj);
					break;
					default:
						obj = null;
					break;		
				}
			}
			break;
			case (byte) (0x03):
			{
				switch(cc) {
					case (byte) (0xB0):
						obj = GroupCode03Mapper.createCoffeeMachine(node, eoj);
					break;
					case (byte) (0xB1):
						obj = GroupCode03Mapper.createCoffeeMill(node, eoj);
					break;
					case (byte) (0xB2):
						obj = GroupCode03Mapper.createElectricHotWaterPot(node, eoj);
					break;
					case (byte) (0xB3):
						obj = GroupCode03Mapper.createElectricStove(node, eoj);
					break;
					case (byte) (0xB4):
						obj = GroupCode03Mapper.createToaster(node, eoj);
					break;
					case (byte) (0xB5):
						obj = GroupCode03Mapper.createJuicerFoodMixer(node, eoj);
					break;
					case (byte) (0xB6):
						obj = GroupCode03Mapper.createFoodProcessor(node, eoj);
					break;
					case (byte) (0xB7):
						obj = GroupCode03Mapper.createRefrigerator(node, eoj);
					break;
					case (byte) (0xB8):
						obj = GroupCode03Mapper.createCombinationMicrowaveOven(node, eoj);
					break;
					case (byte) (0xB9):
						obj = GroupCode03Mapper.createCookingHeater(node, eoj);
					break;
					case (byte) (0xBA):
						obj = GroupCode03Mapper.createOven(node, eoj);
					break;
					case (byte) (0xBB):
						obj = GroupCode03Mapper.createRiceCooker(node, eoj);
					break;
					case (byte) (0xBC):
						obj = GroupCode03Mapper.createElectronicJar(node, eoj);
					break;
					case (byte) (0xBD):
						obj = GroupCode03Mapper.createDishWasher(node, eoj);
					break;
					case (byte) (0xBE):
						obj = GroupCode03Mapper.createDishDryer(node, eoj);
					break;
					case (byte) (0xBF):
						obj = GroupCode03Mapper.createRiceCardCooker(node, eoj);
					break;
					case (byte) (0xC0):
						obj = GroupCode03Mapper.createKeepWarmMachine(node, eoj);
					break;
					case (byte) (0xC1):
						obj = GroupCode03Mapper.createRiceMill(node, eoj);
					break;
					case (byte) (0xC2):
						obj = GroupCode03Mapper.createBreadCooker(node, eoj);
					break;
					case (byte) (0xC3):
						obj = GroupCode03Mapper.createSlowCooker(node, eoj);
					break;
					case (byte) (0xC4):
						obj = GroupCode03Mapper.createPicklesCooker(node, eoj);
					break;
					case (byte) (0xC5):
						obj = GroupCode03Mapper.createWashingMachine(node, eoj);
					break;
					case (byte) (0xC6):
						obj = GroupCode03Mapper.createClothesDryer(node, eoj);
					break;
					case (byte) (0xC7):
						obj = GroupCode03Mapper.createElectricIron(node, eoj);
					break;	
					case (byte) (0xC8):
						obj = GroupCode03Mapper.createTrouserPress(node, eoj);
					break;
					case (byte) (0xC9):
						obj = GroupCode03Mapper.createFutonDryer(node, eoj);
					break;
					case (byte) (0xCA):
						obj = GroupCode03Mapper.createShoesDryer(node, eoj);
					break;
					case (byte) (0xCB):
						obj = GroupCode03Mapper.createVacuumCleaner(node, eoj);
					break;
					case (byte) (0xCC):
						obj = GroupCode03Mapper.createDisposer(node, eoj);
					break;
					case (byte) (0xCD):
						obj = GroupCode03Mapper.createMosquitoCatcher(node, eoj);
					break;
					case (byte) (0xCE):
						obj = GroupCode03Mapper.createCommercialShowCase(node, eoj);
					break;
					case (byte) (0xCF):
						obj = GroupCode03Mapper.createCommercialRefrigerator(node, eoj);
					break;
					case (byte) (0xD0):
						obj = GroupCode03Mapper.createCommerciaHotCase(node, eoj);
					break;
					case (byte) (0xD1):
						obj = GroupCode03Mapper.createCommercialFryer(node, eoj);
					break;
					case (byte) (0xD2):
						obj = GroupCode03Mapper.createCommercialMicrowaveOven(node, eoj);
					break;
					case (byte) (0xD3):
						obj = GroupCode03Mapper.createWasherAndDryer(node, eoj);
					break;
					case (byte) (0xD4):
						obj = GroupCode03Mapper.createCommerciaShowCaseOutdoorUnit(node, eoj);
					break;
					default:
						obj = null;
					break;
				}
			}
			break;
			case (byte) (0x04):
			{
				switch(cc) {
					case (byte) (0x01):
						obj = GroupCode04Mapper.createWeighingMachine(node, eoj);
					break;
					case (byte) (0x02):
						obj = GroupCode04Mapper.createClinicalThermoMeter(node, eoj);
					break;
					case (byte) (0x03):
						obj = GroupCode04Mapper.createBloodPressuremeter(node, eoj);
					break;
					case (byte) (0x04):
						obj = GroupCode04Mapper.createBloodSugarmeter(node, eoj);
					break;
					case (byte) (0x05):
						obj = GroupCode04Mapper.createBodyFatmeter(node, eoj);
					break;
					default:
						obj = null;
					break;
				}
			}
			break;
			case (byte) (0x05):
			{
				switch(cc) {
					case (byte) (0xFA):
						obj = GroupCode05Mapper.createParallelProcessingCombinationTypePowerControl(node, eoj);
					break;
					case (byte) (0xFB):
						obj = GroupCode05Mapper.createDREventController(node, eoj);
					break;
					case (byte) (0xFC):
						obj = GroupCode05Mapper.createSecureCommunicationSharedKeySetupNode(node, eoj);
					break;
					case (byte) (0xFD):
						obj = GroupCode05Mapper.createSwitch(node, eoj);
					break;
					case (byte) (0xFE):
						obj = GroupCode05Mapper.createPortableTerminal(node, eoj);
					break;
					case (byte) (0xFF):
						obj = GroupCode05Mapper.createController(node, eoj);
					break;
					default:
						obj = null;
					break;
				}
			}
			break;
			case (byte) (0x06):
			{
				switch(cc) {
					case (byte) (0x01):
						obj = GroupCode06Mapper.createDisplay(node, eoj);
					break;
					case (byte) (0x02):
						obj = GroupCode06Mapper.createTelevision(node, eoj);
					break;
					case (byte) (0x03):
						obj = GroupCode06Mapper.createAudio(node, eoj);
					break;
					case (byte) (0x04):
						obj = GroupCode06Mapper.createNetworkCamera(node, eoj);
					break;
					default:
						obj = null;
					break;
				}
			}
			break;
			default:
				logger.log(Level.WARNING, "There is no specification for this GroupCode and Class Code");
				obj = null;
			break;
		}
		return obj;
	}
}
