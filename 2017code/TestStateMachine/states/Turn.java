package states;

import states.StateMachine.Goals;
import states.StateMachine.Pos;
import states.StateMachine.States;

public class Turn implements RobotState{

	Goals goal;
	Pos pos;
	double range;
	double current;
	double desired;
	double iteration = 0;
	
	public Turn(Goals gl, Pos position){
		goal = gl;
		pos = position;
	}
	
	private boolean inRange(){
		if (Math.abs(desired - current) < range){
			return true;
		}
		return false;
	}
	
	@Override
	public States run() {
		if (!inRange()){
			//turn
			return States.TURN;
		}
		switch (goal){
			case NOTHING:
				return States.STOP;
			case LINE:
				if (pos == Pos.FL || pos == Pos.FR){
					return States.STOP;
				}
				if (pos == Pos.FM){
					if (iteration == 1 || iteration == 3){
						return States.MOVE;
					}else{
						return States.STOP;
					}
				}
				break;
			case SHOOT:
				if (pos == Pos.FL || pos == Pos.FM){
					if (iteration == 1 || iteration == 3 || iteration == 5){
						return States.MOVE;
					}
				}
				if (pos == Pos.FR){
					if (iteration == 2){
						return States.MOVE;
					}
				}
				break;
			default:
				return States.STOP;	
		}
		return States.STOP;
	}
}
	
