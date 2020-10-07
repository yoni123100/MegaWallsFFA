package me.main.yoni.ClassSystem.Classes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.main.yoni.Main;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Timeformatter;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.Class;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.Teleport;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Enderman extends Class{

	@Override
	public String getName() {
		return "Enderman";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Enderman class has","special teleportation","powers and endurance.");
	}

	@Override
	public ClassType getType() {
		return ClassType.NORMAL;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.ENDER_CHEST);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FALL, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FALL, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FALL, 2);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FALL, 2);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FALL, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FALL, 3);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FALL, 4);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FALL, 4);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 2));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FALL, 4);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
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
		return "Enderman Teleport";
	}

	@Override
	public int getXPPerHit() {
		if(getHitType() == HitType.MELEE) {
			return 20;
		}
		return 0;
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 10;
		for(int i = 1; i < upgrade; i++) {
			if(damage == 10) {
				damage++;
				continue;
			}
			damage += 2;
		}
		int speed = 0;
		if(upgrade < 4) {
			speed = 1;
		}
		if(upgrade < 9 && upgrade > 3) {
			speed = 2;
		}
		if(upgrade == 9) {
			speed = 3;
		}
		return Arrays.asList("§7Teleports you §c"+damage+"","§7blocks towards the nearest","§7player and gain Speed","§c"+Timeformatter.formatForUpgradeLvls(speed)+"§7 for 5 seconds.");
	}

	
	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}

	
	@EventHandler
	public void kb(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			final Player p = (Player)e.getEntity();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			double damage = 7;
			for(int i = 0; i < 9; i++) {
				if(i == 8) {
					damage += 4;
					continue;
				}
				damage += 3;
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					
					@Override
					public void run() {
						p.setVelocity(new Vector(0, 0, 0));
						
					}
				}, 1);
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		final Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(Utils.isUsingSword(p.getItemInHand()) == false) return;
		if(p.getLevel() < 100) return;
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		Teleport.use(p, upgrade.getCurrentUpgrade());
	}

	@Override
	public String getAbilityReadyName() {
		return "Teleport";
	}
	
}
