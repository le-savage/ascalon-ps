package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Locations;
import com.janus.model.PlayerRights;
import com.janus.model.container.impl.Bank;
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

    public static long zeroCost = 500000000;//500m
    public static long oneCost = 800000000;//800m
    public static long twoCost = 1350000000;//1.35b
    public static long threeCost = 1600000000;//1.6b
    public static long fourCost = 2000000000;//2b


    public static boolean canOpenBossRewardBox(Player player) {
        return player.getLocation() != Locations.Location.BOSS_TIER_LOCATION && player.getRegionInstance() == null;
    }

    public static boolean hasBossRewardBox(Player player) {
        for (Bank bank : player.getBanks()) {
            if (bank == null) {
                continue;
            }
            return bank.contains(rewardBox) || player.getInventory().contains(rewardBox);
        }
        return false;
    }

    public static void addBossRewardBox(Player player) {
        if (!hasBossRewardBox(player)) {
            player.getInventory().add(rewardBox, 1);
        }
    }

    public static void removeBossRewardBox(Player player) {
        System.out.println("removeReward method called");
        if (player.getInventory().contains(rewardBox)) {
            player.getInventory().delete(rewardBox, 1, true);
            System.out.println("Reward removed for " + player.getUsername().toUpperCase());
        }
    }


    public static void buyBossBoxReward(Player player) {
        long cost = 0;
        switch (player.getKbdTier()) {
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
        long pouch = player.getMoneyInPouch();
        if (pouch < cost && player.getRights() != PlayerRights.OWNER) {
            player.getPacketSender().sendInterfaceRemoval();
            player.getPacketSender().sendMessage("You need " + Misc.setupMoney(cost) + " in your pouch to open this box!");
            return;
        }
        DialogueManager.start(player, 179);
        player.getPacketSender().sendString(2461, "Pay " + Misc.insertCommasToNumber(Long.toString(cost)) + " GP to unlock the reward");
        player.getPacketSender().sendString(2462, "No thanks - that's way to expensive!");
        player.setDialogueActionId(85);

    }

    public static void coinReward(Player player) {
        int tierOne = Misc.random(10000000, 500000000);//10 - 500m
        int tierTwo = Misc.random(200000000, 850000000);//200 - 850m
        int tierThree = Misc.random(300000000, 1000000000);//300 - 1b
        int tierFour = Misc.random(400000000, 1200000000);//400 - 1.2b
        int tierFive = Misc.random(600000000, 2000000000);//600 - 2b
        Inventory invent = player.getInventory();
        switch (player.getKbdTier()) {

            case 1:
                invent.add(995, tierOne);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierOne) + "!");
                break;
            case 2:
                invent.add(995, tierTwo);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierOne) + "!");
                break;
            case 3:
                invent.add(995, tierThree);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierOne) + "!");
                break;
            case 4:
                invent.add(995, tierFour);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierOne) + "!");
                break;
            case 5:
                invent.add(995, tierFive);
                player.getPacketSender().sendMessage("Better luck next time! Enjoy your courtesy prize of " + Misc.setupMoney(tierOne) + "!");
                break;

        }
    }

    public static void giveReward(Player player) {
        switch (player.getKbdTier()) {
            case 1:
                player.setMoneyInPouch(player.getMoneyInPouch() - zeroCost);
                player.getInventory().add(ZERO[Misc.getRandom(ZERO.length - 1)], 1);
                break;

            case 2:
                player.setMoneyInPouch(player.getMoneyInPouch() - oneCost);
                player.getInventory().add(ONE[Misc.getRandom(ONE.length - 1)], 1);
                break;

            case 3:
                player.setMoneyInPouch(player.getMoneyInPouch() - twoCost);
                player.getInventory().add(TWO[Misc.getRandom(TWO.length - 1)], 1);
                break;

            case 4:
                player.setMoneyInPouch(player.getMoneyInPouch() - threeCost);
                player.getInventory().add(THREE[Misc.getRandom(THREE.length - 1)], 1);
                break;
            case 5:
                player.getInventory().add(FOUR[Misc.getRandom(FOUR.length - 1)], 1);
                player.setKbdTier(0);
                player.getPacketSender().sendMessage("Congratulations! We've reset your progress so you can restart!");
                break;
        }
        removeBossRewardBox(player);
        player.getPacketSender().sendInterfaceRemoval();
    }

    public static boolean canAfford(Player player) {
        switch (player.getKbdTier()) {
            case 1:
                if (player.getMoneyInPouch() >= zeroCost)
                    return true;
                break;
            case 2:
                if (player.getMoneyInPouch() >= oneCost)
                    return true;
                break;
            case 3:
                if (player.getMoneyInPouch() >= twoCost)
                    return true;
                break;
            case 4:
                if (player.getMoneyInPouch() >= threeCost)
                    return true;
                break;
            case 5:
                if (player.getMoneyInPouch() >= fourCost)
                    return true;
                break;
        }
        return false;
    }

    public static void chargePlayer(Player player) {
        switch (player.getKbdTier()) {
            case 1:
                player.setMoneyInPouch(player.getMoneyInPouch() - zeroCost);
                break;
            case 2:
                player.setMoneyInPouch(player.getMoneyInPouch() - oneCost);
                break;
            case 3:
                player.setMoneyInPouch(player.getMoneyInPouch() - twoCost);
                break;
            case 4:
                player.setMoneyInPouch(player.getMoneyInPouch() - threeCost);
                break;
            case 5:
                player.setMoneyInPouch(player.getMoneyInPouch() - fourCost);
                break;
        }
        removeBossRewardBox(player);
        player.getPacketSender().sendInterfaceRemoval();
        player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
    }

    public static void openBossRewardBox(Player player) {
        if (!canAfford(player)) {
            player.getPacketSender().sendMessage("You can't afford to open this!");
            return;
        }
            int random = Misc.random(0, 100);
            chargePlayer(player);
            switch (player.getRights()) {
                case PLAYER:
                    player.forceChat("I need 60+ for a prize as a " + player.getRights().toString() + ".. I rolled " + random + "!");
                    if (random <= 59) {
                        coinReward(player);
                    } else {
                        giveReward(player);
                    }
                    break;
                case DONATOR:
                    player.forceChat("I need 50+ for a prize as a " + player.getRights().toString() + ".. I rolled " + random + "!");
                    if (random <= 49) {
                        coinReward(player);
                    } else {
                        giveReward(player);
                    }
                    break;
                case SUPER_DONATOR:
                    player.forceChat("I need 40+ for a prize as a " + player.getRights().toString() + ".. I rolled " + random + "!");
                    if (random <= 39) {
                        coinReward(player);
                    } else {
                        giveReward(player);
                    }
                    break;
                case EXTREME_DONATOR:
                    player.forceChat("I need 30+ for a prize as a " + player.getRights().toString() + ".. I rolled " + random + "!");
                    if (random <= 29) {
                        coinReward(player);
                    } else {
                        giveReward(player);
                    }
                    break;
                case LEGENDARY_DONATOR:
                    player.forceChat("I need 20+ for a prize as a " + player.getRights().toString() + ".. I rolled " + random + "!");
                    if (random <= 19) {
                        coinReward(player);
                    } else {
                        giveReward(player);
                    }
                    break;
                case UBER_DONATOR:
                    player.forceChat("I need 10+ for a prize as an " + player.getRights().toString() + ".. I rolled " + random + "!");
                    if (random <= 9) {
                        coinReward(player);
                    } else {
                        giveReward(player);
                    }
                    break;
                default:
                    player.forceChat("I need 50+ for a prize as a " + player.getRights().toString() + ".. I rolled " + random + "!");
                    if (random <= 49) {
                        coinReward(player);
                    } else {
                        giveReward(player);
                    }
                    break;
        }


    }
}