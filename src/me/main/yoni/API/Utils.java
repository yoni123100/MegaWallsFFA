package me.main.yoni.API;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.main.yoni.Main;
import me.main.yoni.ClassSystem.Class;
import me.main.yoni.Listeners.OneOnOneListener;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Utils {
	
	public static ArrayList<String> cd = new ArrayList<>();
	
	public static void sendSubTitle(Player player, String title) {
		CraftPlayer craftplayer = (CraftPlayer) player;
		PlayerConnection connection = craftplayer.getHandle().playerConnection;
		IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + title + "'}");
		PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
				subtitleJSON,0,0,0);
		connection.sendPacket(subtitlePacket);
	}
	
	public static void sendTitle(Player player, String title, int fadeIn, int stay, int fadeOut) {
		CraftPlayer craftplayer = (CraftPlayer) player;
		PlayerConnection connection = craftplayer.getHandle().playerConnection;
		IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '" + title + "'}");
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON,
				fadeIn, stay, fadeOut);
		connection.sendPacket(titlePacket);
	}
	
	public static int getCoinsForKill(Player killer) {
		if(killer.hasPermission("megawalls.coins.vip5")) {
			return Main.getPlugin().getConfig().getInt("coins-per-kill-vip5");
		}
		if(killer.hasPermission("megawalls.coins.vip4")) {
			return Main.getPlugin().getConfig().getInt("coins-per-kill-vip4");
		}
		if(killer.hasPermission("megawalls.coins.vip3")) {
			return Main.getPlugin().getConfig().getInt("coins-per-kill-vip3");
		}
		if(killer.hasPermission("megawalls.coins.vip2")) {
			return Main.getPlugin().getConfig().getInt("coins-per-kill-vip2");
		}
		if(killer.hasPermission("megawalls.coins.vip1")) {
			return Main.getPlugin().getConfig().getInt("coins-per-kill-vip1");
		}
		return Main.getPlugin().getConfig().getInt("coins-per-kill-vip1");
	}
	
	public static boolean isArmor(ItemStack item) {
		if(item.getType().toString().contains("HELMET")) {
			return true;
		}
		if(item.getType().toString().contains("CHESTPLATE")) {
			return true;
		}
		if(item.getType().toString().contains("LEGGINGS")) {
			return true;
		}
		if(item.getType().toString().contains("BOOTS")) {
			return true;
		}
		return false;
	}
	
	public static HashMap<Integer, ItemStack> getNewStartingItems(Player p,Class c){
		ItemStack pix = new ItemStack(Material.DIAMOND_PICKAXE);
		pix.addEnchantment(Enchantment.DIG_SPEED, 3);
		pix.addEnchantment(Enchantment.DURABILITY, 3);
		p.updateInventory();
		HashMap<Integer, ItemStack> items = c.getStartingItems(9);
		items.put(items.size(), Utils.getBlockForClass(p, c));
		items.put(items.size(), pix);
		items.put(items.size(), ItemStackCreator.createItem(new ItemStack(Material.COMPASS), ChatColor.GREEN + "Player Tracker §7(Right Click)", 1));
		return items;
	}
	
	  public static ItemStack getBlockForClass(Player p, me.main.yoni.ClassSystem.Class c) {
		  	MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(c.getName().equalsIgnoreCase("blaze") || c.getName().equalsIgnoreCase("arcanist")) {
				return new ItemStack(Material.IRON_ORE,(player.getBlocksUpgrade() + 1) * 16);
			} else if(c.getName().equalsIgnoreCase("skeleton")) {
				return new ItemStack(Material.WOOD,(player.getBlocksUpgrade() + 1) * 16);
			} else {
				return new ItemStack(Material.COBBLESTONE,(player.getBlocksUpgrade() + 1) * 16);
			}
	  }
	
	  public static void lookAt(Entity ent,Location location) {
	        ent.getLocation().setYaw(getLocalAngle(new Vector(location.getX(), 0, location.getZ()), location.toVector()));
	    }

	  	public static boolean isNumber(String s) {
	  		try {
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
	  	}
	  
	    public static final float getLocalAngle(Vector point1, Vector point2) {
	        double dx = point2.getX() - point1.getX();
	        double dz = point2.getZ() - point1.getZ();
	        float angle = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90;
	        if(angle < 0) {
	            angle += 360.0F;
	        }
	        return angle;
	    }
	
	  public static void spawnPirateBird(final Player owner)
	  {
	    final Bat bat = (Bat)owner.getLocation().getWorld().spawnEntity(owner.getLocation(), EntityType.BAT);
	    final Zombie z = (Zombie)owner.getLocation().getWorld().spawnEntity(owner.getLocation(), EntityType.ZOMBIE);
	    bat.setPassenger(z);
	    ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
	    SkullMeta meta = (SkullMeta)i.getItemMeta();
	    meta.setOwner("Seagull");
	    i.setItemMeta(meta);
	    z.getEquipment().setHelmet(i);

	    bat.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 0));
	    bat.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2147483647, 0));
	    bat.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2147483647, 10));
	    z.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 0));
	    z.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2147483647, 0));
	    z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2147483647, 10));
	    
	    new BukkitRunnable() {
			
			@Override
			public void run() {
				if(owner.isDead() || owner == null || bat == null || bat.isDead() || z.isDead() || z == null) {
					cancel();
					if(bat != null) {
						bat.remove();
					}
					if(z != null) {
						z.remove();
					}
					return;
				}
				for(Entity ent : bat.getNearbyEntities(5, 5, 5)) {
					if(ent == owner || ent == z) continue;
					if(!(ent instanceof LivingEntity)) continue;
					bat.setVelocity(ent.getLocation().toVector().subtract(bat.getLocation().toVector()).multiply(0.5));
					for(Player ps : Bukkit.getOnlinePlayers()) {
						ps.playSound(ent.getLocation(), Sound.EXPLODE, 5, 5);
					}
					lookAt(z, ent.getLocation());
					lookAt(bat, ent.getLocation());
					z.remove();
					bat.remove();
					((LivingEntity)ent).damage(2);
					cancel();
				}
				if(owner.getLocation().distance(bat.getLocation()) > 5) {
					bat.setVelocity(owner.getLocation().toVector().subtract(bat.getLocation().toVector()).multiply(0.2));
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 5);
	  }

	public static void realDamage(final Entity p,final Player damager,double damage) {
		final double real = damage;
		final double hp = ((Damageable)p).getHealth();
		// 5 - 6 = -1
		// 7 - 6 = 1
		if(p.isDead()) return;
		double lol = hp - real;
		if(lol <= 0) {
			//Need to be tested.
			((LivingEntity)p).damage(100,damager);
		} else {
			((LivingEntity)p).setHealth(hp - real);
			((LivingEntity)p).damage(0);
		}
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static void setNormalItems(Player p) {
		p.getInventory().setArmorContents(null);
		for(PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
		p.getInventory().clear();
		ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
		SkullMeta sm = (SkullMeta) skull.getItemMeta();
		sm.setOwner(p.getName());
		sm.setDisplayName(ChatColor.GREEN + "My Profile " + org.bukkit.ChatColor.GRAY + "(Right Click)");
		sm.setLore(Arrays.asList(ChatColor.GRAY + "Click to see your own stats"));
		skull.setItemMeta(sm);
		p.getInventory().setItem(8, ItemStackCreator.createItem(new ItemStack(Material.COMMAND), "§aClass Selector§7 (Right Click)", 1));
		p.getInventory().setItem(1, skull);
		p.getInventory().setItem(7, ItemStackCreator.createItem(new ItemStack(Material.EMERALD), "§aShop§7 (Right Click)", 1));
		p.getInventory().setItem(4, ItemStackCreator.createItem(new ItemStack(Material.CAKE), "§cPLAY!§7 (Right Click)", 1));
		if(p.hasPermission("megawalls.spectate")) {
			p.getInventory().setItem(5, ItemStackCreator.createItem(new ItemStack(Material.GOLD_NUGGET), "§aStaff Spectate Item§7 (Right Click)", 1));
		}
		p.getInventory().setItem(0, ItemStackCreator.createItem(new ItemStack(Material.COMPASS), "§aGame Menu§7 (Right Click)", 1));
		p.updateInventory();
		p.setLevel(0);
		p.setExp(0);
		p.setMaxHealth(20.0);
		p.setFoodLevel(20);
		p.setHealth(20.0);
		p.updateInventory();
	}

	public static void giveItems(final Player p){
		if(!p.isOnline()) return;
		if(cd.contains(p.getName())) {
			return;
		}
		if(OneOnOneListener.accept.containsKey(p)) {
			OneOnOneListener.accept.remove(p);
		}
		if(OneOnOneListener.accepted.containsKey(p)) {
			OneOnOneListener.accepted.remove(p);
		}
		if(OneOnOneListener.queue.contains(p.getName())) {
			OneOnOneListener.queue.remove(p.getName());
		}
		if(Main.playing.contains(p.getName())) {
			Main.playing.remove(p.getName());
		}
		if(Main.lobby.contains(p.getName())) {
			Main.lobby.remove(p.getName());
		}
//		if(p.isDead()) {
//			p.spigot().respawn();
//			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
//				
//				@Override
//				public void run() {
//					giveItems(p);
//					
//				}
//			}, 5);
//			return;
//		}
		if(Main.getFFALobby() != null) {
			p.teleport(Main.getFFALobby());
		}
		cd.add(p.getName());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(cd.contains(p.getName())) {
					cd.remove(p.getName());
				}
			}
		}, 20*3);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				p.getInventory().setArmorContents(null);
				for(PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
				p.getInventory().clear();
				ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
				SkullMeta sm = (SkullMeta) skull.getItemMeta();
				sm.setOwner(p.getName());
				sm.setDisplayName(ChatColor.GREEN + "My Profile " + org.bukkit.ChatColor.GRAY + "(Right Click)");
				sm.setLore(Arrays.asList(ChatColor.GRAY + "Click to see your own stats"));
				skull.setItemMeta(sm);
				p.getInventory().setItem(8, ItemStackCreator.createItem(new ItemStack(Material.COMMAND), "§aClass Selector§7 (Right Click)", 1));
				p.getInventory().setItem(1, skull);
				p.getInventory().setItem(7, ItemStackCreator.createItem(new ItemStack(Material.EMERALD), "§aShop§7 (Right Click)", 1));
				p.getInventory().setItem(4, ItemStackCreator.createItem(new ItemStack(Material.CAKE), "§cPLAY!§7 (Right Click)", 1));
				if(p.hasPermission("megawalls.spectate")) {
					p.getInventory().setItem(5, ItemStackCreator.createItem(new ItemStack(Material.GOLD_NUGGET), "§aStaff Spectate Item§7 (Right Click)", 1));
				}
				p.getInventory().setItem(0, ItemStackCreator.createItem(new ItemStack(Material.COMPASS), "§aGame Menu§7 (Right Click)", 1));
				p.updateInventory();
				p.setLevel(0);
				p.setExp(0);
				p.setMaxHealth(20.0);
				p.setFoodLevel(20);
				p.setHealth(20.0);
				p.setGameMode(GameMode.ADVENTURE);
				Main.updateScoreboard(p);
				
			}
		}, 5);
	}



	public static ItemStack getSteaks(int amount, String name) {
		if(name.equalsIgnoreCase("Squid")) {
			return ItemStackCreator.createItem(new ItemStack(Material.COOKED_FISH,amount),ChatColor.AQUA + name + " Salmon", amount);
		}
		return ItemStackCreator.createItem(new ItemStack(Material.COOKED_BEEF,amount),ChatColor.AQUA + name + " Steak", amount);
	}

	public static ItemStack getPotionRegeneration(int amount,String c) {
		ItemStack potion = new ItemStack(Material.POTION,amount,(byte)8225);
		PotionMeta pm = (PotionMeta) potion.getItemMeta();
		if(c != "Pirate") {
			pm.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 2), true);
			pm.setDisplayName(ChatColor.AQUA+"Potion of Regeneration III");
		} else {
			pm.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 1), true);
			pm.setDisplayName(ChatColor.AQUA+"Potion of Regeneration II");
		}
		potion.setItemMeta(pm);
		return potion;
	}

	public static ItemStack getPotionHeal(int heal,int amount) {
		ItemStack h = new ItemStack(Material.POTION,amount,(byte)8229);
		PotionMeta hm = (PotionMeta) h.getItemMeta();
		hm.setDisplayName(ChatColor.AQUA+"Potion of Heal ("+heal+ChatColor.RED+"❤"+ChatColor.AQUA+")");
		h.setItemMeta(hm);
		return h;
	}

	public static ItemStack getPotionSpeed(int amount) {
		ItemStack h = new ItemStack(Material.POTION,amount,(byte)8226);
		PotionMeta hm = (PotionMeta) h.getItemMeta();
		hm.addCustomEffect(new PotionEffect(PotionEffectType.SPEED,20*15,1), true);
		hm.setDisplayName(ChatColor.AQUA+"Potion of Speed ("+15+" "+ChatColor.RED+"Seconds"+ChatColor.AQUA+")");
		h.setItemMeta(hm);
		return h;
	}

	public static boolean isUsingSword(ItemStack item) {
		if(item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.IRON_SWORD || item.getType() == Material.STONE_SWORD
				|| item.getType() == Material.WOOD_SWORD) {
			return true;
		}
		return false;
	}

	public static String toString(ItemStack item){
		if(item.getType() == Material.POTION) {
			if(item.getItemMeta().getDisplayName().replaceAll("§b", "").contains("Heal")) {
				String a = ChatColor.stripColor(item.getItemMeta().getDisplayName());
				if(item.getAmount() > 1)
				a = ChatColor.GRAY + a + " §8x" + item.getAmount();
				return a.replaceAll("❤", ChatColor.RED + "❤" + ChatColor.GRAY);
			}
			String a = ChatColor.stripColor(item.getItemMeta().getDisplayName());
			if(item.getAmount() > 1)
			a = ChatColor.GRAY + a + " §8x" + item.getAmount();
			return a;
		}
		String s = "§7"+StringUtils.capitalize(item.getType().name().toLowerCase().replace("_", " "));
		if(item.getEnchantments().size() > 0){
			s+= " §8(";
			for(Enchantment ench : item.getEnchantments().keySet()){
				s+= "§7"+StringUtils.capitalize(ench.getName().toLowerCase().replace("_", " "))+" "+Timeformatter.formatForUpgradeLvls(item.getEnchantments().get(ench))+", ";
			}
			s+="§8)";
			s = s.replace(", §8)", "§8)");
		}
		if(item.getAmount() > 1){
			s+= " §8x"+item.getAmount();
		}
		return s;
	}

	public static Entity getTarget(Player p) {
		Entity target;
		List<Entity> nearbyE = p.getNearbyEntities(25,25, 25);
		ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();
		for (Entity e : nearbyE) {
			if (e instanceof LivingEntity) {
				livingE.add((LivingEntity) e);
			}
		}

		target = null;
		BlockIterator bItr = new BlockIterator(p, 25);
		Block block;
		Location loc;
		int bx, by, bz;
		double ex, ey, ez;
		// loop through player's line of sight
		while (bItr.hasNext()) {
			block = bItr.next();
			bx = block.getX();
			by = block.getY();
			bz = block.getZ();
			// check for entities near this block in the line of sight
			for (LivingEntity e : livingE) {
				loc = e.getLocation();
				ex = loc.getX();
				ey = loc.getY();
				ez = loc.getZ();
				if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5)) {
					// entity is close enough, set target and stop
					target = e;
					break;
				}
			}
		}
		return target;
	}
	
	public static void sendActionBar(Player player, String message){
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc,(byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
    }

	public static void addLevel(Player p,int lvl) {
		if(!Main.playing.contains(p.getName())) return;
		final Player damager = p;
		if(p.getLevel() >= 100) return;
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() == null) return;
		damager.setLevel(damager.getLevel() + lvl);
		if(damager.getLevel() > 100) {
			damager.setLevel(100);
		}
		float xp = (float) ((double)damager.getLevel()/100);
		damager.setExp(xp);
		if(damager.getLevel() >= 100) {
			new BukkitRunnable() {
				
				int time = 0;
				boolean run = false;
				float x = (float) ((double)damager.getLevel()/100);
				public void run() {
					x = (float) ((double)damager.getLevel()/100);
					if(damager.getLevel() < 100) {
						damager.setExp(x);
						cancel();
						return;
					}
					if(time == 0) {
						time = 20;
						me.main.yoni.ClassSystem.Class c = MPlayerManager.getMPlayer(damager.getName()).getCurrentClass();
						damager.sendMessage("§aYour §b"+c.getAbilityReadyName()+" §aSkill is ready!");
						if(c.getName().equalsIgnoreCase("blaze") || c.getName().equalsIgnoreCase("skeleton")){
							damager.sendMessage("§bLeft Click §awith any bow to activate your skill!");
						} else {
							damager.sendMessage("§bRight Click §awith any sword to activate your skill!");
						}
					}
					time--;
					if(run) {
						damager.setExp(0);
						run = false;
					} else {
						damager.setExp(1);
						run = true;
					}
				}
			}.runTaskTimer(Main.getPlugin(Main.class), 10, 10);
		}
	}
	
	public static void autoArmor(Player p) {
		for(ItemStack item : p.getInventory().getContents()) { 
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
		p.updateInventory();
	}
	
    public static void setTab(EntityPlayer player, String foot, String head) {
    	  IChatBaseComponent header = ChatSerializer.a("{\"text\":\""+head+"\"}");
          IChatBaseComponent footer = ChatSerializer.a("{\"text\":\""+foot+"\"}");
        
          PacketPlayOutPlayerListHeaderFooter tph = new PacketPlayOutPlayerListHeaderFooter();

          try {
              Field headerField = tph.getClass().getDeclaredField("a");
              headerField.setAccessible(true);
              headerField.set(tph, header);
              headerField.setAccessible(!headerField.isAccessible());
         
              Field footerField = tph.getClass().getDeclaredField("b");
              footerField.setAccessible(true);
              footerField.set(tph, footer);
              footerField.setAccessible(!footerField.isAccessible());
          } catch (Exception e) {
              e.printStackTrace();
          }  
        
          player.playerConnection.sendPacket(tph);
    }

	public static void give1v1Items(final Player p) {
		if(!p.isOnline()) return;
		if(cd.contains(p.getName())) {
			return;
		}
		if(Main.playing.contains(p.getName())) {
			Main.playing.remove(p.getName());
		}
		if(OneOnOneListener.accept.containsKey(p)) {
			OneOnOneListener.accept.remove(p);
		}
		if(OneOnOneListener.accepted.containsKey(p)) {
			OneOnOneListener.accepted.remove(p);
		}
		if(OneOnOneListener.queue.contains(p.getName())) {
			OneOnOneListener.queue.remove(p.getName());
		}
		MPlayerManager.getMPlayer(p.getName()).setCurrentDuelClass(null);
//		if(p.isDead()) {
//			p.spigot().respawn();
//			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
//				
//				@Override
//				public void run() {
//					give1v1Items(p);
//					
//				}
//			}, 5);
//			return;
//		}
		if(Main.get1v1Lobby() != null) {
			p.teleport(Main.get1v1Lobby());
		}
		cd.add(p.getName());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(cd.contains(p.getName())) {
					cd.remove(p.getName());
				}
				
			}
		}, 20*3);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				p.setGameMode(GameMode.ADVENTURE);
				p.getInventory().setArmorContents(null);
				for(PotionEffect potion : p.getActivePotionEffects()) p.removePotionEffect(potion.getType());
				p.getInventory().clear();
				p.setMaxHealth(20.0);
				p.setHealth(p.getMaxHealth());
				p.setFoodLevel(20);
				p.getInventory().setItem(0, ItemStackCreator.createItem(new ItemStack(Material.DIAMOND_SWORD), "§aDuel Stick§7 (Click on a Player)", 1));
				p.getInventory().setItem(8, ItemStackCreator.createItem(new ItemStack(Material.REDSTONE), "§cReturn to lobby", 1));
				p.getInventory().setItem(4, ItemStackCreator.createItem(new ItemStack(Material.INK_SACK,1,(byte)8), "§6Queue (§cOFF§6)", 1));
				Main.updateScoreboard(p);
				p.updateInventory();
				p.setMaxHealth(20.0);
				p.setLevel(0);
				p.setExp(0);
				p.setHealth(20.0);
				
			}
		}, 5);
	}
}
