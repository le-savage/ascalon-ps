package com.janus.world.content.skill.impl.smithing;

import com.janus.model.Skill;
import com.janus.world.entity.impl.player.Player;

/**
 * Data for melting down bars.
 *
 * @author Flub
 */


public enum ItemMeltData {

    /*** Bronze ***/
    BRONZE_DAGGER(1205, 2349, 0),
    BRONZE_SWORD(1277, 2349, 0),
    BRONZE_MACE(1422, 2349, 0),
    BRONZE_MED_HELM(1139, 2349, 0),
    BRONZE_HATCHET(1351, 2349, 0),


    BRONZE_SCIMITAR(1321, 2349, 0),
    BRONZE_LONG_SWORD(1291, 2349, 0),
    BRONZE_CLAWS(3095, 2349, 0),
    BRONZE_FULL_HELM(1155, 2349, 0),
    BRONZE_SQ_SHIELD(1173, 2349, 0),

    BRONZE_2H(1307, 2349, 0),
    BRONZE_WARHAMMER(1337, 2349, 0),
    BRONZE_BATTLE_AXE(1375, 2349, 0),
    BRONZE_CHAIN_BODY(1103, 2349, 2),
    BRONZE_PLATE_LEGS(1075, 2349, 2),
    BRONZE_PLATE_SKIRT(1087, 2349, 2),
    BRONZE_KITE_SHIELD(1189, 2349, 2),

    BRONZE_PLATE_BODY(1117, 2349, 4),
/*** End Bronze ***/


    /*** IRON ***/
    IRON_DAGGER(1203, 2351, 0),
    IRON_SWORD(1279, 2351, 0),
    IRON_MACE(1420, 2351, 0),
    IRON_MED_HELM(1137, 2351, 0),
    IRON_HATCHET(1349, 2351, 0),


    IRON_SCIMITAR(1323, 2351, 0),
    IRON_LONG_SWORD(1293, 2351, 0),
    IRON_CLAWS(3096, 2351, 0),
    IRON_FULL_HELM(1153, 2351, 0),
    IRON_SQ_SHIELD(1175, 2351, 0),

    IRON_2H(1309, 2351, 0),
    IRON_WARHAMMER(1335, 2351, 0),
    IRON_BATTLE_AXE(1363, 2351, 0),
    IRON_CHAIN_BODY(1101, 2351, 2),
    IRON_PLATE_LEGS(1067, 2351, 2),
    IRON_PLATE_SKIRT(1081, 2351, 2),
    IRON_KITE_SHIELD(1191, 2351, 2),

    IRON_PLATE_BODY(1115, 2351, 4),
/*** End IRON ***/


    /*** STEEL ***/
    STEEL_DAGGER(1207, 2353, 0),
    STEEL_SWORD(1281, 2353, 0),
    STEEL_MACE(1424, 2353, 0),
    STEEL_MED_HELM(1141, 2353, 0),
    STEEL_HATCHET(1353, 2353, 0),


    STEEL_SCIMITAR(1325, 2353, 0),
    STEEL_LONG_SWORD(1295, 2353, 0),
    STEEL_CLAWS(3097, 2353, 0),
    STEEL_FULL_HELM(1157, 2353, 0),
    STEEL_SQ_SHIELD(1177, 2353, 0),

    STEEL_2H(1311, 2353, 0),
    STEEL_WARHAMMER(1339, 2353, 0),
    STEEL_BATTLE_AXE(1365, 2353, 0),
    STEEL_CHAIN_BODY(1105, 2353, 2),
    STEEL_PLATE_LEGS(1069, 2353, 2),
    STEEL_PLATE_SKIRT(1083, 2353, 2),
    STEEL_KITE_SHIELD(1193, 2353, 2),

    STEEL_PLATE_BODY(1119, 2353, 4),
/*** End STEEL ***/


    /*** MITHRIL ***/
    MITHRIL_DAGGER(1209, 2359, 0),
    MITHRIL_SWORD(1285, 2359, 0),
    MITHRIL_MACE(1428, 2359, 0),
    MITHRIL_MED_HELM(1143, 2359, 0),
    MITHRIL_HATCHET(1355, 2359, 0),


    MITHRIL_SCIMITAR(1329, 2359, 0),
    MITHRIL_LONG_SWORD(1299, 2359, 0),
    MITHRIL_CLAWS(3099, 2359, 0),
    MITHRIL_FULL_HELM(1159, 2359, 0),
    MITHRIL_SQ_SHIELD(1181, 2359, 0),

    MITHRIL_2H(1315, 2359, 0),
    MITHRIL_WARHAMMER(1343, 2359, 0),
    MITHRIL_BATTLE_AXE(1369, 2359, 0),
    MITHRIL_CHAIN_BODY(1109, 2359, 2),
    MITHRIL_PLATE_LEGS(1071, 2359, 2),
    MITHRIL_PLATE_SKIRT(1085, 2359, 2),
    MITHRIL_KITE_SHIELD(1197, 2359, 2),

    MITHRIL_PLATE_BODY(1121, 2359, 4),
/*** End MITHRIL ***/


    /*** ADAMANT ***/
    ADAMANT_DAGGER(1211, 2361, 0),
    ADAMANT_SWORD(1287, 2361, 0),
    ADAMANT_MACE(1430, 2361, 0),
    ADAMANT_MED_HELM(1145, 2361, 0),
    ADAMANT_HATCHET(1357, 2361, 0),


    ADAMANT_SCIMITAR(1331, 2361, 0),
    ADAMANT_LONG_SWORD(1301, 2361, 0),
    ADAMANT_CLAWS(3100, 2361, 0),
    ADAMANT_FULL_HELM(1161, 2361, 0),
    ADAMANT_SQ_SHIELD(1183, 2361, 0),

    ADAMANT_2H(1317, 2361, 0),
    ADAMANT_WARHAMMER(1345, 2361, 0),
    ADAMANT_BATTLE_AXE(1371, 2361, 0),
    ADAMANT_CHAIN_BODY(1111, 2361, 2),
    ADAMANT_PLATE_LEGS(1073, 2361, 2),
    ADAMANT_PLATE_SKIRT(1091, 2361, 2),
    ADAMANT_KITE_SHIELD(1199, 2361, 2),

    ADAMANT_PLATE_BODY(1123, 2361, 4),
/*** End ADAMANT ***/


    /*** RUNE ***/
    RUNE_DAGGER(1213, 2363, 0),
    RUNE_SWORD(1289, 2363, 0),
    RUNE_MACE(1432, 2363, 0),
    RUNE_MED_HELM(1147, 2363, 0),
    RUNE_HATCHET(1359, 2363, 0),

    RUNE_SCIMITAR(1333, 2363, 0),
    RUNE_LONG_SWORD(1303, 2363, 0),
    RUNE_CLAWS(3101, 2363, 0),

    RUNE_FULL_HELM(1163, 2363, 0),
    RUNE_SQ_SHIELD(1185, 2363, 0),
    RUNE_2H(1319, 2363, 0),
    RUNE_WARHAMMER(1347, 2363, 0),
    RUNE_BATTLE_AXE(1373, 2363, 0),


    RUNE_CHAIN_BODY(1113, 2363, 2),
    RUNE_PLATE_LEGS(1079, 2363, 2),
    RUNE_PLATE_SKIRT(1093, 2363, 2),
    RUNE_KITE_SHIELD(1201, 2363, 2),

    RUNE_PLATE_BODY(1127, 2363, 4),
/*** End RUNE ***/


    /*** DRAGON ***/
    DRAGON_DAGGER(1215, 21061, 0),
    DRAGON_LONGSWORD(1305, 21061, 0),
    DRAGON_SCIMITAR(4587, 21061, 0),
    DRAGON_SPEAR(1249, 21061, 0),
    DRAGON_MACE(1434, 21061, 0),
    DRAGON_BATTLE_AXE(1377, 21061, 0),
    DRAGON_MED_HELM(1149, 21061, 0),

    DRAGON_HATCHET(6739, 21061, 1),
    DRAGON_2H(7158, 21061, 1),
    DRAGON_PLATELEGS(4087, 21061, 1),
    DRAGON_PLATESKIRT(4585, 21061, 1),

    DRAGON_CHAIN(3140, 21061, 4),

    DRAGON_BOOTS(11732, 21061, 8),

    DRAGON_PICKAXE(15259, 21061, 15),
    DRAGON_PLATEBODY(14479, 21061, 15),
    DRAGON_FULL_HELM(11335, 21061, 15),


    RUINED_DRAGON_LUMP(14472, 21061, 5),
    RUINED_DRAGON_SLICE(14474, 21061, 5),
    RUINED_DRAGON_SHARD(14476, 21061, 5),

/*** End DRAGON ***/


    /*** BARROWS ***/
    DHAROK_HELM(4716, 21062, 4),
    DHAROK_TOP(4720, 21062, 4),
    DHAROK_LEG(4722, 21062, 4),
    DHAROK_WEP(4718, 21062, 4),

    VERAC_HELM(4753, 21062, 4),
    VERAC_TOP(4757, 21062, 4),
    VERAC_LEG(4759, 21062, 4),
    VERAC_WEP(4755, 21062, 4),

    GUTHAN_HELM(4724, 21062, 4),
    GUTHAN_TOP(4728, 21062, 4),
    GUTHAN_LEG(4730, 21062, 4),
    GUTHAN_WEP(4726, 21062, 4),

    TORAG_HELM(4745, 21062, 4),
    TORAG_TOP(4749, 21062, 4),
    TORAG_LEG(4751, 21062, 4),
    TORAG_WEP(4747, 21062, 4),

    AHRIM_HELM(4708, 21062, 4),
    AHRIM_TOP(4712, 21062, 4),
    AHRIM_LEG(4714, 21062, 4),
    AHRIM_WEP(4710, 21062, 4),

    KARIL_HELM(4732, 21062, 4),
    KARIL_TOP(4736, 21062, 4),
    KARIL_LEG(4738, 21062, 4),
    KARIL_WEP(4734, 21062, 4),
/*** End BARROWS ***/

    /*** MISC ***/


    ARMADYL_GODSWORD(11694, 21063, 3),
    ARMADYL_HELM(11718, 21063, 4),
    ARMADYL_TOP(11720, 21063, 4),
    ARMADYL_LEG(11722, 21063, 4),

    BANDOS_GODSWORD(11696, 21064, 3),
    BANDOS_TOP(11724, 21064, 4),
    BANDOS_LEG(11726, 21064, 4),
    BANDOS_BOOTS(11728, 21064, 1),

    THIRD_AGE_HELM(10350, 21065, 4),
    THIRD_AGE_TOP(10348, 21065, 4),
    THIRD_AGE_LEG(10346, 21065, 4),
    THIRD_AGE_SHIELD(10352, 21065, 4),

    TORVA_HELM(14008, 21066, 4),
    TORVA_TOP(14009, 21066, 4),
    TORVA_LEG(14010, 21066, 4);

    public static final int levelReq = 50;
    public static final int[] furnaceIDs = {6096};
    /*** End MISC ***/


    int originalItem, meltedBar, barAmount;

    ItemMeltData(int originalItem, int meltedBar, int barAmount) {
        this.originalItem = originalItem;
        this.meltedBar = meltedBar;
        this.barAmount = barAmount;
    }

    public static ItemMeltData forItem(int item) {
        for (ItemMeltData data : ItemMeltData.values()) {
            if (data.getOriginalItem() == item) {
                return data;
            }
        }
        return null;
    }

    public static boolean isFurnace(int object) {
        for (int i : furnaceIDs)
            if (object == i)
                return true;
        return false;
    }

    public static boolean success(Player player) {
        return true;
    }

    public static boolean canMelt(Player player, int id) {
        ItemMeltData item = forItem(id);
        if (item == null)
            return false;
        if (player.getSkillManager().getMaxLevel(Skill.SMITHING) < levelReq) {
            player.getPacketSender().sendMessage("You must be level " + levelReq + " smithing to melt items.");
            return false;
        }
        if (!player.getInventory().contains(id)) {
            player.getPacketSender().sendMessage("You have no more items to melt down!");
            return false;
        }
        return true;
    }

    public int getOriginalItem() {
        return originalItem;
    }

    public int getMeltedBar() {
        return meltedBar;
    }

    public int getBarAmount() {
        return barAmount;
    }

}
