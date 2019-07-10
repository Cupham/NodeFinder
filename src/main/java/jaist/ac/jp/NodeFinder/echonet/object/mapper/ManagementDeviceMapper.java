package jaist.ac.jp.NodeFinder.echonet.object.mapper;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.management.eController;
import jaist.ac.jp.NodeFinder.echonet.object.management.eDREventController;
import jaist.ac.jp.NodeFinder.echonet.object.management.eParallelProcessingCombinationTypePowerControl;
import jaist.ac.jp.NodeFinder.echonet.object.management.eSwitch;

public class ManagementDeviceMapper {
	private static final Logger logger = Logger.getLogger(HeathDeviceMapper.class.getName());
	
	public static eDataObject createParallelProcessingCombinationTypePowerControl(Node node, EOJ eoj) {
		logger.info("Creating ParallelProcessingCombinationTypePowerControl from ECHONET Lite frame...");
		return new eParallelProcessingCombinationTypePowerControl(node, eoj);
	}
	public static eDataObject createDREventController(Node node, EOJ eoj) {
		logger.info("Creating DREventController from ECHONET Lite frame...");
		return new eDREventController(node, eoj);
	}
	public static eDataObject createSecureCommunicationSharedKeySetupNode(Node node, EOJ eoj) {
		logger.warning("SecureCommunicationSharedKeySetupNode specification has not been released");
		return null;
	}
	public static eDataObject createSwitch(Node node, EOJ eoj) {
		logger.info("Creating Switch from ECHONET Lite frame...");
		return new eSwitch(node, eoj);
	}
	public static eDataObject createPortableTerminal(Node node, EOJ eoj) {
		logger.warning("PortableTerminal specification has not been released");
		return null;
	}
	public static eDataObject createController(Node node, EOJ eoj) {
		logger.info("Creating Controller from ECHONET Lite frame...");
		return new eController(node, eoj);
	}
}
