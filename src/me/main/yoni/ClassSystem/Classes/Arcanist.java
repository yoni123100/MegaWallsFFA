package me.main.yoni.ClassSystem.Classes;

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
import me.main.yoni.ClassSystem.Abilities.Beam;
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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Arcanist extends Class{

	@Override
	public String getName() {
		return "Arcanist";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Arcanist class uses his","power to gain energy faster","than most");
	}

	@Override
	public ClassType getType() {
		return ClassType.HERO;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.BOOK);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
		}
		if(upgrade == 2){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2, Utils.getPotionHeal(8,1));
		}
		if(upgrade == 3){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2, Utils.getPotionHeal(8,1));
			items.put(3, Utils.getPotionSpeed(1));
		}
		if(upgrade == 4){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2, Utils.getPotionHeal(8,1));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 5){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2, Utils.getPotionHeal(8,1));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 6){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8,1));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 7){
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8,1));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 8){
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8,2));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 9){
			ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2, Utils.getPotionHeal(8,2));
			items.put(3, Utils.getPotionSpeed(2));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 2);
			enchats.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		return items;
	}


	//Ability - Beam

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
		if(!Main.playing.contains(p.getName())) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(Utils.isUsingSword(p.getItemInHand()) == false) return;
		if(p.getLevel() < 100) return;
		p.setExp(0);
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		if(upgrade.getCurrentUpgrade() == 1) {
			Beam.shoot(p, 6);
		}
		if(upgrade.getCurrentUpgrade() == 2) {
			Beam.shoot(p, 6.75);
		}
		if(upgrade.getCurrentUpgrade() == 3) {
			Beam.shoot(p, 7.5);
		}
		if(upgrade.getCurrentUpgrade() == 4) {
			Beam.shoot(p, 8.25);
		}
		if(upgrade.getCurrentUpgrade() == 5) {
			Beam.shoot(p, 9);
		}
		if(upgrade.getCurrentUpgrade() == 6) {
			Beam.shoot(p, 9.75);
		}
		if(upgrade.getCurrentUpgrade() == 7) {
			Beam.shoot(p, 10.5);
		}
		if(upgrade.getCurrentUpgrade() == 8) {
			Beam.shoot(p, 11.25);
		}
		if(upgrade.getCurrentUpgrade() == 9) {
			Beam.shoot(p, 12);
		}
		p.setLevel(0);
		p.setExp(0);
	}
	
	@EventHandler
	public void secondSkill(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player)e.getEntity();
		MPlayer player = MPlayerManager.getMPlayer(e.getEntity().getName());
		if(!Main.playing.contains(player.getPlayer().getPlayer().getName())) return;
		if(player.getCurrentClass() != this) return;
		double damage = 6.5;
		for(int i = 0; i < 9; i++) {
			damage += 1.5;
		}
		double chance = damage/100;
		double random = new Random().nextDouble();
		if(random <= chance) {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.playSound(e.getEntity().getLocation(), Sound.EXPLODE, 1, 1);
			}
			for(Entity ent : e.getEntity().getNearbyEntities(2, 2, 2)) {
				if(ent instanceof LivingEntity) {
					if(TeamManager.getTeamByPlayer(p) != null) {
						if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) {
							continue;
						}
					}
					((LivingEntity)ent).damage(2);
				}
			}
		}
	}
	
	@EventHandler
	public void firstSkill(PlayerDeathEvent e) {
		if(!Main.playing.contains(e.getEntity().getName())) return;
		if(!(e.getEntity().getKiller() instanceof Player)) return;
		Player killer = (Player)e.getEntity().getKiller();
		MPlayer player = MPlayerManager.getMPlayer(killer.getName());
		if(player.getCurrentClass() != this)return;
		double damage = 0.5;
		for(int i = 0; i<9; i++) {
			damage += 0.5;
		}
		e.getEntity().getKiller().addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*6,2));
		int time = (int) (20 * damage);
		e.getEntity().getKiller().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,time,1));
	}
	
	@EventHandler
	public void mining(BlockBreakEvent e) {
		if(!Main.playing.contains(e.getPlayer().getName())) return;
		MPlayer player = MPlayerManager.getMPlayer(e.getPlayer().getName());
		if(player.getCurrentClass() != this)return;
		if(e.getBlock().getType().name().contains("ORE")) {
			Utils.addLevel(player.getPlayer().getPlayer(), 10);
		}
	}

	@Override
	public int getPrice() {
		return 10000;
	}

	@Override
	public int getXPPerHit() {
		if(getHitType() == HitType.MELEE)
			return 35;
		return 0;
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
		return "Arcane Beam";
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 5.25;
		for(int i = 0; i < upgrade; i++) {
			damage += 0.75;
		}
		return Arrays.asList("§7Cast a beam that hits","§7player for §c"+damage+"§7 damage.");
	}


	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}

}
