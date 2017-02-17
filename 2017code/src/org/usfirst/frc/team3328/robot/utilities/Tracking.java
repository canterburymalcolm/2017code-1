package org.usfirst.frc.team3328.robot.utilities;

import org.usfirst.frc.team3328.robot.networking.Target;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class Tracking {
	
	Relay spike;
	Target target;
	Controller utilXbox;
	PIDTest pid;
	private double pixel;
	private double trackSpeed;
	private double goal = 320;
	private int deadZone = 5;
	private boolean tracking = false;
	
	public Tracking(Target target, PIDTest pid){
		this.target = target;
		this.pid = pid;
		spike = new Relay(0);
	}
	
	public void setGoal(int target){
		goal = target;
	}
	
	public double getGoal(){
		return goal;
	}
	
	public boolean toggleTracking(){
		if (!tracking){
			tracking = true;
		}else{
			tracking = false; 
		}
		return tracking;
	}
	
	public void updateTracking(){
//		if(!target.getStatus()){
//			tracking = false;
//		}
		if (Math.abs(pixel - goal) < deadZone){
			tracking = false;
		}
	}
	
	public boolean isTracking(){
		if (tracking){
			spike.set(Value.kForward);
		}else{
			spike.set(Value.kOff);
		}
		pixel = target.getPixel();
		System.out.println("Status: " + target.getStatus() + "| Pixel: " + pixel + "| Track: " + tracking);
		return tracking;
	}	
	
	public void updateTrackSpeed(){
		pid.setError(pixel - goal);
		trackSpeed = pid.getCorrection();
	}
	
	public double track(){
		pixel = target.getPixel();
		updateTracking();
		updateTrackSpeed();
		if (pixel > goal + deadZone){
			return trackSpeed;
		}else if (pixel < goal - deadZone){
			return -trackSpeed;
		}else{
			return 0;
		}
	}
	
}
