package jaist.ac.jp.NodeFinder.echonet.object.sensors;



import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.common.EPC;
import echowand.net.Node;
import echowand.net.SubnetException;
import echowand.service.Service;
import echowand.service.result.GetListener;
import echowand.service.result.GetResult;
import echowand.service.result.ResultData;
import echowand.service.result.ResultFrame;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.objectmapper.DeviceIDEnum;
import jaist.ac.jp.NodeFinder.util.EchonetDataConverter;
import jaist.ac.jp.NodeFinder.util.SampleConstants;

public class eElectricEnergySensor extends eDataObject{
	private static final Logger logger = Logger.getLogger(eElectricEnergySensor.class.getName());
	private Timer timer;
	private float cumulativeElectricEnergyValue;
	private long mediumCapacitySensorInstantaneousElectricEnergy;
	private float smallCapacitySensorInstantaneousElectricEnergy;
	private float largeCapacitySensorInstantaneousElectricEnergy;
	private float cumulativeElectricEnergyMeasureLog;
	private int effectiveVoltage;
	
	public eElectricEnergySensor(Node node,EOJ eoj) {
		super(node, eoj);
		this.setGroupCode((byte) 0x00);
		this.setClassCode((byte) 0x22);
		this.setInstanceCode(eoj.getInstanceCode());
		setType(DeviceIDEnum.ElectricEnergySensor);
	}
	
	// Provided Services			
	// Device Property Monitoring
	public void	refreshCumulativeElectricEnergyValue(float val) {
		if(val != this.getCumulativeElectricEnergyValue()) {
			logger.info(String.format("CumulativeElectricEnergyValue changed from %f to %f",getCumulativeElectricEnergyValue(),val));
			setCumulativeElectricEnergyValue(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshMediumCapacitySensorInstantaneousElectricEnergy(long val) {
		if(val != this.getMediumCapacitySensorInstantaneousElectricEnergy()) {
			logger.info(String.format("MediumCapacitySensorInstantaneousElectricEnergy changed from %f to %f",getMediumCapacitySensorInstantaneousElectricEnergy(),val));
			setMediumCapacitySensorInstantaneousElectricEnergy(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshSmallCapacitySensorInstantaneousElectricEnergy(float val) {
		if(val != this.getSmallCapacitySensorInstantaneousElectricEnergy()) {
			logger.info(String.format("SmallCapacitySensorInstantaneousElectricEnergy changed from %f to %f",getSmallCapacitySensorInstantaneousElectricEnergy(),val));
			setSmallCapacitySensorInstantaneousElectricEnergy(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshLargeCapacitySensorInstantaneousElectricEnergy(float val) {
		if(val != this.getLargeCapacitySensorInstantaneousElectricEnergy()) {
			logger.info(String.format("LargeCapacitySensorInstantaneousElectricEnergy changed from %f to %f",getLargeCapacitySensorInstantaneousElectricEnergy(),val));
			setLargeCapacitySensorInstantaneousElectricEnergy(val);
			//TODO: More task can be added here
		}
	}
	public void	refreshCumulativeElectricEnergyMeasureLog(float val) {
		if(val != this.getCumulativeElectricEnergyMeasureLog()) {
			logger.info(String.format("CumulativeElectricEnergyMeasureLog changed from %f to %f",getCumulativeElectricEnergyMeasureLog(),val));
			setCumulativeElectricEnergyMeasureLog(val);
			//TODO: More task can be added here
		}
	}
	
	public void	refreshEffectiveVoltage(int val) {
		if(val != this.getEffectiveVoltage()) {
			logger.info(String.format("EffectiveVoltage changed from %d to %d",getEffectiveVoltage(),val));
			setEffectiveVoltage(val);
			//TODO: More task can be added here
		}
	}
	
	
	// Override functions
	@Override
	public void dataFromEOJ(Service s){
		final Service service = s;
		final Node node = this.getNode();
		final EOJ eoj = this.getEoj();
		observeSuperData(service, node, eoj);
		observeSpecificData(service);
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				getSpecificData(service);
				getSuperData(service, node, eoj);
				
			}
		}, SampleConstants.getDelayInterval(), SampleConstants.getRefreshInterval());	
	}
	
	
 	private void getSpecificData(Service service){
		ArrayList<EPC> epcs = new ArrayList<EPC>();
		epcs.add(EPC.xE0);
		epcs.add(EPC.xE1);
		epcs.add(EPC.xE2);
		epcs.add(EPC.xE3);
		epcs.add(EPC.xE4);
		epcs.add(EPC.xE5);
		try {
			service.doGet(this.getNode(), this.getEoj(), epcs, 5000, new GetListener() {
				@Override
			    public void receive(GetResult result, ResultFrame resultFrame, ResultData resultData) {
					if (resultData.isEmpty()) {
						return;
					}
					switch (resultData.getEPC()) 
					{
						case xE0:
							refreshCumulativeElectricEnergyValue(EchonetDataConverter.dataToLong(resultData)*0.001f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE0, EDT: 0x%02X}=={CumulativeElectricEnergyValue:%f kWh}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getCumulativeElectricEnergyValue()));
						break;
						case xE1:
							refreshMediumCapacitySensorInstantaneousElectricEnergy(EchonetDataConverter.dataToLong(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE1, EDT: 0x%02X}=={MediumCapacitySensorInstantaneousElectricEnergy:%d W}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getMediumCapacitySensorInstantaneousElectricEnergy()));
						break;
						case xE2:
							refreshSmallCapacitySensorInstantaneousElectricEnergy(EchonetDataConverter.dataToShort(resultData)*0.1f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE2, EDT: 0x%02X}=={SmallCapacitySensorInstantaneousElectricEnergy:%f W}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getSmallCapacitySensorInstantaneousElectricEnergy()));
						break;
						case xE3:
							refreshLargeCapacitySensorInstantaneousElectricEnergy(EchonetDataConverter.dataToShort(resultData)*0.1f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE3, EDT: 0x%02X}=={LargeCapacitySensorInstantaneousElectricEnergy:%f kW}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getLargeCapacitySensorInstantaneousElectricEnergy()));
						break;
						case xE4:
							refreshCumulativeElectricEnergyMeasureLog(EchonetDataConverter.dataToLong(resultData)*0.001f);
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE4, EDT: 0x%02X}=={CumulativeElectricEnergyMeasureLog:%f kWh}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getCumulativeElectricEnergyMeasureLog()));
						break;
						case xE5:
							refreshEffectiveVoltage(EchonetDataConverter.dataToShort(resultData));
							logger.info(String.format("Node:%s@EOJ:%s {EPC:0xE5, EDT: 0x%02X}=={EffectiveVoltage:%d V}",
									 getNode().getNodeInfo().toString(),getEoj().toString(),resultData.toBytes()[0],getEffectiveVoltage()));
						break;
						default:
							break;
					}	
				}	
			});
		} catch (SubnetException ex) {
			logger.log(Level.SEVERE, ex.toString());
		}
	}



	@Override
	public void observeSpecificData(Service service) {
	}

	public float getCumulativeElectricEnergyValue() {
		return cumulativeElectricEnergyValue;
	}

	public void setCumulativeElectricEnergyValue(float cumulativeElectricEnergyValue) {
		this.cumulativeElectricEnergyValue = cumulativeElectricEnergyValue;
	}

	public long getMediumCapacitySensorInstantaneousElectricEnergy() {
		return mediumCapacitySensorInstantaneousElectricEnergy;
	}

	public void setMediumCapacitySensorInstantaneousElectricEnergy(
			long mediumCapacitySensorInstantaneousElectricEnergy) {
		this.mediumCapacitySensorInstantaneousElectricEnergy = mediumCapacitySensorInstantaneousElectricEnergy;
	}

	public float getSmallCapacitySensorInstantaneousElectricEnergy() {
		return smallCapacitySensorInstantaneousElectricEnergy;
	}

	public void setSmallCapacitySensorInstantaneousElectricEnergy(float smallCapacitySensorInstantaneousElectricEnergy) {
		this.smallCapacitySensorInstantaneousElectricEnergy = smallCapacitySensorInstantaneousElectricEnergy;
	}

	public float getCumulativeElectricEnergyMeasureLog() {
		return cumulativeElectricEnergyMeasureLog;
	}

	public void setCumulativeElectricEnergyMeasureLog(float cumulativeElectricEnergyMeasureLog) {
		this.cumulativeElectricEnergyMeasureLog = cumulativeElectricEnergyMeasureLog;
	}

	public float getLargeCapacitySensorInstantaneousElectricEnergy() {
		return largeCapacitySensorInstantaneousElectricEnergy;
	}

	public void setLargeCapacitySensorInstantaneousElectricEnergy(float largeCapacitySensorInstantaneousElectricEnergy) {
		this.largeCapacitySensorInstantaneousElectricEnergy = largeCapacitySensorInstantaneousElectricEnergy;
	}

	public int getEffectiveVoltage() {
		return effectiveVoltage;
	}

	public void setEffectiveVoltage(int effectiveVoltage) {
		this.effectiveVoltage = effectiveVoltage;
	}
	

}
