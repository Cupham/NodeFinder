/*******************************************************************************
 * Copyright 2019 Cu Pham
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
/**
 * Convert DataObject
 */
package jaist.ac.jp.NodeFinder.echonet.object.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import echowand.common.EPC;
import echowand.object.EchonetObjectException;
import echowand.object.ObjectData;
import echowand.service.result.ResultData;

/**
 * @author Cu Pham
 *
 */
public class eConverter {

	private final static String notFaultDescription = "The fault description is not found!";
	public enum FaultType {
		Recoverable_Faults, Require_Repair_Faults, Undefined
	};

	/**
	 * convert data to integer value. Apply for value have max 4 bytes: status,
	 * temperature, ...
	 * 
	 * @param data
	 * @return byte value
	 */
	public static byte dataToByte(ObjectData data) {
		return (new BigInteger(data.toBytes()).byteValue());
	}
	public static byte dataToByte(ResultData data) {
		return (new BigInteger(data.toBytes()).byteValue());
	}

	/**
	 * convert data to integer value. Apply for value have max 4 bytes: status,
	 * temperature, ...
	 * 
	 * @param data
	 * @return integer value
	 */
	public static int dataToInteger(ObjectData data) {
		return (new BigInteger(data.toBytes()).intValue());
	}
	public static int dataToInteger(ResultData data) {
		return (new BigInteger(data.toBytes()).intValue());
	}
	
	public static float dataToFloat(ObjectData data) {
		return (new BigInteger(data.toBytes()).floatValue());
	}
	public static float dataToFloat(ResultData data) {
		return (new BigInteger(data.toBytes()).floatValue());
	}
	/**
	 * convert data to short value. Apply for value have max 2 bytes.
	 * 
	 * @param data
	 * @return short value
	 */
	public static short dataToShort(ObjectData data) {
		return (new BigInteger(data.toBytes()).shortValue());
	}
	public static short dataToShort(ResultData data) {
		return (new BigInteger(data.toBytes()).shortValue());
	}

	/**
	 * convert data to long value. Apply for value have max 8 bytes.
	 * 
	 * @param data
	 * @return long value
	 */
	public static long dataToLong(ObjectData data) {
		return (new BigInteger(data.toBytes()).longValue());
	}
	public static long dataToLong(ResultData data) {
		return (new BigInteger(data.toBytes()).longValue());
	}

	/**
	 * convert data to integer value. Apply for value have max 4 bytes: status,
	 * temperature, ...
	 * 
	 * @param input
	 * @return integer value
	 */
	public static int dataToInteger(byte[] input) {
		return (new BigInteger(input).intValue());
	}
	
	public static float datatoFloat(byte[] input) {
		return (new BigInteger(input).floatValue());
	}

	/**
	 * convert data to string value.
	 * 
	 * @param input
	 * @return string value
	 */
	public static String dataToString(ObjectData data) {
		return (new String(data.toBytes()));
	}
	public static String dataToString(ResultData data) {
		return (new String(data.toBytes()));
	}

	/**
	 * Convert to standard version
	 * 
	 * @param data
	 * @return string as standard version
	 */
	public static String dataToVersion(ObjectData data) {
		int dataSize = data.size();

		if (dataSize != 4) {
			return "Invalid";
		}

		int major = (0x000000ff) & data.get(0);
		int minor = (0x000000ff) & data.get(1);
		int tail = (0x000000ff) & data.get(3);
		byte b3 = data.get(2);

		String supported = "" + (char) b3;

		String versionString = String.format("%d.%d (%s) %d", major, minor, supported, tail);

		return versionString;
	}
	public static String dataToVersion(ResultData data) {
		int dataSize = data.size();

		if (dataSize != 4) {
			return "Invalid";
		}

		int major = (0x000000ff) & data.get(0);
		int minor = (0x000000ff) & data.get(1);
		int tail = (0x000000ff) & data.get(3);
		byte b3 = data.get(2);

		String supported = "" + (char) b3;

		String versionString = String.format("%d.%d (%s) %d", major, minor, supported, tail);

		return versionString;
	}

	/**
	 * Convert to fault code
	 * 
	 * @param data
	 * @return string as fault code
	 */
	public static String dataToFaultCode(ObjectData data) {
		int dataSize = data.size();

		if (dataSize <= 5) {
			return "Invalid";
		}

		int size = (0x000000ff) & data.get(0);
		if (size < 1) {
			return "Invalid";
		}
		byte manuCode[] = new byte[3];
		manuCode[0] = data.get(1);
		manuCode[1] = data.get(2);
		manuCode[2] = data.get(3);
		String manCode = new String(manuCode);
		size = (dataSize < size) ? dataSize : size;
		byte stringFault[] = new byte[size];
		int j = 0;

		for (int i = 4; (i < dataSize && j < size); i++) {
			stringFault[j++] = data.get(i);
		}
		String fault = new String(stringFault);
		String faultCode = String.format("%s - %s", manCode, fault);
		return faultCode;
	}
	public static String dataToFaultCode(ResultData data) {
		int dataSize = data.size();

		if (dataSize <= 5) {
			return "Invalid";
		}

		int size = (0x000000ff) & data.get(0);
		if (size < 1) {
			return "Invalid";
		}
		byte manuCode[] = new byte[3];
		manuCode[0] = data.get(1);
		manuCode[1] = data.get(2);
		manuCode[2] = data.get(3);
		String manCode = new String(manuCode);
		size = (dataSize < size) ? dataSize : size;
		byte stringFault[] = new byte[size];
		int j = 0;

		for (int i = 4; (i < dataSize && j < size); i++) {
			stringFault[j++] = data.get(i);
		}
		String fault = new String(stringFault);
		String faultCode = String.format("%s - %s", manCode, fault);
		return faultCode;
	}
	
	/**
	 * Convert to data AirConditioner OperationMode
	 * 
	 * @param data
	 * @return string as fault code
	 */
	public static String dataToAirConditionerOperationMode(ObjectData data) {
		int dataSize = data.size();
		String rs = "";
		if (dataSize != 1) {
			rs =  "Invalid";
		}

		switch (data.get(0)) {
			case (byte)0x41:
				rs = "Automatic";
				break;
			case (byte) 0x42:
				rs = "Cooling";
				break;
			case (byte) 0x43:
				rs = "Heating";
				break;
			case (byte) 0x44:
				rs = "Dehumidification";
				break;
			case (byte) 0x45:
				rs = "Air circulator";
			break;
			case (byte) 0x40:
				rs = "Other";
			break;
			default:
				rs = "Invalid";
				break;
		}
		
		return rs;
	}
	public static String dataToStringCode(ResultData data) {
		int dataSize = data.size();
		String rs = "";
		if (dataSize >= 247) {
			rs =  "Invalid";
		}

		switch (data.get(1)) {
			case (byte)0x01:
				rs = "ANSI X3.4";
				break;
			case (byte) 0x02:
				rs = "Shift –JIS";
				break;
			case (byte) 0x03:
				rs = "JIS";
				break;
			case (byte) 0x04:
				rs = "Japanese EUC";
				break;
			case (byte) 0x05:
				rs = "UCS-4";
			break;
			case (byte) 0x06:
				rs = "UCS-2 ";
			break;
			case (byte) 0x07:
				rs = "Latin-1";
			break;
			case (byte) 0x08:
				rs = "UTF-8 ";
			break;
			default:
				rs = "NotSet";
				break;
		}
		
		return rs;
	}
	public static enum AudioInput {
		BuildInTurner((byte)0x00),
		BuildInOpticalDiskDevice((byte)0x01),
		BuildInMD((byte)0x02),
		BuildInCassettle((byte)0x08),
		ExternalAnalog((byte)0x10),
		ExternalHDMI((byte)0x11),
		ExternalUsb((byte)0x20),
		BuildInMemoryCard((byte)0x21),
		BuildInStorage((byte)0x22),
		DedicateTerminal((byte)0x23),
		NetworkInput((byte)0x30),
		NotSet((byte)0xFE),
		Others((byte)0xFF);
		
		private byte code; 
		private AudioInput(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static AudioInput fromCode(byte code) {
	        for (AudioInput i : AudioInput.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	
	public static enum FITContractType {
		FIT((byte)0x41),
		NonFIT((byte)0x42),
		NoSetting((byte)0x43);
		
		private byte code; 
		private FITContractType(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static FITContractType fromCode(byte code) {
	        for (FITContractType i : FITContractType.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum SelfConsumptionType {
		WithSelfConsumption((byte)0x41),
		WithoutSelfConsumption((byte)0x42),
		Unknown((byte)0x43);
		
		private byte code; 
		private SelfConsumptionType(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static SelfConsumptionType fromCode(byte code) {
	        for (SelfConsumptionType i : SelfConsumptionType.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	
	public static enum SystemInterconnectedType {
		SystemInterconnectedType_ReservePowerFlowAcceptable((byte)0x00),
		IndependentType((byte)0x00),
		SystemInterconnectedType_ReservePowerFlowNotAcceptable((byte)0x02),
		Unknown((byte)0x03);
		
		private byte code; 
		private SystemInterconnectedType(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static SystemInterconnectedType fromCode(byte code) {
	        for (SystemInterconnectedType i : SystemInterconnectedType.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum OutputPowerRestraint {
		OngoingRestraint_OutputPowerControl((byte)0x41),
		OngoingRestraint_ExceptOutputPowerControl((byte)0x42),
		OngoingRestraint_ReasonUnknown((byte)0x43),
		NotRestraint((byte)0x44),
		Unknown((byte)0x45);
		
		private byte code; 
		private OutputPowerRestraint(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static OutputPowerRestraint fromCode(byte code) {
	        for (OutputPowerRestraint i : OutputPowerRestraint.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	
	public static enum OpenCloseStop{
		Open((byte)0x41),
		Close((byte)0x42),
		Stop((byte)0x43);
		
		private byte code; 
		private OpenCloseStop(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static OpenCloseStop fromCode(byte code) {
	        for (OpenCloseStop i : OpenCloseStop.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum WaterTemperature{
		Cooling_Level1((byte)0x21),
		Cooling_Level2((byte)0x22),
		Cooling_Level3((byte)0x23),
		Cooling_Level4((byte)0x24),
		Cooling_Level5((byte)0x25),
		Cooling_Level6((byte)0x26),
		Cooling_Level7((byte)0x27),
		Cooling_Level8((byte)0x28),
		Cooling_Level9((byte)0x29),
		Cooling_Level10((byte)0x2A),
		Cooling_Level11((byte)0x2B),
		Cooling_Level12((byte)0x2C),
		Cooling_Level13((byte)0x2D),
		Cooling_Level14((byte)0x2E),
		Cooling_Level15((byte)0x2F),
		Heating_Level1((byte)0x31),
		Heating_Level2((byte)0x32),
		Heating_Level3((byte)0x33),
		Heating_Level4((byte)0x34),
		Heating_Level5((byte)0x35),
		Heating_Level6((byte)0x36),
		Heating_Level7((byte)0x37),
		Heating_Level8((byte)0x38),
		Heating_Level9((byte)0x39),
		Heating_Level10((byte)0x3A),
		Heating_Level11((byte)0x3B),
		Heating_Level12((byte)0x3C),
		Heating_Level13((byte)0x3D),
		Heating_Level14((byte)0x3E),
		Heating_Level15((byte)0x3F),
		Auto((byte)0x41);
		
		private byte code; 
		private WaterTemperature(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static WaterTemperature fromCode(byte code) {
	        for (WaterTemperature i : WaterTemperature.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum TemperatureSetting{
		Level1((byte)0x31),
		Level2((byte)0x32),
		Level3((byte)0x33),
		Level4((byte)0x34),
		Level5((byte)0x35),
		Level6((byte)0x36),
		Level7((byte)0x37),
		Level8((byte)0x38),
		Level9((byte)0x39),
		Level10((byte)0x3A),
		Level11((byte)0x3B),
		Level12((byte)0x3C),
		Level13((byte)0x3D),
		Level14((byte)0x3E),
		Level15((byte)0x3F),
		Auto((byte)0x41);
		
		private byte code; 
		private TemperatureSetting(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static TemperatureSetting fromCode(byte code) {
	        for (TemperatureSetting i : TemperatureSetting.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum OpenCloseStatus{
		FullyOpen((byte)0x41),
		FullyClosed((byte)0x42),
		Open((byte)0x43),
		Closed((byte)0x44),
		StoppedHalfway((byte)0x45);
		
		private byte code; 
		private OpenCloseStatus(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static OpenCloseStatus fromCode(byte code) {
	        for (OpenCloseStatus i : OpenCloseStatus.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum AlarmStatus{
		Normal((byte)0x40),
		BreakOpen((byte)0x41),
		DoorOpen((byte)0x42),
		ManualLocked((byte)0x43),
		Tampered((byte)0x44);
		
		private byte code; 
		private AlarmStatus(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static AlarmStatus fromCode(byte code) {
	        for (AlarmStatus i : AlarmStatus.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	
	public static enum WaterHeaterSetting{
		AutomaticWaterHeatingUsed((byte)0x41),
		NonAutomaticWaterHeatingStopped((byte)0x42),
		NonAutomaticWaterHeatingUsed((byte)0x43);
		
		private byte code; 
		private WaterHeaterSetting(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static WaterHeaterSetting fromCode(byte code) {
	        for (WaterHeaterSetting i : WaterHeaterSetting.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum BathOperationStatus{
		FillingHotWater((byte)0x41),
		Stopped((byte)0x42),
		KeepingTemperature((byte)0x43);
		
		private byte code; 
		private BathOperationStatus(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static BathOperationStatus fromCode(byte code) {
	        for (BathOperationStatus i : BathOperationStatus.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum SelectiveOperation{
		DegreeOfSettingPossition_Open((byte)0x41),
		OperationTimeSettingValue_Open((byte)0x42),
		OperationTimeSettingValue_Close((byte)0x43),
		UserDefine((byte)0x44);
		
		private byte code; 
		private SelectiveOperation(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static SelectiveOperation fromCode(byte code) {
	        for (SelectiveOperation i : SelectiveOperation.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum HeatingShiftTime{
		Nine_AM((byte)0x09),
		Ten_AM((byte)0x1A),
		Eleven_AM((byte)0x1B),
		Twelve_PM((byte)0x1C),
		One_PM((byte)0x1D),
		Two_PM((byte)0x1E),
		Three_PM((byte)0x1F),
		Four_PM((byte)0x10),
		Five_PM((byte)0x11),
		Eight_PM((byte)0x14),
		Nine_PM((byte)0x15),
		Ten_PM((byte)0x16),
		Eleven_PM((byte)0x17),
		Twelve_AM((byte)0x18),
		One_AM((byte)0x01),
		Clear((byte)0x00);
		
		private byte code; 
		private HeatingShiftTime(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static HeatingShiftTime fromCode(byte code) {
	        for (HeatingShiftTime i : HeatingShiftTime.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	
	public static enum AirFlowRate{
		Automatic((byte)0x41),
		Level_1((byte)0x31),
		Level_2((byte)0x32),
		Level_3((byte)0x33),
		Level_4((byte)0x34),
		Level_5((byte)0x35),
		Level_6((byte)0x36),
		Level_7((byte)0x37),
		Level_8((byte)0x38);
		
		private byte code; 
		private AirFlowRate(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static AirFlowRate fromCode(byte code) {
	        for (AirFlowRate i : AirFlowRate.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum AirconditionerOperationMode {
		Automatic((byte)0x41),
		Cooling((byte)0x42),
		Heating((byte)0x43),
		Dehumidification((byte)0x44),
		AirCirculator((byte)0x45),
		Others((byte)0x40);
		
		private byte code; 
		private AirconditionerOperationMode(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static AirconditionerOperationMode fromCode(byte code) {
	        for (AirconditionerOperationMode i : AirconditionerOperationMode.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum HumidifyingSetting {
		AutomaticSetting((byte)0x70),
		ContinuousOperation((byte)0x71),
		IntermittentOperation((byte)0x72),
		Level_1((byte)0x31),
		Level_2((byte)0x32),
		Level_3((byte)0x33);
		
		private byte code; 
		private HumidifyingSetting(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static HumidifyingSetting fromCode(byte code) {
	        for (HumidifyingSetting i : HumidifyingSetting.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum FanOperation {
		OFF((byte)0x31),
		Weak((byte)0x32),
		Strong((byte)0x33);
		
		private byte code; 
		private FanOperation(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static FanOperation fromCode(byte code) {
	        for (FanOperation i : FanOperation.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum ShowcaseOperationMode {
		Cooling((byte)0x41),
		NonCooling((byte)0x42),
		Defrosting((byte)0x43),
		Others((byte)0x40);
		
		private byte code; 
		private ShowcaseOperationMode(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static ShowcaseOperationMode fromCode(byte code) {
	        for (ShowcaseOperationMode i : ShowcaseOperationMode.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum ShowcaseType {
		NonFluorocarbonInverter((byte)0x41),
		Inverter((byte)0x42),
		Others((byte)0x40);
		
		private byte code; 
		private ShowcaseType(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static ShowcaseType fromCode(byte code) {
	        for (ShowcaseType i : ShowcaseType.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum ShowcaseShape {
		BoxType((byte)0x41),
		DesktopType((byte)0x42),
		TrippleGlassType((byte)0x43),
		QuadrupleGlassType((byte)0x44),
		ReachIn((byte)0x45),
		GlassTopType((byte)0x46),
		MultistageOpen_CeilingBlowOffType((byte)0x47),
		MultistageOpen_BacksideBlowOffType((byte)0x48),
		FlatType((byte)0x49),
		WalkINType((byte)0x4A),
		Others((byte)0x40);
		
		private byte code; 
		private ShowcaseShape(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static ShowcaseShape fromCode(byte code) {
	        for (ShowcaseShape i : ShowcaseShape.values()) {
	            if (i.code() == code) {
	                return i;
	            }
	        }
	        return null;
	    }
	}
	public static enum RiceCookingStatus {
		Stop((byte)0x41),
		Preheating((byte)0x42),
		RiceCooking((byte)0x43),
		Steaming((byte)0x44),
		RiceCookingCompleted((byte)0x45);
		
		private byte code; 
		private RiceCookingStatus(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static RiceCookingStatus fromCode(byte code) {
	        for (RiceCookingStatus r : RiceCookingStatus.values()) {
	            if (r.code() == code) {
	                return r;
	            }
	        }
	        return null;
	    }
	}
	public static enum DRProgramType {
		CPP((byte)0x30),
		PTR((byte)0x31),
		PowerUseLimit((byte)0x32),
		PowerGenerationLimit((byte)0x33),
		ElectricForecast((byte)0x34),
		DLC((byte)0x35),
		LevelDesignation((byte)0x36),
		UserDefinedDomain((byte)0x80);
		
		private byte code; 
		private DRProgramType(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static DRProgramType fromCode(byte code) {
	        for (DRProgramType d : DRProgramType.values()) {
	            if (d.code() == code) {
	                return d;
	            } else if(d.code() >= (byte)0x80 &&d.code() <= (byte)0xFD) {
	            	return DRProgramType.UserDefinedDomain;
	            }
	        }
	        return null;
	    }
	}
	public static enum OperationSetting {
		Automatic((byte)0x41),
		Standard((byte)0x42),
		Level_1((byte)0x31),
		Level_2((byte)0x32),
		Level_3((byte)0x33),
		Level_4((byte)0x34),
		Level_5((byte)0x35),
		Level_6((byte)0x36),
		Level_7((byte)0x37),
		Level_8((byte)0x38);
		
		private byte code; 
		private OperationSetting(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static OperationSetting fromCode(byte code) {
	        for (OperationSetting d : OperationSetting.values()) {
	            if (d.code() == code) {
	                return d;
	            } 
	        }
	        return null;
	    }
	}
	public static enum SprinklerValveOpenCloseSetting {
		AutomaticON((byte)0x40),
		ManualON((byte)0x41),
		ManualOFF((byte)0x42);
		
		private byte code; 
		private SprinklerValveOpenCloseSetting(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static SprinklerValveOpenCloseSetting fromCode(byte code) {
	        for (SprinklerValveOpenCloseSetting d : SprinklerValveOpenCloseSetting.values()) {
	            if (d.code() == code) {
	                return d;
	            } 
	        }
	        return null;
	    }
	}
	public static enum DryerOperation {
		VentilationOperation((byte)0x10),
		PrewarmerOperation((byte)0x20),
		HeaterOperation((byte)0x30),
		BathroomDryerOperation((byte)0x40),
		CoolAirCirculatorOperation((byte)0x50),
		Stop((byte)0x00);
		
		private byte code; 
		private DryerOperation(byte msg) {
			this.code = msg;
		}
		public byte code() {
			return code;
		}
		public static DryerOperation fromCode(byte code) {
	        for (DryerOperation d : DryerOperation.values()) {
	            if (d.code() == code) {
	                return d;
	            } 
	        }
	        return null;
	    }
	}
	public static String dataToStringCode(byte data) {

		String rs = "";
		switch (data) {
			case (byte)0x01:
				rs = "ANSI X3.4";
				break;
			case (byte) 0x02:
				rs = "Shift –JIS";
				break;
			case (byte) 0x03:
				rs = "JIS";
				break;
			case (byte) 0x04:
				rs = "Japanese EUC";
				break;
			case (byte) 0x05:
				rs = "UCS-4";
			break;
			case (byte) 0x06:
				rs = "UCS-2 ";
			break;
			case (byte) 0x07:
				rs = "Latin-1";
			break;
			case (byte) 0x08:
				rs = "UTF-8 ";
			break;
			default:
				rs = "NotSet";
				break;
		}
		
		return rs;
	}
	public static String dataToAirConditionerOperationMode(ResultData data) {
		int dataSize = data.size();
		String rs = "";
		if (dataSize != 1) {
			rs =  "Invalid";
		}

		switch (data.get(0)) {
			case (byte)0x41:
				rs = "Automatic";
				break;
			case (byte) 0x42:
				rs = "Cooling";
				break;
			case (byte) 0x43:
				rs = "Heating";
				break;
			case (byte) 0x44:
				rs = "Dehumidification";
				break;
			case (byte) 0x45:
				rs = "Air circulator";
			break;
			case (byte) 0x40:
				rs = "Other";
			break;
			default:
				rs = "Invalid";
				break;
		}
		
		return rs;
	}
	public static long longFromByte(byte b1, byte b2, byte b3, byte b4) {
		return (b4+ b3*256 + b2*256*256 + b1*256*256*256);
	}
	public static short shortFromByte(byte b1, byte b2) {
		return (short)(b2+ b1*256);
	}
	public static String dataTo8BitMapcode(ResultData data) {
		String rs = "";
		byte[] allData = data.toBytes();
		rs = String.format("%8s",Integer.toBinaryString(allData[0] & 0xFF)).replace(' ', '0');	
		return rs;
	}
	public static String dataTo4BitMapcode(ResultData data) {
		String rs = "";
		byte[] allData = data.toBytes();
		rs = String.format("%4s",Integer.toBinaryString(allData[0] & 0xFF)).replace(' ', '0');	
		return rs;
	}
	
	public static ObjectData dataFromAirConditionerOperationMode(String mode) {
		ObjectData rs = null;

		switch (mode.toLowerCase().trim()) {
			case "automatic":
				rs = new ObjectData((byte)0x41);
				break;
			case "cooling":
				rs = new ObjectData((byte)0x42);
				break;
			case "heating":
				rs = new ObjectData((byte)0x43);
				break;
			case "dehumidification":
				rs = new ObjectData((byte)0x44);
				break;
			case "air circulator":
				rs = new ObjectData((byte)0x45);
			break;
			default:
				rs = new ObjectData((byte)0x40);
				break;
		}
		
		return rs;
	}
	
	public static String dataToLevel(ObjectData data) {
		int dataSize = data.size();
		String rs = "";
		if (dataSize != 1) {
			rs =  "Invalid";
		}

		switch (data.get(0)) {
			case (byte)0x41:
				rs = "Automatic";
				break;
			case (byte) 0x31:
				rs = "Level 1";
				break;
			case (byte) 0x32:
				rs = "Level 2";
				break;
			case (byte) 0x33:
				rs = "Level 3";
				break;
			case (byte) 0x34:
				rs = "Level 4";
			break;
			case (byte) 0x35:
				rs = "Level 5";
			break;
			case (byte) 0x36:
				rs = "Level 6";
				break;
			case (byte) 0x37:
				rs = "Level 7";
			break;
			case (byte) 0x38:
				rs = "Level 8";
			break;
			default:
				rs = "Automatic";
				break;
		}
		
		return rs;
	}
	public static String dataToOpeningLevel(ObjectData data) {
		int dataSize = data.size();
		String rs = "";
		if (dataSize != 1) {
			rs =  "Invalid";
		}

		switch (data.get(0)) {
			case (byte)0x30:
				rs = "Closed";
				break;
			case (byte) 0x31:
				rs = "Open Level 1";
				break;
			case (byte) 0x32:
				rs = "Open Level 2";
				break;
			case (byte) 0x33:
				rs = "Open Level 3";
				break;
			case (byte) 0x34:
				rs = "Open Level 4";
			break;
			case (byte) 0x35:
				rs = "Open Level 5";
			break;
			case (byte) 0x36:
				rs = "Open Level 6";
				break;
			case (byte) 0x37:
				rs = "Open Level 7";
			break;
			case (byte) 0x38:
				rs = "Fully Open";
			break;
			default:
				rs = "Unknown";
				break;
		}
		
		return rs;
	}
	public static String dataToOpeningLevel(ResultData data) {
		int dataSize = data.size();
		String rs = "";
		if (dataSize != 1) {
			rs =  "Invalid";
		}

		switch (data.get(0)) {
			case (byte)0x30:
				rs = "Closed";
				break;
			case (byte) 0x31:
				rs = "Open Level 1";
				break;
			case (byte) 0x32:
				rs = "Open Level 2";
				break;
			case (byte) 0x33:
				rs = "Open Level 3";
				break;
			case (byte) 0x34:
				rs = "Open Level 4";
			break;
			case (byte) 0x35:
				rs = "Open Level 5";
			break;
			case (byte) 0x36:
				rs = "Open Level 6";
				break;
			case (byte) 0x37:
				rs = "Open Level 7";
			break;
			case (byte) 0x38:
				rs = "Fully Open";
			break;
			default:
				rs = "Unknown";
				break;
		}
		
		return rs;
	}
	public static String dataToLevel(ResultData data) {
		int dataSize = data.size();
		String rs = "";
		if (dataSize != 1) {
			rs =  "Invalid";
		}

		switch (data.get(0)) {
			case (byte)0x41:
				rs = "Automatic";
				break;
			case (byte) 0x31:
				rs = "Level 1";
				break;
			case (byte) 0x32:
				rs = "Level 2";
				break;
			case (byte) 0x33:
				rs = "Level 3";
				break;
			case (byte) 0x34:
				rs = "Level 4";
			break;
			case (byte) 0x35:
				rs = "Level 5";
			break;
			case (byte) 0x36:
				rs = "Level 6";
				break;
			case (byte) 0x37:
				rs = "Level 7";
			break;
			case (byte) 0x38:
				rs = "Level 8";
			break;
			default:
				rs = "Automatic";
				break;
		}
		
		return rs;
	}
	public static String dataToDirection(ResultData data) {
		int dataSize = data.size();
		String rs = "";
		if (dataSize != 1) {
			rs =  "Invalid";
		}

		switch (data.get(0)) {
			case (byte) 0x30:
				rs = "No Passage";
				break;
			case (byte) 0x31:
				rs = "IN";
				break;
			case (byte) 0x32:
				rs = "IN_RIGHT";
				break;
			case (byte) 0x33:
				rs = "PARALLEL_RIGHT";
				break;
			case (byte) 0x34:
				rs = "OUT_RIGHT";
			break;
			case (byte) 0x35:
				rs = "OUT";
			break;
			case (byte) 0x36:
				rs = "OUT_LEFT";
				break;
			case (byte) 0x37:
				rs = "PARALLEL_LEFT";
			break;
			case (byte) 0x38:
				rs = "IN_LEFT";
			break;
			default:
				rs = "Passage detected but not located";
				break;
		}
		
		return rs;
	}
	
	public static ObjectData dataFromAirConditionerFlowRate(String airFlowRate) {
		ObjectData data = null;
		switch (airFlowRate.trim().toLowerCase()) {
		case "automatic":
			data = new ObjectData((byte)0x41);
			break;
		case "level 1":
			data = new ObjectData((byte)0x31);
			break;
		case "level 2":
			data = new ObjectData((byte)0x32);
			break;
		case "level 3":
			data = new ObjectData((byte)0x33);
			break;
		case "level 4":
			data = new ObjectData((byte)0x34);
			break;
		case "level 5":		
			data = new ObjectData((byte)0x35);
			break;
		case "level 6":
			data = new ObjectData((byte)0x36);
			break;
		case "level 7":
			data = new ObjectData((byte)0x37);
			break;
		case "level 8":
			data = new ObjectData((byte)0x38);
			break;
		default:
			data = new ObjectData((byte)0x41);
			break;
		}
		return data;
	}

	/**
	 * Get bit in byte by index
	 * 
	 * @param b
	 * @param bit
	 * @return true - 1, false - 0
	 */
	private static Boolean isBitSet(byte b, int bit) {
		return (b & (1 << bit)) != 0;
	}

	/**
	 * Get bit array as hashmap from byte with 3 bit lowest is 0
	 * 
	 * @param data
	 * @return hashmap bit and index
	 */
	private static HashMap<Integer, Integer> getHashmapDefaultFromByte(byte data) {
		HashMap<Integer, Integer> firstMap = new HashMap<Integer, Integer>();
		firstMap.put(0, 0);
		firstMap.put(1, 0);
		firstMap.put(2, 0);
		for (int i = 3; i < 8; i++) {
			firstMap.put(i, (isBitSet(data, i)) ? 1 : 0);
		}
		return firstMap;
	}

	/**
	 * Get bit array as hashmap from list bit: true - 1, false - 0
	 * 
	 * @param data
	 * @return hashmap bit and index
	 */
	private static HashMap<Integer, Integer> getHashmap(int bit7, int bit6, int bit5, int bit4, int bit3, int bit2,
			int bit1, int bit0) {
		HashMap<Integer, Integer> firstMap = new HashMap<Integer, Integer>();
		firstMap.put(0, (bit0 == 0) ? 0 : 1);
		firstMap.put(1, (bit1 == 0) ? 0 : 1);
		firstMap.put(2, (bit2 == 0) ? 0 : 1);
		firstMap.put(3, (bit3 == 0) ? 0 : 1);
		firstMap.put(4, (bit4 == 0) ? 0 : 1);
		firstMap.put(5, (bit5 == 0) ? 0 : 1);
		firstMap.put(6, (bit6 == 0) ? 0 : 1);
		firstMap.put(7, (bit7 == 0) ? 0 : 1);

		return firstMap;
	}

	private static boolean equalsHashmap(HashMap<Integer, Integer> h1, HashMap<Integer, Integer> h2) {
		try {

			for (int i = 0; i < 8; i++) {
				if (h1.get(i) != h2.get(i)) {
					return false;
				}
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Convert install location
	 * 
	 * @param odata
	 * @return string as install location
	 */
	public static String dataToInstallLocation(ObjectData odata) {
		byte data[] = odata.toBytes();
		byte firstByte = data[0];
		if (firstByte == (byte) 0xFF) {
			return SampleConstants.LOCATION_NOT_SPECIFIC;
		} else if (firstByte == (byte) 0x00) {
			return SampleConstants.LOCATION_UNDEFINED;
		} else if (firstByte == (byte) 0x01) { // 17 bytes
			String rs = "";
			for (int i = 1; i < data.length; i++) {
				rs += data[i];
			}
			return rs;
		} else if (!isBitSet(firstByte, 3) && !isBitSet(firstByte, 4) && !isBitSet(firstByte, 5)
				&& !isBitSet(firstByte, 6) && !isBitSet(firstByte, 7)) {
			return SampleConstants.RESERVED;
		} else {
			HashMap<Integer, Integer> firstMap = getHashmapDefaultFromByte(firstByte);
			if (equalsHashmap(firstMap, getHashmap(0, 1, 1, 1, 1, 0, 0, 0))) {
				return SampleConstants.OTHER;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 1, 1, 0, 0, 0, 0))) {
				return SampleConstants.VERANDA_BALCONY;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 1, 0, 1, 0, 0, 0))) {
				return SampleConstants.GARAGE;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 1, 0, 0, 0, 0, 0))) {
				return SampleConstants.GARDEN_PERIMETER;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 0, 1, 1, 0, 0, 0))) {
				return SampleConstants.STORE_ROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 0, 1, 0, 0, 0, 0))) {
				return SampleConstants.FRONT_DOOR;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 0, 0, 1, 0, 0, 0))) {
				return SampleConstants.STAIR_WAY;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 0, 0, 0, 0, 0, 0))) {
				return SampleConstants.ROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 1, 1, 1, 0, 0, 0))) {
				return SampleConstants.PASSAGEWAY;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 1, 1, 0, 0, 0, 0))) {
				return SampleConstants.WASH_CHANGING_ROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 1, 0, 1, 0, 0, 0))) {
				return SampleConstants.LAVATORY;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 1, 0, 0, 0, 0, 0))) {
				return SampleConstants.BATHROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 0, 1, 1, 0, 0, 0))) {
				return SampleConstants.KITCHEN;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 0, 1, 0, 0, 0, 0))) {
				return SampleConstants.DINING_ROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 0, 0, 1, 0, 0, 0))) {
				return SampleConstants.LIVING_ROOM;
			} else {
				return SampleConstants.FREE_DEFINITION;
			}
		}
	}
	public static String dataToInstallLocation(ResultData rdata) {
		byte data[] = rdata.toBytes();
		byte firstByte = data[0];
		if (firstByte == (byte) 0xFF) {
			return SampleConstants.LOCATION_NOT_SPECIFIC;
		} else if (firstByte == (byte) 0x00) {
			return SampleConstants.LOCATION_UNDEFINED;
		} else if (firstByte == (byte) 0x01) { // 17 bytes
			String rs = "";
			for (int i = 1; i < data.length; i++) {
				rs += data[i];
			}
			return rs;
		} else if (!isBitSet(firstByte, 3) && !isBitSet(firstByte, 4) && !isBitSet(firstByte, 5)
				&& !isBitSet(firstByte, 6) && !isBitSet(firstByte, 7)) {
			return SampleConstants.RESERVED;
		} else {
			HashMap<Integer, Integer> firstMap = getHashmapDefaultFromByte(firstByte);
			if (equalsHashmap(firstMap, getHashmap(0, 1, 1, 1, 1, 0, 0, 0))) {
				return SampleConstants.OTHER;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 1, 1, 0, 0, 0, 0))) {
				return SampleConstants.VERANDA_BALCONY;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 1, 0, 1, 0, 0, 0))) {
				return SampleConstants.GARAGE;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 1, 0, 0, 0, 0, 0))) {
				return SampleConstants.GARDEN_PERIMETER;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 0, 1, 1, 0, 0, 0))) {
				return SampleConstants.STORE_ROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 0, 1, 0, 0, 0, 0))) {
				return SampleConstants.FRONT_DOOR;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 0, 0, 1, 0, 0, 0))) {
				return SampleConstants.STAIR_WAY;
			} else if (equalsHashmap(firstMap, getHashmap(0, 1, 0, 0, 0, 0, 0, 0))) {
				return SampleConstants.ROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 1, 1, 1, 0, 0, 0))) {
				return SampleConstants.PASSAGEWAY;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 1, 1, 0, 0, 0, 0))) {
				return SampleConstants.WASH_CHANGING_ROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 1, 0, 1, 0, 0, 0))) {
				return SampleConstants.LAVATORY;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 1, 0, 0, 0, 0, 0))) {
				return SampleConstants.BATHROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 0, 1, 1, 0, 0, 0))) {
				return SampleConstants.KITCHEN;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 0, 1, 0, 0, 0, 0))) {
				return SampleConstants.DINING_ROOM;
			} else if (equalsHashmap(firstMap, getHashmap(0, 0, 0, 0, 1, 0, 0, 0))) {
				return SampleConstants.LIVING_ROOM;
			} else {
				return SampleConstants.FREE_DEFINITION;
			}
		}
	}

	public static ObjectData installLocationtoDataObj(String installation) {
		ObjectData data = new ObjectData((byte)0x78);
		if(installation.trim().equalsIgnoreCase(SampleConstants.LIVING_ROOM)) {
			data = new ObjectData((byte)0x08);
		}
		
		if(installation.trim().equalsIgnoreCase(SampleConstants.DINING_ROOM)) {
			data = new ObjectData((byte)0x10);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.KITCHEN)) {
			data = new ObjectData((byte)0x18);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.BATHROOM)) {
			data = new ObjectData((byte)0x20);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.LAVATORY)) {
			data = new ObjectData((byte)0x28);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.WASH_CHANGING_ROOM)) {
			data = new ObjectData((byte)0x30);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.PASSAGEWAY)) {
			data = new ObjectData((byte)0x38);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.ROOM)) {
			data = new ObjectData((byte)0x40);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.STAIR_WAY)) {
			data = new ObjectData((byte)0x48);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.FRONT_DOOR)) {
			data = new ObjectData((byte)0x50);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.STORE_ROOM)) {
			data = new ObjectData((byte)0x58);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.GARDEN_PERIMETER)) {
			data = new ObjectData((byte)0x60);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.GARAGE)) {
			data = new ObjectData((byte)0x68);
		}
		if(installation.trim().equalsIgnoreCase(SampleConstants.VERANDA_BALCONY)) {
			data = new ObjectData((byte)0x70);
		}
		return data;
	}
	public static Date dataDateTime(ObjectData odata) {
		try {
			Calendar calendar = Calendar.getInstance();
			Date rs = null;
			byte dateArray[];
			dateArray = odata.toBytes();
			int year = (((dateArray[0] & 0xff) << 8) | (dateArray[1] & 0xff));
			int month = dateArray[2];
			int day = dateArray[3];
			calendar.set(year, month, day);
			rs = calendar.getTime();
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	public static Date dataToDate(ResultData rdata) {
		try {
			Calendar calendar = Calendar.getInstance();
			Date rs = null;
			byte dateArray[];
			dateArray = rdata.toBytes();
			int year = (((dateArray[0] & 0xff) << 8) | (dateArray[1] & 0xff));
			int month = dateArray[2];
			int day = dateArray[3];
			calendar.set(year, month, day);
			rs = calendar.getTime();
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}
	public static String dataToTime(ResultData rdata) {
		byte timeArray[];
		timeArray = rdata.toBytes();
		int h = timeArray[0];
		int m = timeArray[1];
		return "" + ((h < 10) ? ("0" + h) : (h + "")) + ":" + ((m < 10) ? ("0" + m) : ("" + m));
	}

	/**
	 * Convert identification number
	 * 
	 * @param odata
	 * @return string as identification number
	 */
	public static String dataToIdentifiCationNumber(ObjectData odata) {
		byte data[] = odata.toBytes();
		byte firstByte = data[0];
		String comProtocol = "";
		String number = "";
		byte realData[] = new byte[data.length - 1];
		for (int i = 1; i < data.length; i++) {
			realData[i - 1] = data[i];
		}
		if (firstByte == (byte) 0xFE) {
			for (int i = 0; i < realData.length; i++) {
				number += realData[i];
			}
			return number;

		} else if (firstByte == (byte) 0xFF) {
			for (int i = 0; i < realData.length; i++) {
				number += realData[i];
			}
			return number;
		} else {

			switch (firstByte) {
			case (byte) 0x11:
			case (byte) 0x12:
			case (byte) 0x13:
			case (byte) 0x14:
			case (byte) 0x15:
			case (byte) 0x16:
			case (byte) 0x17:
			case (byte) 0x18:
			case (byte) 0x19:
			case (byte) 0x1A:
			case (byte) 0x1B:
			case (byte) 0x1C:
			case (byte) 0x1D:
			case (byte) 0x1E:
			case (byte) 0x1F:
				comProtocol = "Power line Communication Protocol a and d systems";
				break;
			case (byte) 0x31:
			case (byte) 0x32:
			case (byte) 0x33:
			case (byte) 0x34:
			case (byte) 0x35:
			case (byte) 0x36:
			case (byte) 0x37:
			case (byte) 0x38:
			case (byte) 0x39:
			case (byte) 0x3A:
			case (byte) 0x3B:
			case (byte) 0x3C:
			case (byte) 0x3D:
			case (byte) 0x3E:
			case (byte) 0x3F:
				comProtocol = "Low-Power Radio Communication Protocol";
				break;
			case (byte) 0x41:
			case (byte) 0x42:
			case (byte) 0x43:
			case (byte) 0x44:
			case (byte) 0x45:
			case (byte) 0x46:
			case (byte) 0x47:
			case (byte) 0x48:
			case (byte) 0x49:
			case (byte) 0x4A:
			case (byte) 0x4B:
			case (byte) 0x4C:
			case (byte) 0x4D:
			case (byte) 0x4E:
			case (byte) 0x4F:
				comProtocol = "Extended HBS";
				break;
			case (byte) 0x51:
			case (byte) 0x52:
			case (byte) 0x53:
			case (byte) 0x54:
			case (byte) 0x55:
			case (byte) 0x56:
			case (byte) 0x57:
			case (byte) 0x58:
			case (byte) 0x59:
			case (byte) 0x5A:
			case (byte) 0x5B:
			case (byte) 0x5C:
			case (byte) 0x5D:
			case (byte) 0x5E:
			case (byte) 0x5F:
				comProtocol = "IrDA";
				break;
			case (byte) 0x61:
			case (byte) 0x62:
			case (byte) 0x63:
			case (byte) 0x64:
			case (byte) 0x65:
			case (byte) 0x66:
			case (byte) 0x67:
			case (byte) 0x68:
			case (byte) 0x69:
			case (byte) 0x6A:
			case (byte) 0x6B:
			case (byte) 0x6C:
			case (byte) 0x6D:
			case (byte) 0x6E:
			case (byte) 0x6F:
				comProtocol = "LonTalk";
				break;
			case (byte) 0x71:
			case (byte) 0x72:
			case (byte) 0x73:
			case (byte) 0x74:
			case (byte) 0x75:
			case (byte) 0x76:
			case (byte) 0x77:
			case (byte) 0x78:
			case (byte) 0x79:
			case (byte) 0x7A:
			case (byte) 0x7B:
			case (byte) 0x7C:
			case (byte) 0x7D:
			case (byte) 0x7E:
			case (byte) 0x7F:
				comProtocol = "Bluetooth";
				break;
			case (byte) 0x81:
			case (byte) 0x82:
			case (byte) 0x83:
			case (byte) 0x84:
			case (byte) 0x85:
			case (byte) 0x86:
			case (byte) 0x87:
			case (byte) 0x88:
			case (byte) 0x89:
			case (byte) 0x8A:
			case (byte) 0x8B:
			case (byte) 0x8C:
			case (byte) 0x8D:
			case (byte) 0x8E:
			case (byte) 0x8F:
				comProtocol = "Ethernet";
				break;
			case (byte) 0x91:
			case (byte) 0x92:
			case (byte) 0x93:
			case (byte) 0x94:
			case (byte) 0x95:
			case (byte) 0x96:
			case (byte) 0x97:
			case (byte) 0x98:
			case (byte) 0x99:
			case (byte) 0x9A:
			case (byte) 0x9B:
			case (byte) 0x9C:
			case (byte) 0x9D:
			case (byte) 0x9E:
			case (byte) 0x9F:
				comProtocol = "IEEE802.11/11b";
				break;
			case (byte) 0xA1:
				comProtocol = "Power line Communication Protocol c systems";
				break;
			case (byte) 0xB1:
				comProtocol = "IPv6/Ethernet";
				break;
			case (byte) 0xB2:
				comProtocol = "IPv6/6LoWPAN";
				break;
			default:
				comProtocol = "Undefined";
				break;

			}
			number = new BigInteger(realData).toString() + "";
			return comProtocol + ((number.trim().length() > 1) ? " " + number : "");
		}
	}
	public static String dataToIdentifiCationNumber(ResultData rdata) {
		byte data[] = rdata.toBytes();
		byte firstByte = data[0];
		String comProtocol = "";
		String number = "";
		byte realData[] = new byte[data.length - 1];
		for (int i = 1; i < data.length; i++) {
			realData[i - 1] = data[i];
		}
		if (firstByte == (byte) 0xFE) {
			for (int i = 0; i < realData.length; i++) {
				number += realData[i];
			}
			return number;

		} else if (firstByte == (byte) 0xFF) {
			for (int i = 0; i < realData.length; i++) {
				number += realData[i];
			}
			return number;
		} else {

			switch (firstByte) {
			case (byte) 0x11:
			case (byte) 0x12:
			case (byte) 0x13:
			case (byte) 0x14:
			case (byte) 0x15:
			case (byte) 0x16:
			case (byte) 0x17:
			case (byte) 0x18:
			case (byte) 0x19:
			case (byte) 0x1A:
			case (byte) 0x1B:
			case (byte) 0x1C:
			case (byte) 0x1D:
			case (byte) 0x1E:
			case (byte) 0x1F:
				comProtocol = SampleConstants.PLC_A_D;
				break;
			case (byte) 0x31:
			case (byte) 0x32:
			case (byte) 0x33:
			case (byte) 0x34:
			case (byte) 0x35:
			case (byte) 0x36:
			case (byte) 0x37:
			case (byte) 0x38:
			case (byte) 0x39:
			case (byte) 0x3A:
			case (byte) 0x3B:
			case (byte) 0x3C:
			case (byte) 0x3D:
			case (byte) 0x3E:
			case (byte) 0x3F:
				comProtocol = SampleConstants.LOW_POWER_RADIO_COMMUNICATION_PROTOCOL;
				break;
			case (byte) 0x41:
			case (byte) 0x42:
			case (byte) 0x43:
			case (byte) 0x44:
			case (byte) 0x45:
			case (byte) 0x46:
			case (byte) 0x47:
			case (byte) 0x48:
			case (byte) 0x49:
			case (byte) 0x4A:
			case (byte) 0x4B:
			case (byte) 0x4C:
			case (byte) 0x4D:
			case (byte) 0x4E:
			case (byte) 0x4F:
				comProtocol = SampleConstants.EXTENDED_HBS;
				break;
			case (byte) 0x51:
			case (byte) 0x52:
			case (byte) 0x53:
			case (byte) 0x54:
			case (byte) 0x55:
			case (byte) 0x56:
			case (byte) 0x57:
			case (byte) 0x58:
			case (byte) 0x59:
			case (byte) 0x5A:
			case (byte) 0x5B:
			case (byte) 0x5C:
			case (byte) 0x5D:
			case (byte) 0x5E:
			case (byte) 0x5F:
				comProtocol = SampleConstants.IrDA;
				break;
			case (byte) 0x61:
			case (byte) 0x62:
			case (byte) 0x63:
			case (byte) 0x64:
			case (byte) 0x65:
			case (byte) 0x66:
			case (byte) 0x67:
			case (byte) 0x68:
			case (byte) 0x69:
			case (byte) 0x6A:
			case (byte) 0x6B:
			case (byte) 0x6C:
			case (byte) 0x6D:
			case (byte) 0x6E:
			case (byte) 0x6F:
				comProtocol = SampleConstants.LONTALK;
				break;
			case (byte) 0x71:
			case (byte) 0x72:
			case (byte) 0x73:
			case (byte) 0x74:
			case (byte) 0x75:
			case (byte) 0x76:
			case (byte) 0x77:
			case (byte) 0x78:
			case (byte) 0x79:
			case (byte) 0x7A:
			case (byte) 0x7B:
			case (byte) 0x7C:
			case (byte) 0x7D:
			case (byte) 0x7E:
			case (byte) 0x7F:
				comProtocol = SampleConstants.BLUETOOTH;
				break;
			case (byte) 0x81:
			case (byte) 0x82:
			case (byte) 0x83:
			case (byte) 0x84:
			case (byte) 0x85:
			case (byte) 0x86:
			case (byte) 0x87:
			case (byte) 0x88:
			case (byte) 0x89:
			case (byte) 0x8A:
			case (byte) 0x8B:
			case (byte) 0x8C:
			case (byte) 0x8D:
			case (byte) 0x8E:
			case (byte) 0x8F:
				comProtocol = SampleConstants.ETHERNET;
				break;
			case (byte) 0x91:
			case (byte) 0x92:
			case (byte) 0x93:
			case (byte) 0x94:
			case (byte) 0x95:
			case (byte) 0x96:
			case (byte) 0x97:
			case (byte) 0x98:
			case (byte) 0x99:
			case (byte) 0x9A:
			case (byte) 0x9B:
			case (byte) 0x9C:
			case (byte) 0x9D:
			case (byte) 0x9E:
			case (byte) 0x9F:
				comProtocol = SampleConstants.IEEE802_11_11_B;
				break;
			case (byte) 0xA1:
				comProtocol = SampleConstants.PLC_C;
				break;
			case (byte) 0xB1:
				comProtocol = SampleConstants.IPV6_ETHERNET;
				break;
			case (byte) 0xB2:
				comProtocol = SampleConstants.IPV6_6LOWPAN;
				break;
			default:
				comProtocol = SampleConstants.UNDEFINED;
				break;

			}
			number = new BigInteger(realData).toString() + "";
			return comProtocol + ((number.trim().length() > 1) ? " " + number : "");
		}
	}
	public static String dataToCoordinator(ResultData data) {
		String coordinator = "";	
		byte[] allData = data.toBytes();
		Byte firstByte = new Byte(allData[0]);
		Byte secondByte = new Byte(allData[1]);
		Byte thirdByte = new Byte(allData[2]);
		return String.format("X=%d, Y=%d, Z=%d",firstByte.intValue(),secondByte.intValue(),thirdByte.intValue());
	}
	public static String getFaultDetail(ObjectData odata) throws EchonetObjectException {
		FaultType faultType = null;
		String contentSpecification = "";
		byte[] allData = odata.toBytes();

		if (allData[1] != (byte) 0x00) {
			if (allData[0] == (byte) 0xFF) {
				faultType = FaultType.Undefined;
				contentSpecification = SampleConstants.SPECIAL_FAULT;
			} else {
				faultType = FaultType.Undefined;
				contentSpecification = SampleConstants.FAULT_UNDEFINED;
			}
		} else {
			switch (allData[0]) {
			case (byte) 0x00:
				contentSpecification = SampleConstants.NO_FAULT;
				faultType = null;
				break;
			case (byte) 0x01:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_1;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x02:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_2;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x03:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_3;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x04:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_4;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x05:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_5;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x06:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_6;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x07:
			case (byte) 0x08:
				contentSpecification = SampleConstants.FAULT_UNDEFINED;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x09:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_7;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x0A:
			case (byte) 0x0B:
			case (byte) 0x0C:
			case (byte) 0x0D:
			case (byte) 0x0E:
			case (byte) 0x0F:
			case (byte) 0x11:
			case (byte) 0x12:
			case (byte) 0x13:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_1;
				faultType = FaultType.Require_Repair_Faults;
				break;
			case (byte) 0x14:
			case (byte) 0x15:
			case (byte) 0x16:
			case (byte) 0x17:
			case (byte) 0x18:
			case (byte) 0x19:
			case (byte) 0x1A:
			case (byte) 0x1B:
			case (byte) 0x1C:
			case (byte) 0x1D:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_2;
				faultType = FaultType.Require_Repair_Faults;
				break;
			case (byte) 0x1E:
			case (byte) 0x1F:
			case (byte) 0x21:
			case (byte) 0x22:
			case (byte) 0x23:
			case (byte) 0x24:
			case (byte) 0x25:
			case (byte) 0x26:
			case (byte) 0x27:
			case (byte) 0x28:
			case (byte) 0x29:
			case (byte) 0x2A:
			case (byte) 0x2B:
			case (byte) 0x2C:
			case (byte) 0x2D:
			case (byte) 0x2E:
			case (byte) 0x2F:
			case (byte) 0x31:
			case (byte) 0x32:
			case (byte) 0x33:
			case (byte) 0x34:
			case (byte) 0x35:
			case (byte) 0x36:
			case (byte) 0x37:
			case (byte) 0x38:
			case (byte) 0x39:
			case (byte) 0x3A:
			case (byte) 0x3B:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_3;
				faultType = FaultType.Require_Repair_Faults;
				break;
			case (byte) 0x3C:
			case (byte) 0x3D:
			case (byte) 0x3E:
			case (byte) 0x3F:
			case (byte) 0x41:
			case (byte) 0x42:
			case (byte) 0x43:
			case (byte) 0x44:
			case (byte) 0x45:
			case (byte) 0x46:
			case (byte) 0x47:
			case (byte) 0x48:
			case (byte) 0x49:
			case (byte) 0x4A:
			case (byte) 0x4B:
			case (byte) 0x4C:
			case (byte) 0x4D:
			case (byte) 0x4E:
			case (byte) 0x4F:
			case (byte) 0x51:
			case (byte) 0x52:
			case (byte) 0x53:
			case (byte) 0x54:
			case (byte) 0x55:
			case (byte) 0x56:
			case (byte) 0x57:
			case (byte) 0x58:
			case (byte) 0x59:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_4;
				faultType = FaultType.Require_Repair_Faults;
				break;
			case (byte) 0x5A:
			case (byte) 0x5B:
			case (byte) 0x5C:
			case (byte) 0x5D:
			case (byte) 0x5E:
			case (byte) 0x5F:
			case (byte) 0x61:
			case (byte) 0x62:
			case (byte) 0x63:
			case (byte) 0x64:
			case (byte) 0x65:
			case (byte) 0x66:
			case (byte) 0x67:
			case (byte) 0x68:
			case (byte) 0x69:
			case (byte) 0x6A:
			case (byte) 0x6B:
			case (byte) 0x6C:
			case (byte) 0x6D:
			case (byte) 0x6E:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_5;
				faultType = FaultType.Require_Repair_Faults;
				break;
			default:
				if (eConverter.dataToInteger(allData) <= (byte) 0x03E8
						&& eConverter.dataToInteger(allData) >= (byte) 0x006F) {
					contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_6;
					faultType = FaultType.Require_Repair_Faults;
				} else {
					faultType = FaultType.Undefined;
					contentSpecification = SampleConstants.UNKNOWN_FAULT;
				}
				break;
			}
		}

		//return (faultType == null) ? notFaultDescription : faultType.toString() + ":" + contentSpecification;
		return contentSpecification;
	}

	public static String getFaultDetail(ResultData rdata) throws EchonetObjectException {
		FaultType faultType = null;
		String contentSpecification = "";
		byte[] allData = rdata.toBytes();

		if (allData[1] != (byte) 0x00) {
			if (allData[0] == (byte) 0xFF) {
				faultType = FaultType.Undefined;
				contentSpecification = SampleConstants.SPECIAL_FAULT;
			} else {
				faultType = FaultType.Undefined;
				contentSpecification = SampleConstants.FAULT_UNDEFINED;
			}
		} else {
			switch (allData[0]) {
			case (byte) 0x00:
				contentSpecification = SampleConstants.NO_FAULT;
				faultType = null;
				break;
			case (byte) 0x01:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_1;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x02:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_2;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x03:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_3;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x04:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_4;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x05:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_5;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x06:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_6;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x07:
			case (byte) 0x08:
				contentSpecification = SampleConstants.FAULT_UNDEFINED;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x09:
				contentSpecification = SampleConstants.RECOVERABLE_TYPE_7;
				faultType = FaultType.Recoverable_Faults;
				break;
			case (byte) 0x0A:
			case (byte) 0x0B:
			case (byte) 0x0C:
			case (byte) 0x0D:
			case (byte) 0x0E:
			case (byte) 0x0F:
			case (byte) 0x11:
			case (byte) 0x12:
			case (byte) 0x13:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_1;
				faultType = FaultType.Require_Repair_Faults;
				break;
			case (byte) 0x14:
			case (byte) 0x15:
			case (byte) 0x16:
			case (byte) 0x17:
			case (byte) 0x18:
			case (byte) 0x19:
			case (byte) 0x1A:
			case (byte) 0x1B:
			case (byte) 0x1C:
			case (byte) 0x1D:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_2;
				faultType = FaultType.Require_Repair_Faults;
				break;
			case (byte) 0x1E:
			case (byte) 0x1F:
			case (byte) 0x21:
			case (byte) 0x22:
			case (byte) 0x23:
			case (byte) 0x24:
			case (byte) 0x25:
			case (byte) 0x26:
			case (byte) 0x27:
			case (byte) 0x28:
			case (byte) 0x29:
			case (byte) 0x2A:
			case (byte) 0x2B:
			case (byte) 0x2C:
			case (byte) 0x2D:
			case (byte) 0x2E:
			case (byte) 0x2F:
			case (byte) 0x31:
			case (byte) 0x32:
			case (byte) 0x33:
			case (byte) 0x34:
			case (byte) 0x35:
			case (byte) 0x36:
			case (byte) 0x37:
			case (byte) 0x38:
			case (byte) 0x39:
			case (byte) 0x3A:
			case (byte) 0x3B:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_3;
				faultType = FaultType.Require_Repair_Faults;
				break;
			case (byte) 0x3C:
			case (byte) 0x3D:
			case (byte) 0x3E:
			case (byte) 0x3F:
			case (byte) 0x41:
			case (byte) 0x42:
			case (byte) 0x43:
			case (byte) 0x44:
			case (byte) 0x45:
			case (byte) 0x46:
			case (byte) 0x47:
			case (byte) 0x48:
			case (byte) 0x49:
			case (byte) 0x4A:
			case (byte) 0x4B:
			case (byte) 0x4C:
			case (byte) 0x4D:
			case (byte) 0x4E:
			case (byte) 0x4F:
			case (byte) 0x51:
			case (byte) 0x52:
			case (byte) 0x53:
			case (byte) 0x54:
			case (byte) 0x55:
			case (byte) 0x56:
			case (byte) 0x57:
			case (byte) 0x58:
			case (byte) 0x59:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_4;
				faultType = FaultType.Require_Repair_Faults;
				break;
			case (byte) 0x5A:
			case (byte) 0x5B:
			case (byte) 0x5C:
			case (byte) 0x5D:
			case (byte) 0x5E:
			case (byte) 0x5F:
			case (byte) 0x61:
			case (byte) 0x62:
			case (byte) 0x63:
			case (byte) 0x64:
			case (byte) 0x65:
			case (byte) 0x66:
			case (byte) 0x67:
			case (byte) 0x68:
			case (byte) 0x69:
			case (byte) 0x6A:
			case (byte) 0x6B:
			case (byte) 0x6C:
			case (byte) 0x6D:
			case (byte) 0x6E:
				contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_5;
				faultType = FaultType.Require_Repair_Faults;
				break;
			default:
				if (eConverter.dataToInteger(allData) <= (byte) 0x03E8
						&& eConverter.dataToInteger(allData) >= (byte) 0x006F) {
					contentSpecification = SampleConstants.REPAIR_REQUIRED_TYPE_6;
					faultType = FaultType.Require_Repair_Faults;
				} else {
					faultType = FaultType.Undefined;
					contentSpecification = SampleConstants.UNKNOWN_FAULT;
				}
				break;
			}
		}

		//return (faultType == null) ? notFaultDescription : faultType.toString() + ":" + contentSpecification;
		return contentSpecification;
	}
	

	/**
	 * Convert data to real
	 * 
	 * @param data
	 * @return double value
	 */
	public static double dataToReal(ObjectData data) {
		boolean is_negative = false;
		int dataSize = data.size();
		BigDecimal up = BigDecimal.valueOf(0x0100);
		BigDecimal dec = new BigDecimal(0);

		for (int i = 0; i < dataSize; i++) {
			if ((i == 0) && (data.get(i) < 0)) {
				is_negative = true;
			}

			int pos_data;
			if (is_negative) {
				pos_data = 0xff & ((int) ((byte) -1) ^ data.get(i));
			} else {
				pos_data = 0x00ff & (int) data.get(i);
			}

			BigDecimal cur = BigDecimal.valueOf(pos_data);
			dec = dec.multiply(up).add(cur);
		}

		if (is_negative) {
			dec = dec.add(BigDecimal.valueOf(1));
			dec = dec.negate();
		}
		return dec.doubleValue();
	}
	public static String dataToCummalativeTime(ResultData data) {
		byte timeArray[];
		timeArray = data.toBytes();
		String unit = "";
		switch (timeArray[0]) {
		case (byte) 0x41:
			unit = "seconds";
			break;
		case (byte) 0x42:
			unit = "months";
			break;
		case (byte) 0x43:
			unit = "hours";
			break;
		case (byte) 0x44:
			unit = "days";
			break;
		default:
			unit = "seconds";
			break;
		}
		byte valueArray[] = new byte[4];
		valueArray[0] = timeArray[1];
		valueArray[1] = timeArray[2];
		valueArray[2] = timeArray[3];
		valueArray[3] = timeArray[4];
		int timeSpan = eConverter.dataToInteger(valueArray);
		return timeSpan + " " + unit;
	}
	public static String getIPAddr(String input) {
		String IPADDRESS_PATTERN = 
		        "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
		String ip = "";
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			ip = matcher.group();
		}
		return ip;
	}

	public static byte[] requestData(EPC epc, String data) {
		try {
			byte[] result;
			data = data.trim();
			switch (epc) {
			case x80:
				Boolean status = null;
				status = Boolean.parseBoolean(data);
				if (status == null)
					throw new ParseException("Cannot parse to boolean as operation status: EPC.x80.", 0);
				result = new byte[1];
				result[0] = ((status) ? ((byte) 0x30) : ((byte) 0x31));
				return result;
			case x81:
				data = data.toLowerCase();
				result = new byte[1];
				switch (data) {
				case "living room":
					result[0] = (byte) 0x08;
					break;
				case "dining room":
					result[0] = (byte) 0x10;
					break;
				case "kitchen":
					result[0] = (byte) 0x18;
					break;
				case "bathroom":
					result[0] = (byte) 0x20;
					break;
				case "lavatory":
					result[0] = (byte) 0x28;
					break;
				case "washroom/changing room":
					result[0] = (byte) 0x30;
					break;
				case "passageway":
					result[0] = (byte) 0x38;
					break;
				case "room":
					result[0] = (byte) 0x40;
					break;
				case "stairway":
					result[0] = (byte) 0x48;
					break;
				case "front door":
					result[0] = (byte) 0x50;
					break;
				case "storeroom":
					result[0] = (byte) 0x58;
					break;
				case "garden/perimeter":
					result[0] = (byte) 0x60;
					break;
				case "garage":
					result[0] = (byte) 0x68;
					break;
				case "veranda/balcony":
					result[0] = (byte) 0x70;
					break;
				case "others":
					result[0] = (byte) 0x78;
					break;
				default:
					result[0] = (byte) 0x00;
					break;
				}
				return result;
			case x87:
				Integer setting = null;
				setting = Integer.parseInt(data);
				if (setting == null) {
					throw new ParseException("Cannot parse to number as current limit setting: EPC.x87.",0);
				}
				result = new byte[1];
				if (setting.intValue() < 0)
					setting = 0;
				if (setting.intValue() > 100)
					setting = 100;
				result[0] = setting.byteValue();
				return result;
			case x8F:
				Boolean saving = null;
				saving = Boolean.parseBoolean(data);
				if (saving == null) {
					throw new ParseException("Cannot parse to boolean as power-saving operation setting: EPC.x8F.", 0);
				}
				result = new byte[1];
				result[0] = ((saving) ? ((byte) 0x41) : ((byte) 0x42));
				return result;
			case x93:
				Boolean remote = null;
				remote = Boolean.parseBoolean(data);
				if (remote == null) {
					throw new ParseException("Cannot parse to boolean as remote control setting: EPC.x93.",0);
				}
				result = new byte[1];
				result[0] = ((remote) ? ((byte) 0x41) : ((byte) 0x42));
				return result;
			case x97:
				String[] allTime = data.split(":");
				Integer hour = Integer.parseInt(allTime[0]);
				Integer minutes = Integer.parseInt(allTime[1]);
				if (hour == null || minutes == null) {
					throw new ParseException("Cannot parse to time as current time setting: EPC.x97.",0);
				}
				result = new byte[2];
				result[0] = hour.byteValue();
				result[1] = minutes.byteValue();
				return result;
			case x98:
				String[] allDate = data.split(":");
				Integer year = Integer.parseInt(allDate[0]);
				Integer month = Integer.parseInt(allDate[1]);
				Integer day = Integer.parseInt(allDate[1]);
				if (year == null || month == null || day == null) {
					throw new ParseException("Cannot parse to time as current date setting: EPC.x98.",0);
				}
				byte[] dataYear = new byte[2];
				dataYear[0] = (byte) (year & 0xFF);
				dataYear[1] = (byte) ((year >> 8) & 0xFF);

				result = new byte[4];
				result[0] = dataYear[1];
				result[1] = dataYear[0];
				result[2] = month.byteValue();
				result[3] = day.byteValue();
				return result;
			case x99:
				Integer power = Integer.parseInt(data);
				if (power == null) {
					throw new ParseException("Cannot parse to number as power limit setting: EPC.x99.",0);
				}
				byte[] dataPower = new byte[2];
				dataPower[0] = (byte) (power & 0xFF);
				dataPower[1] = (byte) ((power >> 8) & 0xFF);

				result = new byte[2];
				result[0] = dataPower[1];
				result[1] = dataPower[0];
				return result;
			default:
				return null;
			}
		} catch (ParseException ex) {
			return null;
		} catch (Exception ex) {
			return null;
		}
	}

}
