package com.AscalonPS.world.content.skill.impl.fletching;

public enum DragonBoltsData {


    /**
     * Created by Flub - Fuck yeaaaa
     */

    DRAGON_OPAL(21029, 45, 21027, 2, 84, 10),
    DRAGON_JADE(21029, 9187, 21033, 2, 84, 10),
    DRAGON_PEARL(21029, 46, 21034, 3, 84, 10),
    DRAGON_TOPAZ(21029, 9188, 21035, 3, 84, 10),
    DRAGON_SAPPHIRE(21029, 9189, 21036, 4, 84, 10),
    DRAGON_EMERALD(21029, 9190, 21037, 6, 84, 10),
    DRAGON_RUBY(21029, 9191, 21038, 7, 84, 10),
    DRAGON_DIAMOND(21029, 9192, 21039, 8, 84, 10),
    DRAGON_DRAGONSTONE(21029, 9193, 21040, 9, 84, 10),
    DRAGON_ONYX(21029, 9194, 21041, 10, 84, 10);

    private int bolt, tip, outcome, levelReq, amount, xp;

    private DragonBoltsData(int bolt, int tip, int outcome, int xp, int levelReq, int amount) {
        this.bolt = bolt;
        this.tip = tip;
        this.outcome = outcome;
        this.xp = xp;
        this.levelReq = levelReq;
        this.amount = amount;
    }


    public int getDragonBolt() {
        return bolt;
    }

    public int getTip() {
        return tip;
    }

    public int getDragonOutcome() {
        return outcome;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getAmount() {
        return amount;
    }

    public int getXp() {
        return xp;
    }


    public static DragonBoltsData forBolts(int id) {
        for (DragonBoltsData bolt : DragonBoltsData.values()) {
            if (bolt.getDragonBolt() == id) {
                return bolt;
            }
        }
        return null;
    }

    public static DragonBoltsData forTip(int id) {
        for (DragonBoltsData bolt : DragonBoltsData.values()) {
            if (bolt.getTip() == id) {
                return bolt;
            }
        }
        return null;
    }
}




