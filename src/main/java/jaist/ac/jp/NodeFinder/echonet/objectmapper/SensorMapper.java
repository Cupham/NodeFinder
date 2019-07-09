package jaist.ac.jp.NodeFinder.echonet.objectmapper;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eCrimePreventionSensor;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eEarthquakeSensor;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eElectricLeakSensor;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eEmergencyButton;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eFirstAidSensor;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eGasLeakSensor;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eHumanDetectionSensor;

public class SensorMapper {	
	private static final Logger logger = Logger.getLogger(SensorMapper.class.getName());
	public static eDataObject createGasLeakedSensor(Node node, EOJ eoj) {
		logger.info("Creating GasLeakSensor from ECHONET Lite frame...");
		return new eGasLeakSensor(node, eoj);
	}
	public static eDataObject createCrimePreventionSensor(Node node, EOJ eoj) {
		logger.info("Creating CrimePreventionSensor from ECHONET Lite frame...");
		return new eCrimePreventionSensor(node, eoj);
	}
	
	public static eDataObject createEmergencyButton(Node node, EOJ eoj) {	
		logger.info("Creating EmergencyButton from ECHONET Lite frame...");
		return new eEmergencyButton(node, eoj);
	}
	public static eDataObject createFirstAIDSensor(Node node, EOJ eoj) {
		logger.info("Creating First-AidSensor from ECHONET Lite frame...");
		return new eFirstAidSensor(node, eoj);
	}
	public static eDataObject createEarthQuakeSensor(Node node, EOJ eoj) {
		logger.info("Creating EarthquakeSensor from ECHONET Lite frame...");
		return new eEarthquakeSensor(node, eoj);
	}
	
	public static eDataObject createElectricLeakSensor(Node node, EOJ eoj) {
		logger.info("Creating ElectricLeak from ECHONET Lite frame...");
		return new eElectricLeakSensor(node, eoj);
	}
	public static eDataObject createHumanDetectionSensor(Node node, EOJ eoj) {
		logger.info("Creating HumanDetectionSensor from ECHONET Lite frame...");
		return new eHumanDetectionSensor(node, eoj);
	}
	public static eDataObject createVisitorSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createCallSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createCondensationSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createAirPollutionSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createOxygenSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createIlluminanceSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createSoundSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createMailingSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createWeightSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createTemperatureSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createHumiditySensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createRainSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createWaterLevelSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createBathWaterLevelSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createBathHeatingStatusSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createWaterLeakSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createWaterOverFlowSensor(Node node, EOJ eoj) {
		
		return null;
	}	
	public static eDataObject createFireSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createCigaretteSmokeSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createCO2Sensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createGasSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createVOCSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createDifferentialPressureSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createAirSpeedSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createOdorSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createFlameSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createElectricEnergySensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createCurrentValueSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createDaylightSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createWaterFlowRateSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createMicroMotionSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createPassageSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createBedPresenceSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createOpen_CloseSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createActivityMountSensor(Node node, EOJ eoj) {
		
		return null;
	}
	public static eDataObject createHumanBodyLocationSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createSnowSensor(Node node, EOJ eoj) {
		
		return null;
	}
	
	public static eDataObject createAirPressureSensor(Node node, EOJ eoj) {
		
		return null;
	}
}
