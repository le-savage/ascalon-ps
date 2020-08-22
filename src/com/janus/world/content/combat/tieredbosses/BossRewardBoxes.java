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
    public static final int[] ONE = {13864, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 6830, 17291, 16359, 16293, 10336, 6831, 10374, 10370, 10368, 10390, 10386, 10384, 10388, 10372, 10362, 7803};
    public static final int[] TWO = {1038, 1040, 1046, 1042, 1048, 1044, 1050, 7449, 5608, 13666, 9946, 9945, 9944, 10732, 9634, 9636, 9638, 11789, 15422, 15423, 15425, 981, 9920, 10507, 19314, 9472};
    public static final int[] THREE = {18891, 18892, 18893, 18894, 18898, 18900, 18901, 14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015};
    public static final int[] FOUR = {14016, 10350, 10348, 10346, 10352, 10342, 10338, 10340, 10334, 10330, 10332, 20998, 16711, 17259, 16689, 17361, 6833, 18899};

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
        int tierOne = Misc.random(1000000, 5000000);//1 - 5m
        int tierTwo = Misc.random(5000000, 10000000);//5 - 10m
        int tierThree = Misc.random(10000000, 15000000);//10 - 15m
        int tierFour = Misc.random(20000000, 30000000);//20 - 30m
        int tierFive = Misc.random(35000000, 100000000);//35 - 100m
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

    public static void openBossRewardBox(Player player) {
        long pouch = player.getMoneyInPouch();
        int random = Misc.random(0, 100);
        player.forceChat("I need 60+ for a prize.. I rolled " + random + "!");

        if (random <= 59) {
            coinReward(player);
        }

        if (random >= 60) {
            switch (player.getKbdTier()) {
                case 1:
                    player.setMoneyInPouch(pouch - zeroCost);
                    player.getInventory().add(ZERO[Misc.getRandom(ZERO.length - 1)], 1);
                    break;

                case 2:
                    player.setMoneyInPouch(pouch - oneCost);
                    player.getInventory().add(ONE[Misc.getRandom(ONE.length - 1)], 1);
                    break;

                case 3:
                    player.setMoneyInPouch(pouch - twoCost);
                    player.getInventory().add(TWO[Misc.getRandom(TWO.length - 1)], 1);
                    break;

                case 4:
                    player.setMoneyInPouch(pouch - threeCost);
                    player.getInventory().add(THREE[Misc.getRandom(THREE.length - 1)], 1);
                    break;
                case 5:
                    player.setMoneyInPouch(pouch - fourCost);
                    player.getInventory().add(FOUR[Misc.getRandom(FOUR.length - 1)], 1);
                    player.setKbdTier(0);
                    player.getPacketSender().sendMessage("Congratulations! We've reset your progress so you can restart!");
                    break;
            }
            removeBossRewardBox(player);
            player.getPacketSender().sendInterfaceRemoval();
        }
    }
}