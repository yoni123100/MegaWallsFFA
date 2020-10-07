package me.main.yoni.ClassSystem.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.main.yoni.Main;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.ParticleEffect;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.Tornado;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Shaman extends me.main.yoni.ClassSystem.Class{

	@Override
	public String getName() {
		return "Shaman";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Shaman class uses","spiritual abilities to","attack and defend.");
	}

	@Override
	public ClassType getType() {
		return ClassType.HERO;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.ENCHANTMENT_TABLE);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getSteaks(2, getName()));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(1));
			items.put(3, Utils.getSteaks(2, getName()));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(1));
			items.put(3, Utils.getSteaks(2, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_FALL, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(2));
			items.put(3, Utils.getSteaks(2, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_FALL, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(2));
			items.put(3, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_FALL, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(2));
			items.put(3, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_FALL, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 2));
			items.put(2, Utils.getPotionSpeed(2));
			items.put(3, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_FALL, 2);
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.DIAMOND_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 2));
			items.put(2, Utils.getPotionSpeed(2));
			items.put(3, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_FALL, 2);
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
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
		return "Tornado";
	}

	@Override
	public int getXPPerHit() {
		if(getHitType() == HitType.MELEE) {
			return 8;
		}
		return 0;
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 5.5;
		for(int i = 0; i<upgrade; i++) {
			damage+=0.5;
		}
		return Arrays.asList("§7Summons a destructive","§7tornado that will send","§7blocks flying and causing","§c"+damage+"§7 damage to players in its","§7path every second.");
	}


	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}

	@EventHandler
	public void firstSkill(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player)e.getDamager();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			double damage = 1.5;
			for(int i = 0; i < 9; i++) {
				damage += 0.5;
			}
			double chance = damage/100;
			double random = new Random().nextDouble();
			int time = (int) (20*damage);
			if(random <= chance) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,time,1));
				if(e.getEntity() instanceof LivingEntity && !e.getEntity().isDead()) {
					((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,time,0));
				}
			}
		}
	}

	@EventHandler
	public void secondSkill(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			double damage = 4.37;
			for(int i = 0; i < 9; i++) {
				if(i == 2 || i == 4 || i == 6 || i == 8) {
					damage += 0.62;
					damage = Utils.round(damage, 2);
					continue;
				}
				damage += 0.63;
				damage = Utils.round(damage, 2);
			}
			int time = (int) (20*damage);
			double chance = damage/100;
			double random = new Random().nextDouble();
			if(random <= chance) {
				final Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
				wolf.setCustomName(p.getName() + "'s wolf");
				wolf.setCustomNameVisible(true);
				wolf.setCanPickupItems(false);
				wolf.setTamed(true);
				wolf.setAdult();
				wolf.setSitting(false);
				wolf.setOwner(p);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

					@Override
					public void run() {
						if(wolf != null || !wolf.isDead()) {
							wolf.remove();
						}
					}
				}, time);
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
		Tornado.use(p, upgrade.getCurrentUpgrade());
		p.setLevel(0);
		p.setExp(0);
	}

	public static void createTornado(final Player player) {
		new BukkitRunnable() {
			int i = 0;
			Location loc = player.getLocation();
			public void run() {
				Location l = loc.add(0, 0, 0);
				Location t = l.clone().add(0, 0, 0);
				double r = .45 * (2 * (2.35 / 5));
				for (double y = 0; y < 5; y += .375) {
					double fr = r * y;
					if (fr > 2) {
						fr = 2;
					}
					for (Vector v : createCircle(y, fr)) {
						ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 0, 1, t.add(v), 100);
						t.subtract(v);
					}
				}
				i++;
				if(i >= 20) cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 4, 4);
	}

	private static ArrayList<Vector> createCircle(double y, double radius) {
		double amount = radius * 64;
		double inc = (2 * Math.PI) / amount;
		ArrayList<Vector> vecs = new ArrayList<Vector>();
		for (int i = 0; i < amount; i++) {
			double angle = i * inc;
			double x = radius * Math.cos(angle);
			double z = radius * Math.sin(angle);
			Vector v = new Vector(x, y, z);
			vecs.add(v);
		}
		return vecs;
	}
	/*
	 * public static void createTornado(Player player) {
	    new BukkitRunnable() {
		    double radius1 = 0.01;
		    double radius2 = 0.01;
		    int i = 0;
		    Location loc1 = player.getLocation();
		    Location loc = player.getLocation();
		    public void run() {
				 for(double y = 0; y <= 5; y += 0.05) {
				        double x = radius1 * Math.cos(y+i);
				        double z = radius1 * Math.sin(y+i);
				        Location loc2 = loc1.add(x, y, z);
				        ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 0, 1, loc2, 100);
			            loc1.subtract(x, y, z);
			            if(radius1 < 1)
			            radius1 += 0.005;
				 }
				 for(double y = 0; y <= 5; y += 0.05) {
				        double x = radius2 * Math.cos(y-i);
				        double z = radius2 * Math.sin(y-i);
				        Location loc2 = loc.add(x, y, z);
				        ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 0, 1, loc2, 100);
			            loc2.subtract(x, y, z);
			            if(radius2 < 1)
			            radius2 += 0.005;
				 }
				 i++;
				 if(i >= 20) cancel();
		    }
		}.runTaskTimer(Main.getPlugin(), 4, 4);
	}
	 */

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}
}