package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.Climber;
import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.Feeder;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class Teleop {
	
	DriveSystem drive;
	Shooter shoot;
	Feeder feed;
	Climber climb;
	Controller utilXbox;
	Controller driveXbox;
	Tracking track = drive.getTrack();
	
	public Teleop(DriveSystem drive, Shooter shoot, Feeder feed, Climber climb,
				Controller utilXbox, Controller driveXbox){
		this.drive = drive;
		this.shoot = shoot;
		this.feed = feed;
		this.climb = climb;
		this.utilXbox = utilXbox;
		this.driveXbox = driveXbox;
	}
	
	public void run(){
		//tracking
		if (utilXbox.getButtonRelease(Buttons.LBUMP)){
			track.toggleTracking();
		}
		//driving
		if (driveXbox.getButtonRelease(Buttons.LBUMP)){
			drive.restrain();
		}
		drive.controlledMove(driveXbox.getX(), driveXbox.getY());
		//shooting
		if (utilXbox.getButtonRelease(Buttons.RBUMP)){
			shoot.toggleShooter();
		}
		shoot.shooterControl();
		//feeding
		if (utilXbox.getButtonRelease(Buttons.A) || utilXbox.getButtonRelease(Buttons.B)){
			feed.controlFeeder();
		}
		//climbing
		climb.controlClimber(utilXbox.getX());
	}
	
	public DriveSystem getDrive(){
		return drive;
	}
	
	public Shooter getShooter(){
		return shoot;
	}
	
	public Feeder getFeeder(){
		return feed;
	}
	
	public Climber getClimber(){
		return climb;
	}
	
	
	
	
	
	
	
	
	
}
