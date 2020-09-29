package com.janus.world.content.combat.tieredbosses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EquipmentSetups {

    ZERO(13899, 1540, 10828 ,13887, 13893, 6585,19111, 7462, 13239),
    ONE(1333,-1, 1163, 1127, 1079, 1725, 6570, 7460, 4131),
    TWO(1329, -1, 1159, 1121, 1071, 1725, 1007, 7458, 4127),//Hardest
    THREE(861, -1, 6326, 2501, 2495, 15126, 10499, 2489, 6328),//range
    FOUR(1381, -1, 4089, 4091, 4093, -1, 9762, 4095, 4097);//magic

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
