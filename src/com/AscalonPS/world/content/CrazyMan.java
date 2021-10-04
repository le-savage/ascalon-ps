package com.AscalonPS.world.content;

import com.AscalonPS.GameSettings;
import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Locations;
import com.AscalonPS.model.Position;
import com.AscalonPS.world.World;
import com.AscalonPS.world.content.transportation.TeleportHandler;
import com.AscalonPS.world.entity.impl.npc.NPC;
import com.AscalonPS.world.entity.impl.player.Player;

public class CrazyMan {

    public static NPC CrazyMan;
    public static final int CrazyManID = 3;

    public static void destructMan(final Player player) {

        System.out.println("Handle Man Death. NPC Matched...");

        player.getRegionInstance().getNpcsList().forEach(npc -> npc.removeInstancedNpcs(Locations.Location.MAN, player.getPosition().getZ()));


        player.getRegionInstance().destruct();

        TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION.copy(), player.getSpellbook().getTeleportType());

        //player.getPacketSender().sendMessage("Sent home - you already had an instanced boss!");
    }


    public static void spawnMan(final Player player) {
        TaskManager.submit(new Task(5, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.MAN || player.getRegionInstance().getNpcsList().contains(CrazyMan)) {
                    System.out.print("Failed to spawn man. One of the variables was not correct.");
                    stop();
                    return;
                }


                System.out.print("Man spawn task finished");
                //player.setRegionInstance(new RegionInstance(player, RegionInstanceType.MAN));
                NPC man = new NPC(CrazyManID, new Position(1964, 4754, player.getPosition().getZ())).setSpawnedFor(player);
                World.register(man);
                player.getRegionInstance().getNpcsList().add(man);
                man.getCombatBuilder().attack(player);
                stop();
            }

        });
    }

    /*public static void spawn(Player player) {
        getPlayer = player;
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.MAN));
        CrazyMan = new NPC(3, new Position(1964, 4754, player.getIndex() * 4));

        World.register(CrazyMan);

    }*/


}
