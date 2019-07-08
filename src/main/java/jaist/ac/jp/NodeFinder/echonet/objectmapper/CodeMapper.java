package jaist.ac.jp.NodeFinder.echonet.objectmapper;



import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;

public class CodeMapper {
	private static final Logger logger = Logger.getLogger(CodeMapper.class.getName());
	
	public static eDataObject dataObjectFromCode(byte gc ,byte cc, Node node, EOJ eoj) {
		eDataObject obj = null;
		switch (gc) {		
			case (byte) (0x00): 
				switch(cc) {
					case (byte) (0x01):
						obj = SensorMapper.createGasLeakedSensor(node, eoj);
					break;
					case (byte) (0x02):
						obj = SensorMapper.createCrimePreventionSensor(node, eoj);
					break;
					case (byte) (0x03):
						obj = SensorMapper.createEmergencyButton(node, eoj);
					break;
					case (byte) (0x04):
						obj = SensorMapper.createFirstAIDSensor(node, eoj);
					break;
					case (byte) (0x05):
						obj = SensorMapper.createEarthQuakeSensor(node, eoj);
					break;
					case (byte) (0x06):
						obj = SensorMapper.createElectricLeakSensor(node, eoj);
					break;
					case (byte) (0x07):
						obj = SensorMapper.createHumanDetectionSensor(node, eoj);
					break;
					case (byte) (0x08):
						obj = SensorMapper.createVisitorSensor(node, eoj);
					break;
					case (byte) (0x09):
						obj = SensorMapper.createCallSensor(node, eoj);
					break;
					case (byte) (0x0A):
						obj = SensorMapper.createCondensationSensor(node, eoj);
					break;
					case (byte) (0x0B):
						obj = SensorMapper.createAirPollutionSensor(node, eoj);
					break;
					case (byte) (0x0C):
						obj = SensorMapper.createOxygenSensor(node, eoj);
					break;
					case (byte) (0x0D):
						obj = SensorMapper.createIlluminanceSensor(node, eoj);
					break;
					case (byte) (0x0E):
						obj = SensorMapper.createSoundSensor(node, eoj);
					break;
					case (byte) (0x0F):
						obj = SensorMapper.createMailingSensor(node, eoj);
					break;
					case (byte) (0x10):
						obj = SensorMapper.createWeightSensor(node, eoj);
					break;
					case (byte) (0x11):
						obj = SensorMapper.createTemperatureSensor(node, eoj);
					break;
					case (byte) (0x12):
						obj = SensorMapper.createHumiditySensor(node, eoj);
					break;
					case (byte) (0x13):
						obj = SensorMapper.createRainSensor(node, eoj);
					break;
					case (byte) (0x14):
						obj = SensorMapper.createWaterLevelSensor(node, eoj);
					break;
					case (byte) (0x15):
						obj = SensorMapper.createBathWaterLevelSensor(node, eoj);
					break;
					case (byte) (0x16):
						obj = SensorMapper.createBathHeatingStatusSensor(node, eoj);
					break;
					case (byte) (0x17):
						obj = SensorMapper.createWaterLeakSensor(node, eoj);
					break;
					case (byte) (0x18):
						obj = SensorMapper.createWaterOverFlowSensor(node, eoj);
					break;
					case (byte) (0x19):
						obj = SensorMapper.createFireSensor(node, eoj);
					break;
					case (byte) (0x1A):
						obj = SensorMapper.createCigaretteSmokeSensor(node, eoj);
					break;
					case (byte) (0x1B):
						obj = SensorMapper.createCO2Sensor(node, eoj);
					break;
					case (byte) (0x1C):
						obj = SensorMapper.createGasSensor(node, eoj);
					break;
					case (byte) (0x1D):
						obj = SensorMapper.createVOCSensor(node, eoj);
					break;
					case (byte) (0x1E):
						obj = SensorMapper.createDifferentialPressureSensor(node, eoj);
					break;
					case (byte) (0x1F):
						obj = SensorMapper.createAirSpeedSensor(node, eoj);
					break;
					case (byte) (0x20):
						obj = SensorMapper.createOdorSensor(node, eoj);
					break;
					case (byte) (0x21):
						obj = SensorMapper.createFlameSensor(node, eoj);
					break;
					case (byte) (0x22):
						obj = SensorMapper.createElectricEnergySensor(node, eoj);
					break;
					case (byte) (0x23):
						obj = SensorMapper.createCurrentValueSensor(node, eoj);
					break;
					case (byte) (0x24):
						obj = SensorMapper.createDaylightSensor(node, eoj);
					break;
					case (byte) (0x25):
						obj = SensorMapper.createWaterFlowRateSensor(node, eoj);
					break;
					case (byte) (0x26):
						obj = SensorMapper.createMicroMotionSensor(node, eoj);
					break;
					case (byte) (0x27):
						obj = SensorMapper.createPassageSensor(node, eoj);
					break;
					case (byte) (0x28):
						obj = SensorMapper.createBedPresenceSensor(node, eoj);
					break;
					case (byte) (0x29):
						obj = SensorMapper.createOpen_CloseSensor(node, eoj);
					break;
					case (byte) (0x2A):
						obj = SensorMapper.createActivityMountSensor(node, eoj);
					break;
					case (byte) (0x2B):
						obj = SensorMapper.createHumanBodyLocationSensor(node, eoj);
					break;
					case (byte) (0x2C):
						obj = SensorMapper.createSnowSensor(node, eoj);
					break;
					case (byte) (0x2D):
						obj = SensorMapper.createAirPressureSensor(node, eoj);
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
						obj = AirConditionerMapper.createHomeAirConditioner(node, eoj);
					break;
					case (byte) (0x31):
						obj = AirConditionerMapper.createColdBlaster(node, eoj);
					break;
					case (byte) (0x32):
						obj = AirConditionerMapper.createElectricFan(node, eoj);
					break;
					case (byte) (0x33):
						obj = AirConditionerMapper.createVentilationFan(node, eoj);
					break;
					case (byte) (0x34):
						obj = AirConditionerMapper.createAirconWithVentilationFan(node, eoj);
					break;
					case (byte) (0x35):
						obj = AirConditionerMapper.createAirCleaner(node, eoj);
					break;
					case (byte) (0x36):
						obj = AirConditionerMapper.createColdBlastFan(node, eoj);
					break;
					case (byte) (0x37):
						obj = AirConditionerMapper.createCirculator(node, eoj);
					break;
					case (byte) (0x38):
						obj = AirConditionerMapper.createDehumidifier(node, eoj);
					break;
					case (byte) (0x39):
						obj = AirConditionerMapper.createHumidifier(node, eoj);
					break;
					case (byte) (0x40):
						obj = AirConditionerMapper.createElectricCarpet(node, eoj);
					break;
					case (byte) (0x41):
						obj = AirConditionerMapper.createFloorHeater(node, eoj);
					break;
					case (byte) (0x42):
						obj = AirConditionerMapper.createElectricHeater(node, eoj);
					break;
					case (byte) (0x43):
						obj = AirConditionerMapper.createFanHeater(node, eoj);
					break;
					case (byte) (0x44):
						obj = AirConditionerMapper.createBatteryCharger(node, eoj);
					break;
					case (byte) (0x47):
						obj = AirConditionerMapper.createCommercialAirconThermalStorageUnit(node, eoj);
					break;
					case (byte) (0x48):
						obj = AirConditionerMapper.createCommercialFanCoilUnit(node, eoj);
					break;
					case (byte) (0x49):
						obj = AirConditionerMapper.createCommercialAirConColdSource(node, eoj);
					break;
					case (byte) (0x50):
						obj = AirConditionerMapper.createCommercialAirConHotSource(node, eoj);
					break;
					case (byte) (0x51):
						obj = AirConditionerMapper.createCommercialAirConVAV(node, eoj);
					break;
					case (byte) (0x52):
						obj = AirConditionerMapper.createCommercialAirHandlingUnit(node, eoj);
					break;
					case (byte) (0x53):
						obj = AirConditionerMapper.createUnitCooler(node, eoj);
					break;
					case (byte) (0x54):
						obj = AirConditionerMapper.createCommercialCondensingUnit(node, eoj);
					break;
					case (byte) (0x55):
						obj = AirConditionerMapper.createElectricStorageHeater(node, eoj);
					break;
					case (byte) (0x56):
						obj = AirConditionerMapper.createCommercialAirConIndoorUnit(node, eoj);
					break;
					case (byte) (0x57):
						obj = AirConditionerMapper.createCommercialAirConOutdoorUnit(node, eoj);
					break;
					case (byte) (0x58):
						obj = AirConditionerMapper.createCommercialGasHeatAirConIndoorUnit(node, eoj);
					break;
					case (byte) (0x59):
						obj = AirConditionerMapper.createCommercialGasHeatAirConOutdoorUnit(node, eoj);
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
						obj = HousingDeviceMapper.createElectricBlind(node, eoj);
					break;
					case (byte) (0x61):
						obj = HousingDeviceMapper.createElectricShutter(node, eoj);
					break;
					case (byte) (0x62):
						obj = HousingDeviceMapper.createElectricCurtain(node, eoj);
					break;
					case (byte) (0x63):
						obj = HousingDeviceMapper.createElectricRainSlidingDoor(node, eoj);
					break;
					case (byte) (0x64):
						obj = HousingDeviceMapper.createElectricGate(node, eoj);
					break;
					case (byte) (0x65):
						obj = HousingDeviceMapper.createElectricWindow(node, eoj);
					break;
					case (byte) (0x66):
						obj = HousingDeviceMapper.createElectricEntranceDoor(node, eoj);
					break;
					case (byte) (0x67):
						obj = HousingDeviceMapper.createGardenSprinkler(node, eoj);
					break;
					case (byte) (0x68):
						obj = HousingDeviceMapper.createFireSprinkler(node, eoj);
					break;
					case (byte) (0x69):
						obj = HousingDeviceMapper.createFountain(node, eoj);
					break;
					case (byte) (0x6A):
						obj = HousingDeviceMapper.createInstantaneousWaterHeater(node, eoj);
					break;
					case (byte) (0x6B):
						obj = HousingDeviceMapper.createElectricWaterHeater(node, eoj);
					break;
					case (byte) (0x6C):
						obj = HousingDeviceMapper.createSolarWaterHeater(node, eoj);
					break;
					case (byte) (0x6D):
						obj = HousingDeviceMapper.createCirculationPump(node, eoj);
					break;
					case (byte) (0x6E):
						obj = HousingDeviceMapper.createBidetEquippedToilet(node, eoj);
					break;
					case (byte) (0x6F):
						obj = HousingDeviceMapper.createElectricLock(node, eoj);
					break;
					case (byte) (0x70):
						obj = HousingDeviceMapper.createGasLineValve(node, eoj);
					break;
					case (byte) (0x71):
						obj = HousingDeviceMapper.createHomeSauna(node, eoj);
					break;
					case (byte) (0x72):
						obj = HousingDeviceMapper.createHotWaterGenerator(node, eoj);
					break;
					case (byte) (0x73):
						obj = HousingDeviceMapper.createBathRoomDryer(node, eoj);
					break;
					case (byte) (0x74):
						obj = HousingDeviceMapper.createHomeElevator(node, eoj);
					break;
					case (byte) (0x75):
						obj = HousingDeviceMapper.createElectricRoomDivider(node, eoj);
					break;
					case (byte) (0x76):
						obj = HousingDeviceMapper.createHorizontalTransfer(node, eoj);
					break;
					case (byte) (0x77):
						obj = HousingDeviceMapper.createElectricallyClothesDryingPole(node, eoj);
					break;
					case (byte) (0x78):
						obj = HousingDeviceMapper.createSepticTank(node, eoj);
					break;
					case (byte) (0x79):
						obj = HousingDeviceMapper.createHomeSolarPowerGeneration(node, eoj);
					break;
					case (byte) (0x7A):
						obj = HousingDeviceMapper.createColdHotWaterHeatSourceEquipment(node, eoj);
					break;
					case (byte) (0x7B):
						obj = HousingDeviceMapper.createFloorHeater(node, eoj);
					break;
					case (byte) (0x7C):
						obj = HousingDeviceMapper.createFuelCell(node, eoj);
					break;
					case (byte) (0x7D):
						obj = HousingDeviceMapper.createStorageBattery(node, eoj);
					break;
					case (byte) (0x7E):
						obj = HousingDeviceMapper.createChargerForElectricVehicle(node, eoj);
					break;
					case (byte) (0x7F):
						obj = HousingDeviceMapper.createEngineCogeneration(node, eoj);
					break;
					case (byte) (0x80):
						obj = HousingDeviceMapper.createElectricEnergyMeter(node, eoj);
					break;
					case (byte) (0x81):
						obj = HousingDeviceMapper.createWaterFlowMeter(node, eoj);
					break;
					case (byte) (0x82):
						obj = HousingDeviceMapper.createGasMeter(node, eoj);
					break;
					case (byte) (0x83):
						obj = HousingDeviceMapper.createLPGasMeter(node, eoj);
					break;
					case (byte) (0x84):
						obj = HousingDeviceMapper.createClock(node, eoj);
					break;
					case (byte) (0x85):
						obj = HousingDeviceMapper.createAutomaticDoor(node, eoj);
					break;
					case (byte) (0x86):
						obj = HousingDeviceMapper.createCommercialElevator(node, eoj);
					break;
					case (byte) (0x87):
						obj = HousingDeviceMapper.createDistributionPanelMetering(node, eoj);
					break;
					case (byte) (0x88):
						obj = HousingDeviceMapper.createLowVoltageSmartElectricEnergyMeter(node, eoj);
					break;
					case (byte) (0x89):
						obj = HousingDeviceMapper.createSmartGasMeter(node, eoj);
					break;
					case (byte) (0x8A):
						obj = HousingDeviceMapper.createHighVoltageSmartElectricEnergyMeter(node, eoj);
					break;
					case (byte) (0x8B):
						obj = HousingDeviceMapper.createKeroseneMeter(node, eoj);
					break;
					case (byte) (0x8C):
						obj = HousingDeviceMapper.createSmartKeroseneMeter(node, eoj);
					break;
					case (byte) (0x90):
						obj = HousingDeviceMapper.createGeneralLightingFamily(node, eoj);
					break;
					case (byte) (0x91):
						obj = HousingDeviceMapper.createSingleFunctionLighting(node, eoj);
					break;
					case (byte) (0x92):
						obj = HousingDeviceMapper.createLightingForSolidLightEmittingSource(node, eoj);
					break;
					case (byte) (0x99):
						obj = HousingDeviceMapper.createEmergencyLighting(node, eoj);
					break;
					case (byte) (0x9D):
						obj = HousingDeviceMapper.createEquipmentLight(node, eoj);
					break;	
					case (byte) (0xA0):
						obj = HousingDeviceMapper.createBuzzer(node, eoj);
					break;
					case (byte) (0xA1):
						obj = HousingDeviceMapper.createChargerForElectricVehicle(node, eoj);
					break;
					case (byte) (0xA2):
						obj = HousingDeviceMapper.createHouseholdSmallWindTurbinePowerGeneration(node, eoj);
					break;
					case (byte) (0xA3):
						obj = HousingDeviceMapper.createLightingSystem(node, eoj);
					break;
					case (byte) (0xA4):
						obj = HousingDeviceMapper.createExtendedLightingSystem(node, eoj);
					break;
					case (byte) (0xA5):
						obj = HousingDeviceMapper.createMultipleInputPCS(node, eoj);
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
						obj = CookingDeviceMapper.createCoffeeMachine(node, eoj);
					break;
					case (byte) (0xB1):
						obj = CookingDeviceMapper.createCoffeeMill(node, eoj);
					break;
					case (byte) (0xB2):
						obj = CookingDeviceMapper.createElectricHotWaterPot(node, eoj);
					break;
					case (byte) (0xB3):
						obj = CookingDeviceMapper.createElectricStove(node, eoj);
					break;
					case (byte) (0xB4):
						obj = CookingDeviceMapper.createToaster(node, eoj);
					break;
					case (byte) (0xB5):
						obj = CookingDeviceMapper.createJuicerFoodMixer(node, eoj);
					break;
					case (byte) (0xB6):
						obj = CookingDeviceMapper.createFoodProcessor(node, eoj);
					break;
					case (byte) (0xB7):
						obj = CookingDeviceMapper.createRefrigerator(node, eoj);
					break;
					case (byte) (0xB8):
						obj = CookingDeviceMapper.createCombinationMicrowaveOven(node, eoj);
					break;
					case (byte) (0xB9):
						obj = CookingDeviceMapper.createCookingHeater(node, eoj);
					break;
					case (byte) (0xBA):
						obj = CookingDeviceMapper.createOven(node, eoj);
					break;
					case (byte) (0xBB):
						obj = CookingDeviceMapper.createRiceCooker(node, eoj);
					break;
					case (byte) (0xBC):
						obj = CookingDeviceMapper.createElectronicJar(node, eoj);
					break;
					case (byte) (0xBD):
						obj = CookingDeviceMapper.createDishWasher(node, eoj);
					break;
					case (byte) (0xBE):
						obj = CookingDeviceMapper.createDishDryer(node, eoj);
					break;
					case (byte) (0xBF):
						obj = CookingDeviceMapper.createRiceCardCooker(node, eoj);
					break;
					case (byte) (0xC0):
						obj = CookingDeviceMapper.createKeepWarmMachine(node, eoj);
					break;
					case (byte) (0xC1):
						obj = CookingDeviceMapper.createRiceMill(node, eoj);
					break;
					case (byte) (0xC2):
						obj = CookingDeviceMapper.createBreadCooker(node, eoj);
					break;
					case (byte) (0xC3):
						obj = CookingDeviceMapper.createSlowCooker(node, eoj);
					break;
					case (byte) (0xC4):
						obj = CookingDeviceMapper.createPicklesCooker(node, eoj);
					break;
					case (byte) (0xC5):
						obj = CookingDeviceMapper.createWashingMachine(node, eoj);
					break;
					case (byte) (0xC6):
						obj = CookingDeviceMapper.createClothesDryer(node, eoj);
					break;
					case (byte) (0xC7):
						obj = CookingDeviceMapper.createElectricIron(node, eoj);
					break;	
					case (byte) (0xC8):
						obj = CookingDeviceMapper.createTrouserPress(node, eoj);
					break;
					case (byte) (0xC9):
						obj = CookingDeviceMapper.createFutonDryer(node, eoj);
					break;
					case (byte) (0xCA):
						obj = CookingDeviceMapper.createShoesDryer(node, eoj);
					break;
					case (byte) (0xCB):
						obj = CookingDeviceMapper.createVacuumCleaner(node, eoj);
					break;
					case (byte) (0xCC):
						obj = CookingDeviceMapper.createDisposer(node, eoj);
					break;
					case (byte) (0xCD):
						obj = CookingDeviceMapper.createMosquitoCatcher(node, eoj);
					break;
					case (byte) (0xCE):
						obj = CookingDeviceMapper.createCommercialShowCase(node, eoj);
					break;
					case (byte) (0xCF):
						obj = CookingDeviceMapper.createCommercialRefrigerator(node, eoj);
					break;
					case (byte) (0xD0):
						obj = CookingDeviceMapper.createCommerciaHotCase(node, eoj);
					break;
					case (byte) (0xD1):
						obj = CookingDeviceMapper.createCommercialFryer(node, eoj);
					break;
					case (byte) (0xD2):
						obj = CookingDeviceMapper.createCommercialMicrowaveOven(node, eoj);
					break;
					case (byte) (0xD3):
						obj = CookingDeviceMapper.createWasherAndDryer(node, eoj);
					break;
					case (byte) (0xD4):
						obj = CookingDeviceMapper.createCommerciaShowCaseOutdoorUnit(node, eoj);
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
						obj = HeathDeviceMapper.createWeighingMachine(node, eoj);
					break;
					case (byte) (0x02):
						obj = HeathDeviceMapper.createClinicalThermoMeter(node, eoj);
					break;
					case (byte) (0x03):
						obj = HeathDeviceMapper.createBloodPressuremeter(node, eoj);
					break;
					case (byte) (0x04):
						obj = HeathDeviceMapper.createBloodSugarmeter(node, eoj);
					break;
					case (byte) (0x05):
						obj = HeathDeviceMapper.createBodyFatmeter(node, eoj);
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
						obj = ManagementDeviceMapper.createParallelProcessingCombinationTypePowerControl(node, eoj);
					break;
					case (byte) (0xFB):
						obj = ManagementDeviceMapper.createDREventController(node, eoj);
					break;
					case (byte) (0xFC):
						obj = ManagementDeviceMapper.createSecureCommunicationSharedKeySetupNode(node, eoj);
					break;
					case (byte) (0xFD):
						obj = ManagementDeviceMapper.createSwitch(node, eoj);
					break;
					case (byte) (0xFE):
						obj = ManagementDeviceMapper.createPortableTerminal(node, eoj);
					break;
					case (byte) (0xFF):
						obj = ManagementDeviceMapper.createController(node, eoj);
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
						obj = AudioVisualDeviceMapper.createDisplay(node, eoj);
					break;
					case (byte) (0x02):
						obj = AudioVisualDeviceMapper.createTelevision(node, eoj);
					break;
					case (byte) (0x03):
						obj = AudioVisualDeviceMapper.createAudio(node, eoj);
					break;
					case (byte) (0x04):
						obj = AudioVisualDeviceMapper.createNetworkCamera(node, eoj);
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
