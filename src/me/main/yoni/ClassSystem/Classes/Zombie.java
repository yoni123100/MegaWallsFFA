package me.main.yoni.ClassSystem.Classes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.Class;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.Heal;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Zombie extends Class{

	@Override
	public String getName() {
		return "Zombie";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Zombie class focuses on","defensive gameplay and","boosts.");
	}

	@Override
	public ClassType getType() {
		return ClassType.NORMAL;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.ROTTEN_FLESH);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_PROJECTILE, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null,enchats));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_PROJECTILE, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null,enchats));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_PROJECTILE, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null,enchats));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_PROJECTILE, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null,enchats));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null,enchats));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null,enchats));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(10, 1));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null,enchats));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(10, 1));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null,enchats));
		}
		return items;
	}

	@Override
	public int getPrice() {
		return 0;
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
		return "Circle of Healing";
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 1.5;
		for(int i = 0; i<upgrade; i++) {
			damage+=0.5;
		}
		return Arrays.asList("§7Heal yourself §c"+damage+"§7 and nearby","§7friendly player for 1/2 of that.");
	}

	@Override
	public int getXPPerHit() {
		return 12;
	}

	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}
	
	@EventHandler
	public void firstSkill(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
			float damage = (float) 6.87;
			for(int i = 0; i < 9; i++) {
				if(i == 2 || i == 4 || i == 6 || i == 8) {
					damage += 3.12;
					damage = (float) Utils.round(damage, 2);
					continue;
				}
				damage += 3.13;
				damage = (float) Utils.round(damage, 2);
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20,0));
			}
		}
	}
	
	@EventHandler
	public void secondSkill(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
			double damage = 3;
			for(int i = 0; i < 9; i++) {
				if(i == 8) {
					damage += 3;
					continue;
				}
				damage += 2;
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*3, 0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*3, 0));
			}
		}
	}
	
	@EventHandler
	public void miningSkill(BlockBreakEvent e) {
		if(MPlayerManager.getMPlayer(e.getPlayer().getName()).getCurrentClass() != this) return;
		Player p = (Player)e.getPlayer();
		double damage = 1.25;
		for(int i = 0; i < 9; i++) {
			damage += 3.75;
		}
		double random = new Random().nextInt(101);
		if(random <= damage) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,20*3+10,0));
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
		Heal.use(p, upgrade.getCurrentUpgrade());
		p.setLevel(0);
		p.setExp(0);
	}

	@Override
	public String getAbilityReadyName() {
		return "Heal";
	}

}
