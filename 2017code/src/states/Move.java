package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import states.StateMachine.*;

public class Move implements RobotState{
	
	DriveSystem drive;
	Goals goal;
	Pos pos;
	double distance = -1;
	double distanceTraveled;
	double iteration = 0;
	
	public Move(Goals gl, Pos position, DriveSystem dr){
		goal = gl;
		pos = position;
		drive = dr;
	}
	
	@Override
	public States run() {
		distanceTraveled = drive.getDistance();
		if (distanceTraveled < distance){
			drive.move(.2, .2);
			return States.MOVE;
		}
		drive.move(0, 0);
		distance = -1;
		switch (goal){
			case NOTHING:
				return States.STOP;
			case LINE:
				if (pos == Pos.FL || pos == Pos.FR || pos == Pos.BL || pos == Pos.BR){
					return States.STOP;
				}
				if (pos == Pos.FM || pos == Pos.BM){
					if (iteration == 2){
						return States.TURN;
					}else{
						return States.STOP;
					}
				}
				break;
			case SHOOT:
				if (pos == Pos.FL || pos == Pos.FM || pos == Pos.BM || pos == Pos.BR){
					if (iteration == 2 || iteration == 4){
						return States.TURN;
					}
					if (iteration == 6){
						return States.TRACKSHOT;
					}
				}
				if (pos == Pos.FR || pos == Pos.BL){
					if (iteration == 1){
						return States.TURN;
					}
					if (iteration == 3){
						return States.TRACKSHOT;
					}
				}
				break;
			case GEAR:
				if (pos == Pos.FL || pos == Pos.FR || pos == Pos.BL || pos == Pos.BR){
					if (iteration == 1){
						return States.TURN;
					}
					if (iteration == 3){
						return States.TRACKGEAR;
					}
				}else if(pos == Pos.FM || pos == Pos.BM){
					if (iteration == 1){
						return States.TRACKGEAR;
					}
				}
				break;
			default:
				return States.STOP;	
		}
		return States.STOP;
	}

}
