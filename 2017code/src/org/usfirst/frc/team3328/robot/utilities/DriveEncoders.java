package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.Encoder;

public class DriveEncoders {
	
	Encoder fr;
	Encoder fl;
	Encoder br;
	Encoder bl;
	
	public DriveEncoders(){
		fr = new Encoder(0,0);
		fl = new Encoder(1,1);
		br = new Encoder(2,2);
		bl = new Encoder(3,3);
	}
	
	public void reset(){
		fr.reset();
		fl.reset();
		br.reset();
		bl.reset();
	}
	
	public double frRate(){
		return fr.getRate();
	}

	public double flRate(){
		return fl.getRate();
	}
	
	public double brRate(){
		return br.getRate();
	}
	
	public double blRate(){
		return bl.getRate();
	}
	
	public double frDistance(){
		return fr.getDistance();
	}
	
	public double flDistance(){
		return fl.getDistance();
	}
	
	public double brDistance(){
		return br.getDistance();
	}
	
	public double blDistance(){
		return bl.getDistance();
	}
}
