package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.skill.impl.crafting.LeatherMaking;
import com.janus.world.content.skill.impl.crafting.leatherData;
import com.janus.world.entity.impl.player.Player;

public class EnterAmountOfLeatherToCraft extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        for (final leatherData l : leatherData.values()) {
            if (player.getSelectedSkillingItem() == l.getLeather()) {
                LeatherMaking.craftLeather(player, l, amount);
                break;
            }
        }
    }
}
