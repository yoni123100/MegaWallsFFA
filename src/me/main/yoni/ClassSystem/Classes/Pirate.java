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
import me.main.yoni.ClassSystem.Abilities.CannonFire;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Pirate extends me.main.yoni.ClassSystem.Class{
	
	public static ArrayList<String> cd = new ArrayList<>();

	@Override
	public String getName() {
		return "Pirate";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("Use your cunning and wit to","survive, or just blow","people up with your","parrots.");
	}

	@Override
	public ClassType getType() {
		return ClassType.HERO;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.STONE_AXE);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.DURABILITY, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.DURABILITY, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.DURABILITY, 1);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionRegeneration(1, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.DURABILITY, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionRegeneration(1, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchants.put(Enchantment.DURABILITY, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionRegeneration(2, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchants.put(Enchantment.DURABILITY, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionRegeneration(2, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchants.put(Enchantment.DURABILITY, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionRegeneration(2, getName()));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchants.put(Enchantment.DURABILITY, 1);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		return items;
	}

	@Override
	public int getPrice() {
		return 20000;
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
		return "Ability";
	}

	@Override
	public int getXPPerHit() {
		return 13;
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 5.5;
		for(int i = 0; i < upgrade; i++) {
			damage += 0.5;
		}
		return Arrays.asList("§7Launch a cannonball that","§7does §c"+damage+"§7 damage on impact.","§7The cannonball also penetrates thin barricades!");
	}


	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}
	
	@EventHandler
	public void hit(ProjectileHitEvent e) { 
		if(e.getEntity() instanceof WitherSkull) {
			if(e.getEntity().getShooter() instanceof Player) {
				WitherSkull ws = (WitherSkull) e.getEntity();
				Player shooter =  (Player) e.getEntity().getShooter();
				if(MPlayerManager.getMPlayer(shooter.getName()).getCurrentClass() != this) return;
				Upgrade upgrade = new Upgrade(shooter, this,UpgradeType.ABILITY);
				for(Entity ent : ws.getNearbyEntities(3, 3, 3)) {
					if(ent instanceof LivingEntity) {
						if(shooter == ent) continue;
						if(TeamManager.getTeamByPlayer(shooter) != null) {
							if(TeamManager.getTeamByPlayer(shooter).getPlayers().contains(ent)) {
								continue;
							}
						}
						Utils.realDamage(ent, shooter, 10);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void seven(EntityDamageEvent e) {
		if(e.isCancelled()) return;
		if(!me.main.yoni.Main.playing.contains(e.getEntity().getName())) return;
		if(e.getEntity() instanceof Player) {
			final Player p = (Player)e.getEntity();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			if(p.getHealth() - e.getDamage() <= 10) {
				if(cd.contains(p.getName())) return;
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*15,1));
				cd.add(p.getName());
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

					@Override
					public void run() {
						cd.remove(p.getName());

					}
				}, 20 * 30);
			}
		}
	}
	
	@EventHandler
	public void wither(EntityExplodeEvent e) {
		if(e.getEntity() instanceof WitherSkull) {
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
		CannonFire.use(p);
		p.setLevel(0);
		p.setExp(0);
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}

}
