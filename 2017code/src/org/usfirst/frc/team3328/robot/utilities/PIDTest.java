package org.usfirst.frc.team3328.robot.utilities;

public class PIDTest {
	
	double pOut;
	double iOut;
	double dOut;
	double KP;
	double KI;
	double KD;
	double error;
	double deltaError;
	double prevError = error;
	double integralError;
	double correction;
	double timeChange;
	long lastTime;
	
	public PIDTest(double P, double I, double D){
		KP = P;
		KI = I;
		KD = D;
	}
	
	public void adjustP(double newP){
		KP += newP;
	}
	
	public void adjustI(double newI){
		KI += newI;
	}
	
	public void adjustD(double newD){
		KD += newD;
	}
	
	public double getP(){
		return KP;
	}
	
	public double getI(){
		return KI;
	}
	
	public double getD(){
		return KD;
	}
	
	public void setError(double error){
		this.error = error;
		this.error /= 360;
	}
	
	public double getCorrection(){
		long now = System.nanoTime();
		timeChange = (double)(now - lastTime);
		
		deltaError = (prevError - error);
		
		integralError += (error / 100);
		
		pOut = error * KP;
		iOut = integralError * KI;
		dOut = deltaError * KD;
		
		correction = pOut + iOut + dOut;
//		if (correction > 500){
//			correction = 500;
//		}
//		if (correction < -250){
//			correction = -250;
//		}
		
		return correction;
	}
}
 