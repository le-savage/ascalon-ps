package com.janus.world.content.combat.tieredbosses;

public enum EquipmentSetups {

    ZERO(13899, 11283, 10828 ,13887, 13893, 6585,19111, 7462, 13239),
    ONE(1333,1201, 1163, 1127, 1079, 1725, 6570, 7460, 4131),
    TWO(1325, 1193, 1157, 1119, 1069, 1725, 1007, 7456, 4123),//Hardest
    THREE(11235, -1, 11664, 8839, 8840, 15126, 10499, 8842, 2577),//range
    FOUR(13058, 1540, 4089, 4091, 4093, 10366, 9762, 4095, 4097);//magic

    private final int weapon;
    private final int shield;
    private final int helm;
    private final int body;
    private final int legs;
    private final int neck;
    private final int cape;
    private final int hands;
    private final int feet;

    EquipmentSetups(int weapon, int shield, int helm, int body, int legs, int neck, int cape, int hands, int feet) {
        this.weapon = weapon;
        this.shield = shield;
        this.helm = helm;
        this.body = body;
        this.legs = legs;
        this.neck = neck;
        this.cape = cape;
        this.hands = hands;
        this.feet = feet;
    }

    public int getWeapon() {
        return weapon;
    }

    public int getShield() {
        return shield;
    }

    public int getHelm() {
        return helm;
    }

    public int getBody() {
        return body;
    }

    public int getLegs() {
        return legs;
    }

    public int getNeck() {
        return neck;
    }

    public int getCape() {
        return cape;
    }

    public int getHands() {
        return hands;
    }

    public int getFeet() {
        return feet;
    }


}
