package org.usfirst.frc.team3328.robot.networking;

import edu.wpi.first.wpilibj.SpeedController;

public class NetworkTablesTargetProvider implements TargetProvider{
	
	Target target = new Target();
	
	
	//Starts "listenerThread" upon instantiation
	Listener listener = new Listener("listenerThread", target);
	
	@Override
	public Target getTarget() {
		return target;
	}

	
	
}
