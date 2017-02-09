package org.usfirst.frc.team3328.robot.networking;


public class SteamWorksTarget implements Target {
	//private AtomicReference<Double> pixel; 
	private double pixel;
	private long lastTime = 0, time = 0;
	private boolean status = false;
	
	@Override
	public double getPixel(){
		//return pixel.get();
		return pixel;
	}
	
	@Override
	public void setPixel(double ang){
		//System.out.println("set pixel to " + ang);
		//pixel.set(ang);
		pixel = ang;
	}
	
	@Override
	public void setStatus(boolean stat){
		status = stat;
	}
	
	@Override
	public boolean getStatus(){
		return status;
	}
	
	@Override
	public void setTime(long stamp){
		time = stamp;
	}
	
	@Override
	public boolean isNew(){
		if (time != lastTime){
			lastTime = time;
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public void printValues(){
		System.out.printf("Pixel: %06.2f\n", getPixel());
	}
}
