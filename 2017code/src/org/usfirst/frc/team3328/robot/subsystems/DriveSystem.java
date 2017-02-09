package org.usfirst.frc.team3328.robot.subsystems;

public interface DriveSystem {
	
	boolean placingGear();
	
	void resetDistance();
	
	//formats and prints the value that the speed controllers are receiving.
	void printSpeed();
	
	void stop();

	//Uses the gyro to turn until it reaches a desired angle.
	//should work while moving and while stopped
	//the speed of each side is separately adjusted using the displacement
	void autoAngle(double current, double desired);
	
	void restrain();
	
	void move(double left, double right);
	
	void placeGear();
	
	//updates the value of "restraint"
	//sets each motor to the appropriate speed adjusted by the restraint
	void controlledMove();

}