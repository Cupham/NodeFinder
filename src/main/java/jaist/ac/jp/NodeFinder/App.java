package jaist.ac.jp.NodeFinder;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.logic.TooManyObjectsException;
import echowand.monitor.Monitor;
import echowand.monitor.MonitorListener;
import echowand.net.Inet4Subnet;
import echowand.net.Node;
import echowand.net.SubnetException;
import echowand.object.EchonetObjectException;
import echowand.service.Core;
import echowand.service.Service;
import jaist.ac.jp.NodeFinder.echonet.object.EchonetLiteDevice;
import jaist.ac.jp.NodeFinder.echonet.object.NodeProfileObject;
import jaist.ac.jp.NodeFinder.echonet.objectmapper.ServiceExecutor;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace; 

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger logger = Logger.getLogger(App.class.getName());
	public static Core echonetCore;
	public static Service echonetService;
	public static int counter_profileObj;
	public static int counter_deviceObj;
	public static List<EchonetLiteDevice> echonetLiteDevices;
	public static ServiceExecutor cmdExecutor;
	public static String networkInterface;
    public static void main( String[] args )	
    {
    	// init variables
    	echonetService = null;
    	echonetLiteDevices = new ArrayList<EchonetLiteDevice>();
    	counter_profileObj = 0;
    	counter_deviceObj = 0;
    	// get user input
    	logger.log(Level.INFO, "Main Program Started");
    	ArgumentParser parser = ArgumentParsers.newFor("EchonetNetworkMonitor").build()
                .defaultHelp(true)
                .description("Monitoring ECHONET Lite network using network interface, -i to specific network interface name");
    	parser.addArgument("-i", "--interface").help("network interface name");
    	Namespace ns  = null;
    	try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            logger.severe("can not parse input interface!!! " + e.toString());
            System.exit(1);
        }
    	networkInterface = ns.getString("interface");
    	
    	// start main program
    	start();
    	
    }
    
    public static boolean networkMonitor(String networkInterface) {
    		boolean isSuccessed = false;
    		if(echonetService == null) {
    			
    			NetworkInterface nif = null;
				try {
					nif = NetworkInterface.getByName(networkInterface);
				} catch (SocketException e1) {
					logger.log(Level.SEVERE, e1.toString());
				}
    			try {
					echonetCore = new Core(Inet4Subnet.startSubnet(nif));
				} catch (SubnetException e1) {
					logger.log(Level.SEVERE, e1.toString());
				}
    			try {
					echonetCore.startService();
				} catch (TooManyObjectsException e1) {
					logger.log(Level.SEVERE, e1.toString());
				} catch (SubnetException e1) {
					logger.log(Level.SEVERE, e1.toString());
				}
    			echonetService = new Service(echonetCore); 
    			cmdExecutor.setEchonetLiteService(echonetService);
    			Monitor monitor = new Monitor(echonetCore);
    			monitor.addMonitorListener(new MonitorListener() {
    	            @Override
    	            public void detectEOJsJoined(Monitor monitor, Node node, List<EOJ> eojs) {
    	            	logger.log(Level.INFO, "initialEchonetInterface: detectEOJsJoined: " + node + " " + eojs);
    	                EchonetLiteDevice eDevice = new EchonetLiteDevice(node);
    	                NodeProfileObject profile = null;        
    	                for(EOJ eoj :  eojs) {        	       
    	            	    if(eoj.isProfileObject()) {
    	            	    	counter_profileObj ++;
    	                		profile = new NodeProfileObject(node, eoj);
    	                		profile.profileObjectFromEPC(echonetService);
    	                		eDevice.setProfileObj(profile);
    	                	} else if(eoj.isDeviceObject()) {
    	                		try {
    	                			counter_deviceObj ++;
    								eDevice.parseDataObject(eoj,node,echonetService);
    							} catch (EchonetObjectException e) {
    								logger.log(Level.SEVERE, e.toString());
    							}
    	                	}
    	                }
    	                echonetLiteDevices.add(eDevice);              
    	            }

    	            @Override
    	            public void detectEOJsExpired(Monitor monitor, Node node, List<EOJ> eojs) {
    	            	System.out.println("initialEchonetInterface: detectEOJsExpired: " + node + " " + eojs);
    	            }
    			});
    			try {
					monitor.start();
				} catch (SubnetException e) {
					logger.log(Level.SEVERE,"CAN NOT START MONITORING INTERFACE!!!!!! " + e.toString());
					e.printStackTrace();
				}
    			isSuccessed = true;
    		} 
    		
    		if(isSuccessed) {
    			System.out.println("Initilized ECHONET API successfully!");
    		}
    		return isSuccessed;
    }
    	
    public static void start() {
    	
    	if(!"".equals(networkInterface)) {
    		logger.info(String.format("Start to monitor ECHONET Lite network from the %s interface",networkInterface));
    		networkMonitor(networkInterface);
    	} else {
    		logger.info("Do nothing due to no network interface has been choosen");
    	}
    	
    }
    
    public static void exit() {
    	
    }
}