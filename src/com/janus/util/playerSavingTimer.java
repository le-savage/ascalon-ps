package com.janus.util;

import com.janus.GameSettings;
import com.janus.world.World;
import com.janus.world.content.PlayerPunishment;
import com.janus.world.content.WellOfGoodwill;
import com.janus.world.content.clan.ClanChatManager;

public class playerSavingTimer {


    public static long massSaveTimer = System.currentTimeMillis();

    public static void massSaving() {
        if (System.currentTimeMillis() - massSaveTimer > GameSettings.charcterSavingInterval) {
            World.savePlayers();
            WellOfGoodwill.save();
            ClanChatManager.save();
            //System.out.println("Players saved...");
            //DiscordMessenger.sendErrorLog("SAVED: PLAYERS, FOUNTAIN, CLANCHAT + DAILY REWARDS RELOADED");
            massSaveTimer = System.currentTimeMillis();
            PlayerPunishment.reloadDailyRewards();

        }
    }

}
