package com.janus.world.content.combat.tieredbosses;

public enum StatSetups {
    ;

    public enum firstWave {

        KBD(70, 70, 70, 1, 1, 990, 770),
        BARRELCHEST(99, 99, 99, 99, 99, 990, 990),
        EXAMPLE_NPC_2(99, 99, 99, 99, 99, 990, 990);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        firstWave(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
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

    public enum secondWave {

        KBD(80, 80, 80, 80, 80, 880, 880),
        EXAMPLE_NPC_1(99, 99, 99, 99, 99, 990, 990),
        EXAMPLE_NPC_2(99, 99, 99, 99, 99, 990, 990);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        secondWave(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
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

    public enum thirdWave {

        KBD(70, 70, 99, 70, 70, 770, 770),
        EXAMPLE_NPC_1(99, 99, 99, 99, 99, 990, 990),
        EXAMPLE_NPC_2(99, 99, 99, 99, 99, 990, 990);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        thirdWave(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
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

    public enum fourthWave {

        KBD(1, 60, 1, 60, 1, 550, 660),
        EXAMPLE_NPC_1(99, 99, 99, 99, 99, 990, 990),
        EXAMPLE_NPC_2(99, 99, 99, 99, 99, 990, 990);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        fourthWave(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
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

    public enum fifthWave {

        KBD(1, 30, 1, 1, 59, 500, 500),
        EXAMPLE_NPC_1(99, 99, 99, 99, 99, 990, 990),
        EXAMPLE_NPC_2(99, 99, 99, 99, 99, 990, 990);

        private final int attack;
        private final int defence;
        private final int strength;
        private final int ranged;
        private final int magic;
        private final int constitution;
        private final int prayer;

        fifthWave(int attack, int defence, int strength, int ranged, int magic, int constitution, int prayer) {
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
