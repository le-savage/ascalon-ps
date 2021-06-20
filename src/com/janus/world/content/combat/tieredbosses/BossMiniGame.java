package com.janus.world.content.combat.tieredbosses;

import com.janus.GameSettings;
import com.janus.model.MagicSpellbook;
import com.janus.model.Position;
import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.content.combat.magic.Autocasting;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

public class BossMiniGame {


    public static void StartBossMinigame(Player player) {

        StatSetups.firstWave firstWaveStats = StatSetups.firstWave.KBD;
        StatSetups.secondWave secondWaveStats = StatSetups.secondWave.KBD;
        StatSetups.thirdWave thirdWaveStats = StatSetups.thirdWave.KBD;
        StatSetups.fourthWave fourthWaveStats = StatSetups.fourthWave.KBD;
        StatSetups.fifthWave fifthWaveStats = StatSetups.fifthWave.KBD;

        int[] stats1 = {firstWaveStats.getAttack(), firstWaveStats.getDefence(), firstWaveStats.getStrength(), firstWaveStats.getRanged(), firstWaveStats.getMagic(), firstWaveStats.getConstitution(), firstWaveStats.getPrayer()};
        int[] stats2 = {secondWaveStats.getAttack(), secondWaveStats.getDefence(), secondWaveStats.getStrength(), secondWaveStats.getRanged(), secondWaveStats.getMagic(), secondWaveStats.getConstitution(), secondWaveStats.getPrayer()};
        int[] stats3 = {thirdWaveStats.getAttack(), thirdWaveStats.getDefence(), thirdWaveStats.getStrength(), thirdWaveStats.getRanged(), thirdWaveStats.getMagic(), thirdWaveStats.getConstitution(), thirdWaveStats.getPrayer()};
        int[] stats4 = {fourthWaveStats.getAttack(), fourthWaveStats.getDefence(), fourthWaveStats.getStrength(), fourthWaveStats.getRanged(), fourthWaveStats.getMagic(), fourthWaveStats.getConstitution(), fourthWaveStats.getPrayer()};
        int[] stats5 = {fifthWaveStats.getAttack(), fifthWaveStats.getDefence(), fifthWaveStats.getStrength(), fifthWaveStats.getRanged(), fifthWaveStats.getMagic(), fifthWaveStats.getConstitution(), fifthWaveStats.getPrayer()};


        EquipmentSetups firstWaveGear = EquipmentSetups.FIRST_WAVE;
        EquipmentSetups secondWaveGear = EquipmentSetups.SECOND_WAVE;
        EquipmentSetups thirdWaveGear = EquipmentSetups.THIRD_WAVE;
        EquipmentSetups fourthWaveGear = EquipmentSetups.FOURTH_WAVE;
        EquipmentSetups fifthWaveGear = EquipmentSetups.FIFTH_WAVE;

        int[] gearOne = {firstWaveGear.getWeapon(), firstWaveGear.getShield(), firstWaveGear.getHelm(), firstWaveGear.getBody(), firstWaveGear.getLegs(), firstWaveGear.getNeck(), firstWaveGear.getCape(), firstWaveGear.getHands(), firstWaveGear.getFeet()};
        int[] gearTwo = {secondWaveGear.getWeapon(), secondWaveGear.getShield(), secondWaveGear.getHelm(), secondWaveGear.getBody(), secondWaveGear.getLegs(), secondWaveGear.getNeck(), secondWaveGear.getCape(), secondWaveGear.getHands(), secondWaveGear.getFeet()};
        int[] gearThree = {thirdWaveGear.getWeapon(), thirdWaveGear.getShield(), thirdWaveGear.getHelm(), thirdWaveGear.getBody(), thirdWaveGear.getLegs(), thirdWaveGear.getNeck(), thirdWaveGear.getCape(), thirdWaveGear.getHands(), thirdWaveGear.getFeet()};
        int[] gearFour = {fourthWaveGear.getWeapon(), fourthWaveGear.getShield(), fourthWaveGear.getHelm(), fourthWaveGear.getBody(), fourthWaveGear.getLegs(), fourthWaveGear.getNeck(), fourthWaveGear.getCape(), fourthWaveGear.getHands(), fourthWaveGear.getFeet()};
        int[] gearFive = {fifthWaveGear.getWeapon(), fifthWaveGear.getShield(), fifthWaveGear.getHelm(), fifthWaveGear.getBody(), fifthWaveGear.getLegs(), fifthWaveGear.getNeck(), fifthWaveGear.getCape(), fifthWaveGear.getHands(), fifthWaveGear.getFeet()};


        int x = BossFunctions.ARENA_CENTRE.getX();
        int y = BossFunctions.ARENA_CENTRE.getY();
        int z = player.getPosition().getZ();

       /* NPC firstWaveNPC = new NPC(BossNPCData.FIRST_WAVE_IDS.getFirstWaveID(), new Position(x, y, z)).setSpawnedFor(player);
        NPC secondWaveNPC = new NPC(BossNPCData.FIRST_WAVE_IDS.getSecondWaveID(), new Position(x, y, z)).setSpawnedFor(player);
        NPC thirdWaveNPC = new NPC(BossNPCData.FIRST_WAVE_IDS.getThirdWaveID(), new Position(x, y, z)).setSpawnedFor(player);
        NPC fourthWaveNPC = new NPC(BossNPCData.FIRST_WAVE_IDS.getFourthWaveID(), new Position(x, y, z)).setSpawnedFor(player);
        NPC fifthWaveNPC = new NPC(BossNPCData.FIRST_WAVE_IDS.getFifthWaveID(), new Position(x, y, z)).setSpawnedFor(player);
*/


        NPC firstWaveNPC = new NPC(RandomNPCData.randomFirstWaveID(player), new Position(x, y, z)).setSpawnedFor(player);
        NPC secondWaveNPC = new NPC(RandomNPCData.randomSecondWaveID(player), new Position(x, y, z)).setSpawnedFor(player);
        NPC thirdWaveNPC = new NPC(RandomNPCData.randomThirdWaveID(player), new Position(x, y, z)).setSpawnedFor(player);
        NPC fourthWaveNPC = new NPC(RandomNPCData.randomFourthWaveID(player), new Position(x, y, z)).setSpawnedFor(player);
        NPC fifthWaveNPC = new NPC(RandomNPCData.randomFifthWaveID(player), new Position(x, y, z)).setSpawnedFor(player);

        System.out.println("1: " + firstWaveNPC.getDefinition().getName());
        System.out.println("2: " + secondWaveNPC.getDefinition().getName());
        System.out.println("3: " + thirdWaveNPC.getDefinition().getName());
        System.out.println("4: " + fourthWaveNPC.getDefinition().getName());
        System.out.println("5: " + fifthWaveNPC.getDefinition().getName());



        switch (player.getKbdTier()) {
            case 0:
                BossFunctions.setNewStats(player, stats1[0], stats1[1], stats1[2], stats1[3], stats1[4], stats1[5], stats1[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, gearOne[0], gearOne[1], gearOne[2], gearOne[3], gearOne[4], gearOne[5], gearOne[6], gearOne[7], gearOne[8]);
                World.register(firstWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(firstWaveNPC);
                break;

            case 1:
                BossFunctions.setNewStats(player, stats2[0], stats2[1], stats2[2], stats2[3], stats2[4], stats2[5], stats2[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, gearTwo[0], gearTwo[1], gearTwo[2], gearTwo[3], gearTwo[4], gearTwo[5], gearTwo[6], gearTwo[7], gearTwo[8]);
                World.register(secondWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(secondWaveNPC);
                break;

            case 2:
                BossFunctions.setNewStats(player, stats3[0], stats3[1], stats3[2], stats3[3], stats3[4], stats3[5], stats3[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, gearThree[0], gearThree[1], gearThree[2], gearThree[3], gearThree[4], gearThree[5], gearThree[6], gearThree[7], gearThree[8]);
                World.register(thirdWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(thirdWaveNPC);
                break;

            case 3:
                BossFunctions.setNewStats(player, stats4[0], stats4[1], stats4[2], stats4[3], stats4[4], stats4[5], stats4[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, gearFour[0], gearFour[1], gearFour[2], gearFour[3], gearFour[4], gearFour[5], gearFour[6], gearFour[7], gearFour[8]);
                World.register(fourthWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(fourthWaveNPC);
                break;

            case 4:
                BossFunctions.setNewStats(player, stats5[0], stats5[1], stats5[2], stats5[3], stats5[4], stats5[5], stats5[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, gearFive[0], gearFive[1], gearFive[2], gearFive[3], gearFive[4], gearFive[5], gearFive[6], gearFive[7], gearFive[8]);
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
                player.setKbdTier(0);
                BossFunctions.setNewStats(player, stats1[0], stats1[1], stats1[2], stats1[3], stats1[4], stats1[5], stats1[6]);
                player.getInventory().deleteAll();
                if (player.getSummoning().getFamiliar() != null) {
                    player.getSummoning().unsummon(true, true);
                }
                BossFunctions.setEquipment(player, gearOne[0], gearOne[1], gearOne[2], gearOne[3], gearOne[4], gearOne[5], gearOne[6], gearOne[7], gearOne[8]);
                World.register(firstWaveNPC);
                player.getRegionInstance().getNpcsList().addIfAbsent(firstWaveNPC);
                break;
        }
        InventorySetups.giveItems(player);
    }
}