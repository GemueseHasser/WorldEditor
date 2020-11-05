package de.jonas.util;

import de.jonas.worldeditor.WorldEditor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class Methods {

    public static HashMap<Player, Location> location1 = new HashMap<Player, Location>();
    public static HashMap<Player, Location> location2 = new HashMap<Player, Location>();

    public static HashMap<Integer, HashMap<Location, HashMap<Integer, Integer>>> ux = new HashMap<Integer, HashMap<Location, HashMap<Integer, Integer>>>();

    public static ArrayList<Player> expander = new ArrayList<Player>();

    public static HashMap<Player, Integer> newestundo = new HashMap<Player, Integer>();
    public static HashMap<Player, HashMap<Integer, HashMap<Location, HashMap<Integer, Integer>>>> undos = new HashMap<Player, HashMap<Integer, HashMap<Location, HashMap<Integer, Integer>>>>();

    static int newundo = 0;

    public static List<Location> getLocationsFromTwo(Location loc1, Location loc2) {

        int xTop = 0;
        int xBottom = 0;
        int yTop = 0;
        int yBottom = 0;
        int zTop = 0;
        int zBottom = 0;

        List<Location> locs = new ArrayList<Location>();

        if(loc1.getBlockY() >= loc2.getBlockY()) {
            yTop = loc1.getBlockY();
            yBottom = loc2.getBlockY();
        } else {
            yTop = loc2.getBlockY();
            yBottom = loc1.getBlockY();
        }

        if(loc1.getBlockX() >= loc2.getBlockX()) {
            xTop = loc1.getBlockX();
            xBottom = loc2.getBlockX();
        } else {
            xTop = loc2.getBlockX();
            xBottom = loc1.getBlockX();
        }

        if(loc1.getBlockZ() >= loc2.getBlockZ()) {
            zTop = loc1.getBlockZ();
            zBottom = loc2.getBlockZ();
        } else {
            zTop = loc2.getBlockZ();
            zBottom = loc1.getBlockZ();
        }

        for(int x = xBottom; x <= xTop; x++) {
            for(int y = yBottom; y <= yTop; y++) {
                for(int z = zBottom; z <= zTop; z++) {
                    locs.add(new Location(loc1.getWorld(), x, y, z));
                }
            }
        }

        return locs;
    }

    public static void setBlock(Player p, String mat) {
        if(newestundo.containsKey(p)) {
            newundo = newestundo.get(p) + 1;
        } else {
            newundo = 1;
        }
        HashMap<Location, HashMap<Integer, Integer>> undohash = new HashMap<Location, HashMap<Integer, Integer>>();

        List<String> ids = new ArrayList<String>(Arrays.asList(mat.split(",")));

        if(location1.containsKey(p) && location2.containsKey(p)) {
            if(!(expander.contains(p))) {
                for (Location loc : getLocationsFromTwo(location1.get(p), location2.get(p))) {
                    Random random = new Random();
                    int search = random.nextInt(ids.size());

                    int id = 0;
                    int subid = 0;

                    if (ids.get(search).contains(":")) {
                        String[] x = mat.split(":");
                        id = Integer.valueOf(x[0]);
                        subid = Integer.valueOf(x[1]);
                    } else {
                        id = Integer.valueOf(ids.get(search));
                    }
                    HashMap<Integer, Integer> us = new HashMap<Integer, Integer>();
                    us.put(loc.getBlock().getTypeId(), (int) loc.getBlock().getData());
                    undohash.put(loc, us);

                    loc.getBlock().setType(Material.getMaterial(id));
                    loc.getBlock().setData((byte) subid);
                }
                if (undos.containsKey(p)) {
                    for (int i : undos.get(p).keySet()) {
                        ux.put(i, undos.get(p).get(i));
                    }
                }
                ux.put(newundo, undohash);
                undos.put(p, ux);
                newestundo.put(p, newundo);

                p.sendMessage(WorldEditor.prefix + "Blocks set!");
            } else {
                for (Location loc : expandVert(location1.get(p), location2.get(p))) {
                    Random random = new Random();
                    int search = random.nextInt(ids.size());

                    int id = 0;
                    int subid = 0;

                    if (ids.get(search).contains(":")) {
                        String[] x = mat.split(":");
                        id = Integer.valueOf(x[0]);
                        subid = Integer.valueOf(x[1]);
                    } else {
                        id = Integer.valueOf(ids.get(search));
                    }
                    HashMap<Integer, Integer> us = new HashMap<Integer, Integer>();
                    us.put(loc.getBlock().getTypeId(), (int) loc.getBlock().getData());
                    undohash.put(loc, us);

                    loc.getBlock().setType(Material.getMaterial(id));
                    loc.getBlock().setData((byte) subid);
                }
                if (undos.containsKey(p)) {
                    for (int i : undos.get(p).keySet()) {
                        ux.put(i, undos.get(p).get(i));
                    }
                }
                ux.put(newundo, undohash);
                undos.put(p, ux);
                newestundo.put(p, newundo);

                expander.remove(p);

                p.sendMessage(WorldEditor.prefix + "Blocks set!");
            }
        } else
            p.sendMessage(WorldEditor.prefix+"Please select your Positions first!");
    }

    public static void undo(Player p) {
        if(newestundo.containsKey(p)) {
            int undo = newestundo.get(p);

            HashMap<Integer, HashMap<Location, HashMap<Integer, Integer>>> preundo = undos.get(p);
            HashMap<Location, HashMap<Integer, Integer>> undoh = undos.get(p).get(undo);

            for(Location loc : undoh.keySet()) {
                for(int id : undoh.get(loc).keySet()) {
                    loc.getBlock().setTypeId(id);
                    int subid = undoh.get(loc).get(id);
                    loc.getBlock().setData((byte) subid);
                }
            }
            if(undo > 1) {
                newestundo.put(p, undo-1);
            } else {
                newestundo.remove(p);
            }

            preundo.remove(undo);
            undos.put(p, preundo);

            p.sendMessage(WorldEditor.prefix+"Undo successful!");
        } else {
            p.sendMessage(WorldEditor.prefix+"Nothing to undo!");
        }
    }

    public static void replaceAll(Player p, String args0) {
        if(newestundo.containsKey(p)) {
            newundo = newestundo.get(p) + 1;
        } else {
            newundo = 1;
        }
        HashMap<Location, HashMap<Integer, Integer>> undohash = new HashMap<Location, HashMap<Integer, Integer>>();

        List<String> ids = new ArrayList<String>(Arrays.asList(args0.split(",")));

        if(location1.containsKey(p) && location2.containsKey(p)) {
            if(expander.contains(p)) {
                for (Location loc : expandVert(location1.get(p), location2.get(p))) {
                    Random random = new Random();
                    int search = random.nextInt(ids.size());

                    int id = 0;
                    int subid = 0;

                    if (ids.get(search).contains(":")) {
                        String[] x = args0.split(":");
                        id = Integer.valueOf(x[0]);
                        subid = Integer.valueOf(x[1]);
                    } else {
                        id = Integer.valueOf(ids.get(search));
                    }
                    HashMap<Integer, Integer> us = new HashMap<Integer, Integer>();
                    if (loc.getBlock().getTypeId() != 0) {
                        us.put(loc.getBlock().getTypeId(), (int) loc.getBlock().getData());
                        undohash.put(loc, us);

                        loc.getBlock().setType(Material.getMaterial(id));
                        loc.getBlock().setData((byte) subid);
                    }
                }
                if (undos.containsKey(p)) {
                    for (int i : undos.get(p).keySet()) {
                        ux.put(i, undos.get(p).get(i));
                    }
                }
                ux.put(newundo, undohash);
                undos.put(p, ux);
                newestundo.put(p, newundo);

                p.sendMessage(WorldEditor.prefix + "Blocks replaced!");
            } else {
                for (Location loc : getLocationsFromTwo(location1.get(p), location2.get(p))) {
                    Random random = new Random();
                    int search = random.nextInt(ids.size());

                    int id = 0;
                    int subid = 0;

                    if (ids.get(search).contains(":")) {
                        String[] x = args0.split(":");
                        id = Integer.valueOf(x[0]);
                        subid = Integer.valueOf(x[1]);
                    } else {
                        id = Integer.valueOf(ids.get(search));
                    }
                    HashMap<Integer, Integer> us = new HashMap<Integer, Integer>();
                    if (loc.getBlock().getTypeId() != 0) {
                        us.put(loc.getBlock().getTypeId(), (int) loc.getBlock().getData());
                        undohash.put(loc, us);

                        loc.getBlock().setType(Material.getMaterial(id));
                        loc.getBlock().setData((byte) subid);
                    }
                }
                if (undos.containsKey(p)) {
                    for (int i : undos.get(p).keySet()) {
                        ux.put(i, undos.get(p).get(i));
                    }
                }
                ux.put(newundo, undohash);
                undos.put(p, ux);
                newestundo.put(p, newundo);

                p.sendMessage(WorldEditor.prefix + "Blocks replaced!");
            }
        } else
            p.sendMessage(WorldEditor.prefix+"Please select your Positions first!");
    }

    public static void replaceBlock(Player p, int args0, String args1) {
        if(newestundo.containsKey(p)) {
            newundo = newestundo.get(p) + 1;
        } else {
            newundo = 1;
        }
        HashMap<Location, HashMap<Integer, Integer>> undohash = new HashMap<Location, HashMap<Integer, Integer>>();

        List<String> ids = new ArrayList<String>(Arrays.asList(args1.split(",")));

        if(location1.containsKey(p) && location2.containsKey(p)) {
            if(expander.contains(p)) {
                for (Location loc : expandVert(location1.get(p), location2.get(p))) {
                    Random random = new Random();
                    int search = random.nextInt(ids.size());

                    int id = 0;
                    int subid = 0;

                    if (ids.get(search).contains(":")) {
                        String[] x = args1.split(":");
                        id = Integer.valueOf(x[0]);
                        subid = Integer.valueOf(x[1]);
                    } else {
                        id = Integer.valueOf(ids.get(search));
                    }
                    HashMap<Integer, Integer> us = new HashMap<Integer, Integer>();
                    if (loc.getBlock().getTypeId() == args0) {
                        us.put(loc.getBlock().getTypeId(), (int) loc.getBlock().getData());
                        undohash.put(loc, us);

                        loc.getBlock().setType(Material.getMaterial(id));
                        loc.getBlock().setData((byte) subid);
                    }
                }
                if (undos.containsKey(p)) {
                    for (int i : undos.get(p).keySet()) {
                        ux.put(i, undos.get(p).get(i));
                    }
                }
                ux.put(newundo, undohash);
                undos.put(p, ux);
                newestundo.put(p, newundo);

                p.sendMessage(WorldEditor.prefix + "Blocks replaced!");
            } else {
                for (Location loc : getLocationsFromTwo(location1.get(p), location2.get(p))) {
                    Random random = new Random();
                    int search = random.nextInt(ids.size());

                    int id = 0;
                    int subid = 0;

                    if (ids.get(search).contains(":")) {
                        String[] x = args1.split(":");
                        id = Integer.valueOf(x[0]);
                        subid = Integer.valueOf(x[1]);
                    } else {
                        id = Integer.valueOf(ids.get(search));
                    }
                    HashMap<Integer, Integer> us = new HashMap<Integer, Integer>();
                    if (loc.getBlock().getTypeId() == args0) {
                        us.put(loc.getBlock().getTypeId(), (int) loc.getBlock().getData());
                        undohash.put(loc, us);

                        loc.getBlock().setType(Material.getMaterial(id));
                        loc.getBlock().setData((byte) subid);
                    }
                }
                if (undos.containsKey(p)) {
                    for (int i : undos.get(p).keySet()) {
                        ux.put(i, undos.get(p).get(i));
                    }
                }
                ux.put(newundo, undohash);
                undos.put(p, ux);
                newestundo.put(p, newundo);

                p.sendMessage(WorldEditor.prefix + "Blocks replaced!");
            }
        } else
            p.sendMessage(WorldEditor.prefix+"Please select your Positions first!");
    }

    public static List<Location> expandVert(Location loc1, Location loc2) {
        int xTop = 0;
        int xBottom = 0;
        int yTop = 255;
        int yBottom = 1;
        int zTop = 0;
        int zBottom = 0;

        List<Location> locs = new ArrayList<Location>();

        if(loc1.getBlockX() >= loc2.getBlockX()) {
            xTop = loc1.getBlockX();
            xBottom = loc2.getBlockX();
        } else {
            xTop = loc2.getBlockX();
            xBottom = loc1.getBlockX();
        }

        if(loc1.getBlockZ() >= loc2.getBlockZ()) {
            zTop = loc1.getBlockZ();
            zBottom = loc2.getBlockZ();
        } else {
            zTop = loc2.getBlockZ();
            zBottom = loc1.getBlockZ();
        }

        for(int x = xBottom; x <= xTop; x++) {
            for(int y = yBottom; y <= yTop; y++) {
                for(int z = zBottom; z <= zTop; z++) {
                    locs.add(new Location(loc1.getWorld(), x, y, z));
                }
            }
        }

        return locs;
    }

    public static void redo(Player p) {
        p.sendMessage(WorldEditor.prefix+"In Work! :D");
    }

    public static void brush(Player p, int size, Material material) {
        double x = p.getLocation().getX();
        double y = p.getLocation().getY();
        double z = p.getLocation().getZ();
        float yaw = p.getLocation().getYaw();
        float pitch = p.getLocation().getPitch();
        Location loc = new Location(p.getWorld(), x, y, z, yaw, pitch);
        for(Location LOC = loc; loc.getBlock().getType() == Material.AIR; z++) {
            loc = new Location(p.getWorld(), x, y, z, yaw, pitch);
        }
        if(size < 10) {
            if(size == 1) {
                loc.getBlock().setType(material);
            }
        } else {
            p.sendMessage(WorldEditor.prefix+"Bitte wähle eine Größe zwischen 1 und 10!");
        }
    }
}
