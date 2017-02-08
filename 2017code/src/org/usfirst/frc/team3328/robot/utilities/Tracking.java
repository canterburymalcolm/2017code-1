package org.usfirst.frc.team3328.robot.utilities;

import org.usfirst.frc.team3328.robot.networking.Target;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Tracking extends PIDSubsystem{
	
	Target target;
	Controller utilXbox;
	private double pixel;
	private double trackSpeed;
	private double goal;
	private int pixelDeadZone = 20;
	public boolean tracking;
	
	public Tracking(Target pixelTarget, Controller utilitiesController){
		super("tracking", 0, 0, 0);
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
		target = pixelTarget;
		utilXbox = utilitiesController;
	}
	
	public void setGoal(int target){
		goal = target;
	}
	
	//toggles tracking if the left bumper is pressed
	//disables tracking if aligned
	//returns true if tracking
	public boolean isTracking(){
		if (pixel < 350 && pixel > 290){
			tracking = false;
		}
		if (utilXbox.getButtonRelease(Buttons.LBUMP)){
			tracking = !tracking;
		}
		return tracking;
	}	
	
	//takes a pixel offset to aim the robot
	//turns until it's within a specified dead zone
	//turns at trackSpeed if outside of dead zone
	public double track(){
		pixel = target.getPixel();
		tracking = true;
		if (pixel > goal + pixelDeadZone){
			return trackSpeed;
		}else if (pixel < goal - pixelDeadZone){
			return -trackSpeed;
		}else{
			return 0;
		}
	}


	@Override
	protected double returnPIDInput() {
		return (pixel - goal);
	}


	@Override
	protected void usePIDOutput(double output) {
		trackSpeed = output;
		
	}


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
