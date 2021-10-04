package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.cooking.Cooking;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountToCook extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getSelectedSkillingItem() > 0)
            Cooking.cook(player, player.getSelectedSkillingItem(), amount);
    }

}
