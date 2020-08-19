package com.janus.world.content.combat.tieredbosses;

public enum EquipmentSetups {

    ONE(18899, -1, 18891, 18892, 18893, 17291, 14022, 15946, 13239),//Easiest
    TWO(18899, -1, 18891, 18892, 18893, 17291, 14022, 15946, 13239),
    THREE(18899, -1, 18891, 18892, 18893, 17291, 14022, 15946, 13239),
    FOUR(18899, -1, 18891, 18892, 18893, 17291, 14022, 15946, 13239),
    FIVE(1321, 1189, 1155, 1117, 1075, 1725, 14642, 775, 1837);//Hardest

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
