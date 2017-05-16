package com.arj.vision.targetting;

public class Vec3b {
	private int[] v = new int[3];
	
	public Vec3b(int r, int g, int b) {
		v[0] = b;
		v[1] = g;
		v[2] = r;
	}
	
	public Vec3b() {
		// TODO Auto-generated constructor stub
	}

	public Vec3b(int[] data) {
		this.v = data;
	}

	//right, so FUBAR ubyte imp.
	public Vec3b(byte[] data) {
		v[0] = data[0];
		v[0] &= 0xFF;
		v[1] = data[1];
		v[1] &= 0xFF;
		v[2] = data[2];
		v[2] &= 0xFF;
	}
	
	public int getR() {
		return v[2];
	}
	
	public int getG() {
		return v[1];
	}
	
	public int getB() {
		return v[0];
	}
	
	public int get(int c) {
		return v[c];
	}
}
