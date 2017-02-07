package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.XboxController;

public class SteamWorksXbox implements Controller {
	
	XboxController xbox;
	//array to store previous value of buttons
	boolean[] button = new boolean[10];
	
	public enum Buttons {A(1), B(2), X(3), Y(4), LBUMP(5), RBUMP(6); 
		public int value;
		Buttons(int value) {
			this.value = value;
		}
	}
	
	//instantiates an xbox controller
	public SteamWorksXbox(int channel){
		xbox = new XboxController(channel);
	}
	
	//returns the right trigger minus the left trigger to allow for forwards and reverse
	@Override
	public double getX(){
		return xbox.getRawAxis(3) - xbox.getRawAxis(2);
	}
	
	//returns the x axis with a deadband of .1
	@Override
	public double getY(){
		if (Math.abs(xbox.getRawAxis(0)) > .1){
			return xbox.getRawAxis(0);
		}
		return 0;
	}
	
	//returns true when the button with the index "num" has been released
	@Override
	public boolean getButtonRelease(Buttons but){
		int num = but.value;
		if (xbox.getRawButton(num) &&  !button[num]){
			button[num] = xbox.getRawButton(num);
			return true;
		}
		button[num] = xbox.getRawButton(num);
		return false;
	}
	
	//returns true while the button is pressed not when released
	@Override
	public boolean getButtonPress(Buttons but){
		int num = but.value;
		return xbox.getRawButton(num);
	}
	
	//returns Dpad values
	//0 up 180 down clockwise increasing
	public double getPad(){
		return xbox.getPOV();
	}
	
	
}