package com.AscalonPS.world.content.dailyreward;

import com.AscalonPS.model.Item;
import com.AscalonPS.model.Locations;
import com.AscalonPS.world.World;
import com.AscalonPS.world.content.PlayerPunishment;
import com.AscalonPS.world.entity.impl.player.Player;

public class DailyReward {
    Player player;

    private Item todaysItem;
    private Item tomorrowsItem;

    private long nextRewardTime;
    private int day = 0;

    public DailyReward(Player player) {
        this.player = player;
    }

    public void openInterface() {
        if (player.getLocation() == Locations.Location.WILDERNESS) {
            player.getPacketSender().sendMessage("You can't claim your rewards in the wilderness.");
            return;
        }

        String message = "The 31 Days of Rewards contains a bunch of rewards \\n" + "for you to claim! Login every day to claim your reward. \\n \\n \\n" +
                "Note that this is limited to one per user, which means, \\n" + "do not make multiple accounts to claim rewards. \\n \\n \\n" +
                "Every month has a new reward list, so make sure to \\n" + "stick around!";
        player.getPA().sendString(DailyRewardConstants.DESCRIPTION, message);

        for (int i = 0; i < Rewards.loot.length; i++) {
            Item item = Rewards.loot[i];
            player.getPA().sendItemOnInterfaceInt(DailyRewardConstants.REWARD_CONTAINER, item.getId(), i, item.getAmount());
        }

        if (System.currentTimeMillis() >= nextRewardTime && player.getClaimedTodays()) {
            player.setClaimedTodays(false);
            day++;
        }

        int index = getDay();
        todaysItem = Rewards.loot[index];
        if ((index + 1) >= Rewards.loot.length) {
            tomorrowsItem = Rewards.loot[0];
        } else {
            tomorrowsItem = Rewards.loot[index + 1];
        }

        player.getPA().sendItemOnInterfaceInt(DailyRewardConstants.TODAYS_AWARD, todaysItem.getId(), 0, todaysItem.getAmount());
        player.getPA().sendItemOnInterfaceInt(DailyRewardConstants.TOMORROWS_AWARD, tomorrowsItem.getId(), 0, tomorrowsItem.getAmount());

        if (System.currentTimeMillis() >= nextRewardTime) {
            player.getPA().sendString(DailyRewardConstants.BUTTON_DESCRIPTION, "Claim Reward");
        } else {
            player.getPA().sendString(DailyRewardConstants.BUTTON_DESCRIPTION, "@red@      " + timeLeft());
        }

        player.getPA().sendInterface(DailyRewardConstants.MAIN_INTERFACE);

    }

    public String timeLeft() {
        long durationInMillis = nextRewardTime - System.currentTimeMillis();
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

        if (durationInMillis <= 0) {
            return "Claim Reward";
        } else {
            return String.format("%02d:%02d", hour, minute);
        }
    }

    public void claimReward() {
        if (player.getLocation() == Locations.Location.WILDERNESS) {
            player.getPacketSender().sendMessage("You can't claim your rewards in the wilderness.");
            return;
        }
        if (!(System.currentTimeMillis() >= nextRewardTime)) {
            player.sendMessage("You have already claimed your reward for today! Please wait @red@" + timeLeft() + "@bla@.");
            player.getPA().closeAllWindows();
            return;
        }

        if (PlayerPunishment.hasClaimedDailyRewardIP(player.getHostAddress()) || PlayerPunishment.hasClaimedDailyRewardUID(player.getUUID()) || PlayerPunishment.hasClaimedDailyRewardMAC(player.getMac())) {
            player.sendMessage("You have already claimed a reward today on another account!");
            player.getPA().closeAllWindows();
            World.sendStaffMessage("Player " + player.getUsername() + " tried to claim. He already claimed on an alt!");
            return;
        }


        PlayerPunishment.addIpToDailyRewardList(player.getUsername(), player.getHostAddress(), player.getUUID(), player.getMac());

        if (todaysItem.getDefinition().isNoted() || todaysItem.getDefinition().isStackable()) {
            if (player.getInventory().getFreeSlots() >= 1)
                player.getInventory().add(todaysItem.getId(), todaysItem.getAmount());
             else
                player.getPacketSender().sendMessage("You need at least 1 inventory slot to claim today's reward!");
        }

        if (!todaysItem.getDefinition().isNoted()) {
            if (player.getInventory().getFreeSlots() >= todaysItem.getAmount()) {
                player.getInventory().add(todaysItem.getId(), todaysItem.getAmount());
            } else {
                player.getPacketSender().sendMessage("You need at least " + todaysItem.getAmount() + " inventory slots to claim today's reward!");
            }
        }


        World.sendMessage("@gre@" + player.getUsername() + " has just claimed their daily reward: " + todaysItem.getAmount() + " x " + todaysItem.getDefinition().getName());
        //DiscordMessenger.sendDailyLoginLog("[" + player.getUsername() + "] has Claimed " + todaysItem.getAmount() + " x " + todaysItem.getDefinition().getName() + " [IP: " + player.getHostAddress() + "]");
        nextRewardTime = System.currentTimeMillis() + (1000 * 60 * 60 * 24);
        player.setClaimedTodays(true);
        player.sendMessage("Thank you for all of your support! <col=8505C4>Enjoy your reward</col>.");
        player.getPA().closeAllWindows();
    }

    public void checkToResetDay() {
        if (day >= 31) {
            day = 0;
        }
    }

    public long getNextRewardTime() {
        return nextRewardTime;
    }

    public void setNextRewardTime(long nextRewardTime) {
        this.nextRewardTime = nextRewardTime;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        checkToResetDay();
        return day;
    }

    public boolean hasClaimedTodaysReward() {
        if (!player.getClaimedTodays()) {
            return false;
        } else {
            return true;
        }
    }

}
