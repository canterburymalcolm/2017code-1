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
	public void run() {
		drive.resetDistance();
		while(drive.placingGear()){
			drive.placeGear();
		}
		drive.stop();
	}

}
