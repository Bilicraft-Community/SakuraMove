package com.entiv.sakuramove.listener;

import com.entiv.sakuramove.action.Sprint;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class SpringListener implements Listener {

    Sprint sprint = Sprint.getInstance();

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {

        Action action = event.getAction();
        Player player = event.getPlayer();

        if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
        List<String> allowItems = Arrays.asList("SWORD", "AXE", "PICKAXE");

        for (String allowItem : allowItems) {
            boolean isAllowItem = itemStack.getType().toString().contains(allowItem);

            if (!isAllowItem) continue;

            if (player.isSprinting() && !sprint.isCoolDown(player)) {
                if(!player.isGliding()) { // Prevent player sprinting while using Elytra
                    sprint.accept(player);
                    sprint.setCoolDown(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        sprint.clearCache(player);
    }
}
