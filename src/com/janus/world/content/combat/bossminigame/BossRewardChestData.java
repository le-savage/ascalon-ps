package com.janus.world.content.combat.bossminigame;

import com.janus.model.Item;
import com.janus.util.Misc;
import lombok.Data;

@Data
public class BossRewardChestData {

    /** Rewards Are Stored Here **/
    public static final Item[] SHIT_REWARDS = {
            // Less Than 7m
            new Item(123, 1),
            new Item(537, Misc.random(30, 300)),
            new Item(554, Misc.random(3, 200)), //Fire Rune
            new Item(555, Misc.random(3, 200)), //Water Rune
            new Item(557, Misc.random(3, 200)), //Earth Rune
            new Item(558, Misc.random(3, 200)), //Mind Rune
            new Item(559, Misc.random(3, 200)), //Body Rune
            new Item(556, Misc.random(3, 200)), //Air Rune
            new Item(562, Misc.random(3, 200)), //Chaos Rune
            new Item(563, Misc.random(3, 200)), //Law Rune
            new Item(380, Misc.random(10, 200)), //Lobster
            new Item(374, Misc.random(10, 200)), //Swordie
            new Item(7947, Misc.random(10, 200)), //Monk
            new Item(386, Misc.random(10, 200)), //Shark
            new Item(392, Misc.random(10, 200)), //Manta
            new Item(2443, Misc.random(1, 30)), //Defence Pot
            new Item(2437, Misc.random(1, 30)), //Attack Pot
            new Item(2441, Misc.random(1, 30)), //Strength Pot
            new Item(3041, Misc.random(1, 30)), //Magic Pot
            new Item(4131, 1), //Rune Boots
            new Item(1725, 1),
            new Item(1540, 1),
            new Item(3751, 1),
            new Item(1712, 1),
            new Item(13734, 1),
            new Item(11126, 1),
            new Item(6524, 1),
            new Item(533, Misc.random(100, 500)), //Start Arrows and shit
            new Item(882, Misc.random(100, 500)),
            new Item(884, Misc.random(100, 500)),
            new Item(886, Misc.random(100, 500)),
            new Item(888, Misc.random(100, 500)),
            new Item(890, Misc.random(100, 500)),
            new Item(892, Misc.random(100, 500)),
            new Item(9142, Misc.random(100, 500)),
            new Item(9143, Misc.random(100, 500)),
            new Item(9144, Misc.random(100, 500)),
            new Item(867, Misc.random(100, 500)),
            new Item(868, Misc.random(100, 500)),
            new Item(810, Misc.random(100, 500)),
            new Item(811, Misc.random(100, 500)),
            new Item(11230, Misc.random(100, 500)), //Dragon Dart
            new Item(841, 1), //Shortbow
            new Item(9185, 1), //Rune C Bow
            new Item(1129, 1), //Leather Body
            new Item(3749, 1), //Archer Helm
            new Item(2499, 1), //Blue D hide
            new Item(2493, 1), //Blue D hide
            new Item(2487, 1), //Blue D hide
            new Item(2501, 1), //Red D hide
            new Item(2495, 1), //Red D hide
            new Item(2489, 1), //Red D hide
            new Item(2503, 1), //Black D hide
            new Item(2497, 1), //Black D hide
            new Item(2491, 1), //Black D hide
            new Item(1381, 1), //Air Staff
            new Item(1383, 1), //Water Staff
            new Item(1385, 1), //Earth Staff
            new Item(1387, 1), //Fire Staff
            new Item(4675, 1), //Ancient Staff
            new Item(4089, 1), //Mystic Blue
            new Item(4091, 1), //Mystic Blue
            new Item(4093, 1), //Mystic Blue
            new Item(4095, 1), //Mystic Blue
            new Item(4097, 1), //Mystic Blue
            new Item(4099, 1), //Mystic Black
            new Item(4101, 1), //Mystic Black
            new Item(4103, 1), //Mystic Black
            new Item(4105, 1), //Mystic Black
            new Item(4107, 1), //Mystic Black
            new Item(4109, 1), //Mystic White
            new Item(4111, 1), //Mystic White
            new Item(4113, 1), //Mystic White
            new Item(4115, 1), //Mystic White
            new Item(4117, 1), //Mystic White


    };

    public static final Item[] MEDIUM_REWARDS = {
            // Less than 50m
            new Item(4151, 1),
            new Item(123, 1),
            new Item(4716, 1),
            new Item(4718, 1),
            new Item(4720, 1),
            new Item(4722, 1),
            new Item(4724, 1),
            new Item(4726, 1),
            new Item(4728, 1),
            new Item(4730, 1),
            new Item(7937, 500),
            new Item(13887, 1),
            new Item(13893, 1),
            new Item(15018, 1),
            new Item(15019, 1),
            new Item(15020, 1),
            new Item(990, 35),
            new Item(6585, 1),
            new Item(533, 250),
            new Item(892, 300),
            new Item(1620, 350),
            new Item(1624, 500),
            new Item(890, 700),
            new Item(10025, 3),
            new Item(11212, Misc.random(500,2500))

    };

    public static final Item[] RARE_REWARDS = {
            new Item(123, 1),
            new Item(18891, 1),
            new Item(18892, 1),
            new Item(18893, 1),
            new Item(18900, 1),
            new Item(14008, 1),
            new Item(14010, 1),
            new Item(14011, 1),
            new Item(14012, 1),
            new Item(14013, 1),
            new Item(14014, 1),
            new Item(14015, 1),
            new Item(14016, 1),
            new Item(16711, 1),
            new Item(17259, 1),
            new Item(16689, 1),
            new Item(17361, 1),
            new Item(16359, 1),
            new Item(16293, 1),
            new Item(18899, 1),
            new Item(20998, 1),
            new Item(10350, 1),
            new Item(10348, 1),
            new Item(10346, 1),
            new Item(10352, 1),
            new Item(14009, 1)};

}
