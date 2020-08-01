package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.skill.impl.summoning.PouchMaking;
import com.janus.world.entity.impl.player.Player;

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
