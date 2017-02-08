package org.usfirst.frc.team3328.robot.subsystems;

public interface DriveSystem {
	
	double getDistance();
	
	void resetDistance();
	
	//formats and prints the value that the speed controllers are receiving.
	void printSpeed();
	
	double getSpeed();
	
	void stop();
	
	//gets displacement between the desired angle and the current angle
	//normalizes displacement so it's between 0 - 1
	//rounds displacement off to 2 decimal places
	//all values above 0 and below .05 are set to .05
	void updateDisplacement(double desired, double current);

	//Uses the gyro to turn until it reaches a desired angle.
	//should work while moving and while stopped
	//the speed of each side is separately adjusted using the displacement
	void autoAngle(double current, double desired);
	
	void move(double left, double right);

	//updates the value of "restraint"
	//sets each motor to the appropriate speed adjusted by the restraint
	void controlledMove();

}