package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.util.Misc;
import com.janus.world.content.skill.impl.smithing.ItemMelting;
import com.janus.world.entity.impl.player.Player;

public class EnterAmountToWithdraw extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        player.getPacketSender().sendString(22046, ""+ Misc.quantity(amount));
        player.setAmountToWithdraw(amount);
    }
}
