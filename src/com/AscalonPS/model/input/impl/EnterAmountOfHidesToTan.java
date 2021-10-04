package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.crafting.Tanning;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountOfHidesToTan extends EnterAmount {

    private int button;

    public EnterAmountOfHidesToTan(int button) {
        this.button = button;
    }

    @Override
    public void handleAmount(Player player, int amount) {
        Tanning.tanHide(player, button, amount);
    }

}
