package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.grandexchange.GrandExchange;
import com.janus.world.entity.impl.player.Player;

public class EnterGeQuantity extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        GrandExchange.setQuantity(player, amount);
    }

}
