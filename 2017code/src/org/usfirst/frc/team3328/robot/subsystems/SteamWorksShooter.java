package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.Controller;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksShooter implements Shooter {

	Encoder coder;
	SpeedController t1;
	SpeedController t2;
	Controller con;
	private double speed = 0;
	boolean active = false;
	
	public SteamWorksShooter(Encoder encoder, SpeedController talonController1,
							 SpeedController talonController2, Controller controller){
		coder = encoder;
		t1 = talonController1;
		t2 = talonController2;
		con = controller;
	}
	
	@Override
	public boolean isMax(){
		return coder.get() >= 1000;
	}
	
	// Gradually builds up speed to max 
	private void maxSpeed(){
		if (speed < 1){
			speed += 0.02;
		}
		t1.set(speed);
		t2.set(speed);
	}
	
	// Sets talons and speed to 0
	private void stop(){
		speed = 0;
		t1.set(0);
		t2.set(0);
	}

	private void isTrack(){
		
	}
	
	// Method to check whether the button
	// is pressed and sets the talons to
	// max speed whether or not.
	@Override
	public void shooterControl(){
		if (con.getButtonRelease(6)){	
			active = !active;
		}
		if (active){
			maxSpeed();
		}else{
			stop();
		}
	
	}
	
	
	
}
