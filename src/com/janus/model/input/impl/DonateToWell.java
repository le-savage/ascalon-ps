package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.WellOfGoodwill;
import com.janus.world.entity.impl.player.Player;

public class DonateToWell extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        WellOfGoodwill.donate(player, amount);
    }

}
