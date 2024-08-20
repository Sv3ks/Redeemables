package dev.sv3ks.redeemables.commands;

import dev.sv3ks.redeemables.Redeemables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RedeemCommand implements CommandExecutor {
    
    final Redeemables main;

    public RedeemCommand(Redeemables main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can redeem rewards!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Incorrect syntax. Correct syntax: /redeem <code>");
            return true;
        }

        if (!main.getConfig().isSet(args[0])) {
            sender.sendMessage(ChatColor.RED + "Invalid code.");
            return true;
        }

        if (main.getConfig().isSet(args[0] + ".used_by")) {
            if (main.getConfig().getStringList(args[0] + ".used_by").contains(player.getUniqueId().toString())) {
                sender.sendMessage(ChatColor.RED + "You already used that code!");
                return true;
            }
            if (main.getConfig().getStringList(args[0] + ".used_by").size() == main.getConfig().getInt(args[0] + ".uses")) {
                sender.sendMessage(ChatColor.RED + "The code has reached its max uses.");
                return true;
            }
        }

        List<String> used_by = new ArrayList<>();
        if (main.getConfig().isSet(args[0] + ".used_by")) {
            used_by = main.getConfig().getStringList(args[0] + ".used_by");
        }

        if (main.getConfig().getInt(args[0] + ".required empty slots", 0) > emptySlots(player)) {
            player.sendMessage(ChatColor.RED + "Not enough empty inventory slots!");
            return true;
        }

        used_by.add(player.getUniqueId().toString());
        main.getConfig().set(args[0] + ".used_by", used_by);
        main.saveConfig();
        try {
            for (String rewardCommand :
                    main.getConfig().getStringList(args[0] + ".commands")) {
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        rewardCommand.replace("%PLAYER%", player.getName())
                );
            }
        } catch (NullPointerException e) {
            sender.sendMessage(ChatColor.DARK_RED + "Internal Error: " + args[0] + ".commands is not set.");
            return true;
        }
        sender.sendMessage(ChatColor.GREEN + "You redeemed " + args[0] + ChatColor.GREEN + "!");
        return true;
    }

    private int emptySlots(Player player) {
        int slots = 0;
        for (int i = 0; i < 35; i++) {
            if (player.getInventory().getItem(i) == null)
                slots++;
        }
        return slots;
    }

}
