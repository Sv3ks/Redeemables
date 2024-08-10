package dev.sv3ks.redeemables.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static dev.sv3ks.redeemables.Redeemables.getPlugin;

public class RedeemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can redeem rewards!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cIncorrect syntax. Correct syntax: /redeem <code>");
            return true;
        }

        if (!getPlugin().getConfig().isSet(args[0])) {
            sender.sendMessage("§cInvalid code.");
            return true;
        }

        Player player = (Player) sender;

        if (
                !getPlugin().getConfig().isSet(args[0] + ".used_by") ||
                !getPlugin().getConfig().getStringList(args[0] + ".used_by").contains(player.getUniqueId().toString())
        ) {
            List<String> used_by = new ArrayList<>();
            if (getPlugin().getConfig().isSet(args[0] + ".used_by")) {
                used_by = getPlugin().getConfig().getStringList(args[0] + ".used_by");
            }
            used_by.add(player.getUniqueId().toString());
            getPlugin().getConfig().set(args[0] + ".used_by", used_by);
            getPlugin().saveConfig();
            try {
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        getPlugin().getConfig().getString(args[0] + ".command")
                                .replace("%PLAYER%", player.getName())
                );
            } catch (NullPointerException e) {
                sender.sendMessage("§4Internal Error: " + args[0] + ".command is not set.");
                return true;
            }
            sender.sendMessage("§aYou redeemed " + args[0] + "§a!");
            return true;
        }

        if (
                getPlugin().getConfig().getStringList(args[0] + ".used_by").contains(player.getUniqueId().toString())
        ) {
            sender.sendMessage("§cYou already used that code!");
            return true;
        }

        if (
                getPlugin().getConfig().getStringList(args[0] + ".used_by").size() ==
                        getPlugin().getConfig().getInt(args[0] + ".uses")
        ) {
            sender.sendMessage("§cThe code has reached its max uses.");
            return true;
        }


        player.sendMessage("§cI have no idea what happened.");
        return true;
    }
}
