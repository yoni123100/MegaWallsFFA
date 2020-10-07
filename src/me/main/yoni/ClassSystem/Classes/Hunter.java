package me.main.yoni.ClassSystem.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.main.yoni.Main;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.Class;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.ClassSystem.Abilities.HomingTask;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Hunter extends Class{
	
	public ArrayList<String> cd = new ArrayList<>();

	@Override
	public String getName() {
		return "Hunter";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("This archery class powers","up with the thrill of the","hunt.");
	}

	@Override
	public ClassType getType() {
		return ClassType.HERO;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.BOW);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>();
		if(upgrade == 1){
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.DURABILITY, 2);
			ItemStack sword = new ItemStack(Material.WOOD_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, ItemStackCreator.createItem(bow, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getSteaks(2, getName()));
			items.put(3, new ItemStack(Material.ARROW,12));
		}
		if(upgrade == 2){
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.DURABILITY, 2);
			ItemStack sword = new ItemStack(Material.STONE_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, ItemStackCreator.createItem(bow, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getSteaks(2, getName()));
			items.put(3, new ItemStack(Material.ARROW,16));
		}
		if(upgrade == 3){
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.DURABILITY, 2);
			ItemStack sword = new ItemStack(Material.STONE_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, ItemStackCreator.createItem(bow, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getSteaks(2, getName()));
			items.put(3, new ItemStack(Material.ARROW,30));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.DURABILITY, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
		}
		if(upgrade == 4){
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.DURABILITY, 2);
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, ItemStackCreator.createItem(bow, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getSteaks(3, getName()));
			items.put(3, new ItemStack(Material.ARROW,24));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.DURABILITY, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(5, ItemStackCreator.createItemStack(new ItemStack(Material.CHAINMAIL_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 5){
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.DURABILITY, 2);
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, ItemStackCreator.createItem(bow, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getSteaks(3, getName()));
			items.put(3, new ItemStack(Material.ARROW,32));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.DURABILITY, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(5, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants));
		}
		if(upgrade == 6){
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			bow.addEnchantment(Enchantment.DURABILITY, 2);
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, ItemStackCreator.createItem(bow, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getSteaks(3, getName()));
			items.put(3, new ItemStack(Material.ARROW,40));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchants.put(Enchantment.PROTECTION_PROJECTILE, 1);
			HashMap<Enchantment, Integer> enchants2 = new HashMap<>();
			enchants2.put(Enchantment.DURABILITY, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(5, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants2));
		}
		if(upgrade == 7){
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			bow.addEnchantment(Enchantment.DURABILITY, 2);
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, ItemStackCreator.createItem(bow, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getSteaks(3, getName()));
			items.put(3, new ItemStack(Material.ARROW,48));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchants.put(Enchantment.PROTECTION_PROJECTILE, 1);
			HashMap<Enchantment, Integer> enchants2 = new HashMap<>();
			enchants2.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			enchants2.put(Enchantment.DURABILITY, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(5, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants2));
		}
		if(upgrade == 8){
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			bow.addEnchantment(Enchantment.DURABILITY, 2);
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, ItemStackCreator.createItem(bow, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getSteaks(3, getName()));
			items.put(3, new ItemStack(Material.ARROW,56));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			enchants.put(Enchantment.PROTECTION_PROJECTILE, 2);
			HashMap<Enchantment, Integer> enchants2 = new HashMap<>();
			enchants2.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			enchants2.put(Enchantment.DURABILITY, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(5, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants2));
		}
		if(upgrade == 9){
			ItemStack bow = new ItemStack(Material.BOW);
			bow.addEnchantment(Enchantment.ARROW_DAMAGE, 1);
			bow.addEnchantment(Enchantment.DURABILITY, 2);
			ItemStack sword = new ItemStack(Material.IRON_SWORD);
			sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
			sword.addEnchantment(Enchantment.DURABILITY, 2);
			items.put(0, ItemStackCreator.createItem(sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, ItemStackCreator.createItem(bow, ChatColor.AQUA + getName() + " Bow", 1,getSwordLore(upgrade)));
			items.put(2, Utils.getSteaks(3, getName()));
			items.put(3, new ItemStack(Material.ARROW,64));
			HashMap<Enchantment, Integer> enchants = new HashMap<>();
			enchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			enchants.put(Enchantment.PROTECTION_PROJECTILE, 2);
			HashMap<Enchantment, Integer> enchants2 = new HashMap<>();
			enchants2.put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			enchants2.put(Enchantment.DURABILITY, 2);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_HELMET), 1, ChatColor.AQUA + getName() + " Helmet", null, enchants));
			items.put(5, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchants2));
			items.put(6, new ItemStack(Material.GOLDEN_APPLE,2));
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
		return "Eagle's Eye";
	}

	@Override
	public int getXPPerHit() {
		return 2;
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		int damage = 6;
		for(int i = 0; i < upgrade; i++) {
			damage += 1;
		}
		return Arrays.asList("§7Upon activation, you will","§7have homing arrows for §c"+damage+"","§7seconds.");
	}


	@Override
	public HitType getHitType() {
		return HitType.TIMER;
	}

	@EventHandler
	public void eventArrowFired(EntityShootBowEvent e) {
		if(e.getForce() != 1.0) return;
		if(!(e.getEntity() instanceof Player)) return;
		Player p = (Player)e.getEntity();
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(!cd.contains(p.getName())) return;
		if (((e.getEntity() instanceof LivingEntity)) && ((e.getEntity() instanceof Player)) && ((e.getProjectile() instanceof Arrow))){
			LivingEntity player = e.getEntity();
			double minAngle = 6.283185307179586D;
			Entity minEntity = null;
			for (Entity entity : player.getNearbyEntities(64.0D, 64.0D, 64.0D)) {
				if ((player.hasLineOfSight(entity)) && (!entity.isDead()) && (entity instanceof Player)){
					Vector toTarget = entity.getLocation().toVector().clone().subtract(player.getLocation().toVector());
					double angle = e.getProjectile().getVelocity().angle(toTarget);
					if (angle < minAngle){
						minAngle = angle;
						minEntity = entity;
					}
				}
			}
			if (minEntity != null && minEntity instanceof LivingEntity && e.getProjectile() instanceof Arrow) {
				new HomingTask((Arrow)e.getProjectile(), (LivingEntity)minEntity, Main.getPlugin());
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		final Player p = e.getPlayer();
		if(e.getAction().name().contains("LEFT") == false) return;
		if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != this) return;
		if(p.getItemInHand().getType() != Material.BOW) return;
		if(p.getLevel() < 100) return;
		Upgrade upgrade = new Upgrade(p, this, UpgradeType.ABILITY);
		int damage = 6;
		for(int i = 0; i < upgrade.getCurrentUpgrade(); i++) {
			damage += 1;
		}
		p.setLevel(0);
		p.setExp(0);
		cd.add(p.getName());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				cd.remove(p.getName());
				p.sendMessage(ChatColor.GREEN + "Your Eagle's Eye wore off.");
				
			}
		}, 20*damage);
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}

}
