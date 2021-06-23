package com.janus.world.content.minigames.impl;

import com.janus.model.*;
import com.janus.model.Locations.Location;
import com.janus.model.RegionInstance.RegionInstanceType;
import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

/**
 * Handles the Barrows minigame and it's objects, npcs, etc.
 *
 * @editor Gabbe
 */
public class NewBarrows {

    public static final Position entrance = new Position(3565, 3313);

    public static final int ahrimID = 2025;
    public static final int dharokID = 2026;
    public static final int guthanID = 2027;
    public static final int karilID = 2028;
    public static final int toragID = 2029;
    public static final int veracID = 2030;


    public static int[] rewards = {4708, 4710, 4712, 4714, 4708, 4710, 4712, 4714, 4716, 4718, 4720,
            4722, 4724, 4718, 4720,
            4722, 4724, 4718, 4720,
            4722, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4745, 4747,
            4749, 4751, 4753, 4755, 4757, 4759};


    public static void spawnBrothers(Player player) { //TODO Check where to initialise this

        player.setPlayedNewBarrows(true);

        player.getMinigameAttributes().getBarrowsMinigameAttributes().setKillcount(0);//Reset the players KC each time the game starts

         NPC ahrim = new NPC(ahrimID, new Position(3566, 3288, player.getIndex() * 4));
         NPC dharok = new NPC(dharokID, new Position(3568, 3289, player.getIndex() * 4));
         NPC guthan = new NPC(guthanID, new Position(3566, 3290, player.getIndex() * 4));
         NPC karil = new NPC(karilID, new Position(3564, 3289, player.getIndex() * 4));
         NPC torag = new NPC(toragID, new Position(3563, 3291, player.getIndex() * 4));
         NPC verac = new NPC(veracID, new Position(3566, 3285, player.getIndex() * 4));

        player.getPacketSender().sendInterfaceRemoval();

            if (player.getLocation() == Location.BARROWS) {
                player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY(), player.getIndex() * 4));
                player.setRegionInstance(new RegionInstance(player, RegionInstanceType.BARROWS));
                
                ahrim.setSpawnedFor(player);
                ahrim.getCombatBuilder().attack(player);
                World.register(ahrim);
                player.getRegionInstance().getNpcsList().add(ahrim);

                dharok.setSpawnedFor(player);
                dharok.getCombatBuilder().attack(player);
                World.register(dharok);
                player.getRegionInstance().getNpcsList().add(dharok);

                guthan.setSpawnedFor(player);
                guthan.getCombatBuilder().attack(player);
                World.register(guthan);
                player.getRegionInstance().getNpcsList().add(guthan);

                karil.setSpawnedFor(player);
                karil.getCombatBuilder().attack(player);
                World.register(karil);
                player.getRegionInstance().getNpcsList().add(karil);

                torag.setSpawnedFor(player);
                torag.getCombatBuilder().attack(player);
                World.register(torag);
                player.getRegionInstance().getNpcsList().add(torag);

                verac.setSpawnedFor(player);
                verac.getCombatBuilder().attack(player);
                World.register(verac);
                player.getRegionInstance().getNpcsList().add(verac);
            }
    }

    public static void resetBarrows(Player player) {
        System.out.println("RESET BARROWS GAME FOR "+player.getUsername());
        System.out.println("OLD KC: "+player.getBarrowsKC());

        player.setBarrowsKC(0);

        System.out.println("NEW KC AFTER RESET: "+player.getBarrowsKC());

        if (player.getRegionInstance() != null) {
            player.getRegionInstance().getNpcsList().remove(player);
            player.getRegionInstance().destruct();
        }
    }

    public static int randomReward() {
        return rewards[(int) (Math.random() * rewards.length)];
    }

    public static void rewardPlayer(Player player) {
        int random = Misc.random(0, 100);
        int randomAmountOfCoins = Misc.random(500000, 10000000);

        if (random < 74) {
            player.forceChat("I rolled " + random +".. I need 75+ to gain a barrows piece!");
            player.getPacketSender().sendMessage("Sorry, better luck next time! Enjoy "+ Misc.setupMoney(randomAmountOfCoins) +" for your effort!");
            player.getInventory().add(995, randomAmountOfCoins);
        }

        if (random >= 75 && random <= 95) {
            player.forceChat("I needed 75 or more to win.. I rolled "+random+"!");
            player.getInventory().add(randomReward(), 1);
        }
        if (random >= 95 && random <= 99) {
            player.forceChat("I just rolled "+random+" and won a double prize!");
            player.getInventory().add(randomReward(), 1);
            player.getInventory().add(randomReward(), 1);
        }
        if (random == 100) {
            player.forceChat("Wow! I just rolled "+random+" and won a triple prize!");
            World.sendFilteredMessage("@blu@[BARROWS] @red@ "+player.getUsername() + " just won a @blu@ triple @red@ prize at barrows!");
            player.getInventory().add(randomReward(), 1);
            player.getInventory().add(randomReward(), 1);
            player.getInventory().add(randomReward(), 1);
        }
    }

}
