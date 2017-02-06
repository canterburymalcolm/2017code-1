package org.usfirst.frc.team3328.robot.subsystems;

public interface Shooter {
	
	boolean isEmpty();
	
	boolean isMax();
	
	void maxSpeed();
	
	void stop();
	
	void shooterControl();

}