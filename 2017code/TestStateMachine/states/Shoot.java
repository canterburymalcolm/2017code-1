package states;

import states.StateMachine.Goals;
import states.StateMachine.Pos;
import states.StateMachine.States;

public class Shoot implements RobotState{

	Goals goal;
	Pos pos;
	
	public Shoot(Goals gl, Pos position){
		goal = gl;
		pos = position;
	}

	@Override
	public States run() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
