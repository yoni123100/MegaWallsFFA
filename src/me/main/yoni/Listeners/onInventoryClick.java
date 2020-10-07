package me.main.yoni.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import me.main.yoni.Main;
import me.main.yoni.API.Inventories;
import me.main.yoni.API.Timeformatter;
import me.main.yoni.API.Utils;
import me.main.yoni.BoosterSystem.Booster;
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
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class onInventoryClick implements Listener {
	
	public static HashMap<Player, String> selectedClass = new HashMap<>();
	
	public static ArrayList<String> save = new ArrayList<>();
	
	public static ArrayList<String> spec = new ArrayList<>();
	

	public static String getFullMessage() {
		return ChatColor.RED + "You can't join this team, because it's already full!";
	}
	
	@EventHandler
	public void close(final InventoryCloseEvent e) {
		if(!e.getInventory().getName().contains("Editor") && e.getInventory().getName().contains("Layout")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					Utils.setNormalItems((Player) e.getPlayer());
				}
			}, 5);
		}
		if(!save.contains(e.getPlayer().getName())) return;
		for(Class c : ClassManager.getClasses()) {
			if(e.getInventory().getName().equalsIgnoreCase("Edit " + c.getName() + " Layout")) {
				Upgrade upgrade = new Upgrade((Player) e.getPlayer(), c, UpgradeType.KIT);
				int count = 0;
				int im = 0;
				for(ItemStack item : e.getInventory().getContents()) {
					im=0;
					if(item == null || item.getType() == Material.AIR) {
						count++;
						continue;
					}
					for(ItemStack i : Utils.getNewStartingItems((Player) e.getPlayer(), c).values()) {
						if(i.equals(item)) {
							MPlayerManager.setItem((Player) e.getPlayer(), c, im, count);
							break;
						}
						im++;
					}
					count++;
				}
				save.remove(e.getPlayer().getName());
				e.getPlayer().sendMessage(ChatColor.GREEN + "Layout for " + c.getName() + " has been saved!"); 
			}
		}
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e){
		if(e.getPlayer().getOpenInventory() != null) {
			if(e.getPlayer().getOpenInventory().getTopInventory().getName().contains("Layout")) {
				e.getPlayer().getOpenInventory().getTopInventory().addItem(e.getItemDrop().getItemStack());
				e.getItemDrop().remove();
				e.getPlayer().getOpenInventory().setCursor(new ItemStack(Material.AIR));
				e.getPlayer().updateInventory();
			}
		}
		if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
			e.getPlayer().updateInventory();
		}
	}
	
	public static void specPlayer(final Player p, Player target) {
		if(target == null) {
			p.sendMessage(ChatColor.RED + "Target not found!");
			return;
		}
		p.sendMessage(ChatColor.GREEN + "You have been teleported to " + target.getDisplayName() + ChatColor.GREEN + ".");
		p.sendMessage(ChatColor.GREEN + "You can leave this mode by typing " + ChatColor.GOLD + "/lobby" + ChatColor.GREEN + ".");
		p.teleport(target.getLocation().add(0, 2, 0));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				p.setGameMode(GameMode.SPECTATOR);
				
			}
		}, 5);
		if(!spec.contains(p.getName())) {
			spec.add(p.getName());
		}
		p.closeInventory();
	}
	
	@EventHandler
	public void gg(InventoryClickEvent e) {
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		for(Class c : ClassManager.getClasses()) {
			Player p = (Player)e.getWhoClicked();
			if(e.getInventory().getName().equalsIgnoreCase("Edit " + c.getName() + " Layout")) {
				if(e.getClick().equals(ClickType.NUMBER_KEY)) {e.setCancelled(true); return;}
				if(e.getClick() == ClickType.DROP) {
					e.setCancelled(true);
					return;
				}
				if(e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
					e.setCancelled(true);
					return;
				}
				if(e.getRawSlot() == 48) {
					Inventories.openGlobalInventroyEditor(p);
					e.setCancelled(true);
				}
				if(e.getCurrentItem().getType() == Material.TNT) {
					e.setCursor(new ItemStack(Material.AIR));
					p.sendMessage(ChatColor.RED + "Layout has been reset!");
					MPlayerManager.resetLayout(p, c);
					Inventories.openInventroyEditor(p, c);
					e.setCancelled(true);
					p.updateInventory();
					return;
				}
				if(e.getCurrentItem().getType() == Material.CHEST) {
					e.setCursor(new ItemStack(Material.AIR));
					save.add(p.getName());
					p.closeInventory();
					e.setCancelled(true);
					return;
				}
			}
		}
		if(e.getInventory().getName().equalsIgnoreCase("Layout Editor")) {
			Player p = (Player)e.getWhoClicked();
			e.setCancelled(true);
			if(e.getCurrentItem().getType() == Material.ARROW) {
				Inventories.openShop(p);
				return;
			}
			if(!e.getCurrentItem().hasItemMeta()) return;
			String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().replace("Edit ", "").replace(" Layout", ""));
			if(ClassManager.getClass(name) == null) return;
			Inventories.openInventroyEditor(p, ClassManager.getClass(name));
		}
		if(e.getInventory().getName().contains("Please choose a player")) {
			final Player p = (Player)e.getWhoClicked();
			e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			if(!e.getCurrentItem().hasItemMeta()) return;
			String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
			if(Bukkit.getPlayer(name) == null) {
				p.sendMessage(ChatColor.RED + "This player is not online!");
				return;
			}
			Player target = Bukkit.getPlayer(name);
			if(target == null) {
				p.sendMessage(ChatColor.RED + "Target not found!");
				return;
			}
			specPlayer(p, target);
			return;
		}
		if (e.getInventory().getName().contains("Stats") || e.getInventory().getName().contains("'s Classes")) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void statsClick(InventoryClickEvent e) {
		if(e.getInventory().getName().contains("My Network Boosters")) {
			Player p = (Player)e.getWhoClicked();
			e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			if(!e.getCurrentItem().hasItemMeta()) return;
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.RED + "")) {
				p.sendMessage(ChatColor.RED + "You already activated this booster!");
				return;
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.GREEN + "")) {
				if(BoosterManager.getBoosters().size() > 0) {
					p.sendMessage(ChatColor.RED + "There is already an Activated Network booster!");
					return;
				}
				if(BoosterManager.getBooster(p) != null) {
					p.sendMessage(ChatColor.RED + "You already activated a booster!");
					return;
				}
				Booster b = new Booster(p,60*60,new ArrayList<String>());
				BoosterManager.create(b);
				p.sendMessage(ChatColor.GREEN + "You activated a Network Booster!");
				MPlayerManager.getMPlayer(p.getName()).removeNetworkBoosters(1);
				Inventories.openBoosterInventory(p);
				Bukkit.broadcastMessage(ChatColor.GREEN + p.getName() + " has activated a Network Booster!");
				Bukkit.broadcastMessage(ChatColor.GREEN + "You can type /tip " + p.getName() + " and earn free coins :)");
				return;
			}
			return;
		}
		if(e.getInventory().getName().contains("Class Interface")) {
			Player p = (Player)e.getWhoClicked();
			e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			if(e.getCurrentItem().getType() == Material.BARRIER) {
				if(!OneOnOneListener.accepted.containsKey(p)) {
					p.sendMessage(ChatColor.RED + "You're not the team leader!");
					return;
				}
				OneOnOneListener.accepted.remove(p);
				p.sendMessage(ChatColor.RED + "You canceled the duel!");
				return;
			}
			String name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
			if(ClassManager.getClass(name) == null) return;
			if(!MPlayerManager.getMPlayer(p.getName()).hasClass(ClassManager.getClass(name))) {
				p.sendMessage(ChatColor.RED + "You don't own this class!");
				return;
			}
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(player.getCurrentDuelClass() == ClassManager.getClass(name)) {
				p.sendMessage(ChatColor.RED + "You already choose this class!");
				return;
			}
			MPlayerManager.getMPlayer(p.getName()).setCurrentDuelClass(ClassManager.getClass(name));
			p.sendMessage(ChatColor.GREEN + "You selected the " + ChatColor.YELLOW + name + ChatColor.GREEN + " class! please wait until the rest of players will choose their class too!");
			return;
		}
		if(e.getInventory().getName().contains("§7Block Upgrades")) {
			Player p = (Player)e.getWhoClicked();
			e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			if(e.getCurrentItem().getType() == Material.ARROW) {
				Inventories.openShop((Player) e.getWhoClicked());
				return;
			}
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(!(e.getRawSlot() >= 12 && e.getRawSlot() <= 14)) return;
			Upgrade u = new Upgrade(p, UpgradeType.BLOCKS);
			int upgrade = Timeformatter.formatFromLetters(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("Team Upgrade: ", "")));
			if(u.getCurrentUpgrade() >= upgrade) {
				p.sendMessage(ChatColor.RED + "You already upgraded this level!");
				return;
			}
			int lvl = u.getCurrentUpgrade() + 1;
			if(u.getCurrentUpgrade() + 1 < upgrade) {
				p.sendMessage(ChatColor.RED + "You must upgrade level " + lvl + " first!");
				return;
			}
			if(player.getCoins() >= player.getCoinsForNextBlockUpgrade()) {
				p.sendMessage("§6You've purchased §a"+"Block Upgrade: "+Timeformatter.formatForUpgradeLvls(lvl)+"§6.");
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
				player.removeCoins(player.getCoinsForNextBlockUpgrade());
				u.upgrade();
				Main.updateScoreboard(p);
				Inventories.openBlockUpgrades(p);
			} else {
				p.sendMessage(ChatColor.RED + "You dont have enough coins to buy this upgrade!");
			}
			return;
		}
		if(e.getInventory().getName().contains("§6Team Shop")) {
			e.setCancelled(true);
			Player p = (Player)e.getWhoClicked();
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			if(!e.getCurrentItem().hasItemMeta()) return;
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§aGo Back")) {
				Inventories.openShop(p);
				return;
			}
			if(!(e.getRawSlot() >= 12 && e.getRawSlot() <= 14)) return;
			Upgrade u = new Upgrade(p, UpgradeType.TEAM);
			int upgrade = Timeformatter.formatFromLetters(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("Team Upgrade: ", "")));
			if(u.getCurrentUpgrade() >= upgrade) {
				p.sendMessage(ChatColor.RED + "You already upgraded this level!");
				return;
			}
			int lvl = u.getCurrentUpgrade() + 1;
			if(u.getCurrentUpgrade() + 1 < upgrade) {
				p.sendMessage(ChatColor.RED + "You must upgrade level " + lvl + " first!");
				return;
			}
			if(player.getCoins() >= player.getCoinsForNextTeamUpgrade()) {
				p.sendMessage("§6You've purchased §a"+"Team Upgrade: "+Timeformatter.formatForUpgradeLvls(lvl)+"§6.");
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
				player.removeCoins(player.getCoinsForNextTeamUpgrade());
				u.upgrade();
				Main.updateScoreboard(p);
				Inventories.openTeamUpgradeGUI(p);
			} else {
				p.sendMessage(ChatColor.RED + "You dont have enough coins to buy this upgrade!");
			}
			return;
		}
		if(e.getInventory().getName().contains("Stats")) {
			e.setCancelled(true);
			return;
		}
		if(e.getInventory().getName().contains("§7Confirm - ")) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			if(!e.getCurrentItem().hasItemMeta()) return;
			Player p = (Player)e.getWhoClicked();
			String name = ChatColor.stripColor(ChatColor.stripColor(e.getInventory().getName().replaceAll("§7Confirm - ", "")));
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			if(name.equalsIgnoreCase("Team Option")) {
				if(e.getCurrentItem().getType() != Material.STAINED_CLAY) return;
				if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§a")) {
					if(player.getCoins() >= 6500){
						player.removeCoins(6500);
						player.buyTeam();
						Main.updateScoreboard(p);
						p.sendMessage("§6You've purchased §a"+"Team Option"+"§6.");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2, 2);
						p.closeInventory();
					}else{
						p.sendMessage("§cYou don't have enough coins to buy Team Option.");
						p.closeInventory();
					}
					return;
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§c")) {
					Inventories.openShop(p);
					return;
				}
				return;
			}
			Class mwClass = ClassManager.getClass(name);
			if(mwClass == null) return;
			if(mwClass.getName().equalsIgnoreCase(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()))) return;
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§a")) {
				if(player.getCoins() >= mwClass.getPrice()){
					player.addClass(mwClass);
					player.removeCoins(mwClass.getPrice());
					Main.updateScoreboard(p);
					p.sendMessage("§6You've purchased the §a"+mwClass.getName()+"§6 Class.");
					Inventories.openClassesShop(p, ClassType.HERO);
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 2, 2);
					p.closeInventory();
				}else{
					p.sendMessage("§cYou don't have enough coins to buy this class.");
					p.closeInventory();
				}
				return;
			}
			if(e.getCurrentItem().getItemMeta().getDisplayName().contains("§c")) {
				Inventories.openClassesShop(p, mwClass.getType());
				return;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void inventoryClick(InventoryClickEvent e){
		if(e.getCurrentItem() == null) return;
		if(e.getCurrentItem().getType() == null) return;
		if(e.getCurrentItem().getItemMeta() == null) return;
		if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
		if(e.getWhoClicked() instanceof Player){
			Player p = (Player) e.getWhoClicked();
			if(MPlayerManager.getMPlayer(p.getName()) == null){
				MPlayerManager.create(p);
			}
			MPlayer player = MPlayerManager.getMPlayer(p.getName());
			Inventory inv = e.getInventory();
			String name = inv.getName();
			for(Class mwClass : ClassManager.getClasses()){
				if(name.contains("§7"+mwClass.getName()+" - §aShop")){
					e.setCancelled(true);
					p.updateInventory();
					if(e.getRawSlot() == 21){
						if(mwClass.getType() == ClassType.HERO){
							Inventories.openClassesShop(p,ClassType.HERO);
						}else{
							Inventories.openClassesShop(p,ClassType.NORMAL);
						}
					}
					if(e.getRawSlot() == 23){
						int price = 250000;
						if(mwClass.getType() == ClassType.HERO)
							price = 500000;
						if(new Upgrade(p, mwClass, UpgradeType.KIT).hasPrestige() == false){
							if(player.getCoins() >= price){
								boolean has = true;
								for(UpgradeType type : UpgradeType.values()) {
									if(type == UpgradeType.BLOCKS || type == UpgradeType.TEAM) continue;
									if(new Upgrade(p, mwClass, type).getCurrentUpgrade() != 9) {
										p.sendMessage("§cYou have to buy all skills of this class first!");
										has = false;
										break;
									}
								}
								if(!has) return;
								player.removeCoins(price);
								new Upgrade(p, mwClass, UpgradeType.KIT).addPrestige();
								p.sendMessage("§6You purchased: §aPrestige");
								Inventories.openUpgradeShop(p, mwClass);
								Main.updateScoreboard(p);
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
							}else{
								p.sendMessage("§cYou don't have enough coins to buy prestige for this class.");
							}
						}else{
							p.sendMessage("§cYou already own this item!");
						}
						return;
					}
					if(e.getCurrentItem().getType() == Material.STAINED_CLAY && e.getCurrentItem().getData().getData() == 4){
						String sPrice = "";
						for(String string : e.getCurrentItem().getItemMeta().getLore()){
							if(string.contains("Cost: ")){
								sPrice = string.split(" ")[1].replace("§6", "");
							}
						}
						if(sPrice == "") return;
						int price = Integer.valueOf(sPrice);
						if(player.getCoins() >= price){	
							UpgradeType type = null;
							if(type == null && e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0].replace("§a", "").contains("Kit")){
								type = UpgradeType.KIT;
							}
							if(e.getRawSlot() < 9){
								type = UpgradeType.ABILITY;
							}
							int num = Timeformatter.formatFromLetters(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[2]);
							if(num == 0) num = Timeformatter.formatFromLetters(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[3]);
							if(num == 0) num = Timeformatter.formatFromLetters(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[4]);
							if(num == 0) num = Timeformatter.formatFromLetters(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[5]);
							if(num == 0) num = Timeformatter.formatFromLetters(e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[6]);
							if(num != 0){
								player.removeCoins(price);
								Upgrade upgrade = new Upgrade(p, mwClass, type);
								upgrade.upgrade();
								p.sendMessage("§6You purchased §a"+type.getName()+": " + mwClass.getNameByType(type) + " " + Timeformatter.formatForUpgradeLvls(upgrade.getCurrentUpgrade()) + ChatColor.GOLD + "!");
								p.playSound(p.getLocation(), Sound.NOTE_PIANO,2,2);
								Main.updateScoreboard(p);
								Inventories.openUpgradeShop(p, mwClass);
							}
						}else{
							p.sendMessage("§cYou don't have enough coins to upgrade the class.");
						}
					}
					break;
				}
			}
			if(name.equalsIgnoreCase("§7Shop")){
				e.setCancelled(true);
				p.updateInventory();
				if(e.getRawSlot() == 30) {
					Inventories.openBlockUpgrades(p);
					return;
				}
				if(e.getRawSlot() == 31) {
					if(!player.boughtTeam()) {
						if(player.getCoins() >= 6000) {
							Inventories.confirmTeamBuy(p);
							return;
						} else {
							p.sendMessage("§cYou don't have enough coins to buy Team Option.");
							return;
						}
					}
					Inventories.openTeamUpgradeGUI(p);
				}
				if(e.getRawSlot() == 32) {
					Inventories.openGlobalInventroyEditor(p);
				}
				if(e.getRawSlot() == 11){
					Inventories.openClassesShop(p,ClassType.NORMAL);
				}
				if(e.getRawSlot() == 15){
					Inventories.openClassesShop(p,ClassType.HERO);
				}
			}
			if(name.contains("Class Selector")){
				e.setCancelled(true);
				p.updateInventory();
				if(e.getRawSlot() == 13) {
					Main.randomSpawn(p);
				}
				if(e.getRawSlot() == 11){
					Inventories.openClassesSelector(p,ClassType.NORMAL);
				}
				if(e.getRawSlot() == 15){
					Inventories.openClassesSelector(p,ClassType.HERO);
				}
			}
			if(name.equalsIgnoreCase("§7Classes - §aShop") || name.equalsIgnoreCase("§7Hero Classes - §aShop")){
				e.setCancelled(true);
				p.updateInventory();
				if(e.getCurrentItem().hasItemMeta()){
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aGo Back")) {
						Inventories.openShop(p);
					}
				}
				for(Class mwClass : ClassManager.getClasses()){
					if(e.getCurrentItem().getItemMeta().getDisplayName().replace("§a", "").contains(mwClass.getName())){
						if(mwClass.getPrice() == 0 || player.hasClass(mwClass)){
							Inventories.openUpgradeShop(p, mwClass);
//							p.sendMessage(ChatColor.RED + "You've already bought this class!");
							break;
						}
//						if(player.getCoins() >= mwClass.getPrice()){
//							player.addClass(mwClass);
//							player.removeCoins(mwClass.getPrice());
//							Main.updateScoreboard(p);
//							p.sendMessage("§7You've purchased the §a"+mwClass.getName()+"§7 Class.");
//							Inventories.openClassesShop(p, ClassType.HERO);
//							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2, 2);
//							break;
//							p.sendMessage("§cYou don't have enough coins to buy this class.");
//						}
						if(player.getCoins() < mwClass.getPrice()) {
							p.sendMessage(ChatColor.RED + "You dont have enough coins for this class!");
							p.closeInventory();
							return;
						}
						Inventories.confirmBuy(p, mwClass);
					}
				}
			}
			if(name.equalsIgnoreCase("§7Classes") || name.equalsIgnoreCase("§7Hero Classes")){
				e.setCancelled(true);
				p.updateInventory();
				if(e.getCurrentItem().hasItemMeta()) {
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN+"Go Back")) {
						Inventories.openClassSelector(p);
						return;
					}
				}
				for(Class mwClass : ClassManager.getClasses()){
					if(e.getCurrentItem().getItemMeta().getDisplayName().contains(mwClass.getName())){
						if(player.hasClass(mwClass)){
							p.sendMessage("§aYou have selected the §e" + mwClass.getName() + " §aclass!");
						player.setCurrentClass(mwClass);
						Inventories.openClassesSelector(p, mwClass.getType());
						Main.updateScoreboard(p);
						break;
					}else{
						p.sendMessage("§cYou don't own this class.");
						break;
					}
					}
				}
			}
		}
	}
}
