package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.skill.impl.smithing.ItemMelting;
import com.janus.world.entity.impl.player.Player;

public class EnterAmountToMelt extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        if (player.getSelectedSkillingItem() > 0)
            ItemMelting.melt(player, player.getSelectedSkillingItem(), amount);
    }

}
