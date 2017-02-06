package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.SpeedController;

public class ShooterTalons {
	
	private SpeedController t1;
	private SpeedController t2;
	
	public ShooterTalons(SpeedController talonController1, SpeedController talonController2){
		t1 = talonController1;
		t2 = talonController2;
	}

	public void set(double speed){
		t1.set(speed);
		t2.set(speed);
	}
	
	public void stop() {
		t1.set(0);
		t2.set(0);
	}
	
}
