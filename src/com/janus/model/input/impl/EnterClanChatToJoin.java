package com.janus.model.input.impl;

import com.janus.model.input.Input;
import com.janus.world.content.clan.ClanChatManager;
import com.janus.world.entity.impl.player.Player;

public class EnterClanChatToJoin extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        if (syntax.length() <= 1) {
            player.getPacketSender().sendMessage("Invalid syntax entered.");
            return;
        }
        ClanChatManager.join(player, syntax);
    }
}
