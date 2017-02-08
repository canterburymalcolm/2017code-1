package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class TrackGear implements RobotState{

	DriveSystem drive;
	Tracking track;
	
	public TrackGear(DriveSystem dr, Tracking trackingSystem) {
		drive = dr;
		track = trackingSystem;
	}
	
	@Override
	public void setValue(double val){
		
	}
	
	@Override
	public void run() {
		track.track();
		while (track.tracking) {
			drive.controlledMove();
		}
		drive.stop();
	}

}
