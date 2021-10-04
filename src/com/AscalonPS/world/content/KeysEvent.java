/*
package com.janus.world.content;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.Animation;
import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.entity.impl.player.Player;



public class KeysEvent {
	
	//Item ids that will be dropped
	public static int pvmKey = 1543;
	
	//useless, just needed to write down object id
	public static int chest = 6420;

	public static int rareLoots[] = {15606, 15608, 15610, 15612, 15614, 15616, 15618, 15620, 15622, 7671, 7673, 10840, 989, 989, 989, 989, 19335, 6739, 15259, 11720, 11722, 11724, 11726, 18782, 18782, 18782, 18782, };//3RD BEST
	
	public static int ultraLoots[] = {9470, 4084, 4084, 9921, 9922, 9923, 9924, 9925, 15352, 5607, 4565, 10330, 10332, 10334, 10336, 10338, 10340, 10342, 10344, 10346, 10348, 10350, 10352};//2ND BEST
	
	public static int amazingLoots[] = {1038, 1040, 1042, 1044, 1046, 1048, 1050, 1051, 10735, 14049, 14051, 14046, 14045, 14047, };//BEST
	
	public static int commonLoots[] = {4151, 6585, 11732, 536, 2577, 2581, 6328, 6916, 6918, 6920, 6922, 6924, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4753, 4755, 4757, 4759, 4747, 4749, 4745, 4751, 10025, 6570};//WORST
	*/
/*
 * Start methods below
 *//*



 */
/*
 * Grabs a random item from aray
 *//*

	public static int getRandomItem(int[] array) {
		return array[Misc.getRandom(array.length - 1)];
	}

	
	*/
/*
 * Opening the chest with suspense, give reward
 *//*

	public static void openChest(Player player) {
		if(!player.getClickDelay().elapsed(1000))
			return;
		if (player.getInventory().contains(1543)) {

		
			TaskManager.submit(new Task(0, player, true) {
			@Override
			public void execute() {
				player.performAnimation(new Animation(6387));
				player.getPacketSender().sendMessage("Opening Chest...");
				player.getInventory().delete(1543, 1);
				giveReward(player);
				this.stop();
			}
		});
      } else {
		  
    	  player.getPacketSender().sendMessage("You require a wilderness key to open this chest!");
    	  return;
      }
	 
	}
	
	public static void giveReward(Player player) {
		if (Misc.getRandom(20) == 5) { //Rare Item
			int rareDrops = getRandomItem(rareLoots);
			player.getInventory().add(rareDrops, 1);
			World.sendMessage("@or3@[Red Key Chest]@bla@ "+player.getUsername()+ " has received a Rare from the chest!");
		} else if (Misc.getRandom(225) == 147) {//Ultra Rare items
			World.sendMessage("@or3@[Red Key Chest]@bla@ "+player.getUsername()+ " has received an Ultra Rare from the chest!");
			int ultraDrops = getRandomItem(ultraLoots);
			player.getInventory().add(ultraDrops, 1);
		} else if (Misc.getRandom(400) == 388) {//Amazing items
			World.sendMessage("@or3@[Red Key Chest]@bla@ "+player.getUsername()+ " has received a Legendary Reward from the chest!!");
			int amazingDrops = getRandomItem(amazingLoots);
			player.getInventory().add(amazingDrops, 1);
		} else {//Common items
			int commonDrops = getRandomItem(commonLoots);
			player.getInventory().add(commonDrops, 1);
		}
			
	}
	
}*/

package com.AscalonPS.world.content;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.World;
import com.AscalonPS.world.entity.impl.player.Player;


public class KeysEvent {

    //Item ids that will be dropped
    public static int pvmKey = 1543;

    //useless, just needed to write down object id
    public static int chest = 6420;
    public static int ultraLoots[] = {11724, 11726, 11728, 11720, 11722, 11730, 11732, 19335, 15019, 15018, 15020, 6585, 15356};

    public static int amazingLoots[] = {21026, 20059, 15352, 5607, 4565, 10330, 10332, 10334, 10336, 10338, 10340, 10342, 10344, 10346, 10348, 10350, 10352, 1038, 1040, 1042, 1044, 1046, 1048, 1050};//2ND BEST

    public static int rareLoots[] = {2577, 2581, 6328, 6916, 6918, 6920, 6922, 6924, 4724, 4726, 4728, 4730, 4732, 4734, 4736, 4738, 4753, 4755, 4757, 4759, 4747, 4749, 4745, 4751, 10025};

    public static int commonLoots[] = {1067, 1069, 1071, 1073, 1077, 1079, 1099, 1135, 1149, 1187, 1249, 1305, 1377, 9470, 4084, 9921, 9922, 9923, 9924, 9925, 10735, 15259, 9185, 4131, 19336, 19337, 19338, 19339, 19340, 6199, 2678, 2677, 2685, 2679, 2681, 2684, 2682, 2683};//WORST
    /*
     * Start methods below
     */


    /*
     * Grabs a random item from aray
     */
    public static int getRandomItem(int[] array) {
        return array[Misc.getRandom(array.length - 1)];
    }


    /*
     * Opening the chest with suspense, give reward
     */
    public static void openChest(Player player) {
        if (!player.getClickDelay().elapsed(1000))
            return;
        if (player.getInventory().contains(1543)) {


            TaskManager.submit(new Task(0, player, true) {
                @Override
                public void execute() {
                    player.performAnimation(new Animation(6387));
                    player.getPacketSender().sendMessage("Opening Chest...");
                    player.getInventory().delete(1543, 1);
                    giveReward(player);
                    this.stop();
                }
            });
        } else {

            player.getPacketSender().sendMessage("You require a wilderness key to open this chest!");
            return;
        }

    }

    public static void giveReward(Player player) {
        if (Misc.getRandom(100) == 30) { //Rare Item
            int rareDrops = getRandomItem(rareLoots);
            player.getInventory().add(rareDrops, 1);
            World.sendMessage("@or3@[Red Key Chest]@bla@ " + player.getUsername() + " has received a Rare from the chest!");
        } else if (Misc.getRandom(400) == 320) {//Ultra Rare items
            World.sendMessage("@or3@[Red Key Chest]@bla@ " + player.getUsername() + " has received an Ultra Rare from the chest!");
            int ultraDrops = getRandomItem(ultraLoots);
            player.getInventory().add(ultraDrops, 1);
        } else if (Misc.getRandom(600) == 588) {//Amazing items
            World.sendMessage("@or3@[Red Key Chest]@bla@ " + player.getUsername() + " has received a Legendary Reward from the chest!!");
            int amazingDrops = getRandomItem(amazingLoots);
            player.getInventory().add(amazingDrops, 1);
        } else {//Common items
            int commonDrops = getRandomItem(commonLoots);
            player.getInventory().add(commonDrops, 1);
        }

    }

}
