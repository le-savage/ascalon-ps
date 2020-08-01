package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.skill.impl.prayer.BonesOnAltar;
import com.janus.world.entity.impl.player.Player;

public class EnterAmountOfBonesToSacrifice extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        BonesOnAltar.offerBones(player, amount);
    }

}
