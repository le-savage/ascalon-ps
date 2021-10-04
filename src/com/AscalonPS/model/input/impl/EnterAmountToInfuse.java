package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.summoning.PouchMaking;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountToInfuse extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getInterfaceId() != 63471) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        PouchMaking.infusePouches(player, amount);
    }

}
