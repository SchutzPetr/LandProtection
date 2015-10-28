package cz.Sicka.LandProtection.WorldArea;

import java.util.UUID;

import org.bukkit.World;

import cz.Sicka.Core.User.UManager;
import cz.Sicka.Core.User.User;
import cz.Sicka.Core.Utils.Replacer;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.Utils.Tasks.TimeBuildRefreshTask;

public class WorldArea extends WorldData{
	private TimedBuild build;
	private TimeBuildRefreshTask buildTask;
	
	public WorldArea(String worldName){
		super(worldName);
		if(this.isEnableBuildPerInterval()){
			build = new TimedBuild(this);
			buildTask = new TimeBuildRefreshTask(this, build);
		}
	}

	public WorldArea(World world){
		super(world.getName());
		if(this.isEnableBuildPerInterval()){
			build = new TimedBuild(this);
			buildTask = new TimeBuildRefreshTask(this, build);
		}
		
	}
	
	//TODO: moderator opravneni
	public boolean allowAction(User user, Flag flag){
		return allowAction(flag);
	}
	
	public boolean allowAction(UUID userUniqueId, Flag flag){
		if((flag == FlagManager.DESTROY || flag == FlagManager.PLACE) && build != null){
			boolean canbuild = build.canBuild(userUniqueId, true).booleanValue();
			if(!canbuild){
				UManager.getUser(userUniqueId).sendMessage("Bylo dosazeno limito na tezbu mimo pozemek. Limit bude obnoven za " 
			                          + Replacer.getDateByCurrentTimeMillis(buildTask.getTimeLeft(), "mm:ss"));
			}
			return canbuild;
		}else{
			return allowAction(flag);
		}
	}
	
	public boolean allowAction(Flag flag){
		Flag origFlag = flag;
		Boolean flagValue;
		while(true){
			flagValue = this.getFlags().getFlag(flag);
			if (flagValue != null) {
                return flagValue;
            }
			if(flag.getParent() != null){
				flag = flag.getParent();
			}else{
				return origFlag.getDefaultValue();
			}
		}
	}
}
