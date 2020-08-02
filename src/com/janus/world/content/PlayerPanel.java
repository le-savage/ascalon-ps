package com.janus.world.content;

import com.janus.GameLoader;
import com.janus.model.definitions.NPCDrops;
import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.content.minigames.impl.Nomad;
import com.janus.world.content.minigames.impl.RecipeForDisaster;
import com.janus.world.entity.impl.player.Player;

public class PlayerPanel {

    public static final String LINE_START = "   > ";

    public static void refreshPanel(Player player) {

        int counter = 39159;
        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Tools");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Staff Online");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Player Panel");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Kill Log");
        player.getPacketSender().sendString(counter++, LINE_START.replace(">", "*") + "@or1@Drop Log");

        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Worldwide Information");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Evil Tree: @yel@" + (EvilTrees.getLocation() != null ? EvilTrees.getLocation().playerPanelFrame : "N/A"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Fountain of Goodwill: @yel@" + (WellOfGoodwill.isActive() ? WellOfGoodwill.getMinutesRemaining() + " mins" : "N/A"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Crashed Star: @yel@" + (ShootingStar.getLocation() != null ? ShootingStar.getLocation().playerPanelFrame : "N/A"));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Bonus: @yel@" + GameLoader.getSpecialDay());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Players Online: @yel@" + World.getPlayers().size() + "");

        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@ General Information");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Server Time: @yel@" + Misc.getCurrentServerTime());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Time Played: @yel@" + Misc.getHoursPlayed((player.getTotalPlayTime() + player.getRecordedLogin().elapsed())));
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Rank: @yel@" + player.getRights().toString());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Donated: @yel@" + player.getAmountDonated());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Exp Lock: @yel@" + (player.experienceLocked() ? "Locked" : "Unlocked"));

        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "@or3@-@whi@ Player Statistics");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Loyalty Points:@yel@ " + player.getPointsHandler().getLoyaltyPoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Donation Points:@yel@ " + player.getPointsHandler().getDonationPoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Voting Points:@yel@ " + player.getPointsHandler().getVotingPoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Trivia Points:@yel@ " + player.getPointsHandler().getTriviaPoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Prestige Points:@yel@ " + player.getPointsHandler().getPrestigePoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Dung. Tokens:@yel@ " + player.getPointsHandler().getDungeoneeringTokens());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Boss Points:@yel@ " + player.getBossPoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Slayer Points:@yel@ " + player.getPointsHandler().getSlayerPoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Pk Points:@yel@ " + player.getPointsHandler().getPkPoints());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Difficulty:@yel@ " + player.getDifficulty().toString());

        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Drop Rate:@yel@ +" + (NPCDrops.getDroprate(player)) + "%");

        player.getPacketSender().sendString(counter++, "");

        player.getPacketSender().sendString(counter++, "-@whi@ Slayer Information");
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Master: @yel@" + player.getSlayer().getSlayerMaster());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task: @yel@" + player.getSlayer().getSlayerTask());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task Amount: @yel@" + player.getSlayer().getAmountToSlay());
        player.getPacketSender().sendString(counter++, LINE_START + "@or1@Task Streak: @yel@" + player.getSlayer().getTaskStreak());

        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "-@whi@ Quests");
        player.getPacketSender().sendString(counter++,
                LINE_START + RecipeForDisaster.getQuestTabPrefix(player) + "Recipe For Disaster ");
        player.getPacketSender().sendString(counter++,
                LINE_START + Nomad.getQuestTabPrefix(player) + "Nomad's Requiem ");
        player.getPacketSender().sendString(counter++, "");
        player.getPacketSender().sendString(counter++, "");


    }

}