package qingchenw.repack.com.TheAJ471.torch;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
* 新增特性:
* 1.加入命令和权限
* 2.更多可自定义的配置且支持&样式代码
* 3.以两种方式开启夜视,还可以使用开关模式
* 4.修复了使用红石火把关闭夜视
* 5.修复夜视药水失效的问题
* 6.不会对喝夜视药水的玩家启用无限夜视
* 7.//启用无限夜视后无法使用夜视药水
* 8.加入禁用无限夜视的世界和玩家
* 9.加入开关无限夜视的消息通知
* 10.问题:可能不支持除1.8之外的版本(未测试)
*/
public class TorchLight extends JavaPlugin
{
	public static TorchLight plugin;
	
    public TorchLight()
    {
    	plugin = this;
	}

    public void onEnable()
    {
    	// 我可能是Mod写多了QAQ
    	try
    	{
        	// Load Config
        	new TorchConfig(this);
        	// Register event
        	new TorchListener(this);
        	// Load Command
        	new TorchCommand(this);
            this.getLogger().info(TorchConfig.getDescription().getName() + " has been enabled! Version: " + TorchConfig.getDescription().getVersion() + "!");
            this.getLogger().info("The author was TheAJ471, reload by QingChenW!");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
            this.getLogger().info("Can't load the plugin, it has been disabled!");
    	}
    }
    
    public void onDisable()
    {
        this.getLogger().info(TorchConfig.getDescription().getName() + " has been disabled!");
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
    	if (cmd.getName().equalsIgnoreCase("torchlight"))
    	{
    	    if (sender instanceof Player)
    	    {
    	        return TorchCommand.onPlayerCommand((Player) sender, cmd, label, args);
    		}
    	    else
    	    {
    	    	return TorchCommand.onCommand(sender, cmd, label, args);
    	    }
    	}
    	return false;
    }
    
    public void reloadConfig()
    {
    	super.reloadConfig();
    	TorchConfig.reloadConfig();
    }

	public static ItemStack createItem(Material material, String name, int count, byte data, String string)
	{
		ItemStack item = new ItemStack(material, count, data);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(string));
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
    
    public static String getBukkitVersion()
    {
      Matcher matcher = Pattern.compile("v\\d+_\\d+_R\\d+").matcher(Bukkit.getServer().getClass().getPackage().getName());
      if (matcher.find())
      {
        return matcher.group();
      }
      return null;
    }
}
