package dev.sv3ks.redeemables.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class CommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> completions  = new ArrayList<>();
		if (args.length == 0) {
			completions.add("help");
			completions.add("add");
			completions.add("addcode");
			completions.add("addcmd");
			completions.add("addcommand");
		} else if (Arrays.asList("add","addcode").contains(args[0])) {
			if (args.length == 1) completions.add("<reward-code>");
			else if (args.length == 2) completions.add("<uses>");
			else completions = null;
		} else if (Arrays.asList("addcmd","addcommand").contains(args[0])) {
			if (args.length == 1) completions.add("<reward-code>");
			else if (args.length == 2) completions.add("<command>");
			else completions = null;
		} else {
			return completions = null;
		}
		return completions;
	}

}
