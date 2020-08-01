package com.janus.model.input.impl;

import com.janus.model.input.Input;
import com.janus.world.entity.impl.player.Player;

public class PosInput extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        player.getPacketSender().sendClientRightClickRemoval();
        player.getPlayerOwnedShopManager().updateFilter(syntax);

    }
}
