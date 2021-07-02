package com.janus.world.content.newquesttab;

import com.janus.GameLoader;
import com.janus.GameSettings;
import com.janus.engine.task.Task;
import com.janus.model.definitions.NPCDrops;
import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.content.*;
import com.janus.world.entity.impl.player.Player;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestTab {
    private final Player player;
    private int currentTab = 1;
    public static Task refreshPanel() {
        return new Task(1) {

            @Override
            protected void execute() {
                for(Player player : World.getPlayers()) {
                    if(player == null)
                        continue;
                    player.getQuestTab().refresh();
                }
            }
        };
    }
    public void refresh() {
        for(int i = 40051; i <= 40101; i++) {
            player.getPA().sendString(i, "");
        }
        switch (currentTab) {
            case 1:
                sendServerOverview();
                break;
            case 2:
                sendPlayerOverview();
                break;
            case 3:
                sendTools();
                break;
        }
    }
    public void sendServerOverview() {
        int id = 40051;
        player.getPA().sendString(id++, "");
        player.getPA().sendString(id++, "Server Time - " + Misc.getCurrentServerTime());
        player.getPA().sendString(id++, "Players Online - " + World.getPlayers().size());
        player.getPA().sendString(id++, "Server Bonus - " + GameLoader.getSpecialDay());
        player.getPA().sendString(id++, "Crashed Star - "  + (ShootingStar.getLocation() != null ? ShootingStar.getLocation().playerPanelFrame : "N/A"));
        player.getPA().sendString(id++, "Goodwill - " + (WellOfGoodwill.isActive() ? WellOfGoodwill.getMinutesRemaining() + " mins" : "N/A"));
        player.getPA().sendString(id++, "Evil Tree - " + (EvilTrees.getLocation() != null ? EvilTrees.getLocation().playerPanelFrame : "N/A"));
    }
    public void sendPlayerOverview() {
        int id = 40051;
        player.getPA().sendString(id++, "");
        player.getPA().sendString(id++, "Rank - " + player.getRights().name());
        player.getPA().sendString(id++, "Donated - $" + player.getAmountDonated());
        player.getPA().sendString(id++, "Time Played - " + player.getTotalPlayTime());
        player.getPA().sendString(id++, "EXP Lock - " + player.experienceLocked());
        player.getPA().sendString(id++, "Drop Rate - " + NPCDrops.getDroprate(player));
        player.getPA().sendString(id++, "Difficulty - " + player.getDifficulty().name());
        player.getPA().sendString(id++, "Loyalty Points - " + player.getPointsHandler().getLoyaltyPoints());
        player.getPA().sendString(id++, "Donation Points - " + player.getPointsHandler().getDonationPoints());
        player.getPA().sendString(id++, "Voting Points - " + player.getPointsHandler().getVotingPoints());
        player.getPA().sendString(id++, "Trivia Points - " + player.getPointsHandler().getTriviaPoints());
        player.getPA().sendString(id++, "Prestige Points - " + player.getPointsHandler().getPrestigePoints());
        player.getPA().sendString(id++, "Dung Tokens - " + player.getPointsHandler().getDungeoneeringTokens());
        player.getPA().sendString(id++, "Boss Points - " + player.getBossPoints());
        player.getPA().sendString(id++, "Slayer Points - " + player.getPointsHandler().getSlayerPoints());
        player.getPA().sendString(id++, "PK Points - " + player.getPointsHandler().getPkPoints());
        player.getPA().sendString(id++, "");
        player.getPA().sendString(id++, "--- Slayer ---");
        player.getPA().sendString(id++, "Master - " + player.getSlayer().getSlayerMaster().name());
        player.getPA().sendString(id++, "Task - " + player.getSlayer().getSlayerTask().name());
        player.getPA().sendString(id++, "Task Amount - " + player.getSlayer().getAmountToSlay());
        player.getPA().sendString(id++, "Task Streak - " + player.getSlayer().getTaskStreak());
    }
    public void sendTools() {
        int id = 40051;
        player.getPA().sendString(id++, "");
        player.getPA().sendString(id++, "Player Panel");
        player.getPA().sendString(id++, "Kill Log");
        player.getPA().sendString(id++, "Staff Online");
        player.getPA().sendString(id++, "Drop Log");
    }
    public boolean handleButton(int id) {
        switch(id) {
            case -25532:
                currentTab = 1;
                player.getPA().sendString(40003, "Server Overview");
                player.getPA().sendSpriteChange(40004, 1055);
                player.getPA().sendSpriteChange(40005, 1054);
                player.getPA().sendSpriteChange(40006, 1054);
                return true;
            case -25531:
                currentTab = 2;
                player.getPA().sendString(40003, "Player Overview");
                player.getPA().sendSpriteChange(40004, 1054);
                player.getPA().sendSpriteChange(40005, 1055);
                player.getPA().sendSpriteChange(40006, 1054);
                return true;
            case -25530:
                currentTab = 3;
                player.getPA().sendString(40003, "Tools");
                player.getPA().sendSpriteChange(40004, 1054);
                player.getPA().sendSpriteChange(40005, 1054);
                player.getPA().sendSpriteChange(40006, 1055);
                return true;
        }

        if(currentTab == 3) {
            switch (id) {
                case -25485:
                    return true;
                case -25484:
                    ProfileViewing.view(player, player);
                    return true;
                case -25483:
                    KillsTracker.open(player);
                    return true;
                case -25482:
                    player.getPacketSender().sendTabInterface(GameSettings.QUESTS_TAB, 46343);
                    StaffList.updateInterface(player);
                    return true;
                case -25481:
                    DropLog.open(player);
                    return true;
            }
        }
        return false;
    }
}
