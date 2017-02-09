package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.Encoder;

public class DriveEncoders {
	
	Encoder right;
	Encoder left;
	
	public DriveEncoders(){
		right = new Encoder(2,3);
		left = new Encoder(4,5);
	}
	
	public void reset(){
		/*fr.reset();
		fl.reset();
		br.reset();
		bl.reset();*/
	}
	
	public double getDistance(){
		return (rightDistance() + leftDistance()) / 2;
	}
	
	public double getOffset(){
		return rightDistance() - leftDistance();
	}
	
	public double rightRate(){
		return right.getRate();
	}

	public double leftRate(){
		return left.getRate();
	}
	
	public double rightDistance(){
		return right.getDistance();
	}
	
	public double leftDistance(){
		return left.getDistance();
	}
	
}
