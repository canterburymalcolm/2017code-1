package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.utilities.IMU;

import states.StateMachine.Goals;
import states.StateMachine.Pos;
import states.StateMachine.States;

public class Turn implements RobotState{
	
	DriveSystem drive;
	IMU imu;
	Goals goal;
	Pos pos;
	double range;
	double current;
	double desired;
	double iteration = 0;
	
	public Turn(Goals gl, Pos position, DriveSystem dr, IMU ADIS){
		goal = gl;
		pos = position;
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
	public States run() {
		if (!inRange()){
			drive.autoAngle(0, current, desired);
			return States.TURN;
		}
		drive.move(0, 0);
		desired = 400;
		switch (goal){
			case NOTHING:
				return States.STOP;
			case LINE:
				if (pos == Pos.FL || pos == Pos.FR || pos == Pos.BL || pos == Pos.BR){
					return States.STOP;
				}
				if (pos == Pos.FM || pos == Pos.BM){
					if (iteration == 1 || iteration == 3){
						return States.MOVE;
					}else{
						return States.STOP;
					}
				}
				break;
			case SHOOT:
				if (pos == Pos.FL || pos == Pos.FM || pos == Pos.BR || pos == Pos.BM){
					if (iteration == 1 || iteration == 3 || iteration == 5){
						return States.MOVE;
					}
				}
				if (pos == Pos.FR || pos == Pos.BL){
					if (iteration == 2){
						return States.MOVE;
					}
				}
				break;
			case GEAR:
				if (pos == Pos.FL || pos == Pos.FR || pos == Pos.BL || pos == Pos.BR){
					if (iteration == 2){
						return States.MOVE;
					}
				}else if(pos == Pos.FM || pos == Pos.BM){
						return States.STOP;
				}
				break;
			default:
				return States.STOP;	
		}
		return States.STOP;
	}
}
	
