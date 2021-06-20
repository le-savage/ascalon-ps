package com.janus.world.content.combat.tieredbosses;

import com.janus.util.Misc;
import com.janus.world.entity.impl.player.Player;

public class RandomNPCData {

    public static final int[] firstWaveIDs = {3, 50, 0, 5666, 7286};
    public static final int[] secondWaveIDs = {3, 50, 0, 5666, 7286};
    public static final int[] thirdWaveIDs = {3, 50, 0, 5666, 7286};
    public static final int[] fourthWaveIDs = {3, 50, 0, 5666, 7286};
    public static final int[] fifthWaveIDs = {3, 50, 0, 5666, 7286};

    public static int randomFirstWaveID(Player player) {
        System.out.println("RANDOM NPC 1 SELECTED");
        return (firstWaveIDs[Misc.getRandom(firstWaveIDs.length - 1)]);
    }

    public static int randomSecondWaveID(Player player) {
        System.out.println("RANDOM NPC 2 SELECTED");
        return (secondWaveIDs[Misc.getRandom(secondWaveIDs.length - 1)]);
    }

    public static int randomThirdWaveID(Player player) {
        System.out.println("RANDOM NPC 3 SELECTED");
        return (thirdWaveIDs[Misc.getRandom(thirdWaveIDs.length - 1)]);
    }

    public static int randomFourthWaveID(Player player) {
        System.out.println("RANDOM NPC 4 SELECTED");
        return (fourthWaveIDs[Misc.getRandom(fourthWaveIDs.length - 1)]);
    }

    public static int randomFifthWaveID(Player player) {
        System.out.println("RANDOM NPC 5 SELECTED");
        return (fifthWaveIDs[Misc.getRandom(fifthWaveIDs.length - 1)]);
    }

}
