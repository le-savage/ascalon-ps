package com.AscalonPS.world.content.skill.impl.slayer;

import com.AscalonPS.model.Position;
import com.AscalonPS.util.Misc;

/**
 * @author Gabriel Hannason
 */

public enum SlayerTasks {

    NO_TASK(null, -1, null, -1, null, 0, 0),

    /**
     * Easy tasks
     */
    ROCK_CRAB(SlayerMaster.VANNAKA, 1265, "Rock Crabs can be found in the Training Teleport.", 2100, new Position(2709, 3715, 0), 15, 20),
    EXPERIMENT(SlayerMaster.VANNAKA, 1677, "Experiments can be found in the Training Teleport.", 2150, new Position(3564, 9954, 0), 20, 10),
    GIANT_BAT(SlayerMaster.VANNAKA, 78, "Giant Bats can be found in Taverly Dungeon.", 2000, new Position(2907, 9833), 10, 20),
    CHAOS_DRUID(SlayerMaster.VANNAKA, 181, "Chaos Druids can be found in Edgeville Dungeon.", 2120, new Position(3109, 9931, 0), 20, 15),
    ZOMBIE(SlayerMaster.VANNAKA, 76, "Zombies can be found in Edgeville Dungeon.", 2000, new Position(3144, 9903, 0), 10, 15),
    HOBGOBLIN(SlayerMaster.VANNAKA, 2686, "Hobgoblins can be found in Edgeville Dungeon.", 4500, new Position(3123, 9876, 0), 19, 16),
    HILL_GIANT(SlayerMaster.VANNAKA, 117, "Hill Giants can be found in Edgeville Dungeon.", 4700, new Position(3120, 9844, 0), 15, 15),
    DEADLY_RED_SPIDER(SlayerMaster.VANNAKA, 63, "Deadly Red Spiders can be found in Edgeville Dungeon.", 4500, new Position(3083, 9940, 0), 10, 14),
    BABY_BLUE_DRAGON(SlayerMaster.VANNAKA, 52, "Baby Blue Dragons can be found in Taverly Dungeon.", 5000, new Position(2891, 9772, 0), 20, 20),
    SKELETON(SlayerMaster.VANNAKA, 90, "Skeletons can be found in Edgeville Dungeon.", 2200, new Position(3094, 9896), 10, 10),
    EARTH_WARRIOR(SlayerMaster.VANNAKA, 124, "Earth Warriors can be found in Edgeville Dungeon.", 540, new Position(3124, 9986), 15, 10),
    YAK(SlayerMaster.VANNAKA, 5529, "Yaks can be found in the Training Teleport.", 2500, new Position(3203, 3267), 15, 20),
    GHOUL(SlayerMaster.VANNAKA, 1218, "Ghouls can be found in the Training Teleport.", 4800, new Position(3418, 3508), 15, 17),

    /**
     * Medium tasks
     */
    BANDIT(SlayerMaster.DURADEL, 1880, "Bandits can be found in the Training teleport.", 6500, new Position(3172, 2976), 20, 25),
    WILD_DOG(SlayerMaster.DURADEL, 1593, "Wild Dogs can be found in Brimhaven Dungeon.", 6700, new Position(2680, 9557), 30, 20),
    MOSS_GIANT(SlayerMaster.DURADEL, 112, "Moss Giants can be found in Brimhaven Dungeon.", 6600, new Position(2676, 9549), 40, 15),
    FIRE_GIANT(SlayerMaster.DURADEL, 110, "Fire Giants can be found in Brimhaven Dungeon.", 6900, new Position(2664, 9480), 20, 29),
    GREEN_DRAGON(SlayerMaster.DURADEL, 941, "Green Dragons can be found in western Wilderness.", 7500, new Position(2977, 3615), 20, 39),
    BLUE_DRAGON(SlayerMaster.DURADEL, 55, "Blue Dragons can be found in Taverly Dungeon.", 8000, new Position(2892, 9799), 20, 20),
    HELLHOUND(SlayerMaster.DURADEL, 49, "Hellhounds can be found in Taverly Dungeon.", 8000, new Position(2870, 9848), 30, 15),
    BLACK_DEMON(SlayerMaster.DURADEL, 84, "Black Demons can be found in Edgeville Dungeon.", 8270, new Position(3089, 9967), 30, 20),
    BLOODVELD(SlayerMaster.DURADEL, 1618, "Bloodvelds can be found in Slayer Tower.", 7200, new Position(3418, 3570, 1), 20, 20),
    INFERNAL_MAGE(SlayerMaster.DURADEL, 1643, "Infernal Mages can be found in Slayer Tower.", 7000, new Position(3445, 3579, 1), 30, 30),
    ABERRANT_SPECTRE(SlayerMaster.DURADEL, 1604, "Aberrant Spectres can be found in Slayer Tower.", 7300, new Position(3432, 3553, 1), 15, 45),
    NECHRYAEL(SlayerMaster.DURADEL, 1613, "Nechryaels can be found in Slayer Tower.", 7800, new Position(3448, 3564, 2), 50, 10),
    GARGOYLE(SlayerMaster.DURADEL, 1610, "Gargoyles can be found in Slayer Tower.", 8100, new Position(3438, 3534, 2), 35, 25),
    TZHAAR_XIL(SlayerMaster.DURADEL, 2605, "TzHaar-Xils can be found in Tzhaar City.", 9000, new Position(2445, 5147), 15, 45),
    TZHAAR_HUR(SlayerMaster.DURADEL, 2600, "TzHaar-Hurs can be found in Tzhaar City.", 7900, new Position(2456, 5135), 15, 45),
    ORK(SlayerMaster.DURADEL, 6273, "Orks can be found in the Godwars Dungeon.", 7600, new Position(2867, 5322), 50, 10),
    ARMOURED_ZOMBIE(SlayerMaster.DURADEL, 8162, "Armoured Zombies can be found in the Training Teleport", 8000, new Position(3085, 9672), 35, 25),
    DUST_DEVIL(SlayerMaster.DURADEL, 1624, "Dust Devils can be found in the Training Teleport", 9500, new Position(3279, 2964), 40, 30),
    JUNGLE_STRYKEWYRM(SlayerMaster.DURADEL, 9467, "Strykewyrms can be found in the Strykewyrm Cavern", 10874, new Position(2731, 5095), 35, 25),
    DESERT_STRYKEWYRM(SlayerMaster.DURADEL, 9465, "Strykewyrms can be found in the Strykewyrm Cavern.", 11120, new Position(2731, 5095), 37, 21),

    /**
     * Hard tasks
     */
    MONKEY_GUARD(SlayerMaster.KURADEL, 1459, "Monkey Guards can be found in the Training Teleport", 16000, new Position(2795, 2775), 50, 20),
    WATERFIEND(SlayerMaster.KURADEL, 5361, "Waterfiends can be found in the Ancient Cavern.", 17400, new Position(1737, 5353), 55, 40),
    ICE_STRYKEWYRM(SlayerMaster.KURADEL, 9463, "Strykewyrms can be found in the Strykewyrm Cavern.", 15877, new Position(2731, 5095), 60, 40),
    STEEL_DRAGON(SlayerMaster.KURADEL, 1592, "Steel dragons can be found in Brimhaven Dungeon.", 18600, new Position(2710, 9441), 50, 30),
    MITHRIL_DRAGON(SlayerMaster.KURADEL, 5363, "Mithril Dragons can be found in the Ancient Cavern.", 16000, new Position(1761, 5329, 1), 70, 30),
    GREEN_BRUTAL_DRAGON(SlayerMaster.KURADEL, 5362, "Green Brutal Dragons can be found in the Ancient Cavern.", 15590, new Position(1767, 5340), 50, 30),
    SKELETON_WARLORD(SlayerMaster.KURADEL, 6105, "Skeleton Warlords can be found in the Ancient Cavern.", 14400, new Position(1763, 5358), 80, 20),
    SKELETON_BRUTE(SlayerMaster.KURADEL, 6104, "Skeleton Brutes can be found in the Ancient Cavern.", 14400, new Position(1788, 5335), 90, 10),
    AVIANSIE(SlayerMaster.KURADEL, 6246, "Aviansies can be found in the Godwars Dungeon.", 14600, new Position(2868, 5268, 2), 80, 19),
    FROST_DRAGON(SlayerMaster.KURADEL, 51, "Frost Dragons can be found in the deepest of Wilderness.", 22570, new Position(2968, 3902), 60, 15),
    ANGRY_BARBARIAN_SPIRIT(SlayerMaster.KURADEL, 750, "Angry Barbarian Spirits can be found in the Ancient Cavern", 25000, new Position(1754, 5325), 60, 30),
    TZHAAR_XIL2(SlayerMaster.KURADEL, 2605, "TzHaar-Xils can be found in Tzhaar City.", 17000, new Position(2445, 5147), 70, 30),
    SLASH_BASH(SlayerMaster.KURADEL, 2060, "Slash Bash can be found using the Boss teleport.", 19000, new Position(2547, 9448), 50, 50),
    GREEN_DRAGON2(SlayerMaster.KURADEL, 941, "Green Dragons can be found in western Wilderness.", 12000, new Position(2977, 3615), 60, 40),
    BLUE_DRAGON2(SlayerMaster.KURADEL, 55, "Blue Dragons can be found in Taverly Dungeon.", 11000, new Position(2892, 9799), 50, 17),
    HELLHOUND2(SlayerMaster.KURADEL, 49, "Hellhounds can be found in Taverly Dungeon.", 14000, new Position(2870, 9848), 50, 30),
    BLACK_DEMON2(SlayerMaster.KURADEL, 84, "Black Demons can be found in Edgeville Dungeon.", 8270, new Position(3089, 9967), 70, 10),
    BLOODVELD2(SlayerMaster.KURADEL, 1618, "Bloodvelds can be found in Slayer Tower.", 15000, new Position(3418, 3570, 1), 60, 16),
    INFERNAL_MAGE2(SlayerMaster.KURADEL, 1643, "Infernal Mages can be found in Slayer Tower.", 13000, new Position(3445, 3579, 1), 50, 19),
    ABERRANT_SPECTRE2(SlayerMaster.KURADEL, 1604, "Aberrant Spectres can be found in Slayer Tower.", 14000, new Position(3432, 3553, 1), 50, 12),
    NECHRYAEL2(SlayerMaster.KURADEL, 1613, "Nechryaels can be found in Slayer Tower.", 12000, new Position(3448, 3564, 2), 63, 5),
    /**
     * Elite
     */
    NEX(SlayerMaster.SUMONA, 13447, "Nex can be found in the Godwars Dungeon.", 100000, new Position(2903, 5203), 20, 50),
    GENERAL_GRAARDOR(SlayerMaster.SUMONA, 6260, "General Graardor can be found in the Godwars Dungeon.", 68000, new Position(2863, 5354, 2), 110, 40),
    TORMENTED_DEMON(SlayerMaster.SUMONA, 8349, "Tormented Demons can be found using the Boss teleport.", 40000, new Position(2602, 5713), 105, 45),
    KING_BLACK_DRAGON(SlayerMaster.SUMONA, 50, "The King Black Dragon can be found using the Boss teleport.", 26000, new Position(2273, 4680, 1), 55, 25),
    DAGANNOTH_SUPREME(SlayerMaster.SUMONA, 2881, "The Dagannoth Kings can be found using the Boss teleport.", 26000, new Position(1908, 4367), 50, 30),
    DAGANNOTH_REX(SlayerMaster.SUMONA, 2883, "The Dagannoth Kings can be found using the Boss teleport.", 26000, new Position(1908, 4367), 50, 30),
    DAGANNOTH_PRIME(SlayerMaster.SUMONA, 2882, "The Dagannoth Kings can be found using the Boss teleport.", 26000, new Position(1908, 4367), 50, 30),
    CHAOS_ELEMENTAL(SlayerMaster.SUMONA, 3200, "The Chaos Elemental can be found using the Boss teleport.", 58000, new Position(3285, 3921), 115, 35),
    KALPHITE_QUEEN(SlayerMaster.SUMONA, 1160, "The Kalphite Queen can be found using the Boss teleport.", 31000, new Position(3476, 9502), 120, 30),
    PHOENIX(SlayerMaster.SUMONA, 8549, "The Phoenix can be found using the Boss teleport.", 21000, new Position(2839, 9557), 80, 50),
    CORPOREAL_BEAST(SlayerMaster.SUMONA, 8133, "The Corporeal Beast can be found using the Boss teleport.", 80000, new Position(2885, 4375), 100, 30),
    BANDOS_AVATAR(SlayerMaster.SUMONA, 4540, "The Bandos Avatar can be found using the Boss teleport.", 34000, new Position(2891, 4767), 80, 40),
    CRAZY_MAN(SlayerMaster.SUMONA, 3, "The crazy man can be found using the Boss teleport", 40000, new Position(1959, 4759), 101, 49),
    TZTOK_JAD(SlayerMaster.SUMONA, 2745, "Jad can be found in the minigames teleport menu!", 20000, new Position(2439, 5168), 40, 49);

    private SlayerTasks(SlayerMaster taskMaster, int npcId, String npcLocation, int XP, Position taskPosition, int minimumToKill, int additionalRandom) {
        this.taskMaster = taskMaster;
        this.npcId = npcId;
        this.npcLocation = npcLocation;
        this.XP = XP;
        this.taskPosition = taskPosition;
        this.minimumToKill = minimumToKill;
        this.additionalRandom = additionalRandom;
    }

    private SlayerMaster taskMaster;
    private int npcId;
    private String npcLocation;
    private int XP;
    private Position taskPosition;
    private int minimumToKill;
    private int additionalRandom;

    public SlayerMaster getTaskMaster() {
        return this.taskMaster;
    }

    public int getNpcId() {
        return this.npcId;
    }

    public String getNpcLocation() {
        return this.npcLocation;
    }

    public int getXP() {
        return this.XP;
    }

    public Position getTaskPosition() {
        return this.taskPosition;
    }

    public static SlayerTasks forId(int id) {
        for (SlayerTasks tasks : SlayerTasks.values()) {
            if (tasks.ordinal() == id) {
                return tasks;
            }
        }
        return null;
    }

    public static int[] getNewTaskData(SlayerMaster master) {
        int slayerTaskId = 1, slayerTaskAmount = 20;
        int easyTasks = 0, mediumTasks = 0, hardTasks = 0, eliteTasks = 0;
        SlayerTasks taskToSet = SlayerTasks.forId(slayerTaskId);
        int minimumToKill = taskToSet != null ? taskToSet.minimumToKill : 0;
        int additionalRandom = taskToSet != null ? taskToSet.additionalRandom : 0;

        /*
         * Calculating amount of tasks
         */
        for (SlayerTasks task : SlayerTasks.values()) {
            if (task.getTaskMaster() == SlayerMaster.VANNAKA)
                easyTasks++;
            else if (task.getTaskMaster() == SlayerMaster.DURADEL)
                mediumTasks++;
            else if (task.getTaskMaster() == SlayerMaster.KURADEL)
                hardTasks++;
            else if (task.getTaskMaster() == SlayerMaster.SUMONA)
                eliteTasks++;
        }


        switch (master) {

            case SUMONA:
                slayerTaskId = 1 + easyTasks + mediumTasks + hardTasks + Misc.getRandom(eliteTasks - 1);
                break;
            case KURADEL:
                slayerTaskId = 1 + easyTasks + mediumTasks + Misc.getRandom(hardTasks - 1);
                break;
            case DURADEL:
                slayerTaskId = easyTasks - 1 + Misc.getRandom(mediumTasks);
                break;
            case VANNAKA:
                slayerTaskId = 1 + Misc.getRandom(easyTasks);
                break;
        }
        int amountToKill = minimumToKill + Misc.random(additionalRandom);
        return new int[]{slayerTaskId, amountToKill};
    }
    @Override
    public String toString() {
        return Misc.ucFirst(name().toLowerCase().replaceAll("_", " ").replaceAll("2", ""));
    }
}
