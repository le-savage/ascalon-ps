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

    GLACORS_CAVE("Glacors", 1382, new Position(3050, 9573), Categories.BOSSES),









    ;

    private final String name;
    private final int npcId;
    private final Position location;
    private final Categories category;
}
