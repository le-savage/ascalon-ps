package com.AscalonPS.world.content.skill.impl.crafting;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Skill;
import com.AscalonPS.model.input.impl.EnterAmountToSpin;
import com.AscalonPS.world.entity.impl.player.Player;

public class Flax {

    private static final int FLAX_ID = 1779;

    public static void showSpinInterface(Player player) {
        player.getPacketSender().sendInterfaceRemoval();
        player.getSkillManager().stopSkilling();
        if (!player.getInventory().contains(1779)) {
            player.getPacketSender().sendMessage("You do not have any Flax to spin.");
            return;
        }
        player.setInputHandling(new EnterAmountToSpin());
        player.getPacketSender().sendString(2799, "Bowstring").sendInterfaceModel(1746, 1777, 150).sendChatboxInterface(4429);
        player.getPacketSender().sendString(2800, "How many would you like to make?");
    }

    public static void spinFlax(final Player player, final int amount) {
        if (amount <= 0)
            return;
        player.getSkillManager().stopSkilling();
        player.getPacketSender().sendInterfaceRemoval();
        player.setCurrentTask(new Task(2, player, true) {
            int amountSpan = 0;

            @Override
            public void execute() {
                player.getPacketSender().sendRichPresenceState("Spinning Flax!");
                player.getPacketSender().sendSmallImageKey("crafting");
                player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.CRAFTING));
                if (!player.getInventory().contains(FLAX_ID)) {
                    stop();
                    return;
                }
                player.getSkillManager().addExperience(Skill.CRAFTING, 324);
                player.performAnimation(new Animation(896));
                player.getInventory().delete(FLAX_ID, 1);
                player.getInventory().add(1777, 1);
                amountSpan++;
                if (amountSpan >= amount)
                    stop();
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }
}
