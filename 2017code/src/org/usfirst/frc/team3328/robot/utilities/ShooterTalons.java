package org.usfirst.frc.team3328.robot.utilities;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.TalonSRX;

public class ShooterTalons {
	
	private CANTalon t1;
	private CANTalon t2;
	double rampRate = 6;
	
	public ShooterTalons(CANTalon t1, CANTalon t2){
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
	
	public double getRate(){
		System.out.println("vel " + t1.getAnalogInVelocity());
		return t1.get();
	}
	
}
