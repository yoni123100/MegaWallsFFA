package me.main.yoni.ClassSystem.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.main.yoni.Main;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.ExplosiveArrow;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Skeleton extends me.main.yoni.ClassSystem.Class{

	@Override
	public String getName() {
		return "Skeleton";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Skeleton class makes","excellent use of ranged","abilities and weapons.");
	}

	@Override
	public ClassType getType() {
		return ClassType.NORMAL;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.BONE);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>();
		if(upgrade == 1){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2,ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.AQUA + getName() + " Arrows", 30));
		}
		if(upgrade == 2){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2,ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.AQUA + getName() + " Arrows", 35));
		}
		if(upgrade == 3){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2,ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.AQUA + getName() + " Arrows", 40));
		}
		if(upgrade == 4){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getSteaks(3, getName()));
			items.put(3,ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.AQUA + getName() + " Arrows", 45));
		}
		if(upgrade == 5){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(1));
			items.put(3, Utils.getSteaks(3, getName()));
			items.put(4,ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.AQUA + getName() + " Arrows", 50));
		}
		if(upgrade == 6){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(2));
			items.put(3, Utils.getSteaks(3, getName()));
			items.put(4,ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.AQUA + getName() + " Arrows", 55));
		}
		if(upgrade == 7){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getPotionHeal(8, 1));
			items.put(2, Utils.getPotionSpeed(2));
			items.put(3, Utils.getSteaks(3, getName()));
			items.put(4,ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.AQUA + getName() + " Arrows", 60));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_PROJECTILE, 2);
			items.put(5, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
		}
		if(upgrade == 8){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
			sword.addEnchantment(Enchantment.DURABILITY, 1);
			ItemStack axe = new ItemStack(Material.IRON_AXE);
			axe.addEnchantment(Enchantment.DIG_SPEED, 1);
			axe.addEnchantment(Enchantment.DURABILITY, 1);
			items.put(0, ItemStackCreator.createItem(axe, ChatColor.AQUA + getName() + " Axe", 1));
			items.put(1, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getPotionHeal(8, 2));
			items.put(3, Utils.getPotionSpeed(2));
			items.put(4, Utils.getSteaks(3, getName()));
			items.put(5,ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.AQUA + getName() + " Arrows", 64));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_PROJECTILE, 3);
			items.put(6, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
		}
		if(upgrade == 9){
			ItemStack sword = new ItemStack(Material.BOW);
			sword.addEnchantment(Enchantment.ARROW_DAMAGE, 4);
			sword.addEnchantment(Enchantment.DURABILITY, 1);
			ItemStack axe = new ItemStack(Material.IRON_AXE);
			axe.addEnchantment(Enchantment.DIG_SPEED, 1);
			axe.addEnchantment(Enchantment.DURABILITY, 1);
			items.put(0, ItemStackCreator.createItem(axe, ChatColor.AQUA + getName() + " Axe", 1));
			items.put(1, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getPotionHeal(8, 2));
			items.put(3, Utils.getPotionSpeed(2));
			items.put(4, Utils.getSteaks(3, getName()));
			items.put(5,ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.AQUA + getName() + " Arrows", 64));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_PROJECTILE, 4);
			items.put(6, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
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
		return "Explosive Arrow";
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 1.5;
		for(int i = 0; i<upgrade; i++) {
			damage+=0.5;
		}
		return Arrays.asList("§7Fire an explosive arrow","§7that deals up to §c"+damage+"§7 damage","§7to nearby players.");
	}

	@Override
	public int getXPPerHit() {
		return 20;
	}

	@Override
	public HitType getHitType() {
		return HitType.PROJECTILE;
	}

	
	@EventHandler
	public void firstSkill(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Arrow) {
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
				double damage = 0;
				for(int i = 0; i < 9; i++) {
					damage += 10;
				}
				double chance = damage/100;
				double random = new Random().nextDouble();
				if(random <= chance) {
					p.getInventory().addItem(new ItemStack(Material.ARROW));
					p.sendMessage(ChatColor.YELLOW + "Skeleton: You Salvaging skill has given you an arrow.");
				}
			}
		}
	}
	
	@EventHandler
	public void hit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Arrow) {
			Arrow a = (Arrow)e.getDamager();
			if(ExplosiveArrow.exArrow.containsKey(a)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void hit(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Arrow) {
			Arrow a = (Arrow) e.getEntity();
			if(ExplosiveArrow.exArrow.containsKey(a)) {
				Player p = (Player)ExplosiveArrow.exArrow.get(a);
				double damage = 1.5;
				for(int i = 0; i<9; i++) {
					damage+=0.5;
				}
				ArrayList<Entity> used = new ArrayList<>();
				for(Entity ent : a.getNearbyEntities(4, 4, 4)) {
					if(ent == p) continue;
					if(used.contains(ent)) continue;
					if(ent instanceof LivingEntity) {
						if(TeamManager.getTeamByPlayer(p) != null) {
							if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) {
								continue;
							}
						}
						if(ent instanceof Player) {
							Player enp = (Player)ent;
							MPlayer player = MPlayerManager.getMPlayer(enp.getName());
							if(player.getCurrentClass().getName().equalsIgnoreCase("Skeleton")) {
								Utils.realDamage(ent, p, damage/2);
								used.add(ent);
								continue;
							}
						}
						Utils.realDamage(ent, p, damage);
						used.add(ent);
					}
				}
				ExplosiveArrow.exArrow.remove(a);
				used.clear();
			}
 		}
	}
	
	@EventHandler
	public void onGround(final ProjectileHitEvent e) {
		if(e.getEntity() instanceof Arrow) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					if(e.getEntity() != null) {
						e.getEntity().remove();
					}
				}
			}, 1);
		}
	}
	
	@EventHandler
	public void secondSkill(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
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
				double damage = 3;
				for(int i = 0; i < 9; i++) {
					damage += 0.5;
				}
				int time = (int) (20*damage);
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,time,0));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,time,1));
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("LEFT") == false) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(p.getItemInHand().getType() != Material.BOW) return;
		if(p.getLevel() < 100) return;
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		ExplosiveArrow.use(p, upgrade.getCurrentUpgrade());
		p.setLevel(0);
		p.setExp(0);
	}
	
	@EventHandler
	public void onInteract2(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(Utils.isUsingSword(p.getItemInHand()) == false) return;
		if(p.getLevel() < 100) return;
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		ExplosiveArrow.use(p, upgrade.getCurrentUpgrade());
		p.setLevel(0);
		p.setExp(0);
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}
	
}
