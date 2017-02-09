package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class SteamWorksDriveSystem extends PIDSubsystem implements DriveSystem {
	
	Controller driveXbox;
	Tracking track;
	private DriveTalons talons;
	private DriveEncoders encoders;
	public double restraint = 1;
	public double displacement;
	private double angleDeadZone = 0;
	private double angleSpeed = .08;
	private double gearSpeed = .1;
	boolean placingGear = false;
	
	//instantiates talons assigns controller to "con"
	public SteamWorksDriveSystem(DriveEncoders driveEncoders, DriveTalons driveTalons
								, Controller Controller1, Tracking PID){
		super("anglePID", 0, 0, 0);
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
		encoders = driveEncoders;
		talons = driveTalons;
		driveXbox = Controller1;
		track = PID;
		track.setGoal(320);
	}
	
	public void resetDistance(){
		encoders.reset();
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
	@Override
	public void restrain(){
		if (driveXbox.getButtonRelease(Buttons.LBUMP) && restraint > 1){
			restraint -= 1;
		}
		if (driveXbox.getButtonRelease(Buttons.RBUMP) && restraint < 10){
			restraint += 1;
		}
	}
	
	@Override
	protected double returnPIDInput() {
		return displacement;
	}

	@Override
	protected void usePIDOutput(double output) {
		angleSpeed = output;
	}
	
	//uses PID to determine the speed it turns
	@Override
	public void autoAngle(double current, double desired){
		displacement = desired - current;
		if (current > desired + angleDeadZone){
			move(-angleSpeed, angleSpeed);
		}else if(current < desired + angleDeadZone){
			move(angleSpeed, -angleSpeed);
		}else{
			stop();
		}
	}
	
	@Override
	public void placeGear(){
		placingGear = true;
		if(encoders.getDistance() > 1){
			move(gearSpeed, gearSpeed);
		}else{
			placingGear = false;
			stop();
		}
	}
	
	@Override
	public boolean placingGear(){
		return placingGear;
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


	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

}