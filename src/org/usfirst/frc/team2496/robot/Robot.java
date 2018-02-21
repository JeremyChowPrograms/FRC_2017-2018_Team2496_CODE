package org.usfirst.frc.team2496.robot;

import com.shwinlib.ShwinDrive;
import com.shwinlib.ShwinPID;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("deprecation")
public class Robot extends SampleRobot {
	ADXRS450_Gyro gyro = new ADXRS450_Gyro();
//	Servo servo = new Servo(0);
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
	boolean startingOnLeft = true; // true or false

	public Robot() {

	}

	@Override
	public void robotInit() {
		claw2.setInverted(true);
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		en0.setDistancePerPulse(8 * Math.PI / 200);
		en1.setDistancePerPulse(8 * Math.PI / 200);
		en2.setDistancePerPulse(Math.PI / 200);
		SmartDashboard.putBoolean("StartingOnLeft", startingOnLeft);
	}

	@Override
	public void autonomous() {
		startingOnLeft = SmartDashboard.getBoolean("StartingOnLeft", startingOnLeft);
		en0.reset();
		en1.reset();
		en2.reset();
		fixedHeight = 0.0d;
		gyro.reset();
		Thread liftControl = new Thread() {
			@Override
			public void run() {
				ShwinPID pid = new ShwinPID(speed, 0, 20000f, 0);
				while (isAutonomous() && isEnabled()) {
					pid.updateSpeed(speed, 0, 20000f, 0);
					double error = fixedHeight - en2.getDistance();
					double debug = pid.doPID(error);
					lift.set(debug);
					System.out.println(error + " " + debug);
				}
			}
		};
		liftControl.start();
		String gameCode = DriverStation.getInstance().getGameSpecificMessage();
		if((gameCode.charAt(1)=='L')==startingOnLeft){
			if (!startingOnLeft) {
				double kin = 0;
				double kinscale = 0.01;

				en0.reset();
				en1.reset();
				while (en0.getDistance() < 300 || en1.getDistance() <300) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);
				//turning LEFT
				ShwinPID pid = new ShwinPID(0.008f, 0, 200000f, 0);
				gyro.reset();
				double doPid = 0;
				while (90 + gyro.getAngle() > 0) {
					doPid = pid.doPID(90 + gyro.getAngle());
					sd.tankDrive(-0.01 - doPid, -0.01 - doPid);
				}
				sd.tankDrive(1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0); // end
				

				fixedHeight = 30.0;
				Timer.delay(2);
				en0.reset();
				en1.reset();
				while (en0.getDistance() < 24 || en1.getDistance() < 24) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);

				claw.set(-1);
				claw2.set(-1);
				Timer.delay(1);
				claw.set(0);
				claw2.set(0);
				en0.reset();
				en1.reset();
				while (en0.getDistance() >-24 || en1.getDistance() >-24) {
					sd.tankDrive(-0.4 - kin * kinscale, 0.4 - kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(1, -1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);
			}else{
				double kin = 0;
				double kinscale = 0.01;
				en0.reset();
				en1.reset();
				while (en0.getDistance() < 300 || en1.getDistance() < 300) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);

				// turning RIGHT 90 degrees
				ShwinPID pid = new ShwinPID(0.01f, 0, 200000f, 0);
				gyro.reset();
				double doPid = 0;
				while (90 - gyro.getAngle() > 0) {
					doPid = pid.doPID(90 - gyro.getAngle());
					sd.tankDrive(doPid, doPid);
				}
				sd.tankDrive(-0.5, -0.5);
				Timer.delay(0.1);
				sd.tankDrive(0, 0); // end

				fixedHeight = 30.0;
				Timer.delay(2);
				en0.reset();
				en1.reset();
				while (en0.getDistance() < 24 || en1.getDistance() < 24) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);

				claw.set(-1);
				claw2.set(-1);
				Timer.delay(1);
				claw.set(0);
				claw2.set(0);
				
				
				en0.reset();
				en1.reset();
				while (en0.getDistance() >-24 || en1.getDistance() >-24) {
					sd.tankDrive(-0.4 - kin * kinscale, 0.4 - kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(1, -1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);
			}
		}
		else{
			if (gameCode.startsWith("L") == startingOnLeft) {
				if (!startingOnLeft) {
					double kin = 0;
					double kinscale = 0.01;
					fixedHeight = 17.0d;
					while (en0.getDistance() < 90.85 || en1.getDistance() < 90.85) {
						sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
						kin = (en1.getDistance() - en0.getDistance());
					}
					sd.tankDrive(-1, 1);
					Timer.delay(0.1);
					sd.tankDrive(0, 0);
					Timer.delay(2);
					claw.set(-1);
					claw2.set(-1);
					Timer.delay(1);
					claw.set(0);
					claw2.set(0);
				} else {
					double kin = 0;
					double kinscale = 0.01;
					while (en0.getDistance() < 126.85 || en1.getDistance() < 126.85) {
						sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
						kin = (en1.getDistance() - en0.getDistance());
					}
					sd.tankDrive(-1, 1);
					Timer.delay(0.1);
					sd.tankDrive(0, 0);
				//	double diff = 1;
					fixedHeight = 17.0d;
					speed = 0.15f;

					ShwinPID pid = new ShwinPID(0.01f, 0, 200000f, 0);
					gyro.reset();
					double doPid = 0;
					while (90 - gyro.getAngle() > 0) {
						doPid = pid.doPID(90 - gyro.getAngle());
						sd.tankDrive(doPid, doPid);
					}
					sd.tankDrive(-0.5, -0.5);
					Timer.delay(0.1);
					sd.tankDrive(0, 0);

					en0.reset();
					en1.reset();
					while (en0.getDistance() < 24 || en1.getDistance() < 24) {
						sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
						kin = (en1.getDistance() - en0.getDistance());
					}
					sd.tankDrive(-1, 1);
					Timer.delay(0.1);
					sd.tankDrive(0, 0);
					Timer.delay(0.5);
					claw.set(-1);
					claw2.set(-1);
					Timer.delay(1);
					claw.set(0);
					claw2.set(0);
				}
			} else if (!startingOnLeft) {
				double kin = 0;
				double kinscale = 0.01; // forward 3ft
				while (en0.getDistance() < 36 || en1.getDistance() < 36) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);
				en0.reset();
				en1.reset();

				// turn
				gyro.reset();

				double diff = 1;
				ShwinPID pid = new ShwinPID(0.008f, 0, 200000f, 0);
				gyro.reset();
				double doPid = 0;
				while (90 + gyro.getAngle() > 0) {
					doPid = pid.doPID(90 + gyro.getAngle());
					sd.tankDrive(-0.01 - doPid, -0.01 - doPid);
				}
				sd.tankDrive(1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);

				en0.reset();
				en1.reset();

				// forward 9ft
				while (en0.getDistance() < 6 * 12 || en1.getDistance() < 6 * 12) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);
				en0.reset();
				en1.reset();

				// turn
				gyro.reset();
				while (gyro.getAngle() < 90) {
					diff = (90.0 - gyro.getAngle()) / 90.0;
					sd.tankDrive(0.17 + 0.3 * diff, 0.17 + 0.3 * diff);
				}
				sd.tankDrive(0, 0);
				en0.reset();
				en1.reset();

				fixedHeight = 17.0d;
				speed = 0.15f;
				// forward
				while (en0.getDistance() < 51.85 || en1.getDistance() < 51.85) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);

				System.out.println("Working...");
				Timer.delay(0.5);
				claw.set(-1);
				claw2.set(-1);
				Timer.delay(1);
				claw.set(0);
				claw2.set(0);
			} else {

				double kin = 0;
				double kinscale = 0.01; // forward 3ft
				while (en0.getDistance() < 36 || en1.getDistance() < 36) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);
				en0.reset();
				en1.reset();

				// turn
				double diff = 1;
				gyro.reset();
				while (gyro.getAngle() < 90) {
					diff = (90.0 - gyro.getAngle()) / 90.0;
					sd.tankDrive(0.17 + 0.3 * diff, 0.17 + 0.3 * diff);
				}
				sd.tankDrive(0, 0);
				en0.reset();
				en1.reset();

				// forward 9ft
				while (en0.getDistance() < 9 * 12 || en1.getDistance() < 9 * 12) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);
				en0.reset();
				en1.reset();

				// turn
				gyro.reset();
				ShwinPID pid = new ShwinPID(0.01f, 0, 200000f, 0);
				gyro.reset();
				double doPid = 0;
				while (90 + gyro.getAngle() > 0) {
					doPid = pid.doPID(90 + gyro.getAngle());
					sd.tankDrive(-0.01 - doPid, -0.01 - doPid);
				}
				sd.tankDrive(1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);
				speed = 0.15f;
				fixedHeight = 18.0d;
				en0.reset();
				en1.reset(); // forward
				while (en0.getDistance() < 51.85 || en1.getDistance() < 51.85) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0);

				Timer.delay(0.5);
				claw.set(-1);
				claw2.set(-1);
				Timer.delay(1);
				claw.set(0);
				claw2.set(0);
			}
		}
	}
	/**/

	double fixedHeight = 0.0d;
	int stage = 0;

	DigitalOutput doy = new DigitalOutput(6);
	float speed = 0.2f;

	@Override
	public void operatorControl() { // Main thread
		en0.reset();
		en1.reset();
		en2.reset();
		// fixedHeight = 0.0d; //Questionable
		double ts1, ts2;
		gyro.reset();
		Thread liftControl = new Thread() {
			@Override
			public void run() {
				ShwinPID pid = new ShwinPID(speed, 0, 20000f, 0);
				while (isOperatorControl() && isEnabled()) {
					pid.updateSpeed(speed, 0, 20000f, 0);
					double error = fixedHeight - en2.getDistance();
					double debug = pid.doPID(error);
					lift.set(debug);
				}
			}
		};
		liftControl.start();
		while (isOperatorControl() && isEnabled()) {
			ts1 = (-stick0.getZ() / 2) + 0.5d;
			ts2 = (-stick1.getZ() / 2) + 0.5d;
			SmartDashboard.putNumber("speed", speed);
			SmartDashboard.putNumber("Gyro", gyro.getAngle());
			SmartDashboard.putNumber("L Encoder", en0.getDistance());
			SmartDashboard.putNumber("R Encoder", en1.getDistance());
			SmartDashboard.putNumber("Lift", en2.getDistance());
			SmartDashboard.putNumber("Fixed Height", fixedHeight);
			SmartDashboard.putNumber("ts1", ts1);
			SmartDashboard.putNumber("ts2", ts2);
			sd.tankDrive(-stick0.getY() * ts1, stick1.getY() * ts2);
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
			if (stick1.getRawButton(2)) {
				speed = 0.2f;
				fixedHeight = 8.0d;
			}
			if (stick1.getRawButton(3)) {
				speed = 0.02f;
				fixedHeight = 3.0d;
			}
			if (stick1.getRawButton(4)) {
				speed = 0.2f;
				fixedHeight = 15.0d;
			}
			if (stick1.getRawButton(5)) {
				speed = 0.13f;
				fixedHeight = 30.0d;
			}
			if (stick0.getRawButton(8)) {
				climb.set(-ts1);
			} else if (stick0.getRawButton(9)) {
				climb.set(ts2);
			} else {
				climb.set(0);
			}
			// turning RIGHT 90 degrees
			if (stick1.getRawButton(7)) { // start
				ShwinPID pid = new ShwinPID(0.0067f, 0, 200000f, 0);
				gyro.reset();
				double doPid = 0;
				while (90 - gyro.getAngle() > 0) {
					doPid = pid.doPID(90 - gyro.getAngle());
					sd.tankDrive(doPid, doPid);
				}
				sd.tankDrive(-0.5, -0.5);
				Timer.delay(0.1);
				sd.tankDrive(0, 0); // end
			}
			// turning LEFT 90 degrees
			if (stick0.getRawButton(10)) { // start
				ShwinPID pid = new ShwinPID(0.008f, 0, 200000f, 0);
				gyro.reset();
				double doPid = 0;
				while (90 + gyro.getAngle() > 0) {
					doPid = pid.doPID(90 + gyro.getAngle());
					sd.tankDrive(-0.01 - doPid, -0.01 - doPid);
				}
				sd.tankDrive(1, 1);
				Timer.delay(0.1);
				sd.tankDrive(0, 0); // end
			}
			if (stick1.getRawButton(8)) {
				doy.pulse(0.0005);
			} else if (stick1.getRawButton(9)) {
				doy.pulse(0.0025);
			} else {
				doy.pulse(0.0015);
			}
			if (stick0.getRawButton(6)) {
				gyro.calibrate();
				gyro.reset();
			}
			Timer.delay(0.005);
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
			diff = (90.0 - gyro.getAngle()) / 90.0;
			sd.tankDrive(0.5 * diff, 0.5 * diff);
		}
		sd.tankDrive(0, 0);

	}
}
