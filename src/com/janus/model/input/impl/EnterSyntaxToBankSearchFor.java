package com.janus.model.input.impl;

import com.janus.model.container.impl.Bank.BankSearchAttributes;
import com.janus.model.input.Input;
import com.janus.world.entity.impl.player.Player;

public class EnterSyntaxToBankSearchFor extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        boolean searchingBank = player.isBanking() && player.getBankSearchingAttribtues().isSearchingBank();
        if (searchingBank)
            BankSearchAttributes.beginSearch(player, syntax);
    }
}
