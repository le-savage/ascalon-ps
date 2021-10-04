package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.prayer.BonesOnAltar;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountOfBonesToSacrifice extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        BonesOnAltar.offerBones(player, amount);
    }

}
