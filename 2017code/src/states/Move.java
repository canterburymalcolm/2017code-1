package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;

public class Move implements RobotState{
	
	DriveSystem drive;
	double distance = -1;
	double distanceTraveled;
	
	public Move(DriveSystem dr){
		drive = dr;
	}
	
	@Override
	public void setValue(double value) {
		distance = value;
	}
	
	@Override
	public void run() {
		System.out.println("moving");
		while (distanceTraveled < distance){
			distanceTraveled += .01;
			drive.move(.2, .2);
		}
		drive.stop();
	}

}
