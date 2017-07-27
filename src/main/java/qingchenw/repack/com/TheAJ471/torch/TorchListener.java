package qingchenw.repack.com.TheAJ471.torch;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TorchListener implements Listener
{
	@SuppressWarnings("unused")
	private static TorchLight plugin;

	public TorchListener(TorchLight plugin)
	{
		TorchListener.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (TorchConfig.getConfiguration().getString("type").equals("item") && player.hasPermission("torch.use"))
		{
			if (!TorchConfig.world.contains(player.getWorld().getName()))
			{
				if (!TorchConfig.player.contains(player.getName()))
				{
					if (player.getInventory().getHeldItemSlot() == TorchConfig.getConfiguration().getInt("enable.slot") &&
							player.getItemInHand().getType().equals(TorchConfig.enable_item))
					{
						if(TorchConfig.getConfiguration().getBoolean("toggle"))
						{
							player.getInventory().setItemInHand(null);
							setDisableItem(player);
						}
						toggleTorch(player, true);
						player.getWorld().playSound(player.getLocation(), TorchConfig.enable_sound, 2.0F, 0.0F);
						player.sendMessage(TorchConfig.getMessage("enable"));
					}
					else if(player.getInventory().getHeldItemSlot() == TorchConfig.getConfiguration().getInt("disable.slot") &&
							player.getItemInHand().getType().equals(TorchConfig.disable_item))
					{
						if(TorchConfig.getConfiguration().getBoolean("toggle"))
						{
							player.getInventory().setItemInHand(null);
							setEnableItem(player);
						}
						toggleTorch(player, false);
						player.getWorld().playSound(player.getLocation(), TorchConfig.disable_sound, 2.0F, 0.0F);
						player.sendMessage(TorchConfig.getMessage("disable"));
					}
				}
				else
				{
					player.sendMessage(TorchConfig.getMessage("player"));
				}
			}
			else
			{
				player.sendMessage(TorchConfig.getMessage("world"));
			}
		}
	}

	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent event)
	{
		Player player = event.getPlayer();
		if (TorchConfig.world.contains(player.getWorld().getName()) && TorchConfig.light.contains(player))
		{
			toggleTorch(player, false);
			player.sendMessage(TorchConfig.getMessage("world"));
		}
	}
	
	@EventHandler
	public void onConsumeItem(PlayerItemConsumeEvent event)
	{
		/*//TODO 然而并不好使
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		if(item.getType().equals(Material.POTION))
		{
			if (Potion.fromItemStack(item).getType().equals(PotionType.NIGHT_VISION) && TorchConfig.light.contains(player))
			{
				event.setCancelled(true);
			}
		}
		*/
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent event)
	{
		if(TorchConfig.getConfiguration().getString("type").equals("item"))
		{
			if(event.getItemDrop().getItemStack().equals(TorchConfig.getEnableItem()) ||
				event.getItemDrop().getItemStack().equals(TorchConfig.getDisableItem()))
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(TorchConfig.getConfiguration().getString("type").equals("item"))
		{
			setEnableItem(player);
			if(!TorchConfig.getConfiguration().getBoolean("toggle")) setDisableItem(player);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event)
	{
		toggleTorch(event.getPlayer(), false);
	}

	@EventHandler
	public void onKick(PlayerKickEvent event)
	{
		toggleTorch(event.getPlayer(), false);
	}

	/** 
	 * 这个方法就是提供的API啦,直接调用就行,插件会自动处理一切后续事务
	 * 
	 * @param player 需要开启/关闭无限夜视的玩家
	 * @param open true为开启,false为关闭
	 */
	public static void toggleTorch(Player player, boolean open)
	{
		if(open)
		{
			if (!TorchConfig.light.contains(player) && !player.hasPotionEffect(PotionEffectType.NIGHT_VISION))
			{
				player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1));
				TorchConfig.light.add(player);
			}
		}
		else
		{
			if (TorchConfig.light.contains(player))
			{
				player.removePotionEffect(PotionEffectType.NIGHT_VISION);
				TorchConfig.light.remove(player);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void setEnableItem(Player player)
	{
		player.getInventory().setItem(TorchConfig.getConfiguration().getInt("enable.slot"), TorchConfig.getEnableItem());
		player.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	public static void setDisableItem(Player player)
	{
		player.getInventory().setItem(TorchConfig.getConfiguration().getInt("disable.slot"), TorchConfig.getDisableItem());
		player.updateInventory();
	}
}
