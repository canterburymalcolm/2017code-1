package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.networking.Comms;
import org.usfirst.frc.team3328.robot.networking.NetworkTablesTargetProvider;
import org.usfirst.frc.team3328.robot.networking.Target;
import org.usfirst.frc.team3328.robot.subsystems.Climber;
import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.Feeder;
import org.usfirst.frc.team3328.robot.subsystems.HotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksClimber;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksDriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksFeeder;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksHotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksShooter;
import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.IMU;
import org.usfirst.frc.team3328.robot.utilities.ShooterTalons;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import states.StateMachine;

public class Robot extends IterativeRobot {
	IMU imu;
	DriveEncoders driveEncoders;
	Encoder shootEncoder;
	SpeedController fl;
	SpeedController fr;
	SpeedController bl;
	SpeedController br;
	DriveTalons driveTalons;
	SpeedController climbControl;
	SpeedController feedControl;
	SpeedController t1;
	SpeedController t2;
	ShooterTalons shootControl;
	SpeedController beltControl;
	Controller driveXbox;
	Controller utilXbox;
	Tracking track;
	DriveSystem drive;
	Climber climb;
	Feeder feed;
	Shooter shoot;
	HotelLobby belt;
	StateMachine auto;
	Comms comms;
	Target target;
	NetworkTablesTargetProvider targetProvider;
	

	@Override
	public void robotInit() {
		imu = new ADIS16448_IMU();
		driveEncoders = new DriveEncoders();
		shootEncoder = new Encoder(0, 1);
		fl = new Talon(0);
		fr = new Talon(1);
		bl = new Talon(2);
		br = new Talon(3);
		driveTalons = new DriveTalons(fl, fr, bl, br);
		climbControl = new Talon(4);
		feedControl = new Talon(5);
		t1 = new Talon(6);
		t2 = new Talon(7);
		shootControl = new ShooterTalons(t1, t2);
		beltControl = new Talon(8);
		driveXbox = new SteamWorksXbox(1);
		utilXbox = new SteamWorksXbox(0);
		track = new Tracking(target, utilXbox);
		drive = new SteamWorksDriveSystem(driveEncoders, driveTalons, driveXbox, track);
		climb = new SteamWorksClimber(climbControl, utilXbox);
		feed = new SteamWorksFeeder(feedControl, utilXbox);
		shoot = new SteamWorksShooter(shootEncoder, shootControl, utilXbox);
		belt = new SteamWorksHotelLobby(beltControl, shoot);
		auto = new StateMachine(2, drive, track, shoot, imu, belt);
		comms = new Comms();
		targetProvider = new NetworkTablesTargetProvider();
		target = targetProvider.getTarget();
		imu.init();
	}

	@Override
	public void autonomousInit() {
		//auto init code
	}

	@Override
	public void autonomousPeriodic() {
		//drive.autoAngle(imu.getAngleZ(), 0);
	}

	@Override
	public void teleopPeriodic() {
		/*drive.controlledMove();
		drive.printSpeed();*/
		//target.printValues();
		/*if (auto.getState() != States.STOP){
			auto.run();
		}*/
		if (target.flip){
			drive.move(1, 1);
		}else{
			drive.move(-1, -1);
		}
	}

	@Override
	public void testPeriodic() {
	}
}