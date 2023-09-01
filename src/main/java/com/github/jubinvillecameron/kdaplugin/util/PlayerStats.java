package com.github.jubinvillecameron.kdaplugin.util;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public class PlayerStats implements Serializable {

    /*
    All player data is stores and serialized in this variable.
    There are 5 variables stored:
    const UUID, kills, deaths, kda, and the player's username.
    Contains getter and setter methods as well as update methods
     */

    private final UUID PLAYERUUID; //store player's uuid which never changes

    private int kills;
    private int deaths;
    private int kda; //kill death ration k/d

    private String name;

    public PlayerStats(Player player){

        PLAYERUUID = player.getUniqueId();
        name = player.getName();
        kills = 0;
        deaths = 0;
        kda = 0;

    }

    //getter methods
    public int getKda() {
        return kda;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getKills() {
        return kills;
    }

    public String getName() {
        return name;
    }

    public UUID getPLAYERUUID() {
        return PLAYERUUID;
    }

    //setter methods
    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setKda(int kda) {
        this.kda = kda;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setName(String name) {
        this.name = name;
    }

    //update functions

    public void updateKDA(){
        //Update kda to reflect the current kills/deaths
        this.kda = this.kills/this.deaths;
    }

    public void addKill(int i){
        //add i kills
        this.kills += i;
    }

    public void addDeath(int i){
        //add i deaths
        this.deaths += i;
    }
}
