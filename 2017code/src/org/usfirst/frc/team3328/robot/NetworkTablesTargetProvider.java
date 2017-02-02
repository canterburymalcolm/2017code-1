package org.usfirst.frc.team3328.robot;

public class NetworkTablesTargetProvider implements TargetProvider{
	
	Target target = new Target();
	
	//Starts "listenerThread" upon instantiation
	Listener listener = new Listener("listenerThread", target);
	
	@Override
	public Target getTarget() {
		return target;
	}

	
	
}
