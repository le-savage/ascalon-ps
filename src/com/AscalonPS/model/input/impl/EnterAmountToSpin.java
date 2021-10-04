package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.crafting.Flax;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountToSpin extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        Flax.spinFlax(player, amount);
    }

}
