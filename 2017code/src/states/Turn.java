package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.IMU;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;


public class Turn implements RobotState{
	
	Timer timer;
	DriveSystem drive;
	IMU imu;
	double range = 2;
	double current;
	double desired;
	
	public Turn(DriveSystem dr, IMU ADIS){
		drive = dr;
		imu = ADIS;
		timer = new Timer();
	}
	
	private boolean inRange(){
		current = imu.getAngleZ();
		//System.out.printf("current: %f|Desired: %f\n", current, desired);
		if (Math.abs(desired - current) < range){
			return true;
		}
		return false;
	}
	
	@Override
	public void setValue(double value) {
		current = imu.getAngleZ();
		desired = current + value;
		timer.start();
	}
	
	@Override
	public boolean run() {
		if (!inRange()){
			timer.reset();
			drive.autoAngle(current, desired);
			return false;
		}
		if (timer.get() > 1){
			drive.stop();	
			return true;
		}else{
			return false;
		}
	}

}
	
