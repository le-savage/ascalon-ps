package com.AscalonPS.world.content;

import com.AscalonPS.model.PlayerRights;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.World;
import com.AscalonPS.world.entity.impl.player.Player;

public class RUBoxes {//Items in mystery boxes

    public static final int[] bestInSlotBox = {20998, 18899, 21054};

    public static void openBestInSlotBox(Player player) {
        if (player.getInventory().getFreeSlots() < 1) {
            player.getPacketSender().sendMessage("You need at least 1 inventory space");
            return;
        }
        World.sendMessage("@red@" + player.getUsername() + " has just opened a best in slot box!");
        //DiscordMessenger.sendDailyLoginLog("["+player.getUsername()+"] Has redeemed a best in slot MBOX! [IP: "+player.getHostAddress()+"]");
        player.getInventory().delete(14664, 1);
        bestInSlotBox(player);
    }

    public static void bestInSlotBox(Player player) {
        player.getInventory().add(bestInSlotBox[Misc.getRandom(bestInSlotBox.length - 1)], 1);
    }


    public static final int[] commonRewards = {1543, 14484, 4083, 20555, 15019, 15220, 15018, 15020, 6199, 14000, 14001, 14002, 14003, 14004, 14005, 14006, 14007, 10836, 10837, 10838, 10839, 18744, 18745, 18746};
    public static final int[] uncommonRewards = {13045, 4453, 13047, 14484, 20555, 19780, 11694, 13239, 12708, 13235, 13738, 13744, 20012, 20010, 20011, 20019, 20020, 20016, 20017, 20018, 20021, 20022};
    public static final int[] rareRewards = {13740, 4450, 4454, 12926, 13051, 17291, 17291, 13045, 4453, 12601, 18895, 18896, 18898, 9813, 14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016, 19311, 19308, 19314};
    public static final int[] extremeRewards = {20998, 18899, 14018, 13742, 12284, 17291, 4450, 4454, 13051, 17291, 13740, 12601, 4453, 19317, 19320, 4452, 4448, 19317, 19320, 19308};
    public static final int[] legendaryRewards = {18891, 18892, 18893, 20998, 14018, 13742, 1038, 1040, 1042, 1044, 1046, 1048, 1419, 5607, 10330, 10332, 10334, 10338, 10340, 10342, 10344, 10346, 10348, 10350, 10352, 21051, 4452, 4448, 21054, 21055, 21056, 21057};
    public static final int[] bossRewards = {11971, 11972, 11978, 11979, 11981, 11982, 11983, 11984, 11985, 11986, 11987, 11988, 11989, 11990, 11991, 11992, 11993, 11994, 11995, 11996, 11997, 12001, 12002, 12003, 12004, 12005, 12006, 12006, 14914, 20079, 20080, 20081, 20082, 20083, 20085, 20087, 20088, 20089, 20090, 20103};

    public static void openCommonBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(6829, 1);
        commonRReward(player);
    }

    public static void openUncommonBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(6830, 1);
        uncommonRReward(player);
        //World.sendMessage("@red@[Unommon-Box]@bla@ "+player.getUsername()+ " has received "+uncommonRReward(Player+"!");
    }

    public static void openRareBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(6831, 1);
        rareRReward(player);
        //World.sendMessage("@red@[Rare-Box]@bla@ "+player.getUsername()+ " has received "+rareRReward(player)+"!");
    }

    public static void openExtremeBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(6832, 1);
        extremeRReward(player);
        //World.sendMessage("@red@[Extreme-Box]@bla@"+player.getUsername()+ "just opened "+r+"" );

    }

    public static void openLegendaryBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(6833, 1);
        legendaryRReward(player);
        legendaryRReward(player);
        //World.sendMessage("@red@[Legendary-Box]@bla@ "+player.getUsername()+ " has received "+legendaryRReward(player)+"!");
    }

    public static void openBossBox(Player player) {
        if (player.getInventory().getFreeSlots() < 3) {
            player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
            return;
        }
        player.getInventory().delete(6183, 1);
        if ((player.getRights().equals(PlayerRights.EXTREME_DONATOR) || (player.getRights().equals(PlayerRights.LEGENDARY_DONATOR)) || (player.getRights().equals(PlayerRights.UBER_DONATOR) || player.getUsername().equalsIgnoreCase("Zodiac")))) {
            bossPetReward(player);
        }
        bossPetReward(player);
        player.getPacketSender().sendMessage("Did you know Extreme Donors+ get two pets from this box!");
    }


    public static void  commonRReward(Player player) {
        player.getInventory().add(commonRewards[Misc.getRandom(commonRewards.length - 1)], 1);
        //player.getInventory().add(995, 1000000 + RandomUtility.RANDOM.nextInt(5000000));
    }


    public static void uncommonRReward(Player player) {
        player.getInventory().add(uncommonRewards[Misc.getRandom(uncommonRewards.length - 1)], 1);
        //player.getInventory().add(995, 2500000 + RandomUtility.RANDOM.nextInt(7850000));

    }

    public static void rareRReward(Player player) {
        player.getInventory().add(rareRewards[Misc.getRandom(rareRewards.length - 1)], 1);
        //player.getInventory().add(995, 5000000 + RandomUtility.RANDOM.nextInt(10000000));
    }


    public static void extremeRReward(Player player) {
        player.getInventory().add(extremeRewards[Misc.getRandom(extremeRewards.length - 1)], 1);
        //player.getInventory().add(995, 12500000 + RandomUtility.RANDOM.nextInt(25000000));
    }


    public static void legendaryRReward(Player player) {
        player.getInventory().add(legendaryRewards[Misc.getRandom(legendaryRewards.length - 1)], 1);

        //player.getInventory().add(995, 25000000 + RandomUtility.RANDOM.nextInt(27500000));
    }

    public static void bossPetReward(Player player) {
        player.getInventory().add(bossRewards[Misc.getRandom(bossRewards.length - 1)], 1);

    }


}
