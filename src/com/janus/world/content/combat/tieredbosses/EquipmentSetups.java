package com.janus.world.content.combat.tieredbosses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EquipmentSetups {

    //Default used in case of errors
    DEFAULT(-1, -1, -1 ,-1, -1, -1,-1, -1, -1),

    //First
    FROST_DRAGON(13899, 1540, 10828 ,13887, 13893, 6585,19111, 7462, 13239),
    BLACK_DRAGON(13899, 1540, 10828 ,13887, 13893, 6585,19111, 7462, 13239),
    KBD_WAVE_ONE(13899, 1540, 10828 ,13887, 13893, 6585,19111, 7462, 13239),
    //Second
    TORMENTED_DEMON(13899, 1540, 10828 ,13887, 13893, 6585,19111, 7462, 13239),
    CHAOS_ELEMENTAL(13899, 1540, 10828 ,13887, 13893, 6585,19111, 7462, 13239),
    DAGANNOTH_PRIME(13899, 1540, 10828 ,13887, 13893, 6585,19111, 7462, 13239),
    //Third
    DAGANNOTH_SUPREME(1333,-1, 1163, 1127, 1079, 1725, 6570, 7460, 4131),
    BARRELCHEST(1329, -1, 1159, 1121, 1071, 1725, 1007, 7458, 4127),
    CERBERUS(861, -1, 6326, 2501, 2495, 15126, 10499, 2489, 6328),
    //Fourth
    THERMONUCLEAR_SMOKEDEVIL(1333,-1, 1163, 1127, 1079, 1725, 6570, 7460, 4131),
    KBD_WAVE_TWO(1329, -1, 1159, 1121, 1071, 1725, 1007, 7458, 4127),
    DAGANNOTH_REX(861, -1, 6326, 2501, 2495, 15126, 10499, 2489, 6328),
    //Fifth
    CRAZY_LVL2_MAN(1333,-1, 1163, 1127, 1079, 1725, 6570, 7460, 4131),
    KALPHITE_QUEEN(1329, -1, 1159, 1121, 1071, 1725, 1007, 7458, 4127),
    BORK(861, -1, 6326, 2501, 2495, 15126, 10499, 2489, 6328);

    private final int weapon;
    private final int shield;
    private final int helm;
    private final int body;
    private final int legs;
    private final int neck;
    private final int cape;
    private final int hands;
    private final int feet;

}
