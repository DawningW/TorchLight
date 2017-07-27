package qingchenw.repack.com.TheAJ471.torch;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TorchCommand
{
	private static TorchLight plugin;
	
    public TorchCommand(TorchLight plugin)
    {
    	TorchCommand.plugin = plugin;
	}

	public static boolean onPlayerCommand(Player sender, Command cmd, String label, String[] args)
    {
    	if (args.length == 0)
    	{
			if(TorchConfig.getConfiguration().getString("type").equals("command"))
			{
				if (!TorchConfig.light.contains(sender))
				{
					TorchListener.toggleTorch(sender, true);
					sender.getWorld().playSound(sender.getLocation(), TorchConfig.enable_sound, 2.0F, 0.0F);
					sender.sendMessage(TorchConfig.getMessage("enable"));
				}
				else
				{
					TorchListener.toggleTorch(sender, false);
					sender.getWorld().playSound(sender.getLocation(), TorchConfig.disable_sound, 2.0F, 0.0F);
					sender.sendMessage(TorchConfig.getMessage("disable"));
				}
				return true;
			}
    	}
    	else if (args[0].equals("enable"))
    	{
			if(TorchConfig.getConfiguration().getString("type").equals("command"))
			{
				TorchListener.toggleTorch(sender, true);
				sender.getWorld().playSound(sender.getLocation(), TorchConfig.enable_sound, 2.0F, 0.0F);
				sender.sendMessage(TorchConfig.getMessage("enable"));
				return true;
			}
    	}
    	else if (args[0].equals("disable"))
    	{
			if(TorchConfig.getConfiguration().getString("type").equals("command"))
			{
				TorchListener.toggleTorch(sender, false);
				sender.getWorld().playSound(sender.getLocation(), TorchConfig.disable_sound, 2.0F, 0.0F);
				sender.sendMessage(TorchConfig.getMessage("disable"));
				return true;
			}
    	}
    	return onCommand(sender, cmd, label, args);
    }
    
    public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
    	if (args.length == 0)
    	{
	    	sender.sendMessage(TorchConfig.getDescription().getName() + " 版本: " + TorchConfig.getDescription().getVersion());
        	sender.sendMessage("原作者 TheAJ471, 由 QingChenW 重制!");
        	sender.sendMessage("查看帮助:/torchlight help");
	    	return true;
    	}
    	else if (args[0].equals("help"))
    	{
    		sender.sendMessage(TorchConfig.getDescription().getName() + " 版本: " + TorchConfig.getDescription().getVersion());
    		sender.sendMessage("查看配置文件:/torchlight config");
    		sender.sendMessage("重载配置:/torchlight reload");
        	return true;
        }
        else if (args[0].equals("config"))
        {
        	if(sender.hasPermission("torch.command") || sender instanceof ConsoleCommandSender)
        	{
        		sender.sendMessage("[TorchLight] 输出配置文件:" + TorchConfig.getConfiguration().saveToString());
	        	return true;
        	}
        	else
        	{
        		sender.sendMessage("[TorchLight] 你没有此命令的权限!");
	        	return true;
        	}
        }
    	else if (args[0].equals("reload"))
    	{
        	if(sender.hasPermission("torch.command") || sender instanceof ConsoleCommandSender)
        	{
        		plugin.reloadConfig();
        		sender.sendMessage("[TorchLight] 重载配置文件成功!");
	        	return true;
        	}
        	else
        	{
        		sender.sendMessage("[TorchLight] 你没有此命令的权限!");
	        	return true;
        	}
	    }
    	else
    	{
        	sender.sendMessage("未找到此子命令, 请查看帮助: /torchlight");
        	return true;
    	}
    }
}
