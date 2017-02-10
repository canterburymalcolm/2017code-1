package org.usfirst.frc.team3328.robotTests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.usfirst.frc.team3328.robot.utilities.PID;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class TrackingTest {
	
	boolean state;
	double store;
	FakeController con = new FakeController();
	FakeTarget target = new FakeTarget();
	PID pid = new PID("TrackingTest", 0, 0, 0);
	Tracking track = new Tracking(target, pid);
	
	@Test
	public void isTracking_pixelWithinDeadZone_returnsFalse(){
		target.setPixel(track.getGoal());
		track.isTracking();
		assertTrue(track.getTracking());
	}
	
	@Test
	public void isTracking_lBumpReleased_togglesTracking(){
		state = track.getTracking();
		con.setlBump(true);
		assertTrue(track.getTracking() != state);
	}
	
	@Test
	public void track_pixelWithinDeadZone_trackSpeedSetToZero(){
		target.setPixel(track.getGoal());
		assertTrue(track.track() == 0);
	}
	
	@Test
	public void track_twoDifferentErrorLengths_largerErrorCorrectsFaster(){
		target.setPixel(300);
		store = track.track();
		target.setPixel(250);
		assertTrue(track.track() > store);
	}
	
	@Test
	public void track_inverseErrors_inverseCorrection(){
		target.setPixel(220);
		store = track.track();
		target.setPixel(420);
		assertTrue(track.track() + store == 0);
	}
	
	
	
}
