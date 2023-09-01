package com.github.jubinvillecameron.kdaplugin.listeners;

import com.github.jubinvillecameron.kdaplugin.KdaPlugin;
import com.github.jubinvillecameron.kdaplugin.util.PlayerStats;
import com.github.jubinvillecameron.kdaplugin.util.UUIDPlayerKey;
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
            HashMap<UUIDPlayerKey, PlayerStats> pStats = KdaPlugin.getPlayerStats();

            UUIDPlayerKey victimUP = new UUIDPlayerKey(victim);
            UUIDPlayerKey killerUP = new UUIDPlayerKey(killer);

            @NotNull PlayerStats victimStats = pStats.get(victimUP);

            @NotNull PlayerStats killerStats = pStats.get(killerUP);

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
            pStats.put(killerUP,killerStats);
            pStats.put(victimUP, victimStats);

        }
    }
}
