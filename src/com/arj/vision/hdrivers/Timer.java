package com.arj.vision.hdrivers;

public class Timer implements Runnable{
	public Timer() {
	
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Thread.sleep(1000);
				System.out.println(CameraHandler.fps);
				CameraHandler.fps =0;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
