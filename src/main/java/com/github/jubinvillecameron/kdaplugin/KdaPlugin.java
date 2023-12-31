package com.github.jubinvillecameron.kdaplugin;

import com.github.jubinvillecameron.kdaplugin.commands.StatsCommand;
import com.github.jubinvillecameron.kdaplugin.listeners.onDeath;
import com.github.jubinvillecameron.kdaplugin.listeners.onJoin;
import com.github.jubinvillecameron.kdaplugin.util.PlayerStats;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public final class KdaPlugin extends JavaPlugin {

    private static HashMap<UUID, PlayerStats> playerStats = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        //Listen for kills, deaths -> assign that to a player's uuid
        //have an object which stores player's uuid (const) name, kills, deaths, kda

        //on launch don't load the object at all.
        //if a player joins -> add their object to a hashmap which can easily be looked up.
        //if not just keep it in a sql

        //just going to work on hashmap stuff right now

        this.getServer().getPluginManager().registerEvents(new onJoin(), this); //onJoin we initialize new stats object if we didn't load it
        this.getServer().getPluginManager().registerEvents(new onDeath(), this); //onDeath updates players kills and deaths if they were killed by player

        //load all of our data, if no data is found we create a folder
        getDataFolder().mkdirs();
        getDataFolder().setWritable(true);
        getDataFolder().setExecutable(true);

        //loading will be simply loading every single user from our file into our hashmap, as a server gets bigger and bigger
        //this will be bad practice, but for now its fine

        //idea for later, is having a sql data server which we can request from, and when a player logs on we request their specific data
        //then load it in our hashmap, and when they leave the server we update our sql database then remove it from the hashmap
        //for offline players we can also look things up, and through our web server we can easily find our highest values

        this.getCommand("stats").setExecutor(new StatsCommand());

        getLogger().info("Loading kills");

        //load our serialized file
        String filePath = getDataFolder() + "killStats.ser";
        try {
            // Create an ObjectInputStream to deserialize the HashMap from the specified file path
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // Deserialize the object (in this case, it should be a HashMap)
            HashMap<UUID, PlayerStats> deserializedHashMap = (HashMap<UUID, PlayerStats>) objectInputStream.readObject();

            // Close the streams
            objectInputStream.close();
            fileInputStream.close();

            // Now you can work with the deserialized HashMap
            getLogger().info("Kills have been loaded");

            playerStats = deserializedHashMap; //if File not found, it's fine since our class initializes an empty hashmap anyways



        } catch (FileNotFoundException e) {

            getLogger().info("File does not exist, will generate on plugin shutdown");
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        try {
            // Create an ObjectOutputStream to serialize the HashMap
            String filePath = getDataFolder() + "killStats.ser";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            // Serialize the HashMap
            objectOutputStream.writeObject(playerStats);

            // Close the streams
            objectOutputStream.close();
            fileOutputStream.close();

            getLogger().info("Kills have been saved");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static HashMap<UUID, PlayerStats> getPlayerStats(){

        return playerStats;
    }
}
