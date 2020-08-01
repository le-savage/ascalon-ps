package com.janus.util;

import com.janus.GameServer;
import com.janus.world.World;
import com.janus.world.content.WellOfGoodwill;
import com.janus.world.content.WellOfWealth;
import com.janus.world.content.clan.ClanChatManager;
import com.janus.world.content.grandexchange.GrandExchangeOffers;
import com.janus.world.entity.impl.player.Player;
import com.janus.world.entity.impl.player.PlayerHandler;

import java.util.logging.Logger;

public class ShutdownHook extends Thread {

    /**
     * The ShutdownHook logger to print out information.
     */
    private static final Logger logger = Logger.getLogger(ShutdownHook.class.getName());

    @Override
    public void run() {
        logger.info("The shutdown hook is processing all required actions...");
        World.savePlayers();
        GameServer.setUpdating(true);
        for (Player player : World.getPlayers()) {
            if (player != null) {
                //	World.deregister(player);
                PlayerHandler.handleLogout(player);
            }
        }
        WellOfGoodwill.save();
        WellOfWealth.save();
        GrandExchangeOffers.save();
        ClanChatManager.save();
        logger.info("The shudown hook actions have been completed, shutting the server down...");
    }
}
