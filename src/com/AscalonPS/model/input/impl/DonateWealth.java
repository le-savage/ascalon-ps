package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.WellOfWealth;
import com.AscalonPS.world.entity.impl.player.Player;

public class DonateWealth extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        WellOfWealth.donate(player, amount);
    }

}
