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
		return encoder.get() > 0;
	}
	
	@Override
	public void startShoot(){
		talons.set(speed);
		startLoad();
	}
	
	@Override
	public void stopShoot(){
		talons.set(speed);
		stopLoad();
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

	@Override
	public boolean isMax() {
		return encoder.get() > 0;
	}

	
}
