package states;

import states.StateMachine.*;

public class Start implements RobotState{
	
	Goals goal;
	Pos pos;
	States state;
	
	public Start(Goals gl, Pos position){
		goal = gl;
		pos = position;
	}
	
	@Override
	public States run(){
		if (goal == Goals.NOTHING){
			return States.STOP;
		}
		switch (pos){
			case  FL:
				if (goal == Goals.LINE){
					state = States.MOVE;
				}else{
					state = States.TURN;
				}
				break;
			case FM:
				state = States.TURN;
				break;
			case FR:
				if (goal == Goals.LINE){
					state = States.MOVE;
				}else{
					state = States.TURN;
				}
				break;
			default:
				state = States.STOP;
		}
		return state;	
	}
	
}
