package com.AscalonPS.util;

import com.AscalonPS.GameSettings;
import com.AscalonPS.world.World;
import com.AscalonPS.world.content.PlayerPunishment;
import com.AscalonPS.world.content.WellOfGoodwill;
import com.AscalonPS.world.content.clan.ClanChatManager;

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
