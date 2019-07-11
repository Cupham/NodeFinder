package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import java.util.logging.Logger;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;

public class EPC0x80Handler {
	private static final Logger logger = Logger.getLogger(EPC0x80Handler.class.getName());
	
	public static void refreshOperationStatus(Object obj, boolean newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			System.out.println(dev.getType());
			if(dev.getOperationStatus() != newVal) {
				dev.setOperationStatus(newVal);
				//TODO: More operation can be added here
				
			}
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(profile.getOperationStatus() != newVal) {
					profile.setOperationStatus(newVal);
					//TODO: More operation can be added here
					
				}
				
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(light.getOperationStatus() != newVal) {
					light.setOperationStatus(newVal);
				}
				
			}
			
			
		}
	}
}
