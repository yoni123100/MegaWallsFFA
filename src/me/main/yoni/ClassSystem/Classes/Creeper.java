package me.main.yoni.ClassSystem.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.main.yoni.Main;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.Class;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.Detonate;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Creeper extends Class{

	public static ArrayList<String> cd = new ArrayList<>();

	@Override
	public String getName() {
		return "Creeper";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Creeper class uses","explosion based powers to","win. Energy is gained by","hitting players in melee","range.");
	}

	@Override
	public ClassType getType() {
		return ClassType.NORMAL;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.TNT);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 2);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 2);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.WOOD_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 2);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 3);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 4);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 1));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 4);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8, 2));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 4);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		return items;
	}

	@Override
	public int getPrice() {
		return 800;
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
		return "Detonate";
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
		double damage = 1.25;
		for(int i = 0; i < upgrade; i++) {
			damage += 0.75;
		}
		return Arrays.asList("§7Detonate an explosion that","§7deals up to §c"+damage+"§7 damage to","§7nearby players. However, it","§7takes 3 seconds to","§7detonate.");
	}

	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}

	@EventHandler
	public void mining(BlockBreakEvent e) {
		MPlayer player = MPlayerManager.getMPlayer(e.getPlayer().getName());
		if(player.getCurrentClass() != this)return;
		if(e.getBlock().getType() == Material.COAL_ORE) {
			double damage = 7;
			for(int i = 0; i < 9; i++) {
				damage += 2;
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				e.getPlayer().getWorld().dropItem(e.getBlock().getLocation(), new ItemStack(Material.TNT));
			}
		}
	}

	@EventHandler
	public void seven(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			final Player p = (Player)e.getEntity();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			if(p.getHealth() - e.getDamage() <= 14) {
				if(cd.contains(p.getName())) return;
				double damage = 1.25;
				for(int i = 0; i < 9; i++) {
					damage += 0.75;
				}
				int time = (int) (20 * damage);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,time,1));
				cd.add(p.getName());
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

					@Override
					public void run() {
						cd.remove(p.getName());

					}
				}, 20 * 15);
			}
		}
	}

	@EventHandler
	public void death(PlayerDeathEvent e) {
		Player p = (Player)e.getEntity();
		MPlayer player = MPlayerManager.getMPlayer(p.getName());
		if(player.getCurrentClass() != this)return;
		double damage = 10;
		for(int i = 0; i < 9; i++) {
			damage += 10;
		}
		double change = damage/100;
		double random = new Random().nextDouble();
		if(random <= change) {
			org.bukkit.entity.Creeper b = (org.bukkit.entity.Creeper) p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
			b.setCustomName(ChatColor.AQUA + p.getName());
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
		Detonate.use(p, upgrade.getCurrentUpgrade());
		p.setLevel(0);
		p.setExp(0);
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}

}
