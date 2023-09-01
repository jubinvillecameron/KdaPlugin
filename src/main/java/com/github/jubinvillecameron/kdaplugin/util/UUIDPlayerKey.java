package com.github.jubinvillecameron.kdaplugin.util;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UUIDPlayerKey implements Serializable {
    /*
    Class is so that our hashmap gets properly updated when a player changes their player name,
    allows us to properly lookup said players stats using the hashmap, otherwise we would have to do http requests to
    the mojang servers in order to get our said player uuid
     */
    private final UUID PLAYERUUID;
    private String playerName;

    public UUIDPlayerKey(UUID pUUID, String pName){

        this.PLAYERUUID = pUUID;
        this.playerName = pName;
    }

    public UUIDPlayerKey(Player player){

        this.PLAYERUUID = player.getUniqueId();
        this.playerName = player.getName();
    }

    @Override
    public boolean equals(Object obj){

        //if uuid & pname are same = true

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.PLAYERUUID == ((UUIDPlayerKey) obj).getPLAYERUUID() && this.playerName.equals(((UUIDPlayerKey) obj).getPlayerName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(PLAYERUUID, playerName);
    }

    public UUID getPLAYERUUID() {
        return PLAYERUUID;
    }
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String name){
        this.playerName = name;
    }
}
