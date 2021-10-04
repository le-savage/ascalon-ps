package com.AscalonPS.world.content.skill.impl.fletching;

public enum BoltData {


    BRONZE_BOLT(9375, 877, 5, 9),

    BLURITE_BOLT(9376, 9139, 10, 24),

    IRON_BOLT(9377, 9140, 15, 39),

    STEEL_BOLT(9378, 9141, 35, 46),

    MITHRIL_BOLT(9379, 9142, 50, 54),

    ADAMANT_BOLT(9380, 9143, 70, 61),

    RUNITE_BOLT(9381, 9144, 100, 69),

    DRAGON_BOLT(17926, 21029, 120, 84);

    public int unfinishedBolt, finishedBolt, xp, levelReq;

    private BoltData(int unfinishedBolt, int finishedBolt, int xp, int levelReq) {
        this.unfinishedBolt = unfinishedBolt;
        this.finishedBolt = finishedBolt;
        this.xp = xp;
        this.levelReq = levelReq;
    }

    public int getUnfinishedBolt() {
        return unfinishedBolt;
    }

    public int getFinishedBolt() {
        return finishedBolt;
    }

    public int getXp() {
        return xp;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public static BoltData forBolt(int id) {
        for (BoltData ar : BoltData.values()) {
            if (ar.getFinishedBolt() == id) {
                return ar;
            }
        }
        return null;
    }

}
