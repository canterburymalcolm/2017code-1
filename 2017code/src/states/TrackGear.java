package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class TrackGear implements RobotState{

	Tracking track;
	DriveSystem drive;
	double trackSpeed;
	
	public TrackGear(DriveSystem drive) {
		this.drive = drive;
		track = drive.getTrack();
	}
	
	@Override
	public void setValue(double val){
		
	}
	
	@Override
	public boolean run() {
		trackSpeed = track.track();
		if (track.getTracking()) {
			drive.move(trackSpeed, -trackSpeed);
			return false;
		}
		drive.stop();
		return true;
	}

}
