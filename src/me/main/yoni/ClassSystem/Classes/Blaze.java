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
import me.main.yoni.ClassSystem.Abilities.ImmolatingBurst;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blaze extends Class{

	@Override
	public String getName() {
		return "Blaze";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Blaze class uses the","spirit of fire to enforce","flames.");
	}

	@Override
	public ClassType getType() {
		return ClassType.HERO;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.BLAZE_ROD);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>();
		if(upgrade == 1){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(1,getName()));
			items.put(2,new ItemStack(Material.ARROW,16));
		}
		if(upgrade == 2){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2,new ItemStack(Material.ARROW,22));
		}
		if(upgrade == 3){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2,new ItemStack(Material.ARROW,28));
		}
		if(upgrade == 4){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2,new ItemStack(Material.ARROW,32));
			ItemStack s = new ItemStack(Material.DIAMOND_SWORD);
			s.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItem(s, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FIRE, 1);
			enchats.put(Enchantment.DURABILITY, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
		}
		if(upgrade == 5){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2,getName()));
			items.put(2,new ItemStack(Material.ARROW,40));
			ItemStack s = new ItemStack(Material.DIAMOND_SWORD);
			s.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItem(s, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FIRE,2);
			enchats.put(Enchantment.DURABILITY, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
			items.put(5, Utils.getPotionSpeed(1));
		}
		if(upgrade == 6){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2,new ItemStack(Material.ARROW,46));
			ItemStack s = new ItemStack(Material.DIAMOND_SWORD);
			s.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItem(s, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FIRE,2);
			enchats.put(Enchantment.DURABILITY, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
			items.put(5, Utils.getPotionSpeed(1));
		}
		if(upgrade == 7){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2,new ItemStack(Material.ARROW,52));
			ItemStack s = new ItemStack(Material.DIAMOND_SWORD);
			s.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItem(s, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FIRE,2);
			enchats.put(Enchantment.DURABILITY, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
			items.put(5, Utils.getPotionHeal(8, 1));
			items.put(6, Utils.getPotionSpeed(2));
		}
		if(upgrade == 8){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2,new ItemStack(Material.ARROW,58));
			ItemStack s = new ItemStack(Material.DIAMOND_SWORD);
			s.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItem(s, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FIRE,2);
			enchats.put(Enchantment.PROTECTION_PROJECTILE,1);
			enchats.put(Enchantment.DURABILITY, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
			items.put(5, Utils.getPotionHeal(8, 2));
			items.put(6, Utils.getPotionSpeed(2));
		}
		if(upgrade == 9){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3,getName()));
			items.put(2,ItemStackCreator.createItem(new ItemStack(Material.ARROW,64), ChatColor.AQUA + getName() + " Arrows", 64));
			ItemStack s = new ItemStack(Material.DIAMOND_SWORD);
			s.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItem(s, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.PROTECTION_FIRE,2);
			enchats.put(Enchantment.PROTECTION_PROJECTILE,1);
			enchats.put(Enchantment.DURABILITY, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_LEGGINGS), 1, ChatColor.AQUA + getName() + " Leggings", null, enchats));
			items.put(5, Utils.getPotionHeal(8, 2));
			items.put(6, Utils.getPotionSpeed(2));
		}
		return items;
	}

	@Override
	public int getPrice() {
		return 15000;
	}

	@Override
	public int getXPPerHit() {
		if(getHitType() == HitType.TIMER) {
			return 5;
		}
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
		return "Immolating Burst";
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		float damage = (float) 1.9;
		for(int i = 0; i < upgrade; i++) {
			damage += 0.3;
			damage = (float) Utils.round(damage, 2);
		}
		return Arrays.asList("§7Immolate your enemies,","§7charge up your skill and","§7shoot 3 fireballs dealing","§c"+damage + "§7 damage each.");
	}

	
	@Override
	public HitType getHitType() {
		return HitType.TIMER;
	}
	
	@EventHandler
	public void arrows(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(e.getDamager() instanceof Arrow) {
			Arrow a = (Arrow)e.getDamager();
			if(a.getShooter() instanceof Player) {
				Player p = (Player)a.getShooter();
				if(p == e.getEntity()) return;
				if(TeamManager.getTeamByPlayer(p) != null) {
					if(TeamManager.getTeamByPlayer(p).getPlayers().contains(e.getEntity())) {
						return;
					}
				}
				MPlayer player = MPlayerManager.getMPlayer(p.getName());
				if(player.getCurrentClass() != this) return;
				double damage = 7.5;
				for(int i = 0; i < 9; i++) {
					damage += 2.5;
				}
				Entity noob = e.getEntity();
				double chance = damage/100;
				double random = new Random().nextDouble();
				if(random <= chance) {
					if(noob instanceof LivingEntity) {
						((LivingEntity)noob).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20*5,1));
						((LivingEntity)noob).setFireTicks(20*3);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void gay(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Projectile) {
			Projectile proj = (Projectile)e.getDamager();
			if(proj instanceof Fireball && proj.getShooter() instanceof org.bukkit.entity.Blaze){
				Fireball fb = (Fireball)proj;
				fb.setFireTicks(0);
				e.setCancelled(true);
				e.setDamage(2);
			}
		}
	}
	
	@EventHandler
	public void mining(BlockBreakEvent e) {
		MPlayer player = MPlayerManager.getMPlayer(e.getPlayer().getName());
		if(player.getCurrentClass() != this)return;
		if(e.getBlock().getType().name().contains("ORE")) {
			double damage = 43.75;
			for(int i = 0; i < 9; i++) {
				damage += 6.25;
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,20*5,1));
			}
		}
	}
	
	@EventHandler
	public void hit(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Fireball && e.getEntity().getShooter() instanceof Player) {
			Player p = (Player)e.getEntity().getShooter();
			if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() == this) {
				float damage = (float) 1.9;
				for(int i = 0; i < 9; i++) {
					damage += 0.3;
					damage = (float) Utils.round(damage, 2);
				}
				for(Player ps : Bukkit.getOnlinePlayers()) {
					ps.playSound(e.getEntity().getLocation(), Sound.EXPLODE, 2, 2);
				}
				for(Entity ent : e.getEntity().getNearbyEntities(3, 3, 3)) {
					if(ent == p) continue;
					if(ent instanceof LivingEntity && !ent.isDead()) {
						if(TeamManager.getTeamByPlayer(p) != null) {
							if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) {
								continue;
							}
						}
						Utils.realDamage(ent, p, damage);
					}
				}
				e.getEntity().remove();
			}
		}
	}
	
	@EventHandler
	public void hit2(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Fireball) {
			e.setCancelled(true);
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
		try {
			ImmolatingBurst.use(p, upgrade.getCurrentUpgrade());
		} catch (Exception e2) {
			// TODO: handle exception
		}
		p.setLevel(0);
		p.setExp(0);
	}
	
		@EventHandler
	    public void onFrameBrake(HangingBreakEvent e) {
	    if (e.getCause().toString().equals("ENTITY") || e.getCause().toString().equals("EXPLOSION")) {
	        e.setCancelled(true);
	    }
	}
	
	@EventHandler
	public void onInteract2(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("LEFT") == false) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(p.getItemInHand().getType() != Material.BOW) return;
		if(p.getLevel() < 100) return;
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		ImmolatingBurst.use(p, upgrade.getCurrentUpgrade());
		p.setLevel(0);
		p.setExp(0);
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}

}
