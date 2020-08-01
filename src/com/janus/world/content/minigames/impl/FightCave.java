package com.janus.world.content.minigames.impl;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.Locations;
import com.janus.model.Position;
import com.janus.model.RegionInstance;
import com.janus.model.Locations.Location;
import com.janus.model.RegionInstance.RegionInstanceType;
import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.content.dialogue.DialogueManager;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

public class FightCave {

    public static final int JAD_NPC_ID = 2745;

    public static void enterCave(Player player) {
        player.getPacketSender().sendRichPresenceState("Fighting Jad!");
        player.getPacketSender().sendRichPresenceSmallPictureText("CB LVL: " + player.getSkillManager().getCombatLevel());
        player.getPacketSender().sendSmallImageKey("minigame");
        player.moveTo(new Position(2413, 5117, player.getIndex() * 4));
        DialogueManager.start(player, 36);
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.FIGHT_CAVE));
        spawnJad(player);
    }

    public static void leaveCave(Player player, boolean resetStats) {
        player.getPacketSender().sendRichPresenceState("TzHaar Caves");
        player.getPacketSender().sendRichPresenceSmallPictureText("CB LVL: " + player.getSkillManager().getCombatLevel());
        player.getPacketSender().sendSmallImageKey("minigame");
        Locations.Location.FIGHT_CAVES.leave(player);
        if (resetStats)
            player.restart();
    }

    public static void spawnJad(final Player player) {
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Location.FIGHT_CAVES) {
                    stop();
                    return;
                }
                NPC n = new NPC(JAD_NPC_ID, new Position(2399, 5083, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(n);
                player.getRegionInstance().getNpcsList().add(n);
                n.getCombatBuilder().attack(player);
                stop();
            }
        });
    }

    public static void handleJadDeath(final Player player, NPC n) {
        if (n.getId() == JAD_NPC_ID) {
            if (player.getRegionInstance() != null)
                player.getRegionInstance().getNpcsList().remove(n);
            leaveCave(player, true);
            DialogueManager.start(player, 37);
            player.getInventory().add(6570, 1).add(6529, 1000 + Misc.getRandom(2000));
        }
    }

}
