package com.AscalonPS.world.content.combat.strategy.impl;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Graphic;
import com.AscalonPS.model.Projectile;
import com.AscalonPS.world.content.combat.CombatContainer;
import com.AscalonPS.world.content.combat.CombatType;
import com.AscalonPS.world.content.combat.strategy.CombatStrategy;
import com.AscalonPS.world.entity.impl.Character;
import com.AscalonPS.world.entity.impl.npc.NPC;
import com.AscalonPS.world.entity.impl.player.Player;

public class Steelwill implements CombatStrategy {

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return victim.isPlayer() && ((Player) victim).getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom();
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC steelwill = (NPC) entity;

        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (steelwill.isChargingAttack()) {
            return true;
        }

        steelwill.performAnimation(new Animation(steelwill.getDefinition().getAttackAnimation()));
        steelwill.performGraphic(new Graphic(1202));
        steelwill.setChargingAttack(true);

        steelwill.getCombatBuilder().setContainer(new CombatContainer(steelwill, victim, 1, 3, CombatType.MAGIC, true));

        TaskManager.submit(new Task(1, steelwill, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 1) {
                    new Projectile(steelwill, victim, 1203, 44, 3, 43, 43, 0).sendProjectile();
                    steelwill.setChargingAttack(false);
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
        return 8;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MAGIC;
    }
}
