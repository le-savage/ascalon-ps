package com.AscalonPS.world.content.combat.bossminigame;


import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Item;
import com.AscalonPS.model.PlayerRights;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.World;
import com.AscalonPS.world.content.dialogue.DialogueManager;
import com.AscalonPS.world.entity.impl.player.Player;


public class BossRewardChest {

    /** ID of the reward chest **/
    public static int rewardChestID = 4126;

    /** This runs the code after the chest is clicked **/

    public static void clickChest(Player player) {
        if (!player.getClickDelay().elapsed(1000))
            return;

        if (!player.isShouldGiveBossReward()) {
            player.getPacketSender().sendMessage("You're not due a reward yet. Complete at least one wave.");
            return;
        }

        TaskManager.submit(new Task(0, player, true) {
            @Override
            public void execute() {
                player.performAnimation(new Animation(6387));
                player.getPacketSender().sendMessage("Looting Chest...");
                getChances(player); // Gets the chances after all modifiers have been applied.
                chestDialogue(player);
                this.stop();
            }
        });
    }

    /** Setting the ints **/

    private static int chanceOfShitReward = 233;
    private static int chanceOfMediumReward = 900;
    private static int chanceOfRareReward = 998;


    /** Here we apply some modifiers to increase the chance of winning a rare **/

    public static void getChances(Player player) {

        /** Default Chance Of Each Reward (Out of 1000) **/

        chanceOfShitReward = 233;
        chanceOfMediumReward = 900;
        chanceOfRareReward = 998;

        PlayerRights rights = player.getRights();
        int currentBossWave = player.getCurrentBossWave();
        System.out.println("Player "+player.getUsername() + " is a " + rights + " on wave: " + currentBossWave);

        /* This should be obvious. It will change chances based on the players rights **/

        switch (rights) {
            case UBER_DONATOR:
            case SUPPORT:
            case MODERATOR:
            case ADMINISTRATOR:
            case DEVELOPER:
            case COMMUNITYMANAGER:
            case OWNER:
                chanceOfShitReward -= 10; //2% Decreased change of a shit reward
                chanceOfMediumReward -= 7; //5% Increased chance of medium reward
                chanceOfRareReward -= 1;//5% Increased chance of rare reward
                break;
        }

        /* This will check the current player wave. For every wave,
          it will increase the chance to win a rare and medium reward whilst
          also decreasing the chance to win a shit reward
         */

        if (currentBossWave >= 1) { //Only modifies if wave is more than 1
            chanceOfShitReward -= currentBossWave * 4; //3% Decreased change of a shit reward per wave
            chanceOfMediumReward -= Math.floor((currentBossWave * 2) / 0.3); //2% Increased chance of medium reward per wave
            chanceOfRareReward -= currentBossWave;//0.1% Increased chance of rare reward per wave after wave
        }

        //Work Out Percentage Chance
        System.out.println(player.getUsername() + " Shit Reward Chance:"+((double)(1000-chanceOfShitReward)/10) +"% Medium:"+((double)(1000-chanceOfMediumReward)/10)+"% Rare:"+((double)(1000-chanceOfRareReward)/10)+"%");
    }

    /** This configures the dialogue that the player sees. It
     * provides information as well as offers the option to
     * gamble or open for the current reward
     */

    public static void chestDialogue(Player player) {
        if (player.getCurrentBossWave() <= 4) {
            DialogueManager.start(player, 179);
            player.getPacketSender().sendString(988, "It's time to roll a random number!");
            player.getPacketSender().sendString(990, "Number needed: Crap: @blu@" + (chanceOfShitReward + "+") + "@bla@, Medium: @blu@" + (chanceOfMediumReward + "+") + (player.currentBossWave > 1 ? ("@bla@, Rare: @blu@" + (chanceOfRareReward + "+") + "@bla@") : ""));
            player.setDialogueActionId(85);
        } else {
            DialogueManager.start(player, 182);
            player.getPacketSender().sendString(2460, "Good Job!");
            player.getPacketSender().sendString(2461, "Lets open this chest! My chances are below..");
            player.getPacketSender().sendString(2462, "Number needed: Crap: @blu@" + (chanceOfShitReward + "+") + "@bla@, Medium: @blu@" + (chanceOfMediumReward + "+") + (player.currentBossWave > 1 ? ("@bla@, Rare: @blu@" + (chanceOfRareReward + "+") + "@bla@") : ""));
            player.setDialogueActionId(86);
        }
    }

    /** This code runs if the player decides to open the chest.
     * It will decide what reward to give the player,
     * and also add it to their inventory.
     * After that, it sets 'shouldGiveBossReward' to false
     * and resets current progress
     */
    public static void pickReward(Player player) {



        System.out.println("Getting Chance modifiers from getChances");

        int chance = Misc.random(0, 1000); //Pick the number

        player.forceChat("I rolled " + chance+"!");

        System.out.println("Boss Game Roll for "+ player.getUsername() + " was " + chance);

        Item rewardGiven = null;

        if (chance < chanceOfShitReward) { //No Reward for you Mr
            player.getPacketSender().sendMessage("Sorry! Better luck next time..");
            System.out.println("["+player.getUsername()+"] No Reward");

        }

        if (chance >= chanceOfShitReward && chance < chanceOfMediumReward) { //Player deserves a shit reward for this roll
            Item shitReward = BossRewardChestData.SHIT_REWARDS[Misc.getRandom(BossRewardChestData.SHIT_REWARDS.length - 1)];
            player.getInventory().add(shitReward);
            rewardGiven = shitReward;
            System.out.println("["+player.getUsername()+"] Shit Reward selected:  " + shitReward.getAmount() + " x " + shitReward.getDefinition().getName());
        }

        if (chance >= chanceOfMediumReward && chance < chanceOfRareReward) {
            Item mediumReward = BossRewardChestData.MEDIUM_REWARDS[Misc.getRandom(BossRewardChestData.MEDIUM_REWARDS.length - 1)];
            player.getInventory().add(mediumReward);
            rewardGiven = mediumReward;
            System.out.println("["+player.getUsername()+"] Medium Reward selected:  " + mediumReward.getAmount() + " x " + mediumReward.getDefinition().getName());
        }

        if (chance >= chanceOfRareReward) {
            if (player.getCurrentBossWave() == 1) {
                Item mediumReward = BossRewardChestData.MEDIUM_REWARDS[Misc.getRandom(BossRewardChestData.MEDIUM_REWARDS.length - 1)];
                player.getInventory().add(mediumReward);
                System.out.println("["+player.getUsername()+"] Rare on wave 1 Reward selected:  " + mediumReward.getAmount() + " x " + mediumReward.getDefinition().getName());
            } else if (player.getCurrentBossWave() >= 2) {
                Item rareReward = BossRewardChestData.RARE_REWARDS[Misc.getRandom(BossRewardChestData.RARE_REWARDS.length - 1)];
                player.getInventory().add(rareReward);
                rewardGiven = rareReward;
                System.out.println("["+player.getUsername()+"] Rare Reward selected:  " + rareReward.getAmount() + " x " + rareReward.getDefinition().getName());
            }
        }

        World.sendMessage("@bla@[@blu@"+player.getUsername()+"@bla@] has won @blu@"+rewardGiven.getAmount()+" @bla@x @blu@"+rewardGiven.getDefinition().getName()+"@bla@ from @blu@wave "+player.getCurrentBossWave() + " at ::boss!");

        player.getPacketSender().sendInterfaceRemoval();
        BossMinigameFunctions.resetProgress(player);

    }
}


