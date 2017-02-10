package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksHotelLobby implements HotelLobby {
	
	SpeedController belt;
	
	public SteamWorksHotelLobby(SpeedController belt) {
		this.belt = belt;
	}
	
	@Override
	public void runBelt(){
		belt.set(1);
	}
	
	@Override
	public void stopBelt(){
		belt.set(0);
	}

}
