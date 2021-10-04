package com.AscalonPS.world.content.combat.strategy.impl;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Graphic;
import com.AscalonPS.model.Locations;
import com.AscalonPS.model.Position;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.content.combat.CombatContainer;
import com.AscalonPS.world.content.combat.CombatType;
import com.AscalonPS.world.content.combat.strategy.CombatStrategy;
import com.AscalonPS.world.entity.impl.Character;
import com.AscalonPS.world.entity.impl.npc.NPC;
import com.AscalonPS.world.entity.impl.player.Player;

import java.util.List;

public class Vetion implements CombatStrategy {

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
        NPC vetion = (NPC) entity;
        if (vetion.isChargingAttack() || victim.getConstitution() <= 0) {
            return true;
        }
        if (Locations.goodDistance(vetion.getPosition().copy(), victim.getPosition().copy(), 3) && Misc.getRandom(5) <= 3) {
            vetion.performAnimation(new Animation(5487));
            vetion.getCombatBuilder().setContainer(new CombatContainer(vetion, victim, 1, 1, CombatType.MELEE, true));
        } else {
            vetion.setChargingAttack(true);
            vetion.performAnimation(new Animation(vetion.getDefinition().getAttackAnimation()));

            final Position start = victim.getPosition().copy();
            final Position second = new Position(start.getX() + 2, start.getY() + Misc.getRandom(2));
            final Position last = new Position(start.getX() - 2, start.getY() - Misc.getRandom(2));

            final Player p = (Player) victim;
            final List<Player> list = Misc.getCombinedPlayerList(p);

            TaskManager.submit(new Task(1, vetion, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 0) {
                        p.getPacketSender().sendGlobalGraphic(new Graphic(281), start);
                        p.getPacketSender().sendGlobalGraphic(new Graphic(281), second);
                        p.getPacketSender().sendGlobalGraphic(new Graphic(281), last);
                    } else if (tick == 3) {
                        for (Player t : list) {
                            if (t == null)
                                continue;

                        }
                        vetion.setChargingAttack(false);
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
        return 4;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
