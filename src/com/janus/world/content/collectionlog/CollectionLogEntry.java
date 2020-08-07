package com.janus.world.content.collectionlog;

import com.janus.world.entity.impl.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CollectionLogEntry {
    private int npcId;
    private int item;
    private int amount;

    public void submit(Player player) {
        player.getCollectionLogData().add(this);
    }
}
