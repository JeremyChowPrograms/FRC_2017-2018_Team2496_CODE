package com.shwinlib;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public abstract class MotorSubsystem {
	public int id; //unique id for each subsystem, currently unused
	protected SpeedController motors[]; //array with ALL motors
	protected int revField[]; //byte field with either 1 or -1 giving motor dir for reversing
	protected Joystick js;
	
	public MotorSubsystem(int id, SpeedController motors[], int revField[]) { //default constructor
		this.id = id;
		this.motors = motors;
		this.revField = revField;
		if(revField.length != motors.length) { //Ensure revField matches with motor length
			this.revField = new int[motors.length];  
			for(int i = 0; i < motors.length; i++) { //Default initalization for the field
				this.revField[i] = 1;
			}
		}
	}
	
	public void setJoystick(Joystick js) {
		this.js = js;
	}
	
	/**
	 * Set all speed controllers to specified speed bounded -1.0 to 1.0
	 */
	public void setAll(double value) { 
		int i = 0; 
		for(SpeedController sc : motors) {
			sc.set(value * revField[i]);
			i++;
		}
	}
	
	/**
	 * Set individual speed controllers to specified speed bounded -1.0 to 1.0
	 */
	public void set(int index, double value) { 
		motors[index].set(value * revField[index]);
	}
	
	
	/**
	 * Immediate control cut off and deinit for absolute emergency
	 */
	public void eStop() { //
		for(SpeedController sc : motors) {
			sc.set(0.0);
			sc = null;
			continue;
		}
	}
	
	/**
	 * Log any output data to console
	 */
	public abstract void consoleLog(); 
	  
	
	/**
	 * Add to or update SmartDashboard log
	 */
	public abstract void dashboardLog(); //
	
	/**
	 * Update joystick control
	 */
	public abstract void control(); 
	
	
	
}
