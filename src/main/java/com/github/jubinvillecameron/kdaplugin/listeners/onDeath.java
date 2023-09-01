package com.github.jubinvillecameron.kdaplugin.listeners;

import com.github.jubinvillecameron.kdaplugin.KdaPlugin;
import com.github.jubinvillecameron.kdaplugin.util.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class onDeath implements Listener {

    public void onPlayerDeath(PlayerDeathEvent event){

        //get victim and killer -> update their kills/deaths in their respective playerStatsClass
        Player victim = event.getPlayer();
        Player killer = victim.getKiller();

        if(killer != null){

            //get stats, make sure it isn't null for our player then update them
            HashMap<UUID, PlayerStats> pStats = KdaPlugin.getPlayerStats();


            @NotNull PlayerStats victimStats = pStats.get(victim.getUniqueId());

            @NotNull PlayerStats killerStats = pStats.get(killer.getUniqueId());

            if (victimStats == null){
                throw new RuntimeException("Victim does not have a PlayerStats object");
            }

            if (killerStats == null){
                throw new RuntimeException("Killer does not have a PlayerStats object");
            }

            //update their deaths -> subsequently update the kda
            victimStats.addDeath(1);
            killerStats.addKill(1);
            victimStats.updateKDA();
            killerStats.updateKDA();

            //put it back where it came from
            pStats.put(killer.getUniqueId(),killerStats);
            pStats.put(victim.getUniqueId(), victimStats);

        }
    }
}
