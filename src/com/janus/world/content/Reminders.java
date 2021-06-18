package com.janus.world.content;

import com.janus.util.Misc;
import com.janus.util.Stopwatch;
import com.janus.world.World;
import com.janus.world.content.discord.DiscordMessenger;

/*
 * @author Bas
 * www.Arlania.com
 */

public class Reminders {


    private static final int TIME = 900000; //10 minutes
    private static Stopwatch timer = new Stopwatch().reset();
    public static String currentMessage;

    /*
     * Random Message Data
     */
    private static final String[][] MESSAGE_DATA = {
            {"<img=10> @blu@[TIPS]@bla@ Donators get free access to instances!"},
            {"<img=10> @blu@[TIPS]@bla@ Donors get HP refills and other perks in instances!"},
            {"<img=10> @blu@[TIPS]@bla@ Super Donor? Use ::pickup (value) to auto pickup drops!"},
            {"<img=10> @blu@[TIPS]@bla@ Melt your unwanted armour and weapons! TP To Smithing!"},
            {"<img=10> @blu@[TIPS]@bla@ ::benefits for donation rewards!"},
            {"<img=10> @blu@[TIPS]@bla@ ::loot to see mystery box items!"},
            {"<img=10> @blu@[TIPS]@bla@ ::drops for our drop table!"},
            {"<img=10> @blu@[TIPS]@bla@ Join 'Help' CC For Help/Tips!"},
            {"<img=10> @blu@[TIPS]@bla@ Donate to help the server grow! ::store"},
            {"<img=10> @blu@[TIPS]@bla@ Use the ::help command to ask staff for help"},
            {"<img=10> @blu@[TIPS]@bla@ Use ::commands to find a list of commands"},
            {"<img=10> @blu@[TIPS]@bla@ Register and post on the forums to keep them active! ::Forum"},
            {"<img=10> @blu@[TIPS]@bla@ Simply click the skill you'd like to train for teleports!"},
            {"<img=10> @blu@[TIPS]@bla@ ::vote for cash and vote points! Claim with ::reward or ::voted"},
            {"<img=10> @blu@[TIPS]@bla@ Sell items via trading or Player Shops!"},
            {"<img=10> @blu@[TIPS]@bla@ Check what items sell for using ::trades or click the link!"},
            {"<img=10> @blu@[TIPS]@bla@ Donate to ::fountain for bonus XP and drop rates!"},
            {"<img=10> @blu@[TIPS]@bla@ ::pray to teleport to alters!"},
            {"<img=10> @blu@[TIPS]@bla@ ::portals or ::teleports to travel to the City Portals!"},
            {"<img=10> @blu@[TIPS]@bla@ Join ::discord for giveaways!"},
            {"<img=10> @blu@[TIPS]@bla@ Boss Pet boxes now available!"},
            {"<img=10> @blu@[TIPS]@bla@ Use ::updates to view our latest additions!"},
            {"<img=10> @blu@[TIPS]@bla@ Use ::maxhit to see what damage you can do!"},
            {"<img=10> @blu@[TIPS]@bla@ Use ::bug to report a bug and receive a reward!"},
            {"<img=10> @blu@[TIPS]@bla@ Talk to Max to prestige your skills!"},
            {"<img=10> @blu@[TIPS]@bla@ ::afk to skill whilst you're away!"},


    };

    /*
     * Sequence called in world.java
     * Handles the main method
     * Grabs random message and announces it
     */
    public static void sequence() {
        if (timer.elapsed(TIME)) {
            timer.reset();
            {

                currentMessage = MESSAGE_DATA[Misc.getRandom(MESSAGE_DATA.length - 1)][0];
                World.sendFilteredMessage(currentMessage);
                //DiscordMessenger.sendErrorLog("REMINDER SENT");
			/*//World.savePlayers();
				PlayerPunishment.reloadDailyRewards();
				WellOfGoodwill.save();
				WellOfWealth.save();
				//GrandExchangeOffers.save();
				ClanChatManager.save();*/

            }

            //World.savePlayers();
            //PlayerPunishment.reloadDailyRewards();
        }


    }

}