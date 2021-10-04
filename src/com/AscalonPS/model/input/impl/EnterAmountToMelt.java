package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.smithing.ItemMelting;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountToMelt extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getSelectedSkillingItem() > 0)
            ItemMelting.melt(player, player.getSelectedSkillingItem(), amount);
    }

}
