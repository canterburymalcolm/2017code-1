package org.usfirst.frc.team3328.robot.networking;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class Listener implements Runnable, ITableListener {
	
	SpeedController talon;
	Thread listener;
	Target target;
	NetworkTable table;
	double angle = 0;
	double distance = 0;
	
	public Listener(){
	}
	
	public Listener(String name, Target object){
		listener = new Thread(this, name);
		target = object;
		NetworkTable.shutdown();
		NetworkTable.setClientMode();
		NetworkTable.initialize();
		NetworkTable.setIPAddress("10.33.28.4");
		table = NetworkTable.getTable("JetsonData");
		table.addTableListener(this);
		System.out.printf("Created thread %s, %s, key: %s\n", name, table.isConnected(), table.getKeys().toString());
		listener.start();
	}
	
	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		target.setStatus(isNew);
		target.setTime(System.nanoTime());
		if (key.equals("pixels")){
			System.out.printf("pixel: %06.2f\n", table.getNumber("pixels", 0.0));
			target.setPixel(table.getNumber("pixels", 0.0)); 
		}
	}

	public void run(){
		for(;;){
			try{
				Thread.sleep(500);
			}catch(Exception e){
				
			}
		}
	}
}
