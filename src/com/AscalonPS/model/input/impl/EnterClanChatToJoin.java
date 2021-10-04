package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.Input;
import com.AscalonPS.world.content.clan.ClanChatManager;
import com.AscalonPS.world.entity.impl.player.Player;

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
