package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.PIDTest;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class SteamWorksDriveSystem implements DriveSystem {
	
	Tracking track;
	ADIS16448_IMU imu;
	PIDTest  pid;
	private DriveTalons talons;
	private DriveEncoders encoders;
	public double restraint = 1;
	public double displacement;
	private double angleDeadZone = 0;
	private double angleSpeed = .08;
	private double gearSpeed = .1;
	boolean placingGear = false;
	
	public SteamWorksDriveSystem(DriveEncoders encoders, DriveTalons talons, 
								Tracking track, ADIS16448_IMU imu, PIDTest pid){
		this.encoders = encoders;
		this.talons = talons;
		this.track = track;
		this.track.setGoal(320);
		this.imu = imu;
		this.imu.calibrate();
		this.pid = pid;
	}
	
	@Override
	public ADIS16448_IMU getImu(){
		return imu;
	}
	
	@Override
	public Tracking getTrack(){
		return track;
	}
	
	@Override
	public void resetDistance(){
		encoders.reset();
	}
	
	@Override
	public double getDistance(){
		return encoders.getDistance();
	}
	
	@Override
	public void move(double left, double right){
		talons.right(right);
		talons.left(left);
	}
	
	public double calculateSpeed(double position){
		return (Math.sin(position - (Math.PI / 2)) + 1) * 2; 
	}
	
	@Override
	public void stop(){
		talons.stop();
	}
	
	@Override
	public void printSpeed(){
		System.out.printf("%.2f || %.2f\n",talons.getfl(), talons.getfr());
	}
	
	@Override
	public void upRestraint(){
		if (restraint < 10){
			restraint += 1;
		}
	}
	
	@Override
	public void downRestraint(){
		if (restraint > 1){
			restraint -=1;
		}
	}
	
	public void updateAngleSpeed(){
		pid.setError(displacement);
		angleSpeed = pid.getCorrection();
	}
	
	@Override
	public void autoAngle(double current, double desired){
	displacement = desired - current;
		updateAngleSpeed();
		//System.out.println(angleSpeed);
		move(-angleSpeed, angleSpeed);
		if (current > desired + angleDeadZone){
			move(-angleSpeed, angleSpeed);
		}else if(current < desired + angleDeadZone){
			move(angleSpeed, -angleSpeed);
		}else{
			stop();
		}
	}
	
	public DriveTalons getTalons(){
		return talons;
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
	
	@Override
	public void controlledMove(double xAxis, double yAxis){
		double x = calculateSpeed(xAxis);
		double y = calculateSpeed(yAxis);
		if (!track.isTracking()){
			move((x + y) / restraint, 
				(x - y) / restraint);
		}else{
			double turn = track.track();
			double move = track.getMovement();
			move(turn + move, -turn + move);
		}
	}

}