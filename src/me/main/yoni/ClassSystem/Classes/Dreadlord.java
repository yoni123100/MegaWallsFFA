package me.main.yoni.ClassSystem.Classes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.ShadowBurst;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Dreadlord extends me.main.yoni.ClassSystem.Class {

	public static HashMap<Entity, Integer> hits = new HashMap<>();
	
	@Override
	public String getName() {
		return "Dreadlord";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Dreadlord class uses","explosive abilities to","attack and steal health.");
	}

	@Override
	public ClassType getType() {
		return ClassType.HERO;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.SKULL_ITEM,1,(byte)1);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(2));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(2));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 2));
			items.put(3, Utils.getPotionSpeed(2));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 2));
			items.put(3, Utils.getPotionSpeed(2));
			items.put(4, ItemStackCreator.createItem(new ItemStack(Material.DIAMOND_HELMET), ChatColor.AQUA + getName() + " Helmet", 1));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			Sword.addEnchantment(Enchantment.DAMAGE_UNDEAD, 1);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 2));
			items.put(3, Utils.getPotionSpeed(2));
			ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
			helmet.addUnsafeEnchantment(Enchantment.PROTECTION_FIRE, 1);
			helmet.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 2);
			items.put(4, ItemStackCreator.createItem(helmet, ChatColor.AQUA + getName() + " Helmet", 1));
		}
		return items;
	}

	@Override
	public int getPrice() {
		return 10000;
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
		return "Shadow Burst";
	}

	@Override
	public int getXPPerHit() {
		if(getHitType() == HitType.MELEE) {
			return 12;
		}
		return 0;
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 2.25;
		for(int i = 0; i < upgrade; i++) {
			damage+=0.75;
		}
		return Arrays.asList("§7Fire three wither heads at","§7once dealing up to §c"+damage+"","§7damage per head.");
	}

	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}
	
	@SuppressWarnings("unused")
	@EventHandler
	public void hit(ProjectileHitEvent e) throws InterruptedException {
		if(e.getEntity() instanceof WitherSkull) {
			WitherSkull ws = (WitherSkull)e.getEntity();
			if(!(ws.getShooter() instanceof Player)) return;
			Player p = (Player)ws.getShooter();
			if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() == this) {
				Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
				double damage = 2.25;
				for(int i = 0; i < upgrade.getCurrentUpgrade(); i++) {
					damage += 0.75;
				}
				for(Player ps : Bukkit.getOnlinePlayers()) {
					ps.playSound(e.getEntity().getLocation(), Sound.EXPLODE, 5, 5);
				}
				for(Entity ent : ws.getNearbyEntities(2.5, 2.5, 2.5)) {
					if(!(ent instanceof Player)) continue;
					if(ent == p) continue;
					Player enp = (Player)ent;
					if(TeamManager.getTeamByPlayer(p) != null) {
						if(TeamManager.getTeamByPlayer(p).getPlayers().contains(enp)) {
							continue;
						}
					}
					Utils.realDamage(enp, p, 4);
				}
			}
		}
	}
	
	@EventHandler
	public void kill(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
			Player p = (Player)e.getEntity().getKiller();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			double regen = 1.5;
			double strength = 0.5;
			for(int i = 0; i < 9; i++) {
				regen += 0.5;
				strength += 0.5;
			}
			int time = (int) (20 * regen);
			int time2 = (int) (20 * strength);
			p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,time,0));
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,time2,0));
		}
	}
	
	@EventHandler
	public void mining(BlockBreakEvent e) {
		MPlayer player = MPlayerManager.getMPlayer(e.getPlayer().getName());
		if(player.getCurrentClass() != this)return;
		if(e.getBlock().getType() == Material.IRON_ORE) {
			double damage = 16;
			for(int i = 0; i < 9; i++) {
				if(i == 3 || i == 6 || i == 8) {
					damage += 10;
					continue;
				}
				damage+=9;
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				e.getBlock().setType(Material.AIR);
				e.getPlayer().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));	
			}
		}
	}
	
	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p = (Player)e.getDamager();
			if(TeamManager.getTeamByPlayer(p) != null) {
				if(TeamManager.getTeamByPlayer(p).getPlayers().contains(e.getEntity())) return;
			}
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			float damage = (float) 10.37;
			for(int i = 0; i < 9; i++) {
				if(i == 2 || i == 4 || i == 6 || i == 8) {
					damage += 1.62;
					continue;
				}
				damage += 1.63;
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				p.setHealth(p.getHealth() + 1 >= p.getMaxHealth() ? p.getMaxHealth() : p.getHealth() + 1);
				p.setFoodLevel(p.getFoodLevel() + 6 >= 20 ? 20 : p.getFoodLevel() + 6);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) throws InterruptedException{
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
//		if(Game.getGameState() != GameStage.STARTED) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(Utils.isUsingSword(p.getItemInHand()) == false) return;
		if(p.getLevel() < 100) return;
		ShadowBurst.use(p);
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}
	
}
