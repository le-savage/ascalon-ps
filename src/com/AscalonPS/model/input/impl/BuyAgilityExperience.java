package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.Difficulty;
import com.AscalonPS.model.Skill;
import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.entity.impl.player.Player;

public class BuyAgilityExperience extends EnterAmount {

    @Override
    public void handleAmount(Player player, int amount) {
        player.getPacketSender().sendInterfaceRemoval();
        int ticketAmount = player.getInventory().getAmount(2996);
        if (ticketAmount == 0) {
            player.getPacketSender().sendMessage("You do not have any tickets.");
            return;
        }
        if (ticketAmount > amount) {
            ticketAmount = amount;
        }

        if (player.getInventory().getAmount(2996) < ticketAmount) {
            return;
        }

        Difficulty difficulty = player.getDifficulty();
        int exp = ticketAmount * 7680;
        switch (difficulty) {
            case Easy:
                exp *= 3;
            case Medium:
            case Default:
                break;
            case Hard:
                exp *= 0.5;
                break;
            case Insane:
                exp *= 0.25;
                break;
            case Zezima:
                exp *= 0.1;
                break;
        }
        player.getInventory().delete(2996, ticketAmount);
        player.getSkillManager().addExperience(Skill.AGILITY, exp);
        player.getPacketSender().sendMessage("You've bought " + exp + " Agility experience for " + ticketAmount + " Agility ticket" + (ticketAmount == 1 ? "" : "s") + ".");
    }

}
