package dev.sv3ks.redeemables.commands;

import dev.sv3ks.redeemables.Redeemables;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    final Redeemables main;

    public ReloadCommand(Redeemables main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        main.reloadConfig();
        main.saveConfig();
        sender.sendMessage(ChatColor.GRAY + "Reloading codes...");
        return false;
    }
}
