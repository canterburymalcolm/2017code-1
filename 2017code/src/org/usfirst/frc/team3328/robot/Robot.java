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
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

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
	SpeedController shootControl1;
	SpeedController shootControl2;
	SpeedController beltControl;
	Controller driveXbox;
	Controller utilXbox;
	DriveSystem drive;
	Climber climb;
	Feeder feed;
	Shooter shoot;
	HotelLobby belt;
	Comms comms;
	Target target;
	NetworkTablesTargetProvider targetProvider;
	

	@Override
	public void robotInit() {
		imu = new ADIS16448_IMU();
		driveEncoders = new DriveEncoders();
		fl = new Talon(0);
		fr = new Talon(1);
		bl = new Talon(2);
		br = new Talon(3);
		driveTalons = new DriveTalons(fl, fr, bl, br);
		climbControl = new Talon(4);
		feedControl = new Talon(5);
		shootControl1 = new Talon(6);
		shootControl2 = new Talon(7);
		beltControl = new Talon(8);
		driveXbox = new SteamWorksXbox(0);
		utilXbox = new SteamWorksXbox(1);
		drive = new SteamWorksDriveSystem(driveEncoders, driveTalons, driveXbox, utilXbox, target);
		climb = new SteamWorksClimber(climbControl, utilXbox);
		feed = new SteamWorksFeeder(feedControl, utilXbox);
		shoot = new SteamWorksShooter(shootEncoder, shootControl1, shootControl2, utilXbox);
		belt = new SteamWorksHotelLobby(beltControl);
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
		//drive.autoAngle(0, imu.getAngleZ(), 0);
	}

	@Override
	public void teleopPeriodic() {
		drive.controlledMove();
		drive.printSpeed();
		//target.printValues();
		
	}

	@Override
	public void testPeriodic() {
	}
}