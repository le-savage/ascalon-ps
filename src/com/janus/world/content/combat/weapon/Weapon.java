package com.janus.world.content.combat.weapon;

import com.janus.world.entity.impl.Character;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.util.ArrayList;
import java.util.List;

public abstract class Weapon {
    public abstract int weaponId();
    public abstract int hitAmount(Character attacker, Character victim);
    public abstract void handleAttack(Character attacker, Character victim);
    public abstract List<Character> targets(Character attacker, Character victim);

    static List<Weapon> weapons = new ArrayList<>();
    public static void loadWeapons() {
        new FastClasspathScanner().matchSubclassesOf(Weapon.class, clazz -> {
            try {
                Weapon weapon = clazz.newInstance();
                weapons.add(weapon);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }).scan();
    }
    public static Weapon getWeaponFromId(int id) {
        return weapons.stream().filter(wep -> wep.weaponId() == id).findAny().orElse(null);
    }
}
