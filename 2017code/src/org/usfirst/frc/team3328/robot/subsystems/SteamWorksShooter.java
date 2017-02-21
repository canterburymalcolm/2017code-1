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
		return false;
	}
	
	@Override
	public boolean isMax(){
		return encoder.get() >= 1000;
	}
	
	@Override
	public void maxSpeed(){
		if (speed < .65){
			speed += 0.01;
		}
		talons.set(speed);
	}
	
	@Override
	public void stop(){
		if (speed > 0){
			speed -= .01;
		}
		talons.set(speed);
	}
	
	@Override
	public void toggleBelt(){
		belt.toggle();
	}
	
	@Override
	public void toggleShooter(){
		active = !active;
		belt.toggle();
	}
	
	@Override
	public void shooterControl(){	
		if (active){
			maxSpeed();
			belt.runBelt();
		}else{
			stop();
		}
	
	}
	
}
