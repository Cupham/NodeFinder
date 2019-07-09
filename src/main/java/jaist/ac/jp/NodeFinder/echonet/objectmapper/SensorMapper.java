package jaist.ac.jp.NodeFinder.echonet.objectmapper;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eActivityMountSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eAirPollutionSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eAirPressureSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eAirSpeedSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eBathHeatingStatusSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eBathWaterLevelSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eBedPresenceSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eCO2Sensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eCallSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eCigaretteSmokeSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eCondensationSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eCrimePreventionSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eCurrentValueSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eDifferentialPressureSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eEarthquakeSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eElectricEnergySensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eElectricLeakSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eEmergencyButton;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eFireSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eFirstAidSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eFlameSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eGasLeakSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eGasSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eHumanDetectionSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eHumiditySensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eIlliuminanceSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eMailingSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eMicroMotionSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eOdorSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eOpenCloseSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eOxygenSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.ePassageSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eRainSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eSnowSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eSoundSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eTemperatureSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eVOCSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eVisitorSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eWaterFlowRateSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eWaterLeakSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eWaterLevelSensor;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eWaterOverFlow;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eWeightSensor;

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
		logger.info("Creating VisitorSensor from ECHONET Lite frame...");
		return new eVisitorSensor(node, eoj);
	}
	
	public static eDataObject createCallSensor(Node node, EOJ eoj) {
		logger.info("Creating CallSensor from ECHONET Lite frame...");
		return new eCallSensor(node, eoj);
	}
	
	public static eDataObject createCondensationSensor(Node node, EOJ eoj) {
		logger.info("Creating CondensationSensor from ECHONET Lite frame...");
		return new eCondensationSensor(node, eoj);
	}
	public static eDataObject createAirPollutionSensor(Node node, EOJ eoj) {
		logger.info("Creating AirPollutionSensor from ECHONET Lite frame...");
		return new eAirPollutionSensor(node, eoj);
	}
	public static eDataObject createOxygenSensor(Node node, EOJ eoj) {
		logger.info("Creating OxygenSensor from ECHONET Lite frame...");
		return new eOxygenSensor(node, eoj);
	}
	
	public static eDataObject createIlluminanceSensor(Node node, EOJ eoj) {
		logger.info("Creating IlluminanceSensor from ECHONET Lite frame...");
		return new eIlliuminanceSensor(node, eoj);
	}
	
	public static eDataObject createSoundSensor(Node node, EOJ eoj) {
		logger.info("Creating SoundSensor from ECHONET Lite frame...");
		return new eSoundSensor(node, eoj);
	}
	
	public static eDataObject createMailingSensor(Node node, EOJ eoj) {
		logger.info("Creating MailingSensor from ECHONET Lite frame...");
		return new eMailingSensor(node, eoj);
	}
	public static eDataObject createWeightSensor(Node node, EOJ eoj) {
		logger.info("Creating WeightSensor from ECHONET Lite frame...");
		return new eWeightSensor(node, eoj);
	}
	public static eDataObject createTemperatureSensor(Node node, EOJ eoj) {
		logger.info("Creating TemperatureSensor from ECHONET Lite frame...");
		return new eTemperatureSensor(node, eoj);
	}
	public static eDataObject createHumiditySensor(Node node, EOJ eoj) {
		logger.info("Creating HumiditySensor from ECHONET Lite frame...");
		return new eHumiditySensor(node, eoj);
	}
	public static eDataObject createRainSensor(Node node, EOJ eoj) {
		logger.info("Creating RainSensor from ECHONET Lite frame...");
		return new eRainSensor(node, eoj);
	}
	
	public static eDataObject createWaterLevelSensor(Node node, EOJ eoj) {
		logger.info("Creating WaterLevelSensor from ECHONET Lite frame...");
		return new eWaterLevelSensor(node, eoj);
	}
	
	public static eDataObject createBathWaterLevelSensor(Node node, EOJ eoj) {
		logger.info("Creating BathWaterLevelSensor from ECHONET Lite frame...");
		return new eBathWaterLevelSensor(node, eoj);
	}
	public static eDataObject createBathHeatingStatusSensor(Node node, EOJ eoj) {
		logger.info("CreatingBathHeatingStatusSensor from ECHONET Lite frame...");
		return new eBathHeatingStatusSensor(node, eoj);
	}
	public static eDataObject createWaterLeakSensor(Node node, EOJ eoj) {
		logger.info("Creating WaterLeakSensor from ECHONET Lite frame...");
		return new eWaterLeakSensor(node, eoj);
	}
	public static eDataObject createWaterOverFlowSensor(Node node, EOJ eoj) {
		logger.info("Creating WaterOverFlowSensor from ECHONET Lite frame...");
		return new eWaterOverFlow(node, eoj);
	}	
	public static eDataObject createFireSensor(Node node, EOJ eoj) {
		logger.info("Creating FireSensor from ECHONET Lite frame...");
		return new eFireSensor(node, eoj);
	}
	
	public static eDataObject createCigaretteSmokeSensor(Node node, EOJ eoj) {
		logger.info("Creating CigaretteSmokeSensor from ECHONET Lite frame...");
		return new eCigaretteSmokeSensor(node, eoj);
	}
	
	public static eDataObject createCO2Sensor(Node node, EOJ eoj) {
		logger.info("Creating CO2Sensor from ECHONET Lite frame...");
		return new eCO2Sensor(node, eoj);
	}
	public static eDataObject createGasSensor(Node node, EOJ eoj) {
		logger.info("Creating GasSensor from ECHONET Lite frame...");
		return new eGasSensor(node, eoj);
	}
	public static eDataObject createVOCSensor(Node node, EOJ eoj) {
		logger.info("Creating VOCSensor from ECHONET Lite frame...");
		return new eVOCSensor(node, eoj);
	}
	public static eDataObject createDifferentialPressureSensor(Node node, EOJ eoj) {
		logger.info("Creating DifferentialPressureSensor from ECHONET Lite frame...");
		return new eDifferentialPressureSensor(node, eoj);
	}
	public static eDataObject createAirSpeedSensor(Node node, EOJ eoj) {
		logger.info("Creating AirSpeedSensor from ECHONET Lite frame...");
		return new eAirSpeedSensor(node, eoj);
	}
	
	public static eDataObject createOdorSensor(Node node, EOJ eoj) {
		logger.info("Creating OdorSensor from ECHONET Lite frame...");
		return new eOdorSensor(node, eoj);
	}
	
	public static eDataObject createFlameSensor(Node node, EOJ eoj) {
		logger.info("Creating FlameSensor from ECHONET Lite frame...");
		return new eFlameSensor(node, eoj);
	}
	public static eDataObject createElectricEnergySensor(Node node, EOJ eoj) {
		logger.info("Creating ElectricEnergySensor from ECHONET Lite frame...");
		return new eElectricEnergySensor(node, eoj);
	}
	public static eDataObject createCurrentValueSensor(Node node, EOJ eoj) {
		logger.info("CreatingCurrentValueSensor from ECHONET Lite frame...");
		return new eCurrentValueSensor(node, eoj);
	}
	public static eDataObject createDaylightSensor(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createWaterFlowRateSensor(Node node, EOJ eoj) {
		logger.info("Creating WaterFlowRateSensor from ECHONET Lite frame...");
		return new eWaterFlowRateSensor(node, eoj);
	}
	
	public static eDataObject createMicroMotionSensor(Node node, EOJ eoj) {
		logger.info("Creating MicroMotionSensor from ECHONET Lite frame...");
		return new eMicroMotionSensor(node, eoj);
	}
	
	public static eDataObject createPassageSensor(Node node, EOJ eoj) {
		logger.info("Creating PassageSensor from ECHONET Lite frame...");
		return new ePassageSensor(node, eoj);
	}
	public static eDataObject createBedPresenceSensor(Node node, EOJ eoj) {
		logger.info("Creating BedPresenceSensor from ECHONET Lite frame...");
		return new eBedPresenceSensor(node, eoj);
	}
	public static eDataObject createOpen_CloseSensor(Node node, EOJ eoj) {
		logger.info("Creating createOpen_CloseSensor from ECHONET Lite frame...");
		return new eOpenCloseSensor(node, eoj);
	}
	public static eDataObject createActivityMountSensor(Node node, EOJ eoj) {
		logger.info("Creating ActivityMountSensor from ECHONET Lite frame...");
		return new eActivityMountSensor(node, eoj);
	}
	public static eDataObject createHumanBodyLocationSensor(Node node, EOJ eoj) {
		logger.info("Creating HumanBodyLocationSensor from ECHONET Lite frame...");
		return new eHumanDetectionSensor(node, eoj);
	}
	
	public static eDataObject createSnowSensor(Node node, EOJ eoj) {
		logger.info("Creating SnowSensor from ECHONET Lite frame...");
		return new eSnowSensor(node, eoj);
	}
	
	public static eDataObject createAirPressureSensor(Node node, EOJ eoj) {
		logger.info("Creating AirPressureSensor from ECHONET Lite frame...");
		return new eAirPressureSensor(node, eoj);
	}
}
