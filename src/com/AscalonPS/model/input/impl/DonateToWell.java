package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.WellOfGoodwill;
import com.AscalonPS.world.entity.impl.player.Player;

public class DonateToWell extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        WellOfGoodwill.donate(player, amount);
    }

}
