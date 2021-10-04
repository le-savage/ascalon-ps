package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.container.impl.Bank.BankSearchAttributes;
import com.AscalonPS.model.input.Input;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterSyntaxToBankSearchFor extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        boolean searchingBank = player.isBanking() && player.getBankSearchingAttribtues().isSearchingBank();
        if (searchingBank)
            BankSearchAttributes.beginSearch(player, syntax);
    }
}
