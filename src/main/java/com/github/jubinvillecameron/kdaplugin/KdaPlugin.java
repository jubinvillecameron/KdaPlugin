package com.github.jubinvillecameron.kdaplugin;

import com.github.jubinvillecameron.kdaplugin.listeners.onDeath;
import com.github.jubinvillecameron.kdaplugin.listeners.onFirstJoin;
import org.bukkit.plugin.java.JavaPlugin;

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

        this.getServer().getPluginManager().registerEvents(new onFirstJoin(), this); //onJoin we initialize new stats object if we didn't load it
        this.getServer().getPluginManager().registerEvents(new onDeath(), this); //onDeath updates players kills and deaths if they were killed by player


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static HashMap<UUID, PlayerStats> getPlayerStats(){

        return playerStats;
    }
}
