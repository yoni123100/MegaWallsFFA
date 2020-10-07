package me.main.yoni.ClassSystem.Classes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.Wrath;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Herobrine extends me.main.yoni.ClassSystem.Class{

	@Override
	public String getName() {
		return "Herobrine";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Herobrine class uses","supernatural abilities to","attack and destroy your","enemies.");
	}

	@Override
	public ClassType getType() {
		return ClassType.NORMAL;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.ENDER_PEARL);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(1, getName()));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(6, 1));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(6, 1));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(6, 1));
			items.put(3, Utils.getPotionSpeed(1));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(6, 1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchant.put(Enchantment.DURABILITY, 1);
			enchant.put(Enchantment.WATER_WORKER, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchant));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(6, 2));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchant.put(Enchantment.DURABILITY, 1);
			enchant.put(Enchantment.WATER_WORKER, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchant));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionHeal(6, 2));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchant.put(Enchantment.DURABILITY, 1);
			enchant.put(Enchantment.WATER_WORKER, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchant));
		}
		return items;
	}

	@Override
	public int getPrice() {
		return 600;
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
		return "Wrath";
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
		double damage = -0.5;
		for(int i = 0; i<upgrade; i++) {
			damage+=0.5;
		}
		return Arrays.asList("§7Unleash the wrath of","§7Herobrine striking all","§7nearby enemies for §c"+damage+"","§7damage.");
	}

	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}

	public static double getChestPercents(int upgrade) {
		double damage = -1;
		for(int i = 0; i < upgrade; i++) {
			if(i == 4 || i == 8) {
				damage += 12;
				continue;
			}
			damage += 11;
		}
		return damage;
	}
	
	@EventHandler
	public void kill(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
			Player p = (Player)e.getEntity().getKiller();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			double damage = 1.5;
			for(int i = 0; i < 9; i++) {
				damage += 0.5;
			}
			int time = (int) (20 * damage);
			p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,time,0));
		}
	}
	
	@EventHandler
	public void secondSkill(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player)e.getDamager();
			if(TeamManager.getTeamByPlayer(p) != null) {
				if(TeamManager.getTeamByPlayer(p).getPlayers().contains(e.getEntity())) return;
			}
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			double damage = 21;
			for(int i = 0; i < 9; i++) {
				damage += 6;
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20,1));
			}
		}
	}
	
	//Mining is in the ChestPercent Class.
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(Utils.isUsingSword(p.getItemInHand()) == false) return;
		if(p.getLevel() < 100) return;
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		Wrath.use(p, upgrade.getCurrentUpgrade());
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}

}
