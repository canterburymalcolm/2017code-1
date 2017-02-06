package states;

import states.StateMachine.*;

public class Move implements RobotState{
	
	Goals goal;
	Pos pos;
	double distance = -1;
	double distanceTraveled;
	double iteration = 0;
	
	public Move(Goals gl, Pos position){
		goal = gl;
		pos = position;
	}
	
	@Override
	public States run() {
		if (distanceTraveled < distance){
			//move forwards
			return States.MOVE;
		}
		switch (goal){
			case NOTHING:
				return States.STOP;
			case LINE:
				if (pos == Pos.FL || pos == Pos.FR){
					return States.STOP;
				}
				if (pos == Pos.FM){
					if (iteration == 2){
						return States.TURN;
					}else{
						return States.STOP;
					}
				}
				break;
			case SHOOT:
				if (pos == Pos.FL || pos == Pos.FM){
					if (iteration == 2 || iteration == 4){
						return States.TURN;
					}
					if (iteration == 6){
						return States.TRACKSHOT;
					}
				}
				if (pos == Pos.FR){
					if (iteration == 1){
						return States.TURN;
					}
					if (iteration == 3){
						return States.TRACKSHOT;
					}
				}
				break;
			default:
				return States.STOP;	
		}
		return States.STOP;
	}

}
