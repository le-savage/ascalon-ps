package com.janus.world.content.combat.tieredbosses;

import com.janus.GameSettings;
import com.janus.model.MagicSpellbook;
import com.janus.model.Position;
import com.janus.world.World;
import com.janus.world.content.combat.magic.Autocasting;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

public class BossMiniGame {


    public static void StartBossMinigame(Player player) {

        int x = BossFunctions.ARENA_CENTRE.getX();
        int y = BossFunctions.ARENA_CENTRE.getY();
        int z = player.getPosition().getZ();

        NPC firstWaveNPC = new NPC(RandomNPCData.randomFirstWaveID(player), new Position(x, y, z)).setSpawnedFor(player);
        NPC secondWaveNPC = new NPC(RandomNPCData.randomSecondWaveID(player), new Position(x, y, z)).setSpawnedFor(player);
        NPC thirdWaveNPC = new NPC(RandomNPCData.randomThirdWaveID(player), new Position(x, y, z)).setSpawnedFor(player);
        NPC fourthWaveNPC = new NPC(RandomNPCData.randomFourthWaveID(player), new Position(x, y, z)).setSpawnedFor(player);
        NPC fifthWaveNPC = new NPC(RandomNPCData.randomFifthWaveID(player), new Position(x, y, z)).setSpawnedFor(player);

        /** DECLARATIONS **/

        StatSetups firstWaveStats = StatSetups.DEFAULT;
        StatSetups secondWaveStats = StatSetups.DEFAULT;
        StatSetups thirdWaveStats = StatSetups.DEFAULT;
        StatSetups fourthWaveStats = StatSetups.DEFAULT;
        StatSetups fifthWaveStats = StatSetups.DEFAULT;

        EquipmentSetups firstWaveGear = EquipmentSetups.DEFAULT;
        EquipmentSetups secondWaveGear = EquipmentSetups.DEFAULT;
        EquipmentSetups thirdWaveGear = EquipmentSetups.DEFAULT;
        EquipmentSetups fourthWaveGear = EquipmentSetups.DEFAULT;
        EquipmentSetups fifthWaveGear = EquipmentSetups.DEFAULT;

        /** SWITCHES TO MATCH STATS / INVENTORY WITH BOSS **/

        switch (firstWaveNPC.getId()) { // First Wave
            case 51:// Frost Dragon
                firstWaveGear = EquipmentSetups.FROST_DRAGON;
                firstWaveStats = StatSetups.FROST_DRAGON;
                break;
            case 54:// Black Dragon
                firstWaveGear = EquipmentSetups.BLACK_DRAGON;
                firstWaveStats = StatSetups.BLACK_DRAGON;
                break;
            case 50:// King Black Dragon (KBD)
                firstWaveGear = EquipmentSetups.KBD_WAVE_ONE;
                firstWaveStats = StatSetups.KBD_WAVE_ONE;
                break;
        }

        switch (secondWaveNPC.getId()) {
            case 8349:// Tormented Demon
                secondWaveGear = EquipmentSetups.TORMENTED_DEMON;
                secondWaveStats = StatSetups.TORMENTED_DEMON;
                break;
            case 3200:// Chaos Elemental
                secondWaveGear = EquipmentSetups.CHAOS_ELEMENTAL;
                secondWaveStats = StatSetups.CHAOS_ELEMENTAL;
                break;
            case 2882:// Dagganoth Prime
                secondWaveGear = EquipmentSetups.DAGANNOTH_PRIME;
                secondWaveStats = StatSetups.DAGANNOTH_PRIME;
                break;
        }

        switch (thirdWaveNPC.getId()) {
            case 2881:// Dagannoth Supreme
                secondWaveGear = EquipmentSetups.DAGANNOTH_SUPREME;
                secondWaveStats = StatSetups.DAGANNOTH_SUPREME;
                break;
            case 5666:// Barrelchest
                secondWaveGear = EquipmentSetups.BARRELCHEST;
                secondWaveStats = StatSetups.BARREL_CHEST;
                break;
            case 1999:// Cerberus
                secondWaveGear = EquipmentSetups.CERBERUS;
                secondWaveStats = StatSetups.CERBERUS;
                break;
        }

        switch (fourthWaveNPC.getId()) {
            case 499:// Thermonuclear Smoke Devil
                secondWaveGear = EquipmentSetups.THERMONUCLEAR_SMOKEDEVIL;
                secondWaveStats = StatSetups.THERMONUCLEAR_SMOKEDEVIL;
                break;
            case 50:// King Black Dragon (KBD)
                secondWaveGear = EquipmentSetups.KBD_WAVE_TWO;
                secondWaveStats = StatSetups.KBD_WAVE_TWO;
                break;
            case 2883:// Dagannoth Rex
                secondWaveGear = EquipmentSetups.DAGANNOTH_REX;
                secondWaveStats = StatSetups.DAGANNOTH_REX;
                break;
        }

        switch (fifthWaveNPC.getId()) {
            case 3:// Crazy Level 2 Man
                secondWaveGear = EquipmentSetups.CRAZY_LVL2_MAN;
                secondWaveStats = StatSetups.CRAZY_LVL2_MAN;
                break;
            case 1158:// Kalphite Queen
                secondWaveGear = EquipmentSetups.KALPHITE_QUEEN;
                secondWaveStats = StatSetups.KALPHITE_QUEEN;
                break;
            case 7134:// Bork
                secondWaveGear = EquipmentSetups.BORK;
                secondWaveStats = StatSetups.BORK;
                break;
        }

        int[] waveOneGear = {firstWaveGear.getWeapon(), firstWaveGear.getShield(), firstWaveGear.getHelm(), firstWaveGear.getBody(), firstWaveGear.getLegs(), firstWaveGear.getNeck(), firstWaveGear.getCape(), firstWaveGear.getHands(), firstWaveGear.getFeet()};
        int[] waveTwoGear = {secondWaveGear.getWeapon(), secondWaveGear.getShield(), secondWaveGear.getHelm(), secondWaveGear.getBody(), secondWaveGear.getLegs(), secondWaveGear.getNeck(), secondWaveGear.getCape(), secondWaveGear.getHands(), secondWaveGear.getFeet()};
        int[] waveThreeGear = {thirdWaveGear.getWeapon(), thirdWaveGear.getShield(), thirdWaveGear.getHelm(), thirdWaveGear.getBody(), thirdWaveGear.getLegs(), thirdWaveGear.getNeck(), thirdWaveGear.getCape(), thirdWaveGear.getHands(), thirdWaveGear.getFeet()};
        int[] waveFourGear = {fourthWaveGear.getWeapon(), fourthWaveGear.getShield(), fourthWaveGear.getHelm(), fourthWaveGear.getBody(), fourthWaveGear.getLegs(), fourthWaveGear.getNeck(), fourthWaveGear.getCape(), fourthWaveGear.getHands(), fourthWaveGear.getFeet()};
        int[] waveFiveGear = {fifthWaveGear.getWeapon(), fifthWaveGear.getShield(), fifthWaveGear.getHelm(), fifthWaveGear.getBody(), fifthWaveGear.getLegs(), fifthWaveGear.getNeck(), fifthWaveGear.getCape(), fifthWaveGear.getHands(), fifthWaveGear.getFeet()};

        int[] waveOneStats = {firstWaveStats.getAttack(), firstWaveStats.getDefence(), firstWaveStats.getStrength(), firstWaveStats.getRanged(), firstWaveStats.getMagic(), firstWaveStats.getConstitution(), firstWaveStats.getPrayer()};
        int[] waveTwoStats = {secondWaveStats.getAttack(), secondWaveStats.getDefence(), secondWaveStats.getStrength(), secondWaveStats.getRanged(), secondWaveStats.getMagic(), secondWaveStats.getConstitution(), secondWaveStats.getPrayer()};
        int[] waveThreeStats = {thirdWaveStats.getAttack(), thirdWaveStats.getDefence(), thirdWaveStats.getStrength(), thirdWaveStats.getRanged(), thirdWaveStats.getMagic(), thirdWaveStats.getConstitution(), thirdWaveStats.getPrayer()};
        int[] waveFourStats = {fourthWaveStats.getAttack(), fourthWaveStats.getDefence(), fourthWaveStats.getStrength(), fourthWaveStats.getRanged(), fourthWaveStats.getMagic(), fourthWaveStats.getConstitution(), fourthWaveStats.getPrayer()};
        int[] waveFiveStats = {fifthWaveStats.getAttack(), fifthWaveStats.getDefence(), fifthWaveStats.getStrength(), fifthWaveStats.getRanged(), fifthWaveStats.getMagic(), fifthWaveStats.getConstitution(), fifthWaveStats.getPrayer()};


        switch (player.getCurrentBossWave()) {
            case 0:
                BossFunctions.setNewStats(player, waveOneStats[0], waveOneStats[1], waveOneStats[2], waveOneStats[3], waveOneStats[4], waveOneStats[5], waveOneStats[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, waveOneGear[0], waveOneGear[1], waveOneGear[2], waveOneGear[3], waveOneGear[4], waveOneGear[5], waveOneGear[6], waveOneGear[7], waveOneGear[8]);
                World.register(firstWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(firstWaveNPC);
                break;

            case 1:
                BossFunctions.setNewStats(player, waveTwoStats[0], waveTwoStats[1], waveTwoStats[2], waveTwoStats[3], waveTwoStats[4], waveTwoStats[5], waveTwoStats[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, waveTwoGear[0], waveTwoGear[1], waveTwoGear[2], waveTwoGear[3], waveTwoGear[4], waveTwoGear[5], waveTwoGear[6], waveTwoGear[7], waveTwoGear[8]);
                World.register(secondWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(secondWaveNPC);
                break;

            case 2:
                BossFunctions.setNewStats(player, waveThreeStats[0], waveThreeStats[1], waveThreeStats[2], waveThreeStats[3], waveThreeStats[4], waveThreeStats[5], waveThreeStats[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, waveThreeGear[0], waveThreeGear[1], waveThreeGear[2], waveThreeGear[3], waveThreeGear[4], waveThreeGear[5], waveThreeGear[6], waveThreeGear[7], waveThreeGear[8]);
                World.register(thirdWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(thirdWaveNPC);
                break;

            case 3:
                BossFunctions.setNewStats(player, waveFourStats[0], waveFourStats[1], waveFourStats[2], waveFourStats[3], waveFourStats[4], waveFourStats[5], waveFourStats[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, waveFourGear[0], waveFourGear[1], waveFourGear[2], waveFourGear[3], waveFourGear[4], waveFourGear[5], waveFourGear[6], waveFourGear[7], waveFourGear[8]);
                World.register(fourthWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(fourthWaveNPC);
                break;

            case 4:
                BossFunctions.setNewStats(player, waveFiveStats[0], waveFiveStats[1], waveFiveStats[2], waveFiveStats[3], waveFiveStats[4], waveFiveStats[5], waveFiveStats[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, waveFiveGear[0], waveFiveGear[1], waveFiveGear[2], waveFiveGear[3], waveFiveGear[4], waveFiveGear[5], waveFiveGear[6], waveFiveGear[7], waveFiveGear[8]);
                World.register(fifthWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(fifthWaveNPC);
                player.setSpellbook(MagicSpellbook.NORMAL);
                Autocasting.resetAutocast(player, true);
                player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId()).sendMessage("Your magic spellbook is changed..");
                Autocasting.handleAutocast(player, 1181);
                break;

            case 5:
            case 6:
            case 7:
                player.setCurrentBossWave(0);
                BossFunctions.setNewStats(player, waveOneStats[0], waveOneStats[1], waveOneStats[2], waveOneStats[3], waveOneStats[4], waveOneStats[5], waveOneStats[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, waveOneGear[0], waveOneGear[1], waveOneGear[2], waveOneGear[3], waveOneGear[4], waveOneGear[5], waveOneGear[6], waveOneGear[7], waveOneGear[8]);
                World.register(firstWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(firstWaveNPC);
                break;
        }
        InventorySetups.giveItems(player);
    }
}