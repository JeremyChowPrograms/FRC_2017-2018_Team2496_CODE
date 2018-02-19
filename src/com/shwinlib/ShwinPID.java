package com.shwinlib;

public class ShwinPID {
  long deltaT;
  long prevT;
  double kP;
  double kI;
  double kD;
  double integLimit;
  double integData;
  double prevError = 0, prevTime = 0;
  
  public ShwinPID(double kP, double kI, double kD, double integLimit) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
    this.integLimit = integLimit;
  }
  
 


  public double doPID(double error) {
    deltaT = (System.nanoTime() - prevT);
    System.out.print(deltaT +" ");
    prevT = System.nanoTime();
    
    double P = error * kP;
    System.out.print(P+" ");
    double I = 0.0f; //you dont need this right now
    double D = (error-prevError)/deltaT * kD;
    prevError = error;
    return (P + I + D); 
  }



}
