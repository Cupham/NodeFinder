package jaist.ac.jp.NodeFinder.echonet.object.mapper;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eAirCleaner;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eAirconditionerVentilationFan;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eCommercialAirConditionerIndoorUnit;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eCommercialAirConditionerOutdoorUnit;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eElectricHeater;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eElectricStorageHeater;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eFanHeater;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eGasHeatCommercialAirConditionerIndoorUnit;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eGasHeatCommercialAirConditionerOutdoorUnit;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eHomeAirConditioner;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eHumidifier;
import jaist.ac.jp.NodeFinder.echonet.object.airconditioner.eVentilationFan;

public class AirConditionerMapper {
	private static final Logger logger = Logger.getLogger(AirConditionerMapper.class.getName());
	
	public static eDataObject createHomeAirConditioner(Node node, EOJ eoj) {
		logger.info("Creating HomeAirConditioner from ECHONET Lite frame...");
		return new eHomeAirConditioner(node, eoj);
	}
	public static eDataObject createColdBlaster(Node node, EOJ eoj) {
		logger.warning("ColdBlaster specification has not been released");
		return null;
	}
	public static eDataObject createElectricFan(Node node, EOJ eoj) {
		logger.warning("ElectricFan specification has not been released");
		return null;
	}
	public static eDataObject createVentilationFan(Node node, EOJ eoj) {
		logger.info("Creating VentilationFan from ECHONET Lite frame...");
		return new eVentilationFan(node, eoj);
	}
	public static eDataObject createAirconWithVentilationFan(Node node, EOJ eoj) {
		logger.info("Creating AirconWithVentilationFan from ECHONET Lite frame...");
		return new eAirconditionerVentilationFan(node, eoj);
	}
	public static eDataObject createAirCleaner(Node node, EOJ eoj) {
		logger.info("Creating AirCleaner from ECHONET Lite frame...");
		return new eAirCleaner(node, eoj);
	}
	public static eDataObject createColdBlastFan(Node node, EOJ eoj) {
		logger.warning("ColdBlastFan specification has not been released");
		return null;
	}
	public static eDataObject createCirculator(Node node, EOJ eoj) {
		logger.warning("Circulator specification has not been released");
		return null;
	}
	public static eDataObject createDehumidifier(Node node, EOJ eoj) {
		logger.warning("Dehumidifier specification has not been released");
		return null;
	}
	public static eDataObject createHumidifier(Node node, EOJ eoj) {
		logger.info("Creating Humidifier from ECHONET Lite frame...");
		return new eHumidifier(node, eoj);
	}
	public static eDataObject createCeilingFan(Node node, EOJ eoj) {
		logger.warning("CeilingFan specification has not been released");
		return null;
	}
	public static eDataObject createElectricKotatsu(Node node, EOJ eoj) {
		logger.warning("ElectricKotatsu specification has not been released");
		return null;
	}
	public static eDataObject createElectricHeatingPad(Node node, EOJ eoj) {
		logger.warning("ElectricHeatingPad specification has not been released");
		return null;
	}
	public static eDataObject createElectricBlanket(Node node, EOJ eoj) {
		logger.warning("ElectricBlanket specification has not been released");
		return null;
	}
	public static eDataObject createSpaceHeater(Node node, EOJ eoj) {
		logger.warning("SpaceHeater specification has not been released");
		return null;
	}
	public static eDataObject createPanelHeater(Node node, EOJ eoj) {
		logger.warning("PanelHeater specification has not been released");
		return null;
	}
	public static eDataObject createElectricCarpet(Node node, EOJ eoj) {
		logger.warning("ElectricCarpet specification has not been released");
		return null;
	}
	public static eDataObject createFloorHeater(Node node, EOJ eoj) {
		logger.warning("FloorHeater specification has not been released");
		return null;
	}
	
	public static eDataObject createElectricHeater(Node node, EOJ eoj) {
		logger.info("Creating ElectricHeater from ECHONET Lite frame...");
		return new eElectricHeater(node, eoj);
	}
	public static eDataObject createFanHeater(Node node, EOJ eoj) {
		logger.info("Creating FanHeater from ECHONET Lite frame...");
		return new eFanHeater(node, eoj);
	}
	public static eDataObject createBatteryCharger(Node node, EOJ eoj) {
		logger.warning("BatteryCharger specification has not been released");
		return null;
	}
	public static eDataObject createCommercialAirconThermalStorageUnit(Node node, EOJ eoj) {
		logger.warning("CommercialAirconThermalStorageUnit specification has not been released");
		return null;
	}
	public static eDataObject createCommercialFanCoilUnit(Node node, EOJ eoj) {
		logger.warning("CommercialFanCoilUnit specification has not been released");
		return null;
	}
	
	public static eDataObject createCommercialAirConColdSource(Node node, EOJ eoj) {
		logger.warning("CommercialAirConColdSource specification has not been released");
		return null;
	}
	public static eDataObject createCommercialAirConHotSource(Node node, EOJ eoj) {
		logger.warning("CommercialAirConHotSource specification has not been released");
		return null;
	}
	public static eDataObject createCommercialAirConVAV(Node node, EOJ eoj) {
		logger.warning("CommercialAirConVAV specification has not been released");
		return null;
	}
	public static eDataObject createCommercialAirHandlingUnit(Node node, EOJ eoj) {
		logger.warning("CommercialAirHandlingUnit specification has not been released");
		return null;
	}
	public static eDataObject createUnitCooler(Node node, EOJ eoj) {
		logger.warning("UnitCooler specification has not been released");
		return null;
	}
	
	public static eDataObject createCommercialCondensingUnit(Node node, EOJ eoj) {
		logger.warning("CommercialCondensingUnit specification has not been released");
		return null;
	}
	public static eDataObject createElectricStorageHeater(Node node, EOJ eoj) {
		logger.info("Creating ElectricStorageHeater from ECHONET Lite frame...");
		return new eElectricStorageHeater(node, eoj);
	}
	public static eDataObject createCommercialAirConIndoorUnit(Node node, EOJ eoj) {
		logger.info("Creating CommercialAirConIndoorUnit from ECHONET Lite frame...");
		return new eCommercialAirConditionerIndoorUnit(node, eoj);
	}
	public static eDataObject createCommercialAirConOutdoorUnit(Node node, EOJ eoj) {
		logger.info("Creating CommercialAirConOutdoorUnit from ECHONET Lite frame...");
		return new eCommercialAirConditionerOutdoorUnit(node, eoj);
	}
	public static eDataObject createCommercialGasHeatAirConIndoorUnit(Node node, EOJ eoj) {
		logger.info("Creating CommercialGasHeatAirConIndoorUnit from ECHONET Lite frame...");
		return new eGasHeatCommercialAirConditionerIndoorUnit(node, eoj);
	}
	public static eDataObject createCommercialGasHeatAirConOutdoorUnit(Node node, EOJ eoj) {
		logger.info("Creating CommercialGasHeatAirConOutdoorUnit from ECHONET Lite frame...");
		return new eGasHeatCommercialAirConditionerOutdoorUnit(node, eoj);
	}
}
