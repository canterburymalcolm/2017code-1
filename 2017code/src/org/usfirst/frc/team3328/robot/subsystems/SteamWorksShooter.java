package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.ShooterTalons;

import edu.wpi.first.wpilibj.Encoder;

public class SteamWorksShooter implements Shooter {

	HotelLobby belt;
	Encoder encoder;
	ShooterTalons talons;
	private double speed = 0;
	boolean active = false;
	
	public SteamWorksShooter(Encoder encoder, ShooterTalons talons, HotelLobby belt){
		this.encoder = encoder;
		this.talons = talons;
		this.belt = belt;
	}
	
	@Override
	public HotelLobby getBelt(){
		return belt;
	}
	
	@Override
	public boolean isEmpty(){
		//returns true if hotel is empty
		return true;
	}
	
	@Override
	public boolean isMax(){
		return encoder.get() >= 1000;
	}
	
	// Gradually builds up speed to max
	@Override
	public void maxSpeed(){
		if (speed < .75){
			speed += 0.01;
		}
		talons.set(speed);
	}
	
	// Sets talons and speed to 0
	@Override
	public void stop(){
		if (speed > 0){
			speed -= .01;
		}
		talons.set(speed);
	}
	
	@Override
	public void toggleShooter(){
		active = !active;
	}
	
	// Method to check whether the button
	// is pressed and sets the talons to
	// max speed whether or not.
	@Override
	public void shooterControl(){	
		if (active){
			maxSpeed();
			belt.runBelt();
		}else{
			stop();
			belt.stopBelt();
		}
	
	}
	
	
	
}
