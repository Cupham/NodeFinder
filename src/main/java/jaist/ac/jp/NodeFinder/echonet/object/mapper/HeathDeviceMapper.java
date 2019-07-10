package jaist.ac.jp.NodeFinder.echonet.object.mapper;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.health.eWeighingMachine;

public class HeathDeviceMapper {
	private static final Logger logger = Logger.getLogger(HeathDeviceMapper.class.getName());
	
	public static eDataObject createWeighingMachine(Node node, EOJ eoj) {
		logger.info("Creating WeighingMachine from ECHONET Lite frame...");
		return new eWeighingMachine(node, eoj);
	}
	public static eDataObject createClinicalThermoMeter(Node node, EOJ eoj) {
		logger.warning("ClinicalThermoMeter specification has not been released");
		return null;
	}
	public static eDataObject createBloodPressuremeter(Node node, EOJ eoj) {
		logger.warning("BloodPressuremeter specification has not been released");
		return null;
	}
	public static eDataObject createBloodSugarmeter(Node node, EOJ eoj) {
		logger.warning("BloodSugarmeter specification has not been released");
		return null;
	}
	public static eDataObject createBodyFatmeter(Node node, EOJ eoj) {
		logger.warning("BodyFatmeter specification has not been released");
		return null;
	}


}
