package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;

public class Move implements RobotState{
	
	DriveSystem drive;
	double distance = -1;
	double distanceTraveled = 0;
	double speed = .4;
	
	public Move(DriveSystem dr){
		drive = dr;
	}
	
	@Override
	public void setValue(double value) {
		distance = value;
		drive.resetDistance();
	}
	
	@Override
	public boolean run() {
		distanceTraveled = drive.getDistance();
		if (distanceTraveled < distance){
			drive.move(speed, speed);
			return false;
		}
		drive.stop();
		drive.resetDistance();
		return true;
	}

}
