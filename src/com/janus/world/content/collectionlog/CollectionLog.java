package com.janus.world.content.collectionlog;

import com.janus.model.definitions.NPCDrops;
import com.janus.model.definitions.NpcDefinition;
import com.janus.model.input.Input;
import com.janus.util.Misc;
import com.janus.world.content.KillsTracker;
import com.janus.world.entity.impl.player.Player;
import lombok.RequiredArgsConstructor;
import lombok.var;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CollectionLog {
    private final int[] NPC_LIST = new int[]{1265, 1023, 1233, 1234, 13479, 13478, 13474, 13747, 12343, 12886, 10103, 606, 607, 1614,
            603, 12843, 53, 8018, 13635, 8008, 3308, 3117, 201, 203, 8010, 252, 449, 452, 2341, 3831, 185, 6430, 187, 3779, 3334, 8013, 12239, 3830};
    private final Player player;
    private List<Integer> currentlyViewing = new ArrayList<>();

    public void open() {
        initialiseCurrentlyViewing();
        sendBossNames();
        sendNpcData(0);
        player.getPA().sendInterface(30360);
    }

    private void sendBossNames() {
        for (int i = 30560; i <= 30627; i++) {
            player.getPA().sendString(i, "");
        }
        int[] startingLine = new int[]{30560};
        currentlyViewing.forEach(entry -> {
            var def = NpcDefinition.forId(entry);
            if (def != null)
                player.getPA().sendString(startingLine[0]++, def.getName());
        });
    }

    private void initialiseCurrentlyViewing() {
        currentlyViewing.clear();
        System.out.println(NPCDrops.getDrops().size());
        NPCDrops.getDrops().forEach((npc, drop) -> {
            if (npc > 0) {
                if (NpcDefinition.forId(npc) != null) {
                    currentlyViewing.add(npc);
                }
            }
        });
    }

    public void search(String name) {
        initialiseCurrentlyViewing();
        var tempList = new ArrayList<Integer>();
        for (int data : currentlyViewing) {
            if (Objects.nonNull(NpcDefinition.forId(data))) {
                if (!NpcDefinition.forId(data).getName().toLowerCase().contains(name.toLowerCase()))
                    tempList.add(data);
            } else {
                tempList.add(data);
            }
        }
        currentlyViewing.removeAll(tempList);
        for (int i = 0; i < 100; i++) {
            player.getPacketSender().sendString(30560 + i, "");
        }
        sendBossNames();
    }

    private boolean hasObtainedItem(int npc, int item) {
        return player.getCollectionLogData().stream().filter(data -> data.getNpcId() == npc && data.getItem() == item).findFirst().isPresent();
    }

    private void sendNpcData(int index) {
        var definition = NpcDefinition.forId(currentlyViewing.get(index));
        player.getPacketSender().sendNpcOnInterface(30367, definition.getId());
        player.getPacketSender().resetItemsOnInterface(30375, 20);
        player.getPacketSender().sendString(30369, "Killcount: " +
                Misc.insertCommasToNumber(String.valueOf(KillsTracker.getTotalKillsForNpc(definition.getId(), player))));
        var drops = NPCDrops.forId(definition.getId());
        int slot = 0;
        for (NPCDrops.NpcDropItem drop : drops.getDropList()) {
            if (hasObtainedItem(definition.getId(), drop.getId())) {
                var item = player.getCollectionLogData().stream().filter(data ->
                        data.getNpcId() == definition.getId() && data.getItem() == drop.getId()).findFirst().get();
                player.getPacketSender().sendItemOnInterface(30375, item.getItem(), slot++, item.getAmount());
            } else {
                player.getPacketSender().sendItemOnInterface(30375, drop.getId(), slot++, 0);
            }
        }
    }

    public void handleSearch(String input) {
        initialiseCurrentlyViewing();
        currentlyViewing.removeIf(def -> !NpcDefinition.forId(def).getName().toLowerCase().contains(input.toLowerCase()));
        sendBossNames();
    }

    public boolean handleButton(int buttonId) {
        if (buttonId == 30362) {
            player.setInputHandling(new EnterNpcName());
            player.getPacketSender().sendEnterInputPrompt("Which NPC would you like to find?");
        }
        if (!(buttonId >= 30560 && buttonId <= 30760)) {
            return false;
        }
        int index = -30560 + buttonId;
        if (currentlyViewing.size() > index) {
            sendNpcData(index);
        }
        return true;
    }
}

class EnterNpcName extends Input {

    @Override
    public void handleSyntax(Player player, String syntax) {
        if (syntax.length() <= 1) {
            player.getPacketSender().sendMessage("Invalid syntax entered.");
            return;
        }
        player.getCollectionLog().handleSearch(syntax);
    }
}
