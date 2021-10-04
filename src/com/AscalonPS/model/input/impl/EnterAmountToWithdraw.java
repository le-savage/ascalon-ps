package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountToWithdraw extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        player.getPacketSender().sendString(22046, ""+ Misc.quantity(amount));
        player.setAmountToWithdraw(amount);
    }
}
