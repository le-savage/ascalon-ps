package com.janus.world.content.combat.weapon.effects.impl.weapon;

import com.janus.model.Item;
import com.janus.world.content.combat.CombatContainer;
import com.janus.world.entity.impl.Character;
import com.janus.world.entity.impl.player.Player;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class ItemEffect {

    public abstract int itemId();

    public abstract int hitAmount(Character attacker, Character victim);

    public abstract void handleAttack(Character attacker, Character victim);

    public abstract void afterAttack(CombatContainer container);

    public abstract List<Character> targets(Character attacker, Character victim);

    static List<ItemEffect> itemEffects = new ArrayList<>();

    public static void loadEffects() {
        new FastClasspathScanner().matchSubclassesOf(ItemEffect.class, clazz -> {
            try {
                ItemEffect itemEffect = clazz.newInstance();
                itemEffects.add(itemEffect);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).scan();
    }

    public static int highestHitAmount(Player player, Character attacker, Character victim) {
        return player.currentEffects.stream().max(Comparator.comparing(v -> v.hitAmount(attacker, victim))).get().hitAmount(attacker, victim);
    }

    public static ItemEffect getEffectFromId(int id) {
        return itemEffects.stream().filter(wep -> wep.itemId() == id).findAny().orElse(null);
    }

    public static void refreshEffects(Player player) {
        player.currentEffects.clear();
        for (Item item : player.getEquipment().getItems()) {
            if (getEffectFromId(item.getId()) != null) {
                player.currentEffects.add(getEffectFromId(item.getId()));
            }
        }
    }
}
