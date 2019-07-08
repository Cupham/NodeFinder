package jaist.ac.jp.NodeFinder.util;


import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.common.EPC;
import echowand.net.Node;
import echowand.object.EchonetObjectException;
import echowand.object.ObjectData;
import echowand.object.RemoteObject;
import echowand.service.Service;
import jaist.ac.jp.NodeFinder.echonet.object.eSuperClass;

public class EDTParser {
	private static final Logger logger = Logger.getLogger(EDTParser.class.getName());
	
	public static void refreshOperationStatus(Object obj, boolean newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(cl.getOperationStatus() != newVal) {
				cl.operationStatus = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
	}
	
	public static void refreshInstallLocation(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getInstallLocation())) {
				cl.installLocation = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	public static void refreshStandardVersionInfo(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getStandardVersionInfo())) {
				cl.standardVersionInfo = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	public static void refreshIdentificationNumber(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getIdentificationNumber())) {
				cl.identificationNumber = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	public static void refreshInstantaneousPower(Object obj, short newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(cl.getInstantaneousPower() != newVal) {
				cl.instantaneousPower = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	public static void refreshCumulativePower(Object obj, long newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(cl.getCumulativePower() != newVal) {
				cl.cumulativePower = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	public static void refreshManufactureerFaultCode(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getManufacturerFaultCode())) {
				cl.manufacturerFaultCode = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	
	public static void refreshCurrentLimitSetting(Object obj, int newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(cl.getCurrentLimitSetting() != newVal) {
				cl.currentLimitSetting = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	public static void refreshFaultStatus(Object obj, boolean newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(cl.isFaultStatus() != newVal) {
				cl.faultStatus = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	public static void refreshFaultDescription(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getFaultDescription())) {
				cl.faultDescription = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	public static void refreshManufacturerCode(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getManufacturerCode())) {
				cl.manufacturerCode = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	public static void refreshBusinessFacilityCode(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getBusinessFacilityCode())) {
				cl.businessFacilityCode = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	public static void refreshProductCode(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getProductCode())) {
				cl.productCode = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	public static void refreshProductNumber(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getProductNumber())) {
				cl.productNumber = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	public static void refreshProductDate(Object obj, Date newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(newVal != cl.getProductDate()) {
				cl.productDate = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	public static void refreshPowerSaving(Object obj, boolean newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(cl.getPowerSaving() != newVal) {
				cl.powerSaving = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	public static void refreshThroughPublicNetwork(Object obj, boolean newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(cl.getThroughPublicNetwork() != newVal) {
				cl.throughPublicNetwork = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	public static void refreshCurrentTimeSetting(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getCurrentTimeSetting())) {
				cl.currentTimeSetting = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	public static void refreshCurrentDateSetting(Object obj, Date newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(newVal != cl.getCurrentDateSetting()) {
				cl.currentDateSetting = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	public static void refreshPowerLimit(Object obj, short newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(cl.getPowerLimit() != newVal) {
				cl.powerLimit = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	
	public static void refreshCumulativeTime(Object obj, String newVal) {
		if(obj instanceof eSuperClass) {
			eSuperClass cl = (eSuperClass) obj;
			if(!newVal.equals(cl.getCumulativeTime())) {
				cl.cumulativeTime = newVal;
				// TODO
			}
		} else if(obj.getClass().equals("")) {
			
		}
		
	}
	// Command executor to update device infor
	public static boolean executeCommand(Service service,Node node, EOJ eoj, EPC epc, ObjectData data) {
		boolean rs = false;
		service.registerRemoteEOJ(node, eoj);
		RemoteObject remoteObject = service.getRemoteObject(node, eoj);
		System.out.println(String.format("Execute command [IP:%s, EOJ:%s, Data:%s]",node.getNodeInfo().toString(),eoj,data));
		logger.info(String.format("Execute command [IP:%s, EOJ:%s, Data:%s]",node.getNodeInfo().toString(),eoj,data));
		try {
			if (remoteObject.setData(epc, data)) {
				rs= true;
				System.out.println(String.format("Completed: [IP:%s, EOJ:%s, Data:%s]",node.getNodeInfo().toString(),eoj,data));
				logger.info(String.format("Completed: [IP:%s, EOJ:%s, Data:%s]",node.getNodeInfo().toString(),eoj,data));
			}
		} catch (EchonetObjectException e) {
			System.out.println("Can not find object: " +e.toString());
			logger.log(Level.SEVERE, "Can not find object: " +e.toString());
			rs= false;
		}
		return rs;
	
	}
	
}
