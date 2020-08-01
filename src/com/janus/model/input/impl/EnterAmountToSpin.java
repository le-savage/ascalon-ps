package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.skill.impl.crafting.Flax;
import com.janus.world.entity.impl.player.Player;

public class EnterAmountToSpin extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        Flax.spinFlax(player, amount);
    }

}
