package com.janus.world.content;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.Animation;
import com.janus.model.Position;
import com.janus.world.content.minigames.impl.NewBarrows;
import com.janus.world.content.treasuretrails.CoordinateScrolls;
import com.janus.world.content.treasuretrails.DiggingScrolls;
import com.janus.world.content.treasuretrails.MapScrolls;
import com.janus.world.entity.impl.player.Player;

public class Digging {

    public static void dig(final Player player) {
        if (!player.getClickDelay().elapsed(2000))
            return;
        player.getMovementQueue().reset();
        player.getPacketSender().sendMessage("You start digging..");
        player.performAnimation(new Animation(830));
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                /**
                 * Clue scrolls
                 */
                if (/*ClueScrolls.digSpot(player)*/DiggingScrolls.digClue(player) || MapScrolls.digClue(player) || CoordinateScrolls.digClue(player)) {
                    stop();
                    return;
                }
                Position targetPosition = null;

                /**
                 * Barrows
                 */
                if (inArea(player.getPosition(), 3562, 3294, 3569, 3284)) {
                    NewBarrows.spawnBrothers(player);
                }


                /** clue scrolls** x,y**/
                else if (inClue(player.getPosition(), 3096, 3496))
                    if (player.getInventory().contains(2677)) {
                        player.getInventory().delete(2677, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 2732, 5337)) //mining
                    if (player.getInventory().contains(2678)) {
                        player.getInventory().delete(2678, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 3366, 3267))
                    if (player.getInventory().contains(2679)) {
                        player.getInventory().delete(2679, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 3145, 9915))
                    if (player.getInventory().contains(2680)) {
                        player.getInventory().delete(2680, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 2710, 5354)) //fisher
                    if (player.getInventory().contains(2681)) {
                        player.getInventory().delete(2681, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 3451, 3717))
                    if (player.getInventory().contains(2682)) {
                        player.getInventory().delete(2682, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 2280, 4697))
                    if (player.getInventory().contains(2683)) {
                        player.getInventory().delete(2683, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 2660, 2651))
                    if (player.getInventory().contains(2684)) {
                        player.getInventory().delete(2684, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }
                else if (inClue(player.getPosition(), 2441, 3096))
                    if (player.getInventory().contains(2685)) {
                        player.getInventory().delete(2685, 1);
                        player.getInventory().add(2714, 1);
                        ClueScrolls.incrementCluesCompleted(1);
                        player.getPacketSender().sendMessage("You found a casket, open it for your reward!");
                        stop();
                    } else {

                    }

                player.getPacketSender().sendMessage("You find nothing of interest.");
                stop();
            }
        });
        player.getClickDelay().reset();
    }

    private static boolean inArea(Position pos, int x, int y, int x1, int y1) {
        return pos.getX() > x && pos.getX() < x1 && pos.getY() < y && pos.getY() > y1;
    }

    private static boolean inClue(Position pos, int x, int y) {
        return pos.getX() == x && pos.getY() == y;
    }
}
