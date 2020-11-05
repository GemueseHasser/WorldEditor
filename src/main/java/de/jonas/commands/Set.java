package de.jonas.commands;

import de.jonas.util.Methods;
import de.jonas.worldeditor.WorldEditor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Set implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("wordleditor")) {
                if (args.length == 1) {
                    try {
                        Methods.setBlock(p, args[0]);
                    } catch (Exception e) {
                        try {
                            ItemStack i = new ItemStack(Material.getMaterial(args[0].toUpperCase()));
                            int id = i.getTypeId();
                            Methods.setBlock(p, String.valueOf(id));
                        } catch (Exception e1) {
                            p.sendMessage(WorldEditor.prefix + "You must type an Material or an ID");
                        }
                    }
                } else
                    p.sendMessage(WorldEditor.prefix + "Bitte benutze: //set <id:subid>");
            } else
                p.sendMessage("§cDazu hast du keine Rechte!");
        } else
            sender.sendMessage("Du musst ein Spieler sein!");
        return true;
    }
}
