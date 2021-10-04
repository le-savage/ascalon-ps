package com.AscalonPS.world.entity;

import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.GameObject;
import com.AscalonPS.net.PlayerSession;
import com.AscalonPS.net.SessionState;
import com.AscalonPS.world.World;
import com.AscalonPS.world.clip.region.RegionClipping;
import com.AscalonPS.world.content.CustomObjects;
import com.AscalonPS.world.entity.impl.npc.NPC;
import com.AscalonPS.world.entity.impl.player.Player;

public class EntityHandler {

    public static void register(Entity entity) {
        if (entity.isPlayer()) {
            Player player = (Player) entity;
            PlayerSession session = player.getSession();
            if (session.getState() == SessionState.LOGGING_IN && !World.getLoginQueue().contains(player)) {
                World.getLoginQueue().add(player);
            }
        }
        if (entity.isNpc()) {
            NPC npc = (NPC) entity;
            World.getNpcs().add(npc);
        } else if (entity.isGameObject()) {
            GameObject gameObject = (GameObject) entity;
            RegionClipping.addObject(gameObject);
            CustomObjects.spawnGlobalObjectWithinDistance(gameObject);
        }
    }

    public static void deregister(Entity entity) {
        if (entity.isPlayer()) {
            Player player = (Player) entity;
            World.getPlayers().remove(player);
        } else if (entity.isNpc()) {
            NPC npc = (NPC) entity;
            TaskManager.cancelTasks(npc.getCombatBuilder());
            TaskManager.cancelTasks(entity);
            World.getNpcs().remove(npc);
        } else if (entity.isGameObject()) {
            GameObject gameObject = (GameObject) entity;
            RegionClipping.removeObject(gameObject);
            CustomObjects.deleteGlobalObjectWithinDistance(gameObject);
        }
    }
}
