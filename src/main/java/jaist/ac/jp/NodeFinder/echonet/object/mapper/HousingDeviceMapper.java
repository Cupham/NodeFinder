package jaist.ac.jp.NodeFinder.echonet.object.mapper;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eBidetEquippedToilet;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eBuzzer;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eColdHotWaterHeatSourceEquipment;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricBlind;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricCurtain;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricEnergyMeter;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricEntranceDoor;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricGate;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricLock;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricRainSlidingDoor;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricShutter;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricVehicleCharger;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricVehicleChargerDischarger;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricWaterHeater;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eElectricWindow;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eEngineCogeneration;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eExtendedLightingSystem;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eFloorHeater;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eFuelCell;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eGardenSprinkler;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eGasMeter;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eHighVoltageSmartElectricEnergyMeter;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eHouseholdSmallWindTurbinePowerGeneration;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eHouseholdSolarPowerGeneration;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eInstantaneousWaterHeater;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eKeroseneMeter;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eLPGasMeter;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eLightingForSolidLightEmittingSource;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eLowVoltageSmartElectricEnergyMeter;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eMultipleInputPCS;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eSingleFunctionLighting;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eSmartGasMeter;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eStorageBattery;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eWaterFlowMeter;
import jaist.ac.jp.NodeFinder.echonet.object.management.eParallelProcessingCombinationTypePowerControl;

public class HousingDeviceMapper {
	private static final Logger logger = Logger.getLogger(HousingDeviceMapper.class.getName());
	
	public static eDataObject createElectricBlind(Node node, EOJ eoj) {
		logger.info("Creating ElectricBlind from ECHONET Lite frame...");
		return new eElectricBlind(node, eoj);
	}
	public static eDataObject createElectricShutter(Node node, EOJ eoj) {
		logger.info("Creating ElectricShutter from ECHONET Lite frame...");
		return new eElectricShutter(node, eoj);
	}
	public static eDataObject createElectricCurtain(Node node, EOJ eoj) {
		logger.info("Creating ElectricCurtain from ECHONET Lite frame...");
		return new eElectricCurtain(node, eoj);
	}
	public static eDataObject createElectricRainSlidingDoor(Node node, EOJ eoj) {
		logger.info("Creating ElectricRainSlidingDoor from ECHONET Lite frame...");
		return new eElectricRainSlidingDoor(node, eoj);
	}
	public static eDataObject createElectricGate(Node node, EOJ eoj) {
		logger.info("Creating ElectricGate from ECHONET Lite frame...");
		return new eElectricGate(node, eoj);
	}
	public static eDataObject createElectricWindow(Node node, EOJ eoj) {
		logger.info("Creating ElectricWindow from ECHONET Lite frame...");
		return new eElectricWindow(node, eoj);
	}
	public static eDataObject createElectricEntranceDoor(Node node, EOJ eoj) {
		logger.info("Creating ElectricEntranceDoor from ECHONET Lite frame...");
		return new eElectricEntranceDoor(node, eoj);
	}
	public static eDataObject createGardenSprinkler(Node node, EOJ eoj) {
		logger.info("Creating GardenSprinkler from ECHONET Lite frame...");
		return new eGardenSprinkler(node, eoj);
	}
	public static eDataObject createFireSprinkler(Node node, EOJ eoj) {
		logger.warning("FireSprinkler specification has not been released");
		return null;
	}
	public static eDataObject createFountain(Node node, EOJ eoj) {
		logger.warning("Fountain specification has not been released");
		return null;
	}
	public static eDataObject createInstantaneousWaterHeater(Node node, EOJ eoj) {
		logger.info("Creating InstantaneousWaterHeater from ECHONET Lite frame...");
		return new eInstantaneousWaterHeater(node, eoj);
	}
	public static eDataObject createElectricWaterHeater(Node node, EOJ eoj) {
		logger.info("Creating ElectricWaterHeater from ECHONET Lite frame...");
		return new eElectricWaterHeater(node, eoj);
	}
	public static eDataObject createSolarWaterHeater(Node node, EOJ eoj) {
		logger.warning("SolarWaterHeater specification has not been released");
		return null;
	}
	public static eDataObject createCirculationPump(Node node, EOJ eoj) {
		logger.warning("CirculationPump specification has not been released");
		return null;
	}
	public static eDataObject createBidetEquippedToilet(Node node, EOJ eoj) {
		logger.info("Creating BidetEquippedToilet from ECHONET Lite frame...");
		return new eBidetEquippedToilet(node, eoj);
	}
	public static eDataObject createElectricLock(Node node, EOJ eoj) {
		logger.info("Creating ElectricLock from ECHONET Lite frame...");
		return new eElectricLock(node, eoj);
	}
	public static eDataObject createGasLineValve(Node node, EOJ eoj) {
		logger.warning("GasLineValve specification has not been released");
		return null;
	}	
	public static eDataObject createHomeSauna(Node node, EOJ eoj) {
		logger.warning("HomeSauna specification has not been released");
		return null;
	}
	public static eDataObject createHotWaterGenerator(Node node, EOJ eoj) {
		logger.warning("HotWaterGenerator specification has not been released");
		return null;
	}
	public static eDataObject createBathRoomDryer(Node node, EOJ eoj) {
		logger.info("Creating ElectricLock from ECHONET Lite frame...");
		return null;
	}
	public static eDataObject createHomeElevator(Node node, EOJ eoj) {
		logger.warning("HomeElevator specification has not been released");
		return null;
	}
	public static eDataObject createElectricRoomDivider(Node node, EOJ eoj) {
		logger.warning("ElectricRoomDivider specification has not been released");
		return null;
	}
	public static eDataObject createHorizontalTransfer(Node node, EOJ eoj) {
		logger.warning("HorizontalTransfer specification has not been released");
		return null;
	}
	public static eDataObject createElectricallyClothesDryingPole(Node node, EOJ eoj) {
		logger.warning("ElectricallyClothesDryingPole specification has not been released");
		return null;
	}
	public static eDataObject createSepticTank(Node node, EOJ eoj) {
		logger.warning("SepticTank specification has not been released");
		return null;
	}
	public static eDataObject createHomeSolarPowerGeneration(Node node, EOJ eoj) {
		logger.info("Creating HomeSolarPowerGeneration from ECHONET Lite frame...");
		return new eHouseholdSolarPowerGeneration(node, eoj);
	}
	public static eDataObject createColdHotWaterHeatSourceEquipment(Node node, EOJ eoj) {
		logger.info("Creating ColdHotWaterHeatSourceEquipment from ECHONET Lite frame...");
		return new eColdHotWaterHeatSourceEquipment(node, eoj);
	}
	public static eDataObject createFloorHeater(Node node, EOJ eoj) {
		logger.info("Creating FloorHeater from ECHONET Lite frame...");
		return new eFloorHeater(node, eoj);
	}
	public static eDataObject createFuelCell(Node node, EOJ eoj) {
		logger.info("Creating FuelCel from ECHONET Lite frame...");
		return new eFuelCell(node, eoj);
	}
	public static eDataObject createStorageBattery(Node node, EOJ eoj) {
		logger.info("Creating StorageBattery from ECHONET Lite frame...");
		return new eStorageBattery(node, eoj);
	}
	public static eDataObject createElectricCehicleChargerDischarger(Node node, EOJ eoj) {
		logger.info("Creating ElectricCehicleChargerDischarger from ECHONET Lite frame...");
		return new eElectricVehicleChargerDischarger(node, eoj);
	}
	public static eDataObject createEngineCogeneration(Node node, EOJ eoj) {
		logger.info("Creating EngineCogeneration from ECHONET Lite frame...");
		return new eEngineCogeneration(node, eoj);
	}
	public static eDataObject createElectricEnergyMeter(Node node, EOJ eoj) {
		logger.info("Creating ElectricEnergyMeter from ECHONET Lite frame...");
		return new eElectricEnergyMeter(node, eoj);
	}
	public static eDataObject createWaterFlowMeter(Node node, EOJ eoj) {
		logger.info("Creating WaterFlowMeter from ECHONET Lite frame...");
		return new eWaterFlowMeter(node, eoj);
	}
	public static eDataObject createGasMeter(Node node, EOJ eoj) {
		logger.info("Creating GasMeter from ECHONET Lite frame...");
		return new eGasMeter(node, eoj);
	}
	public static eDataObject createLPGasMeter(Node node, EOJ eoj) {
		logger.info("Creating LPGasMeter from ECHONET Lite frame...");
		return new eLPGasMeter(node, eoj);
	}
	public static eDataObject createClock(Node node, EOJ eoj) {
		logger.warning("Clock specification has not been released");
		return null;
	}
	public static eDataObject createAutomaticDoor(Node node, EOJ eoj) {
		logger.warning("AutomaticDoor specification has not been released");
		return null;
	}
	public static eDataObject createCommercialElevator(Node node, EOJ eoj) {
		logger.warning("CommercialElevator specification has not been released");
		return null;
	}
	public static eDataObject createDistributionPanelMetering(Node node, EOJ eoj) {
		logger.info("Creating DistributionPanelMetering from ECHONET Lite frame...");
		return new eParallelProcessingCombinationTypePowerControl(node, eoj);
	}
	public static eDataObject createLowVoltageSmartElectricEnergyMeter(Node node, EOJ eoj) {
		logger.info("Creating LowVoltageSmartElectricEnergyMeter from ECHONET Lite frame...");
		return new eLowVoltageSmartElectricEnergyMeter(node, eoj);
	}
	public static eDataObject createSmartGasMeter(Node node, EOJ eoj) {
		logger.info("Creating SmartGasMeter from ECHONET Lite frame...");
		return new eSmartGasMeter(node, eoj);
	}
	public static eDataObject createHighVoltageSmartElectricEnergyMeter(Node node, EOJ eoj) {
		logger.info("Creating HighVoltageSmartElectricEnergyMeter from ECHONET Lite frame...");
		return  new eHighVoltageSmartElectricEnergyMeter(node, eoj);
	}
	public static eDataObject createKeroseneMeter(Node node, EOJ eoj) {
		logger.info("Creating KeroseneMeter from ECHONET Lite frame...");
		return new eKeroseneMeter(node, eoj);
	}
	public static eDataObject createSmartKeroseneMeter(Node node, EOJ eoj) {
		logger.info("Creating SmartKeroseneMeter( from ECHONET Lite frame...");
		return createSmartKeroseneMeter(node, eoj);
	}
	public static eDataObject createGeneralLightingFamily(Node node, EOJ eoj) {
		logger.info("Creating lighting device from echonet lite frame...");
		return new eGeneralLighting(node,eoj);
	}
	public static eDataObject createSingleFunctionLighting(Node node, EOJ eoj) {
		logger.info("Creating SingleFunctionLighting from ECHONET Lite frame...");
		return new eSingleFunctionLighting(node, eoj);
	}
	public static eDataObject createLightingForSolidLightEmittingSource(Node node, EOJ eoj) {
		logger.info("Creating LightingForSolidLightEmittingSource from ECHONET Lite frame...");
		return new eLightingForSolidLightEmittingSource(node, eoj);
	}
	public static eDataObject createEmergencyLighting(Node node, EOJ eoj) {
		logger.warning("EmergencyLighting specification has not been released");
		return null;
	}
	public static eDataObject createEquipmentLight(Node node, EOJ eoj) {
		logger.warning("EquipmentLight specification has not been released");
		return null;
	}
	public static eDataObject createBuzzer(Node node, EOJ eoj) {
		logger.info("Creating Buzzer from ECHONET Lite frame...");
		return new eBuzzer(node, eoj);
	}
	public static eDataObject createChargerForElectricVehicle(Node node, EOJ eoj) {
		logger.info("Creating ChargerForElectricVehicle from ECHONET Lite frame...");
		return new eElectricVehicleCharger(node, eoj);
	}
	public static eDataObject createHouseholdSmallWindTurbinePowerGeneration(Node node, EOJ eoj) {
		logger.info("Creating HouseholdSmallWindTurbinePowerGeneration from ECHONET Lite frame...");
		return new eHouseholdSmallWindTurbinePowerGeneration(node, eoj);
	}
	public static eDataObject createLightingSystem(Node node, EOJ eoj) {
		logger.warning("LightingSystem specification has not been released");
		return null;
	}
	public static eDataObject createExtendedLightingSystem(Node node, EOJ eoj) {
		logger.info("Creating ExtendedLightingSystem from ECHONET Lite frame...");
		return new eExtendedLightingSystem(node, eoj);
	}
	public static eDataObject createMultipleInputPCS(Node node, EOJ eoj) {
		logger.info("Creating MultipleInputPCS from ECHONET Lite frame...");
		return new eMultipleInputPCS(node, eoj);
	}
}
