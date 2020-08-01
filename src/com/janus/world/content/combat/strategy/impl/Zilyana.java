package com.janus.world.content.combat.strategy.impl;

import com.janus.model.Animation;
import com.janus.model.Graphic;
import com.janus.model.Locations;
import com.janus.util.Misc;
import com.janus.world.content.combat.CombatContainer;
import com.janus.world.content.combat.CombatType;
import com.janus.world.content.combat.strategy.CombatStrategy;
import com.janus.world.entity.impl.Character;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

public class Zilyana implements CombatStrategy {

    private static final Animation attack_anim = new Animation(6967);

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
        NPC zilyana = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (Locations.goodDistance(zilyana.getPosition().copy(), victim.getPosition().copy(), 1) && Misc.getRandom(5) <= 3) {
            zilyana.performAnimation(new Animation(zilyana.getDefinition().getAttackAnimation()));
            zilyana.getCombatBuilder().setContainer(new CombatContainer(zilyana, victim, 1, 1, CombatType.MELEE, true));
        } else {
            zilyana.performAnimation(attack_anim);
            zilyana.performGraphic(new Graphic(1220));
            zilyana.getCombatBuilder().setContainer(new CombatContainer(zilyana, victim, 2, 3, CombatType.MAGIC, true));
            zilyana.getCombatBuilder().setAttackTimer(7);
        }
        return true;
    }

    @Override
    public int attackDelay(Character entity) {
        return entity.getAttackSpeed();
    }

    @Override
    public int attackDistance(Character entity) {
        return 2;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
