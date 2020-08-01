package com.janus.world.content;

import com.janus.model.Item;
import com.janus.util.Misc;
import com.janus.util.RandomUtility;
import com.janus.world.World;
import com.janus.world.entity.impl.player.Player;

public class ClueScrolls {

    public static int CluesCompleted;
    public static String currentHint;

    public static final int[] ACTIVE_CLUES = {2677, 2678, 2679, 2680, 2681, 2682, 2683, 2684, 2685};
    // digging locations or show reward on reading clue??
    // to-do change name of clue scrolls in item def

    private static final Item[][] BASIC_STACKS = {//Always get 1 of the following
            {new Item(454, 25)},//Coal
            {new Item(437, 50)},//Copper Ore
            {new Item(439, 50)},//Tin Ore
            {new Item(454, 50)},//Iron Ore
            {new Item(454, 30)},//Mithril Ore
            {new Item(454, 15)},//Addy Ore
            {new Item(454, 5)},//Rune Ore
            {new Item(2350, 25)},//Bronze Bar
            {new Item(2352, 25)},//Iron Bar
            {new Item(2354, 32)},//Steel Bar
            {new Item(2360, 25)},//Mithril Bar
            {new Item(2362, 12)},//Adamant Bar
            {new Item(2364, 8)},//Rune Bar
            {new Item(299, 200)},//Mithril Seeds
            {new Item(1128, 1)},//Rune PlateBody
            {new Item(1514, 200)},//magic logs
            {new Item(15243, 10)},// handcannon shot
            {new Item(3145, 10)},//cooked karambwan
            {new Item(3143, 200)},//Raw karambwan
            {new Item(1333, 1)},//rune scimitar
            {new Item(995, 10000000)},//coins

    };

    private static final Item[][] LOW_LEVEL_REWARD = {//Always get 2 of the following
            {new Item(454, 125)},//Coal
            {new Item(437, 250)},//Copper Ore
            {new Item(439, 250)},//Tin Ore
            {new Item(454, 250)},//Iron Ore
            {new Item(454, 100)},//Mithril Ore
            {new Item(454, 55)},//Addy Ore
            {new Item(454, 15)},//Rune Ore
            {new Item(2350, 80)},//Bronze Bar
            {new Item(2352, 80)},//Iron Bar
            {new Item(2354, 80)},//Steel Bar
            {new Item(2360, 65)},//Mithril Bar
            {new Item(2362, 55)},//Adamant Bar
            {new Item(2364, 27)},//Rune Bar
            {new Item(299, 50)},//Mithril Seeds
            {new Item(537, 15)},//Dragon Bones
            {new Item(18831, 2)},//Frost Dragon Bones
            {new Item(5698, 1)},//Dds
            {new Item(19111, 1)},//Kiln Cape
            {new Item(2631, 1)},//Highway Mask
            {new Item(2661, 1)},//Saradomin Rune Equip
            {new Item(2663, 1)},//Saradomin Rune Equip
            {new Item(2665, 1)},//Saradomin Rune Equip
            {new Item(2667, 1)},//Saradomin Rune Equip
            {new Item(2615, 1)},//Rune (G)
            {new Item(2617, 1)},//Rune (G)
            {new Item(2619, 1)},//Rune (G)
            {new Item(2621, 1)},//Rune (G)
            {new Item(2623, 1)},//Rune (T)
            {new Item(2625, 1)},//Rune (T)
            {new Item(2627, 1)},//Rune (T)
            {new Item(2629, 1)},//Rune (T)
            {new Item(1543, 1)},//Red Key
            {new Item(2653, 1)},//Zamorak Rune Equip
            {new Item(2655, 1)},//Zamorak Rune Equip
            {new Item(2657, 1)},//Zamorak Rune Equip
            {new Item(2659, 1)},//Zamorak Rune Equip
            {new Item(2669, 1)},//Guthix Rune Equip
            {new Item(2671, 1)},//Guthix Rune Equip
            {new Item(2673, 1)},//Guthix Rune Equip
            {new Item(2675, 1)},//Guthix Rune Equip
            {new Item(8950, 1)},//Pirate Hat
            {new Item(8928, 1)},//Pirate Hat
            {new Item(13354, 1)},//Pirate Hat
    };

    private static final Item[][] MEDIUM_LEVEL_REWARD = {//1 in 3 chance to hit this table


            {new Item(4587, 1)},//dragon scimitar
            {new Item(20072, 1)},//dragon defender
            {new Item(4178, 1)},//abbysal whip
            {new Item(11335, 1)},//dragon full helm
            {new Item(6586, 1)},//amulet of fury
            {new Item(1514, 500)},//magic logs
            {new Item(2364, 500)},//rune bar
            {new Item(454, 500)},//coal
            {new Item(995, 20000000)},//coins
            {new Item(15259, 1)},//dragon pickaxe
            {new Item(990, 5)},//crystal key
            {new Item(19336, 1)},//dragon full helm (or)
            {new Item(19337, 1)},//dragon plateboy (or)
            {new Item(19338, 1)},//dragon platelegs (or)
    };

    private static final Item[][] HIGH_LEVEL_REWARD = {//1 in 10 chance to hit the table
            {new Item(454, 1000)},//Coal
            {new Item(437, 750)},//Copper Ore
            {new Item(439, 750)},//Tin Ore
            {new Item(454, 250)},//Iron Ore
            {new Item(454, 100)},//Mithril Ore
            {new Item(454, 900)},//Addy Ore
            {new Item(454, 700)},//Rune Ore
            {new Item(2350, 800)},//Bronze Bar
            {new Item(2352, 800)},//Iron Bar
            {new Item(2354, 800)},//Steel Bar
            {new Item(2360, 650)},//Mithril Bar
            {new Item(2362, 700)},//Adamant Bar
            {new Item(2364, 600)},//Rune Bar
            {new Item(299, 500)},//Mithril Seeds
            {new Item(537, 200)},//Dragon Bones
            {new Item(18331, 2)},//Frost Dragon Bones
            {new Item(18937, 1)},//1h bonus xp scroll
            {new Item(7936, 700)},//pure essence
            {new Item(20059, 1)},//dryogre rapier
            {new Item(20057, 1)},//drygore longsword
            {new Item(19111, 1)},//Kiln Cape
            {new Item(20058, 1)},//drygore mace
            {new Item(21026, 1)},//ring of coins
            {new Item(1543, 10)},//Red Key

    };

    private static final Item[][] EXTREME_LEVEL_REWARD = {//1 in 1500 chance to hit the table
            {new Item(14484, 1)},//dragon claws
            {new Item(13051, 1)},//armadyl crossbow
            {new Item(20555, 1)},//dragon warhammer
            {new Item(11694, 1)},//armadyl godsword
            {new Item(13239, 1)},//[primordial boots
            {new Item(12708, 1)},//pegesian boots
            {new Item(13235, 1)},//eternal boots
            {new Item(20998, 1)},//twiste bow
            {new Item(6832, 1)},//$30 mystery bo
            {new Item(14018, 1)},//ornate katana
            {new Item(1038, 1)},//red p hat
            {new Item(1040, 1)},//yellow p hat
            {new Item(1042, 1)},//blue p hat
            {new Item(12926, 1)},//Blowpipe
            {new Item(14044, 1)},//Black Phat
            {new Item(14050, 1)},//Black Santa
            {new Item(4084, 1)},//Sled
    };

    private static final String[] HINTS = {"Dig somewhere in the edgeville bank",
            "Dig near the mining guild teleport", "Dig somewhere near the duel arena tele",
            "Dig near one of the slayer masters", "Dig in the area you might see fisherman",
            "Dig near the tele to get chaotics", "Dig near the king of dragons",
            "Dig near the fourth minigame teleport", "Dig where players plant flowers"};

    public static void addClueRewards(Player player) {
        if (player.getInventory().getFreeSlots() < 6) {
            player.getPacketSender().sendMessage("You must have atleast 6 free inventory spaces!");
            return;
        }


        player.getInventory().delete(2714, 1);//Deletes Clue Casket
        Item[] basicLoot = BASIC_STACKS[Misc.getRandom(BASIC_STACKS.length - 1)];
        for (Item item : basicLoot) {
            player.getInventory().add(item);
        }

        if (RandomUtility.RANDOM.nextInt(1) == 1) {
            Item[] lowLoot = LOW_LEVEL_REWARD[Misc.getRandom(LOW_LEVEL_REWARD.length - 1)];
            for (Item item : lowLoot) {
                player.getInventory().add(item);
                player.getInventory().add(item);
                player.getInventory().add(item);
            }
        } else if (RandomUtility.RANDOM.nextInt(3) == 2) {
            Item[] mediumLoot = MEDIUM_LEVEL_REWARD[Misc.getRandom(MEDIUM_LEVEL_REWARD.length - 1)];
            for (Item item : mediumLoot) {
                player.getInventory().add(item);
            }
        } else if (RandomUtility.RANDOM.nextInt(10) == 5) {
            Item[] highLoot = HIGH_LEVEL_REWARD[Misc.getRandom(HIGH_LEVEL_REWARD.length - 1)];
            for (Item item : highLoot) {
                player.getInventory().add(item);
            }
        } else if (RandomUtility.RANDOM.nextInt(1500) == 1288) {
            Item[] extremeLoot = EXTREME_LEVEL_REWARD[Misc.getRandom(EXTREME_LEVEL_REWARD.length - 1)];
            for (Item item : extremeLoot) {
                player.getInventory().add(item);
                World.sendMessage("@or3@[Clue Scroll]@bla@ " + player.getUsername() + " has received a Rare!");
            }
        }

    }

    public static void giveHint(Player player, int itemId) {
        if (itemId == 2677) {
            int index = 0;
            currentHint = HINTS[index];
            player.getPacketSender().sendInterface(47700);
            player.getPacketSender().sendString(47704, currentHint);
            player.getPacketSender().sendString(47703, " " + CluesCompleted);

        }
        if (itemId == 2678) {
            int index = 1;
            currentHint = HINTS[index];
            player.getPacketSender().sendInterface(47700);
            player.getPacketSender().sendString(47704, currentHint);
            player.getPacketSender().sendString(47703, " " + CluesCompleted);

        }
        if (itemId == 2679) {
            int index = 2;
            currentHint = HINTS[index];
            player.getPacketSender().sendInterface(47700);
            player.getPacketSender().sendString(47704, currentHint);
            player.getPacketSender().sendString(47703, " " + CluesCompleted);

        }
        if (itemId == 2680) {
            int index = 3;
            currentHint = HINTS[index];
            player.getPacketSender().sendInterface(47700);
            player.getPacketSender().sendString(47704, currentHint);
            player.getPacketSender().sendString(47703, " " + CluesCompleted);

        }
        if (itemId == 2681) {
            int index = 4;
            currentHint = HINTS[index];
            player.getPacketSender().sendInterface(47700);
            player.getPacketSender().sendString(47704, currentHint);
            player.getPacketSender().sendString(47703, " " + CluesCompleted);

        }
        if (itemId == 2682) {
            int index = 5;
            currentHint = HINTS[index];
            player.getPacketSender().sendInterface(47700);
            player.getPacketSender().sendString(47704, currentHint);
            player.getPacketSender().sendString(47703, " " + CluesCompleted);

        }
        if (itemId == 2683) {
            int index = 6;
            currentHint = HINTS[index];
            player.getPacketSender().sendInterface(47700);
            player.getPacketSender().sendString(47704, currentHint);
            player.getPacketSender().sendString(47703, " " + CluesCompleted);

        }
        if (itemId == 2684) {
            int index = 7;
            currentHint = HINTS[index];
            player.getPacketSender().sendInterface(47700);
            player.getPacketSender().sendString(47704, currentHint);
            player.getPacketSender().sendString(47703, " " + CluesCompleted);

        }
        if (itemId == 2685) {
            int index = 8;
            currentHint = HINTS[index];
            player.getPacketSender().sendInterface(47700);
            player.getPacketSender().sendString(47704, currentHint);
            player.getPacketSender().sendString(47703, " " + CluesCompleted);

        }

    }

    public static void setCluesCompleted(int CluesCompleted, boolean add) {
        if (add)
            CluesCompleted += CluesCompleted;
        else
            ClueScrolls.CluesCompleted = CluesCompleted;
    }

    public static void incrementCluesCompleted(double amount) {
        CluesCompleted += amount;
    }

    public static int getCluesCompleted() {
        return CluesCompleted;
    }

}
