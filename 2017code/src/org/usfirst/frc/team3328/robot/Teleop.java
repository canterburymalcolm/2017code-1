package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.Arm;
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
	Arm arm;
	Tracking track;
	
	public Teleop(DriveSystem drive, Shooter shoot, Feeder feed, Climber climb,
				Arm arm, Controller utilXbox, Controller driveXbox){
		this.drive = drive;
		this.shoot = shoot;
		this.feed = feed;
		this.climb = climb;
		this.utilXbox = utilXbox;
		this.driveXbox = driveXbox;
		this.arm = arm;
		this.track = this.drive.getTrack();
	}
	
	public void run(){
		//tracking
		if (driveXbox.getButtonRelease(Buttons.A)){
			track.toggleTracking();
		}
		//driving
		if (driveXbox.getButtonRelease(Buttons.LBUMP)){
			drive.upRestraint();
		}
		if (driveXbox.getButtonRelease(Buttons.RBUMP)){
			drive.downRestraint();
		}
		drive.controlledMove(driveXbox.getX(), driveXbox.getY());
		//shooting
		if (utilXbox.getButtonRelease(Buttons.RBUMP)){
			if (shoot.isShooting()){
				shoot.stopShoot();
			}else{
				shoot.startShoot();
			}
		}
		if (utilXbox.getButtonRelease(Buttons.X) || utilXbox.getButtonRelease(Buttons.Y)){
			if (shoot.isLoading()){
				shoot.stopLoad();
			}else{
				shoot.startLoad();
			}
		}
		//feeding
		if (utilXbox.getButtonRelease(Buttons.A)){
			feed.controlFeeder();
		}
		//Extending
		if(utilXbox.getButtonRelease(Buttons.B)){
			if (arm.isExtended()){
				arm.rectract();
			}else{
				arm.extend();
			}
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
