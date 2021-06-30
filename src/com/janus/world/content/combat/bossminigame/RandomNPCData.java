package com.janus.world.content.combat.bossminigame;

import com.janus.util.Misc;
import com.janus.world.entity.impl.player.Player;

public class RandomNPCData { //CHANGE NPC'S IN HERE.. THESE ARE NPC ID'S

    public static final int[] firstWaveIDs = {51, 54, 50}; //dragon tier
    public static final int[] secondWaveIDs = {8349, 3200, 2882}; //mage tier
    public static final int[] thirdWaveIDs = {2881, 5666, 1999,}; //melee tier
    public static final int[] fourthWaveIDs = {499, 50, 2883}; //mage tier
    public static final int[] fifthWaveIDs = {3, 2001, 7134}; //boss tier

    public static int randomFirstWaveID() {
        System.out.println("RANDOM NPC 1 SELECTED");
        return (firstWaveIDs[Misc.getRandom(firstWaveIDs.length - 1)]);
    }

    public static int randomSecondWaveID() {
        System.out.println("RANDOM NPC 2 SELECTED");
        return (secondWaveIDs[Misc.getRandom(secondWaveIDs.length - 1)]);
    }

    public static int randomThirdWaveID() {
        System.out.println("RANDOM NPC 3 SELECTED");
        return (thirdWaveIDs[Misc.getRandom(thirdWaveIDs.length - 1)]);
    }

    public static int randomFourthWaveID() {
        System.out.println("RANDOM NPC 4 SELECTED");
        return (fourthWaveIDs[Misc.getRandom(fourthWaveIDs.length - 1)]);
    }

    public static int randomFifthWaveID() {
        System.out.println("RANDOM NPC 5 SELECTED");
        return (fifthWaveIDs[Misc.getRandom(fifthWaveIDs.length - 1)]);
    }

}
