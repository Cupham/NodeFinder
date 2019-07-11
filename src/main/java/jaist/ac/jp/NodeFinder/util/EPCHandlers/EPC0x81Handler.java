package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;

public class EPC0x81Handler {
	public static void refreshInstallLocation(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(!newVal.equals(profile.getInstallLocation())) {
					profile.setInstallLocation(newVal);
					//TODO: More operation can be added here
				}		
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(!newVal.equals(light.getInstallLocation())) {
					light.setInstallLocation(newVal);
					//TODO: More operation can be added here
				}
				
			}
	
		}
	}

}
