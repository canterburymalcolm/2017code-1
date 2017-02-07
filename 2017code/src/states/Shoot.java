package states;

import org.usfirst.frc.team3328.robot.subsystems.HotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;

import states.StateMachine.Goals;
import states.StateMachine.Pos;
import states.StateMachine.States;

public class Shoot implements RobotState{

	Goals goal;
	Pos pos;
	Shooter shooter;
	HotelLobby belt;
	
	public Shoot(Goals gl, Pos position, Shooter sh, HotelLobby lobby){
		goal = gl;
		pos = position;
		shooter = sh;
		belt = lobby;
	}

	@Override
	public States run() {
		return States.STOP;
		/*belt.controlBelt();
		shooter.maxSpeed();
		return States.SHOOT;*/
	}
	
}
