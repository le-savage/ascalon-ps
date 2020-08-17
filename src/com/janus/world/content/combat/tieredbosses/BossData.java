package com.janus.world.content.combat.tieredbosses;

public enum BossData {

    KING_BLACK_DRAGON(50 ,3, 40, 10, 50);

    private int level1;
    private int level2;
    private int level3;
    private int level4;
    private int level5;

    BossData(int level1, int level2, int level3, int level4, int level5) {
        this.level1 = level1;
        this.level2 = level2;
        this.level3 = level3;
        this.level4 = level4;
        this.level5 = level5;
    }

    public int getLevel1ID() {
        return level1;
    }
    public int getLevel2ID() {
        return level2;
    }
    public int getLevel3ID() {
        return level3;
    }
    public int getLevel4ID() {
        return level4;
    }
    public int getLevel5ID() {
        return level5;
    }

}
