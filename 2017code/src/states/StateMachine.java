package states;

import java.util.*;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.HotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;
import org.usfirst.frc.team3328.robot.utilities.IMU;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class StateMachine {
	
	//States
	public enum States {TURN, MOVE, TRACKSHOT, SHOOT, TRACKGEAR, GEAR, STOP};
	Move move;
	Turn turn;
	TrackShot trackShot;
	Shoot shoot;
	TrackGear trackGear;
	Gear gear;
	Stop stop;
	
	//classes to be passed to the states
	DriveSystem drive;
	Tracking track;
	Shooter shooter;
	HotelLobby belt;
	IMU imu;
	
	//Map of the states and their respective classes
	Map<States, RobotState> classes = new HashMap<States, RobotState>(){{
		put(States.MOVE, move);
		put(States.TURN, turn);
		put(States.TRACKSHOT, trackShot);
		put(States.SHOOT, shoot);
		put(States.TRACKGEAR, trackGear);
		put(States.GEAR, gear);
		put(States.STOP, stop);
	}};
	
	//Constants
	private final double LINELONG = 10;
	private final double LINESHORT = 3;
	private final double SHOOTLONG = 10;
	private final double SHOOTMIDDLE = 5;
	private final double SHOOTSHORT = 3;
	private final double GEARlONG = 10;
	private final double GEARMIDDLE = 5;
	private final double GEARSHORT = 3;
	
	//Modes
	List<State> lineStandard = Arrays.asList(
			new State(States.MOVE, LINELONG),
			new State(States.STOP, 0));
	List<State> lineMiddle = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.MOVE, LINESHORT),
			new State(States.TURN, -90),
			new State(States.MOVE, LINELONG),
			new State(States.STOP, 0));
	List<State> shootFL = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTLONG),
			new State(States.TURN, -90),
			new State(States.MOVE, SHOOTMIDDLE),
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTSHORT),
			new State(States.TRACKSHOT, 0),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootFM = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTMIDDLE),
			new State(States.TURN, -90),
			new State(States.MOVE, SHOOTMIDDLE),
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTSHORT),
			new State(States.TRACKSHOT, 0),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootFR = Arrays.asList(
			new State(States.MOVE, SHOOTMIDDLE),
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTSHORT),
			new State(States.TRACKSHOT, 0),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootBL = Arrays.asList(
			new State(States.MOVE, SHOOTMIDDLE),
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTSHORT),
			new State(States.TRACKSHOT, 0),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootBM = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTMIDDLE),
			new State(States.TURN, -90),
			new State(States.MOVE, SHOOTMIDDLE),
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTSHORT),
			new State(States.TRACKSHOT, 0),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootBR = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTLONG),
			new State(States.TURN, -90),
			new State(States.MOVE, SHOOTMIDDLE),
			new State(States.TURN, 90),
			new State(States.MOVE, SHOOTSHORT),
			new State(States.TRACKSHOT, 0),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> gearFL = Arrays.asList(
			new State(States.MOVE, GEARlONG),
			new State(States.TURN, 90),
			new State(States.MOVE, GEARSHORT),
			new State(States.TRACKGEAR, 0),
			new State(States.GEAR, 0));
	List<State> gearFM = Arrays.asList(
			new State(States.MOVE, GEARMIDDLE),
			new State(States.TRACKGEAR, 0),
			new State(States.GEAR, 0));
	List<State> gearFR = Arrays.asList(
			new State(States.MOVE, GEARlONG),
			new State(States.TURN, -90),
			new State(States.MOVE, GEARSHORT),
			new State(States.TRACKGEAR, 0),
			new State(States.GEAR, 0));
	List<State> gearBL = Arrays.asList(
			new State(States.MOVE, GEARlONG),
			new State(States.TURN, -90),
			new State(States.MOVE, GEARSHORT),
			new State(States.TRACKGEAR, 0),
			new State(States.GEAR, 0));
	List<State> gearBM = Arrays.asList(
			new State(States.MOVE, GEARMIDDLE),
			new State(States.TRACKGEAR, 0),
			new State(States.GEAR, 0));
	List<State> gearBR = Arrays.asList(
			new State(States.MOVE, GEARlONG),
			new State(States.TURN, 90),
			new State(States.MOVE, GEARSHORT),
			new State(States.TRACKGEAR, 0),
			new State(States.GEAR, 0));
	List<State> custom = Arrays.asList();
	List<State> nothing = Arrays.asList(
			new State(States.STOP, 0));
	
	List<State> mode;
	
	public StateMachine(int input, DriveSystem dr, Tracking trackingSystem, Shooter sh, IMU ADIS, HotelLobby lobby){
		switch (input){
			case 0:
				mode = lineStandard;
				break;
			case 1:
				mode = lineMiddle;
				break;
			case 2:
				mode = lineStandard;
				break;
			case 3:
				mode = lineStandard;
				break;
			case 4:
				mode = lineMiddle;
				break;
			case 5:
				mode = lineStandard;
				break;
			case 6:
				mode = shootFL;
				break;
			case 7:
				mode = shootFM;
				break;
			case 8:
				mode = shootFR;
				break;
			case 9:
				mode = shootBL;
				break;
			case 10:
				mode = shootBM;
				break;
			case 11:
				mode = shootBR;
				break;
			case 12:
				mode = gearFL;
				break;
			case 13:
				mode = gearFM;
				break;
			case 14:
				mode = gearFR;
				break;
			case 15:
				mode = gearBL;
				break;
			case 16:
				mode = gearBM;
				break;
			case 17:
				mode = gearBR;
				break;
			case 18:
				mode = custom;
				break;
			case 19:
				mode = nothing;
				break;
			default:
				mode = nothing;
		}
		drive = dr;
		track = trackingSystem;
		shooter = sh;
		imu = ADIS;
		belt = lobby;
		move = new Move(drive);
		turn = new Turn(drive, imu);
		trackShot = new TrackShot(drive, track);
		shoot = new Shoot(shooter, belt);
		trackGear = new TrackGear(drive, track);
		gear = new Gear(drive);
		stop = new Stop(drive, shooter);
		//createMap();
	}
	
	public void createMap(){
		classes.put(States.MOVE, move);
		classes.put(States.TURN, turn);
		classes.put(States.TRACKSHOT, trackShot);
		classes.put(States.SHOOT, shoot);
		classes.put(States.TRACKGEAR, trackGear);
		classes.put(States.GEAR, gear);
		classes.put(States.STOP, stop);
	}
	
	public void run(){
		for(int i = 0; i < mode.size(); i++){
			System.out.printf("State: %s", mode.get(i).getState());
			classes.get(mode.get(i).getState()).setValue(mode.get(i).getValue());
			classes.get(mode.get(i).getState()).run();
		}
	}
}
