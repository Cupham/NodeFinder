package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import java.util.Date;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;

public class EPC0x98Handler {
	public static void refreshCurrentDateSetting(Object obj, Date newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(profile.getCurrentDateSetting() != newVal) {
					profile.setCurrentDateSetting(newVal);
					//TODO: More operation can be added here
				}		
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(light.getCurrentDateSetting() != newVal) {
					light.setCurrentDateSetting(newVal);
					//TODO: More operation can be added here
				}
				
			}
	
		}
	}

}
