package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksHotelLobby implements HotelLobby {
	
	SpeedController belt;
	double beltSpeed = -.5;
	
	public SteamWorksHotelLobby(SpeedController belt) {
		this.belt = belt;
	}
	
	@Override
	public void toggle(){
		belt.set((belt.get() == 0) ? beltSpeed : 0);
	}
	
	@Override
	public void runBelt(){
		belt.set(beltSpeed);
	}
	
	@Override
	public void stopBelt(){
		belt.set(0);
	}

}
