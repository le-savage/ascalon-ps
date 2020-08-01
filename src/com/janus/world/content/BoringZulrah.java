package com.janus.world.content;

import com.janus.model.Position;
import com.janus.model.RegionInstance;
import com.janus.model.RegionInstance.RegionInstanceType;
import com.janus.world.World;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

public class BoringZulrah {

    public static NPC BoringZulrah;


    static Player getPlayer;


    public static void despawn(Player player) {
        World.deregister(BoringZulrah);

    }

    public static void spawn(Player player) {
        getPlayer = player;
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.BORINGZULRAHZONE));
        BoringZulrah = new NPC(2044, new Position(2268, 3072, player.getIndex() * 4));

        World.register(BoringZulrah);

    }


}

