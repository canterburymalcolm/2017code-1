package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public interface DriveSystem {
	
	boolean placingGear();
	
	ADIS16448_IMU getImu();
	
	Tracking getTrack();
	
	void resetDistance();
	
	void printSpeed();
	
	void stop();

	void autoAngle(double current, double desired);
	
	void restrain();
	
	void move(double left, double right);
	
	void placeGear();

	void controlledMove(double xAxis, double yAxis);

}