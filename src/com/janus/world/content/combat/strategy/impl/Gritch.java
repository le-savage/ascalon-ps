package com.janus.world.content.combat.strategy.impl;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.Animation;
import com.janus.model.Graphic;
import com.janus.model.Locations;
import com.janus.model.Projectile;
import com.janus.util.Misc;
import com.janus.world.content.combat.CombatContainer;
import com.janus.world.content.combat.CombatType;
import com.janus.world.content.combat.strategy.CombatStrategy;
import com.janus.world.entity.impl.Character;
import com.janus.world.entity.impl.npc.NPC;

public class Gritch implements CombatStrategy {

    private static final Animation anim = new Animation(69);
    private static final Graphic gfx = new Graphic(386);

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return true;
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC gritch = (NPC) entity;
        if (gritch.isChargingAttack() || victim.getConstitution() <= 0) {
            gritch.getCombatBuilder().setAttackTimer(4);
            return true;
        }
        if (Locations.goodDistance(gritch.getPosition().copy(), victim.getPosition().copy(), 1) && Misc.getRandom(5) <= 3) {
            gritch.performAnimation(new Animation(gritch.getDefinition().getAttackAnimation()));
            gritch.getCombatBuilder().setContainer(new CombatContainer(gritch, victim, 1, 1, CombatType.MELEE, true));
        } else {
            gritch.setChargingAttack(true);
            gritch.performAnimation(anim);
            gritch.getCombatBuilder().setContainer(new CombatContainer(gritch, victim, 1, 3, CombatType.RANGED, true));
            TaskManager.submit(new Task(1, gritch, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 1) {
                        new Projectile(gritch, victim, gfx.getId(), 44, 3, 43, 43, 0).sendProjectile();
                        gritch.setChargingAttack(false);
                        stop();
                    }
                    tick++;
                }
            });
        }
        return true;
    }


    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 5;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
