package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class TrackShot implements RobotState {
	
	DriveSystem drive;
	Tracking track;
	
	public TrackShot(DriveSystem dr, Tracking trackingSystem){
		drive = dr;
		track = trackingSystem;
	}
	
	@Override
	public void setValue(double value){
		
	}
	
	@Override
	public void run() {
		track.track();
		if (track.tracking){
			drive.controlledMove();
		}
		drive.stop();
	}

}
