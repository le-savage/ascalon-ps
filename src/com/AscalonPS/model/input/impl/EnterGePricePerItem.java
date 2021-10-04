package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.grandexchange.GrandExchange;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterGePricePerItem extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        GrandExchange.setPricePerItem(player, amount);
    }

}
