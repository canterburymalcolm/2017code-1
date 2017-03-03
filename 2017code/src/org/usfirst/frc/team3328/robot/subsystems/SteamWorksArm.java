package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;

public class SteamWorksArm implements Arm{
	
	Servo servo;
	
	public SteamWorksArm(Servo servo){
		this.servo = servo;
	}
	
	@Override
	public void extend() {
		servo.set(50);
		
	}

	@Override
	public void rectract() {
		servo.set(0);
	}

	@Override
	public boolean isExtended() {
		return servo.get() > 0;
	}

	
	
}
