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
import me.main.yoni.ClassSystem.Abilities.IronPunch;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Golem extends me.main.yoni.ClassSystem.Class {
	
	public ArrayList<String> cd = new ArrayList<>();

	@Override
	public String getName() {
		return "Golem";
	}

	@Override
	public List<String> getDescription() {
		return Arrays.asList("The Golem class uses the","all mighty powers of the ","iron god.");
	}

	@Override
	public ClassType getType() {
		return ClassType.HERO;
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(Material.IRON_CHESTPLATE);
	}

	@Override
	public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
		HashMap<Integer, ItemStack> items = new HashMap<>(); 
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(1, getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			items.put(2, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionRegeneration(1,getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionRegeneration(2,getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(2, getName()));
			items.put(2, Utils.getPotionRegeneration(2,getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionRegeneration(2,getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.IRON_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionRegeneration(2,getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionRegeneration(2,getName()));
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 1);
			HashMap<Enchantment, Integer> enchats2 = new HashMap<>();
			enchats2.put(Enchantment.DURABILITY, 3);
			items.put(3, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats2));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.DURABILITY, 3);
			items.put(0, ItemStackCreator.createItem(Sword, ChatColor.AQUA + getName() + " Sword", 1,getSwordLore(upgrade)));
			items.put(1, Utils.getSteaks(3, getName()));
			items.put(2, Utils.getPotionRegeneration(2,getName()));
			ItemStack slowpot = new ItemStack(Material.POTION,1);
			PotionMeta im = (PotionMeta) slowpot.getItemMeta();
			Potion ppp = new Potion(16426);
			ppp.setSplash(true);
			ppp.apply(slowpot);
			im.setDisplayName(ChatColor.AQUA+"Splash Potion of Slow III");
			im.addCustomEffect(new PotionEffect(PotionEffectType.SLOW,20*5,2), true);
			slowpot.setItemMeta(im);
			items.put(3, slowpot);
			HashMap<Enchantment, Integer> enchats = new HashMap<>();
			enchats.put(Enchantment.DURABILITY, 3);
			enchats.put(Enchantment.PROTECTION_EXPLOSIONS, 1);
			HashMap<Enchantment, Integer> enchats2 = new HashMap<>();
			enchats2.put(Enchantment.DURABILITY, 3);
			enchats2.put(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			items.put(4, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_CHESTPLATE), 1, ChatColor.AQUA + getName() + " Chestplate", null, enchats));
			items.put(5, ItemStackCreator.createItemStack(new ItemStack(Material.DIAMOND_BOOTS), 1, ChatColor.AQUA + getName() + " Boots", null, enchats2));
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
		return "Iron Punch";
	}

	@Override
	public int getXPPerHit() {
		if(getHitType() == HitType.MELEE) {
			return 10;
		}
		return 0;
	}

	@Override
	public List<String> getAbilityDescription(int upgrade) {
		double damage = 0.5;
		for(int i = 0; i<upgrade; i++) {
			damage+=0.5;
		}
		return Arrays.asList("§7Cast a hexagon causing §c"+damage+"","§7damage in 5 blocks radius.");
	}

	
	@Override
	public HitType getHitType() {
		return HitType.MELEE;
	}
	
	@EventHandler
	public void kill(PlayerDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player) {
			final Player killer = (Player)e.getEntity().getKiller();
			MPlayer player = MPlayerManager.getMPlayer(killer.getName());
			if(player.getCurrentClass() != this) return;
			if(cd.contains(killer.getName())) return;
			double damage = 1.75;
			for(int i = 0; i < 9; i++) {
				damage += 1.25;
			}
			cd.add(killer.getName());
			int time = (int) (20 * damage);
			killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION,time,1));
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					if(cd.contains(killer.getName())) {
						cd.remove(killer.getName());
					}
				}
			}, 20*45);
		}
	}
	
	@EventHandler
	public void secondSkill(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Arrow) {
			Player p = (Player)e.getEntity();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentClass() != this) return;
			double damage = 1;
			for(int i = 0; i < 9; i++) {
				damage += 1;
			}
			int time = (int) (20*damage);
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,time,0));
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
		IronPunch.use(p, upgrade.getCurrentUpgrade());
	}

	@Override
	public String getAbilityReadyName() {
		return getAbilityName();
	}
}
