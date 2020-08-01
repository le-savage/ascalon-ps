package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.skill.impl.fletching.Fletching;
import com.janus.world.entity.impl.player.Player;

public class EnterAmountOfBoltsToMake extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        Fletching.makeBolt(player, amount);
    }

}
