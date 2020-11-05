package de.jonas.worldeditor;

import de.jonas.commands.*;
import de.jonas.listener.OnInteract;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldEditor extends JavaPlugin {

    private static WorldEditor plugin;
    public static String prefix = "§f§l[§cWorld-Editor§f§l] §6";

    @Override
    public void onEnable() {
        plugin = this;

        getCommand("/set").setExecutor(new Set());
        getCommand("/pos1").setExecutor(new Pos1());
        getCommand("/pos2").setExecutor(new Pos2());
        getCommand("/undo").setExecutor(new Undo());
        getCommand("/replace").setExecutor(new Replace());
        getCommand("/expand").setExecutor(new Expand());
        getCommand("/redo").setExecutor(new Redo());

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new OnInteract(), this);

        loadConfig();

        Bukkit.getConsoleSender().sendMessage(prefix+"Das Plugin wurde erfolgreich aktiviert!");
    }

    @Override
    public void onDisable() {
        System.out.println("[World-Editor] Das Plugin wurde deaktiviert!");
    }

    public static WorldEditor getPlugin() {
        return plugin;
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
