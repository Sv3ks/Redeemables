package dev.sv3ks.redeemables.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.sv3ks.redeemables.Redeemables.getPlugin;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
            commandSender.sendMessage(
                    "§a/ra info\n" +
                    "§a/ra <add|addcode> <reward-code> <uses>\n" +
                    "§a/ra <addcmd|addcommand> <reward-code> <command>"
            );
            return true;
        }

        if (
                args.length == 3 &&
                (
                        args[0].equalsIgnoreCase("add") ||
                        args[0].equalsIgnoreCase("addcode")
                )
        ) {
            try {
                getPlugin().getConfig().set(args[1]+".uses", Integer.valueOf(args[2]));
                getPlugin().saveConfig();
                commandSender.sendMessage("§aAdded redeemable " + args[1] + " with " + args[2] + " uses.");
                return true;
            } catch (NumberFormatException e) {
                commandSender.sendMessage("§cInvalid amount of uses!");
                return true;
            }
        }

        if (
                args.length <= 3 &&
                (
                        args[0].equalsIgnoreCase("addcmd") ||
                        args[0].equalsIgnoreCase("addcommand")
                )
        ) {
            if (!getPlugin().getConfig().isSet(args[0])) {
                commandSender.sendMessage("§cInvalid code!");
                return true;
            }

            String cmd = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            List<String> commands = new ArrayList<>();
            if (getPlugin().getConfig().isSet(args[0] + ".commands")) {
                commands = getPlugin().getConfig().getStringList(args[0] + ".commands");
            }
            commands.add(cmd);
            getPlugin().getConfig().set(args[0] + ".commands", commands);
            getPlugin().saveConfig();
            commandSender.sendMessage("§aAdded " + cmd + " to " + args[0] + "'s commands.");
        }

        commandSender.sendMessage("§cIncorrect usage. See correct usage with /ra information.");
        return true;
    }
}
