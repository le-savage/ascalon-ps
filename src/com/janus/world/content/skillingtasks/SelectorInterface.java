package com.janus.world.content.skillingtasks;

import com.janus.model.Skill;
import com.janus.util.Misc;
import com.janus.world.entity.impl.player.Player;
import lombok.AllArgsConstructor;

public class SelectorInterface {

    public static int confirmButton = -16451;
    public static int difficultyButton = -16454;

    public static boolean openSelector(Player player) {
        String taskDifficulty = player.getTaskDifficulty();
        player.getPacketSender().sendInterface(49000); // Open the interface
        player.getPacketSender().sendString(49089, "Difficulty: " + Misc.formatText(taskDifficulty)); // Fill the difficulty box
        player.setUsableObject(new Object[1]).setUsableObject(0, "skill"); // Create the 'Temporary' value used to store skill choice
        System.out.println("Opened Selector. Usable object is " + player.getUsableObject()[0].toString());
        return true;
    }

    public static boolean handleButton(Player player, int button) {

        Interface_Buttons interfaceButton = Interface_Buttons.forButton(button);

        if (interfaceButton == null)
            return false;

        Skill skill = Skill.forName(interfaceButton.toString());
        System.out.println("Skill chosen: " + skill);

        player.setUsableObject(0, skill);
        System.out.println("We just set usable object 0 to " + player.getUsableObject()[0].toString());

        player.getPacketSender().sendString(49006, Misc.formatText(interfaceButton.toString().toLowerCase()));

        return false;
    }

    public static void changeDifficulty(Player player) {
        String currentDifficulty = player.getTaskDifficulty();
        /** Difficulties;
         *  Easy,
         *  Medium,
         *  Hard,
         *  Expert,
         *  Elite
         */
        if (currentDifficulty.equalsIgnoreCase("easy"))
            player.setTaskDifficulty("medium");
        if (currentDifficulty.equalsIgnoreCase("medium"))
            player.setTaskDifficulty("hard");
        if (currentDifficulty.equalsIgnoreCase("hard"))
            player.setTaskDifficulty("expert");
        if (currentDifficulty.equalsIgnoreCase("expert"))
            player.setTaskDifficulty("elite");
        if (currentDifficulty.equalsIgnoreCase("elite"))
            player.setTaskDifficulty("easy");

        String newDifficulty = player.getTaskDifficulty();

        player.getPacketSender().sendString(49089, "Difficulty: " + Misc.formatText(newDifficulty));
    }

    public static void handleConfirm(Player player) {

        if (player.getUsableObject()[0] != null)
            SkillingTasks.assignTask(player, (Skill) player.getUsableObject()[0]);
    }

    @AllArgsConstructor
    enum Interface_Buttons {

        ATTACK(-16529),
        MAGIC(-16526),
        MINING(-16523),
        WOODCUTTING(-16520),
        AGILITY(-16517),
        FLETCHING(-16514),
        THIEVING(-16511),
        STRENGTH(-16508),
        RANGED(-16505),
        SMITHING(-16502),
        FIREMAKING(-16499),
        HERBLORE(-16496),
        SLAYER(-16493),
        CONSTRUCTION(-16490),
        DEFENCE(-16487),
        PRAYER(-16484),
        FISHING(-16481),
        CRAFTING(-16478),
        FARMING(-16475),
        HUNTER(-16472),
        SUMMONING(-16469),
        CONSTITUTION(-16466),
        DUNGEONEERING(-16463),
        COOKING(-16460),
        RUNECRAFTING(-16457);

        private int button;

        public static Interface_Buttons forButton(int button) {
            for (Interface_Buttons skill : Interface_Buttons.values()) {
                if (skill != null && skill.button == button) {
                    return skill;
                }
            }
            return null;
        }
    }
}