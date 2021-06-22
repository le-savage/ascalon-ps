package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Locations;
import com.janus.model.container.impl.Inventory;
import com.janus.util.Misc;
import com.janus.world.content.dialogue.DialogueManager;
import com.janus.world.entity.impl.player.Player;

public class BossRewardBoxes {

    public static final int[] ZERO = {15426, 13887, 13893, 13899, 13905, 13896, 13884, 13890, 13902, 13861, 13858, 13864, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759};
    public static final int[] ONE = {15426, 13887, 13893, 13899, 13905, 13896, 13884, 13890, 13902, 13861, 13858, 13864, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759, 13864, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 6830, 17291, 16359, 16293, 10336, 6831, 10374, 10370, 10368, 10390, 10386, 10384, 10388, 10372, 10362, 7803};
    public static final int[] TWO = {13864, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 6830, 17291, 16359, 16293, 10336, 6831, 10374, 10370, 10368, 10390, 10386, 10384, 10388, 10372, 10362, 7803, 1038, 1040, 1046, 1042, 1048, 1044, 1050, 7449, 5608, 13666, 9946, 9945, 9944, 10732, 9634, 9636, 9638, 11789, 15422, 15423, 15425, 981, 9920, 10507, 19314, 9472};
    public static final int[] THREE = {10336, 6831, 10374, 10370, 10368, 10390, 10386, 10384, 10388, 10372, 10362, 7803, 1038, 1040, 1046, 1042, 1048, 1044, 1050, 7449, 5608, 13666, 9946, 9945, 9944, 10732, 9634, 9636, 9638, 11789, 15422, 15423, 15425, 981, 9920, 10507, 19314, 9472, 18891, 18892, 18893, 18894, 18898, 18900, 18901, 14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015};
    public static final int[] FOUR = {17291, 16359, 16293, 10336, 6831, 10374, 10370, 10368, 10390, 10386, 10384, 10388, 10372, 10362, 7803, 1038, 1040, 1046, 1042, 1048, 1044, 1050, 7449, 5608, 13666, 9946, 9945, 9944, 10732, 9634, 9636, 9638, 11789, 15422, 15423, 15425, 981, 9920, 10507, 19314, 9472, 18891, 18892, 18893, 18894, 18898, 18900, 18901, 14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016, 10350, 10348, 10346, 10352, 10342, 10338, 10340, 10334, 10330, 10332, 20998, 16711, 17259, 16689, 17361, 6833, 18899};

    public static final int rewardBox = 6759;

    public static int zeroCost = 500000000;//500m
    public static int oneCost = 800000000;//800m
    public static int twoCost = 1350000000;//1.35b
    public static int threeCost = 1600000000;//1.6b
    public static int fourCost = 2000000000;//2b

    /** Checks if the player has actually completed a tier and earned a reward **/

    public static boolean hasEarnedReward(Player player) {
        return player.getLocation() != Locations.Location.BOSS_TIER_LOCATION && player.getRegionInstance() == null && player.isShouldGiveBossReward();
    }

    /** Checks the bank and inventory for existing reward boxes **/

    public static boolean hasExistingBox(Player player) {

        for (int i = 0; i < player.getBanks().length; i++) {
            if (player.getBank(i).contains(rewardBox)) {
                return true;
            }
        }
        if (player.getInventory().contains(rewardBox)) {
            return true;
        }

        return false;
    }

    /** Adds the boss reward to the players inventory **/

    public static void addBossRewardBox(Player player) {
        if (!hasExistingBox(player) && player.isShouldGiveBossReward()) {
            player.getInventory().add(rewardBox, 1);
            player.setShouldGiveBossReward(false);
        } else {
            player.getPacketSender().sendMessage("You have not earned a reward yet.");
        }
    }

    /** Deletes the boss reward from the players inventory **/

    public static void removeBossRewardBox(Player player) {
        System.out.println("removeReward method called");
        if (player.getInventory().contains(rewardBox)) {
            player.getInventory().delete(rewardBox, 1);
            System.out.println("Reward removed for " + player.getUsername().toUpperCase());
        }
    }

    /** Sets the price and tells the player depending on the current tier **/

    public static void setCostToOpen(Player player) {
        long cost = 0;
        switch (player.getCurrentBossWave()) {
            case 1:
                cost = zeroCost;
                break;
            case 2:
                cost = oneCost;
                break;
            case 3:
                cost = twoCost;
                break;
            case 4:
                cost = threeCost;
                break;
            case 5:
                cost = fourCost;
                break;
        }
        DialogueManager.start(player, 179);
        player.getPacketSender().sendString(2461, "Pay " + Misc.insertCommasToNumber(Long.toString(cost)) + " GP to unlock the reward");
        player.getPacketSender().sendString(2462, "No thanks - Skip to the next tier!");
        player.setDialogueActionId(85);

    }

    /**
     * Checks to see if the player can afford to open the chest
     **/

    public static boolean canAffordToOpen(Player player) {
        int cash = player.getInventory().getAmount(995);
        switch (player.getCurrentBossWave()) {
            case 1:
                if (cash >= zeroCost)
                    return true;
                break;
            case 2:
                if (cash >= oneCost)
                    return true;
                break;
            case 3:
                if (cash >= twoCost)
                    return true;
                break;
            case 4:
                if (cash >= threeCost)
                    return true;
                break;
            case 5:
                if (cash >= fourCost)
                    return true;
                break;
        }
        return false;
    }

    /**
     * Covers the method triggered when opening the box. This makes the palyer
     * call a random number. IF the player wins, calls the giveReward method.
     * If the player loses, calls the coinReward method.
     *
     * @param player - The user who is opening the box.
     */

    public static void openBossRewardBox(Player player) {
        if (!canAffordToOpen(player)) {
            player.getPacketSender().sendMessage("You can't afford to open this! Ensure the cash is in your inventory!");
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        int random = Misc.random(0, 100); // Gets the random number between 0-100
        chargePlayer(player);
        String playerRights = player.getRights().toString();
        switch (player.getRights()) { // The roll needed to win is reduced depending on rank.
            case PLAYER:
                player.forceChat("I need 60+ for a prize as a " + playerRights + ".. I rolled " + random + "!");
                if (random <= 59) {
                    playerLosesRoll(player);
                } else {
                    playerWinsRoll(player);
                }
                break;
            case SUPER_DONATOR:
                player.forceChat("I need 40+ for a prize as a " + playerRights + ".. I rolled " + random + "!");
                if (random <= 39) {
                    playerLosesRoll(player);
                } else {
                    playerWinsRoll(player);
                }
                break;
            case EXTREME_DONATOR:
                player.forceChat("I need 30+ for a prize as a " + playerRights + ".. I rolled " + random + "!");
                if (random <= 29) {
                    playerLosesRoll(player);
                } else {
                    playerWinsRoll(player);
                }
                break;
            case LEGENDARY_DONATOR:
                player.forceChat("I need 20+ for a prize as a " + playerRights + ".. I rolled " + random + "!");
                if (random <= 19) {
                    playerLosesRoll(player);
                } else {
                    playerWinsRoll(player);
                }
                break;
            case UBER_DONATOR:
                player.forceChat("I need 10+ for a prize as an " + playerRights + ".. I rolled " + random + "!");
                if (random <= 9) {
                    playerLosesRoll(player);
                } else {
                    playerWinsRoll(player);
                }
                break;
            case DONATOR:
            default:
                player.forceChat("I need 50+ for a prize as a " + playerRights + ".. I rolled " + random + "!");
                if (random <= 49) {
                    playerLosesRoll(player);
                } else {
                    playerWinsRoll(player);
                }
                break;
        }
    }

    /**
     * Charges the player by deducting cash from the inventory
     **/

    public static void chargePlayer(Player player) {
        if (!canAffordToOpen(player)) {
            player.getPacketSender().sendMessage("You can't afford to open this! Ensure the cash is in your inventory!");
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        Inventory inventory = player.getInventory();
        switch (player.getCurrentBossWave()) {
            case 1:
                inventory.delete(995, zeroCost);
                break;
            case 2:
                inventory.delete(995, oneCost);
                break;
            case 3:
                inventory.delete(995, twoCost);
                break;
            case 4:
                inventory.delete(995, threeCost);
                break;
            case 5:
                inventory.delete(995, fourCost);
                break;
        }
        removeBossRewardBox(player);
        player.getPacketSender().sendInterfaceRemoval();
    }

    /**
     * Gives the player a reward after paying to open the chest in the inventory
     **/

    public static void playerWinsRoll(Player player) {
        switch (player.getCurrentBossWave()) {
            case 1:
                player.getInventory().add(ZERO[Misc.getRandom(ZERO.length - 1)], 1);
                break;

            case 2:
                player.getInventory().add(ONE[Misc.getRandom(ONE.length - 1)], 1);
                break;

            case 3:
                player.getInventory().add(TWO[Misc.getRandom(TWO.length - 1)], 1);
                break;

            case 4:
                player.getInventory().add(THREE[Misc.getRandom(THREE.length - 1)], 1);
                break;
            case 5:
                player.getInventory().add(FOUR[Misc.getRandom(FOUR.length - 1)], 1);
                player.setCurrentBossWave(0);
                player.getPacketSender().sendMessage("Congratulations! We've reset your progress so you can do it all again ;)");
                break;
        }
        removeBossRewardBox(player);
        player.getPacketSender().sendInterfaceRemoval();
    }

    /**
     * Method responsible for awarding players who lose their roll
     **/

    public static void playerLosesRoll(Player player) {
        int tierOne = Misc.random(10000000, 500000000);//10 - 500m
        int tierTwo = Misc.random(200000000, 850000000);//200 - 850m
        int tierThree = Misc.random(300000000, 1000000000);//300 - 1b
        int tierFour = Misc.random(400000000, 1200000000);//400 - 1.2b
        int tierFive = Misc.random(600000000, 2000000000);//600 - 2b
        Inventory invent = player.getInventory();
        switch (player.getCurrentBossWave()) {
            case 1:
                invent.add(995, tierOne);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierOne) + "!");
                break;
            case 2:
                invent.add(995, tierTwo);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierTwo) + "!");
                break;
            case 3:
                invent.add(995, tierThree);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierThree) + "!");
                break;
            case 4:
                invent.add(995, tierFour);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierFour) + "!");
                break;
            case 5:
                invent.add(995, tierFive);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierFive) + "!");
                break;
        }
    }
}