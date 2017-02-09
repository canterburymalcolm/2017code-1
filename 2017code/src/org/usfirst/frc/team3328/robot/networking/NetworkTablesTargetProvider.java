package org.usfirst.frc.team3328.robot.networking;

public class NetworkTablesTargetProvider implements TargetProvider{
	
	Target target = new SteamWorksTarget();
	
	
	//Starts "listenerThread" upon instantiation
	Listener listener = new Listener("listenerThread", target);
	
	@Override
	public Target getTarget() {
		return target;
	}

	
	
}
