package com.janus.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public enum Difficulty {

    Default(0, 0),
    Easy(300, 0),
    Medium(100, 2),
    Hard(-50, 5),
    Insane(-150, 10),
    Zezima(-300, 20);

    private int xpBoost, drBoost;

    Difficulty(int xpBoost, int drBoost) {
        this.xpBoost = xpBoost;
        this.drBoost = drBoost;
    }

    private static final ImmutableSet<Difficulty> LOW = Sets.immutableEnumSet(Default, Easy, Medium);
    private static final ImmutableSet<Difficulty> HIGH = Sets.immutableEnumSet(Hard, Insane, Zezima);

    public boolean lowDifficulty() {
        return LOW.contains(this);
    }

    public boolean highDifficulty() {
        return HIGH.contains(this);
    }

    public double getDropRateModifier() {
        return drBoost;
    }

}
