package org.usfirst.frc.team3328.robot.utilities;

import org.usfirst.frc.team3328.robot.networking.Target;

public class Tracking {
	
	Target target;
	Controller utilXbox;
	PID pid;
	private double pixel;
	private double trackSpeed;
	private double goal;
	private int deadZone = 10;
	private boolean tracking;
	
	public Tracking(Target target, PID pid){
		this.target = target;
		this.pid = pid;
	}
	
	public void setGoal(int target){
		goal = target;
	}
	
	public double getGoal(){
		return goal;
	}
	
	public boolean getTracking(){
		return tracking;
	}
	
	public void toggleTracking(){
		tracking = !tracking;
	}
	
	public boolean isTracking(){
		if (pixel < goal + deadZone && pixel > goal - deadZone){
			tracking = false;
		}
		return tracking;
	}	
	
	public void updateTrackSpeed(){
		pid.setError(pixel - goal);
		trackSpeed = pid.getCorrection();
	}
	
	public double track(){
		pixel = target.getPixel();
		tracking = true;
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
