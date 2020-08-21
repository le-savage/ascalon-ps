package com.janus.world.content.combat.tieredbosses;


import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.Animation;
import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.entity.impl.player.Player;


public class LootCrate {

    public static int[] ZERO = {123, 123, 123};

    public static int[] ONE = {123, 123, 123};

    public static int[] TWO = {123, 123, 123};

    public static int[] THREE = {123, 123, 123};

    public static int[] FOUR =  {123, 123, 123};


    public static int getRandomItem(int[] array) {
        return array[Misc.getRandom(array.length - 1)];
    }


    public static void openChest(Player player) {
        if (!player.getClickDelay().elapsed(1000))
            return;

        TaskManager.submit(new Task(0, player, true) {
            @Override
            public void execute() {
                player.performAnimation(new Animation(6387));
                player.getPacketSender().sendMessage("Looting Chest...");
                giveReward(player);
                this.stop();
            }
        });
    }

    public static void giveReward(Player player) {

        switch (player.getKbdTier()) {
            case 1://First reward
                int zeroLoot = getRandomItem(ZERO);
                player.getInventory().add(zeroLoot, 1);
                break;
        }

    }
}


