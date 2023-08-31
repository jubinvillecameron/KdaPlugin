package com.github.jubinvillecameron.kdaplugin.listeners;

import com.github.jubinvillecameron.kdaplugin.KdaPlugin;
import com.github.jubinvillecameron.kdaplugin.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class onFirstJoin implements Listener {


    public void onPlayerJoin(PlayerJoinEvent event) {

        Player p = event.getPlayer();
        HashMap<UUID, PlayerStats> pStats = KdaPlugin.getPlayerStats();

        //if join and their uuid is not contained in the hashmap, we create their object and store it in the hashmap
        //otherwise carry on

        if (!pStats.containsKey(p.getUniqueId())){

            PlayerStats newPlayer = new PlayerStats(p);
            pStats.put(p.getUniqueId(), newPlayer);
        }



    }
}
