package org.usfirst.frc.team3328.robot.utilities;

import org.usfirst.frc.team3328.robot.networking.Target;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class Tracking {
	
	Relay spike;
	Target target;
	Controller utilXbox;
	PID pidAngle;
	PID pidDistance;
	private double pixel;
	private double distance;
	private double move = 0;
	private double pixelGoal = 320;
	private double distanceGoal = 100;
	private double pixelDeadZone = 2;
	private double distanceDeadZone = 5;
	private boolean tracking = false;
	
	public Tracking(Target target, Relay spike, PID pidAngle, PID pidDistance){
		this.target = target;
		this.pidAngle = pidAngle;
		this.pidDistance = pidDistance;
		this.spike = spike;
	}
	
	public void setGoal(int target){
		pixelGoal = target;
	}
		
	public void toggleTracking(){
		tracking = !tracking;
		if (tracking){
			spike.set(Value.kForward);
		}else{
			spike.set(Value.kOff);
		}
	}
	
	public void stopTracking(){
		tracking = false;
		spike.set(Value.kOff);
	}
	
	public void updateTracking(){
		pixel = target.getPixel();
		distance = target.getDistance();
		double pixelDisplacement = Math.abs(pixel - pixelGoal);
		double moveDisplacement = Math.abs(distance - distanceGoal);
		if (target.foundTarget()){
			if (pixelDisplacement < pixelDeadZone){
				move = updateMove(distance - distanceGoal);
				if (moveDisplacement < distanceDeadZone){
					move = 0;
					stopTracking();
				}
			}else{
				move = 0;
			}
		}else{
			stopTracking();
		}
	}
	
	public boolean getTracking(){
		updateTracking();
		System.out.printf("Status: %b |Tracking: %b |Pixel %f |Distance: %.2f\n", 
				target.foundTarget(), tracking, pixel, target.getDistance());
		return tracking;
	}	

	public double updateMove(double error) {
		pidDistance.setError(error);
		return pidDistance.getCorrection();
	}
	
	public double getMove(){
		return move; 
	}
	
	public double updateTurn(double error){
		pidAngle.setError(error);
		return pidAngle.getCorrection();
	}
	
	public double getTurn(){
		return updateTurn(pixel - pixelGoal);
	}
	
}
