package com.AscalonPS.world.content.combat.strategy.impl;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Graphic;
import com.AscalonPS.model.GraphicHeight;
import com.AscalonPS.model.Locations.Location;
import com.AscalonPS.model.Projectile;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.content.combat.CombatContainer;
import com.AscalonPS.world.content.combat.CombatHitTask;
import com.AscalonPS.world.content.combat.CombatType;
import com.AscalonPS.world.content.combat.strategy.CombatStrategy;
import com.AscalonPS.world.entity.impl.Character;
import com.AscalonPS.world.entity.impl.npc.NPC;
import com.AscalonPS.world.entity.impl.player.Player;

public class Graardor implements CombatStrategy {

    private static final Animation attack_anim = new Animation(7063);
    private static final Graphic graphic1 = new Graphic(1200, GraphicHeight.MIDDLE);

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
        NPC graardor = (NPC) entity;
        if (graardor.isChargingAttack() || graardor.getConstitution() <= 0) {
            return true;
        }
        //CombatType style = Misc.getRandom(4) <= 2 && Locations.goodDistance(graardor.getPosition(), victim.getPosition(), 2) ? CombatType.MELEE : CombatType.RANGED;
        CombatType style = CombatType.MELEE;
        if (style == CombatType.MELEE) {
            graardor.performAnimation(new Animation(graardor.getDefinition().getAttackAnimation()));
            graardor.getCombatBuilder().setContainer(new CombatContainer(graardor, victim, 1, 1, CombatType.MELEE, true));
        } else {
            graardor.performAnimation(attack_anim);
            graardor.setChargingAttack(true);
            Player target = (Player) victim;
            for (Player t : Misc.getCombinedPlayerList(target)) {
                if (t == null || t.getLocation() != Location.GODWARS_DUNGEON || t.isTeleporting())
                    continue;
                if (t.getPosition().distanceToPoint(graardor.getPosition().getX(), graardor.getPosition().getY()) > 20)
                    continue;
                new Projectile(graardor, target, graphic1.getId(), 44, 3, 43, 43, 0).sendProjectile();
            }
            TaskManager.submit(new Task(2, target, false) {
                @Override
                public void execute() {
                    for (Player t : Misc.getCombinedPlayerList(target)) {
                        if (t == null || t.getLocation() != Location.GODWARS_DUNGEON)
                            continue;
                        graardor.getCombatBuilder().setVictim(t);
                        new CombatHitTask(graardor.getCombatBuilder(), new CombatContainer(graardor, t, 1, CombatType.RANGED, true)).handleAttack();
                    }
                    graardor.setChargingAttack(false);
                    stop();
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
        return 2;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}
