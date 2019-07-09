package jaist.ac.jp.NodeFinder.echonet.objectmapper;

public enum DeviceIDEnum {
	GasLeakSensor(0x00 << 8 | 0x01),
	CrimePreventionSensor(0x00 << 8 | 0x02),
	EmergencyButton(0x00 << 8 | 0x03),
	FirstAidSensor(0x00 << 8 | 0x04),
	EarthquakeSensor(0x00 << 8 | 0x05),
	ElectricLeakSensor(0x00 << 8 | 0x06),
	HumanDetectionSensor(0x00 << 8 | 0x07),
	GeneralLight(0x02 << 8 | 0x90),
	
	
	
	
	
	Profile(0x0e << 8 | 0xf0);
	
	
	
	
	
	private int code; 
	private DeviceIDEnum(int code) {
		this.code = code;
	}
	public byte code() {
		return (byte) code;
	}

}
