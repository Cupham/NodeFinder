package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eGeneralLighting;

public class EPC0x8DHandler {
	public static void refreshProductNumber(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(newVal.equals(profile.getProductNumber())) {
					profile.setProductNumber(newVal);
					//TODO: More operation can be added here
				}		
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(newVal.equals(light.getProductNumber())) {
					light.setProductNumber(newVal);
					//TODO: More operation can be added here
				}
				
			}
	
		}
	}

}
