package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksHotelLobby implements HotelLobby {
	
	Shooter shooter;
	SpeedController belt;
	
	public SteamWorksHotelLobby(SpeedController talonController, Shooter sh) {
		shooter = sh;
		belt = talonController;
	}
	
	@Override
	public void controlBelt() {
		if (shooter.isMax()){
			belt.set(1);
		}else{
			belt.set(0);
		}
	}

}
