package com.janus.world.content.combat.tieredbosses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatSetups {

        //Default used in case of errors
        DEFAULT(1, 1, 1, 1, 1, 10, 10),

        //First
        FROST_DRAGON(80, 50, 60, 1, 15, 990, 70),
        BLACK_DRAGON(90, 60,    20, 50, 99, 990, 70),
        KBD_WAVE_ONE(70, 70, 70, 1, 1, 990, 770),
        //Second Wave
        TORMENTED_DEMON(80, 80, 80, 80, 80, 880, 70),
        CHAOS_ELEMENTAL(80, 80, 80, 80, 99, 990, 70),
        DAGANNOTH_PRIME(80, 80, 80, 80, 80, 990, 990),
        //Third
        DAGANNOTH_SUPREME(80, 80, 80, 80, 80, 880, 70),
        BARREL_CHEST(70, 70, 70, 1, 70, 990, 70),
        CERBERUS(60, 60, 60, 60, 99, 990, 70),
        //Fourth
        THERMONUCLEAR_SMOKEDEVIL(70, 70, 70, 1, 80, 880, 70),
        VENENATIS(70, 70, 70, 99, 99, 80, 80),
        DAGANNOTH_REX(70, 70, 60, 1, 70, 80, 70),
        //Fifth
        CRAZY_LVL2_MAN(80, 80, 80, 80, 80, 880, 70),
        SCORPIA(80, 80, 80, 99, 99, 990, 70),
        BORK(80, 80, 80, 99, 99, 990, 70);


        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

}
