package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.smithing.Smelting;
import com.AscalonPS.world.content.skill.impl.smithing.SmithingData;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountOfBarsToSmelt extends EnterAmount {

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

    private int bar;

    public int getBar() {
        return bar;
    }

}
