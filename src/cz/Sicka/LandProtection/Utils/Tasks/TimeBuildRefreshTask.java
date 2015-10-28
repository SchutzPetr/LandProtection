package cz.Sicka.LandProtection.Utils.Tasks;

import org.bukkit.Bukkit;

import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.WorldArea.TimedBuild;
import cz.Sicka.LandProtection.WorldArea.WorldArea;

public class TimeBuildRefreshTask {
	private int taskid;
	private TimedBuild build;
	
	long startTime;
	private long interval = 1;
	
	public TimeBuildRefreshTask(WorldArea area, TimedBuild build){
		this.interval = area.getBuildPerInterval_Interval();
		this.build = build;
		runTask();
	}
	
	public void runTask(){
		taskid = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(LandProtection.getInstance(), new Runnable(){

			@Override
			public void run(){
				startTime = System.currentTimeMillis();
				build.refresh();
			}
		}, 0L, interval*60L*20L);
	}
	
	public TimedBuild getTimedBuild() {
		return build;
	}

	public int getTaskID() {
		return taskid;
	}
	
	public void cancelTask(){
		Bukkit.getServer().getScheduler().cancelTask(taskid);
	}
	
	public long getTimeLeft(){
		return (startTime + interval*60000) - System.currentTimeMillis();
	}

}
