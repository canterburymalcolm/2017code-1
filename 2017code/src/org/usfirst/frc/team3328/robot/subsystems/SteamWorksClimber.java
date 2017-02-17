package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksClimber implements Climber {
	
	SpeedController winch;
	boolean active = false;
	
	public SteamWorksClimber(SpeedController winch){
		this.winch = winch;
	}
	
	private void move(double speed){
		winch.set(speed);
	}
	
	@Override
	public void controlClimber(double xAxis){
		if (xAxis > 0){
			move(xAxis);
		}else{
			move(0);
		}
	}
	
}
