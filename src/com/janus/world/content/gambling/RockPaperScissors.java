package com.janus.world.content.gambling;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.util.Misc;
import com.janus.world.entity.impl.player.Player;

public class RockPaperScissors {

    public static void play(Player player, Player player2) {


        TaskManager.submit(new Task(1) {
            @Override
            protected void execute() {
                player.forceChat("Rock.. Paper.. Scissors..");
                player2.forceChat("Rock.. Paper.. Scissors..");
                stop();
            }
        });

        TaskManager.submit(new Task(2) {
            @Override
            protected void execute() {
                int player1Random = Misc.random(0, 2);
                int player2Random = Misc.random(0, 2);

                if (player1Random == 0) { //If player 1 Rolls rock
                    if (player2Random == 0) {
                        player.getPacketSender().sendMessage("Draw!");
                        player2.getPacketSender().sendMessage("Draw!");
                    }
                    if (player2Random == 1) {
                        player.getPacketSender().sendMessage("I lost!");
                        player2.getPacketSender().sendMessage("I won!");
                    }
                    if (player2Random == 2) {
                        player.getPacketSender().sendMessage("I won!");
                        player2.getPacketSender().sendMessage("I lost!");
                    }
                }

                if (player1Random == 1) { //If player 1 Rolls paper
                    if (player2Random == 0) {
                        player.getPacketSender().sendMessage("I won!");
                        player2.getPacketSender().sendMessage("I lost!");
                    }
                    if (player2Random == 1) {
                        player.getPacketSender().sendMessage("Draw!");
                        player2.getPacketSender().sendMessage("Draw!");
                    }
                    if (player2Random == 2) {
                        player.getPacketSender().sendMessage("I lost!");
                        player2.getPacketSender().sendMessage("I won!");
                    }
                }

                if (player1Random == 2) { //If player 1 Rolls Scissors
                    if (player2Random == 0) {
                        player.getPacketSender().sendMessage("I lost!");
                        player2.getPacketSender().sendMessage("I won!");
                    }
                    if (player2Random == 1) {
                        player.getPacketSender().sendMessage("I won!");
                        player2.getPacketSender().sendMessage("I lost!");
                    }
                    if (player2Random == 2) {
                        player.getPacketSender().sendMessage("Draw!");
                        player2.getPacketSender().sendMessage("Draw!");
                    }
                }

                if (player1Random == 0) {
                    player.forceChat("ROCK!");
                } else if (player1Random == 1) {
                    player.forceChat("PAPER!");
                } else if (player1Random == 2) {
                    player.forceChat("SCISSORS!");
                }

                if (player2Random == 0) {
                    player2.forceChat("ROCK!");
                } else if (player2Random == 1) {
                    player2.forceChat("PAPER!");
                } else if (player2Random == 2) {
                    player2.forceChat("SCISSORS!");
                }
                stop();
            }
        });
    }
}
