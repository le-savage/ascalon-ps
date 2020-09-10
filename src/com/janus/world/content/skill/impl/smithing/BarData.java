package com.janus.world.content.skill.impl.smithing;

public class BarData {

    public static enum Bars {

        Bronze(130, 2349),
        Iron(250, 2351),
        Steel(380, 2353),
        Mithril(500, 2359),
        Adamant(630, 2361),
        Rune(750, 2363),
        Dragon(1000, 21061),
        Barrows(3000, 21062),
        Armadyl(3000, 21063),
        Bandos(3000, 21064),
        Third_Age(5000, 21065),
        Torva(5000, 21066);

        private int exp, itemId;

        Bars(int exp, int itemId) {
            this.exp = exp;
            this.itemId = itemId;
        }

        public int getExp() {
            return exp;
        }

        public int getItemId() {
            return itemId;
        }
    }


}
