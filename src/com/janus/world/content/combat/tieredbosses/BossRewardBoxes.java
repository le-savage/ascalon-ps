package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Locations;
import com.janus.util.Misc;
import com.janus.world.entity.impl.player.Player;

public class BossRewardBoxes {

    public static final int[] FIRST = {1543, 14484, 4083, 20555, 15019, 15220};
    public static final int[] SECOND = {1543, 14484, 4083, 20555, 15019, 15220};
    public static final int[] THIRD = {1543, 14484, 4083, 20555, 15019, 15220};
    public static final int[] FOURTH = {1543, 14484, 4083, 20555, 15019, 15220};
    public static final int[] FIFTH = {1543, 14484, 4083, 20555, 15019, 15220};

    public static final int rewardBox = 6759;

    public static int firstCost = 500000000;//500m
    public static int secondCost = 800000000;//800m
    public static int thirdCost = 1350000000;//1.35b
    public static int fourthCost = 1600000000;//1.6b
    public static int fifthCost = 2000000000;//2b

    public static boolean canOpenBossReward(Player player){
        if (player.getLocation() == Locations.Location.BOSS_TIER_LOCATION || player.getRegionInstance() != null){
            return false;
        }
        return true;
    }

    public static void removeBossReward(Player player) {
        System.out.println("removeReward method called");
        if (player.getInventory().contains(rewardBox)) {
            player.getInventory().delete(rewardBox, 1, true);
            System.out.println("Reward removed for "+player.getUsername().toUpperCase());
        }
    }

    public static void addBossReward(Player player) {
        System.out.println("Adding box for player");
        player.getInventory().add(rewardBox, 1);
    }

    /*public static void showConfirmation(Player player) {
        DialogueManager.start(player, 80);

        switch (player.getKbdTier()) {
            case 0:
                firstReward(player);
                break;
            case 1:
                secondReward(player);
            case 2:
                thirdReward(player);
                break;
            case 3:
                fourthReward(player);
                break;
            case 4:
                fifthReward(player);
                break;
        }
    }*/

    public static void openBossReward(Player player) {
        System.out.println("BossRewardBoxes reward called");

        if (!canOpenBossReward(player) || !player.shouldGiveBossReward()) {
            System.out.println(player.getUsername().toUpperCase()+ " was not eligible for a reward");
            return;
        }

        int pouch = player.getMoneyInPouchAsInt();

        switch (player.getKbdTier()) {
            case 0:
                System.out.println("first reward for "+player.getUsername().toUpperCase());
                firstReward(player);
                break;
            case 1:
                secondReward(player);
            case 2:
                thirdReward(player);
                break;
            case 3:
                fourthReward(player);
                break;
            case 4:
                fifthReward(player);
                break;
        }
        player.setShouldGiveBossReward(false);
        System.out.println("Player set to should not give reward");
    }

    public static void firstReward(Player player) {
        player.getInventory().add(FIRST[Misc.getRandom(FIRST.length - 1)], 1);
        removeBossReward(player);
    }


    public static void secondReward(Player player) {
        player.getInventory().add(SECOND[Misc.getRandom(SECOND.length - 1)], 1);
        removeBossReward(player);
    }

    public static void thirdReward(Player player) {
        player.getInventory().add(THIRD[Misc.getRandom(THIRD.length - 1)], 1);
        removeBossReward(player);
    }


    public static void fourthReward(Player player) {
        player.getInventory().add(FOURTH[Misc.getRandom(FOURTH.length - 1)], 1);
        removeBossReward(player);
    }


    public static void fifthReward(Player player) {
        player.getInventory().add(FIFTH[Misc.getRandom(FIFTH.length - 1)], 1);
        removeBossReward(player);
    }


}