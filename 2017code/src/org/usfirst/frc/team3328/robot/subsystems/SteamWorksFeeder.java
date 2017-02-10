package org.usfirst.frc.team3328.robot.subsystems;


import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksFeeder implements Feeder{ 

	SpeedController feeder;
	boolean active = false;

	public SteamWorksFeeder(SpeedController feeder){ 
		this.feeder = feeder;
	}

	//Toggles feeder on and off when A and B is pressed
	@Override
	public void controlFeeder() {
		if (active) {
			feeder.set(0);
		}else{
			feeder.set(1);
		}
		active = !active;
	}
	
	
	
}
