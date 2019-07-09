package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.sensors.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.objectmapper.DeviceIDEnum;

public class EPC0x8FHandler {
	public static void refreshPowerSaving(Object obj, boolean newVal){
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(profile.getPowerSaving()!= newVal) {
					profile.setPowerSaving(newVal);
					//TODO: More operation can be added here
				}		
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(light.getPowerSaving() != newVal) {
					light.setPowerSaving(newVal);
					//TODO: More operation can be added here
				}
				
			}
	
		}
	}

}
