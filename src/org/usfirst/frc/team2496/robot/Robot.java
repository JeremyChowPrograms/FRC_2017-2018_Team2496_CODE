package org.usfirst.frc.team2496.robot;

import com.shwinlib.ShwinDrive;
import com.shwinlib.ShwinPID;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
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
	// Servo servo = new Servo(0);
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
	SpeedController servo = new Talon(0);
	boolean startingOnLeft = false; // true or false
	boolean scaleSwitchMode = false;

	public Robot() {

	}

	@Override
	public void robotInit() {
		claw2.setInverted(true);
		claw.setInverted(true);
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(160, 90);// 96, 54
		en0.setDistancePerPulse(8 * Math.PI / 200);
		en1.setDistancePerPulse(8 * Math.PI / 200);
		en2.setDistancePerPulse(Math.PI / 200);
		SmartDashboard.putBoolean("StartingOnLeft", startingOnLeft);
	}

	@Override
	public void autonomous() {
		en0.reset();
		en1.reset();
		en2.reset();
		fixedHeight = 0.0d;
		gyro.reset();

		Thread servoDown = new Thread() {
			@Override
			public void run() {
				servo.set(-1);
				Timer.delay(8);
				servo.set(0);
			}
		};
		servoDown.start();
		Thread liftControl = new Thread() {
			@Override
			public void run() {
				ShwinPID pid = new ShwinPID(speed, 0, 20000f, 0);
				while (isAutonomous() && isEnabled()) {
					pid.updateSpeed(speed, 0, 20000f, 0);
					double error = fixedHeight - en2.getDistance();
					double debug = pid.doPID(error);
					lift.set(debug);
				}
			}
		};
		liftControl.start();
		String gameCode = DriverStation.getInstance().getGameSpecificMessage();
		claw.set(0.15);
		claw2.set(0.15);
		if (scaleSwitchMode) {
			if ((gameCode.charAt(0) == 'L') == startingOnLeft && true) {
				if (!startingOnLeft) {
					Timer.delay(2);
					double kin = 0;
					double kinscale = 0.02;
					while ((en0.getDistance() < 126.85 || en1.getDistance() < 126.85) && isAutonomous()) {
						sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
						kin = (en1.getDistance() - en0.getDistance());
					}
					sd.tankDrive(-1, 1);
					Timer.delay(0.1);
					sd.tankDrive(0, 0);
					// double diff = 1;
					fixedHeight = 17.0d;
					speed = 0.3f;
					ShwinPID pid = new ShwinPID(0.008f, 0, 200000f, 0);
					gyro.reset();
					double doPid = 0;
					while (90 + gyro.getAngle() > 10 && isAutonomous()) {
						doPid = pid.doPID(95 + gyro.getAngle());
						sd.tankDrive(-0.18 - doPid, -0.18 - doPid);
					}
					sd.tankDrive(0.2, 0.2);
					Timer.delay(0.1);
					sd.tankDrive(0, 0);
					Timer.delay(0.5);
					en0.reset();
					en1.reset();
					while ((en0.getDistance() < 10 || en1.getDistance() < 10) && isAutonomous()) {
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
				} else {
					double kin = 0;
					double kinscale = 0.01;
					while ((en0.getDistance() < 136.85 || en1.getDistance() < 136.85) && isAutonomous()) {
						sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
						kin = (en1.getDistance() - en0.getDistance());
					}
					sd.tankDrive(-1, 1);
					Timer.delay(0.1);
					sd.tankDrive(0, 0);
					// double diff = 1;
					fixedHeight = 17.0d;
					speed = 0.15f;

					// ShwinPID pid = new ShwinPID(0.01f, 0, 200000f, 0);
					gyro.reset();
					double diff = 0.0d;
					while (gyro.getAngle() < 85 && isAutonomous()) {
						diff = (95.0 - gyro.getAngle()) / 90.0;
						sd.tankDrive(0.3 + 0.3 * diff, 0.3 + 0.3 * diff);
					}
					sd.tankDrive(0, 0);
					en0.reset();
					en1.reset();

					en0.reset();
					en1.reset();
					while ((en0.getDistance() < 24 || en1.getDistance() < 24) && isAutonomous()) {
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
			} else {
				/*
				 * double kin = 0; double kinscale = 0.02; en0.reset();
				 * en1.reset(); while (en0.getDistance() < 130 ||
				 * en1.getDistance() < 130) { sd.tankDrive(0.4 + kin * kinscale,
				 * -0.4 + kin * kinscale); kin = (en1.getDistance() -
				 * en0.getDistance()); } sd.tankDrive(-1, 1); Timer.delay(0.1);
				 * sd.tankDrive(0, 0);
				 */

				if ((gameCode.charAt(1) == 'L') == startingOnLeft && false) {
					if (!startingOnLeft) {
						double kin = 0;
						double kinscale = 0.02;

						en0.reset();
						en1.reset();
						while (en0.getDistance() < 300 || en1.getDistance() < 300) {
							sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
							kin = (en1.getDistance() - en0.getDistance());
						}
						sd.tankDrive(-1, 1);
						Timer.delay(0.1);
						sd.tankDrive(0, 0);// start
						ShwinPID pid = new ShwinPID(0.008f, 0, 200000f, 0);
						gyro.reset();
						double doPid = 0;
						while (90 + gyro.getAngle() > 10) {
							doPid = 0.7 * pid.doPID(90 + gyro.getAngle());
							sd.tankDrive(-0.25 - doPid, -0.25 - doPid);
						}
						sd.tankDrive(0.5, 0.5);
						Timer.delay(0.05);
						sd.tankDrive(0, 0);
						en0.reset();
						en1.reset();
						while (en0.getDistance() > -12 || en1.getDistance() > -12) {
							sd.tankDrive(-0.3 - kin * kinscale, 0.3 - kin * kinscale);
							kin = (en1.getDistance() - en0.getDistance());
						}
						sd.tankDrive(1, -1);
						Timer.delay(0.1);
						sd.tankDrive(0, 0);
						fixedHeight = 34.0;
						Timer.delay(
								3);/*
									 * en0.reset(); en1.reset(); while
									 * (en0.getDistance() < 5 ||
									 * en1.getDistance() < 5) { sd.tankDrive(0.3
									 * + kin * kinscale, -0.3 + kin * kinscale);
									 * kin = (en1.getDistance() -
									 * en0.getDistance()); } sd.tankDrive(-1,
									 * 1); Timer.delay(0.1); sd.tankDrive(0, 0);
									 */

						claw.set(-1);
						claw2.set(-1);
						Timer.delay(1);
						claw.set(0);
						claw2.set(
								0);/*
									 * en0.reset(); en1.reset(); while
									 * (en0.getDistance() > -5 ||
									 * en1.getDistance() > -5) {
									 * sd.tankDrive(-0.3 - kin * kinscale, 0.3 -
									 * kin * kinscale); kin = (en1.getDistance()
									 * - en0.getDistance()); } sd.tankDrive(1,
									 * -1); Timer.delay(0.1); sd.tankDrive(0,
									 * 0);
									 */
					} else {
						double kin = 0;
						double kinscale = 0.02;
						en0.reset();
						en1.reset();
						while (en0.getDistance() < 300 || en1.getDistance() < 300) {
							sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
							kin = (en1.getDistance() - en0.getDistance());
						}
						sd.tankDrive(-1, 1);
						Timer.delay(0.1);
						sd.tankDrive(0, 0);

						/*
						 * // turning RIGHT 90 degrees ShwinPID pid = new
						 * ShwinPID(0.01f, 0, 200000f, 0); gyro.reset(); double
						 * doPid = 0; while (90 - gyro.getAngle() > 10) { doPid
						 * = pid.doPID(90 - gyro.getAngle());
						 * sd.tankDrive(0.1+doPid, 0.1+doPid); }
						 * sd.tankDrive(-0.5, -0.5); Timer.delay(0.1);
						 * sd.tankDrive(0, 0); // end
						 */
						double diff = 0.0d;
						gyro.reset();
						while (gyro.getAngle() < 85) {
							sd.tankDrive(0.7, 0.7);
						}
						sd.tankDrive(-0.5, -.5);
						Timer.delay(0.05);
						sd.tankDrive(0, 0);

						en0.reset();
						en1.reset();
						while (en0.getDistance() > -12 || en1.getDistance() > -12) {
							sd.tankDrive(-0.3 + kin * kinscale, 0.3 + kin * kinscale);
							kin = (en1.getDistance() - en0.getDistance());
						}
						sd.tankDrive(1, -1);
						Timer.delay(0.1);
						sd.tankDrive(0, 0);
						fixedHeight = 34.0;
						Timer.delay(3);
						/*
						 * en0.reset(); en1.reset(); while (en0.getDistance() <
						 * 5 || en1.getDistance() < 5) { sd.tankDrive(0.3 + kin
						 * * kinscale, -0.3 + kin * kinscale); kin =
						 * (en1.getDistance() - en0.getDistance()); }
						 * sd.tankDrive(-1, 1); Timer.delay(0.1);
						 * sd.tankDrive(0, 0);
						 */

						claw.set(-1);
						claw2.set(-1);
						Timer.delay(1);
						claw.set(0);
						claw2.set(0);
						/*
						 * en0.reset(); en1.reset(); while (en0.getDistance() >
						 * -5 || en1.getDistance() > -5) { sd.tankDrive(-0.3 -
						 * kin * kinscale, 0.3 - kin * kinscale); kin =
						 * (en1.getDistance() - en0.getDistance()); }
						 * sd.tankDrive(1, -1); Timer.delay(0.1);
						 * sd.tankDrive(0, 0);
						 */
					}
				} else {
					double kin = 0;
					double kinscale = 0.02;
					en0.reset();
					en1.reset();
					while (en0.getDistance() < 130 || en1.getDistance() < 130) {
						sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
						kin = (en1.getDistance() - en0.getDistance());
					}
					sd.tankDrive(-1, 1);
					Timer.delay(0.1);
					sd.tankDrive(0, 0);
				} /*
					 * if (!startingOnLeft) { double kin = 0; double kinscale =
					 * 0.01; // forward 3ft while ((en0.getDistance() < 36 ||
					 * en1.getDistance() < 36) && isAutonomous()) {
					 * sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin *
					 * kinscale); kin = (en1.getDistance() - en0.getDistance());
					 * } sd.tankDrive(-1, 1); Timer.delay(0.1); sd.tankDrive(0,
					 * 0); en0.reset(); en1.reset();
					 * 
					 * // turn gyro.reset();
					 * 
					 * double diff = 1; ShwinPID pid = new ShwinPID(0.008f, 0,
					 * 200000f, 0); gyro.reset(); double doPid = 0; while (90 +
					 * gyro.getAngle() > 5 && isAutonomous()) { doPid =
					 * pid.doPID(90 + gyro.getAngle()); sd.tankDrive(-0.01 -
					 * doPid, -0.01 - doPid); } sd.tankDrive(1, 1);
					 * Timer.delay(0.1); sd.tankDrive(0, 0);
					 * 
					 * en0.reset(); en1.reset();
					 * 
					 * // forward 9ft while ((en0.getDistance() < 6 * 12 +34||
					 * en1.getDistance() < 6 * 12+34) && isAutonomous()) {
					 * sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin *
					 * kinscale); kin = (en1.getDistance() - en0.getDistance());
					 * } sd.tankDrive(-1, 1); Timer.delay(0.1); sd.tankDrive(0,
					 * 0); en0.reset(); en1.reset();
					 * 
					 * // turn gyro.reset(); while (gyro.getAngle() < 90 &&
					 * isAutonomous()) { diff = (90.0 - gyro.getAngle()) / 90.0;
					 * sd.tankDrive(0.17 + 0.3 * diff, 0.17 + 0.3 * diff); }
					 * sd.tankDrive(0, 0); en0.reset(); en1.reset();
					 * 
					 * // fixedHeight = 17.0d; speed = 0.15f; // forward while
					 * ((en0.getDistance() < 51.85 || en1.getDistance() < 51.85)
					 * && isAutonomous()) { sd.tankDrive(0.4 + kin * kinscale,
					 * -0.4 + kin * kinscale); kin = (en1.getDistance() -
					 * en0.getDistance()); } sd.tankDrive(-1, 1);
					 * Timer.delay(0.1); sd.tankDrive(0, 0);
					 * 
					 * Timer.delay(0.5); claw.set(-1); claw2.set(-1);
					 * Timer.delay(1); claw.set(0); claw2.set(0); } else {
					 * 
					 * double kin = 0; double kinscale = 0.01; // forward 3ft
					 * while ((en0.getDistance() < 36 || en1.getDistance() < 36)
					 * && isAutonomous()) { sd.tankDrive(0.4 + kin * kinscale,
					 * -0.4 + kin * kinscale); kin = (en1.getDistance() -
					 * en0.getDistance()); } sd.tankDrive(-1, 1);
					 * Timer.delay(0.1); sd.tankDrive(0, 0); en0.reset();
					 * en1.reset();
					 * 
					 * // turn double diff = 1; gyro.reset(); while
					 * (gyro.getAngle() < 90 && isAutonomous()) { diff = (90.0 -
					 * gyro.getAngle()) / 90.0; sd.tankDrive(0.17 + 0.3 * diff,
					 * 0.17 + 0.3 * diff); } sd.tankDrive(0, 0); en0.reset();
					 * en1.reset();
					 * 
					 * // forward 9ft while ((en0.getDistance() < 9 * 12 ||
					 * en1.getDistance() < 9 * 12) && isAutonomous()) {
					 * sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin *
					 * kinscale); kin = (en1.getDistance() - en0.getDistance());
					 * } sd.tankDrive(-1, 1); Timer.delay(0.1); sd.tankDrive(0,
					 * 0); en0.reset(); en1.reset();
					 * 
					 * // turn gyro.reset(); ShwinPID pid = new ShwinPID(0.01f,
					 * 0, 200000f, 0); gyro.reset(); double doPid = 0; while (90
					 * + gyro.getAngle() > 5 && isAutonomous()) { doPid =
					 * pid.doPID(90 + gyro.getAngle()); sd.tankDrive(-0.01 -
					 * doPid, -0.01 - doPid); } sd.tankDrive(1, 1);
					 * Timer.delay(0.1); sd.tankDrive(0, 0); speed = 0.15f; //
					 * fixedHeight = 18.0d; en0.reset(); en1.reset(); // forward
					 * while ((en0.getDistance() < 51.85 || en1.getDistance() <
					 * 51.85) && isAutonomous()) { sd.tankDrive(0.4 + kin *
					 * kinscale, -0.4 + kin * kinscale); kin =
					 * (en1.getDistance() - en0.getDistance()); }
					 * sd.tankDrive(-1, 1); Timer.delay(0.1); sd.tankDrive(0,
					 * 0);
					 * 
					 * Timer.delay(0.5); claw.set(-1); claw2.set(-1);
					 * Timer.delay(1); claw.set(0); claw2.set(0); }
					 */
			}
		} else {
			if (gameCode.startsWith("R")) {
				Timer.delay(4);
				fixedHeight = 17.0d;
				speed = 0.15f;
				double kin = 0;
				double kinscale = 0.01;
				while ((en0.getDistance() < 96.85 || en1.getDistance() < 96.85) && isAutonomous()) {
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

			} else {
				Timer.delay(2);
				double kin = 0;
				double kinscale = 0.01; // forward 3ft
				while ((en0.getDistance() < 36 || en1.getDistance() < 36) && isAutonomous()) {
					sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
					kin = (en1.getDistance() - en0.getDistance());
				}
				sd.tankDrive(-0.4, 0.4);
				Timer.delay(0.05);
				sd.tankDrive(0, 0);
				en0.reset();
				en1.reset();

				// turn
				gyro.reset();

				double diff = 1;
				ShwinPID pid = new ShwinPID(0.008f, 0, 200000f, 0);
				gyro.reset();
				double doPid = 0;
				while (90 + gyro.getAngle() > 10 && isAutonomous()) {
					doPid = 0.7 * pid.doPID(90 + gyro.getAngle());
					sd.tankDrive(-0.25 - doPid, -0.25 - doPid);
				}
				sd.tankDrive(0.5, 0.5);
				Timer.delay(0.05);
				sd.tankDrive(0, 0);

				en0.reset();
				en1.reset();

				// forward 9ft
				while ((en0.getDistance() < 9 * 12 || en1.getDistance() < 9 * 12) && isAutonomous()) {
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
				while (gyro.getAngle() < 85 && isAutonomous()) {
					diff = (90.0 - gyro.getAngle()) / 90.0;
					sd.tankDrive(0.3 + 0.3 * diff, 0.3 + 0.3 * diff);
				}
				sd.tankDrive(0, 0);
				en0.reset();
				en1.reset();

				fixedHeight = 17.0d;
				speed = 0.15f;
				// forward
				while ((en0.getDistance() < 48 || en1.getDistance() < 48) && isAutonomous()) {
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
	double target = 0.0;
	int stage = 0;
	float speed = 0.2f;

	@Override
	public void operatorControl() { // Main thread
		/* COMMENT ME OUT */
		// ShwinPID liftPID = new ShwinPID(0.01, 0.0, 0.0, 20000);
		// en2.reset();
		/* COMMENT ME OUT */
		en0.reset();
		en1.reset();
		// en2.reset();
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
			// if(stick1.getRawButton(3)) target = 1000;
			// if(stick1.getRawButton(2)) target = 0;
			// lift.set

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
				claw.set(0.15);
				claw2.set(0.15);
			}
			if (stick1.getRawButton(2)) {
				speed = 0.2f;
				fixedHeight = 8.0d;
			}
			if (stick1.getRawButton(3)) {
				speed = 0.03f;
				fixedHeight = -1.5d;
			}
			if (stick1.getRawButton(4)) {

				speed = (en2.getDistance() >= 15) ? 0.04f : 0.2f;
				fixedHeight = 15.0d;
			}
			if (stick1.getRawButton(5)) {
				speed = 0.13f;
				fixedHeight = 34.0d;
			}
			if (stick0.getRawButton(8)) {
				climb.set(-ts1);
			} else if (stick0.getRawButton(9)) {
				climb.set(ts2);
			} else {
				climb.set(0);
			}
			// turning RIGHT 90 degrees
			if (stick0.getRawButton(5)) {
				double diff = 0.0d;
				gyro.reset();
				while (gyro.getAngle() < 85) {
					diff = (90.0 - gyro.getAngle()) / 90.0;
					sd.tankDrive(0.3 + 0.3 * diff, 0.3 + 0.3 * diff);
				}
				sd.tankDrive(-0.5, -.5);
				Timer.delay(0.05);
				sd.tankDrive(0, 0);

			}
			// turning LEFT 90 degrees
			if (stick0.getRawButton(4)) { // start
				ShwinPID pid = new ShwinPID(0.008f, 0, 200000f, 0);
				gyro.reset();
				double doPid = 0;
				while (90 + gyro.getAngle() > 10) {
					doPid = 0.7 * pid.doPID(90 + gyro.getAngle());
					sd.tankDrive(-0.25 - doPid, -0.25 - doPid);
				}
				sd.tankDrive(0.5, 0.5);
				Timer.delay(0.05);
				sd.tankDrive(0, 0);
			}
			if (stick1.getRawButton(8)) {
				servo.set(1);
			} else if (stick1.getRawButton(9)) {
				servo.set(-1);
			} else {
				servo.set(0);
			}
			if (stick0.getRawButton(6)) {
				gyro.calibrate();
				gyro.reset();
			}
			if (stick1.getRawButton(11)) {
				en2.reset();
				speed = 0.1f;
				fixedHeight = 4.0d;
			}
			if (stick1.getRawButton(10)) {
				en2.reset();
				speed = 0.1f;
				fixedHeight = -2.0d;
			}
			Timer.delay(0.005);
		}
	}

	@Override
	protected void disabled() {
		servo.set(0);
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
