package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Item;

public enum Rewards {

    FIRST(0, new Item[]{new Item(3755), new Item(1704), new Item(4091)}),
    SECOND(1, new Item[]{new Item(3755), new Item(1704), new Item(4091)}),
    THIRD(2, new Item[]{new Item(3755), new Item(1704), new Item(4091)}),
    FOURTH(3, new Item[]{new Item(3755), new Item(1704), new Item(4091)}),
    FIFTH(4, new Item[]{new Item(3755), new Item(1704), new Item(4091)});

    Rewards(int tier, Item[] items) {
        this.tier = tier;
        this.items = items;
    }

    public int tier;
    public Item[] items;
}

