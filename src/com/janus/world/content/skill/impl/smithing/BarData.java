package com.janus.world.content.skill.impl.smithing;

public class BarData {

    public static enum Bars {

        Bronze(300, 2349),
        Iron(400, 2351),
        Steel(500, 2353),
        Mithril(1000, 2359),
        Adamant(1300, 2361),
        Rune(1700, 2363),
        Dragon(2000, 21061),
        Barrows(5000, 21062),
        Armadyl(5000, 21063),
        Bandos(5000, 21064),
        Third_Age(9000, 21065),
        Torva(9000, 21066);

        Bars(int exp, int itemId) {
            this.exp = exp;
            this.itemId = itemId;
        }

        private int exp, itemId;

        public int getExp() {
            return exp;
        }

        public int getItemId() {
            return itemId;
        }
    }


}
