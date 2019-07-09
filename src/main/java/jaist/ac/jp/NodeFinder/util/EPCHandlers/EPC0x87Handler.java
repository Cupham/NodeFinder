package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.objectmapper.DeviceIDEnum;

public class EPC0x87Handler {
	public static void refreshCurrentLimitSetting(Object obj, int newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(profile.getCurrentLimitSetting() != newVal) {
					profile.setCurrentLimitSetting(newVal);
					//TODO: More operation can be added here
				}		
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(light.getCurrentLimitSetting() != newVal) {
					light.setCurrentLimitSetting(newVal);
					//TODO: More operation can be added here
				}
				
			}
	
		}
	}

}
