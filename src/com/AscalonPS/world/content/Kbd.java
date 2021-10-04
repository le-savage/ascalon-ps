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

public class Kbd {

    public static NPC KBD;


    static Player getPlayer;



/*public static void despawn(Player player) {
World.deregister(KBD);

}*/

    /*public static void spawnKBD(Player player) {
        getPlayer = player;
        player.setRegionInstance(new RegionInstance(player, RegionInstanceType.KBD));
        KBD = new NPC(50, new Position(2274, 4697, player.getIndex() * 4));

        World.register(KBD);

    }*/

    public static void destructKbd(final Player player) {


        player.getRegionInstance().getNpcsList().forEach(npc -> npc.removeInstancedNpcs(Locations.Location.KBD, player.getPosition().getZ()));


        player.getRegionInstance().destruct();

        TeleportHandler.teleportPlayer(player, GameSettings.DEFAULT_POSITION.copy(), player.getSpellbook().getTeleportType());

        //player.getPacketSender().sendMessage("Sent home - you already had an instanced boss!");
    }


    public static void spawnKBD(final Player player) {
        TaskManager.submit(new Task(5, player, false) {
            @Override
            public void execute() {
                if (player.getRegionInstance() == null || !player.isRegistered() || player.getLocation() != Locations.Location.KBD || player.getRegionInstance().getNpcsList().contains(KBD)) {
                    System.out.print("Failed to spawn KBD. One of the variables was not correct.");
                    stop();
                    return;
                }


                System.out.print("KBD spawn task finished for " + player.getUsername());
                KBD = new NPC(50, new Position(2274, 4697, player.getIndex() * 4));
                World.register(KBD);
                player.getRegionInstance().getNpcsList().add(KBD);
                KBD.getCombatBuilder().attack(player);
                stop();
            }

        });
    }


}
