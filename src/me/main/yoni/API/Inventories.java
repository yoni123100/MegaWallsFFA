package me.main.yoni.API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.main.yoni.Configuration;
import me.main.yoni.Main;
import me.main.yoni.BoosterSystem.BoosterManager;
import me.main.yoni.ClassSystem.Class;
import me.main.yoni.ClassSystem.ClassManager;
import me.main.yoni.ClassSystem.ClassType;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Inventories {
	
	public static void openBoosterInventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54,"My Network Boosters");
		int count = 0;
		for(int i = 0; i<MPlayerManager.getMPlayer(p.getName()).getNetworkBoosters(); i++) {
			inv.setItem(count, ItemStackCreator.createItem(new ItemStack(Material.POTION), ChatColor.GREEN + "Network Booster (Right Click To Activate!)", 1,Arrays.asList(ChatColor.GRAY + "Time: " + Timeformatter.formatTime(60*60))));
			count++;
		}
		if(BoosterManager.getBooster((OfflinePlayer) p) != null) { 
			inv.setItem(49, ItemStackCreator.createItem(new ItemStack(Material.POTION), ChatColor.RED + "Network Booster", 1,Arrays.asList(ChatColor.GRAY + Timeformatter.formatTime(BoosterManager.getBooster((OfflinePlayer) p).getTime()))));
		}
		Player player = (Player) p;
		player.openInventory(inv);
	}
	
	public static void openSpecInventory(Player p) {
		if(Main.lobby.contains(p.getName()) || Main.playing.contains(p.getName())) {
			p.sendMessage(ChatColor.RED + "You must be in the FFA lobby with no class applied!");
			return;
		}
		Inventory inv = Bukkit.createInventory(null, 54,"Please choose a player");
		for(Player ps : Bukkit.getOnlinePlayers()) {
			if(ps == p) continue;
			ItemStack item = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
			SkullMeta im = (SkullMeta) item.getItemMeta();
			im.setDisplayName(ps.getName());
			im.setOwner(ps.getName());
			im.setLore(Arrays.asList(ChatColor.GRAY + "Click to spectate " + ps.getDisplayName() + ChatColor.GRAY + "."));
			item.setItemMeta(im);
			inv.addItem(item);
		}
		p.openInventory(inv);
	}
	
	public static void openGlobalInventroyEditor(Player p) {
		Inventory inv = Bukkit.createInventory(null, 54,"Layout Editor");
		int i = 10;
		for(Class c : ClassManager.getClasses()) {
			if(!MPlayerManager.getMPlayer(p.getName()).hasClass(c)) continue;
			if(i == 17 || i==17+9) {
				i+=2;
			}
			inv.setItem(i, ItemStackCreator.createItem(c.getIcon(), ChatColor.GREEN + "Edit " + c.getName() + " Layout", 1,Arrays.asList("§7Click to customize the","§7layout of the " + c.getName() + "","§7class.")));
			i++;
		}
		inv.setItem(48, ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.GREEN + "Go Back", 1));
		inv.setItem(49, ItemStackCreator.createItem(new ItemStack(Material.EMERALD), ChatColor.GRAY + "Total Coins: " + ChatColor.GOLD + MPlayerManager.getMPlayer(p.getName()).getCoins(), 1));
		
		p.openInventory(inv);
	}
	
	public static void openInventroyEditor(Player p, Class c) {
		Inventory inv = Bukkit.createInventory(null, 9*6,"Edit " + c.getName() + " Layout");
		for(int i = 27; i<36; i++) {
			inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)15), ChatColor.GRAY + "All items below this are in the hotbar.", 1));
		}
		for(int i = 45; i<54; i++) {
			inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)15), ChatColor.GRAY + "All items above this are in the hotbar.", 1));
		}
		//Clear Area
		for(int i = 0; i<27; i++) {
			inv.setItem(i, new ItemStack(Material.GLASS));
		}
		//Clear - ON
		for(int i = 0; i<27; i++) {
			inv.setItem(i, new ItemStack(Material.AIR));
		}
		Configuration config2 = Configuration.getConfig(p.getUniqueId().toString(),"PlayerLayouts");
		for(String s : config2.getConfigurationSection(c.getName()+".").getKeys(false)) {
			if(config2.getInt(c.getName()+"."+s) < 35 && config2.getInt(c.getName()+"."+s) > 27) {
				inv.setItem(config2.getInt(c.getName()+"."+s)+9, Utils.getNewStartingItems(p, c).get(Integer.valueOf(s)));
				continue;
			}
			inv.setItem(config2.getInt(c.getName()+"."+s), Utils.getNewStartingItems(p, c).get(Integer.valueOf(s)));
		}
		inv.setItem(48, ItemStackCreator.createItem(new ItemStack(Material.ARROW), ChatColor.GREEN + "Go Back", 1));
		inv.setItem(49, ItemStackCreator.createItem(new ItemStack(Material.CHEST), ChatColor.GREEN + "Save", 1));
		inv.setItem(50, ItemStackCreator.createItem(new ItemStack(Material.TNT), ChatColor.RED + "Reset Layout", 1,Arrays.asList("§7Warning! This will reset","§7the layout and cannot be","§7undone.")));
		p.openInventory(inv);
	}
	
	public static void OneOnOneGUI(Player p,ArrayList<Player> list1, ArrayList<Player> list2) {
		Inventory inv = Bukkit.createInventory(null, 54,"Class Interface");
		MPlayer player = MPlayerManager.getMPlayer(p.getName());
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)8));
		}
		int i  = 10;
		for(Class c : ClassManager.getClasses()) {
			if(c.getType() == ClassType.HERO) continue;
			if(player.hasClass(c))
			inv.setItem(i, ItemStackCreator.createItem(c.getIcon(), "§a"+c.getName(), 1,fixDescription(c.getDescription())));
			else
				inv.setItem(i, ItemStackCreator.createItem(c.getIcon(), "§c"+c.getName(), 1,fixDescription(c.getDescription())));
			i++;
		}
		i = 29;
		for(Class c : ClassManager.getClasses()) {
			if(c.getType() == ClassType.NORMAL) continue;
			if(i == 34) {
				i+=5;
			}
			if(player.hasClass(c))
			inv.setItem(i, ItemStackCreator.createItem(c.getIcon(), "§a"+c.getName(), 1,fixDescription(c.getDescription())));
			else
				inv.setItem(i, ItemStackCreator.createItem(c.getIcon(), "§c"+c.getName(), 1,fixDescription(c.getDescription())));
			i++;
		}
		inv.setItem(45,ItemStackCreator.createItem(new ItemStack(Material.BARRIER), "§cCancel", 1,Arrays.asList(ChatColor.GRAY + "Click to cancel duel")));
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.BLUE + "Group 1: ");
		for(Player ps : list1) {
			if(MPlayerManager.getMPlayer(ps.getName()).getCurrentDuelClass() == null) {
				lore.add(ChatColor.RED + ps.getName() + " - NOT READY");
			} else {
				lore.add(ChatColor.GREEN + ps.getName() + " - READY - " + MPlayerManager.getMPlayer(ps.getName()).getCurrentDuelClass().getName());
			}
		}
		lore.add(ChatColor.BLUE + "Group 2: ");
		for(Player ps : list2) {
			if(MPlayerManager.getMPlayer(ps.getName()).getCurrentDuelClass() == null) {
				lore.add(ChatColor.RED + ps.getName() + " - NOT READY");
			} else {
				lore.add(ChatColor.GREEN + ps.getName() + " - READY - " + MPlayerManager.getMPlayer(ps.getName()).getCurrentDuelClass().getName());
			}
		}
		inv.setItem(53,ItemStackCreator.createItem(new ItemStack(Material.BOAT), "§6Information", 1,lore));
		p.openInventory(inv);
	}
	
	public static ArrayList<String> getTeamUpgradeLore(Player p,int upgrade) {
		ArrayList<String> lore = new ArrayList<>();
		int price = 0;
		if(upgrade == 1) {
			lore.add(ChatColor.GRAY + "Play with " + ChatColor.RED + "1" + ChatColor.GRAY + " friend!");
		}
		if(upgrade == 2) {
			lore.add(ChatColor.GRAY + "Play with " + ChatColor.RED + "2" + ChatColor.GRAY + " friends!");
			price = 20000;
		}
		if(upgrade == 3) {
			lore.add(ChatColor.GRAY + "Play with " + ChatColor.RED + "3" + ChatColor.GRAY + " friends!");
			price = 30000;
		}
		if(MPlayerManager.getMPlayer(p.getName()).getTeamUpgrade() < upgrade) {
			lore.add("");
			lore.add(ChatColor.GRAY + "Cost: " + ChatColor.GOLD + price);
		}
		return lore;
	}
	
	public static void openTeamUpgradeGUI(Player p) {
		MPlayer player = MPlayerManager.getMPlayer(p.getName());
		Inventory inv = Bukkit.createInventory(null, 9*4,ChatColor.GOLD + "Team Shop");
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, ItemStackUtil.createItem(Material.STAINED_GLASS_PANE, 1, 7, " ", null));
		}
		int upgrade = 0;
		for(int i = 3+9; i<6+9; i++) {
			upgrade++;
			if(player.getTeamUpgrade() >= upgrade) {
				inv.setItem(i, ItemStackUtil.createItem(Material.STAINED_CLAY, 1, 13, "§aTeam Upgrade: " + Timeformatter.formatForUpgradeLvls(upgrade), getTeamUpgradeLore(p,upgrade)));
			} else if(player.getCoins() >= player.getCoinsForNextTeamUpgrade() && player.getTeamUpgrade() + 1 == upgrade) {
				inv.setItem(i, ItemStackUtil.createItem(Material.STAINED_CLAY, 1, 4,"§cTeam Upgrade: " + Timeformatter.formatForUpgradeLvls(upgrade), getTeamUpgradeLore(p,upgrade)));
			} else {
				inv.setItem(i, ItemStackUtil.createItem(Material.STAINED_CLAY, 1, 14,"§cTeam Upgrade: " + Timeformatter.formatForUpgradeLvls(upgrade), getTeamUpgradeLore(p,upgrade)));
			}
		}
		inv.setItem(30, ItemStackCreator.createItem(Material.ARROW, "§aGo Back", 1));
		inv.setItem(31, ItemStackCreator.createItem(Material.EMERALD, "§6Coins: §a"+player.getCoins(), 1));
		p.openInventory(inv);
	}
	
	public static void openStats(Player p, OfflinePlayer target) {
		MPlayer tplayer = MPlayerManager.getMPlayer(target.getName());
		if(tplayer == null) return;
		Inventory inv = Bukkit.createInventory(null,9*3,target.getName()+"'s Stats");
		for(int i = 0; i < inv.getSize(); i++) 
			inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7), "", 1));
		inv.setItem(13-9, ItemStackCreator.createItem(new ItemStack(Material.GOLD_NUGGET), "§7Coins: §6" + tplayer.getCoins(), 1));
		inv.setItem(13, ItemStackCreator.createItem(new ItemStack(Material.DIAMOND), "§7Duel Wins: §b" + tplayer.getWins(), 1));
		inv.setItem(12, ItemStackCreator.createItem(new ItemStack(Material.DIAMOND_SWORD), "§7Kills: §a" + tplayer.getKills(), 1));
		inv.setItem(14, ItemStackCreator.createItem(new ItemStack(Material.TNT), "§7Deaths: §c" + tplayer.getDeaths(), 1));
		if(tplayer.getCurrentClass() == null) {
			inv.setItem(13+9, ItemStackCreator.createItem(new ItemStack(Material.PAPER), "§7Current Class: §e" + "None", 1));
		} else {
			inv.setItem(13+9, ItemStackCreator.createItem(tplayer.getCurrentClass().getIcon(), "§7Current Class: §e" + tplayer.getCurrentClass().getName(), 1));
		}
		p.openInventory(inv);
	}

	public static List<String> fixDescription(List<String> firstList){
		List<String> list = new ArrayList<>();
		for(String string : firstList){
			list.add("§7"+string);
		}
		return list;
	}

	public static int getColor(Player p,me.main.yoni.ClassSystem.Class c, UpgradeType type, int count) {
		Upgrade u = new Upgrade(p, c, type);
		MPlayer player = MPlayerManager.getMPlayer(p.getName());
		if(player.getCoins() >= c.getUpgradePrice(u.getNextUpgrade()) && count == u.getNextUpgrade() && u.getCurrentUpgrade() != u.getNextUpgrade()) {
			return 4;
		}
		if(count <= u.getCurrentUpgrade()) { 
			return 5;
		}
		if(count > u.getCurrentUpgrade()) {
			return 14;
		}
		return 14;
	}

	public static void openUpgradeShop(Player p, me.main.yoni.ClassSystem.Class c){
		Inventory inv = Bukkit.createInventory(null, 9*3, "§7"+c.getName()+" - §aShop");
		for(int i = 0; i < inv.getSize(); i++) inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		int count = 1;
		for(int i = 9; i < 18; i++) {
			Upgrade u = new Upgrade(p, c, UpgradeType.KIT);
			ItemStack item = new ItemStack(Material.STAINED_CLAY,1,(byte)getColor(p, c, UpgradeType.KIT, count));
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(u.getCurrentUpgrade() >= count ? ChatColor.GREEN + "Kit Upgrade: " + c.getName() + " " + Timeformatter.formatForUpgradeLvls(count) : ChatColor.RED + "Kit Upgrade: " + c.getName() + " " + Timeformatter.formatForUpgradeLvls(count));
			ArrayList<String> lore = new ArrayList<>();
			for(ItemStack t : c.getStartingItems(count).values()) {
				lore.add(ChatColor.GRAY + Utils.toString(t));
			}
			if(count > u.getCurrentUpgrade()){
				lore.add("");
				lore.add(ChatColor.GRAY + "Cost: " + ChatColor.GOLD + c.getUpgradePrice(count));
			}
			im.setLore(lore);
			item.setItemMeta(im);
			inv.setItem(i, item);
			count++;
		}
		count = 1;
		for(int i = 0; i<9; i++) {
			Upgrade u = new Upgrade(p, c, UpgradeType.ABILITY);
			ItemStack item = new ItemStack(Material.STAINED_CLAY,1,(byte)getColor(p, c, UpgradeType.ABILITY, count));
			ItemMeta im = item.getItemMeta();
			im.setDisplayName(u.getCurrentUpgrade() >= count ? ChatColor.GREEN + "Ability: " + c.getAbilityName() + " " + Timeformatter.formatForUpgradeLvls(count) : ChatColor.RED + "Ability: " + c.getAbilityName() + " " + Timeformatter.formatForUpgradeLvls(count));
			ArrayList<String> lore = new ArrayList<>();
			for(String g : c.getAbilityDescription(count)) {
				lore.add(g);
			}
			if(count > u.getCurrentUpgrade()){
				lore.add("");
				lore.add(ChatColor.GRAY + "Cost: " + ChatColor.GOLD + c.getUpgradePrice(count));
			}
			im.setLore(lore);
			item.setItemMeta(im);
			inv.setItem(i, item);
			count++;
		}
		inv.setItem(21, ItemStackCreator.createItem(Material.ARROW, "§aGo Back", 1));
		inv.setItem(22, ItemStackCreator.createItem(Material.EMERALD, "§6Coins: §a"+MPlayerManager.getMPlayer(p.getName()).getCoins(), 1));
		if(c.getType() == ClassType.HERO) {
			if(new Upgrade(p, c, UpgradeType.KIT).hasPrestige() == false)
			inv.setItem(23, ItemStackCreator.createItem(Material.GOLDEN_APPLE, "§cPrestige Level", 1,Arrays.asList("§7Max out all skills to access the prestige level!","§7Grants +2§c❤§7 every life","","§7Cost: §6500000")));
			else
				inv.setItem(23, ItemStackCreator.createItem(Material.GOLDEN_APPLE, "§aPrestige Level", 1,Arrays.asList("§7Max out all skills to access the prestige level!","§7Grants +2§c❤§7 every life")));
		} else {
			if(new Upgrade(p, c, UpgradeType.KIT).hasPrestige() == false)
			inv.setItem(23, ItemStackCreator.createItem(Material.GOLDEN_APPLE, "§cPrestige Level", 1,Arrays.asList("§7Max out all skills to access the prestige level!","§7Grants +2§c❤§7 every life","","§7Cost: §6250000")));
			else
				inv.setItem(23, ItemStackCreator.createItem(Material.GOLDEN_APPLE, "§aPrestige Level", 1,Arrays.asList("§7Max out all skills to access the prestige level!","§7Grants +2§c❤§7 every life")));
		}
		p.openInventory(inv);
	}
	
	public static ArrayList<String> getLoreForUpgrade(int upgrade, boolean lore) {
		int amount = 32-16;
		int time = 0;
		for(int i = 0; i<upgrade; i++) {
			if(i == 1) {
				time+=2;
				continue;
			}
			time+=3;
		}
		for(int i = 0; i<upgrade; i++) {
			amount+=16;
		}
		ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.GRAY + "You will receive §c" + amount + " §7blocks");
		list.add(ChatColor.GRAY + "that upon placing they will");
		list.add(ChatColor.GRAY + "stay for §c"+time+"§7 seconds.");
		if(lore) {
			list.add("");
			list.add(ChatColor.GOLD+"Cost: " + ChatColor.GREEN + getCoinsForNextBlockUpgrade(upgrade));
		}
		return list;
	}

	public static int getCoinsForNextBlockUpgrade(int upgrade) {
		if(upgrade == 2) {
			return 1500;
		}
		if(upgrade == 3) {
			return 3000;
		}
		return 3000;
	}
	
	public static int getTimeBlocks(Player p) {
		MPlayer player = MPlayerManager.getMPlayer(p.getName());
		int time = 0;
		for(int i = 0; i<player.getBlocksUpgrade(); i++) {
			if(i == 1) {
				time+=2;
				continue;
			}
			time+=3;
		}
		return time;
	}
	
	public static void openBlockUpgrades(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27+9, "§7Block Upgrades");
		for(int i = 0; i < inv.getSize(); i++) inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		int count = 0;
		MPlayer player = MPlayerManager.getMPlayer(p.getName());
		for(int i = 12; i<15; i++) {
			count++;
			if(player.getBlocksUpgrade() >= count) {
				inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_CLAY,1,(short)5), "§aBlock Upgrade: " + Timeformatter.formatForUpgradeLvls(count), 1,getLoreForUpgrade(count,false)));
			} else{
				if(player.getBlocksUpgrade() < count && player.getCoins() >= player.getCoinsForNextBlockUpgrade() && player.getBlocksUpgrade() + 1 == count) {
					inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_CLAY,1,(short) 4), "§cBlock Upgrade: " + Timeformatter.formatForUpgradeLvls(count), 1,getLoreForUpgrade(count,true)));
				} else {
					inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_CLAY,1,(short) 14), "§cBlock Upgrade: " + Timeformatter.formatForUpgradeLvls(count), 1,getLoreForUpgrade(count,true)));
				}
			}
		}
		inv.setItem(30, ItemStackCreator.createItem(Material.ARROW, "§aGo Back", 1));
		inv.setItem(31, ItemStackCreator.createItem(Material.EMERALD, "§6Coins: §a"+player.getCoins(), 1));
		p.openInventory(inv);
	}

	public static void openShop(Player p){
		Inventory inv = Bukkit.createInventory(null, 9*5, "§7Shop");
		for(int i = 0; i < inv.getSize(); i++) inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		inv.setItem(11, ItemStackCreator.createItem(Material.IRON_SWORD, "§aClasses", 1,Arrays.asList("§7Click to open the Normal Classes gui.")));
		inv.setItem(30, ItemStackCreator.createItem(Material.COBBLESTONE, "§aBlock Upgrades", 1,Arrays.asList("§7Click to open the block upgrades gui.")));
		inv.setItem(15, ItemStackCreator.createItem(Material.DIAMOND_SWORD, "§aHero Classes", 1,Arrays.asList("§7Click to open the Hero Classes gui."))); 
		if(MPlayerManager.getMPlayer(p.getName()).boughtTeam()) {
			inv.setItem(31, ItemStackCreator.createItem(Material.CAULDRON_ITEM, "§aTeam Option - BETA", 1,Arrays.asList("§7Play with your friends","§7against other players!","","§7Click for options")));
		} else {
			inv.setItem(31, ItemStackCreator.createItem(Material.CAULDRON_ITEM, "§cTeam Option - BETA", 1,Arrays.asList("§7Play with your friends","§7against other players!","","§6Cost: §a6500")));
		}
		inv.setItem(32, ItemStackCreator.createItem(new ItemStack(Material.WORKBENCH), ChatColor.GREEN + "Layout Editor", 1,Arrays.asList("§7Allows you to customize the","§7layout of kit items for any","§7of your classes.")));
		p.openInventory(inv);
	}

	public static void openClassSelector(Player p){
		Inventory inv = Bukkit.createInventory(null, 27, "§7Class Selector");
		for(int i = 0; i < inv.getSize(); i++) inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		inv.setItem(11, ItemStackCreator.createItem(Material.IRON_SWORD, "§aClasses", 1,Arrays.asList("§7Click to open the Normal Classes gui.")));
		inv.setItem(15, ItemStackCreator.createItem(Material.DIAMOND_SWORD, "§aHero Classes", 1,Arrays.asList("§7Click to open the Hero Classes gui.")));
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() == null) {
			inv.setItem(13, ItemStackCreator.createItem(new ItemStack(Material.PAPER), "§aCurrent Class: " + ChatColor.GOLD + "None", 1,null));
		} else {
			inv.setItem(13, ItemStackCreator.createItem(MPlayerManager.getMPlayer(p.getName()).getCurrentClass().getIcon(), "§aCurrent Class: " + ChatColor.GOLD + MPlayerManager.getMPlayer(p.getName()).getCurrentClass().getName(), 1,Arrays.asList("§7Click to Play!")));
		}
		p.openInventory(inv);
	}

	public static void openClassesSelector(Player p,ClassType type){
		Inventory inv = Bukkit.createInventory(null, 9*4, "§7Classes");
		if(type == ClassType.HERO) inv = Bukkit.createInventory(null, 9*5, "§7Hero Classes");
		for(int i = 0; i <= 9; i++) inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		inv.setItem(17, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		inv.setItem(18, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		MPlayer player = MPlayerManager.getMPlayer(p.getName());
		int i = 10;
		if(type == ClassType.HERO) {
			i = 4;
		}
		for(me.main.yoni.ClassSystem.Class mwClass : ClassManager.getClasses()) {
			if(i == 16 && type == ClassType.HERO){
				i+=5;
			}
			if(mwClass.getType() == type){
				if(player.hasClass(mwClass))
				inv.setItem(i, ItemStackCreator.createItem(mwClass.getIcon(), "§a"+mwClass.getName(), 1,fixDescription(mwClass.getDescription())));
				else
					inv.setItem(i, ItemStackCreator.createItem(mwClass.getIcon(), "§c"+mwClass.getName(), 1,fixDescription(mwClass.getDescription())));
				}
//			Bukkit.broadcastMessage(""+i);
			i++;
			}
			while(inv.firstEmpty() != -1)
			inv.setItem(inv.firstEmpty(), ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
			me.main.yoni.ClassSystem.Class c = MPlayerManager.getMPlayer(p.getName()).getCurrentClass();
		if(type == ClassType.HERO) {
			inv.setItem(39, ItemStackCreator.createItem(Material.ARROW, "§aGo Back", 1));
			if(c == null) {
				inv.setItem(40, ItemStackCreator.createItem(Material.EMERALD, "§6Current Class: §a"+"None", 1));
			} else {
				inv.setItem(40, ItemStackCreator.createItem(Material.EMERALD, "§6Current Class: §a"+c.getName(), 1));
			}
		} else {
			inv.setItem(30, ItemStackCreator.createItem(Material.ARROW, "§aGo Back", 1));
			if(c == null) {
				inv.setItem(31, ItemStackCreator.createItem(Material.EMERALD, "§6Current Class: §a"+"None", 1));
			} else {
				inv.setItem(31, ItemStackCreator.createItem(Material.EMERALD, "§6Current Class: §a"+c.getName(), 1));
			}
		}
		p.openInventory(inv);
	}
	
	public static void confirmBuy(Player p, me.main.yoni.ClassSystem.Class c) {
		Inventory inv = Bukkit.createInventory(null, 27,"§7Confirm - " + c.getName());
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7), " ", 1));
		}
		inv.setItem(2+9, ItemStackCreator.createItem(new ItemStack(Material.STAINED_CLAY,1,(byte)13), "§aCONFIRM", 1));
		inv.setItem(6+9, ItemStackCreator.createItem(new ItemStack(Material.STAINED_CLAY,1,(byte)14), "§cCANCEL", 1));
		ArrayList<String> lore = new ArrayList<>();
		for(String s : c.getDescription()) {
			lore.add(ChatColor.GRAY + s);
		}
		lore.add("");
		lore.add(ChatColor.GOLD + "Cost: " + ChatColor.GREEN + c.getPrice());
		inv.setItem(4+9, ItemStackCreator.createItem(c.getIcon(), "§c"+c.getName(), 1,lore));
		p.openInventory(inv);
	}
	
	public static void confirmTeamBuy(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27,"§7Confirm - " + "§c§lTeam Option");
		for(int i = 0; i<inv.getSize(); i++) {
			inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7), " ", 1));
		}
		inv.setItem(2+9, ItemStackCreator.createItem(new ItemStack(Material.STAINED_CLAY,1,(byte)13), "§aCONFIRM", 1));
		inv.setItem(6+9, ItemStackCreator.createItem(new ItemStack(Material.STAINED_CLAY,1,(byte)14), "§cCANCEL", 1));
		ArrayList<String> lore = new ArrayList<>();
		lore.add("");
		lore.add(ChatColor.GOLD + "Cost: " + ChatColor.GREEN + "25000");
		inv.setItem(4+9, ItemStackCreator.createItem(Material.CAULDRON_ITEM, "§c§l"+"Team Option", 1,lore));
		p.openInventory(inv);
	}

	public static void openClassesShop(Player p,ClassType type){
		Inventory inv = Bukkit.createInventory(null, 9*4, "§7Classes - §aShop");
		if(type == ClassType.HERO) inv = Bukkit.createInventory(null, 9*5, "§7Hero Classes - §aShop");
		MPlayer player = MPlayerManager.getMPlayer(p.getName());
		for(int i = 0; i <= 9; i++) inv.setItem(i, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		inv.setItem(17, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		inv.setItem(18, ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		int i = 10;
		if(type == ClassType.HERO) {
			i = 4;
		}
		for(me.main.yoni.ClassSystem.Class mwClass : ClassManager.getClasses()) {
			if(i == 16 && type == ClassType.HERO) {
				i+=5;
			}
			if(mwClass.getType() == type){
				List<String> list = new ArrayList<>();
				for(String s : mwClass.getDescription()){
					list.add(s);
				}
				list.add("");
				list.add("Cost: §6"+mwClass.getPrice());
				inv.setItem(i, ItemStackCreator.createItem(mwClass.getIcon(), (player.hasClass(mwClass) ? "§a" : "§c")+mwClass.getName(), 1,(player.hasClass(mwClass) ? fixDescription(mwClass.getDescription()) : fixDescription(list))));
			}
			i++;
		}	
		while(inv.firstEmpty() != -1)
			inv.setItem(inv.firstEmpty(), ItemStackCreator.createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 7), "§a", 1));
		if(type == ClassType.HERO) {
			inv.setItem(39, ItemStackCreator.createItem(Material.ARROW, "§aGo Back", 1));
			inv.setItem(40, ItemStackCreator.createItem(Material.EMERALD, "§6Coins: §a"+player.getCoins(), 1));
		} else {
			inv.setItem(30, ItemStackCreator.createItem(Material.ARROW, "§aGo Back", 1));
			inv.setItem(31, ItemStackCreator.createItem(Material.EMERALD, "§6Coins: §a"+player.getCoins(), 1));
		}
		p.openInventory(inv);
	}
}
