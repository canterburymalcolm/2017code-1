package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.networking.NetworkTablesTargetProvider;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksClimber;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksDriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksFeeder;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksHotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksShooter;
import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.PID;
import org.usfirst.frc.team3328.robot.utilities.REVDigitBoard;
import org.usfirst.frc.team3328.robot.utilities.ShooterTalons;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox;
import org.usfirst.frc.team3328.robot.utilities.Tracking;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import states.StateMachine;

public class Robot extends IterativeRobot {
	Teleop telop;
	StateMachine auto;
	REVDigitBoard digit;  
	Controller xbox;
	PID pid;
	boolean autoActive = false;


	@Override
	public void robotInit() {
		xbox = new SteamWorksXbox(1);
		pid = new PID(1.5 ,0, 0);
		telop = new Teleop(
				new SteamWorksDriveSystem(
					new DriveEncoders(
						new Encoder(0,1),
						new Encoder(2,3)),
					new DriveTalons(
						new Talon(0),
						new Talon(1),
						new Talon(2),
						new Talon(3)),
					new Tracking(
						new NetworkTablesTargetProvider().getTarget(),
						new Relay(0),
						new PID(.5 ,.05 ,0),
						new PID(0, 0, 0)),
					new ADIS16448_IMU(),
					pid),
				new SteamWorksShooter(
					new Encoder(4,5),
					new ShooterTalons(
						new CANTalon(0),  
						new CANTalon(1)),
						new SteamWorksHotelLobby(
						new CANTalon(2))),
				new SteamWorksFeeder(
					new CANTalon(3)),
				new SteamWorksClimber(
					new Talon(4)),
				xbox, //util
				new SteamWorksXbox(0)); //drive
		auto = new StateMachine(20, telop);
		digit = new REVDigitBoard();
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
		if (xbox.getButtonRelease(Buttons.A)){
			autoActive = !autoActive;
		}
		if (xbox.getButtonPress(Buttons.LBUMP)){
			if (xbox.getButtonRelease(Buttons.X)){
				pid.adjustP(-.02);
				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
			}
			if (xbox.getButtonRelease(Buttons.Y)){
				pid.adjustI(-.01);
				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
			}
			if (xbox.getButtonRelease(Buttons.B)){
				pid.adjustD(-.02);
				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
			}
		}else{
			if (xbox.getButtonRelease(Buttons.X)){
				pid.adjustP(.02);
				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
			}
			if (xbox.getButtonRelease(Buttons.Y)){
				pid.adjustI(.01);
				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
			}
			if (xbox.getButtonRelease(Buttons.B)){
				pid.adjustD(.02);
				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
			}
		}
		if (autoActive){
			pid.reset();
			auto.run();
		}else{
			auto.reset();
		}
	}
}