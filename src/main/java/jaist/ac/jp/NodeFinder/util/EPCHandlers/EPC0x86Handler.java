package jaist.ac.jp.NodeFinder.util.EPCHandlers;

import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;
import jaist.ac.jp.NodeFinder.echonet.object.deviceobj.eGeneralLighting;
import jaist.ac.jp.NodeFinder.echonet.objectmapper.DeviceIDEnum;

public class EPC0x86Handler {
	public static void refreshManufacturerFaultCode(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass dev = (eSuperClass) obj;
			// Handle differently for each device
			if(dev.getType() == DeviceIDEnum.Profile){
				NodeProfileObject profile = (NodeProfileObject) dev;
				if(!newVal.equals(profile.getManufacturerFaultCode())) {
					profile.setManufacturerFaultCode(newVal);
					//TODO: More operation can be added here
				}		
			} else if(dev.getType() == DeviceIDEnum.GeneralLight){
				eGeneralLighting light = (eGeneralLighting) dev;
				if(!newVal.equals(light.getManufacturerFaultCode())) {
					light.setManufacturerFaultCode(newVal);
					//TODO: More operation can be added here
				}
				
			}
	
		}
	}

}
