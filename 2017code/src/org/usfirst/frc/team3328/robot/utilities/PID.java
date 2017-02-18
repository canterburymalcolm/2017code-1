package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class PID extends PIDSubsystem{
	
	double error;
	double correction;
	
	public PID(String name, double P, double I, double D){
		super(name, P, I, D);
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
	}
	
	public void setError(double error){
		this.error = error;
	}
	
	public double getCorrection(){
		return correction;
	}
	
	@Override
	protected double returnPIDInput() {
		System.out.println("error: " + error);
		return error;
	}

	@Override
	protected void usePIDOutput(double output) {
		correction = output;
		
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	
	
}
