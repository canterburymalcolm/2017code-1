package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;

import states.StateMachine.States;

public class Stop implements RobotState{
	
	DriveSystem drive;
	Shooter shooter;

	public Stop(DriveSystem dr, Shooter sh){
		drive = dr;
		shooter = sh;
	}
	
	@Override
	public States run() {
		drive.move(0, 0);
		//shooter.stop();
		System.out.println("stopped");
		return States.STOP;
	}

}
