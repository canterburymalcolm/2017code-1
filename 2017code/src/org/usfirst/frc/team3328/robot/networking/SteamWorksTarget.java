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
	public void setPixel(double pixel){
		//System.out.println("set pixel to " + ang);
		//pixel.set(ang);
		this.pixel = pixel;
	}
	
	@Override
	public void setStatus(boolean status){
		this.status = status;
	}
	
	@Override
	public boolean getStatus(){
		return status;
	}
	
	@Override
	public void setTime(long time){
		this.time = time;
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
		System.out.printf("Pixel: %06.2f|Status: %b\n", getPixel(), getStatus());
	}
}
