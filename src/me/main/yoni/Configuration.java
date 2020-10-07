package me.main.yoni;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class Configuration {
	
	
	private String name, folder;
	private static List<Configuration> configs = new ArrayList<Configuration>();
	private File file;
	private FileConfiguration config;
	public static List<Configuration> getConfigs(){
		return configs;
	}
	public Configuration(String name, String folder){
		this.name = name;
		this.folder = folder;
		configs.add(this);
		create();
    }
	public Configuration(String name){
		this.name = name;
		this.folder = "null";
		configs.add(this);
		create();
    }
	
	
	public String getName(){
		return name.toLowerCase();
	}
	public static Configuration getConfig(String name, String folder){
		for(Configuration conf : Configuration.getConfigs()){
			if(!conf.getFolder().equalsIgnoreCase(folder)) continue;
			if(conf.getName().equalsIgnoreCase(name)) return conf;
		}
		    return null;
		
	}
	public static Configuration getConfig(String name){
		for(Configuration conf : Configuration.getConfigs()){
			if(!conf.getFolder().equalsIgnoreCase("null")) continue;
			if(conf.getName().equalsIgnoreCase(name)) return conf;
		}
		    return null;
		
	}
	public boolean isEmpty(){
		for(String s : config.getKeys(false))
			if(s == null) return true;
		return false;
	}
	public void saveConfig(){
		try {
			config.save(file);
		} catch (IOException e) {
		}
		
	}
	public String getFolder(){
	return folder;	
	}
	public void create(){
	     if (!Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder().exists())
	    	 Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder().mkdirs();
	     	if(folder.equalsIgnoreCase("null")){
	     		this.folder = "null";
            file = new File(Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder(), name.toLowerCase()+".yml");
            if (!file.exists())
                    try { file.createNewFile(); }
                catch (IOException e) { e.printStackTrace(); }
           
            config = YamlConfiguration.loadConfiguration(file);
            return;
	}
		this.folder = getFolder();
		try {
        File f = new File( Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder() + File.separator + folder);
        if(!f.exists()){
            f.mkdirs();}
            file = new File(Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder()+ File.separator + folder, name.toLowerCase()+".yml");
            if (!file.exists())
                    try { file.createNewFile(); }
                catch (IOException e) { e.printStackTrace(); }
           
            config = YamlConfiguration.loadConfiguration(file);
        
    } catch(SecurityException e) {
        // do something...
        return;
    }
	}
	public void reloadConfig(){
		saveConfig();
	     if (!Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder().exists())
	    	 Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder().mkdirs();
	     	if(folder.equalsIgnoreCase("null")){
	     		this.folder = "null";
            file = new File(Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder(), name.toLowerCase()+".yml");
            if (!file.exists())
                    try { file.createNewFile(); }
                catch (IOException e) { e.printStackTrace(); }
           
            config = YamlConfiguration.loadConfiguration(file);
            return;
	}
		this.folder = getFolder();
		try {
        File f = new File( Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder() + File.separator + folder);
        if(!f.exists()){
            f.mkdirs();}
            file = new File(Bukkit.getPluginManager().getPlugin("MegaWallsFFA").getDataFolder()+ File.separator + folder, name.toLowerCase()+".yml");
            if (!file.exists())
                    try { file.createNewFile(); }
                catch (IOException e) { e.printStackTrace(); }
           
            config = YamlConfiguration.loadConfiguration(file);
        
    } catch(SecurityException e) {
        // do something...
        return;
    }
	}
	
	public FileConfiguration getConfig(){
		return config;
	}
	  public static Location convertToLocation(String from) {
		    World w = Bukkit.getWorld(from.split(" ")[0]);
		    double x = Double.parseDouble(from.split(" ")[1]);
		    double y = Double.parseDouble(from.split(" ")[2]);
		    double z = Double.parseDouble(from.split(" ")[3]);
		    float yaw = Float.parseFloat(from.split(" ")[4]);
		    float pitch = Float.parseFloat(from.split(" ")[5]);
		    return new Location(w, x, y, z, yaw, pitch);
		  }
	  public static Location convertToLocationWithoutPitchAndYaw(String from) {
		    World w = Bukkit.getWorld(from.split(" ")[0]);
		    double x = Double.parseDouble(from.split(" ")[1]);
		    double y = Double.parseDouble(from.split(" ")[2]);
		    double z = Double.parseDouble(from.split(" ")[3]);
		    return new Location(w, x, y, z);
		  }
		  public static String convertLocation(Location loc) {
		    return loc.getWorld().getName() + 
		      " " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + loc.getYaw() + " " + loc.getPitch();
		  }
		  
		  public String parseItemStack(Material mat){
			  return mat.name().toLowerCase();
		  }
		  public ItemStack convertToItemStack(String mat){
			  return new ItemStack(Material.getMaterial(mat.toUpperCase()));
		  }
		  public Object get(String path) {
			    return this.config.get(path);
			  }

			  public String getString(String path) {
			    return this.config.getString(path);
			  }

			  public int getInt(String path) {
			    return this.config.getInt(path);
			  }

			  public boolean getBoolean(String path) {
			    return this.config.getBoolean(path);
			  }

			  public void createSection(String path) {
			    this.config.createSection(path);
			  }

			  public ConfigurationSection getConfigurationSection(String path) {
			    return this.config.getConfigurationSection(path);
			  }

			  public double getDouble(String path) {
			    return this.config.getDouble(path);
			  }


			  public List<?> getList(String path) {
			    return this.config.getList(path);
			  }

			  public boolean contains(String path) {
			    return this.config.contains(path);
			  }

			  public void set(String path, Object value) {
			    this.config.set(path, value);
			  }
			  public List<String> getStringList(String msg){
				  return config.getStringList(msg);
			  }
			  public File getFile(){return file;}
				public static List<String> scanFolder(String folder){
					List<String> results = new ArrayList<String>();


					File[] files =  new File("plugins/MegaWallsFFA/"+folder).listFiles();
					//If this pathname does not denote a directory, then listFiles() returns null. 
					if(files == null) return null;
					for (File file : files) {
					    if (file.isFile()) {
					    	String s = file.getName();
					    	s = s.replaceAll(".yml", "");
					        results.add(s);
					    }
					    
					}
					return results;
				}
}