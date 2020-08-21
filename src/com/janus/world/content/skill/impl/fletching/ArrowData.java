package com.janus.world.content.skill.impl.fletching;

public enum ArrowData {

    HEADLESS(52, 314, 53, 25, 1),
    HEADLESS1(52, 10088, 53, 1, 1),
    HEADLESS2(52, 10090, 53, 1, 1),
    HEADLESS3(52, 10091, 53, 1, 1),
    HEADLESS4(52, 10089, 53, 1, 1),
    HEADLESS5(52, 10087, 53, 1, 1),
    BRONZE(53, 39, 882, 80, 1),
    IRON(53, 40, 884, 200, 15),
    STEEL(53, 41, 886, 400, 30),
    MITHRIL(53, 42, 888, 800, 45),
    ADAMANT(53, 43, 890, 1800, 60),
    RUNE(53, 44, 892, 4200, 75),
    DRAGON(53, 11237, 11212, 15, 90);

    public int item1, item2, outcome, xp, levelReq;

    private ArrowData(int item1, int item2, int outcome, int xp, int levelReq) {
        this.item1 = item1;
        this.item2 = item2;
        this.outcome = outcome;
        this.xp = xp;
        this.levelReq = levelReq;
    }

    public static ArrowData forArrow(int id) {
        for (ArrowData ar : ArrowData.values()) {
            if (ar.getItem2() == id) {
                return ar;
            }
        }
        return null;
    }

    public int getItem1() {
        return item1;
    }

    public int getItem2() {
        return item2;
    }

    public int getOutcome() {
        return outcome;
    }

    public int getXp() {
        return xp;
    }

    public int getLevelReq() {
        return levelReq;
    }

}
