package states;

import java.util.*;

import org.usfirst.frc.team3328.robot.Teleop;
import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.HotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;
import org.usfirst.frc.team3328.robot.utilities.IMU;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateMachine {
	
	private final double LINELONG = 4000;
	private final double LINESHORT = 3;
	private final double SHOOTLONG = 10;
	private final double SHOOTMIDDLE = 500;
	private final double SHOOTSHORT = 3;
	private final double GEARlONG = 10;
	private final double GEARMIDDLE = -1100;
	private final double GEARSHORT = 3;
	
	int iteration = -1;
	double value;
	boolean newState = true;
	States state;
	Modes choice;
	SendableChooser<Modes> chooser;

	
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
			new State(States.TURN, 45),
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
			new State(States.STOP, 0));
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
	List<List<State>> modes = Arrays.asList(lineLong, lineMiddle, shootFL, shootFM, shootFR, shootBL, shootBM, shootBR,
											gearFL, gearFM, gearFR, gearBL, gearBM, gearBR, custom1, custom2, nothing);
	
	public StateMachine(Teleop teleop, SendableChooser<Modes> chooser_){
		this.chooser = chooser_;
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
		addModes();
	}
	
	public void setMode(){
		choice = chooser.getSelected();
		mode = modes.get(choice.ordinal());
	}

	public List<State> getMode(){
		return mode;
	}
	
	private void addModes(){
		chooser.addDefault(Modes.NOTHING.toString(), Modes.NOTHING);
		chooser.addObject(Modes.LINELONG.toString(), Modes.LINELONG);
		chooser.addObject(Modes.LINEMIDDLE.toString(), Modes.LINEMIDDLE);
		chooser.addObject(Modes.SHOOTFL.toString(), Modes.SHOOTFL);
		chooser.addObject(Modes.SHOOTFM.toString(), Modes.SHOOTFM);	
		chooser.addObject(Modes.SHOOTFR.toString(), Modes.SHOOTFR);
		chooser.addObject(Modes.SHOOTBL.toString(), Modes.SHOOTBL);
		chooser.addObject(Modes.SHOOTBM.toString(), Modes.SHOOTBM);
		chooser.addObject(Modes.SHOOTBR.toString(), Modes.SHOOTBR);
		chooser.addObject(Modes.GEARFL.toString(), Modes.GEARFL);
		chooser.addObject(Modes.GEARFM.toString(), Modes.GEARFM);
		chooser.addObject(Modes.GEARFR.toString(), Modes.GEARFR);
		chooser.addObject(Modes.GEARBL.toString(), Modes.GEARBL);
		chooser.addObject(Modes.GEARBM.toString(), Modes.GEARBM);
		chooser.addObject(Modes.GEARBR.toString(), Modes.GEARBR);
		chooser.addObject(Modes.CUSTOM1.toString(), Modes.CUSTOM1);
		chooser.addObject(Modes.CUSTOM2.toString(), Modes.CUSTOM2);
		SmartDashboard.putData("Auto choices", chooser);
	}
	
	public void reset(){
//		iteration = -1;
//		drive.stop();
//		newState = true;
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
