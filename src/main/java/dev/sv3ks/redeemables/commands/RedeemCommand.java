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
        
        String code = args[0];

        if (!main.getConfig().isSet(code)) {
            sender.sendMessage(ChatColor.RED + "Invalid code.");
            return true;
        }

        List<String> usedBy;
        if (main.getConfig().isSet(code + ".used_by")) {
            usedBy = main.getConfig().getStringList(code + ".used_by");
        } else
            usedBy = new ArrayList<>();
        
        if (!usedBy.isEmpty()) {
            if (usedBy.contains(player.getUniqueId().toString())) {
                player.sendMessage(ChatColor.RED + "You've already used that code!");
                return true;
            }

            if (main.getConfig().isSet(code + ".uses"))
                if (usedBy.size() >= main.getConfig().getInt(code + ".uses", 0)) {
                    player.sendMessage(ChatColor.RED + "This code has reached its max uses.");
                    return true;
                }
        }

        if (main.getConfig().getInt(code + ".required empty slots", 0) > emptySlots(player)) {
            player.sendMessage(ChatColor.RED + "Not enough empty inventory slots!");
            return true;
        }
        
        usedBy.add(player.getUniqueId().toString());
        main.getConfig().set(code + ".used_by", usedBy);
        main.saveConfig();
        sender.sendMessage(ChatColor.GREEN + "Code redeemed: " + code + ChatColor.GREEN + "!");
        
        if (main.getConfig().isSet(code + ".commands"))
            for (String rewardCommand : main.getConfig().getStringList(code + ".commands")) {
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        rewardCommand.replace("%PLAYER%", player.getName())
                );
            }
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
