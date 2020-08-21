package com.janus.world.content.combat.tieredbosses;

public enum BossNPCData {

    KING_BLACK_DRAGON(50, 50, 50, 50, 50);

    private int tierZero;
    private int tierOne;
    private int tierTwo;
    private int tierThree;
    private int tierFour;

    BossNPCData(int tierZero, int tierOne, int tierTwo, int tierThree, int tierFour) {
        this.tierZero = tierZero;
        this.tierOne = tierOne;
        this.tierTwo = tierTwo;
        this.tierThree = tierThree;
        this.tierFour = tierFour;
    }

    public int getLevel1ID() {
        return tierZero;
    }

    public int getLevel2ID() {
        return tierOne;
    }

    public int getLevel3ID() {
        return tierTwo;
    }

    public int getLevel4ID() {
        return tierThree;
    }

    public int getLevel5ID() {
        return tierFour;
    }

}
