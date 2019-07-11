package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.housing.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.object.mapper.DeviceIDEnum;

public class EPC0x84Handler {
	public static void refreshInstantaneousPower(Object obj, short newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(profile.getInstantaneousPower() != newVal) {
					profile.setInstantaneousPower(newVal);
					//TODO: More operation can be added here
				}		
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(light.getInstantaneousPower() != newVal) {
					light.setInstantaneousPower(newVal);
					//TODO: More operation can be added here
				}
				
			}
	
		}
	}

}
