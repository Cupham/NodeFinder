package jaist.ac.jp.NodeFinder.echonet.object.mapper;

import java.util.logging.Logger;

import echowand.common.EOJ;
import echowand.net.Node;
import jaist.ac.jp.NodeFinder.echonet.object.eDataObject;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eClothesDryer;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eCombinationMicroOven;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eCommercialShowcase;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eCommercialShowcaseOutdoorUnit;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eCookingHeater;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eHotWaterPot;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eRefrigerator;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eRiceCooker;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eWasherDryer;
import jaist.ac.jp.NodeFinder.echonet.object.cooking.eWashingMachine;

public class CookingDeviceMapper {
	private static final Logger logger = Logger.getLogger(CookingDeviceMapper.class.getName());
	
	public static eDataObject createCoffeeMachine(Node node, EOJ eoj) {
		logger.warning("CoffeeMachine specification has not been released");
		return null;
	}
	public static eDataObject createCoffeeMill(Node node, EOJ eoj) {
		logger.warning("CoffeeMill specification has not been released");
		return null;
	}
	public static eDataObject createElectricHotWaterPot(Node node, EOJ eoj) {
		logger.info("Creating ElectricHotWaterPot from ECHONET Lite frame...");
		return new eHotWaterPot(node, eoj);
	}
	
	public static eDataObject createElectricStove(Node node, EOJ eoj) {
		logger.warning("ElectricStove specification has not been released");
		return null;
	}
	public static eDataObject createToaster(Node node, EOJ eoj) {
		logger.warning("Toaster specification has not been released");
		return null;
	}
	public static eDataObject createJuicerFoodMixer(Node node, EOJ eoj) {
		logger.warning("JuicerFoodMixer specification has not been released");
		return null;
	}
	public static eDataObject createFoodProcessor(Node node, EOJ eoj) {
		logger.warning("FoodProcessor specification has not been released");
		return null;
	}
	public static eDataObject createRefrigerator(Node node, EOJ eoj) {
		logger.info("Creating Refrigerator from ECHONET Lite frame...");
		return new eRefrigerator(node, eoj);
	}
	public static eDataObject createCombinationMicrowaveOven(Node node, EOJ eoj) {
		logger.info("Creating CombinationMicrowaveOven from ECHONET Lite frame...");
		return new eCombinationMicroOven(node, eoj);
	}
	public static eDataObject createCookingHeater(Node node, EOJ eoj) {
		logger.info("Creating CookingHeater from ECHONET Lite frame...");
		return new eCookingHeater(node, eoj);
	}
	public static eDataObject createOven(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createRiceCooker(Node node, EOJ eoj) {
		logger.info("Creating RiceCooker from ECHONET Lite frame...");
		return new eRiceCooker(node, eoj);
	}
	public static eDataObject createElectronicJar(Node node, EOJ eoj) {
		logger.warning("ElectronicJar specification has not been released");
		return null;
	}
	public static eDataObject createDishWasher(Node node, EOJ eoj) {
		logger.warning("DishWasher specification has not been released");
		return null;
	}
	public static eDataObject createDishDryer(Node node, EOJ eoj) {
		logger.warning("DishDryer specification has not been released");
		return null;
	}
	public static eDataObject createRiceCardCooker(Node node, EOJ eoj) {
		logger.warning("RiceCardCooker specification has not been released");
		return null;
	}
	public static eDataObject createKeepWarmMachine(Node node, EOJ eoj) {
		logger.warning("KeepWarmMachine specification has not been released");
		return null;
	}
	public static eDataObject createRiceMill(Node node, EOJ eoj) {
		logger.warning("RiceMill specification has not been released");
		return null;
	}
	public static eDataObject createBreadCooker(Node node, EOJ eoj) {
		logger.warning("BreadCooker specification has not been released");
		return null;
	}
	public static eDataObject createSlowCooker(Node node, EOJ eoj) {
		logger.warning("SlowCooker specification has not been released");
		return null;
	}
	public static eDataObject createPicklesCooker(Node node, EOJ eoj) {
		logger.warning("PicklesCooker specification has not been released");
		return null;
	}
	public static eDataObject createWashingMachine(Node node, EOJ eoj) {
		logger.info("Creating WashingMachine from ECHONET Lite frame...");
		return new eWashingMachine(node, eoj);
	}
	public static eDataObject createClothesDryer(Node node, EOJ eoj) {
		logger.info("Creating ClothesDryer from ECHONET Lite frame...");
		return new eClothesDryer(node, eoj);
	}
	public static eDataObject createElectricIron(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createTrouserPress(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createFutonDryer(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createShoesDryer(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createVacuumCleaner(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createDisposer(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createMosquitoCatcher(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createCommercialShowCase(Node node, EOJ eoj) {
		logger.info("Creating CommercialShowCase from ECHONET Lite frame...");
		return new eCommercialShowcase(node, eoj);
	}
	public static eDataObject createCommercialRefrigerator(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createCommerciaHotCase(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createCommercialFryer(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createCommercialMicrowaveOven(Node node, EOJ eoj) {
		logger.warning("DaylightSensor specification has not been released");
		return null;
	}
	public static eDataObject createWasherAndDryer(Node node, EOJ eoj) {
		logger.info("Creating WasherAndDryer from ECHONET Lite frame...");
		return new eWasherDryer(node, eoj);
	}
	public static eDataObject createCommerciaShowCaseOutdoorUnit(Node node, EOJ eoj) {
		logger.info("Creating CommerciaShowCaseOutdoorUnit from ECHONET Lite frame...");
		return new eCommercialShowcaseOutdoorUnit(node, eoj);
	}

}
