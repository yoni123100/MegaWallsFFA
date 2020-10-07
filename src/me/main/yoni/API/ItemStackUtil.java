package me.main.yoni.API;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackUtil {
	
	public static ItemStack createItem(Material material, int amount, int data,String name,ArrayList<String> lore) {
		ItemStack item = new ItemStack(material,amount,(byte)data);
		ItemMeta im = item.getItemMeta();
		im.setLore(lore);
		im.setDisplayName(name);
		item.setItemMeta(im);
		return item;
	}

}
