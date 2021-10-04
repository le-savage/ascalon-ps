package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.crafting.Gems;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountOfGemsToCut extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getSelectedSkillingItem() > 0)
            Gems.cutGem(player, amount, player.getSelectedSkillingItem());
    }

}
