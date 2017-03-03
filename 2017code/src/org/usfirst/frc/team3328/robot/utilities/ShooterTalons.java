package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.CANSpeedController;

public class ShooterTalons {
	
	private CANSpeedController t1;
	private CANSpeedController t2;
	double rampRate = 6;
	
	public ShooterTalons(CANSpeedController t1, CANSpeedController t2){
		this.t1 = t1;
		this.t2 = t2;
		this.t1.setVoltageRampRate(rampRate);
		this.t2.setVoltageRampRate(rampRate);
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
