package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.utilities.IMU;


public class Turn implements RobotState{
	
	DriveSystem drive;
	IMU imu;
	double range = .5;
	double current;
	double desired;
	
	public Turn(DriveSystem dr, IMU ADIS){
		drive = dr;
		imu = ADIS;
	}
	
	private boolean inRange(){
		current = imu.getAngleZ();
		if (Math.abs(desired - current) < range){
			return true;
		}
		return false;
	}
	
	@Override
	public void setValue(double value) {
		desired = current + value;
	}
	
	@Override
	public boolean run() {
		if (!inRange()){
			drive.autoAngle(current, desired);
			return false;
		}
		drive.stop();	
		return true;
	}

}
	
