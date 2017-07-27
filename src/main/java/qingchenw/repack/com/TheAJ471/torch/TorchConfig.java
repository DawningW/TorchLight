package qingchenw.repack.com.TheAJ471.torch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;

public class TorchConfig
{
	private static TorchLight plugin;
	private static FileConfiguration configuration;
	private static PluginDescriptionFile description;
	private static Map<String, String> language;
	public static Material enable_item;
	public static Material disable_item;
	public static Sound default_sound;
	public static Sound enable_sound;
	public static Sound disable_sound;
	// Cache
	public static List<Player> light = new ArrayList<Player>();
	// Anti-world
	public static List<String> world = new ArrayList<String>();
	// Anti-player
	public static List<String> player = new ArrayList<String>();
	
	static
	{
		switch(TorchLight.getBukkitVersion())
		{
			case "v1_7_R1":
			case "v1_7_R2":
			case "v1_7_R3":
			case "v1_7_R4": default_sound = Sound.valueOf("CLICK"); break;
			case "v1_8_R1":
			case "v1_8_R2":
			case "v1_8_R3": default_sound = Sound.valueOf("CLICK"); break;
			case "v1_9_R1":
			case "v1_9_R2": default_sound = Sound.valueOf("BLOCK_LEVER_CLICK"); break;
			case "v1_10_R1": default_sound = Sound.valueOf("BLOCK_LEVER_CLICK"); break;
			case "v1_11_R1": default_sound = Sound.valueOf("BLOCK_LEVER_CLICK"); break;
			case "v1_12_R1": default_sound = Sound.valueOf("BLOCK_LEVER_CLICK"); break;
			default: default_sound = null; break;
		}
	}
	
    public TorchConfig(TorchLight plugin)
    {
    	TorchConfig.plugin = plugin;
    	plugin.saveDefaultConfig();
    	plugin.reloadConfig();
	}
    
    public static void reloadConfig()
    {
    	configuration = plugin.getConfig();
    	description = plugin.getDescription();
    	
    	language = new HashMap<String, String>();
    	language.put("enable", configuration.getString("messages.enable").replace("&", "§"));
    	language.put("disable", configuration.getString("messages.disable").replace("&", "§"));
    	language.put("world", configuration.getString("messages.blocked.world").replace("&", "§"));
    	language.put("player", configuration.getString("messages.blocked.player").replace("&", "§"));
    	
    	enable_item = Material.getMaterial(configuration.getString("enable.id"));
    	disable_item = Material.getMaterial(configuration.getString("disable.id"));
    	String s = configuration.getString("enable.sound");
    	enable_sound = "DEFAULT".equals(s) ? default_sound : Sound.valueOf(s);
    	s = configuration.getString("disable.sound");
    	disable_sound = "DEFAULT".equals(s) ? default_sound : Sound.valueOf(s);
		
//    	light = configuration.getStringList("playercache");
//    	存储使用中的玩家这种东西以后可能也没有了
    	world = configuration.getStringList("blockedworld");
    	player = configuration.getStringList("blockedplayer");
    }
    
	public static FileConfiguration getConfiguration()
	{
		return configuration;
	}

	public static PluginDescriptionFile getDescription()
	{
		return description;
	}

	public static String getMessage(String key)
	{
		return language.getOrDefault(key, "[TorchLight] 语言条目缺失: " + key);
	}
	
	public static ItemStack getEnableItem()
	{
		return TorchLight.createItem(enable_item, configuration.getString("enable.name"), configuration.getInt("enable.count"), (byte) configuration.getInt("enable.data"), configuration.getString("enable.lore"));
	}
	
	public static ItemStack getDisableItem()
	{
		return TorchLight.createItem(disable_item, configuration.getString("disable.name"), configuration.getInt("disable.count"), (byte) configuration.getInt("disable.data"), configuration.getString("disable.lore"));
	}
}
