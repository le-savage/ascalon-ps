package com.janus.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public enum MaxCapeDropRate {

    Default(0),
    Easy(2),
    Medium(4),
    Hard(5),
    Insane(6),
    Zezima(7);

    private int drBoost;

    MaxCapeDropRate(int drBoost) {
        this.drBoost = drBoost;
    }

    //todo finish adding max cape DR bonus for each difficulty
    public double getDropRateModifier() {
        return drBoost;
    }

}


