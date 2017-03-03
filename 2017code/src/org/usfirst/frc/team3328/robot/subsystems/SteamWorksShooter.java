package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.ShooterTalons;

import edu.wpi.first.wpilibj.Encoder;

public class SteamWorksShooter implements Shooter {

	HotelLobby belt;
	Agitator agitator;
	Encoder encoder;
	ShooterTalons talons;
	private double speed = 0;
	boolean active = false;
	
	public SteamWorksShooter(Encoder encoder, ShooterTalons talons, HotelLobby belt, Agitator agitator){
		this.encoder = encoder;
		this.talons = talons;
		this.belt = belt;
		this.agitator = agitator;
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
	public boolean isShooting(){
		return encoder.get() >= 1000;
	}
	
	@Override
	public void startShoot(){
		if (speed < .65){
			speed += 0.01;
		}
		talons.set(speed);
	}
	
	@Override
	public void stopShoot(){
		if (speed > 0){
			speed -= .01;
		}
		talons.set(speed);
	}
	
	@Override
	public void shooterControl(){	
		if (active){
			startShoot();
			belt.run();
		}else{
			stopShoot();
		}
	
	}

	@Override
	public void startLoad() {
		belt.run();
		agitator.run();
	}

	@Override
	public void stopLoad() {
		belt.stop();
		agitator.stop();
	}

	@Override
	public boolean isLoading() {
		return belt.isRunning();
	}

	
}
