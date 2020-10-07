package me.main.yoni.ClassSystem.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.main.yoni.Main;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.BurningSoul;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Pigman extends me.main.yoni.ClassSystem.Class {
	
	public static ArrayList<String> cd = new ArrayList<>();

	@Override
	public String getName() {
		return "Pigman";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("Half man, half pig, half..","Oh wait! Feel the power of","pork!");
	}

	@Override
	public ClassType getType() {
		return ClassType.HERO;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.GRILLED_PORK);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getSteaks(1, getName()));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getSteaks(1, getName()));
			items.put(3, ItemStackCreator.createItem(new ItemStack(Material.GOLD_CHESTPLATE), ChatColor.AQUA + getName() + " Chestplate", 1));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getSteaks(1, getName()));
			items.put(3, ItemStackCreator.createItem(new ItemStack(Material.GOLD_CHESTPLATE), ChatColor.AQUA + getName() + " Chestplate", 1));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getSteaks(2, getName()));
			items.put(3, ItemStackCreator.createItem(new ItemStack(Material.IRON_CHESTPLATE), ChatColor.AQUA + getName() + " Chestplate", 1));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(1));
			items.put(3, Utils.getSteaks(2, getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(1));
			items.put(3, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(2));
			items.put(3, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(2));
			items.put(3, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
		}
		return items;
	}

	@Override
	public int getPrice() {
		return 15000;
	}

	@Override
	public int getUpgradePrice(int level) {
		if(level == 1) {
			return 0;
		}
		if(level == 2) {
			return 200;
		}
		if(level == 3) {
			return 500;
		}
		if(level == 4) {
			return 1200;
		}
		if(level == 5) {
			return 2400;
		}
		if(level == 6) {
			return 7000;
		}
		if(level == 7) {
			return 13000;
		}
		if(level == 8) {
			return 17000;
		}
		if(level == 9) {
			return 28000;
		}
		return 28000;
	}

	@Override
	public String getAbilityName() {
		return "Burning Soul";
	}

	@Override
	public int getXPPerHit() {
		if(getHitType() == HitType.MELEE) {
			return 10;
		}
		return 0;
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 0.875;
		double Sseconds = 0.875;
		int seconds = 3;
		for(int i = 0; i<upgrade; i++) {
			damage+=0.125;
			Sseconds+=0.125;
		}
		if(upgrade < 5) {
			seconds = 3;
		}
		if(upgrade < 9 && upgrade >= 5) {
			seconds = 4;
		}
		if(upgrade == 9) {
			seconds = 5;
		}
		return Arrays.asList("§7Summon a fire bubble that","§7deals §c"+damage+"§7 damage every","§7second for §c"+seconds+"§7 seconds","§7and gives Strength I to the","§7player for §c"+Sseconds+"§7 seconds.");
	}


	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}

	@EventHandler
	public void secondSkill(EntityDamageEvent e) {
		if(e.isCancelled()) return;
		if(e.getEntity() instanceof Player) {
			final Player p = (Player)e.getEntity();
			if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
			if(cd.contains(p.getName())) return;
			if(p.getHealth() <= 10) {
				double damage = 1.5;
				for(int i = 0; i < 9; i++) {
					damage += 0.5;
				}
				cd.add(p.getName());
				int time = (int) (20 * damage);
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,time,1));
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					
					@Override
					public void run() {
						cd.remove(p.getName());
						
					}
				}, 20 * 30);
			}
		}
	}
	
	@EventHandler
	public void firstSkill(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player)e.getDamager();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			double damage = 1.3;
			for(int i = 0; i < 9; i++) {
				if(i == 6) {
					damage += 0.6;
					damage = Utils.round(damage, 2);
					continue;
				}
				if(i == 7) {
					damage += 0.8;
					damage = Utils.round(damage, 2);
					continue;
				}
				damage += 0.7;
				damage = Utils.round(damage, 2);
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				for(Entity ent : p.getNearbyEntities(8, 8, 8)) {
					if(!(ent instanceof Player)) return;
					Player enp = (Player)ent;
					if(TeamManager.getTeamByPlayer(p) != null) {
						if(TeamManager.getTeamByPlayer(p).getPlayers().contains(enp)) {
							enp.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*8,0));
							if(enp.getHealth() < 20) {
								enp.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*8,0));
							}
						}
					} else {
						p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*8,0));
						if(p.getHealth() < 20) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*8,0));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(Utils.isUsingSword(p.getItemInHand()) == false) return;
		if(p.getLevel() < 100) return;
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		BurningSoul.use(p, upgrade.getCurrentUpgrade());
		p.setLevel(0);
		p.setExp(0);
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}
	
}
