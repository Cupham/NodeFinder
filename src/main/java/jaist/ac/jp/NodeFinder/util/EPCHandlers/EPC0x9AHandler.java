package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.objectmapper.DeviceIDEnum;

public class EPC0x9AHandler {
	public static void refreshCumulativeTime(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(!newVal.equals(profile.getCumulativeTime())) {
					profile.setCumulativeTime(newVal);
					//TODO: More operation can be added here
				}		
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(!newVal.equals(light.getCumulativeTime())) {
					light.setCumulativeTime(newVal);
					//TODO: More operation can be added here
				}
				
			}
	
		}
	}

}
