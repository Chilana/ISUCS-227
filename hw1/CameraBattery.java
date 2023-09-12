package hw1;

public class CameraBattery{
	
	/**
	 * @author chila 
	 * variable for the charge of the battery
	 */
	private double chargeOfBattery; 
	
	/**
	 * @author chila
	 * variable for the setting in which external charges at
	 */
	private int chargeSetting;
	
	/**
	 * @author chila 
	 * variable for the battery capacity given
	 */
	private double batCap;
	
	/**
	 * @author chila
	 * variable for the charge of the camera
	 */
	private double chargeOfCam;
	
	/**
	 * @author chila
	 * the amount of drain used in single given drain use
	 */
	private double drain;
	
	/**
	 * @author chila
	 * the total amount of drain used
	 */
	private double totalDrain;

	/**
	 * @author chila
	 * variable used to determine if the battery is plugged into camera
	 */
	private double chargeYN;
	
	/**
	 * @author chila
	 * amount of charger settings
	 */
	public static final int NUM_CHARGER_SETTINGS = 4;
	
	/**
	 * @author chila
	 * rate at which charge happens per min
	 */
	public static final double CHARGE_RATE = 2.0;
	
	/**
	 * @author chila
	 * rate at which battery is drained per min
	 */
	public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1.0;
	
	/**
	 * @author chila
	 * a variable i created to easy access the rate at drain happens
	 */
	private double camPower;
	
	/**
	 * @author chila method which takes the given parameters and puts them into variables
	 * @param batteryStartingCharge the charge the battery starts with
	 * @param batteryCapacity the max amount of charge the battery can have
	 */
	public CameraBattery(double batteryStartingCharge, double batteryCapacity) {
		batCap = batteryCapacity;
		chargeOfBattery = batteryStartingCharge;
		camPower = DEFAULT_CAMERA_POWER_CONSUMPTION;
		chargeOfBattery = Math.min(batteryStartingCharge, batteryCapacity);
	}
	/**
	 * @author chila method which changes the charge setting by adding 1 then modding at max
	 */
	public void buttonPress() {
		chargeSetting += 1;
		chargeSetting %= NUM_CHARGER_SETTINGS;
	}
	/**
	 * @author chila calculates the amount the camera and battery have been charged
	 * @param minutes the amount of minutes wanted to charge 
	 * @return the amount of charge that should have happened
	 */
	public double cameraCharge(double minutes) {
		double justCam = 0;
		chargeOfCam = Math.min(batCap, ((chargeYN * minutes * CHARGE_RATE) + chargeOfCam));
		justCam = (CHARGE_RATE * minutes * chargeYN);
		justCam = Math.min(batCap - chargeOfBattery, justCam);
		chargeOfBattery = Math.max(chargeOfBattery, (minutes * CHARGE_RATE));
		chargeOfBattery += justCam;
		chargeOfBattery = Math.min(chargeOfBattery, batCap);
		return justCam;
	}
	/**
	 * @author chila calculates the amount the battery should drain and how much the camera should drain
	 * @param minutes amount of minutes drained
	 * @return the charge drained
	 */
	public double drain(double minutes) {
		drain = (minutes * camPower * chargeYN);
		drain = Math.min(drain,chargeOfBattery);
		chargeOfBattery -= (minutes * camPower * chargeYN);
		chargeOfCam = chargeOfBattery * chargeYN;
		totalDrain += drain;
		chargeOfBattery = Math.max(chargeOfBattery, 0);
		chargeOfCam = Math.max(chargeOfCam, 0);
		return drain;
	}
	/**
	 * @author chila calculates the charge of the battery using the outer charger
	 * @param minutes amount of minutes wanted to charge
	 * @return the amount of charge gained
	 */
	public double externalCharge(double minutes) {
		double externalChargeWork = 0;
		externalChargeWork = (minutes * (chargeSetting) * CHARGE_RATE);
		externalChargeWork = Math.min(batCap - chargeOfBattery, externalChargeWork);
		chargeOfBattery = Math.max(chargeOfBattery, (minutes * (chargeSetting + 1)* CHARGE_RATE));
		chargeOfBattery += externalChargeWork;
		chargeOfBattery = Math.min(chargeOfBattery, batCap);
		return externalChargeWork;
	}
	
	/**
	 * @author chila resets the total drain back to zero
	 */
	public void resetBatteryMonitor() {
		totalDrain = 0;
	}
	
	/**
	 * @author chila returns battery capacity
	 * @return battery capacity
	 */
	public double getBatteryCapacity() {
		return batCap;
	}
	/**
	 * @author chila returns the charge of battery
	 * @return
	 */
	public double getBatteryCharge() {
		return chargeOfBattery;
	}
	/**
	 * @author chila returns the charge of camera
	 * @return
	 */
	public double getCameraCharge() {
		return chargeOfCam;
	}
	/**
	 * @author chila returns the rate of power consumption
	 * @return
	 */
	public double getCameraPowerConsumption() {
		return camPower;
	}
	
	/**
	 * @author chila returns the charger setting in external
	 * @return
	 */
	public int getChargerSetting() {
		return chargeSetting;
	}
	/**
	 * @author chila returns the total drain
	 * @return
	 */
	public double getTotalDrain() {
		return totalDrain;
	}
	/**
	 * @author chila acts like moving the battery to the external charger
	 */
	public void moveBatteryExternal() {
		chargeOfCam = 0;
	}
	/**
	 * @author chila acts like putting the battery inside the camera
	 */
	public void moveBatteryCamera() {
		chargeOfCam = chargeOfBattery;
		chargeYN = 1;
		
	}
	/**
	 * @author chila acts like removing the battery from the camera
	 */
	public void removeBattery() { 
		chargeOfCam = 0;
		chargeYN = 0;
		setCameraPowerConsumption(0);
	}
	/**
	 * @author chila sets the power consumption rate with the given parameter
	 * @param cameraPowerConsumption
	 */
	public void setCameraPowerConsumption(double cameraPowerConsumption) {
		camPower = cameraPowerConsumption;
	}
}