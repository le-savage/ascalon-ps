package com.AscalonPS.world.content.combat.weapon.effects.impl.weapon;

import com.AscalonPS.model.CombatIcon;
import com.AscalonPS.model.Hit;
import com.AscalonPS.model.Hitmask;
import com.AscalonPS.model.Locations;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.World;
import com.AscalonPS.world.content.combat.CombatContainer;
import com.AscalonPS.world.content.combat.CombatFactory;
import com.AscalonPS.world.content.combat.CombatType;
import com.AscalonPS.world.content.combat.CombatFormulas;
import com.AscalonPS.world.entity.impl.Character;
import com.AscalonPS.world.entity.impl.npc.NPC;
import com.AscalonPS.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ScytheOfVitur extends ItemEffect { //TODO rewrite this and remove item effects
    @Override
    public int itemId() {
        return 18899;
    }

    @Override
    public int hitAmount(Character attacker, Character victim) {
        /*int hitAmount = 1;
        int victims = targets(attacker, victim).size() + 1; //add 1 for the victim themself
        if(victim.getSize() >= 2) {
            hitAmount = 3;
        }
        if(victims >= 2) {
            hitAmount += 1;
            if(hitAmount > 3)
                hitAmount = 3;
        }
        System.out.println("Hit Amount: " + hitAmount + " - victims size " + victims);
        return hitAmount;*/
        return Misc.random(1, 2);
    }


    @Override
    public void handleAttack(Character attacker, Character victim) {

        AtomicInteger attacked = new AtomicInteger();
        System.out.println(hitAmount(attacker,victim));
        if (hitAmount(attacker, victim) > 2) {
            victim.dealDamage(attacker.getAsPlayer(), new Hit(Misc.random(CombatFormulas.calculateMaxMeleeHit(attacker, victim)), Hitmask.RED, CombatIcon.MELEE));
        }
        targets(attacker, victim).forEach(target -> {
            attacked.getAndIncrement();
            if (attacked.get() >= 3)
                return;
            target.getLastCombat().reset();
            target.getCombatBuilder().setVictim(attacker);
            target.getCombatBuilder().attack(attacker);
            for (int i = 1; i <= hitAmount(attacker, victim); i++) {
                if (CombatFactory.rollAccuracy(attacker, target, CombatType.MELEE)) {
                    target.dealDamage(attacker.getAsPlayer(), new Hit(Misc.random(CombatFormulas.calculateMaxMeleeHit(attacker, target)), Hitmask.RED, CombatIcon.MELEE));
                } else {
                    target.dealDamage(attacker.getAsPlayer(), new Hit(0, Hitmask.RED, CombatIcon.BLOCK));
                }
            }
        });
    }

    @Override
    public void afterAttack(CombatContainer container) {

    }

    @Override
    public List<Character> targets(Character attacker, Character victim) {
        List<Character> list = new ArrayList<>();
        Iterator<? extends Character> it = null;
        if (attacker.isPlayer() && victim.isPlayer()) {
            it = ((Player) attacker).getLocalPlayers().iterator();
        } else if (attacker.isPlayer() && victim.isNpc()) {
            it = ((Player) attacker).getLocalNpcs().iterator();
        } else if (attacker.isNpc() && victim.isNpc()) {
            it = World.getNpcs().iterator();
        } else if (attacker.isNpc() && victim.isPlayer()) {
            it = World.getPlayers().iterator();
        }
        for (Iterator<? extends Character> $it = it; $it.hasNext(); ) {
            Character next = $it.next();
            if (next == null) {
                continue;
            }
            if (!next.getPosition().isWithinDistance(victim.getPosition(),
                    1))
                continue;
            if (next.equals(attacker) || next.equals(victim) || next.getConstitution() <= 0) {
                continue;
            }
            if (next.isNpc() && !next.getAsMob().getDefinition().isAttackable()) {
                continue;
            }

            if (next.isNpc()) {
                NPC n = (NPC) next;
                if (!n.getDefinition().isAttackable() || n.isSummoningNpc() || !Locations.Location.inMulti(n)) {
                    continue;
                }
            } else {
                Player p = (Player) next;
                if (p.getLocation() != Locations.Location.WILDERNESS || !Locations.Location.inMulti(p)) {
                    continue;
                }
            }
            list.add(next);
        }
        return list;
    }
}
