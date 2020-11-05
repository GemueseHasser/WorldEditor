package de.jonas.commands;

import de.jonas.util.Methods;
import de.jonas.worldeditor.WorldEditor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Pos2 implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("worldeditor")) {
                if(args.length == 0) {
                    Methods.location2.put(p, p.getLocation());
                    p.sendMessage(WorldEditor.prefix + "Location 2 Set!");
                } else
                    p.sendMessage(WorldEditor.prefix+"Bitte benutze //pos2!");
            } else
                p.sendMessage("§cDazu hast du keine Rechte!");
        } else
            sender.sendMessage("Du musst ein Spieler sein!");
        return true;
    }
}
