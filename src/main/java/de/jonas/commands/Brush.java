package de.jonas.commands;

import de.jonas.util.Methods;
import de.jonas.worldeditor.WorldEditor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Brush implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("worldeditor")) {
                if(args.length == 2) {
                    Methods.brush(p, size(p, args[0]), mat(p, args[1]));
                } else
                    p.sendMessage(WorldEditor.prefix+"Bitte benutze //brush <Größe> <Material>");
            } else
                p.sendMessage("§cDazu hast du keine Rechte!");
        } else
            sender.sendMessage("Du musst ein Spieler sein!");
        return true;
    }

    public int size(Player p, String s) {
        int i = 0;
        try {
            i = Integer.valueOf(s);
        } catch (Exception e) {
            p.sendMessage(WorldEditor.prefix+"Bitte gebe eine Zahl zwischen 1 und 10 ein!");
        }
        return i;
    }

    public Material mat(Player p, String s) {
        Material material = null;
        try {
            material = Material.getMaterial(s);
        } catch (Exception e) {
            p.sendMessage(WorldEditor.prefix+"Bitte gebe das Material ein!");
        }
        return material;
    }
}
