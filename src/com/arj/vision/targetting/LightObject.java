package com.arj.vision.targetting;
import java.awt.Rectangle;
import java.util.ArrayList;

public class LightObject {

	ArrayList<Rectangle> polygon = new ArrayList<Rectangle>();
	Rectangle boundingBox = null;
	
	double arError = 0;
	double wbError = 0;
	double ar=0;
	double wb=0;

	public LightObject(ArrayList<Rectangle> polygon, Rectangle boundingBox, double arError, double wbError, double ar, double wb) {
		this.polygon = polygon;
		this.boundingBox = boundingBox;
		this.arError = arError;
		this.wbError = wbError;
		this.ar = ar;
		this.wb = wb;
		this.polygon = identifySources(this).polygon;
	}

	public LightObject() {

	}

	public LightObject identifySources(LightObject lo) {
		//issue, not merging within upper level?
		for (int i = 0; i < lo.polygon.size(); i++) {
			Rectangle r0 = lo.polygon.get(i);
			for (int n = i + 1; n < lo.polygon.size(); n++) {
				Rectangle r1 = lo.polygon.get(n);
				if (r0.intersects(r1)) {
					int x0 = Math.min(r0.x, r1.x);
					int y0 = Math.min(r0.y, r1.y);
					int w = Math.max(r0.x + r0.width, r1.x + r1.width) - x0;
					int h = Math.max(r0.y + r0.height, r1.y + r1.height) -y0;
					lo.polygon.set(i, new Rectangle(x0, y0, w, h));
					lo.polygon.remove(n);
					i=-1;
					break;
				}
			}
		}
		
		return lo;

	}
	
	public void idTarget() {
		for(Rectangle r: polygon){
			//if()
		}
	}
	// public static boolean findCoverage()

}
