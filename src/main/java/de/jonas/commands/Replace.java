package de.jonas.commands;

import de.jonas.util.Methods;
import de.jonas.worldeditor.WorldEditor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Replace implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("worldeditor")) {
                if(args.length == 1) {
                    try {
                        Methods.replaceAll(p, args[0]);
                    } catch (Exception e) {
                        try {
                            ItemStack i = new ItemStack(Material.getMaterial(args[0].toUpperCase()));
                            int id = i.getTypeId();
                            Methods.replaceAll(p, String.valueOf(id));
                        } catch (Exception e1) {
                            p.sendMessage(WorldEditor.prefix + "You must type an Material or an ID");
                        }
                    }
                } else if(args.length == 2) {
                    try {
                        Methods.replaceBlock(p, Integer.valueOf(args[0]), args[1]);
                    } catch (Exception e) {
                        try {
                            ItemStack i = new ItemStack(Material.getMaterial(args[0].toUpperCase()));
                            ItemStack ii = new ItemStack(Material.getMaterial(args[1].toUpperCase()));
                            int id = i.getTypeId();
                            int id1 = ii.getTypeId();
                            Methods.replaceBlock(p, id, String.valueOf(id1));
                        } catch (Exception e1) {
                            try {
                                ItemStack ii = new ItemStack(Material.getMaterial(args[1].toUpperCase()));
                                int id1 = ii.getTypeId();
                                Methods.replaceBlock(p, Integer.valueOf(args[0]), String.valueOf(id1));
                            } catch (Exception e2) {
                                try {
                                    ItemStack ii = new ItemStack(Material.getMaterial(args[0].toUpperCase()));
                                    int id1 = ii.getTypeId();
                                    Methods.replaceBlock(p, id1, args[1]);
                                } catch (Exception e3) {
                                    p.sendMessage(WorldEditor.prefix + "You must type an Material or an ID");
                                }
                            }
                        }
                    }
                } else
                    p.sendMessage(WorldEditor.prefix+"Bitte benutze //replace <material | oldMaterial newMaterial>");
            } else
                p.sendMessage("§cDazu hast du keine Rechte!");
        } else
            sender.sendMessage("Du musst ein Spieler sein");
        return true;
    }
}
