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
		switch(goal){
			case NOTHING:
				return States.STOP;
			case LINE:
				 if (pos == Pos.FM || pos == Pos.BM){
					 return States.TURN;
				 }else{
					 return States.MOVE;
				 }
			case SHOOT:
				 if (pos == Pos.FL || pos == Pos.FM || pos == Pos.BR || pos == Pos.BM){
					 return States.TURN;
				 }else{
					 return States.MOVE;
				 }
			case GEAR:
				return States.MOVE;
			default:
				return States.STOP;
		}
	}
	
}
