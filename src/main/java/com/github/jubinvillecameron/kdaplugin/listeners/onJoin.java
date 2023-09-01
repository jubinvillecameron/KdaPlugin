package com.github.jubinvillecameron.kdaplugin.listeners;

import com.github.jubinvillecameron.kdaplugin.KdaPlugin;
import com.github.jubinvillecameron.kdaplugin.util.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class onJoin implements Listener {


    public void onPlayerJoin(PlayerJoinEvent event) {

        //setup a player in our hashmap if we didn't already load them on init

        Player p = event.getPlayer();
        HashMap<UUID, PlayerStats> pStats = KdaPlugin.getPlayerStats();

        //if join and their uuid is not contained in the hashmap, we create their object and store it in the hashmap
        //otherwise carry on

        pStats.computeIfAbsent(p.getUniqueId(), key -> new PlayerStats(p));

    }
}
