package com.AscalonPS.world.content.combat;

import com.AscalonPS.model.Item;
import com.AscalonPS.model.definitions.NpcDefinition;
import com.AscalonPS.util.Misc;
import com.AscalonPS.util.Stopwatch;
import com.AscalonPS.world.World;
import com.AscalonPS.world.entity.impl.player.Player;


public class DailyNPCTask {

    private static final int TIME = 86400000;

    private static Stopwatch timer = new Stopwatch().reset();

    public static int CHOSEN_NPC_ID = 0; //Leave this as 0, it's just a default initialiser

    public static int KILLS_REQUIRED = 500; //Change this if you want to update the task amount

    public static int[] NPC_IDs = {50, 1265, 1459, 181, 2060, 2052, 2028, 2030, 2029, 2026, 76}; //Add your NPC ID's here

    public static Player winner = null;

    public static String lastWinner;


    /** Picks a random NPC for the next task **/
    public static void pickDailyNPC() {
        CHOSEN_NPC_ID = NPC_IDs[Misc.random(NPC_IDs.length - 1)];
        KILLS_REQUIRED = Misc.random(100, 500);
        System.out.println("Today's Chosen NPC is "+ NpcDefinition.forId(CHOSEN_NPC_ID).getName());
        World.sendMessage("@red@New Global NPC Challenge :task "
                + KILLS_REQUIRED
                + " "
                + NpcDefinition.forId(CHOSEN_NPC_ID).getName()
                + "... Complete it first to be rewarded!");
        timer.reset();
    }

    /** Tracks a players progress **/
    public static void countPlayerKill(Player player) {
        int lastKnownNPC = player.getCurrentDailyNPC();
        if (lastKnownNPC != CHOSEN_NPC_ID) { //If the last task wasn't finished and we're onto a new NPC, we need to reset the KC
            player.setCurrentDailyNPCKills(1); //Resetting KC and adding one for the current kill
            player.setCurrentDailyNPC(CHOSEN_NPC_ID); //Setting the last known NPC Task to the current one
        } else { //Players last killed NPC task matches the current one
            if (player.getCurrentDailyNPCKills() < KILLS_REQUIRED) { //Player hasn't won yet
                player.setCurrentDailyNPCKills(player.getCurrentDailyNPCKills() + 1);
                player.getPacketSender().sendMessage((KILLS_REQUIRED - player.getCurrentDailyNPCKills()
                        + " "
                        + NpcDefinition.forId(CHOSEN_NPC_ID).getName()
                        + " kills remaining.")); //Tells the player their current KC every time they get a kill
            } else if (player.getCurrentDailyNPCKills() >= KILLS_REQUIRED) {
                winner = player; //Save the winner
                rewardWinner(winner); //Reward the winner
            }
        }

    }

    public static void rewardWinner(Player player) {
        int amount = Misc.random(500000, 300000000);
        Item reward = new Item(995, amount);
        int freeSlots = player.getInventory().getFreeSlots(); //Int saved to make it clean

            if (freeSlots >= 1) {//If stackable we only need 1 slot
                player.getInventory().add(reward); //Add to inventory
                player.getPacketSender().sendMessage("@blu@Congratulations on winning the daily NPC task! You won " + Misc.setupMoney(amount) + "!");
            } else if (freeSlots == 0) {//If player has no slots
                player.setMoneyInPouch(player.getMoneyInPouch() + amount);
                player.getPacketSender().sendMessage("@blu@Congratulations on winning the daily NPC task!" + Misc.setupMoney(amount) + "sent to your pouch!");
                player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch() + "");
            }

        World.sendMessage("@red@" + winner.getUsername() + " has won today's NPC task and was rewarded "
                + Misc.setupMoney(amount)
                + "!");

        resetDailyNPCGame(); //Restarts the game
    }

    /** Simply restarts the game **/
    public static void resetDailyNPCGame() {
        lastWinner = winner.getUsername(); // Saves last winner name as a string to be used when player is offline.
        winner = null; // Wipes the current winner value
        pickDailyNPC(); // Picks new NPC Task
    }

    public static void dailyResetTask() {
        if (CHOSEN_NPC_ID == 0) {
            pickDailyNPC();
        }
        if (timer.elapsed(TIME)) {
            pickDailyNPC();
        }
    }
}
