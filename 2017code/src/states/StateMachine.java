package states;

import java.util.*;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.HotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;
import org.usfirst.frc.team3328.robot.utilities.IMU;

public class StateMachine {
	
	Map<States, RobotState> classes = new HashMap<>();
	public enum States {START, TURN, MOVE, TRACKSHOT, SHOOT, TRACKGEAR, GEAR, STOP};
	public enum Goals {SHOOT, LINE, GEAR, NOTHING};
	public enum Pos {FL, FM, FR, BL, BM, BR};
	double currentAng;
	double desiredAng;
	double distance;
	int iteration;
	Goals goal;
	Pos pos;
	States state;
	States prevState;
	DriveSystem drive;
	Shooter shooter;
	HotelLobby belt;
	IMU imu;
	Start start = new Start(goal, pos);
	Move move = new Move(goal, pos, drive);
	Turn turn = new Turn(goal, pos, drive, imu);
	TrackShot trackShot = new TrackShot(drive);
	Shoot shoot = new Shoot(goal, pos, shooter, belt);
	TrackGear trackGear = new TrackGear();
	Gear gear = new Gear();
	Stop stop = new Stop(drive, shooter);
	private final double LINELONG = 5;
	private final double LINESHORT = 2;
	private final double SHOOTLONG = 10;
	private final double SHOOTMIDDLE = 5;
	private final double SHOOTSHORT = 2;
	private final double SHOOTFINAL = 1;
	private final double GEARLONG = 6;
	private final double GEARMIDDLE = 3;
	private final double GEARSHORT = 1;
	
	
	public StateMachine(int mode, DriveSystem dr, Shooter sh, IMU ADIS, HotelLobby lobby){
		switch (mode){
			case 0:
				goal = Goals.SHOOT;
				pos = Pos.FL;
				break;
			case 1:
				goal = Goals.SHOOT;
				pos = Pos.FM;
				break;
			case 2:
				goal = Goals.SHOOT;
				pos = Pos.FR;
				break;
			case 3:
				goal = Goals.SHOOT;
				pos = Pos.BL;
				break;
			case 4:
				goal = Goals.SHOOT;
				pos = Pos.BM;
				break;
			case 5:
				goal = Goals.SHOOT;
				pos = Pos.BR;
				break;
			case 6:
				goal = Goals.LINE;
				pos = Pos.FL;
				break;
			case 7:
				goal = Goals.LINE;
				pos = Pos.FM;
				break;
			case 8:
				goal = Goals.LINE;
				pos = Pos.FR;
				break;
			case 9:
				goal = Goals.LINE;
				pos = Pos.BL;
				break;
			case 10:
				goal = Goals.LINE;
				pos = Pos.BM;
				break;
			case 11:
				goal = Goals.LINE;
				pos = Pos.BR;
				break;
			case 12:
				goal = Goals.NOTHING;
				pos = Pos.FL;
				break;
			case 13:
				goal = Goals.GEAR;
				pos = Pos.FL;
				break;
			case 14:
				goal = Goals.GEAR;
				pos = Pos.FM;
				break;
			case 15:
				goal = Goals.GEAR;
				pos = Pos.FR;
				break;
			case 16:
				goal = Goals.GEAR;
				pos = Pos.BL;
				break;
			case 17:
				goal = Goals.GEAR;
				pos = Pos.BM;
				break;
			case 18:
				goal = Goals.GEAR;
				pos = Pos.BR;
				break;
			default:
				goal = Goals.NOTHING;
				pos = Pos.FL;
		}
		drive = dr;
		shooter = sh;
		imu = ADIS;
		belt = lobby;
	}
	
	public void createMap(){
		classes.put(States.START, start);
		classes.put(States.MOVE, move);
		classes.put(States.TURN, turn);
		classes.put(States.TRACKSHOT, trackShot);
		classes.put(States.SHOOT, shoot);
		classes.put(States.TRACKGEAR, trackGear);
		classes.put(States.GEAR, gear);
		classes.put(States.STOP, stop);
	}
	
	public void updateDistance(){
		switch (goal){
			case NOTHING:
				move.distance = 0;
				break;
			case LINE:
				if (move.distance == -1){
					if (pos == Pos.FL || pos == Pos.FR || pos == Pos.BR || pos == Pos.BL){
						if (iteration == 1){
							move.distance = LINELONG;
						}
					}else if (pos == Pos.FM || pos == Pos.BM){
						if (iteration == 2){
							move.distance = LINESHORT;
						}
						if (iteration == 4){
							move.distance = LINELONG;
						}
					}
				}
				break;
			case SHOOT:
				if (move.distance == -1){
					if (pos == Pos.FL || pos == Pos.BR){
						if (iteration == 2){
							move.distance = SHOOTLONG;
						}
						if (iteration == 4){
							move.distance = SHOOTSHORT;
						}
						if (iteration == 6){
							move.distance = SHOOTFINAL;
						}
					}else if (pos == Pos.FM || pos == Pos.BM){
						if (iteration == 2){
							move.distance = SHOOTMIDDLE;
						}
						if (iteration == 4){
							move.distance = SHOOTSHORT;
						}
						if (iteration == 6){
							move.distance = SHOOTFINAL;
						}
					}else if (pos == Pos.FR || pos == Pos.BL){
						if (iteration == 1){
							move.distance = SHOOTSHORT;
						}
						if (iteration == 3){
							move.distance = SHOOTFINAL;
						}
					}
				}
				break;
			case GEAR:
				if (move.distance == -1){
					if (pos == Pos.FL || pos == Pos.BR || pos == Pos.FR || pos == Pos.BL){
						if (iteration == 1){
							move.distance = GEARLONG;
						}
						if (iteration == 3){
							move.distance = GEARSHORT;
						}
					}else if (pos == Pos.FM || pos == Pos.BM){
						if (iteration == 1){
							move.distance = GEARMIDDLE;
						}
					}
				}
				break;
			default:
				move.distance = 0;
		}
	}
	
	public void updateAngles(){
		switch (goal){
			case NOTHING:
				turn.desired = 0;
				break;
			case LINE:
				if (turn.desired == 400){
					if (pos == Pos.FL || pos == Pos.FR || pos == Pos.BR || pos == Pos.BL){
						turn.desired = 0;
					}else if (pos == Pos.FM || pos == Pos.BM){
						if (iteration == 1){
							turn.desired = turn.current + 90;
						}
						if (iteration == 3){
							move.distance = turn.current - 90;
						}
					}
				}
				break;
			case SHOOT:
				if (turn.desired == 400){
					if (pos == Pos.FL || pos == Pos.FM || pos == Pos.BR || pos == Pos.BM){
						if (iteration == 1){
							turn.desired = turn.current + 90;
						}
						if (iteration == 3){
							turn.desired = turn.current - 90;
						}
						if (iteration == 5){
							turn.desired = turn.current + 90;
						}
					}else if (pos == Pos.FR || pos == Pos.BL){
						if (iteration == 2){
							turn.desired = turn.current + 90;
						}
					}
				}
				break;
			case GEAR:
				if (turn.desired == 400){
					if (pos == Pos.FL || pos == Pos.BR){
						if (iteration == 2){
							turn.desired = turn.current + 90;
						}
					}else if(pos == Pos.FR || pos == Pos.BL){
						if (iteration == 2){
							turn.desired = turn.current - 90;
						}
					}else if(pos == Pos.FM || pos == Pos.BM){
						turn.desired = 0;
					}
				}
				break;
			default:
				turn.desired = 0;
		}
	}
	
	public void updateIteration(){
		move.iteration = iteration;
		turn.iteration = iteration;
	}
	
	public void run(){
		iteration = 0;
		createMap();
		state = classes.get(States.START).run();
		prevState = state;
		while (state != States.STOP){
			updateIteration();
			updateDistance();
			updateAngles();
			state = classes.get(state).run();
			if (prevState != state){
				drive.resetDistance();
				iteration++;
			}
			prevState = state;
		}
	}
}
