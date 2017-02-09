package org.usfirst.frc.team3328.robot.networking;

public interface Target {

	double getPixel();

	void setPixel(double ang);

	void setStatus(boolean stat);

	boolean getStatus();

	void setTime(long stamp);

	boolean isNew();

	void printValues();

}