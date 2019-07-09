package jaist.ac.jp.NodeFinder.echonet.objectmapper;

public enum DeviceIDEnum {
	GasLeakSensor(0x00 << 8 | 0x01),
	CrimePreventionSensor(0x00 << 8 | 0x02),
	EmergencyButton(0x00 << 8 | 0x03),
	FirstAidSensor(0x00 << 8 | 0x04),
	EarthquakeSensor(0x00 << 8 | 0x05),
	ElectricLeakSensor(0x00 << 8 | 0x06),
	HumanDetectionSensor(0x00 << 8 | 0x07),
	VisitorDetectionSensor(0x00 << 8 | 0x08),
	CallSensor(0x00 << 8 | 0x09),
	CondensationSensor(0x00 << 8 | 0x0A),
	AirPollutionSensor(0x00 << 8 | 0x0B),
	OxygenSensor(0x00 << 8 | 0x0C),
	IlluminanceSensor(0x00 << 8 | 0x0D),
	SoundSensor(0x00 << 8 | 0x0E),
	MailingSensor(0x00 << 8 | 0x0F),
	WeightSensor(0x00 << 8 | 0x10),
	TemperatureSensor(0x00 << 8 | 0x11),
	HumiditySensor(0x00 << 8 | 0x12),
	RainSensor(0x00 << 8 | 0x13),
	WaterLevelSensor(0x00 << 8 | 0x14),
	BathWaterLevelSensor(0x00 << 8 | 0x15),
	BathHeatingStatusSensor(0x00 << 8 | 0x16),
	WaterLeakSensor(0x00 << 8 | 0x17),
	WaterOverFlowSensor(0x00 << 8 | 0x18),
	FireSensor(0x00 << 8 | 0x19),
	CigaretteSmokeSensor(0x00 << 8 | 0x1A),
	CO2Sensor(0x00 << 8 | 0x1B),
	GasSensor(0x00 << 8 | 0x1C),
	VOCSensor(0x00 << 8 | 0x1D),
	DifferentialPressureSensor(0x00 << 8 | 0x1E),
	AirSpeedSensor(0x00 << 8 | 0x1F),
	OdorSensor(0x00 << 8 | 0x20),
	FlameSensor(0x00 << 8 | 0x21),
	ElectricEnergySensor(0x00 << 8 | 0x22),
	CurrentValueSensor(0x00 << 8 | 0x23),
	WaterFlowRateSensor(0x00 << 8 | 0x25),
	MicroMotionSensor(0x00 << 8 | 0x26),
	PassageSensor(0x00 << 8 | 0x27),
	BedPresenceSensor(0x00 << 8 | 0x28),
	OpenCloseSensor(0x00 << 8 | 0x29),
	ActivityMountSensor(0x00 << 8 | 0x2A),
	HumanBodyLocationSensor(0x00 << 8 | 0x2B),
	SnowSensor(0x00 << 8 | 0x2C),
	AirPressureSensor(0x00 << 8 | 0x2D),
	
	GeneralLight(0x02 << 8 | 0x90),
	
	
	Display(0x06 << 8 | 0x01),
	Television(0x06 << 8 | 0x02),
	Audio(0x06 << 8 | 0x03),
	NetworkCamera(0x06 << 8 | 0x04),
	
	
	Profile(0x0e << 8 | 0xf0);
	
	
	
	
	
	private int code; 
	private DeviceIDEnum(int code) {
		this.code = code;
	}
	public byte code() {
		return (byte) code;
	}

}
