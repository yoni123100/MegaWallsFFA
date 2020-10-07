package me.main.yoni.ClassSystem.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.main.yoni.Main;
import me.main.yoni.API.EffectUtils;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.SquidSplash;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Squid extends me.main.yoni.ClassSystem.Class{
	
	public ArrayList<String> cd = new ArrayList<>();

	@Override
	public String getName() {
		return "Squid";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("Splashing around, like a","Squid, because that what","Squids do. Splash.");
	}

	@Override
	public ClassType getType() {
		return ClassType.NORMAL;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.INK_SACK);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>();
		if(upgrade == 1){
			ItemStack sword = new ItemStack(Material.WOOD_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 2){
			ItemStack sword = new ItemStack(Material.STONE_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 3){
			ItemStack sword = new ItemStack(Material.STONE_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 1);
			enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 4){
			ItemStack sword = new ItemStack(Material.STONE_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(4, getName()));
			items.put(2, Utils.getPotionHeal(6, 1));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 1);
			enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 5){
			ItemStack sword = new ItemStack(Material.STONE_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(4, getName()));
			items.put(2, Utils.getPotionHeal(6, 1));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 1);
			enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 6){
			ItemStack sword = new ItemStack(Material.STONE_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(4, getName()));
			items.put(2, Utils.getPotionHeal(6, 2));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 1);
			enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 7){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(4, getName()));
			items.put(2, Utils.getPotionHeal(6, 2));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 1);
			enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 8){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(4, getName()));
			items.put(2, Utils.getPotionHeal(6, 3));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		if(upgrade == 9){
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(4, getName()));
			items.put(2, Utils.getPotionHeal(6, 3));
			items.put(3, Utils.getPotionSpeed(1));
			HashMap<Enchantment, Integer> enchant = new HashMap<>();
			enchant.put(Enchantment.DURABILITY, 1);
			enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots",null,enchant));
		}
		return items;
	}

	@Override
	public int getPrice() {
		return 450;
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
		return "Squid Splash";
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 35;
		for(int i = 0; i<upgrade; i++) {
			damage+=5;
		}
		return Arrays.asList("§7Deals 3 damages to all","§7enemies in a 5 block","§7radius. You are healed by","§c"+damage+"%§7 of the total damage","§7dealt. Max heal of §c8§7.");
	}

	@Override
	public int getXPPerHit() {
		return 16;
	}

	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}

	
	@EventHandler
	public void firstSkill(PlayerItemConsumeEvent e) {
		Player p = (Player)e.getPlayer();
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(e.getItem().getType() == Material.POTION) {
			double damage = 0.75;
			double blocks = 2.75;
			for(int i = 0; i < 9; i++) {
				damage += 0.25;
				blocks += 0.25;
			}
			for(Entity ent : p.getNearbyEntities(blocks, blocks, blocks)) {
				if(ent instanceof LivingEntity) {
					if(ent.equals(p)) continue;
					if(TeamManager.getTeamByPlayer(p) != null) {
						if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) continue;
					}
					int time = (int) (20 *  damage);
					((LivingEntity)ent).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,time,0));
					EffectUtils.createCircle(p.getLocation(), 20*3,true);
				}
			}
		}
	}
	
	@EventHandler
	public void damage(EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) return;
		final Player p = (Player)e.getEntity();
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(cd.contains(p.getName())) return;
		double damage = 4;
		double time = 360;
		for(int i = 0; i < 9; i++) {
			time -= 10;
			if(i == 2 || i == 4 || i == 5 || i == 7 || i == 8) {
				damage += 0.6;
				damage = Utils.round(damage, 2);
				continue;
			}
			damage += 0.5;
			damage = Utils.round(damage, 2);
		}
		cd.add(p.getName());
		p.setHealth(p.getHealth() + damage >= p.getMaxHealth() ? p.getMaxHealth() : p.getHealth() + damage);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				cd.remove(p.getName());
				
			}
		}, (long) (20*time));
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
//		if(Game.getGameState() != GameStage.STARTED) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(Utils.isUsingSword(p.getItemInHand()) == false) return;
		if(p.getLevel() < 100) return;
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		double damage = 35;
		for(int i = 0; i<upgrade.getCurrentUpgrade(); i++) {
			damage+=5;
		}
		SquidSplash.use(p,damage);
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}

}
