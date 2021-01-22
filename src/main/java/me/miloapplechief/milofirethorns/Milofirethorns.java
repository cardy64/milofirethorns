package me.miloapplechief.milofirethorns;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Adds the enchantment "fire thorns"
 * When you attack armor with fire thorns in catches the attacker on fire.
 */

public final class Milofirethorns extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
        CustomEnchants.register();
    }

    private void giveArmor(Player player, Material mat) {
        ItemStack item = new ItemStack(mat);
        item.addUnsafeEnchantment(CustomEnchants.FIRETHORNS, 1);

        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Fire Thorns I");
        meta.setLore(lore);
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equals("firethorns")) {
            if (!(sender instanceof Player)) {
                return true;
            }
            Player player = (Player) sender;

            giveArmor(player, Material.DIAMOND_HELMET);
            giveArmor(player, Material.DIAMOND_CHESTPLATE);
            giveArmor(player, Material.DIAMOND_LEGGINGS);
            giveArmor(player, Material.DIAMOND_BOOTS);
        }
        return true;
    }

    private void checkFire(Entity damager, ItemStack part) {
        if (part != null && part.hasItemMeta() && part.getItemMeta().hasEnchant(CustomEnchants.FIRETHORNS)) {
            damager.setFireTicks(damager.getFireTicks() + 20);
        }
    }

    @EventHandler
    public void onEntityHitPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        checkFire(event.getDamager(), player.getInventory().getHelmet());
        checkFire(event.getDamager(), player.getInventory().getChestplate());
        checkFire(event.getDamager(), player.getInventory().getLeggings());
        checkFire(event.getDamager(), player.getInventory().getBoots());
    }
}