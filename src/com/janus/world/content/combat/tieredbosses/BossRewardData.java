package com.janus.world.content.combat.tieredbosses;

import com.janus.model.Item;
import lombok.Data;

@Data
public class BossRewardData {

    /** Rewards Are Stored Here **/
    public static final Item[] SHIT_REWARDS = new Item[]{new Item(123, 1),
            new Item(123, 44),
            new Item(1234, 41)};

    public static final Item[] MEDIUM_REWARDS = new Item[]{new Item(123, 1),
            new Item(123, 44),
            new Item(1234, 41)};

    public static final Item[] RARE_REWARDS = new Item[]{new Item(123, 1),
            new Item(123, 44),
            new Item(1234, 41)};

}
