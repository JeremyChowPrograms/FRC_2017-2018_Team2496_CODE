package org.usfirst.frc.team2496.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * 
 * @author Jeremy Chow
 * Custom class for the sonar sensor
 *
 */
public class HRVL_MaxSonar_EZ_MB1013 {
	private DigitalOutput dioo;
	private AnalogInput ain ;

	public HRVL_MaxSonar_EZ_MB1013(int DIOChannel,int analogChannel) {
		dioo = new DigitalOutput(DIOChannel);
		ain = new AnalogInput(analogChannel);
	}

	public void set(boolean enable) {
		dioo.set(enable);
	}

	public double readMM() {
		System.out.println(ain.getValue()+" "+((ain.getVoltage()/5.120)) );
	//System.out.println(ain.getAverageValue()-220+" "+ain.getAverageVoltage()*1000);
		return 0;

	}

}
