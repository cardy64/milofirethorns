package me.miloapplechief.milofirethorns;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Methods for registering our enchantments.
 */
public class CustomEnchants {

    public static final Enchantment FIRETHORNS = new EnchantmentWrapper("firethorns","Fire Thorns",1);

    public static void register() {
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(FIRETHORNS);
        if (!registered) {
            registerEnchantment(FIRETHORNS);
        }
    }

    private static void registerEnchantment(Enchantment enchantment) {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
