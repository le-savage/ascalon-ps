package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.fletching.Fletching;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountOfBoltsToMake extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        Fletching.makeBolt(player, amount);
    }

}
