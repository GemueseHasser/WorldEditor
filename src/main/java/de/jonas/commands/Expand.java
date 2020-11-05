package de.jonas.commands;

import de.jonas.util.Methods;
import de.jonas.worldeditor.WorldEditor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Expand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("worldeditor")) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("vert")) {
                        if(!(Methods.expander.contains(p))) {
                            Methods.expander.add(p);
                        }
                        p.sendMessage(WorldEditor.prefix+"Vert expanded!");
                    } else
                        p.sendMessage(WorldEditor.prefix+"Bitte benutze //expand <vert>");
                } else
                    p.sendMessage(WorldEditor.prefix+"Bitte benutze //expand <vert>");
            } else
                p.sendMessage("§cDazu hast du keine Rechte!");
        } else
            sender.sendMessage("Du musst ein Spieler sein");
        return true;
    }
}
