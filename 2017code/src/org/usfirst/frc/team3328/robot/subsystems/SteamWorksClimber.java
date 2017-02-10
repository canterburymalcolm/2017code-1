package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksClimber implements Climber {
	
	SpeedController winch;
	boolean active = false;
	double factor = 5;
	
	public SteamWorksClimber(SpeedController winch){
		this.winch = winch;
	}
	
	private void move(double speed){
		winch.set(speed / factor);
	}
	
	@Override
	public void controlClimber(double xAxis){
		if (xAxis > 0){
			move(xAxis);
		} 
	}
	
}
