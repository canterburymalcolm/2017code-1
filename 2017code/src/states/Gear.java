package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;

public class Gear implements RobotState{

	DriveSystem drive;
	
	public Gear(DriveSystem dr) {
		drive = dr;	
	}
	
	@Override
	public void setValue(double val){
		
	}
	
	@Override
	public boolean run() {
		if(drive.placingGear()){
			drive.placeGear();
			return false;
		}
		drive.stop();
		return true;
	}

}
