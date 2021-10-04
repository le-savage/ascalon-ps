package com.AscalonPS.world.content.combat.strategy.impl;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Projectile;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.content.combat.CombatContainer;
import com.AscalonPS.world.content.combat.CombatType;
import com.AscalonPS.world.content.combat.strategy.CombatStrategy;
import com.AscalonPS.world.entity.impl.Character;
import com.AscalonPS.world.entity.impl.npc.NPC;

public class PlaneFreezer implements CombatStrategy {

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
        NPC lakra = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (lakra.isChargingAttack()) {
            return true;
        }
        lakra.setChargingAttack(true);
        lakra.performAnimation(new Animation((13770)));
        final CombatType attkType = Misc.getRandom(5) <= 2 ? CombatType.RANGED : CombatType.MAGIC;
        lakra.getCombatBuilder().setContainer(new CombatContainer(lakra, victim, 1, 4, attkType, Misc.getRandom(5) <= 1 ? false : true));
        TaskManager.submit(new Task(1, lakra, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 2) {
                    new Projectile(lakra, victim, (attkType == CombatType.RANGED ? 605 : 473), 44, 3, 43, 43, 0).sendProjectile();
                    lakra.setChargingAttack(false);
                    stop();
                }
                tick++;
            }
        });
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
