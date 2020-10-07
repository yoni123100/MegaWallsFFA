package me.main.yoni.ClassSystem.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.main.yoni.Main;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.Leap;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Spider extends me.main.yoni.ClassSystem.Class {
	
	public static ArrayList<String> cd = new ArrayList<>();

	@Override
	public String getName() {
		return "Spider";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Spider class uses agile","paths for combat.");
	}

	@Override
	public ClassType getType() {
		return ClassType.NORMAL;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.WEB);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>();
		if(upgrade == 1){
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		sword.addEnchantment(Enchantment.DURABILITY, 2);
		items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
		items.put(1, Utils.getSteaks(1, getName()));
		items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null));
		}
		if(upgrade == 2){
			ItemStack sword = new ItemStack(Material.WOOD_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null));
		}
		if(upgrade == 3){
			ItemStack sword = new ItemStack(Material.STONE_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null));
		}
		if(upgrade == 4){
			ItemStack sword = new ItemStack(Material.STONE_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionSpeed(1));
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null));
		}
		if(upgrade == 5){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null));
		}
		if(upgrade == 6){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(2));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null));
		}
		if(upgrade == 7){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 3);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 8){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 2));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 3);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 9){
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 2));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 3);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		return items;
	}

	@Override
	public int getPrice() {
		return 900;
	}

	@Override
	public int getUpgradePrice(int level) {
		if(level == 1) {
			return 0;
		}
		if(level == 2) {
			return 50;
		}
		if(level == 3) {
			return 125;
		}
		if(level == 4) {
			return 300;
		}
		if(level == 5) {
			return 600;
		}
		if(level == 6) {
			return 3500;
		}
		if(level == 7) {
			return 6500;
		}
		if(level == 8) {
			return 8500;
		}
		if(level == 9) {
			return 14000;
		}
		return 14000;
	}

	@Override
	public String getAbilityName() {
		return "Leap";
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 2.75;
		for(int i = 0; i<upgrade; i++) {
			damage+=0.25;
		}
		return Arrays.asList("§7Leap forward into the air,","§7applying §cSlowness II","§7for 4 seconds to all","§7enemies within a §c"+damage+"","§7blocks upon landing. Gives","§7you Absorption I for §c5 §7seconds after casting.");
	}

	@Override
	public int getXPPerHit() {
		return 7;
	}

	@Override
	public HitType getHitType() {
		return HitType.TIMER;
	}
	
	@EventHandler
	public void ability(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		if(e.getCause() != DamageCause.FALL) return;
		Player p = (Player)e.getEntity();
		if(!Main.playing.contains(p.getName())) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(!cd.contains(p.getName())) return;
		cd.remove(p.getName());
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		double damage = 2.75;
		for(int i = 0; i<upgrade.getCurrentUpgrade(); i++) {
			damage+=0.25;
		}
		for(Entity ent : p.getNearbyEntities(damage, damage, damage)) {
			if(ent == p) continue;
			if(TeamManager.getTeamByPlayer(p) != null) { 
				if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) continue;
			}
			if(ent instanceof LivingEntity && !ent.isDead()) {
				((LivingEntity)ent).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*4,1));
			}
		}
	}
	
	@EventHandler
	public void firstSkill(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		if(e.getCause() != DamageCause.FALL) return;
		Player p = (Player)e.getEntity();
		if(!Main.playing.contains(p.getName())) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		double test = p.getHealth() - 4;
		if(p.getHealth() - e.getDamage() > test) return;
		if(p.getHealth() - e.getDamage() <= 0) return;
		double damage = 110;
		double blocks = 4;
		int max = 13;
		
		for(int i = 0; i < 9; i++) {
			max += 1;
			if(i == 8) {
				damage += 30;
				continue;
			}
			damage += 10;
		}
		double precent = damage/100;
		double d = e.getDamage() * precent / 2;
		if(d > max) {
			d = max;
		}
		for(Entity ent : p.getNearbyEntities(blocks, blocks, blocks)) {
			if(ent == p) continue;
			if(TeamManager.getTeamByPlayer(p) != null) { 
				if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) continue;
			}
			if(ent instanceof LivingEntity && !ent.isDead()) {
				Utils.realDamage(ent, p, d);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
		if(p.getItemInHand() == null) return;
		if(p.getItemInHand().getItemMeta() == null) return;
		if(p.getItemInHand().getItemMeta().getDisplayName() == null) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(Utils.isUsingSword(p.getItemInHand()) == false) return;
		if(p.getLevel() < 100) return;
		Leap.use(p);
		cd.add(p.getName());
		p.setLevel(0);
		p.setExp(0);
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}

}
