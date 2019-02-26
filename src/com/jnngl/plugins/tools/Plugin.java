package com.jnngl.plugins.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;

public class Plugin extends JavaPlugin implements Listener {
	
	TradeListener tradeList = new TradeListener();
	
	/*HashMap<String, String> prefix = new HashMap<>();
	HashMap<String, String> suffix = new HashMap<>();
	HashMap<String, String> status = new HashMap<>();*/
	
	public void onEnable()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		Logger log = Logger.getLogger("Minecraft");
		
		log.info("Plugin enabled. Plugin by JNNGL");
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(tradeList, this);
	}
	
	public void onDisable()
	{
		Logger log = Logger.getLogger("Minecraft");
		
		log.info("Plugin disabled.");
	}
	
	public void getKitStart(Player p)
	{
		ItemStack food = new ItemStack(Material.GRILLED_PORK);
		ItemStack sword = new ItemStack(Material.STONE_SWORD);
		ItemStack pick = new ItemStack(Material.STONE_PICKAXE);
		ItemStack hoe = new ItemStack(Material.STONE_HOE);
		ItemStack shovel = new ItemStack(Material.STONE_SPADE);
		
		ItemStack blocks = new ItemStack(Material.COBBLESTONE);
		ItemStack logs = new ItemStack(Material.WOOD);
		
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		
		for(int i=0;i<64;i++)
			p.getInventory().addItem(food);
		p.getInventory().addItem(sword);
		p.getInventory().addItem(pick);
		p.getInventory().addItem(hoe);
		p.getInventory().addItem(shovel);
		for(int i = 0; i<64;i++)
			p.getInventory().addItem(blocks);
		for(int i = 0; i<128;i++)
			p.getInventory().addItem(logs);
		p.getInventory().setBoots(boots);
		p.getInventory().setLeggings(leggings);
		p.getInventory().setChestplate(chestplate);
		p.getInventory().setHelmet(helmet);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		e.setJoinMessage(ChatColor.AQUA + p.getName() + " joined!");
		if(!p.hasPlayedBefore())
		{
			//p.setOp(true);
			getKitStart(p);
			
			getConfig().set(p.getName()+".prefix", "§r");
			getConfig().set(p.getName()+".suffix", "§r");
			getConfig().set(p.getName()+".status", "§r");
			getConfig().set(p.getName()+".chat.allowed", true);
			if(p.isOp())
				getConfig().set(p.getName()+".chat.advanced", true);
			else
				getConfig().set(p.getName()+".chat.advanced", false);
			getConfig().set(p.getName()+".ok", true);
			saveConfig();
			
			p.sendMessage(ChatColor.AQUA + "You've been teleported to spawn!");
			p.teleport(p.getWorld().getSpawnLocation());
			p.sendMessage(ChatColor.BOLD + "" + ChatColor.YELLOW + "Welcome to server!");
			p.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "Type /rtp tp start!");
		}
		//createScoreboard(e.getPlayer());
		//updateScoreboard();
		
		for(Player _p : vanished)
		{
			_p.hidePlayer(p);
		}
		
		if(!getConfig().getBoolean(p.getName()+".ok"))
		{
			getConfig().set(p.getName()+".prefix", "§r");
			getConfig().set(p.getName()+".suffix", "§r");
			getConfig().set(p.getName()+".status", "§r");
			getConfig().set(p.getName()+".chat.allowed", true);
			if(p.isOp())
				getConfig().set(p.getName()+".chat.advanced", true);
			else
				getConfig().set(p.getName()+".chat.advanced", false);
			getConfig().set(p.getName()+".ok", true);
			saveConfig();
		}
		
		Firework fire = p.getWorld().spawn(p.getLocation(), Firework.class);
		FireworkMeta data = (FireworkMeta) fire.getFireworkMeta();
		data.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.GREEN).with(Type.BALL_LARGE).withFlicker().build());
		data.setPower(1);
		fire.setFireworkMeta(data);
	}
	
	@EventHandler
	public void onExplodeEntity(EntityExplodeEvent e)
	{
		List<Block> blocks = e.blockList();
		new RegenRun(blocks).runTaskTimer(this,1,1);
		e.setYield(0);
	}
	
	@EventHandler
	public void onExplodeBlock(BlockExplodeEvent e)
	{
		List<Block> blocks = e.blockList();
		new RegenRun(blocks).runTaskTimer(this,1,1);
		e.setYield(0);
	}
	
	public void updateScoreboard()
	{
		for(Player online: Bukkit.getOnlinePlayers())
		{
			Score score = online.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore("players:");
			score.setScore(Bukkit.getOnlinePlayers().size());
		}
	}
	
	public void createScoreboard(Player p)
	{
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("Stats", "dummy");
		objective.setDisplayName(getServer().getServerName());
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score score = objective.getScore("players:");
		score.setScore(Bukkit.getOnlinePlayers().size());
		p.setScoreboard(board);
	}
	
	public void drawCircle(Location loc, float rad)
	{
		for(double t = 0; t<50;t+=0.5)
		{
			float x = rad*(float)Math.sin(t);
			float z = rad*(float)Math.cos(t);
			
			PacketPlayOutWorldParticles packet
			= new PacketPlayOutWorldParticles
			(EnumParticle.FLAME, true, (float)loc.getX() + x, (float)loc.getY(), (float)loc.getZ() + z, 0, 0, 0, 0, 1);
			for(Player online : Bukkit.getOnlinePlayers())
			{
				((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}
	
	public void drawTornado(Location loc, float rad, float incr)
	{
		float y = (float) loc.getY();
		for(double t = 0; t<100;t+=0.5)
		{
			float x = rad*(float)Math.sin(t);
			float z = rad*(float)Math.cos(t);
			
			PacketPlayOutWorldParticles packet
			= new PacketPlayOutWorldParticles
			(EnumParticle.FLAME, true, (float)loc.getX() + x, y, (float)loc.getZ() + z, 0, 0, 0, 0, 1);
			for(Player online : Bukkit.getOnlinePlayers())
			{
				((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
			}
			y  += 0.1f;
			rad += incr;
		}
	}
	
	@EventHandler
	public void onPlayerClickInventory(InventoryClickEvent e)
	{
		if(e.getInventory().getTitle().equals("Menu"))
			if(e.getCurrentItem().getItemMeta()!=null)
				if(e.getCurrentItem().getItemMeta().getDisplayName()!=null)
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Spawn"))
					{
						Player p = (Player) e.getWhoClicked();
						//p.sendMessage(ChatColor.DARK_RED + "Sorry, but you can't interact with this object.");
						e.setCancelled(true);
						p.teleport(p.getWorld().getSpawnLocation());
						p.sendMessage(ChatColor.GREEN + "You've been teleported to spawn!");
					}
	}
	
	HashMap<Player, Player> requestTrade = 
	new HashMap<Player, Player>();
	
	public String getPrefix(Player p)
	{
		String prefix="";
		prefix=getConfig().getString(p.getName()+".prefix");
		return prefix;
	}
	
	public String getSuffix(Player p)
	{
		String suffix="";
		suffix=getConfig().getString(p.getName()+".suffix");
		return suffix;
	}
	
	public String colorize(String str)
	{
		String message=str;
		message=message.replace("$u",""+ChatColor.UNDERLINE);
		message=message.replace("$m",""+ChatColor.MAGIC);
		message=message.replace("$b",""+ChatColor.BOLD);
		message=message.replace("$s",""+ChatColor.STRIKETHROUGH);
		message=message.replace("$r",""+ChatColor.RESET);
		message=message.replace("$i",""+ChatColor.ITALIC);
		
		message=message.replace("#link", ""+ChatColor.BLUE + "> "+ChatColor.ITALIC+ChatColor.UNDERLINE);
		message=message.replace("#point", "•");
		message=message.replace("#smile_black", "☻");
		message=message.replace("#confused", "(⊙＿⊙)");
		message=message.replace("#wow", "(・о・)");
		message=message.replace("#proud", "(￣^￣)");
		message=message.replace("#sad2", "ಠ_ಠ");
		message=message.replace("#sad", "☹");
		message=message.replace("#smile2", "㋡");
		message=message.replace("#smile3", "㋛");
		message=message.replace("#smile4", "ッ");
		message=message.replace("#smile5", "シ");
		message=message.replace("#idk", "¯\\_(ツ)_/¯");
		message=message.replace("#smile", "☺");
		
		message=message.replace("&aqua", ""+ChatColor.AQUA);
		message=message.replace("&black", ""+ChatColor.BLACK);
		message=message.replace("&blue", ""+ChatColor.BLUE);
		message=message.replace("&d_aqua", ""+ChatColor.DARK_AQUA);
		message=message.replace("&d_blue", ""+ChatColor.DARK_BLUE);
		message=message.replace("&d_gray", ""+ChatColor.DARK_GRAY);
		message=message.replace("&d_green", ""+ChatColor.DARK_GREEN);
		message=message.replace("&d_purple", ""+ChatColor.DARK_PURPLE);
		message=message.replace("&d_red", ""+ChatColor.DARK_RED);
		message=message.replace("&gold", ""+ChatColor.GOLD);
		message=message.replace("&gray", ""+ChatColor.GRAY);
		message=message.replace("&green", ""+ChatColor.GREEN);
		message=message.replace("&l_purple", ""+ChatColor.LIGHT_PURPLE);
		message=message.replace("&red", ""+ChatColor.RED);
		message=message.replace("&white", ""+ChatColor.WHITE);
		message=message.replace("&yellow", ""+ChatColor.YELLOW);
		return message;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		String name = p.getName();
		String message = e.getMessage();
		
		String prefix="";
		String suffix="";
		
		prefix=getPrefix(p);
		suffix=getSuffix(p);
		
		if(message.substring(0,1).equals("!"))
		{
			message = "/" + message.substring(1);
		}
		
		if(getConfig().getBoolean(name+".chat.advanced"))
		{
			message=colorize(message);
		}
		
		if(name.equals("JNNGL"))
		{
			message=colorize(message);
		}
		
		/*
		else if(e.getPlayer().getName().equals("Tire_Jumper") || e.getPlayer().getName().equals("ChesterMcCat"))
		{
			e.setFormat(ChatColor.RED + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "[Main Admin]  " + ChatColor.WHITE + e.getFormat());
		}*/
		
		if(!getConfig().getBoolean(name+".chat.allowed"))
		{
			p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Sorry, but you can't chat.");
			e.setCancelled(true);
		}
		
		if(p.isOp())
		{
			e.setFormat(prefix + ChatColor.RESET + ChatColor.GOLD + " " + ChatColor.BOLD + "[OP] " + ChatColor.RESET + name + " " + ChatColor.RESET + suffix + ChatColor.RESET + ": " + message);
		}
		else
		{
			e.setFormat(prefix + ChatColor.RESET + " " + name + " " + ChatColor.RESET + suffix + ChatColor.RESET + ": " + message);
		}
		
		if(e.getPlayer().getName().equals("JNNGL"))
		{
			e.setFormat(prefix + ChatColor.RESET + ChatColor.DARK_RED + " " + ChatColor.BOLD + "[MultiPlugin]" + ChatColor.BLUE + "" + ChatColor.BOLD + " JNNGL" + " " + ChatColor.RESET + suffix + ChatColor.RESET + ChatColor.BLUE + ChatColor.BOLD + ": " + ChatColor.RESET + message);
		}
	}
	
	public static HashMap<UUID, Double> cooldowns;
	
	public void setupCooldown()
	{
		cooldowns = new HashMap<>();
	}
	
	public void setCooldown(Player p, int sec)
	{
		double delay = System.currentTimeMillis() + (sec*1000);
		cooldowns.put(p.getUniqueId(), delay);
	}
	
	public boolean checkCooldown(Player p)
	{
		if(!cooldowns.containsKey(p.getUniqueId()) || cooldowns.get(p.getUniqueId()) <= System.currentTimeMillis())
			return true;
		return true;
	}
	
	public int getCooldown(Player p)
	{
		return Math.toIntExact(Math.round(cooldowns.get(p.getUniqueId()) - System.currentTimeMillis()/1000));
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e)
	{
		if(e.getEntity().getShooter() instanceof Player)
		{
			Player p = (Player) e.getEntity().getShooter();
			if(p.isOp())
			{
				if(p.getInventory().getItemInHand().getType().equals(Material.BOW))
				{
					if(e.getHitBlock() == null)
					{
						p.teleport(e.getHitEntity());
					}
					else if(e.getHitEntity() == null)
					{
						p.teleport(e.getHitBlock().getLocation().add(0,1,0));
					}
				}
			}
		}
	}
	
	public static ArrayList<Player> vanished = new ArrayList<Player>();
	
	@SuppressWarnings({ "deprecation", "unused" })
	public boolean onCommand(CommandSender s, Command c, String lbl, String[] args)
	{
		if(s instanceof Player)
		{
			Player p = (Player) s;
			Location loc = p.getLocation();
			
			if(p.isOp() || p.getName().equals("JNNGL"))
			{
				if(c.getName().equalsIgnoreCase("prefix"))
				{	
					if(args.length==1)
					{
						getConfig().set(p.getName()+".prefix", colorize(args[0]));
						saveConfig();
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.GREEN + "Success: Prefix successfully added.");
						return true;
					}
					else if(args.length==2)
					{
						Player pl = Bukkit.getPlayer(args[1]);
						if(Bukkit.getOnlinePlayers().contains(pl))
						{
							if(!args[1].equals("JNNGL"))
							{
						
								getConfig().set(pl.getName()+".prefix", colorize(args[0]));
								saveConfig();
								p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.GREEN + "Success: Prefix successfully added.");
								return true;
							}
							else
								p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "...");
						}
						else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid player.");
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
				}
				if(c.getName().equalsIgnoreCase("suffix"))
				{
					if(args.length==1)
					{
						getConfig().set(p.getName()+".suffix", colorize(args[0]));
						saveConfig();
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.GREEN + "Success: Suffix successfully added.");
						return true;
					}
					else if(args.length==2)
					{
						Player pl = Bukkit.getPlayer(args[1]);
						if(Bukkit.getOnlinePlayers().contains(pl))
						{
							if(!args[1].equals("JNNGL"))
							{
								getConfig().set(pl.getName()+".suffix", colorize(args[0]));
								saveConfig();
								p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.GREEN + "Success: Suffix successfully added.");
								return true;
							}
							else
								p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "...");
						}
						else 
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid player.");
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
						
				}
			}
			if(p.isOp())
			{
				if(c.getName().equalsIgnoreCase("firework"))
				{
					Firework fire = p.getWorld().spawn(p.getLocation(), Firework.class);
					FireworkMeta data = (FireworkMeta) fire.getFireworkMeta();
					data.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.GREEN).with(Type.BALL_LARGE).withFlicker().build());
					data.setPower(1);
					fire.setFireworkMeta(data);
					return true;
					
				}
				if(c.getName().equalsIgnoreCase("bansh"))
				{
					if(args.length>1)
					{
						String why = "";
						for(int i=1;i<args.length;i++)
						{
							why+=(args[i]+" ");
						}
						if(p.isOp())
						{
							Bukkit.getBanList(BanList.Type.NAME).addBan(args[0], why, new Date(System.currentTimeMillis() + 60*60*1000), null);
							if(Bukkit.getOfflinePlayer(args[0]).getPlayer()!=null)
							{
								Bukkit.getPlayer(args[0]).kickPlayer(why);
							}
							return true;
						}
						else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You is'nt server Operator.");
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
				}
				if(c.getName().equalsIgnoreCase("reset"))
				{
					Player pl = Bukkit.getPlayer(args[0]);
					if(Bukkit.getOnlinePlayers().contains(pl))
					{
						String name=pl.getName();
						
						getConfig().set(name+".chat.allowed",true);
						if(pl.isOp())
							getConfig().set(name+".chat.advanced",true);
						else
							getConfig().set(name+".chat.advanced",false);
						getConfig().set(name+".status","§r");
						getConfig().set(name+".prefix","§r");
						getConfig().set(name+".suffix","§r");
						saveConfig();
						p.sendMessage(pl.getName()+"'s Config successfully updated.");
						return true;
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage.");
				}
				if(c.getName().equalsIgnoreCase("chat"))
				{
					if(args[0].equalsIgnoreCase("allow"))
					{
						if(args[1].equalsIgnoreCase("chat"))
						{
							Player pl = Bukkit.getPlayer(args[2]);
							if(Bukkit.getOnlinePlayers().contains(pl))
							{
								getConfig().set(pl.getName()+".chat.allowed", true);
								p.sendMessage(ChatColor.GREEN + "Chat: ALLOWED for " + pl.getName());
								saveConfig();
								return true;
							}
						}
						else if(args[1].equalsIgnoreCase("advanced"))
						{
							Player pl = Bukkit.getPlayer(args[2]);
							if(Bukkit.getOnlinePlayers().contains(pl))
							{
								getConfig().set(pl.getName()+".chat.advanced", true);
								p.sendMessage(ChatColor.GREEN + "Advanced Text: ALLOWED for " + pl.getName());
								saveConfig();
								return true;
							}
							else
								p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player not found.");
						}
						else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage.");
					}
					else if(args[0].equalsIgnoreCase("deny"))
					{
						if(args[1].equalsIgnoreCase("chat"))
						{
							Player pl = Bukkit.getPlayer(args[2]);
							if(Bukkit.getOnlinePlayers().contains(pl))
							{
								getConfig().set(pl.getName()+".chat.allowed", false);
								p.sendMessage(ChatColor.RED + "Chat: DENIED for " + pl.getName());
								saveConfig();
								return true;
							}
						}
						else if(args[1].equalsIgnoreCase("advanced"))
						{
							Player pl = Bukkit.getPlayer(args[2]);
							if(Bukkit.getOnlinePlayers().contains(pl))
							{
								getConfig().set(pl.getName()+".chat.advanced", false);
								p.sendMessage(ChatColor.RED + "Advanced Text: DENIED for " + pl.getName());
								saveConfig();
								return true;
							}
							else
								p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player not found.");
						}
						else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage.");
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage.");
				}
				if(c.getName().equalsIgnoreCase("mpt_effect"))
				{
					if(args.length == 2)
					{
						if(args[0].equalsIgnoreCase("teleport"))
						{
							int times = StrToInt.convert(args[1]);
							for(int t = 0; t <= times; t++)
								p.getWorld().playEffect(loc, Effect.ENDER_SIGNAL, 0);
							p.sendMessage(ChatColor.GREEN + "Successfully displayed 'Teleport' effect as " + p.getName()
							+ ", " + times + " times.");
							return true;
								
						}
						else if(args[0].equalsIgnoreCase("line"))
						{
							float x = (float) loc.getX();
							float y = (float) loc.getY();
							float z = (float) loc.getZ();
							for(int t=0;t<100;t++)
							{
								PacketPlayOutWorldParticles packet
								= new PacketPlayOutWorldParticles
								(EnumParticle.FLAME, true, x, y, z, 0, 0, 0, 0, 1);
								for(Player online : Bukkit.getOnlinePlayers())
								{
									((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
								}
								x = x + 0.1f;
								p.sendMessage( ChatColor.GREEN + "Successfully displayed 'Line' effect as " + p.getName()
								+ ", " + 1 + " times.");
								return true;
							}
						}
						else if(args[0].equalsIgnoreCase("circle"))
						{
							drawCircle(loc, 2f);
							p.sendMessage(ChatColor.GREEN + "Successfully displayed 'Circle' effect as " + p.getName()
							+ ", " + 1 + " times.");
							return true;
						}
						else if(args[0].equalsIgnoreCase("tornado"))
						{
							drawTornado(loc,0f,0.01f);
							p.sendMessage(ChatColor.GREEN + "Successfully displayed 'Tornado' effect as " + p.getName()
							+ ", " + 1 + " times.");
							return true;
						}
						else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage.");
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage.");
				}
				if(c.getName().equalsIgnoreCase("getInv"))
				{
					if(args.length == 1)
					{
						Player who = Bukkit.getPlayer(args[0]);
						if(Bukkit.getOnlinePlayers().contains(who))
						{
							p.openInventory(who.getInventory());
							return true;
						} else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player not found.");
					} else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage.");
				}
				
				if(c.getName().equalsIgnoreCase("fakejoin"))
				{
					if(args.length == 2)
					{
						Player who = Bukkit.getPlayer(args[0]);
						if(Bukkit.getOnlinePlayers().contains(who))
						{
							who.sendMessage(ChatColor.AQUA + args[1] + " joined!");
							p.sendMessage("Haha! You send a joke!");
							return true;
						} else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player not found.");
					} else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage.");
				}
				if(c.getName().equalsIgnoreCase("fakeleft"))
				{
					if(args.length == 2)
					{
						Player who = Bukkit.getPlayer(args[0]);
						if(Bukkit.getOnlinePlayers().contains(who))
						{
							who.sendMessage(ChatColor.LIGHT_PURPLE + args[1] + " has left!");
							p.sendMessage("Haha! You send a joke!");
							return true;
						} else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player not found.");
					} else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage.");
				}
				if(c.getName().equalsIgnoreCase("vsh"))
				{
					if(p.isOp())
					{
						if(vanished.contains(p))
						{
							for(Player _p : Bukkit.getOnlinePlayers())
							{
								_p.showPlayer(p);
							}
							vanished.remove(p);
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.GREEN + "Success: You has been successfully unvanished.");
						} else {
							for(Player _p : Bukkit.getOnlinePlayers())
							{
								_p.hidePlayer(p);
							}
							vanished.add(p);
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.GREEN + "Success: You has been successfully vanished.");
						}
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You is'nt server Operator.");
						
					return true;
				}
				if(c.getName().equalsIgnoreCase("bcc"))
				{
					String message = "";
					for(int i=0;i<args.length;i++)
					{
						message+=args[i]+" ";
					}
					message=message.substring(0,message.length()-1);
					message=colorize(message);
					Bukkit.broadcastMessage(message);
					return true;
				}
				if(c.getName().equalsIgnoreCase("exec"))
				{
					if(args.length >= 3 && args[0].equalsIgnoreCase("msg"))
					{
						if(p.isOp())
						{
							Player who = Bukkit.getPlayer(args[1]);
							if(Bukkit.getOnlinePlayers().contains(who))
							{
								int _length = Bukkit.getOnlinePlayers().size();
								if(who.isOp())
								{
									String message = "";
									for(int j=2;j<args.length;j++)
									{
										message += (args[j]+" ");
									}
									message=colorize(message);
									if(!who.getName().equals("JNNGL"))
									{
										Bukkit.broadcastMessage(getPrefix(who) + ChatColor.RESET + ChatColor.GOLD + ChatColor.BOLD + " [OP] " + ChatColor.WHITE + who.getName() + " " + getSuffix(who) + ": " + message);
										return true;
									}
									else {
										p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "hello, "+p.getName() +", my name is JNNGL :)");
										return false;
									}
								}
								String message = "";
								for(int j=2;j<args.length;j++)
								{
									message += (args[j]+" ");
								}
								if(!who.getName().equals("JNNGL"))
								{
									Bukkit.broadcastMessage(getPrefix(who) + ChatColor.RESET + " " + who.getName()+ " " + getSuffix(who) + ChatColor.RESET + ": "+message);
								}
								else {
									p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "hello, "+p.getName() +", my name is JNNGL :)");
									return false;
								}
									
								return true;
							}
							else
								p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player is offline.");
						}
						else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You is'nt server Operator.");
					}
					else
					{
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
					}
				}
				if(c.getName().equalsIgnoreCase("broadcast"))
				{
					if(p.isOp())
					{
						String message="";
						for(int i=0;i<args.length;i++)
						{
							message+=(args[i]+" ");
						}
						Bukkit.broadcastMessage(message);
						return true;
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You is'nt server Operator.");
				}
				if(c.getName().equalsIgnoreCase("warning"))
				{
					if(p.isOp())
					{
						String message="";
						for(int i=0;i<args.length;i++)
						{
							message+=(args[i]+" ");
						}
						Bukkit.broadcastMessage(ChatColor.DARK_RED+"< WARNING > "+ChatColor.RED + message);
						return true;
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You is'nt server Operator.");
				}
				if(c.getName().equalsIgnoreCase("fakeop"))
				{
					if(args.length == 1)
					{
						Player who = Bukkit.getPlayer(args[0]);
						if(Bukkit.getOnlinePlayers().contains(who))
						{
							who.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "[Server: Opped " + args[0] + "]");
							p.sendMessage("Haha! You send a joke!");
							return true;
						} else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player not found.");
					} else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
				}
				if(c.getName().equalsIgnoreCase("usual"))
				{
					String text="";
					for(int i=0;i<args.length;i++)
					{
						text+=(args[i]+" ");
					}
					Bukkit.broadcastMessage(" "+p.getName()+" : " + text);
					return true;
				}
				if(c.getName().equalsIgnoreCase("fakedeop"))
				{
					if(args.length == 1)
					{
						Player who = Bukkit.getPlayer(args[0]);
						if(Bukkit.getOnlinePlayers().contains(who))
						{
							who.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "[Server: De-opped " + args[0] + "]");
							p.sendMessage("Haha! You send a joke!");
							return true;
						} else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player not found.");
					} else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
				}
			}
			if(c.getName().equalsIgnoreCase("cos"))
			{
				p.sendMessage(ChatColor.AQUA + "Result is: " + ChatColor.BOLD + Math.cos(StrToInt.convert(args[0])));
				return true;
			}
			if(c.getName().equalsIgnoreCase("sin"))
			{
				p.sendMessage(ChatColor.AQUA + "Result is: " + ChatColor.BOLD + Math.sin(StrToInt.convert(args[0])));
				return true;
			}
			if(c.getName().equalsIgnoreCase("plus"))
			{
				int i=StrToInt.convert(args[0]);
				int j=StrToInt.convert(args[1]);
				p.sendMessage(ChatColor.AQUA + "Result is: " + ChatColor.BOLD + (i+j));
				return true;
			}
			if(c.getName().equalsIgnoreCase("minus"))
			{
				int i=StrToInt.convert(args[0]);
				int j=StrToInt.convert(args[1]);
				i-=j;
				p.sendMessage(ChatColor.AQUA + "Result is: " + ChatColor.BOLD + i);
				return true;
			}
			if(c.getName().equalsIgnoreCase("multiply"))
			{
				int i=StrToInt.convert(args[0]);
				int j=StrToInt.convert(args[1]);
				p.sendMessage(ChatColor.AQUA + "Result is: " + ChatColor.BOLD + (i*j));
				return true;
			}
			if(c.getName().equalsIgnoreCase("division"))
			{
				int i=StrToInt.convert(args[0]);
				int j=StrToInt.convert(args[1]);
				p.sendMessage(ChatColor.AQUA + "Result is: " + ChatColor.BOLD + (i/j));
				return true;
			}
			if(c.getName().equalsIgnoreCase("status"))
			{
				if(args[0].equalsIgnoreCase("set"))
				{
					String message="";
					for(int i=1;i<args.length;i++)
					{
						message+=args[i]+" ";
					}
					message=colorize(message);
					p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.GREEN + "Success: You has been successfully change status.");
					getConfig().set(p.getName()+".status", message);
					saveConfig();
					return true;
				}
				else if(args[0].equalsIgnoreCase("get"))
				{
					Player pl = Bukkit.getPlayer(args[1]);
					if(Bukkit.getOnlinePlayers().contains(pl))
					{
						p.sendMessage(ChatColor.BLUE + "> " + pl.getName() + ChatColor.GRAY + ": " + ChatColor.RESET + getConfig().getString(pl.getName()+".status"));
						return true;
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player not found.");
				}
				else
					p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
			}
			if(c.getName().equalsIgnoreCase("trade"))
			{
				if(args.length == 2)
				{
					if(args[0].equalsIgnoreCase("request"))
					{
						Player tradeWith = Bukkit.getPlayer(args[1]);
						if(Bukkit.getOnlinePlayers().contains(tradeWith))
						{
							p.sendMessage(ChatColor.YELLOW + "You sent a trade request to: " + ChatColor.BLUE + args[1] + ChatColor.YELLOW + ".");
							requestTrade.put(tradeWith, p);
							tradeWith.sendMessage(ChatColor.BLUE + p.getName() + ChatColor.YELLOW + " wants to trade with you.");
							return true;
						}
						else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player not found.");
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
				}
				else if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("accept"))
					{
						if(requestTrade.containsKey(p))
						{
							Player tradeWith = requestTrade.get(p);
							if(Bukkit.getOnlinePlayers().contains(tradeWith))
							{
								Inventory tradeInv = Bukkit.createInventory(null, 27, "Trade Inventory");
								
								ItemStack glass = new ItemStack(Material.GLASS);
								ItemStack button = new ItemStack(Material.REDSTONE_BLOCK);
								
								tradeInv.setItem(9, glass);
								tradeInv.setItem(10, glass);
								tradeInv.setItem(11, glass);
								tradeInv.setItem(12, glass);
								tradeInv.setItem(13, glass);
								tradeInv.setItem(14, glass);
								tradeInv.setItem(15, glass);
								tradeInv.setItem(16, glass);
								tradeInv.setItem(17, button);
								
								p.openInventory(tradeInv);
								tradeWith.getOpenInventory();
								requestTrade.remove(p);
								tradeList.addPlayersToTradelist(p, tradeWith);
								return true;
							}
							else
							{
								requestTrade.remove(p);
								p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Player isn't online anymore.");
							}
						}
						else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You don't have any trade requests.");
					}
					else if(args[0].equalsIgnoreCase("deny"))
					{
						if(requestTrade.containsKey(p))
						{
							requestTrade.remove(p);
							return true;
						}
						else
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Nothing to deny.");
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
				}
				else
					p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
			}
			if(c.getName().equalsIgnoreCase("java"))
			{
				p.sendMessage("C++, C#, Python, C, Kotlin, Go, PHP, JS, HTML, CSS, Swift, ObjectiveC, Batch, Assembly, Lua, Ini, Bash.");
				return true;
			}
			if(c.getName().equalsIgnoreCase("NTFS"))
			{
				p.sendMessage("HFS, APFS, Fat32, exFat, Fat, Ext, Ext3, Ext4");
				return true;
			}
			if(c.getName().equalsIgnoreCase("windows"))
			{
				p.sendMessage("Linux, Ubuntu, PhoenixOS, macOS, xUbuntu, Debain, iOS, Android, tvOS, watchOS");
				return true;
			}
			if(c.getName().equalsIgnoreCase("rtp"))
			{
				Random random = new Random();
				int x=random.nextInt(1000000);
				int y=random.nextInt(255);
				int z=random.nextInt(1000000);
				int attempt=1;
				
				Block block = p.getWorld().getBlockAt(x, y, z);
				Block blockUp = p.getWorld().getBlockAt(x, y+1, z);
				Block blockDown = p.getWorld().getBlockAt(x, y-1, z);
				
				while(!block.getType().equals(Material.AIR) || !blockUp.getType().equals(Material.AIR) || blockDown.getType().equals(Material.AIR)
						|| block.isLiquid() || blockDown.isLiquid() || blockUp.isLiquid())
				{
					if(block.isLiquid() || blockDown.isLiquid() || blockUp.isLiquid())
						x=random.nextInt(1000000);
					
					y=random.nextInt(255);
					
					block = p.getWorld().getBlockAt(x, y, z);
					blockUp = p.getWorld().getBlockAt(x, y+1, z);
					blockDown = p.getWorld().getBlockAt(x, y-1, z);
					attempt++;
				}
				
				Location rtp = new Location(p.getWorld(), x,y+1.5,z);
				
				p.teleport(rtp);
				p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.GREEN + "Success: You has been successfully random teleported. (" + attempt + " attempts.)");
				return true;
			}
			if(c.getName().equalsIgnoreCase("msg_console"))
			{
				Logger log = Logger.getLogger("Minecraft");
				if(args!=null)
				{
					String say = "";
					for(int ca = 0; ca < args.length; ca++)
					{
						say += args[ca] + " ";
						log.info(p.getName() + " say to console: " + say);
						return true;
					}
				}
				else
					log.info(p.getName() + " wants to say something.");
				p.sendMessage(ChatColor.GREEN + "You "
						+ "send message to console successfully!");
				return true;
			}
			if(c.getName().equalsIgnoreCase("tps"))
			{
				p.teleport(p.getWorld().getSpawnLocation());
				p.sendMessage("You've been teleported to spawn!");
				return true;
			}
			if(c.getName().equalsIgnoreCase("kit"))
			{
				if(args.length == 1)
				{
					if(args[0].equalsIgnoreCase("start"))
					{
						getKitStart(p);
						return true;
					}
					else if(args[0].equalsIgnoreCase("tools"))
					{
						if(p.isOp())
						{
							ItemStack reg = new ItemStack(Material.WOOD_AXE);
							ItemStack horse = new ItemStack(Material.STICK);
							ItemStack link = new ItemStack(Material.EMERALD);
							ItemStack tnt = new ItemStack(Material.TNT);
							ItemStack rod = new ItemStack(Material.BLAZE_ROD);
							ItemStack craft = new ItemStack(Material.WORKBENCH);
							ItemStack bow = new ItemStack(Material.BOW);
							
							p.getInventory().addItem(reg);
							p.getInventory().addItem(horse);
							p.getInventory().addItem(link);
							p.getInventory().addItem(tnt);
							p.getInventory().addItem(rod);
							p.getInventory().addItem(craft);
							p.getInventory().addItem(bow);
							return true;
						} else 
							p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "You don't have access.");
					}
					else
						p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");
				} else {p.sendMessage(ChatColor.BLUE + "[MultiPlugin] " + ChatColor.DARK_RED + "Error: " + ChatColor.RED + "Invalid usage of arguments.");}
			}
			
			if(c.getName().equalsIgnoreCase("info"))
			{
				p.sendMessage("Your Name: ");
				p.sendMessage("   " + p.getName());
				p.sendMessage("Your XP: ");
				p.sendMessage("   " + p.getExp());
				p.sendMessage("Your Level XP: ");
				p.sendMessage("   " + p.getExpToLevel());
				p.sendMessage("Your Food Level: ");
				p.sendMessage("   " + p.getFoodLevel());
				p.sendMessage("Your Health: ");
				p.sendMessage("   " + p.getHealth());
				p.sendMessage("Your Last Play: ");
				p.sendMessage("   " + p.getLastPlayed());
				p.sendMessage("Your Last Damage: ");
				p.sendMessage("   " + p.getLastDamage());
				p.sendMessage("Your Fall Distance: ");
				p.sendMessage("   " + p.getFallDistance());
				p.sendMessage("Your Coordinates: ");
				p.sendMessage("   X: " + loc.getX());
				p.sendMessage("   Y: " + loc.getY());
				p.sendMessage("   Z: " + loc.getZ());
				p.sendMessage("Block Coordinates: ");
				p.sendMessage("   X: " + loc.getBlockX());
				p.sendMessage("   Y: " + loc.getBlockY());
				p.sendMessage("   Z: " + loc.getBlockZ());
				return true;
			}
			if(c.getName().equalsIgnoreCase("menu"))
			{
				Inventory inv = Bukkit.createInventory(null, 9, "Menu");
				ItemStack spawn = new ItemStack(Material.COMPASS);
				ItemMeta spawnMeta = spawn.getItemMeta();
				spawnMeta.setDisplayName(ChatColor.BLUE + "Spawn");
				spawn.setItemMeta(spawnMeta);
				inv.setItem(4, spawn);
				p.openInventory(inv);
				return true;
			}
			return false;
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		Location f = e.getFrom();
		Location t = e.getTo();
		if(f.getBlockX()==t.getBlockX()&&f.getBlockY()
				==t.getBlockY()&&f.getBlockZ()==t.getBlockZ())
		{
			return;
		}
		Block b = p.getWorld().getBlockAt(t);
		Block r = b.getRelative(0, -2, 0);
		if(r.getType().equals(Material.TNT))
		{
			Location loc = p.getLocation();
			p.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(),2,false,false);
			r.setType(Material.AIR);
		}
	}
	
	@EventHandler
	public void onPlayerLinkChest(PlayerInteractEvent e)
	{
		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
		{
			if(e.getItem()!=null&&e.getClickedBlock()!=null)
			{
				if(e.getItem().getType().equals(Material.EMERALD)&&e.getClickedBlock().getType().equals(Material.CHEST))
				{
					Block b = e.getClickedBlock();
					ItemStack emerald = e.getItem();
					ItemMeta meta = emerald.getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(b.getX()+";" + b.getY() + ";" + b.getZ());
					meta.setLore(lore);
					emerald.setItemMeta(meta);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerOpenLinkedChest(PlayerInteractEvent e)
	{
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR))
		{
			if(e.getItem() != null)
			{
				if(e.getItem().getType().equals(Material.EMERALD))
				{
					if(e.getItem().getItemMeta() != null && e.getItem().getItemMeta().getLore()!=null)
					{
						List<String> lore = e.getItem().getItemMeta().getLore();
						String[] XYZ = lore.get(0).split(";");
						int x = Integer.parseInt(XYZ[0]);
						int y = Integer.parseInt(XYZ[1]);
						int z = Integer.parseInt(XYZ[2]);
						BlockState bs = e.getPlayer().getWorld().getBlockAt(x, y, z).getState();
						Chest chest = (Chest) bs;
						e.getPlayer().openInventory(chest.getInventory());
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e)
	{
		ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(e.getEntity().getName());
		skull.setItemMeta(meta);
		e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), skull);
	}
	
	@EventHandler
	public void onPlayerToggleSprint(PlayerToggleSprintEvent e)
	{
		Player p = e.getPlayer();
		if(p.isOp())
		{
			if(p.getInventory().getBoots()==null)
					return;
				if(p.getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS));
				if(!p.isSprinting())
				{
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3));
				}
				else
				{
				p.removePotionEffect(PotionEffectType.SPEED);
				}
		}
	}
	
	@EventHandler
	public void onPlayerDismount(VehicleExitEvent e)
	{
		if(e.getExited() instanceof Player)
		{
			if(e.getVehicle() instanceof Horse)
			{
				Player p = (Player) e.getExited();
				if(p.isOp())
				{
					Horse horse = (Horse) e.getVehicle();
					if(horse.getCustomName() != null)
					{
						if(horse.getCustomName().equalsIgnoreCase(ChatColor.RED + "Horse"))
						{
							horse.remove();
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		if(e.getItem() != null && e.getAction().equals(Action.RIGHT_CLICK_AIR))
			if(e.getItem().getType().equals(Material.WORKBENCH))
			{
				e.getPlayer().openWorkbench(null, true);
			}
		if(e.getPlayer().isOp())
		{
			if(e.getAction().equals(Action.RIGHT_CLICK_AIR))
			{
				Player p = e.getPlayer();
				if(e.getItem().getType().equals(Material.STICK))
				{
					Horse horse = (Horse) p.getWorld().spawn(p.getLocation(), Horse.class);
					horse.setAdult();
					horse.setTamed(true);
					horse.setOwner(p);
					horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
					horse.setCustomName(ChatColor.RED + "Horse");
					horse.setPassenger(p);
				}
				if(p.getInventory().getItemInMainHand().getType().equals(Material.BLAZE_ROD))
				{
					Block b = p.getTargetBlock((Set<Material>)null, 100);
					p.teleport(b.getLocation().add(0, 1, 0));
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		e.setQuitMessage(ChatColor.LIGHT_PURPLE + p.getName() + " has left!");
		//updateScoreboard();
	}
	
}
