package com.janus.world.content.combat.tieredbosses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatSetups {

        //Default used in case of errors
        DEFAULT(1, 1, 1, 1, 1, 10, 10),

        //First
        FROST_DRAGON(99, 99, 99, 99, 99, 990, 990),
        BLACK_DRAGON(99, 99, 99, 99, 99, 990, 990),
        KBD_WAVE_ONE(70, 70, 70, 1, 1, 990, 770),
        //Second Wave
        TORMENTED_DEMON(80, 80, 80, 80, 80, 880, 880),
        CHAOS_ELEMENTAL(99, 99, 99, 99, 99, 990, 990),
        DAGANNOTH_PRIME(99, 99, 99, 99, 99, 990, 990),
        //Third
        DAGANNOTH_SUPREME(80, 80, 80, 80, 80, 880, 880),
        BARREL_CHEST(99, 99, 99, 99, 99, 990, 990),
        CERBERUS(99, 99, 99, 99, 99, 990, 990),
        //Fourth
        THERMONUCLEAR_SMOKEDEVIL(80, 80, 80, 80, 80, 880, 880),
        KBD_WAVE_TWO(99, 99, 99, 99, 99, 990, 990),
        DAGANNOTH_REX(99, 99, 99, 99, 99, 990, 990),
        //Fifth
        CRAZY_LVL2_MAN(80, 80, 80, 80, 80, 880, 880),
        KALPHITE_QUEEN(99, 99, 99, 99, 99, 990, 990),
        BORK(99, 99, 99, 99, 99, 990, 990);


        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

}
