package com.janus.world.content.teleport;


import com.janus.model.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TeleportData {
    SMOKE_DEVILS("Smoke Devils", 498, new Position(2404, 9415), Categories.MONSTER),
    ANCIENT_CAVERNS("Ancient Caverns", 2919, new Position(1748, 5322), Categories.MONSTER),
    ASGARNIAN_ICE_DUNGEON("Asgarnian Ice Dungeon", 2881, new Position(2994, 9551), Categories.MONSTER),

    ;

    private final String name;
    private final int npcId;
    private final Position location;
    private final Categories category;
}
