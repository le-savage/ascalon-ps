package com.janus.model.input.impl;

import com.janus.model.input.EnterAmount;
import com.janus.world.content.skill.impl.smithing.Smelting;
import com.janus.world.content.skill.impl.smithing.SmithingData;
import com.janus.world.entity.impl.player.Player;

public class EnterAmountOfBarsToSmelt extends EnterAmount {

    private int bar;

    public EnterAmountOfBarsToSmelt(int bar) {
        this.bar = bar;
    }

    @Override
    public void handleAmount(Player player, int amount) {
        for (int barId : SmithingData.SMELT_BARS) {
            if (barId == bar) {
                Smelting.smeltBar(player, barId, amount);
                break;
            }
        }
    }

    public int getBar() {
        return bar;
    }

}
