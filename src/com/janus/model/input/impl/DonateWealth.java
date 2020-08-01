package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.WellOfWealth;
import com.janus.world.entity.impl.player.Player;

public class DonateWealth extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        WellOfWealth.donate(player, amount);
    }

}
