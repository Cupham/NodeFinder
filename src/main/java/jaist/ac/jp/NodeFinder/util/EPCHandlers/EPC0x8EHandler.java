package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import java.util.Date;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.objectmapper.DeviceIDEnum;

public class EPC0x8EHandler {
	public static void refreshProductDate(Object obj, Date newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(profile.getProductDate() != newVal) {
					profile.setProductDate(newVal);
					//TODO: More operation can be added here
				}		
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(light.getProductDate() != newVal) {
					light.setProductDate(newVal);
					//TODO: More operation can be added here
				}
				
			}
	
		}
	}

}
