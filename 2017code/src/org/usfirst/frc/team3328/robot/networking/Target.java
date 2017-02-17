package org.usfirst.frc.team3328.robot.networking;

public interface Target {

	void setPixel(double ang);
	
	double getPixel();
	
	void setDistance(double distance);

	double getDistance();

	void setStatus(boolean stat);

	boolean getStatus();

	void printValues();

}