package com.janus.world.content.combat.strategy.impl;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.Animation;
import com.janus.model.Graphic;
import com.janus.model.GraphicHeight;
import com.janus.model.Projectile;
import com.janus.util.Misc;
import com.janus.world.content.combat.CombatContainer;
import com.janus.world.content.combat.CombatType;
import com.janus.world.content.combat.strategy.CombatStrategy;
import com.janus.world.entity.impl.Character;
import com.janus.world.entity.impl.npc.NPC;

public class ChaosElemental implements CombatStrategy {

    private static final Graphic teleGraphic = new Graphic(661);

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
        NPC cE = (NPC) entity;
        if (cE.isChargingAttack() || victim.getConstitution() <= 0) {
            return true;
        }
        cE.getMovementQueue().reset();
        cE.setEntityInteraction(victim);
        final int attackStyle = Misc.getRandom(2); //0 = melee, 1 = range, 2 =mage
        final ElementalData data = ElementalData.forId(attackStyle);
        if (data.startGraphic != null)
            cE.performGraphic(data.startGraphic);
        cE.performAnimation(new Animation(cE.getDefinition().getAttackAnimation()));
        if (data.projectileGraphic != null)
            new Projectile(cE, victim, data.projectileGraphic.getId(), 44, 3, 43, 31, 0).sendProjectile();
        cE.setChargingAttack(true);
        TaskManager.submit(new Task(1, cE, false) {
            @Override
            public void execute() {
                cE.getCombatBuilder().setContainer(new CombatContainer(cE, victim, 1, 2, data.getCombatType(), true));
                if (data.endGraphic != null)
                    victim.performGraphic(data.endGraphic);
                cE.setChargingAttack(false);


                stop();
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
        return CombatType.MIXED;
    }

    private static enum ElementalData {
        MELEE(new Graphic(553, GraphicHeight.HIGH), new Graphic(554, GraphicHeight.MIDDLE), null),
        RANGED(new Graphic(665, GraphicHeight.HIGH), null, new Graphic(552, GraphicHeight.HIGH)),
        MAGIC(new Graphic(550, GraphicHeight.HIGH), new Graphic(551, GraphicHeight.MIDDLE), new Graphic(555, GraphicHeight.HIGH));

        public Graphic startGraphic;
        public Graphic projectileGraphic;
        public Graphic endGraphic;
        ElementalData(Graphic startGfx, Graphic projectile, Graphic endGraphic) {
            startGraphic = startGfx;
            projectileGraphic = projectile;
            this.endGraphic = endGraphic;
        }

        static ElementalData forId(int id) {
            for (ElementalData data : ElementalData.values()) {
                if (data.ordinal() == id)
                    return data;
            }
            return null;
        }

        public CombatType getCombatType() {
            switch (this) {
                case MAGIC:
                    return CombatType.MAGIC;
                case MELEE:
                    return CombatType.MELEE;
                case RANGED:
                    return CombatType.RANGED;
            }
            return CombatType.MELEE;
        }
    }
}
