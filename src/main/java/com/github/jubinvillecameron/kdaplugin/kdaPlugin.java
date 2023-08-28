package com.github.jubinvillecameron.kdaplugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class kdaPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        //Listen for kills, deaths -> assign that to a player's uuid
        //have an object which stores player's uuid (const) name, kills, deaths, kda

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
