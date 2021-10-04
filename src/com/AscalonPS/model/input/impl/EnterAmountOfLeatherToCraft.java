package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.crafting.LeatherMaking;
import com.AscalonPS.world.content.skill.impl.crafting.leatherData;
import com.AscalonPS.world.entity.impl.player.Player;

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
