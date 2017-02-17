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
	private double distance;
	private double trackSpeed;
	private double movement = 0;
	private double pixelGoal = 320;
	private double distanceGoal = 200;
	private int deadZone = 5;
	private boolean tracking = false;
	
	public Tracking(Target target, PIDTest pid){
		this.target = target;
		this.pid = pid;
		spike = new Relay(0);
	}
	
	public void setGoal(int target){
		pixelGoal = target;
	}
	
	public double getGoal(){
		return pixelGoal;
	}
	
	public boolean toggleTracking(){
		tracking = !tracking;
		return tracking;
	}
	
	public double getMovement(){
		return movement;
	}
	
	public void updateTracking(){
		pixel = target.getPixel();
		distance = target.getDistance();
//		if(!target.getStatus()){
//			tracking = false;
//		}
		if (Math.abs(pixel - pixelGoal) < deadZone){
			movement = -.2;
		}
		if (distance < distanceGoal){
			movement = 0;
			if (Math.abs(pixel - pixelGoal) < deadZone){
				tracking = false;
			}
		}
	}
	
	public boolean isTracking(){
		if (tracking){
			spike.set(Value.kForward);
		}else{
			spike.set(Value.kOff);
		}
		pixel = target.getPixel();
		//System.out.println("Status: " + target.getStatus() + "| Pixel: " + pixel + "| Distance: " + target.getDistance() + " | Track: " + tracking);
		return tracking;
	}	
	
	public void updateTrackSpeed(){
		pid.setError(pixel - pixelGoal);
		trackSpeed = pid.getCorrection();
	}
	
	public double track(){
		updateTracking();
		updateTrackSpeed();
		if (pixel > pixelGoal + deadZone){
			return trackSpeed;
		}else if (pixel < pixelGoal - deadZone){
			return -trackSpeed;
		}else{
			return 0;
		}
	}
	
}
