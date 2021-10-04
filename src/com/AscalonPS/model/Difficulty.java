package com.AscalonPS.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public enum Difficulty {

    Default(0, 0, ""),
    Easy(3, 0, "<col=00e62b>"),
    Medium(1, 1, "<col=ad820a>"),
    Hard(0.5, 2, "<col=f76472>"),
    Insane(0.25, 3, "<col=d81124>"),
    Zezima(0.1, 5, "<col=ff031b>"); //TODO change DR

    private double xpRate;
    private int drBoost;
    private String maxAlertColour;

    private static final ImmutableSet<Difficulty> LOW = Sets.immutableEnumSet(Default, Easy, Medium);
    private static final ImmutableSet<Difficulty> HIGH = Sets.immutableEnumSet(Hard, Insane, Zezima);
    Difficulty(double xpBoost, int drBoost, String maxAlertColour) {
        this.xpRate = xpBoost;
        this.drBoost = drBoost;
        this.maxAlertColour = maxAlertColour;
    }

    public boolean lowDifficulty() {
        return LOW.contains(this);
    }

    public boolean highDifficulty() {
        return HIGH.contains(this);
    }

    public double getDropRateModifier() {
        return drBoost;
    }

    public double getXpRate() {
        return xpRate;
    }

    public String getAlertColour() {
        return maxAlertColour;
    }

}
