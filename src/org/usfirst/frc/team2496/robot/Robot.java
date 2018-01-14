package org.usfirst.frc.team2496.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {
Gyro gyro;
	

	public Robot() {
		
	}

	@Override
	public void robotInit() {
		gyro = new AnalogGyro(1);
	}

	
	@Override
	public void autonomous() {
		
	}
	
	@Override
	public void operatorControl() { //Main thread
		 
		while (isOperatorControl() && isEnabled()) {
			SmartDashboard.putNumber("gyro: ", gyro.getAngle());
			Timer.delay(0.005); 
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}
}
