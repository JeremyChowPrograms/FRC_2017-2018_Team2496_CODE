package com.arj.vision.targetting;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.*;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Tracker {
	private static final int CHUNK_SIZE_X = 32; // pixels
	private static final int CHUNK_SIZE_Y = 24;
	private static final Rectangle frameArea = new Rectangle(0, 0, 640, 480);
	static ArrayList<Rectangle> targets = new ArrayList<Rectangle>();
	public static boolean[] bitField = new boolean[(int) (frameArea.width/CHUNK_SIZE_X * frameArea.height/CHUNK_SIZE_Y)];
	static boolean targetAcquired = false;
	static int chunkLoc = 0;
	
	
	public static void findTargets(Mat curFrm) {
		chunkLoc = 0;
		targets = new ArrayList<Rectangle>();
		Mat frm = curFrm;

		for (int y = 0; y < frm.rows() ; y += CHUNK_SIZE_Y) {
			for (int x = 0; x < frm.cols(); x += CHUNK_SIZE_X) {

				targetAcquired = false;

				Rectangle tgt;
				int minX=Integer.MAX_VALUE;
				int minY=Integer.MAX_VALUE;
				int maxX=Integer.MIN_VALUE;
				int maxY=Integer.MIN_VALUE;
				//
				for (int cy = y; cy < (y+CHUNK_SIZE_Y); cy++) {
					for (int cx = x; cx < (x+CHUNK_SIZE_X); cx++) {
						
					
						byte[] data = new byte[3];
						curFrm.get(cy, cx, data);
						Vec3b pos = new Vec3b(data);
						
						if ( pos.getG()  >= 200 && pos.getB() <= 200 && pos.getR() <= 200) {

							curFrm.put(cy, cx, new byte[] {(byte) 255, (byte) 255, (byte) 255}); //what dumb design
							
						}
						else
							curFrm.put(cy, cx, new byte[] { 0, 0, 0 });
						
						
						byte[] b = new byte[3];
						//curFrm.get(cx, cy, b);
						curFrm.get(cy, cx, b);
						if(b[0] != 0) {//light
							
							targetAcquired = true;
							if(cx < minX) minX = cx;
							if(cx > maxX) maxX = cx;
							if(cy < minY) minY = cy;
							if(cy > maxY) maxY = cy;
						}
						else continue;

					}
				}
				if(targetAcquired == true) { 
					tgt = new Rectangle(minX, minY, maxX-minX+3, maxY-minY+3);
					targets.add(tgt);
					bitField[chunkLoc] = targetAcquired;
				} else bitField[chunkLoc] = !targetAcquired;
				chunkLoc++;
				Imgproc.line(frm, new Point(x,y), new Point(x+CHUNK_SIZE_X, y), new Scalar(0, 0, 255));
				Imgproc.line(frm, new Point(x,y), new Point(x, y+CHUNK_SIZE_Y), new Scalar(0, 0, 255));
			}
		}
		
		for(Rectangle r : targets) {
			Imgproc.rectangle(frm, new Point(r.x, r.y), new Point( r.x+r.width, r.y+r.height), new Scalar(255, 0,0), 2);		
		}
		try {
		LightObject lo = new LightObject(targets, null, 0d, 0d, 0d, 0d);
		int q = 0;
		
		for(int i = 0; i < lo.polygon.size(); i++) {
			if(i < q);
			Rectangle r = lo.polygon.get(i);
			Imgproc.rectangle(frm, new Point(r.x, r.y), new Point( r.x+r.width, r.y+r.height), new Scalar(255, 255,0), 2);
		}
		} catch(Exception e) {
			
		} 
	}
	
	
	
}
