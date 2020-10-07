package me.main.yoni.ClassSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import me.main.yoni.Configuration;
import me.main.yoni.Main;
import me.main.yoni.API.Timeformatter;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Class implements Listener{

	public abstract String getName();

	public abstract List<String> getDescription();

	public abstract ClassType getType();

	public abstract ItemStack getIcon();

	public abstract int getPrice();

	public abstract HashMap<Integer, ItemStack> getStartingItems(int upgrade);

	public abstract int getXPPerHit();

	public abstract int getUpgradePrice(int level);
	
	public abstract String getAbilityName();

	public abstract HitType getHitType();

	public abstract List<String> getAbilityDescription(int upgrade);
	
	public abstract String getAbilityReadyName();
	
	public List<String> getSwordLore(int upgrade) {
		List<String> list = new ArrayList<>();
		list.add(ChatColor.GRAY + "Ability: " + ChatColor.RED + getAbilityName());
		list.add("");
		for(String s : getAbilityDescription(upgrade)) {
			list.add(s);
		}
		return list;
	}
	
	public String getNameByType(UpgradeType type) {
		if(type.equals(UpgradeType.ABILITY)) {
			return getAbilityName();
		}
		if(type.equals(UpgradeType.KIT)) {
			return getName();
		}
		return null;
	}
	
	public static void resetClass(Player p) {
		if(Utils.cd.contains(p.getName())) {
			p.sendMessage(ChatColor.RED + "Spam Protection! (3 Seconds)");
			return;
		}
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		Utils.giveItems(p);
		if(Main.playing.contains(p.getName())) {
			Main.playing.remove(p.getName());
		}
		p.teleport(Main.getFFALobby());
	}

	public void autoArmor(Player p,Upgrade upgrade) {
		for(ItemStack item : getStartingItems(upgrade.getCurrentUpgrade()).values()) { 
			if(item == null || item.getType() == Material.AIR) continue;
			if(item.getType().toString().contains("HELMET")) {
				p.getInventory().setHelmet(item);
				p.getInventory().remove(item);
			}
			if(item.getType().toString().contains("CHESTPLATE")) {
				p.getInventory().setChestplate(item);
				p.getInventory().remove(item);
			}
			if(item.getType().toString().contains("LEGGINGS")) {
				p.getInventory().setLeggings(item);
				p.getInventory().remove(item);
			}
			if(item.getType().toString().contains("BOOTS")) {
				p.getInventory().setBoots(item);
				p.getInventory().remove(item);
			}
		}
		if(p.getInventory().getHelmet() == null) {
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		}
		if(p.getInventory().getChestplate() == null) {
			p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		}
		if(p.getInventory().getLeggings() == null) {
			p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		}
		if(p.getInventory().getBoots() == null) {
			p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		}
		p.updateInventory();
	}
	
	public String getPotionEffectName(PotionEffectType pe) {
		if(pe.equals(PotionEffectType.INCREASE_DAMAGE)) {
			return "Strength";
		}
		if(pe.equals(PotionEffectType.SPEED)) {
			return "Speed";
		}
		if(pe.equals(PotionEffectType.FAST_DIGGING)) {
			return "Haste";
		}
		if(pe.equals(PotionEffectType.REGENERATION)) {
			return "Regeneration";
		}
		if(pe.equals(PotionEffectType.DAMAGE_RESISTANCE)) {
			return "Resistance";
		}
		return "PotionEffect";
	}
	
	public void apply(final Player p){
		if(!Main.playing.contains(p.getName())) {
			Main.playing.add(p.getName());
		}
		p.setGameMode(GameMode.SURVIVAL);
		p.setFoodLevel(20);
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		for(PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
		if(new Upgrade(p, this, UpgradeType.KIT).hasPrestige()) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,100000,5));
		} else {
			p.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,100000,4));
		}
		p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL,1,5));
		new Configuration(p.getUniqueId().toString(), "PlayerLayouts");
		Configuration config2 = Configuration.getConfig(p.getUniqueId().toString(),"PlayerLayouts");
		for(String s : config2.getConfigurationSection(getName()+".").getKeys(false)) {
			if(config2.getInt(getName()+"."+s) > 35) {
				p.getInventory().setItem(config2.getInt(getName()+"."+s)-36, Utils.getNewStartingItems(p, this).get(Integer.valueOf(s)));
				continue;
			}
			p.getInventory().setItem(config2.getInt(getName()+"."+s)+9, Utils.getNewStartingItems(p, this).get(Integer.valueOf(s)));
		}
		autoArmor(p,new Upgrade(p, this,UpgradeType.KIT));
		final MPlayer player = MPlayerManager.getMPlayer(p.getName());
		
		if(player.getCurrentClass().getName().equalsIgnoreCase("Hunter")) {
			new BukkitRunnable() {
				
				PotionEffect pe = null;
				boolean sent = false;
				int i = 0;
				@Override
				public void run() {
					if(!player.getCurrentClass().getName().equalsIgnoreCase("Hunter") || !Main.playing.contains(p.getName())) {
						cancel();
						return;
					}
					ArrayList<PotionEffect> list = new ArrayList<>();
					list.add(new PotionEffect(PotionEffectType.SPEED,20*10,0));
					list.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,20*5,0));
					list.add(new PotionEffect(PotionEffectType.FAST_DIGGING,20*15,0));
					list.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,20*5,0));
					list.add(new PotionEffect(PotionEffectType.REGENERATION,20*15,1));
					list.add(new PotionEffect(PotionEffectType.SPEED,20*6,1));
					if(i >= 30 - 5 && !sent) {
						sent = true;
						pe = list.get(new Random().nextInt(list.size()));
						p.sendMessage(ChatColor.GREEN + "Your Force of Nature is going to give you a " + pe.getDuration()/20 + " second " + getPotionEffectName(pe.getType()) + " "
							+ Timeformatter.formatForUpgradeLvls(pe.getAmplifier()+1) + " buff in 5 seconds.");
					}
					if(i >= 30) {
						p.addPotionEffect(pe);
						p.sendMessage(ChatColor.GREEN + "Your Force of Nature gave you a " + pe.getDuration()/20 + " second " + getPotionEffectName(pe.getType()) + " "
								+ Timeformatter.formatForUpgradeLvls(pe.getAmplifier()+1) + " buff.");
						i = 0;
						pe = null;
						sent = false;
					}
					i++;
				}
			}.runTaskTimer(Main.getPlugin(), 20, 20);
		}
		if(player.getCurrentClass().getName().equalsIgnoreCase("Skeleton")) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if(!player.getCurrentClass().getName().equalsIgnoreCase("Skeleton") || !Main.playing.contains(p.getName())) {
						cancel();
						return;
					}
					Utils.addLevel(p, 1);
					
				}
			}.runTaskTimer(Main.getPlugin(), 20, 20);
		}
		if(player.getCurrentClass().getHitType() == HitType.TIMER) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if(!Main.playing.contains(p.getName())) {
						cancel();
						return;
					}
					if(player.getCurrentClass().getHitType() == HitType.TIMER) {
						Utils.addLevel(p, player.getCurrentClass().getXPPerHit());
					} else {
						cancel();
					}
				}
			}.runTaskTimer(Main.getPlugin(), 0, 20);
		}
	}
}
