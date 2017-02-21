package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.SpeedController;

public class ShooterTalons {
	
	private SpeedController t1;
	private SpeedController t2;
	
	public ShooterTalons(SpeedController t1, SpeedController t2){
		this.t1 = t1;
		this.t2 = t2;
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
