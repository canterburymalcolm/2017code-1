package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;

import states.StateMachine.States;

public class TrackShot implements RobotState {
	
	DriveSystem drive;
	
	public TrackShot(DriveSystem dr){
		drive = dr;
	}
	
	@Override
	public States run() {
		drive.track();
		if (drive.isTracking()){
			return States.TRACKSHOT;
		}
		return States.SHOOT;
	}

}
