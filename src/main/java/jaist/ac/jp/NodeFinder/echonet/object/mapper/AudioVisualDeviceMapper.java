package jaist.ac.jp.NodeFinder.echonet.object.mapper;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.av.eAudio;
import jaist.ac.jp.NodeFinder.echonet.object.av.eDisplay;
import jaist.ac.jp.NodeFinder.echonet.object.av.eNetworkCamera;
import jaist.ac.jp.NodeFinder.echonet.object.av.eTelevision;

public class AudioVisualDeviceMapper {
	private static final Logger logger = Logger.getLogger(HeathDeviceMapper.class.getName());
	
	public static eDataObject createDisplay(Node node, EOJ eoj) {
		logger.info("Creating Display from ECHONET Lite frame...");
		return new eDisplay(node, eoj);
	}
	public static eDataObject createTelevision(Node node, EOJ eoj) {
		logger.info("Creating Television from ECHONET Lite frame...");
		return new eTelevision(node, eoj);
	}
	public static eDataObject createAudio(Node node, EOJ eoj) {
		logger.info("Creating Audio from ECHONET Lite frame...");
		return new eAudio(node, eoj);
	}
	public static eDataObject createNetworkCamera(Node node, EOJ eoj) {
		logger.info("Creating NetworkCamera from ECHONET Lite frame...");
		return new eNetworkCamera(node, eoj);
	}

}
