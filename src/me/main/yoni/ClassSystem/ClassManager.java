package me.main.yoni.ClassSystem;

import java.util.ArrayList;
import java.util.List;

import me.main.yoni.Main;
import me.main.yoni.ClassSystem.Classes.Arcanist;
import me.main.yoni.ClassSystem.Classes.Blaze;
import me.main.yoni.ClassSystem.Classes.Creeper;
import me.main.yoni.ClassSystem.Classes.Dreadlord;
import me.main.yoni.ClassSystem.Classes.Enderman;
import me.main.yoni.ClassSystem.Classes.Golem;
import me.main.yoni.ClassSystem.Classes.Herobrine;
import me.main.yoni.ClassSystem.Classes.Hunter;
import me.main.yoni.ClassSystem.Classes.Pigman;
import me.main.yoni.ClassSystem.Classes.Pirate;
import me.main.yoni.ClassSystem.Classes.Shaman;
import me.main.yoni.ClassSystem.Classes.Skeleton;
import me.main.yoni.ClassSystem.Classes.Spider;
import me.main.yoni.ClassSystem.Classes.Squid;
import me.main.yoni.ClassSystem.Classes.Zombie;

import org.bukkit.Bukkit;

public class ClassManager {

	private static ArrayList<Class> classes = new ArrayList<Class>();
	
	private static void addClass(Class megawallsClass){
		if(classes.contains(megawallsClass)) return;
		classes.add(megawallsClass);
	}
	
	private static void clearClasses(){
		classes.clear();
	}
	
	public static void registerClasses(){
		clearClasses();
		addClass(new Skeleton());
		addClass(new Zombie());
		addClass(new Creeper());
		addClass(new Enderman());
		addClass(new Herobrine());
		addClass(new Spider());
		addClass(new Squid());
		addClass(new Dreadlord());
		addClass(new Shaman());
		addClass(new Arcanist());
		addClass(new Golem());
		addClass(new Pigman());
		addClass(new Hunter());
		addClass(new Pirate());
		addClass(new Blaze());
		for(Class mwClass : classes)
			Bukkit.getPluginManager().registerEvents(mwClass, Main.getPlugin());
	}
	
	public static ArrayList<Class> getClasses(){
		return classes;
	}
	
	public static Class getClass(String name){
		for(Class mwClass : classes){
			if(mwClass.getName().equalsIgnoreCase(name)){
				return mwClass;
			}
		}
		return null;
	}
	
}
