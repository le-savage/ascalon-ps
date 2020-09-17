package com.janus.world.content.combat.effect;

import com.janus.model.container.impl.Equipment;
import com.janus.world.content.combat.CombatType;
import com.janus.world.entity.impl.player.Player;

public class EquipmentBonus {

    public static final int[][] VOID_ARMOUR = {
            {Equipment.BODY_SLOT, 8839},
            {Equipment.LEG_SLOT, 8840},
            {Equipment.HANDS_SLOT, 8842}
    };
    public static final int[][] ELITE_VOID_ARMOUR = {
            {Equipment.BODY_SLOT, 19785},
            {Equipment.LEG_SLOT, 19786},
            {Equipment.HANDS_SLOT, 8842}
    };
    private static final int MAGE_VOID_HELM = 11663;

    private static final int RANGED_VOID_HELM = 11664;

    private static final int MELEE_VOID_HELM = 11665;

    private static final int VOID_KNIGHT_DEFLECTOR = 19712;

    public static boolean wearingVoid(Player player, CombatType attackType) {
        int correctEquipment = 0;
        int helmet = attackType == CombatType.MAGIC ? MAGE_VOID_HELM : attackType == CombatType.RANGED ? RANGED_VOID_HELM : MELEE_VOID_HELM;
        for (int[] armour : VOID_ARMOUR) {
            if (player.getEquipment().getItems()[armour[0]].getId() == armour[1]) {
                correctEquipment++;
            }
        }
        if (player.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == VOID_KNIGHT_DEFLECTOR) {
            correctEquipment++;
        }
        return correctEquipment >= 3 && player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == helmet;
    }

    /** Detect Full Armour Sets Used For Bonuses in DesolaceFormulas.java **/

    /** Boolean to detect Full Justicar **/

    public static boolean fullJusticar(Player player) {
        return (player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 18891) &&
                (player.getEquipment().getItems()[Equipment.BODY_SLOT].getId() == 18892) &&
                (player.getEquipment().getItems()[Equipment.LEG_SLOT].getId() == 18893);
    }

    /** Boolean to detect Full Torva **/

    public static boolean fullTorva(Player player) {
        return (player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 14008) &&
                (player.getEquipment().getItems()[Equipment.BODY_SLOT].getId() == 14009) &&
                (player.getEquipment().getItems()[Equipment.LEG_SLOT].getId() == 14010);
    }

    /** Boolean to detect Full Pernix **/

    public static boolean fullPernix(Player player) {
        return (player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 14011) &&
                (player.getEquipment().getItems()[Equipment.BODY_SLOT].getId() == 14012) &&
                (player.getEquipment().getItems()[Equipment.LEG_SLOT].getId() == 14013);
    }

    /** Boolean to detect Full Primal **/

    public static boolean fullPrimal(Player player) {
        return (player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 16711) &&
                (player.getEquipment().getItems()[Equipment.BODY_SLOT].getId() == 17259) &&
                (player.getEquipment().getItems()[Equipment.LEG_SLOT].getId() == 16689) &&
                (player.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 17361) &&
                (player.getEquipment().getItems()[Equipment.FEET_SLOT].getId() == 16359) &&
                (player.getEquipment().getItems()[Equipment.HANDS_SLOT].getId() == 16293);
    }

    public static boolean wearingEliteVoid(Player player, CombatType attackType) {
        int correctEquipment = 0;
        int helmet = attackType == CombatType.MAGIC ? MAGE_VOID_HELM :
                attackType == CombatType.RANGED ? RANGED_VOID_HELM : MELEE_VOID_HELM;
        for (int[] armour : ELITE_VOID_ARMOUR) {
            if (player.getEquipment().getItems()[armour[0]].getId() == armour[1]) {
                correctEquipment++;
            }
        }
        if (player.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == VOID_KNIGHT_DEFLECTOR) {
            correctEquipment++;
        }
        return correctEquipment >= 3 && player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == helmet;
    }

    public static boolean scytheEquipped(Player player) {
        return (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == 18899);
    }
}
