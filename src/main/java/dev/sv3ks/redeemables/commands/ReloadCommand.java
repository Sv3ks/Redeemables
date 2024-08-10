package dev.sv3ks.redeemables.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static dev.sv3ks.redeemables.Redeemables.getPlugin;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        getPlugin().reloadConfig();
        getPlugin().saveConfig();
        sender.sendMessage("§7Reloading codes...");
        return false;
    }
}
