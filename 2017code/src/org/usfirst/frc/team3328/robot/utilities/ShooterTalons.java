package org.usfirst.frc.team3328.robot.utilities;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.CANSpeedController;
import edu.wpi.first.wpilibj.TalonSRX;

public class ShooterTalons {
	
	private CANTalon t1;
	private CANTalon t2;
	double rampRate = 12;
	
	public ShooterTalons(CANTalon t1, CANTalon t2){
		this.t1 = t1;
		t1.changeControlMode(CANTalon.TalonControlMode.Follower);
		t1.set(t2.getDeviceID());
		this.t2 = t2;
		this.t2.setVoltageRampRate(rampRate);
	}
	
	public void set(double speed){
		t2.set(speed);
	}
	
	public void stop() {
		t2.set(0);
	}
	
	public double getRate(){
		//System.out.println(t1.getPulseWidthVelocity());
		return t2.getPulseWidthVelocity();
	}
	
}
