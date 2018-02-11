package org.usfirst.frc.team2496.robot;

import com.shwinlib.ShwinDrive;

<<<<<<< HEAD
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
=======
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
>>>>>>> master
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
=======
>>>>>>> master
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("deprecation")
public class Robot extends SampleRobot {
	ADXRS450_Gyro gyro = new ADXRS450_Gyro();
<<<<<<< HEAD
	ShwinDrive sd = new ShwinDrive(3, 1, 4, 2);
	Joystick stick0 = new Joystick(0);
	Joystick stick1 = new Joystick(1);
	Encoder en0 = new Encoder(2, 3);
	Encoder en1 = new Encoder(1, 0);
	Encoder en2 = new Encoder(4, 5);
	SpeedController claw = new Talon(5);
	SpeedController claw2 = new Talon(6);
	SpeedController lift = new Talon(7);
	SpeedController climb = new Talon(8);
	boolean startingOnLeft = false; //true or false

	public Robot() {

=======
	ShwinDrive sd = new ShwinDrive(3,1,4,2);
	Joystick stick0 = new Joystick(0);
	Joystick stick1 = new Joystick(1);
	Encoder en0 = new Encoder(0, 1);
	Encoder en1 = new Encoder(3,2);
	boolean startingOnLeft = false;

	public Robot() {
		
>>>>>>> master
	}

	@Override
	public void robotInit() {
<<<<<<< HEAD

		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		en0.setDistancePerPulse(8 * Math.PI / 200);
		en1.setDistancePerPulse(8 * Math.PI / 200);
		en2.setDistancePerPulse(Math.PI / 200);
		gyro.calibrate();
=======
		
		en0.setDistancePerPulse(12*Math.PI/800);
		en1.setDistancePerPulse(12*Math.PI/800);
>>>>>>> master
	}

	@Override
	public void autonomous() {
		en0.reset();
		en1.reset();
		gyro.reset();
<<<<<<< HEAD
		String gameCode = DriverStation.getInstance().getGameSpecificMessage();
		if (gameCode.startsWith("L") == startingOnLeft) {
			double kin = 0;
			double kinscale = 0.01;
			while (en0.getDistance() < 90.85 || en1.getDistance() < 90.85) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() - en0.getDistance());
=======
		String gameCode=DriverStation.getInstance().getGameSpecificMessage();
		if(gameCode.startsWith("L")==startingOnLeft){
			double kin = 0;
			double kinscale = 0.01;
			while(en0.getDistance()<135.85||en1.getDistance()<135.85){
				sd.tankDrive(0.4+kin*kinscale, -0.4+kin*kinscale);
				kin=(en1.getDistance()-en0.getDistance());
>>>>>>> master
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
<<<<<<< HEAD
		} else if (!startingOnLeft) {
			double kin = 0;
			double kinscale = 0.01; // forward 3ft
			while (en0.getDistance() < 36 || en1.getDistance() < 36) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() - en0.getDistance());
=======
		}else if(!startingOnLeft){
			double kin = 0;
			double kinscale = 0.01;
			//forward 3ft
			while(en0.getDistance()<36||en1.getDistance()<36){
				sd.tankDrive(0.4+kin*kinscale, -0.4+kin*kinscale);
				kin=(en1.getDistance()-en0.getDistance());
>>>>>>> master
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
			en0.reset();
			en1.reset();
<<<<<<< HEAD

			// turn
			gyro.reset();

			double diff = 1;
			while (gyro.getAngle() > -90) {
				
				sd.tankDrive(-0.15, -0.09);
			}
			sd.tankDrive(1, 1);

			Timer.delay(0.105);
			sd.tankDrive(0, 0);

			en0.reset();
			en1.reset();

			// forward 9ft
			while (en0.getDistance() < 9 * 12 || en1.getDistance() < 9 * 12) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() - en0.getDistance());
=======
			
			//turn
			gyro.reset();
			while(gyro.getAngle()>-90){
				sd.tankDrive(-0.15, -0.09);
			}
			sd.tankDrive(.5, .5);

			Timer.delay(0.05);
			sd.tankDrive(0, 0);
			
			en0.reset();
			en1.reset();
			
			//forward 9ft
			while(en0.getDistance()<9*12||en1.getDistance()<9*12){
				sd.tankDrive(0.4+kin*kinscale, -0.4+kin*kinscale);
				kin=(en1.getDistance()-en0.getDistance());
>>>>>>> master
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
			en0.reset();
			en1.reset();
<<<<<<< HEAD

			// turn
			gyro.reset();
			while (gyro.getAngle() < 90) {
				diff = (90.0-gyro.getAngle())/90.0;
				sd.tankDrive(0.05+0.5*diff,0.05+ 0.5*diff);
			}
=======
			
			//turn
			//turn
			gyro.reset();
			while(gyro.getAngle()<90){
				sd.tankDrive(0.3, 0.17);
			}
			sd.tankDrive(-.5, -.5);

			Timer.delay(0.05);
>>>>>>> master
			sd.tankDrive(0, 0);
			en0.reset();
			en1.reset();

<<<<<<< HEAD
			// forward
			while (en0.getDistance() < 51.85 || en1.getDistance() < 51.85) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() - en0.getDistance());
=======
			//forward
			while(en0.getDistance()<99.85||en1.getDistance()<99.85){
				sd.tankDrive(0.4+kin*kinscale, -0.4+kin*kinscale);
				kin=(en1.getDistance()-en0.getDistance());
>>>>>>> master
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
<<<<<<< HEAD

		} else {

			double kin = 0;
			double kinscale = 0.01; // forward 3ft
			while (en0.getDistance() < 36 || en1.getDistance() < 36) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() - en0.getDistance());
=======
			
		}else{

			double kin = 0;
			double kinscale = 0.01;
			//forward 3ft
			while(en0.getDistance()<36||en1.getDistance()<36){
				sd.tankDrive(0.4+kin*kinscale, -0.4+kin*kinscale);
				kin=(en1.getDistance()-en0.getDistance());
>>>>>>> master
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
			en0.reset();
			en1.reset();

<<<<<<< HEAD
			// turn
			gyro.reset();
			while (gyro.getAngle() < 90) {
=======
			//turn
			gyro.reset();
			while(gyro.getAngle()<90){
>>>>>>> master
				sd.tankDrive(0.3, 0.17);
			}
			sd.tankDrive(-.5, -.5);

<<<<<<< HEAD
			Timer.delay(0.04);
=======
			Timer.delay(0.05);
>>>>>>> master
			sd.tankDrive(0, 0);
			en0.reset();
			en1.reset();

<<<<<<< HEAD
			// forward 9ft
			while (en0.getDistance() < 9 * 12 || en1.getDistance() < 9 * 12) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() -  en0.getDistance());
=======
			
			//forward 9ft
			while(en0.getDistance()<9*12||en1.getDistance()<9*12){
				sd.tankDrive(0.4+kin*kinscale, -0.4+kin*kinscale);
				kin=(en1.getDistance()-en0.getDistance());
>>>>>>> master
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
			en0.reset();
			en1.reset();
<<<<<<< HEAD

			// turn
			gyro.reset();
			while (gyro.getAngle() > -90) {
				sd.tankDrive(-0.15, -0.09);
			}
			sd.tankDrive(1, 1);

			Timer.delay(0.1);
			sd.tankDrive(0, 0);

			en0.reset();
			en1.reset(); // forward
			while (en0.getDistance() < 51.85 || en1.getDistance() < 51.85) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() - en0.getDistance());
=======
			
			
			//turn
			gyro.reset();
			while(gyro.getAngle()>-90){
				sd.tankDrive(-0.15, -0.09);
			}
			sd.tankDrive(.5, .5);

			Timer.delay(0.05);
			sd.tankDrive(0, 0);
			
			en0.reset();
			en1.reset();
			//forward
			while(en0.getDistance()<99.85||en1.getDistance()<99.85){
				sd.tankDrive(0.4+kin*kinscale, -0.4+kin*kinscale);
				kin=(en1.getDistance()-en0.getDistance());
>>>>>>> master
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
		}
	}
	/**/

	boolean cupControl = false;

	@Override
<<<<<<< HEAD
	public void operatorControl() { // Main thread
		en0.reset();
		en1.reset();
		en2.reset();
		double ts1, ts2;
		gyro.reset();
		while (isOperatorControl() && isEnabled()) {
			ts1 = (-stick0.getZ() / 2) + 0.5d;
			ts2 = (-stick1.getZ() / 2) + 0.5d;
			sd.tankDrive(-stick0.getY() * ts1, stick1.getY() * ts2);
			SmartDashboard.putNumber("gyro: ", gyro.getAngle());
			SmartDashboard.putNumber("L Enc", en0.getDistance());
			SmartDashboard.putNumber("R Enc", en1.getDistance());
			SmartDashboard.putNumber("Lift", en2.getDistance());
			SmartDashboard.putNumber("ts1", ts1);
			SmartDashboard.putNumber("ts2", ts2);
			if (!cupControl) {
				if (stick0.getRawButton(1)) {
					claw.set(1);
					claw2.set(1);
				} else if (stick0.getRawButton(3)) {
					claw.set(-1);
					claw2.set(-1);
				} else {
					claw.set(0);
					claw2.set(0);
				}

				if (stick1.getRawButton(1)) {
					lift.set(ts1);
				} else if (stick1.getRawButton(3)) {
					lift.set(-ts2);
				} else {
					lift.set(0);
				}
			}
			if (stick0.getRawButton(8)) {
				climb.set(1);
			} else if (stick0.getRawButton(9)) {
				climb.set(-1);
			} else {
				climb.set(0);
			}
			if (stick1.getRawButton(2)) {
				new Thread() {
					@Override
					public void run() {
						cupControl = true;
						try {
							lift.set(0.75);
							Timer.delay(2);// 59 in
							lift.set(0);
							claw.set(-1);
							claw2.set(-1);
							Timer.delay(2);
							claw.set(0);
							claw2.set(0);
							lift.set(-0.3);
							Timer.delay(2);
							lift.set(0);
						} catch (Exception e) {
							e.printStackTrace();
						}
						cupControl = false;
					}
				}.start();
			}
			Timer.delay(0.005);
=======
	public void operatorControl() { //Main thread
		en0.reset();
		en1.reset();
		gyro.reset();
		while (isOperatorControl() && isEnabled()) {
			sd.tankDrive(-stick0.getY(), stick1.getY());
			SmartDashboard.putNumber("gyro: ", gyro.getAngle());
			SmartDashboard.putNumber("L Enc", en0.getDistance());
			SmartDashboard.putNumber("R Enc", en1.getDistance());
			Timer.delay(0.005); 
>>>>>>> master
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
		gyro.reset();
		double diff = 1;
		while (gyro.getAngle() < 90) {
			diff = (90.0-gyro.getAngle())/90.0;
			sd.tankDrive(0.5*diff, 0.5*diff);
		}
		sd.tankDrive(0, 0);

	}
}
