package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.Input;
import com.AscalonPS.world.entity.impl.player.Player;

public class PosInput extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        player.getPacketSender().sendClientRightClickRemoval();
        player.getPlayerOwnedShopManager().updateFilter(syntax);

    }
}
