package com.AscalonPS.world.content.combat.strategy.impl;

import com.AscalonPS.world.content.combat.CombatContainer;
import com.AscalonPS.world.content.combat.CombatType;
import com.AscalonPS.world.content.combat.strategy.CombatStrategy;
import com.AscalonPS.world.entity.impl.Character;

public class Kraken implements CombatStrategy {

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return false;
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        return false;
    }

    @Override
    public int attackDelay(Character entity) {
        return 0;
    }

    @Override
    public int attackDistance(Character entity) {
        return 0;
    }

    @Override
    public CombatType getCombatType() {
        return null;
    }
}
