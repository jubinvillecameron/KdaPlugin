package com.github.jubinvillecameron.kdaplugin.commands;

import com.github.jubinvillecameron.kdaplugin.KdaPlugin;
import com.github.jubinvillecameron.kdaplugin.util.PlayerStats;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;


public class StatsCommand implements CommandExecutor {

    //#DBDBDB = platinum, #85C7F2 = blue
    //text = plat, blue = highlight


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //type /stats check <name> to display a player's stats
        //will also have a command /stats generatefakedata which allows us to look at all the stats
        //then a /stats top kda/kills/deaths to display the stats

        //first command /stats check
        if (args.length == 2 && args[0].equalsIgnoreCase("check")) {

            //send a request to the mojang api looking for their uuid
            String playerName = args[1];

            //taken from https://wiki.vg/Mojang_API#Username_to_UUID
            String apiEndpoint = "https://api.mojang.com/users/profiles/minecraft/" + playerName;


            //send api request to mojang

            try {

                URL url = new URL(apiEndpoint);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                //if our response code = 200 it's success

                if (responseCode == 200){

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();

                    String line;

                    //get json response
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    reader.close();

                    //now parse response
                    String jsonResponse = response.toString();
                    String uuid = jsonResponse.split("\"")[3];


                    //get player's stats
                    HashMap<UUID,PlayerStats> playerStatsHashMap = KdaPlugin.getPlayerStats();
                    PlayerStats playerStats = playerStatsHashMap.get(UUID.fromString(uuid));

                    //null = player not found

                    if (playerStats != null){

                        /*
                        <username> stats: (underline)
                        Kills: <number>
                        Deaths: <number>
                        KDA: <number
                         */

                        TextComponent menu = Component.text().content(playerStats.getName()).color(TextColor.color(0x85C7F2)).decoration(TextDecoration.UNDERLINED, true)
                                .append(Component.text().content(" stats").color(TextColor.color(0xDBDBDB)).decoration(TextDecoration.UNDERLINED, true)).build();

                        sender.sendMessage(menu);

                        Integer kills = Integer.valueOf(playerStats.getKills());
                        Integer deaths = Integer.valueOf(playerStats.getDeaths());
                        Integer kda = Integer.valueOf(playerStats.getKda());


                        TextComponent killsText = Component.text().content("Kills: ").color(TextColor.color(0xDBDBDB)).append(Component.text().content(kills.toString()).color(TextColor.color(0x85C7F2))).build();
                        sender.sendMessage(killsText);

                        TextComponent deathText = Component.text().content("Death: ").color(TextColor.color(0xDBDBDB)).append(Component.text().content(deaths.toString()).color(TextColor.color(0x85C7F2))).build();
                        sender.sendMessage(deathText);

                        TextComponent kdaText = Component.text().content("KDA: ").color(TextColor.color(0xDBDBDB)).append(Component.text().content(kda.toString()).color(TextColor.color(0x85C7F2))).build();
                        sender.sendMessage(kdaText);


                    }   else {

                        TextComponent error = Component.text().content("could not find player").color(TextColor.color(NamedTextColor.RED)).build();
                        sender.sendMessage(error);
                    }

                }   else {
                    //unable to get uuid so tell player
                    TextComponent error = Component.text().content("could not find player").color(TextColor.color(NamedTextColor.RED)).build();
                    sender.sendMessage(error);

                }




            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
