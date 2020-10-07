package me.main.yoni.ArenaSystem;

public class DuelManager {
	
	public enum DuelType{
		ONEVSONE,TEAM;
	}
	private DuelType type;
	
	public DuelManager(DuelType type) {
		this.type = type;
	}

}
