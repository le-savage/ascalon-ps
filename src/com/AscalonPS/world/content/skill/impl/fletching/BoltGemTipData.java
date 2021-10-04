package com.AscalonPS.world.content.skill.impl.fletching;

/**
 * Created by Flub
 */
public enum BoltGemTipData {
    OPAL(877, 45, 879, 2, 11, 10),
    JADE(9139, 9187, 9335, 2, 26, 10),
    PEARL(9140, 46, 880, 3, 41, 10),
    TOPAZ(9141, 9188, 9336, 3, 56, 10),
    SAPPHIRE(9142, 9189, 9337, 4, 56, 10),
    EMERALD(9142, 9190, 9338, 6, 58, 10),
    RUBY(9143, 9191, 9339, 7, 63, 10),
    DIAMOND(9143, 9192, 9340, 8, 65, 10),
    DRAGONSTONE(9144, 9193, 9341, 9, 71, 10),
    ONYX(9144, 9194, 9342, 10, 73, 10);
/*DRAGON_OPAL(21029, 45, 21027, 2, 84, 10),
DRAGON_JADE(21029, 9187, 21033, 2, 84, 10),
DRAGON_PEARL(21029, 46, 21034, 3, 84, 10),
DRAGON_TOPAZ(21029, 9188, 21035, 3, 84, 10),
DRAGON_SAPPHIRE(21029, 9189, 21036, 4, 84, 10),
DRAGON_EMERALD(21029, 9190, 21037, 6, 84, 10),
DRAGON_RUBY(21029, 9191, 21038, 7, 84, 10),
DRAGON_DIAMOND(21029, 9192, 21039, 8, 84, 10),
DRAGON_DRAGONSTONE(21029, 9193, 21040, 9, 84, 10),
DRAGON_ONYX(21029, 9194, 21041, 10, 84, 10 );*/

    private int bolt, tip, outcome, levelReq, amount, xp;

    private BoltGemTipData(int bolt, int tip, int outcome, int xp, int levelReq, int amount) {
        this.bolt = bolt;
        this.tip = tip;
        this.outcome = outcome;
        this.xp = xp;
        this.levelReq = levelReq;
        this.amount = amount;
    }


    public int getBolt() {
        return bolt;
    }

    public int getTip() {
        return tip;
    }

    public int getOutcome() {
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

    public static BoltGemTipData forBolts(int id) {
        for (BoltGemTipData bolt : BoltGemTipData.values()) {
            if (bolt.getBolt() == id) {
                return bolt;
            }
        }
        return null;
    }

    public static BoltGemTipData forTip(int id) {
        for (BoltGemTipData bolt : BoltGemTipData.values()) {
            if (bolt.getTip() == id) {
                return bolt;
            }
        }
        return null;
    }
}


