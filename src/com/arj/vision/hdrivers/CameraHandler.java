package com.arj.vision.hdrivers;
// IMPORTS
import org.opencv.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import com.arj.vision.display.VideoFrame;
import com.arj.vision.targetting.Tracker;

public class CameraHandler {
	private static VideoCapture cam; //Camera object
	public static Mat curFrmRaw;
	public static Mat curFrm;
	static VideoFrame vf = new VideoFrame("main");
	static VideoFrame mvf = new VideoFrame("secondary");
	public static int fps=0;


	private static void updateFrame(int delay) { 
		cam.read(curFrmRaw);
		curFrm = curFrmRaw.clone();
		Tracker.findTargets(curFrm);
		
		
		fps++;
		try {
			Thread.sleep(delay);
		} catch(Exception e) {
			
		}
	}

	static void load() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.load("J://Program Files//OpenCV//opencv//build//java//x64//opencv_java320.dll"); 
		
		//Init crap
		cam = new VideoCapture(0);
		curFrmRaw = new Mat();
		curFrm = new Mat();
		cam.set(15, 0.0); //expos
		cam.set(14, 0.0); //gain
		cam.set(10, 127); //bright
		cam.set(11, 32);  //contrast
		cam.set(12, 255);
		
		cam.set(17, 255); 
		
		cam.set(18, 255); 
		System.out.println(cam.get(11));
		//cam.set(10, );
		//Give the camera capture object a second to load
		try {
			Thread.sleep(1000);
			new Thread(new Timer()).start(); //f*ck thread safety.
		} catch (Exception e) {

		}
		
	}
	
	

}
