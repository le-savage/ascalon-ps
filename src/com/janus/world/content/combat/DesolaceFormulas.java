package com.janus.world.content.combat;

import com.janus.model.Graphic;
import com.janus.model.Skill;
import com.janus.model.container.impl.Equipment;
import com.janus.model.definitions.ItemDefinition;
import com.janus.util.Misc;
import com.janus.world.content.combat.effect.EquipmentBonus;
import com.janus.world.content.combat.magic.CombatSpell;
import com.janus.world.content.combat.prayer.CurseHandler;
import com.janus.world.content.combat.prayer.PrayerHandler;
import com.janus.world.content.combat.range.CombatRangedAmmo.RangedWeaponData;
import com.janus.world.content.combat.weapon.FightType;
import com.janus.world.content.skill.SkillManager;
import com.janus.world.entity.impl.Character;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

public class DesolaceFormulas {

    /*==============================================================================*/
    /*===================================MELEE=====================================*/


    /**
     * Obsidian items
     */

    public static final int[] obsidianWeapons = {
            746, 747, 6523, 6525, 6526, 6527, 6528
    };

    public static int calculateMaxMeleeHit(Character entity, Character victim) {
        double maxHit = 0;
        if (entity.isNpc()) {
            NPC npc = (NPC) entity;
            maxHit = npc.getDefinition().getMaxHit();
            if (npc.getStrengthWeakened()[0]) {
                maxHit -= (int) ((0.10) * (maxHit));
            } else if (npc.getStrengthWeakened()[1]) {
                maxHit -= (int) ((0.20) * (maxHit));
            } else if (npc.getStrengthWeakened()[2]) {
                maxHit -= (int) ((0.30) * (maxHit));
            }

            /** CUSTOM NPCS **/
            if (npc.getId() == 2026) { //Dharok the wretched
                maxHit += (int) ((int) (npc.getDefaultConstitution() - npc.getConstitution()) * 0.2);
            }
        } else {
            Player player = (Player) entity;

            double base = 0;
            double effective = getEffectiveStr(player);
            double specialBonus = 1;
            if (player.isSpecialActivated()) {
                specialBonus = player.getCombatSpecial().getStrengthBonus();
            }
            double strengthBonus = player.getBonusManager().getOtherBonus()[0];
            base = (13 + effective + (strengthBonus / 8) + ((effective * strengthBonus) / 65)) / 11;
            if (player.getEquipment().getItems()[3].getId() == 4718 && player.getEquipment().getItems()[0].getId() == 4716 && player.getEquipment().getItems()[4].getId() == 4720 && player.getEquipment().getItems()[7].getId() == 4722)
                base += ((player.getSkillManager().getMaxLevel(Skill.CONSTITUTION) - player.getConstitution()) * .045) + 1;
            if (specialBonus > 1)
                base = (base * specialBonus);
            if (hasObsidianEffect(player) || EquipmentBonus.wearingVoid(player, CombatType.MELEE))
                base = (base * 1.2);
            if (EquipmentBonus.wearingEliteVoid(player, CombatType.MELEE))
                base = (base * 1.4);

            if (victim.isNpc()) {
                NPC npc = (NPC) victim;
                if (npc.getDefenceWeakened()[0]) {
                    base += (int) ((0.10) * (base));
                } else if (npc.getDefenceWeakened()[1]) {
                    base += (int) ((0.20) * (base));
                } else if (npc.getDefenceWeakened()[2]) {
                    base += (int) ((0.30) * (base));
                }

                /** SLAYER HELMET **/
                if (npc.getId() == player.getSlayer().getSlayerTask().getNpcId()) {
                    if (player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 13263) {
                        base *= 1.12;
                    }
                }
            }
            maxHit = (base *= 10);
        }
        if (victim.isPlayer()) {
            Player p = (Player) victim;
            if (p.hasStaffOfLightEffect()) {
                maxHit = maxHit / 2;
                p.performGraphic(new Graphic(2319));
            }
        }
        return (int) Math.floor(maxHit);
    }

    /**
     * Calculates a player's Melee attack level (how likely that they're going to hit through defence)
     *
     * @param player The player's Meelee attack level
     * @return The player's Melee attack level
     */
    @SuppressWarnings("incomplete-switch")
    public static int getMeleeAttack(Player player) {
        int attackLevel = player.getSkillManager().getCurrentLevel(Skill.ATTACK);
        switch (player.getFightType().getStyle()) {
            case AGGRESSIVE:
                attackLevel += 3;
                break;
            case CONTROLLED:
                attackLevel += 1;
                break;
        }
        boolean hasVoid = EquipmentBonus.wearingVoid(player, CombatType.MELEE);
        boolean hasEliteVoid = EquipmentBonus.wearingEliteVoid(player, CombatType.MELEE);

        if (PrayerHandler.isActivated(player,
                PrayerHandler.CLARITY_OF_THOUGHT)) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.05;
        } else if (PrayerHandler.isActivated(player,
                PrayerHandler.IMPROVED_REFLEXES)) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.1;
        } else if (PrayerHandler.isActivated(player,
                PrayerHandler.INCREDIBLE_REFLEXES)) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.15;
        } else if (PrayerHandler.isActivated(player,
                PrayerHandler.CHIVALRY)) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.15;
        } else if (PrayerHandler.isActivated(player, PrayerHandler.PIETY)) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.2;
        } else if (CurseHandler.isActivated(player, CurseHandler.LEECH_ATTACK)) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.05 + player.getLeechedBonuses()[2];
        } else if (CurseHandler.isActivated(player, CurseHandler.TURMOIL)) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK)
                    * 0.3 + player.getLeechedBonuses()[2];
        }

        if (hasVoid) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.05; //5% Boost
        }
        if (hasEliteVoid) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.15; //15% boost
        }

        /** Justicar bonus **/

        if (EquipmentBonus.fullJusticar(player)) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.08; // 35% Boost
        }

        /** Torva bonus **/

        if (EquipmentBonus.fullTorva(player)) {
            attackLevel += player.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.05; // 22% boost
        }

        attackLevel *= player.isSpecialActivated() ? player.getCombatSpecial().getAccuracyBonus() : 1;
        int i = (int) player.getBonusManager().getAttackBonus()[bestMeleeAtk(player)];

        if (hasObsidianEffect(player) || hasVoid)
            i *= 1.10;// This is the attack bonus * 1.10.. so 10% boost
        if (hasEliteVoid)
            i *= 1.15; //This is 15% boost
        return (int) (attackLevel + (attackLevel * 0.15) + (i + i * 0.04));
    }

    /**
     * Calculates a player's Melee Defence level
     *
     * @param player The player to calculate Melee defence for
     * @return The player's Melee defence level
     */
    public static int getMeleeDefence(Player player) {
        int defenceLevel = player.getSkillManager().getCurrentLevel(Skill.DEFENCE);
        int bestMeleeDef = (int) player.getBonusManager().getDefenceBonus()[bestMeleeDef(player)];

        boolean fullPrimal = EquipmentBonus.fullPrimal(player);
        if (fullPrimal) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        }
        if (player.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        } else if (player.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
        } else if (player.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        } else if (player.getPrayerActive()[PrayerHandler.CHIVALRY]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
        } else if (player.getPrayerActive()[PrayerHandler.PIETY]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (player.getPrayerActive()[PrayerHandler.RIGOUR]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (player.getPrayerActive()[PrayerHandler.AUGURY]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (player.getCurseActive()[CurseHandler.TURMOIL]) { // turmoil
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        }
        return (int) (defenceLevel + (defenceLevel * 0.15) + (bestMeleeDef + bestMeleeDef * 1.0));
    }

    public static int bestMeleeDef(Player p) {
        if (p.getBonusManager().getDefenceBonus()[0] > p.getBonusManager().getDefenceBonus()[1] && p.getBonusManager().getDefenceBonus()[0] > p.getBonusManager().getDefenceBonus()[2]) {
            return 0;
        }
        if (p.getBonusManager().getDefenceBonus()[1] > p.getBonusManager().getDefenceBonus()[0] && p.getBonusManager().getDefenceBonus()[1] > p.getBonusManager().getDefenceBonus()[2]) {
            return 1;
        }
        return p.getBonusManager().getDefenceBonus()[2] <= p.getBonusManager().getDefenceBonus()[0] || p.getBonusManager().getDefenceBonus()[2] <= p.getBonusManager().getDefenceBonus()[1] ? 0 : 2;
    }

    public static int bestMeleeAtk(Player p) {
        if (p.getBonusManager().getAttackBonus()[0] > p.getBonusManager().getAttackBonus()[1] && p.getBonusManager().getAttackBonus()[0] > p.getBonusManager().getAttackBonus()[2]) {
            return 0;
        }
        if (p.getBonusManager().getAttackBonus()[1] > p.getBonusManager().getAttackBonus()[0] && p.getBonusManager().getAttackBonus()[1] > p.getBonusManager().getAttackBonus()[2]) {
            return 1;
        }
        return p.getBonusManager().getAttackBonus()[2] <= p.getBonusManager().getAttackBonus()[1] || p.getBonusManager().getAttackBonus()[2] <= p.getBonusManager().getAttackBonus()[0] ? 0 : 2;
    }

    public static boolean hasObsidianEffect(Player player) {
        if (player.getEquipment().getItems()[2].getId() != 11128)
            return false;

        for (int weapon : obsidianWeapons) {
            if (player.getEquipment().getItems()[3].getId() == weapon)
                return true;
        }
        return false;
    }

    @SuppressWarnings("incomplete-switch")
    public static int getStyleBonus(Player player) {
        switch (player.getFightType().getStyle()) {
            case AGGRESSIVE:
            case ACCURATE:
                return 3;
            case CONTROLLED:
                return 1;
        }
        return 0;
    }

    public static double getEffectiveStr(Player player) {
        return ((player.getSkillManager().getCurrentLevel(Skill.STRENGTH)) * getPrayerStr(player)) + getStyleBonus(player);
    }

    public static double getPrayerStr(Player player) {
        if (player.getPrayerActive()[1] || player.getCurseActive()[CurseHandler.LEECH_STRENGTH])
            return 1.05;
        else if (player.getPrayerActive()[6])
            return 1.1;
        else if (player.getPrayerActive()[14])
            return 1.15;
        else if (player.getPrayerActive()[24])
            return 1.18;
        else if (player.getPrayerActive()[25])
            return 1.23;
        else if (player.getCurseActive()[CurseHandler.TURMOIL])
            return 1.24;
        return 1;
    }

    /**
     * Calculates a player's Ranged attack (level).
     * Credits: Dexter Morgan
     *
     * @param player The player to calculate Ranged attack level for
     * @return The player's Ranged attack level
     */
    public static int getRangedAttack(Player player) {
        int rangeLevel = player.getSkillManager().getCurrentLevel(Skill.RANGED);
        boolean hasVoid = EquipmentBonus.wearingVoid(player, CombatType.RANGED);
        boolean hasEliteVoid = EquipmentBonus.wearingEliteVoid(player, CombatType.RANGED);
        boolean fullPernix = EquipmentBonus.fullPernix(player);

        double accuracy = player.isSpecialActivated() ? player.getCombatSpecial().getAccuracyBonus() : 1;
        rangeLevel *= accuracy;
        if (fullPernix) { //5% Pernix Buff
            rangeLevel *= 1.05;
        }
        if (hasVoid) {
            rangeLevel += SkillManager.getLevelForExperience(player.getSkillManager().getExperience(Skill.RANGED)) * 0.15;
        }
        if (hasEliteVoid) {
            rangeLevel += SkillManager.getLevelForExperience(player.getSkillManager().getExperience(Skill.RANGED)) * 0.30;
        }
        if (player.getCurseActive()[PrayerHandler.SHARP_EYE] || player.getCurseActive()[CurseHandler.SAP_RANGER]) {
            rangeLevel *= 1.05;
        }
        if (player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 15492) {
            rangeLevel *= 1.2;
        } else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == 20998) {
            rangeLevel *= 1.3; //BUFF TOP TIER RANGED
        } else if (player.getPrayerActive()[PrayerHandler.HAWK_EYE]) {
            rangeLevel *= 1.10;
        } else if (player.getPrayerActive()[PrayerHandler.EAGLE_EYE]) {
            rangeLevel *= 1.15;
        } else if (player.getPrayerActive()[PrayerHandler.RIGOUR]) {
            rangeLevel *= 1.22;
        } else if (player.getCurseActive()[CurseHandler.LEECH_RANGED]) {
            rangeLevel *= 1.10;
        }
        return (int) (rangeLevel + (player.getBonusManager().getAttackBonus()[4] * 2));
    }

    /**
     * Calculates a player's Ranged defence level.
     *
     * @param player The player to calculate the Ranged defence level for
     * @return The player's Ranged defence level
     */
    public static int getRangedDefence(Player player) {
        int defenceLevel = player.getSkillManager().getCurrentLevel(Skill.DEFENCE);
        if (player.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        } else if (player.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
        } else if (player.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        } else if (player.getPrayerActive()[PrayerHandler.CHIVALRY]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
        } else if (player.getPrayerActive()[PrayerHandler.PIETY]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (player.getPrayerActive()[PrayerHandler.RIGOUR]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (player.getPrayerActive()[PrayerHandler.AUGURY]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (player.getCurseActive()[CurseHandler.TURMOIL]) { // turmoil
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE)
                    * 0.20 + player.getLeechedBonuses()[0];
        }
        return (int) (defenceLevel + player.getBonusManager().getDefenceBonus()[4] + (player.getBonusManager().getDefenceBonus()[4] / 2));
    }

    public static int getMagicAttack(Player player) {
        boolean voidEquipment = EquipmentBonus.wearingVoid(player, CombatType.MAGIC);
        boolean voidEliteEquipment = EquipmentBonus.wearingEliteVoid(player, CombatType.MAGIC);

        int attackLevel = player.getSkillManager().getCurrentLevel(Skill.MAGIC);
        if (voidEquipment)
            attackLevel *= 1.10;
        if (voidEliteEquipment)
            attackLevel *= 1.15;
        if (player.getPrayerActive()[PrayerHandler.MYSTIC_WILL] || player.getCurseActive()[CurseHandler.SAP_MAGE]) {
            attackLevel *= 1.05;
        } else if (player.getPrayerActive()[PrayerHandler.MYSTIC_LORE]) {
            attackLevel *= 1.10;
        } else if (player.getPrayerActive()[PrayerHandler.MYSTIC_MIGHT]) {
            attackLevel *= 1.15;
        } else if (player.getPrayerActive()[PrayerHandler.AUGURY]) {
            attackLevel *= 1.22;
        } else if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == 21054 || player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == 21055 ||
                player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == 21056 || player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == 21057) {
            attackLevel *= 1.15; //BUFF TOP TIER MAGIC NIGHTMARE STAFF - Was 60...
        } else if (player.getCurseActive()[CurseHandler.LEECH_MAGIC]) {
            attackLevel *= 1.18;
        }
        attackLevel *= player.isSpecialActivated() ? player.getCombatSpecial().getAccuracyBonus() : 1;

        return (int) (attackLevel + (player.getBonusManager().getAttackBonus()[3] * 2));
    }

    /**
     * Calculates a player's magic defence level
     *
     * @param player The player to calculate magic defence level for
     * @return The player's magic defence level
     */
    public static int getMagicDefence(Player player) {


        int defenceLevel = player.getSkillManager().getCurrentLevel(Skill.DEFENCE) / 2 + player.getSkillManager().getCurrentLevel(Skill.MAGIC) / 2;

        if (player.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        } else if (player.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
        } else if (player.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        } else if (player.getPrayerActive()[PrayerHandler.CHIVALRY]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
        } else if (player.getPrayerActive()[PrayerHandler.PIETY]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (player.getPrayerActive()[PrayerHandler.RIGOUR]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (player.getPrayerActive()[PrayerHandler.AUGURY]) {
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (player.getCurseActive()[CurseHandler.TURMOIL]) { // turmoil
            defenceLevel += player.getSkillManager().getMaxLevel(Skill.DEFENCE)
                    * 0.20 + player.getLeechedBonuses()[0];
        }

        return (int) (defenceLevel + player.getBonusManager().getDefenceBonus()[3] + (player.getBonusManager().getDefenceBonus()[3] / 3));
    }

    /**
     * Calculates a player's magic max hit
     *
     * @param player The player to calculate magic max hit for
     * @return The player's magic max hit damage
     */
    public static int getMagicMaxhit(Character c) {
        int damage = 0;
        CombatSpell spell = c.getCurrentlyCasting();
        if (spell != null) {
            if (spell.maximumHit() > 0)
                damage += spell.maximumHit();
            else {
                if (c.isNpc()) {
                    damage = ((NPC) c).getDefinition().getMaxHit();
                } else {
                    damage = 1;
                }
            }
        }

        if (c.isNpc()) {
            if (spell == null) {
                damage = Misc.getRandom(((NPC) c).getDefinition().getMaxHit());
            }
            return damage;
        }

        Player p = (Player) c;
        double damageMultiplier = 1;

        switch (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId()) {
            case 4675:
            case 6914:
            case 15246:
                damageMultiplier += .10;
                break;
            case 18355:
                damageMultiplier += .20;
                break;
            case 12284:
                damageMultiplier += .22;
                break;
            case 21054:
            case 21055:
            case 21056:
            case 21057:
                damageMultiplier += .25;
                break;


        }

        boolean specialAttack = p.isSpecialActivated();

        int maxHit = -1;

        if (specialAttack) {
            switch (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId()) {
                case 19780:
                    damage = maxHit = 750;
                    break;
                case 11730:
                    damage = maxHit = 310;
                    break;
                case 19028:
                    damage = maxHit = 310;
                    break;
            }
        } else {
            damageMultiplier += 0.25;
        }

        if (p.getEquipment().getItems()[Equipment.AMULET_SLOT].getId() == 18335) {
            damageMultiplier += .10;
        }

        damage *= damageMultiplier;

        if (maxHit > 0) {
            if (damage > maxHit) {
                damage = maxHit;
            }
        }

        return (int) damage;
    }


    public static int getAttackDelay(Player player) {
        int id = player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId();
        String s = ItemDefinition.forId(id).getName().toLowerCase();
        if (id == -1)
            return 4;// unarmed
        if (id == 18357 || id == 14684 || id == 13051)
            return 3;
        RangedWeaponData rangedData = player.getRangedWeaponData();
        if (rangedData != null) {
            int speed = rangedData.getType().getAttackDelay();
            if (player.getFightType() == FightType.SHORTBOW_RAPID || player.getFightType() == FightType.DART_RAPID || player.getFightType() == FightType.KNIFE_RAPID || player.getFightType() == FightType.THROWNAXE_RAPID || player.getFightType() == FightType.JAVELIN_RAPID) {
                speed--;
            }
            return speed;
        }
        if (id == 18365)
            return 3;
        else if (id == 18349) //CCbow and rapier
            return 4;
        if (id == 18353) // cmaul
            return 7;// chaotic maul

        if (id == 20000)
            return 4;// gs
        if (id == 20001)
            return 4;// gs
        if (id == 20002)
            return 4;// gs
        if (id == 20003)
            return 4;// gs
        if (id == 18349)
            return 5;// chaotic rapier
        if (id == 18353) // cmaul
            return 7;// chaotic maul
        if (id == 16877)
            return 4;// dung 16877 shortbow
        if (id == 19143)
            return 3;// sara shortbow
        if (id == 19146)
            return 4;// guthix shortbow
        if (id == 19149)
            return 3;// zammy shortbow

        switch (id) {
            case 11235:
            case 13405: //dbow
            case 15701: // dark bow
            case 15702: // dark bow
            case 15703: // dark bow
            case 15704: // dark bow
            case 19146: // guthix bow
            case 20998: //twisted bow
                return 9;
            case 13879:
                return 8;
            case 15241: // hand cannon
                return 8;
            case 11730:
                return 4;
            case 14484:
                return 5;
            case 19023:
                return 5;
            case 13883:
                return 6;
            case 10887:
            case 6528:
            case 15039:
                return 7;
            case 4450:
            case 13905:
            case 13907:
                return 5;
            case 18353:
                return 7;
            case 18349:
            case 4706:
            case 18971:
            case 4212:
                return 4;


            case 16403: //long primal
                return 5;
        }

        if (s.endsWith("greataxe"))
            return 7;
        else if (s.equals("torags hammers"))
            return 5;
        else if (s.equals("guthans warspear"))
            return 5;
        else if (s.equals("veracs flail"))
            return 5;
        else if (s.equals("ahrims staff"))
            return 6;
        else if (s.equals("chaotic crossbow"))
            return 4;
        else if (s.contains("staff")) {

            if (s.contains("zamarok") || s.contains("guthix")
                    || s.contains("saradomian") || s.contains("slayer")
                    || s.contains("ancient"))
                return 4;

            else
                return 5;
        } else if (s.contains("aril")) {
            if (s.contains("composite") || s.equals("seercull"))
                return 5;
            else if (s.contains("Ogre"))
                return 8;
            else if (s.contains("short") || s.contains("hunt")
                    || s.contains("sword"))
                return 4;
            else if (s.contains("long") || s.contains("crystal"))
                return 6;
            else if (s.contains("'bow"))
                return 7;

            return 5;
        } else if (s.contains("dagger"))
            return 4;
        else if (s.contains("godsword") || s.contains("2h"))
            return 6;
        else if (s.contains("longsword"))
            return 5;
        else if (s.contains("sword"))
            return 4;
        else if (s.contains("scimitar") || s.contains("katana"))
            return 4;
        else if (s.contains("mace"))
            return 5;
        else if (s.contains("battleaxe"))
            return 6;
        else if (s.contains("pickaxe"))
            return 5;
        else if (s.contains("thrownaxe"))
            return 5;
        else if (s.contains("axe"))
            return 5;
        else if (s.contains("warhammer"))
            return 6;
        else if (s.contains("2h"))
            return 7;
        else if (s.contains("spear"))
            return 5;
        else if (s.contains("claw"))
            return 4;
        else if (s.contains("halberd"))
            return 7;

            // sara sword, 2400ms
        else if (s.equals("granite maul"))
            return 7;
        else if (s.equals("toktz-xil-ak"))// sword
            return 4;
        else if (s.equals("tzhaar-ket-em"))// mace
            return 5;
        else if (s.equals("tzhaar-ket-om"))// maul
            return 7;
        else if (s.equals("chaotic maul"))// maul
            return 7;
        else if (s.equals("toktz-xil-ek"))// knife
            return 4;
        else if (s.equals("toktz-xil-ul"))// rings
            return 4;
        else if (s.equals("toktz-mej-tal"))// staff
            return 6;
        else if (s.contains("whip"))
            return 4;
        else if (s.contains("dart"))
            return 3;
        else if (s.contains("knife"))
            return 3;
        else if (s.contains("javelin"))
            return 6;
        return 5;
    }
}
