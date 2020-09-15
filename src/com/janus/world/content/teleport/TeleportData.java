package com.janus.world.content.teleport;


import com.janus.model.Position;
import com.janus.world.content.combat.tieredbosses.BossFunctions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/** This handles the information stored in the new teleport interface.
 *
 * To following categories will help to store the info,
 *     MONSTER,
 *     BOSSES,
 *     SKILLING,
 *     WILDERNESS,
 *     MINIGAMES,
 *     MISC
 */

@Getter
@RequiredArgsConstructor
public enum TeleportData {
    /** Page one - Monsters **/
    INSTANCE_ARENA("Instance Arena", 3, new Position(2679, 3714), Categories.MONSTER),
    ROCK_CRABS("Rock Crabs", 1265, new Position(2679, 3714), Categories.MONSTER),
    EXPERIMENTS("Experiments", 1677, new Position(3561, 9948), Categories.MONSTER),
    BANDITS("Bandits", 1880, new Position(3169, 2982), Categories.MONSTER),
    YAKS("Yaks", 5529, new Position(3206, 3263), Categories.MONSTER),
    GHOULS("Ghouls", 1218, new Position(3420, 3510), Categories.MONSTER),
    ARMOURED_ZOMBIES("Armoured Zombies", 8162, new Position(3086, 9672), Categories.MONSTER),
    DUST_DEVILS("Dust Devils", 1624, new Position(3277, 2964), Categories.MONSTER),
    MONKEY_SKELETONS("Monkey Skeletons", 1471, new Position(2805, 9143), Categories.MONSTER),
    MONKEY_GUARDS("Monkey Guards", 1459, new Position(2793, 2773), Categories.MONSTER),
    CHAOS_DRUIDS("Chaos Druids", 181, new Position(2933, 9848), Categories.MONSTER),
    CHICKEN_PEN("Chicken Pen", 41, new Position(3235, 3295), Categories.MONSTER),
    TZHAAR_MINIONS("Tzhaar Minions", 2611, new Position(2480, 5174), Categories.MONSTER),

    /** Page two - Minigames **/
    BARROWS("Barrows Brawl", 2026, new Position(3565, 3313), Categories.MINIGAMES),
    FIGHT_CAVES("Fight Caves", 2745, new Position(2439, 5169), Categories.MINIGAMES),
    TIERED_BOSSES("Tiered Boss", 50, new Position(BossFunctions.DOOR.getX(), BossFunctions.DOOR.getY()), Categories.MINIGAMES),
    PEST_CONTROL("Pest Control", 3789, new Position(2663, 2654), Categories.MINIGAMES),
    DUEL_ARENA("Duel Arena", 2, new Position(3364, 3267), Categories.MINIGAMES),
    WARRIORS_GUILD("Warriors Guild", 650, new Position(2854, 3540), Categories.MINIGAMES),
    ZOMBIE_SLAUGHTER("Zombie Slaughter", 76, new Position(3503, 3563), Categories.MINIGAMES),
    /** Page two - Quests **/
    RECIPE_FOR_DISASTER("R.F.Disaster", 3385, new Position(3080, 3501), Categories.MISC),
    NOMADS_REQUIEM("Nomads Requiem", 8528, new Position(1892, 3177), Categories.MISC),

    /** Page three - Bosses **/
    KBD("KBD", 50, new Position(2273, 4658), Categories.BOSSES),
    GWD("Godwars", 6247, new Position(2871, 5318, 2), Categories.BOSSES),
    KALPHITE_QUEEN("Kalphite Queen", 1158, new Position(3488, 9516), Categories.BOSSES),
    SLASH_BASH("KBD", 2060, new Position(2547, 9446), Categories.BOSSES),
    FROST_DRAGON("Frost Dragons", 51, new Position(2961, 3882), Categories.WILDERNESS),
    DAGANNOTH_CAVE("Dagannoth Cave", 2882, new Position(1913, 4368), Categories.BOSSES),
    TORMENTED_DEMONS("Tormented Demons", 8349, new Position(2540, 5774), Categories.BOSSES),
    CHAOS_ELEMENTAL("El3meNt4l cHa0s", 3200, new Position(3276, 3915), Categories.WILDERNESS),
    COPRPREAL_BEAST("Corp. Beast", 8133, new Position(2886, 4376), Categories.BOSSES),
    BARREL_CHEST("Barrel Chest", 5666, new Position(2973, 9517, 1), Categories.BOSSES),
    LIZARD_SHAMAN("Lizard Shaman", 6766, new Position(2718, 9811), Categories.BOSSES),
    Bork("Bork", 7134, new Position(3102, 5536), Categories.BOSSES),

    /** Page four - Bosses **/
    PHOENIX("Phoenix", 8549, new Position(2839, 9557), Categories.BOSSES),
    BANDOS_AVATAR("Bandos Avatar", 4540, new Position(2891, 4767), Categories.BOSSES),
    GLACORS_CAVE("Glacors Cave", 1382, new Position(3050, 9573), Categories.BOSSES),
    NEX("Nex", 13447, new Position(2903, 5203), Categories.BOSSES),
    SCORPIA("Scorpia", 2001, new Position(3236, 3941), Categories.WILDERNESS),
    VENENATIS("Venenatis", 2001, new Position(3350, 3734), Categories.WILDERNESS),
    CERBERUS("Cerberus", 1999, new Position(1240, 1243), Categories.BOSSES),
    SKOTIZO("Skotizo", 7286, new Position(3378, 9816), Categories.BOSSES),
    ABYSSAL_SIRE("Abyssal Sire", 5886, new Position(3333, 3864), Categories.WILDERNESS),
    THERMONUCLEAR("Smoke Devil", 499, new Position(2388, 4692), Categories.BOSSES),
    LVL2CRAZYMAN("Crazy Man", 3, new Position(1959, 4759), Categories.BOSSES),

    /** Page five - PKing **/
    GREEN_DRAGONS("Green Dragons", 941, new Position(3351, 3680), Categories.WILDERNESS),
    GRAVEYARD("Graveyard", 2, new Position(3166, 3682), Categories.WILDERNESS),
    GREATER_DEMONS("Greater Demons", 2, new Position(3288, 3886), Categories.WILDERNESS),
    WILDERNESS_CASTLE("Wildy Castle", 2, new Position(3005, 3631), Categories.WILDERNESS),
    WEST_DRAGONS("West Dragons", 941, new Position(2980, 3599), Categories.WILDERNESS),
    EAST_DRAGONS("East Dragons", 941, new Position(3339, 3667), Categories.WILDERNESS),
    CHAOS_ALTER("Chaos Alter", 5621, new Position(3240, 3620), Categories.WILDERNESS),
    WILDY_MINING("Wildy Mining", 5497, new Position(3061, 3886), Categories.WILDERNESS),
    REVS("Revs", 6691, new Position(3651, 3486), Categories.WILDERNESS),
    ROGUES_CASTLE("Rogues Castle", 3286, new Position(3286, 3922), Categories.WILDERNESS),
    ICE_PLATEAU("Ice Plateau", 51, new Position(2953, 3901), Categories.WILDERNESS),
    FUN_PVP("Fun PVP", 51, new Position(2953, 3901), Categories.MINIGAMES),

    /** Page six - Dungeons **/
    EDGEVILLVE("Edge Dungeon", 90, new Position(3097, 9870), Categories.MISC),
    CHAOS_TUNNELS("Chaos Tunnels", 5393, new Position(3185, 5471), Categories.MISC),
    BRIMHAVEN("Brim Dungeon", 112, new Position(2713, 9564), Categories.MISC),
    TAVERLEY("Tav Dungeon", 55, new Position(2884, 9796), Categories.MISC),
    STRYKEWORMS("Strykewyrms", 9467, new Position(2731, 5095), Categories.MONSTER),
    ANCIENT_CAVERN("Ancient Cavern", 749, new Position(1745, 5325), Categories.MISC),
    METAL_DRAGONS("Metal Dragons", 1591, new Position(2711, 9464), Categories.MONSTER),
    APE_ATOLL("Ape Atoll", 1471, new Position(2804, 9146), Categories.MISC),
    SLAYER_TOWER("Slayer Tower", 1657, new Position(3429, 3538), Categories.SKILLING),
    /** Fin **/















    ;

    private final String name;
    private final int npcId;
    private final Position location;
    private final Categories category;
}
