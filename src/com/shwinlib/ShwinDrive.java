package com.shwinlib;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.*;

public class ShwinDrive {
	SpeedController driveMotors[] = new SpeedController[4]; //array of motors
	boolean flag = false; //flag indicating 4 wheel drive
	double prevTurn = 0;
	double prevStraight;
	double prevEnc0;
	double prevEnc1;
	private final double cp = 0.0004; //0.0001
	
	
	public ShwinDrive(int bl, int br, int fl, int fr) {
		this.driveMotors[0] = new TalonSRX(bl);
		this.driveMotors[1] = new TalonSRX(br);
		this.driveMotors[2] = new TalonSRX(fl);
		this.driveMotors[3] = new TalonSRX(fr);
		flag = true;
		
	}
	public ShwinDrive(int l, int r) {
		this.driveMotors[0] = new TalonSRX(l);
		this.driveMotors[1] = new TalonSRX(r);
	}
	
	public void tankDrive(double l, double r) {
		this.driveMotors[0].set(l);
		this.driveMotors[1].set(r);
		this.driveMotors[2].set(l);
		this.driveMotors[3].set(r);
	}
	
	public void arcadeDrive(double forward, double turn, Encoder rEnc, Encoder lEnc, boolean fack) { //mother facker
		turn = 0;//TODO remove
		
		double error = 0;
		boolean straight = false;
		double l;
		double r;
		double fd=1;
		double td=1;
		if(forward <= 0.0) fd = -1;
		if(turn <= 0.0) td = -1;
		forward*=forward * fd ;
		turn*=turn * td;
		l = forward + turn;
		r = forward - turn;
		
		double lEncoder=(double)lEnc.getRaw();
		double rEncoder= (double)rEnc.getRaw();

		
		if(Math.abs(prevTurn) >= 0.2 && Math.abs(turn) <= 0.2) {
			rEnc.reset();
			lEnc.reset();
		}
		
		prevTurn = turn;
		
		if(Math.abs(turn) <= 0.2 && Math.abs(forward) >= 0.1) { //if going straight
			
			error = (lEncoder - rEncoder) *cp;
			r+= error;
		}
		double m = Math.max(l, r);
		if(Math.abs(m)>=1.0) {
			double s = 1.0/m;
			l*= s;
			r*= s;
		}
		
		r*= -1;	
		if(flag == true) {
			driveMotors[0].set(l);
			driveMotors[1].set(r);
			driveMotors[2].set(l);
			driveMotors[3].set(r);
		} else {
			driveMotors[0].set(l);
			driveMotors[1].set(r);
		}
		
	}
	
	public void arcadeDrive(double forward, double turn) { //bound (0.0)+-(1.0)
		double l;
		double r;
		double fd=1;
		double td=1;
		if(forward <= 0.0) fd = -1;
		if(turn <= 0.0) td = -1;
		forward*=forward * fd ;
		turn*=turn * td;
		l = forward + turn;
		r = forward - turn;
		
		double m = Math.max(l, r);
		if(Math.abs(m)>=1.0) {
			double s = 1.0/m;
			l*= s;
			r*= s;
		}
		
		r*= -1;
		
		if(flag == true) {
			driveMotors[0].set(l);
			driveMotors[1].set(r);
			driveMotors[2].set(l);
			driveMotors[3].set(r);
		} else {
			driveMotors[0].set(l);
			driveMotors[1].set(r);
		}
		
	}
	
	@Deprecated
	public void arcadeDrive(double forward) { //bound (0.0)+-(1.0)
		double l = forward;
		double r = forward;
		
			driveMotors[0].set(l);
			driveMotors[1].set(r);
		
		
	}
	
}
