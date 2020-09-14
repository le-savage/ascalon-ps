package com.janus.world.content.teleport;

import com.janus.model.definitions.NpcDefinition;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class TeleportRepository {
    public static List<TeleportData> teleportDataList = new ArrayList<>();
    public static void loadData() {
        Collections.addAll(teleportDataList, TeleportData.values());
    }
    public static List<TeleportData> filterByCategory(Categories category) {
        List<TeleportData> list = new ArrayList<>();
        teleportDataList.forEach(data -> {
            if(data.getCategory() == category)
                list.add(data);
        });
        list.sort(Comparator.comparingInt(o -> NpcDefinition.forId(o.getNpcId()).getCombatLevel()));
        return list;
    }
}
