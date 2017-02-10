package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.networking.NetworkTablesTargetProvider;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksClimber;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksDriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksFeeder;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksHotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksShooter;
import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.PID;
import org.usfirst.frc.team3328.robot.utilities.REVDigitBoard;
import org.usfirst.frc.team3328.robot.utilities.ShooterTalons;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;

import states.StateMachine;

public class Robot extends IterativeRobot {
	Teleop telop;
	StateMachine auto;
	REVDigitBoard digit;
	NetworkTablesTargetProvider targetProvider;

	@Override
	public void robotInit() {
		telop = new Teleop(
				new SteamWorksDriveSystem(
					new DriveEncoders(
						new Encoder(2,3), 
						new Encoder(4,5)),
					new DriveTalons(
						new Talon(0),
						new Talon(1),
						new Talon(2),
						new Talon(3)),
					new Tracking(
							targetProvider.getTarget(),
							new PID("track",0 ,0 ,0)),
					new ADIS16448_IMU(),
					new PID("autoAngle",0 ,0 ,0)),
				new SteamWorksShooter(
						new Encoder(0,1),
						new ShooterTalons(
							new Talon(6),
							new Talon(7)),
						new SteamWorksHotelLobby(
							new Talon(8))),
				new SteamWorksFeeder(
						new Talon(5)),
				new SteamWorksClimber(
						new Talon(4)),
				new SteamWorksXbox(0),
				new SteamWorksXbox(1));
		auto = new StateMachine(2, telop);
		digit = new REVDigitBoard();
		targetProvider = new NetworkTablesTargetProvider();
	}

	@Override
	public void autonomousInit() {
		digit.updateButtons();
	}

	@Override
	public void autonomousPeriodic() {
		auto.run();
	}

	@Override
	public void teleopPeriodic() {
		telop.run();
	}

	@Override
	public void testPeriodic() {
	}
}