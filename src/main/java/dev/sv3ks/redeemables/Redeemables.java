package dev.sv3ks.redeemables;

import dev.sv3ks.redeemables.commands.MainCommand;
import dev.sv3ks.redeemables.commands.RedeemCommand;
import dev.sv3ks.redeemables.commands.ReloadCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Redeemables extends JavaPlugin {

    private static Plugin plugin;
    public static Plugin getPlugin() { return plugin; }

    @Override
    public void onEnable() {
        // Plugin startup logic

        plugin = this;

        // Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Commands
        getCommand("redeem").setExecutor(new RedeemCommand());
        getCommand("rareload").setExecutor(new ReloadCommand());
        getCommand("ra").setExecutor(new MainCommand());

        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("Disabled");
    }
}
