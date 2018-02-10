package org.usfirst.frc.team2496.robot;

import com.shwinlib.ShwinDrive;

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
	boolean startingOnLeft = false;

	public Robot() {

	}

	@Override
	public void robotInit() {

		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		en0.setDistancePerPulse(8 * Math.PI / 200);
		en1.setDistancePerPulse(8 * Math.PI / 200);
		en2.setDistancePerPulse(0.5 * Math.PI / 200);
	}

	@Override
	public void autonomous() {
		en0.reset();
		en1.reset();
		gyro.reset();
		String gameCode = DriverStation.getInstance().getGameSpecificMessage();
		if (gameCode.startsWith("L") == startingOnLeft) {
			double kin = 0;
			double kinscale = 0.01;
			while (en0.getDistance() < 135.85 || en1.getDistance() < 135.85) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() - en0.getDistance());
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
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
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
			en0.reset();
			en1.reset();

			// turn
			gyro.reset();
			while (gyro.getAngle() < 90) {
				sd.tankDrive(0.3, 0.17);
			}
			sd.tankDrive(-.5, -.5);

			Timer.delay(0.025);
			sd.tankDrive(0, 0);
			en0.reset();
			en1.reset();

			// forward
			while (en0.getDistance() < 99.85 || en1.getDistance() < 99.85) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() - en0.getDistance());
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);

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
			gyro.reset();
			while (gyro.getAngle() < 90) {
				sd.tankDrive(0.3, 0.17);
			}
			sd.tankDrive(-.5, -.5);

			Timer.delay(0.04);
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
			while (gyro.getAngle() > -90) {
				sd.tankDrive(-0.15, -0.09);
			}
			sd.tankDrive(1, 1);

			Timer.delay(0.1);
			sd.tankDrive(0, 0);

			en0.reset();
			en1.reset(); // forward
			while (en0.getDistance() < 99.85 || en1.getDistance() < 99.85) {
				sd.tankDrive(0.4 + kin * kinscale, -0.4 + kin * kinscale);
				kin = (en1.getDistance() - en0.getDistance());
			}
			sd.tankDrive(-1, 1);
			Timer.delay(0.1);
			sd.tankDrive(0, 0);
		}
	}
	/**/

	boolean cupControl = false;

	@Override
	public void operatorControl() { // Main thread
		en0.reset();
		en1.reset();
		en2.reset();
		double ts1, ts2;
		// gyro.reset();
		while (isOperatorControl() && isEnabled()) {
			ts1 = (-stick0.getZ() / 2) + 0.5d;
			ts2 = (-stick1.getZ() / 2) + 0.5d;
			sd.tankDrive(-stick0.getY() * ts1, stick1.getY() * ts2);
			SmartDashboard.putNumber("gyro: ", gyro.getAngle());
			SmartDashboard.putNumber("L Enc", en0.getDistance());
			SmartDashboard.putNumber("R Enc", en1.getDistance());
			SmartDashboard.putNumber("Lift", en2.getDistance());
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
					lift.set(0.75);
				} else if (stick1.getRawButton(3)) {
					lift.set(-0.3);
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
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
		sd.tankDrive(0.5, -0.5);
		Timer.delay(2);
		sd.tankDrive(0, 0);

	}
}
