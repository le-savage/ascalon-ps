/*
package com.janus.world.content;

import com.janus.util.Misc;
import com.janus.util.RandomUtility;
import com.janus.world.entity.impl.player.Player;

public class DonationBox {
	
	*/
/*
 * Rewards
 *//*

	public static final int [] shitRewards = {4151, 6585, 2572, 6199, 1543};
	public static final int [] goodRewards = {19780, 14484, 4450, 12926, 12284 ,13045, 20998, 21026, 14484, 11696, 11613, 19780, 13263, 18349, 18351,
			18353, 18355, 18357, 18359, 20000, 20001, 20002};
	
	*/
/*
 * Handles the opening of the donation box
 *//*

	public static void open (Player player) {
		if (player.getInventory().getFreeSlots() < 3) {
			player.getPacketSender().sendMessage("You need at least 3 inventory spaces");
			return;
		}
			//fk
		player.getInventory().delete(6183, 1);
		giveReward(player);
		player.getPacketSender().sendMessage("Congratulations on your reward!");
	}
	
	*/
/*
 * Gives the reward base on misc Random chance
 *//*

	public static void giveReward(Player player) {
		*/
/*
 * 1/3 Chance for a good reward
 *//*

		if (RandomUtility.RANDOM.nextInt(3) == 2) {
			player.getInventory().add(goodRewards[Misc.getRandom(goodRewards.length - 1)], 1);
		} else {
			player.getInventory().add(shitRewards[Misc.getRandom(shitRewards.length - 1)], 1);

		}
		*/
/*
 * Adds 5m + a random amount up to 100m every box
 * Max cash reward = 105m
 *//*

		player.getInventory().add(995, 10000000 + RandomUtility.RANDOM.nextInt(100000000));
	}

}
*/
