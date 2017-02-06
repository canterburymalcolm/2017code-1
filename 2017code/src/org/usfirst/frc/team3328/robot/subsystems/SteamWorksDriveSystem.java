package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.networking.Target;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;

import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksDriveSystem implements DriveSystem {
	
	Controller driveXbox;
	Controller utilXbox;
	private DriveTalons talons;
	private DriveEncoders encoders;
	private double restraint = 1;
	private double factor = 1.1;
	private double displacement;
	private double speed;
	private double pixel;
	private double trackSpeed = 0.08;
	private boolean tracking = false;
	Target target;
	
	//instantiates talons assigns controller to "con"
	public SteamWorksDriveSystem(DriveEncoders driveEncoders, DriveTalons driveTalons,
								 Controller Controller1, Controller Controller2, Target targetPixelValue){
		encoders = driveEncoders;
		talons = driveTalons;
		driveXbox = Controller1;
		utilXbox = Controller2;
		target = targetPixelValue;
		
	}
	
	@Override
	public double getDistance(){
		return (encoders.frDistance() + encoders.blDistance()) / 2;
	}
	
	public void resetDistance(){
		encoders.reset();
	}
	
	@Override
	public double getSpeed(){
		speed = ((driveXbox.getX() + driveXbox.getY()) / restraint) * 10;
		speed = (speed * speed) / 100;
		return speed;
	}
	
	@Override
	//moves the robot
	public void move(double left, double right){
		talons.right(right);
		talons.left(left);
	}
	
	//formats and prints the value that the speed controllers are receiving.
	@Override
	public void printSpeed(){
		System.out.printf("%.2f || %.2f\n",talons.getfl(), talons.getfr());
	}
	
	
	//Dynamic updating for strength of angle correction during auto
	private void updateFactor(){
		if (driveXbox.getButtonRelease(1) && factor > 1){
			factor -= .1;
		}
		if (driveXbox.getButtonRelease(2) && factor < 10){
			factor += .1;
		}
	}
	
	//the speed during teleop is divided by "restraint"
	//the left and right bumpers lower and raise "restraint" respectively
	//"restraint" is confined between 10 & 1
	private void restrain(){
		if (driveXbox.getButtonRelease(5) && restraint > 1){
			restraint -= 1;
		}
		if (driveXbox.getButtonRelease(6) && restraint < 10){
			restraint += 1;
		}
	}
	
	//gets displacement between the desired angle and the current angle
	//normalizes displacement so it's between 0 - 1
	//rounds displacement off to 2 decimal places
	//all values above 0 and below .05 are set to .05
	@Override
	public double updateDisplacement(double desired, double current){
		displacement = (current - desired) / 360;
		if (displacement > 0 && displacement < .05){
			displacement = .05;
		}
		return displacement;
	}
	
	//Uses the gyro to turn until it reaches a desired angle.
	//should work while moving and while stopped
	//the speed of each side is separately adjusted using the displacement
	@Override
	public void autoAngle(double speed, double current, double desired){
		updateFactor();
		System.out.println(factor);
		updateDisplacement(desired, current);
		talons.right((speed + displacement) * factor);
		talons.left((speed - displacement) * factor);
	}
	
	//toggles tracking if the left bumper is pressed
	//disables tracking if aligned
	//returns true if tracking
	@Override
	public boolean isTracking(){
		if (pixel < 350 && pixel > 290){
			tracking = false;
		}
		if (utilXbox.getButtonRelease(5)){
			tracking = !tracking;
		}
		return tracking;
	}	
	
	//takes a pixel offset to aim the robot
	//turns until it's within a specified dead zone
	//turns at trackSpeed if outside of dead zone
	@Override
	public void track(){
		if (pixel > 350){
			move(trackSpeed, -trackSpeed);
		}else if (pixel < 290){
			move(-trackSpeed, trackSpeed);
		}else{
			talons.stop();
		}
	}
	
	//updates the value of "restraint"
	//updates tracking, if tracking then it continues to track
	//sets each motor to the appropriate speed adjusted by the restraint
	@Override
	public void controlledMove(){
		if (!isTracking()){
			pixel = target.getPixel();
			restrain();
			move((driveXbox.getX() + driveXbox.getY()) / restraint, 
				(driveXbox.getX() - driveXbox.getY()) / restraint);
		}else{
			track();
		}
	}

}