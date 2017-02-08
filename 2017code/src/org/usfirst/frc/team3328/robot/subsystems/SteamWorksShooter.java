package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.ShooterTalons;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;

import edu.wpi.first.wpilibj.Encoder;

public class SteamWorksShooter implements Shooter {

	
	Encoder coder;
	ShooterTalons talons;
	Controller con;
	private double speed = 0;
	boolean active = false;
	
	public SteamWorksShooter(Encoder encoder, ShooterTalons talonController, Controller controller){
		coder = encoder;
		talons = talonController;
		con = controller;
	}
	
	@Override
	public boolean isEmpty(){
		//returns true if hotel is empty
		return true;
	}
	
	@Override
	public boolean isMax(){
		return coder.get() >= 1000;
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
	
	
	// Method to check whether the button
	// is pressed and sets the talons to
	// max speed whether or not.
	@Override
	public void shooterControl(){
		if (con.getButtonRelease(Buttons.RBUMP)){	
			active = !active;
			System.out.println(active);
		}
		if (active){
			maxSpeed();
		}else{
			stop();
		}
	
	}
	
	
	
}
