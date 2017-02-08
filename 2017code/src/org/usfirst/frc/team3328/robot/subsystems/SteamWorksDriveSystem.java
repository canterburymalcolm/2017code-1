package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class SteamWorksDriveSystem implements DriveSystem {
	
	Controller driveXbox;
	Tracking track;
	private DriveTalons talons;
	private DriveEncoders encoders;
	private double restraint = 1;
	private double factor = 1.1;
	public double displacement;
	private double angleDeadZone = 20;
	private double angleSpeed = .08;
	private double speed;
	
	//instantiates talons assigns controller to "con"
	public SteamWorksDriveSystem(DriveEncoders driveEncoders, DriveTalons driveTalons
								, Controller Controller1, Tracking PID){
		encoders = driveEncoders;
		talons = driveTalons;
		driveXbox = Controller1;
		track = PID;
		track.setGoal(320);
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
	
	@Override
	public void stop(){
		talons.stop();
	}
	
	//formats and prints the value that the speed controllers are receiving.
	@Override
	public void printSpeed(){
		System.out.printf("%.2f || %.2f\n",talons.getfl(), talons.getfr());
	}
	
	
	//the speed during teleop is divided by "restraint"
	//the left and right bumpers lower and raise "restraint" respectively
	//"restraint" is confined between 10 & 1
	private void restrain(){
		if (driveXbox.getButtonRelease(Buttons.LBUMP) && restraint > 1){
			restraint -= 1;
		}
		if (driveXbox.getButtonRelease(Buttons.RBUMP) && restraint < 10){
			restraint += 1;
		}
	}
	
	//gets displacement between the desired angle and the current angle
	//normalizes displacement so it's between 0 - 1
	//rounds displacement off to 2 decimal places
	//all values above 0 and below .05 are set to .05
	@Override
	public void updateDisplacement(double desired, double current){
		displacement = (current - desired) / 360;
		if (displacement > 0 && displacement < .05){
			displacement = .05;
		}
	}
	
	//Uses the gyro to turn until it reaches a desired angle.
	//should work while moving and while stopped
	//the speed of each side is separately adjusted using the displacement
	@Override
	public void autoAngle(double current, double desired){
		displacement = Math.abs(desired - current);
		if (displacement > angleDeadZone){
			talons.right(angleSpeed * factor);
			talons.left(angleSpeed * factor);
		}
	}
	
	//updates the value of "restraint"
	//updates tracking, if tracking then it continues to track
	//sets each motor to the appropriate speed adjusted by the restraint
	@Override
	public void controlledMove(){
		if (!track.isTracking()){
			restrain();
			move((driveXbox.getX() + driveXbox.getY()) / restraint, 
				(driveXbox.getX() - driveXbox.getY()) / restraint);
		}else{
			move(track.track(), -track.track());
		}
	}

}