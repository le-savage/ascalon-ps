package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Locations;
import com.janus.model.PlayerRights;
import com.janus.model.container.impl.Bank;
import com.janus.util.Misc;
import com.janus.world.content.dialogue.DialogueManager;
import com.janus.world.entity.impl.player.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class BossRewardBoxes {

    public static final int[] FIRST = {15426, 13887, 13893, 13899, 13905, 13896, 13884, 13890, 13902, 13861, 13858, 13864, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747, 4749, 4751, 4753, 4755, 4757, 4759};
    public static final int[] SECOND = {13864, 4716, 4718, 4720, 4722, 4724, 4726, 4728, 4730, 4732, 4734, 6830, 17291, 16359, 16293, 10336, 6831, 10374, 10370, 10368, 10390, 10386, 10384, 10388, 10372, 10362, 7803};
    public static final int[] THIRD = {1038, 1040, 1046, 1042, 1048, 1044, 1050, 7449, 5608, 13666, 9946, 9945, 9944, 10732, 9634, 9636, 9638, 11789, 15422, 15423, 15425, 981, 9920, 10507, 19314, 9472};
    public static final int[] FOURTH = {18891, 18892, 18893, 18894, 18898, 18900, 18901, 14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015};
    public static final int[] FIFTH = {14016, 10350, 10348, 10346, 10352, 10342, 10338, 10340, 10334, 10330, 10332, 20998, 16711, 17259, 16689, 17361, 6833, 18899};

    public static final int rewardBox = 6759;

    public static long firstCost = 500000000;//500m
    public static long secondCost = 800000000;//800m
    public static long thirdCost = 1350000000;//1.35b
    public static long fourthCost = 1600000000;//1.6b
    public static long fifthCost = 2000000000;//2b

    public static AtomicInteger cost;


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
        if (player.getRights() != PlayerRights.OWNER) {
            System.out.println("removeReward method called");
            if (player.getInventory().contains(rewardBox)) {
                player.getInventory().delete(rewardBox, 1, true);
                System.out.println("Reward removed for " + player.getUsername().toUpperCase());
            }
        }
    }


    public static void buyBossBoxReward(Player player) {
        long cost = 0;
        switch (player.getKbdTier()) {
            case 1:
                cost = firstCost;
                break;
            case 2:
                cost = secondCost;
                break;
            case 3:
                cost = thirdCost;
                break;
            case 4:
                cost = fourthCost;
                break;
            case 5:
                cost = fifthCost;
                player.setKbdTier(0);
                player.getPacketSender().sendMessage("Congratulations! We've reset your progress so you can restart!");
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

    public static void openBossRewardBox(Player player) {
        long pouch = player.getMoneyInPouch();
        switch (player.getKbdTier()) {
            case 1:
                player.setMoneyInPouch(pouch - firstCost);
                player.getInventory().add(FIRST[Misc.getRandom(FIRST.length - 1)], 1);
                break;

            case 2:
                player.setMoneyInPouch(pouch - secondCost);
                player.getInventory().add(SECOND[Misc.getRandom(SECOND.length - 1)], 1);
                break;

            case 3:
                player.setMoneyInPouch(pouch - thirdCost);
                player.getInventory().add(THIRD[Misc.getRandom(THIRD.length - 1)], 1);
                break;

            case 4:
                player.setMoneyInPouch(pouch - fourthCost);
                player.getInventory().add(FOURTH[Misc.getRandom(FOURTH.length - 1)], 1);
                break;

            case 5:
                player.setMoneyInPouch(pouch - fifthCost);
                player.getInventory().add(FIFTH[Misc.getRandom(FIFTH.length - 1)], 1);
                break;
        }
        player.setShouldGiveBossReward(false);
        removeBossRewardBox(player);
        player.getPacketSender().sendInterfaceRemoval();
    }
}