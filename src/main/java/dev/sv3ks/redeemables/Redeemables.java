package dev.sv3ks.redeemables;

import dev.sv3ks.redeemables.commands.RedeemCommand;
import dev.sv3ks.redeemables.commands.ReloadCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Redeemables extends JavaPlugin {

    @Override
    public void onEnable() {
        // Config
        saveDefaultConfig();

        // Commands
        registerCommands();
    }

    @Override
    public void onDisable() {
    }

    private void registerCommands() {
        PluginCommand redeemCommand = getCommand("redeem"),
                reloadCommand = getCommand("rareload");

        if (redeemCommand != null)
            redeemCommand.setExecutor(new RedeemCommand(this));
        if (reloadCommand != null)
            reloadCommand.setExecutor(new ReloadCommand(this));

    }

}
