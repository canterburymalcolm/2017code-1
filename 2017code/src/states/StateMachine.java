package states;

import java.util.*;

import org.usfirst.frc.team3328.robot.Teleop;
import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.HotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;
import org.usfirst.frc.team3328.robot.utilities.IMU;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class StateMachine {
	
	private final double LINELONG = 10;
	private final double LINESHORT = 3;
	private final double SHOOTLONG = 10;
	private final double SHOOTMIDDLE = 5;
	private final double SHOOTSHORT = 3;
	private final double GEARlONG = 10;
	private final double GEARMIDDLE = 5;
	private final double GEARSHORT = 3;
	
	int iteration = -1;
	double value;
	boolean newState = true;
	States state;
	
	public enum States {WAIT, TURN, ENCODERTURN, MOVE, TRACKSHOT, SHOOT, TRACKGEAR, GEAR, STOP};
	public enum Modes {LINELONG, LINEMIDDLE, SHOOTFL, SHOOTFM, SHOOTFR, SHOOTBL, SHOOTBM, SHOOTBR, 
						GEARFL, GEARFM, GEARFR, GEARBL, GEARBM, GEARBR, CUSTOM1, CUSTOM2, NOTHING};
	
	DriveSystem drive;
	Tracking track;
	Shooter shooter;
	HotelLobby belt;
	IMU imu;
	
	Map<States, RobotState> classes = new HashMap<States, RobotState>()/*{
		
		private static final long serialVersionUID = 1368017743131992753L;

	{
		put(States.MOVE, new Move(drive));
		put(States.TURN, new Turn(drive, imu));
		put(States.TRACKSHOT, new TrackShot(drive));
		put(States.SHOOT, new Shoot(shooter, belt));
		put(States.TRACKGEAR, new TrackGear(drive));
		put(States.GEAR, new Gear(drive));
		put(States.STOP, new Stop(drive, shooter));
	}}*/;

	
	List<State> lineLong = Arrays.asList(
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
			new State(States.GEAR, 0));
	List<State> gearFM = Arrays.asList(
			new State(States.MOVE, GEARMIDDLE),
			new State(States.GEAR, 0));
	List<State> gearFR = Arrays.asList(
			new State(States.MOVE, GEARlONG),
			new State(States.TURN, -90),
			new State(States.MOVE, GEARSHORT),
			new State(States.GEAR, 0));
	List<State> gearBL = Arrays.asList(
			new State(States.MOVE, GEARlONG),
			new State(States.TURN, -90),
			new State(States.MOVE, GEARSHORT),
			new State(States.GEAR, 0));
	List<State> gearBM = Arrays.asList(
			new State(States.MOVE, GEARMIDDLE),
			new State(States.GEAR, 0));
	List<State> gearBR = Arrays.asList(
			new State(States.MOVE, GEARlONG),
			new State(States.TURN, 90),
			new State(States.MOVE, GEARSHORT),
			new State(States.GEAR, 0));
	List<State> custom1 = Arrays.asList(
			new State(States.MOVE, 225),
			new State(States.MOVE, 450),
			new State(States.MOVE, 450),
			new State(States.TURN, 175),
			new State(States.MOVE, 450),
			new State(States.MOVE, 450),
			new State(States.MOVE, 225),
			new State(States.STOP, 0));
	List<State> custom2 = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.WAIT, 3),
			new State(States.TURN, -90),
			new State(States.WAIT, 3),
			new State(States.TURN, 180),
			new State(States.STOP, 0));
	List<State> nothing = Arrays.asList(
			new State(States.STOP, 0));
	
	List<State> mode;
	
	public StateMachine(Modes input, Teleop teleop){
		switch (input){
			case LINELONG:
				mode = lineLong;
				break;
			case LINEMIDDLE:
				mode = lineMiddle;
				break;
			case SHOOTFL:
				mode = shootFL;
				break;
			case SHOOTFM:
				mode = shootFM;
				break;
			case SHOOTFR:
				mode = shootFR;
				break;
			case SHOOTBL:
				mode = shootBL;
				break;
			case SHOOTBM:
				mode = shootBM;
				break;
			case SHOOTBR:
				mode = shootBR;
				break;
			case GEARFL:
				mode = gearFL;
				break;
			case GEARFM:
				mode = gearFM;
				break;
			case GEARFR:
				mode = gearFR;
				break;
			case GEARBL:
				mode = gearBL;
				break;
			case GEARBM:
				mode = gearBM;
				break;
			case GEARBR:
				mode = gearBR;
				break;
			case CUSTOM1:
				mode = custom1;
				break;
			case CUSTOM2:
				mode = custom2;
				break;
			case NOTHING:
				mode = nothing;
			default:
				mode = nothing;
		}
		drive = teleop.getDrive();
		track = drive.getTrack();
		shooter = teleop.getShooter();
		imu = drive.getImu();
		belt = shooter.getBelt();
		classes.put(States.WAIT,  new Wait());
		classes.put(States.MOVE, new Move(drive));
		classes.put(States.TURN, new Turn(drive, imu));
		classes.put(States.ENCODERTURN, new EncoderTurn(drive));
		classes.put(States.TRACKSHOT, new TrackShot(drive));
		classes.put(States.SHOOT, new Shoot(shooter, belt));
		classes.put(States.TRACKGEAR, new TrackGear(drive));
		classes.put(States.GEAR, new Gear(drive));
		classes.put(States.STOP, new Stop(drive, shooter));
	}
	
	public void reset(){
		iteration = -1;
		drive.stop();
		newState = true;
	}
	
	public void run(){
		if (newState){
			iteration++;
			state = mode.get(iteration).getState();
			value = mode.get(iteration).getValue();
			classes.get(state).setValue(value);
			System.out.printf("State: %s\n", state.toString());
			newState = false;
		}else {
			newState = classes.get(state).run();
		}
	}
}
