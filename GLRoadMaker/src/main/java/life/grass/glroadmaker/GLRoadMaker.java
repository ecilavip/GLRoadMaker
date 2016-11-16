package life.grass.glroadmaker;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GLRoadMaker extends JavaPlugin implements Listener {
	@Override
    public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onCreatingPath(PlayerInteractEvent e) {
		Block target = e.getClickedBlock();
		if(target.getType().equals(Material.DIRT)) {
			Location up = target.getLocation().add(0, 1, 0);
			World world = Bukkit.getServer().getWorld(target.getWorld().getName());
			if(world.getBlockAt(up).getType().equals(Material.AIR)) {
				Player player = e.getPlayer();
				ItemStack mainHand = player.getInventory().getItemInMainHand();
				ItemStack leftHand = player.getInventory().getItemInOffHand();
				if(leftHand.getType().equals(Material.WHEAT)&& mainHand.getType().toString().endsWith("SPADE")) {
					if(leftHand.getAmount() == 1) {
						leftHand.setType(Material.AIR);
						player.getInventory().setItemInOffHand(leftHand);
					} else {
					   leftHand.setAmount(leftHand.getAmount() - 1); 
					}
					mainHand.setDurability((short) (mainHand.getDurability() + 1));
					player.getInventory().setItemInMainHand(mainHand);
					target.setType(Material.GRASS_PATH);
					world.playSound(target.getLocation(), Sound.ITEM_HOE_TILL, 1, 1);
				}
			}
		}
	}
	
}
