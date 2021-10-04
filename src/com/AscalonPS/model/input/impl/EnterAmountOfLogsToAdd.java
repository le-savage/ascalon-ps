package com.AscalonPS.model.input.impl;

import com.AscalonPS.model.input.EnterAmount;
import com.AscalonPS.world.content.skill.impl.firemaking.Firemaking;
import com.AscalonPS.world.content.skill.impl.firemaking.Logdata;
import com.AscalonPS.world.content.skill.impl.firemaking.Logdata.logData;
import com.AscalonPS.world.entity.impl.player.Player;

public class EnterAmountOfLogsToAdd extends EnterAmount {


    @Override
    public void handleAmount(Player player, int amount) {
        Firemaking.lightFire(player, -1, true, amount);
        if (player.getInteractingObject() != null)
            player.setPositionToFace(player.getInteractingObject().getPosition());
    }

    public static void openInterface(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        player.getSkillManager().stopSkilling();
        final logData lData = Logdata.getLogData(player, -1);
        if (lData == null) {
            player.getPacketSender().sendMessage("You do not have any logs to add to this fire.");
            return;
        }
        player.setInputHandling(new EnterAmountOfLogsToAdd());
        player.getPacketSender().sendEnterAmountPrompt("How many logs would you like to add to the fire?");
    }

}
