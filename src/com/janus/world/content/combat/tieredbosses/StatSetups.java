package com.janus.world.content.combat.tieredbosses;

public enum StatSetups {
    ;

    public enum zeroSetup {

        KBD(99, 99, 99, 99, 99, 990, 990);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        zeroSetup(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
            this.attack = attack;
            this.defence = defence;
            this.strength = strength;
            this.ranged = ranged;
            this.magic = magic;
            this.constitution = constitution;
            this.prayer = prayer;
        }

        public int getAttack() {
            return attack;
        }

        public int getDefence() {
            return defence;
        }

        public int getStrength() {
            return strength;
        }

        public int getRanged() {
            return ranged;
        }

        public int getMagic() {
            return magic;
        }

        public int getConstitution() {
            return constitution;
        }

        public int getPrayer() {
            return prayer;
        }


    }

    public enum oneSetup {

        KBD(80, 80, 80, 80, 80, 880, 880);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        oneSetup(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
            this.attack = attack;
            this.defence = defence;
            this.strength = strength;
            this.ranged = ranged;
            this.magic = magic;
            this.constitution = constitution;
            this.prayer = prayer;
        }

        public int getAttack() {
            return attack;
        }

        public int getDefence() {
            return defence;
        }

        public int getStrength() {
            return strength;
        }

        public int getRanged() {
            return ranged;
        }

        public int getMagic() {
            return magic;
        }

        public int getConstitution() {
            return constitution;
        }

        public int getPrayer() {
            return prayer;
        }


    }

    public enum twoSetup {

        KBD(70, 70, 99, 70, 70, 770, 770);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        twoSetup(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
            this.attack = attack;
            this.defence = defence;
            this.strength = strength;
            this.ranged = ranged;
            this.magic = magic;
            this.constitution = constitution;
            this.prayer = prayer;
        }

        public int getAttack() {
            return attack;
        }

        public int getDefence() {
            return defence;
        }

        public int getStrength() {
            return strength;
        }

        public int getRanged() {
            return ranged;
        }

        public int getMagic() {
            return magic;
        }

        public int getConstitution() {
            return constitution;
        }

        public int getPrayer() {
            return prayer;
        }


    }

    public enum threeSetup {

        KBD(1, 60, 1, 60, 1, 550, 660);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        threeSetup(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
            this.attack = attack;
            this.defence = defence;
            this.strength = strength;
            this.ranged = ranged;
            this.magic = magic;
            this.constitution = constitution;
            this.prayer = prayer;
        }

        public int getAttack() {
            return attack;
        }

        public int getDefence() {
            return defence;
        }

        public int getStrength() {
            return strength;
        }

        public int getRanged() {
            return ranged;
        }

        public int getMagic() {
            return magic;
        }

        public int getConstitution() {
            return constitution;
        }

        public int getPrayer() {
            return prayer;
        }


    }

    public enum fourSetup {

        KBD(1, 30, 1, 1, 59, 500, 500);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        fourSetup(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
            this.attack = attack;
            this.defence = defence;
            this.strength = strength;
            this.ranged = ranged;
            this.magic = magic;
            this.constitution = constitution;
            this.prayer = prayer;
        }

        public int getAttack() {
            return attack;
        }

        public int getDefence() {
            return defence;
        }

        public int getStrength() {
            return strength;
        }

        public int getRanged() {
            return ranged;
        }

        public int getMagic() {
            return magic;
        }

        public int getConstitution() {
            return constitution;
        }

        public int getPrayer() {
            return prayer;
        }


    }


}
