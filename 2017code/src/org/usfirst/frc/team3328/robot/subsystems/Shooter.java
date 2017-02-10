package org.usfirst.frc.team3328.robot.subsystems;

public interface Shooter {
	
	HotelLobby getBelt();
	
	boolean isEmpty();
	
	boolean isMax();
	
	void maxSpeed();
	
	void stop();
	
	void toggleShooter();
	
	void shooterControl();

}