package com.janus.world.content;

import com.janus.model.Item;
import com.janus.model.container.impl.Inventory;
import com.janus.world.entity.impl.player.Player;

public class CombineNightmare {

    public static void combineOrbs(Item item, Player player) {
        
        int orbUsed = item.getId();
        Inventory inv = player.getInventory();
        
        if (inv.contains(21054)) {
            if (orbUsed == 21058) {
                inv.delete(21054, 1);
                inv.delete(21058, 1);
                inv.add(21055, 1);
            }
            if (orbUsed == 21059) {
                inv.delete(21054, 1);
                inv.delete(21059, 1);
                inv.add(21056, 1);
            }
            if (orbUsed == 21060) {
                inv.delete(21054, 1);
                inv.delete(21060, 1);
                inv.add(21057, 1);
            }
        }
    }
}
