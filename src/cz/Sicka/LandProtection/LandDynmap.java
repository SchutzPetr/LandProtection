package cz.Sicka.LandProtection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerSet;

import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;

public class LandDynmap {
	private static final String DEF_INFOWINDOW_AREA = "<div class=\"infowindow\"><span style=\"font-weight:bold;font-size:200%\">Informace o oblasti</span><br /><span style=\"font-weight:bold;\">Jmeno oblasti&#720  </span><span style=\"color:#FF6600;font-weight:bold\">%regionname%</span><br /> <span style=\"font-weight:bold;\">Majitel&#720 </span><span style=\"font-weight:bold;color:#FF0066\">%playerowners%</span><br /><br /><span style=\"font-weight:bold;\">Flags&#720<br />------------------</span><br /><span style=\"font-weight:bold;\">%flags%</span></div>";
	private static final String DEF_INFOWINDOW_SUBZONE = "<div class=\"infowindow\"><span style=\"font-weight:bold;font-size:200%\">Informace o subzone</span><br /><span style=\"font-weight:bold;\">Jmeno subzony&#720  </span><span style=\"color:#FF6600;font-weight:bold\">%regionname%</span><br /> <span style=\"font-weight:bold;\">Jmeno hlavni oblasti&#720  </span><span style=\"color:#FF6600;font-weight:bold\">%mainregionname%</span><br /> <span style=\"font-weight:bold;\">Majitel&#720 </span><span style=\"font-weight:bold;color:#FF0066\">%playerowners%</span><br /><br /><span style=\"font-weight:bold;\">Flags&#720<br />------------------</span><br /><span style=\"font-weight:bold;\">%flags%</span></div>";
	private DynmapAPI api;
	private MarkerAPI markerapi;
	private MarkerSet set;
	private String infowindow_area;
	private List<String> flags = new ArrayList<String>();
	private Plugin dynmap;
	private boolean use3d;
	private String infowindow_subzone;
	private Map<Land, AreaMarker> landMarkerSet = new HashMap<Land, AreaMarker>();
	private Map<Subzone, AreaMarker> subzoneMarkerSet = new HashMap<Subzone, AreaMarker>();
	    
	public LandDynmap(LandProtection instance){
		this.infowindow_area = DEF_INFOWINDOW_AREA;
		this.infowindow_subzone = DEF_INFOWINDOW_SUBZONE;
		
		PluginManager pm = instance.getServer().getPluginManager();
        dynmap = pm.getPlugin("dynmap");
        api = (DynmapAPI)dynmap;
		
		markerapi = api.getMarkerAPI();
		if(markerapi == null) {
			LandProtection.logMessage(Level.SEVERE, "Error loading dynmap marker API!");
            return;
        }
		set = markerapi.getMarkerSet("residence.markerset");
		if(set == null){
			set = markerapi.createMarkerSet("residence.markerset", "LandProtection", null, false);
		}else{
			 set.setMarkerSetLabel("LandProtection");
		}
        if(set == null) {
        	LandProtection.logMessage(Level.SEVERE, "Error creating marker set");
            return;
        }
        set.setLayerPriority(10);
        set.setHideByDefault(false);
        show();
	}
	
	public void show(){
		for(Land land : LandProtection.getManager().getLands()){
			show(land);
			show(land.getSubzones());
		}
	}
	
	public void update(){
		for(Land land : LandProtection.getManager().getLands()){
			update(land);
			update(land.getSubzones());
		}
	}
	
	public void update(Collection<Subzone> subzones){
		for(Subzone sub : subzones){
			update(sub);
		}
	}
	
	public void update(Land land){
		this.subzoneMarkerSet.remove(land).deleteMarker();
		show(land);
	}
	
	public void update(Subzone subzone){
		this.subzoneMarkerSet.remove(subzone).deleteMarker();
		show(subzone);
	}
	
	public void removeFromDynmap(Subzone subzone){
		this.subzoneMarkerSet.remove(subzone).deleteMarker();
	}
	
	public void removeFromDynmap(Land land){
		this.landMarkerSet.remove(land).deleteMarker();
	}
	
	public void show(Land land){
		String id = land.getName(); 
		String label = land.getDisplayName(); 
		String world = land.getWorldName();
		double[] x = new double[4];
        double[] z = new double[4];
        
        String desc = formatInfoWindow(land);
        
        x[0] = land.getLowX(); z[0] = land.getLowZ();
        x[1] = land.getLowX(); z[1] = land.getHighZ()+1.0;
        x[2] = land.getHighX() + 1.0; z[2] = land.getHighZ()+1.0;
        x[3] = land.getHighX() + 1.0; z[3] = land.getLowZ();
        
		AreaMarker m = set.createAreaMarker(id, label, false, world, x, z, false);
		m.setDescription(desc);
		if(use3d) { /* If 3D? */
            m.setRangeY(land.getHighY() + 1.0, land.getLowY());
        }
		landMarkerSet.put(land, m);
	}
	
	public void show(Collection<Subzone> subzones){
		for(Subzone sub : subzones){
			show(sub);
		}
	}
	
	public void show(Subzone sub){
		String id = sub.getLand().getName() + "%" + sub.getName(); 
		String label = sub.getDisplayName();
		String world = sub.getLand().getWorldName();
		double[] x = new double[4];
        double[] z = new double[4];
        
        String desc = formatInfoWindow(sub);
        
        x[0] = sub.getLowX(); z[0] = sub.getLowZ();
        x[1] = sub.getLowX(); z[1] = sub.getHighZ()+1.0;
        x[2] = sub.getHighX() + 1.0; z[2] = sub.getHighZ()+1.0;
        x[3] = sub.getHighX() + 1.0; z[3] = sub.getLowZ();
		
		AreaMarker m = set.createAreaMarker(id, label, false, world, x, z, false);
		m.setDescription(desc);
		if(use3d) { /* If 3D? */
            m.setRangeY(sub.getHighY() + 1.0, sub.getLowY());
        }
		subzoneMarkerSet.put(sub, m);
	}
	
	private String formatInfoWindow(Subzone sub) {
    	String v = "<div class=\"regioninfo\">"+ infowindow_subzone +"</div>";
        v = v.replace("%mainregionname%", sub.getLand().getDisplayName());
    	v = v.replace("%regionname%", sub.getDisplayName());
        v = v.replace("%playerowners%", sub.getLand().getOwner().getName());
        String m = sub.getEnterMessage();
        v = v.replace("%entermsg%", (m!=null)?m:"" );
        m = sub.getLeaveMessage();
        v = v.replace("%leavemsg%", (m!=null)?m:"");
        Map<Flag, Boolean> p = sub.getLSFlags().getAreaFlags().getFlagsAndValues();
        
        for(Flag flg : p.keySet()){
        	if(p.get(flg)){
        		String input = flg.getName();
        		String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        		flags.add(output + ": <span style=\"color:green\">True</span><br>");
        	}else{
        		String input = flg.getName();
        		String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        		flags.add(output + ": <span style=\"color:red\">False</span><br>");
        	}
        }
        
        v = v.replace("%flags%", Flags(flags));
        flags.clear();
        return v;
    }
	
	private String formatInfoWindow(Land land) {
    	String v = "<div class=\"regioninfo\">"+ infowindow_area +"</div>";
        v = v.replace("%regionname%", land.getDisplayName());
        v = v.replace("%playerowners%", land.getOwner().getName());
        String m = land.getEnterMessage();
        v = v.replace("%entermsg%", (m!=null)?m:"" );
        m = land.getLeaveMessage();
        v = v.replace("%leavemsg%", (m!=null)?m:"");
        Map<Flag, Boolean> p = land.getLSFlags().getAreaFlags().getFlagsAndValues();
        
        for(Flag flg : p.keySet()){
        	if(p.get(flg)){
        		String input = flg.getName();
        		String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        		flags.add(output + ": <span style=\"color:green\">True</span><br>");
        	}else{
        		String input = flg.getName();
        		String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        		flags.add(output + ": <span style=\"color:red\">False</span><br>");
        	}
        }
        
        v = v.replace("%flags%", Flags(flags));
        flags.clear();
        return v;
    }
	
	private String Flags(List<String> args) {
    	String s = "";
		if(args == null){
			args = null;
		}else{
			for (int i = 0; i < args.size(); i++) {
				s = s + args.get(i) + " ";
			}
		}
		return s;
	}

}