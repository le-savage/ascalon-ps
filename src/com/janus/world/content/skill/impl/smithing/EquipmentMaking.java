package com.janus.world.content.skill.impl.smithing;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.*;
import com.janus.model.definitions.ItemDefinition;
import com.janus.util.Misc;
import com.janus.world.content.Sounds;
import com.janus.world.content.Sounds.Sound;
import com.janus.world.content.skill.impl.smithing.BarData.Bars;
import com.janus.world.entity.impl.player.Player;

public class EquipmentMaking {

    public static int[] possibleItems = {1205, 1351, 1422, 1139, 9375, 9377, 9378, 9379, 9380, 9381, 1277, 4819, 1794, 819, 39, 1321, 1265, 1291, 9420, 1155, 864, 1173, 1337, 1375, 1103, 1189, 3095, 1307, 1087, 1075, 1117, 1203, 15298, 1420, 7225, 1137, 9140, 1279, 4820, 820, 40, 1323, 1267, 1293, 1153, 863, 1175, 9423, 1335, 1363, 1101, 4540, 1191, 3096, 1309, 1081, 1067, 1115, 1207, 1353, 1424, 1141, 9141, 1539, 1281, 821, 41, 1325, 1269, 1295, 2370, 9425, 1157, 865, 1177, 1339, 1365, 1105, 1193, 3097, 1311, 1084, 1069, 1119, 1209, 1355, 1428, 1143, 9142, 1285, 4822, 822, 42, 1329, 1273, 1299, 9427, 1159, 866, 1181, 1343, 9416, 1369, 1109, 1197, 3099, 1315, 1085, 1071, 1121, 1211, 1357, 1430, 1145, 9143, 1287, 4823, 823, 43, 1331, 1271, 1301, 9429, 1161, 867, 1183, 1345, 1371, 1111, 1199, 3100, 1317, 1091, 1073, 1123, 1213, 1359, 1432, 1147, 9144, 1289, 4824, 824, 44, 1333, 1275, 1303, 9431, 1163, 868, 1185, 1347, 1373, 1113, 1201, 3101, 1319, 1093, 1079, 1127, 2, 1083, 1349, 1215, 1305, 4587, 1249, 7158, 6739, 1434, 20555, 1377, 14484, 3140, 4087, 4585, 14479, 1149, 11335, 11232, 11237, 15259, 11732, 17926, 4716, 4753, 4724, 4745, 4708, 4720, 4757, 4728, 4749, 4712, 4722, 4759, 4730, 4751, 4714, 4718, 4755, 4726, 4747, 4710, 4732, 4736, 4738, 4734, 4740, 11694, 11718, 11720, 11722, 11696, 11724, 11726, 11728, 10350, 10348, 10346, 10352, 14008, 14009, 14010
    };

    public static void handleNewAnvil(Player player) {
        player.getPacketSender().sendRichPresenceState("Smithing Items!");
        player.getPacketSender().sendSmallImageKey("smithing");
        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.SMITHING));
        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.SMITHING));
        String newbar = searchForNewBars(player);
        if (newbar == null) {
            return;
        } else {
            switch (newbar.toLowerCase()) {
                case "dragon bar":
                    player.setSelectedSkillingItem(21061);
                    SmithingData.makeDragonInterface(player);
                    break;
                case "barrows bar":
                    player.setSelectedSkillingItem(21062);
                    SmithingData.makeBarrowsInterface(player);
                    break;
                case "armadyl bar":
                    player.setSelectedSkillingItem(21063);
                    SmithingData.makeMiscInterface(player);
                    break;
                case "bandos bar":
                    player.setSelectedSkillingItem(21064);
                    SmithingData.makeMiscInterface(player);
                    break;
                case "third-age bar":
                    player.setSelectedSkillingItem(21065);
                    SmithingData.makeMiscInterface(player);
                    break;
                case "torva bar":
                    player.setSelectedSkillingItem(21066);
                    SmithingData.makeMiscInterface(player);
                    break;
            }
        }
    }

    public static void handleAnvil(Player player) {
        String bar = searchForBars(player);
        if (bar == null) {
            return;
        } else {
            switch (bar.toLowerCase()) {
                case "rune bar":
                    player.setSelectedSkillingItem(2363);
                    SmithingData.makeRuneInterface(player);
                    break;
                case "adamant bar":
                    player.setSelectedSkillingItem(2361);
                    SmithingData.makeAddyInterface(player);
                    break;
                case "mithril bar":
                    player.setSelectedSkillingItem(2359);
                    SmithingData.makeMithInterface(player);
                    break;
                case "steel bar":
                    player.setSelectedSkillingItem(2353);
                    SmithingData.makeSteelInterface(player);
                    break;
                case "iron bar":
                    player.setSelectedSkillingItem(2351);
                    SmithingData.makeIronInterface(player);
                    break;
                case "bronze bar":
                    player.setSelectedSkillingItem(2349);
                    SmithingData.showBronzeInterface(player);
                    break;
                case "gold bar":
                    //case "silver bar":
                    player.getPacketSender().sendMessage("Gold bars should be crafted at a furnace.");
                    break;
                case "silver bar":
                    player.getPacketSender().sendMessage("Hmm... Silver isn't the best choice for the anvil.");
                    break;
            }
        }
    }


    public static String searchForBars(Player player) {
        for (int bar : SmithingData.BARS_SMITH_ORDER) {
            if (player.getInventory().contains(bar)) {
                return ItemDefinition.forId(bar).getName();
            }
        }
        return null;
    }

    public static String searchForNewBars(Player player) {
        for (int newbar : SmithingData.NEW_BARS) {
            if (player.getInventory().contains(newbar)) {
                return ItemDefinition.forId(newbar).getName();
            }
        }
        return null;
    }

    public static void smithItem(final Player player, final Item bar, final Item itemToSmith, final int x) {
        boolean canMakeItem = false;

        if (bar.getId() < 0)
            return;
        if (!player.getClickDelay().elapsed(1100)) {
            return;
        }
        player.getSkillManager().stopSkilling();
        if (player.getRights().equals(PlayerRights.PLAYER)) {
            if (!player.getInventory().contains(2347)) {
                player.getPacketSender().sendMessage("You need a Hammer to smith items.");
                player.getPacketSender().sendInterfaceRemoval();
                return;
            }
        }
        if (player.getInventory().getAmount(bar.getId()) < bar.getAmount() || x <= 0) {
            player.getPacketSender().sendMessage("You do not have enough bars to smith this item.");
            return;
        }
        if (SmithingData.getData(itemToSmith, "reqLvl") > player.getSkillManager().getCurrentLevel(Skill.SMITHING)) {
            player.getPacketSender().sendMessage("You need a Smithing level of at least " + SmithingData.getData(itemToSmith, "reqLvl") + " to make this item.");
            return;
        }

        int currentItemId = itemToSmith.getId();

        for (int i = 0; i < possibleItems.length; i++) {
            if (possibleItems[i] == currentItemId) {
                canMakeItem = true;
                break;
            }
        }

        boolean good2go = false;

        for (int i = 0; i < Bars.values().length; i++) {
			    if (bar.getId() == Bars.values()[i].getItemId()
                    && (itemToSmith.getDefinition().getName().startsWith(ItemDefinition.forId(Bars.values()[i].getItemId()).getName().substring(0, 3))
                    || itemToSmith.getDefinition().getName().equalsIgnoreCase("cannonball")
                    || itemToSmith.getDefinition().getName().equalsIgnoreCase("Oil lantern frame"))) {
                good2go = true;
                break;
            }

            if (bar.getId() == Bars.values()[i].getItemId()
                    && (itemToSmith.getDefinition().getName().startsWith("Dharok"))
                    || itemToSmith.getDefinition().getName().startsWith("Verac")
                    || itemToSmith.getDefinition().getName().startsWith("Guthan")
                    || itemToSmith.getDefinition().getName().startsWith("Torag")
                    || itemToSmith.getDefinition().getName().startsWith("Ahrim")
                    || itemToSmith.getDefinition().getName().startsWith("Karil")
                    || itemToSmith.getDefinition().getName().startsWith("Bolt rack")) {
                good2go = true;
                break;
            }
        }

        if (!good2go || !canMakeItem) {
            player.getPacketSender().sendMessage("You have been caught using cheating software goodbye.");
            //PlayerPunishment.addBannedIP(player.getHostAddress());
            //World.deregister(player);
            //World.getPlayerByName
            //PlayerPunishment.ban(player.getUsername());
            //World.sendMessage("<col=b40404>[BUG ABUSE] "+player.getUsername()+" just tried to smith: "+ItemDefinition.forId(currentItemId).getName() + " using bar "+bar.getDefinition().getName());
            player.getPacketSender().sendMessage("How am I going to smith " + Misc.anOrA(ItemDefinition.forId(currentItemId).getName()) + " " + ItemDefinition.forId(currentItemId).getName() + "?");
            player.getSkillManager().stopSkilling();
            return;
        }

        player.getClickDelay().reset();
        player.getPacketSender().sendInterfaceRemoval();
        player.setCurrentTask(new Task(3, player, true) {
            int amountMade = 0;

            @Override
            public void execute() {
                if (player.getInventory().getAmount(bar.getId()) < bar.getAmount() || amountMade >= x) {
                    this.stop();
                    return;
                }
                if (player.getInteractingObject() != null)
                    player.getInteractingObject().performGraphic(new Graphic(2123));
                player.performAnimation(new Animation(898));
                amountMade++;
                Sounds.sendSound(player, Sound.SMITH_ITEM);
                player.getInventory().delete(bar);
                player.getInventory().add(itemToSmith);
                player.getInventory().refreshItems();
                if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Bronze")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Bronze.getExp() * bar.getAmount());
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Bronze bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Iron")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Iron.getExp() * bar.getAmount());
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Iron bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Steel") || ItemDefinition.forId(itemToSmith.getId()).getName().equalsIgnoreCase("Cannonball")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Steel.getExp() * bar.getAmount());
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Steel bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Mith")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Mithril.getExp() * bar.getAmount());
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Mith bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Adamant")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Adamant.getExp() * bar.getAmount());
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Adamant bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Rune") || ItemDefinition.forId(itemToSmith.getId()).getName().contains("Runite")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Rune.getExp() * bar.getAmount());
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Rune bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Dragon")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Dragon.getExp() * bar.getAmount());
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Rune bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (itemToSmith.getDefinition().getName().startsWith("Dharok")
                        || itemToSmith.getDefinition().getName().startsWith("Verac")
                        || itemToSmith.getDefinition().getName().startsWith("Guthan")
                        || itemToSmith.getDefinition().getName().startsWith("Torag")
                        || itemToSmith.getDefinition().getName().startsWith("Ahrim")
                        || itemToSmith.getDefinition().getName().startsWith("Karil")
                        || itemToSmith.getDefinition().getName().startsWith("Bolt rack")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Barrows.getExp() * bar.getAmount());
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Rune bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Armadyl")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Armadyl.getExp() * bar.getAmount());
                    player.getPacketSender().sendRichPresenceState("Smithing Armadyl Bars!");
                    player.getPacketSender().sendSmallImageKey("smithing");
                    player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.SMITHING));

                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Rune bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Bandos")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Bandos.getExp() * bar.getAmount());
                    player.getPacketSender().sendRichPresenceState("Smithing Bandos Bars!");
                    player.getPacketSender().sendSmallImageKey("smithing");
                    player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.SMITHING));
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Rune bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Third")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Third_Age.getExp() * bar.getAmount());
                    player.getPacketSender().sendRichPresenceState("Smithing Third-Age Bars!");
                    player.getPacketSender().sendSmallImageKey("smithing");
                    player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.SMITHING));
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Rune bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else if (ItemDefinition.forId(itemToSmith.getId()).getName().contains("Torva")) {
                    player.getSkillManager().addExperience(Skill.SMITHING, Bars.Torva.getExp() * bar.getAmount());
                    player.getPacketSender().sendRichPresenceState("Smithing Torva Bars!");
                    player.getPacketSender().sendSmallImageKey("smithing");
                    player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.SMITHING));
                    //player.getPacketSender().sendMessage("Using: "+bar.getAmount()+" Rune bars to make: "+ItemDefinition.forId(itemToSmith.getId()).getName());
                } else {
                    player.getPacketSender().sendMessage("ERROR, no experience added. Please report this to staff!");
                }
                //player.getSkillManager().addExperience(Skill.SMITHING, (int) (Bars.Bronze.getExp() * bar.getAmount()));
                //player.getSkillManager().addExperience(Skill.SMITHING, (int) (SmithingData.getData(itemToSmith, "xp")));
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }
}
