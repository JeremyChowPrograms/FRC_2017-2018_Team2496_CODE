package com.shwinlib;

class PID {
  long deltaT;
  long prevT;
  float kP;
  float kI;
  float kD;
  float integLimit;
  float integData;
  float prevError = 0, prevTime = 0;
  
  public PID(float kP, float kI, float kD, float integLimit) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    this.integLimit = integLimit;
  }
  
 


  public float doPID(float error) {
    deltaT = (long) (System.currentTimeMillis() - prevTime);
    prevTime = System.currentTimeMillis();
    float P = error * kP;
    float I = 0.0f; //you dont need this right now
    float D = (error-prevError)/deltaT * kD;
    prevError = error;
    return (float)(P + I + D); 
  }



}
