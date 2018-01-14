package org.usfirst.frc.team2496.robot;

import com.shwinlib.ShwinDrive;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SampleRobot {
	
	private ShwinDrive drive = new ShwinDrive(1,3,2,4);
	private Joystick js = new Joystick(2);
	private double maxSpeed = 1.0d;
	private boolean left = true;

	public Robot() {
	
	}

	@Override
	public void robotInit() {
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
	}

	
	@Override
	public void autonomous() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.startsWith("L")==left){
			//drive straight 11.32083 feet
			SmartDashboard.putString("Direction", "Forward");
		}else{
			if(left){
				//forward 3ft right 90 deg forward 9ft left 90 deg forward 8.32083
				SmartDashboard.putString("Direction", "at left to right");
			}else{
				//forward 3ft left 90 deg forward 9ft right 90 deg forward 8.32083
				SmartDashboard.putString("Direction", "from right to left");
			}
		}
	}
	
	@Override
	public void operatorControl() { //Main thread
		while (isOperatorControl() && isEnabled()) {
			drive.tankDrive(-js.getRawAxis(1)*maxSpeed, js.getRawAxis(5)*maxSpeed);
			if(js.getPOV()==0){
				maxSpeed+=0.01;
				if(maxSpeed>1.0d){
					maxSpeed=1.0d;
				}
			}else if(js.getPOV()==180){
				maxSpeed-=0.01;
				if(maxSpeed<0.0d){
					maxSpeed=0.0d;
				}
			}
			SmartDashboard.putNumber("Max Speed: ", maxSpeed);
			Timer.delay(0.005); 
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
		HRVL_MaxSonar_EZ_MB1013 sonar = new HRVL_MaxSonar_EZ_MB1013(0, 0);
		sonar.set(true);
		while(isTest()&&isEnabled()){
			System.out.println(sonar.readMM());
		}
	}
}
