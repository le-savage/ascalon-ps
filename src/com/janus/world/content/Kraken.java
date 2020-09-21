package com.janus.world.content;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.engine.task.impl.CeilingCollapseTask;
import com.janus.model.Position;
import com.janus.model.RegionInstance;
import com.janus.world.World;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

public class Kraken {

    public static void enter(Player player) {
        KrakenInstance kInstance = new KrakenInstance(player);

        for (WhirpoolData d : WhirpoolData.values()) {
            NPC whirpool = new NPC(d.npc, new Position(d.spawn.getX(), d.spawn.getY(), player.getPosition().getZ()));
            kInstance.getNpcsList().add(whirpool);
            World.register(whirpool);
        }

        player.setRegionInstance(kInstance);
    }

    public static boolean isWhirpool(NPC n) {
        int npc = n.getId();
        return npc == 2895 || npc == 2900 || npc == 2902 || npc == 2903 || npc == 2891;
    }

    public static void attackPool(Player player, NPC npc) {
        WhirpoolData whirpoolData = WhirpoolData.getPool(npc.getId());
        if (whirpoolData != null) {
            if (((KrakenInstance) player.getRegionInstance()).disturbedPool(whirpoolData.ordinal()))
                return;
            player.getRegionInstance().getNpcsList().remove(npc);
            ((KrakenInstance) player.getRegionInstance()).setDisturbedPool(whirpoolData.ordinal(), true);
            World.deregister(npc);

            final boolean kraken = whirpoolData == WhirpoolData.BIG_POOL;
            TaskManager.submit(new Task(1, player, false) {
                @Override
                protected void execute() {
                    int npcToSpawn = kraken ? 2005 : 3580;
                    Position positionToSpawn = kraken ? new Position(3677, 9887, player.getPosition().getZ()) : new Position(whirpoolData.spawn.getX() + 2, whirpoolData.spawn.getY() + 1, player.getPosition().getZ());
                    NPC spawn = new NPC(npcToSpawn, positionToSpawn);
                    player.getRegionInstance().getNpcsList().add(spawn);
                    World.register(spawn);
                    spawn.getCombatBuilder().attack(player);
                    stop();

                    if (kraken) {
                        player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                        player.getPacketSender().sendMessage("The cave begins to collapse...");
                        TaskManager.submit(new CeilingCollapseTask(player));
                    }
                }
            });
        }
    }

    private static enum WhirpoolData {

        SMALL_POOL_1(2895, new Position(3679, 9884)),
        SMALL_POOL_2(2900, new Position(3676, 9884)),
        SMALL_POOL_3(2902, new Position(3676, 9891)),
        SMALL_POOL_4(2903, new Position(3679, 9891)),
        BIG_POOL(2891, new Position(3677, 9887));

        int npc;
        Position spawn;
        WhirpoolData(int npc, Position spawn) {
            this.npc = npc;
            this.spawn = spawn;
        }

        static WhirpoolData getPool(int npc) {
            for (WhirpoolData d : WhirpoolData.values()) {
                if (d.npc == npc) {
                    return d;
                }
            }
            return null;
        }
    }

    public static class KrakenInstance extends RegionInstance {

        private boolean[] disturbedPool = new boolean[5];

        public KrakenInstance(Player p) {
            super(p, RegionInstance.RegionInstanceType.KRAKEN);

        }

        public boolean disturbedPool(int index) {
            return disturbedPool[index];
        }

        public void setDisturbedPool(int index, boolean disturbed) {
            this.disturbedPool[index] = disturbed;
        }
    }
}
