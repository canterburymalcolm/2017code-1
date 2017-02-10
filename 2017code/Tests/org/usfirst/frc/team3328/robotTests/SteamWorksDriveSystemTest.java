package org.usfirst.frc.team3328.robot;

import static org.junit.Assert.*;
import org.junit.Test;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksDriveSystem;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class SteamWorksDriveSystemTest {
	
	DriveEncoders encoders = new DriveEncoders();
	FakeController fakeCont1 = new FakeController();
	FakeController fakeCont2 = new FakeController();
	FakeTarget target = new FakeTarget();
	Tracking track = new Tracking(target, fakeCont2);
	FakeSpeedController fl = new FakeSpeedController();
	FakeSpeedController fr = new FakeSpeedController();
	FakeSpeedController bl = new FakeSpeedController();
	FakeSpeedController br = new FakeSpeedController();
	DriveTalons talons = new DriveTalons(fl, fr, bl, br);
	FakeADIS16448_IMU imu = new FakeADIS16448_IMU();
	SteamWorksDriveSystem drive = new SteamWorksDriveSystem(encoders, talons, fakeCont1, track);

	@Test
	public void controlledMove_yLargerThanX_rightMotorTurnsBackwards() {
		fakeCont1.setY(1);
		fakeCont1.setX(0);
		drive.controlledMove();
		assertEquals(1.0, (fr.speed),  0);
	}
	
	@Test
	public void controlledMove_xLargerThanY_rightMotorTurnsForwards() {
		fakeCont1.setY(1);
		fakeCont1.setX(0);
		drive.controlledMove();
		assertEquals(-1.0, (fr.speed),  0);
	}
	
	@Test
	public void restrain_LBumperIsPressed_restraintIncrementedBy1(){
		fakeCont1.setlBump(true);
		drive.restrain();
		assertTrue(drive.restraint == 2);
	}
	
	@Test
	public void autoAngle_currentIsLessThanDesired_rightMotorTurnsForwards(){
		drive.autoAngle(-90, 0);
		assertTrue(fr.speed < 0 && br.speed < 0);
	}
	
	@Test
	public void autoAngle_currentIsGreaterThanDesired_rightMotorTurnsForwards(){
		drive.autoAngle(0, 90);
		assertTrue(fr.speed < 0 && br.speed < 0);
	}
	
	@Test
	public void autoAngle_currentIsEqualToDesired_noMovement(){
		drive.autoAngle(0, 0);
		assertTrue(fr.speed == 0 && fl.speed == 0);
	}
	
}
	
	// Arrange Act Assert
	