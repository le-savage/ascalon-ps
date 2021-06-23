package com.janus.world.content.combat.tieredbosses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatSetups {

        //Default used in case of errors
        DEFAULT(1, 1, 1, 1, 1, 10, 10),

        //First
        FROST_DRAGON(80, 50, 60, 1, 15, 990, 990),
        BLACK_DRAGON(90, 60,    20, 50, 99, 990, 990),
        KBD_WAVE_ONE(70, 70, 70, 1, 1, 990, 770),
        //Second Wave
        TORMENTED_DEMON(80, 80, 80, 80, 80, 880, 880),
        CHAOS_ELEMENTAL(92, 95, 90, 99, 99, 990, 990),
        DAGANNOTH_PRIME(93, 91, 9, 99, 99, 990, 990),
        //Third
        DAGANNOTH_SUPREME(80, 80, 80, 80, 80, 880, 880),
        BARREL_CHEST(99, 99, 99, 99, 99, 990, 990),
        CERBERUS(60, 60, 60, 60, 99, 990, 990),
        //Fourth
        THERMONUCLEAR_SMOKEDEVIL(40, 20, 10, 80, 80, 880, 880),
        KBD_WAVE_TWO(99, 99, 99, 99, 99, 990, 990),
        DAGANNOTH_REX(10, 30, 25, 99, 99, 990, 990),
        //Fifth
        CRAZY_LVL2_MAN(99, 80, 80, 80, 80, 880, 880),
        SCORPIA(99, 99, 99, 99, 99, 990, 990),
        BORK(99, 15, 10, 99, 99, 990, 990);


        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

}
