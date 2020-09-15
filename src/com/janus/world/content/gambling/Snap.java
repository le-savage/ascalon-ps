package com.janus.world.content.gambling;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.util.Misc;
import com.janus.world.entity.impl.player.Player;

public class Snap {

    private static int numberOne;
    private static int numberTwo;
    public static boolean gameActive;
    public static Player gambler1;
    public static Player gambler2;

    public static int gambler1Attempts = 0;
    public static int gambler2Attempts = 0;
    private static int opportunitiesToWin = 0;
    private static int drawNumber = 0;


    public static void setPlayers(Player p, Player p2) {
        if (p != null && p2 != null) {
            gambler1 = p;
            gambler2 = p2;
        }

        if (gambler1 != null && gambler2 != null) {
            initiateSnap(gambler1, gambler2);
        }
    }

    public static void initiateSnap(Player player, Player player2) {

        if (player2.allowSnap()) {
            player2.getPacketSender().sendMessage("You can block requests to play with ::blocksnap or toggle using the card!");

            gambler1Attempts = 0;
            gambler2Attempts = 0;
            opportunitiesToWin = 0;
            drawNumber = 0;

            startForceChat(player, player2);
        } else {
            player2.getPacketSender().sendMessage(player.getUsername() + " tried to challenge you to a game of snap! Use ::allowsnap or toggle using the card!");
            player.getPacketSender().sendMessage(player2.getUsername() + " has blocked snap requests.");
        }
    }

    public static void startForceChat(Player player, Player player2) {

        TaskManager.submit(new Task(1) {
            @Override
            protected void execute() {
                player.forceChat("Challenging " + player2.getUsername() + " to a game of snap!");
                player2.forceChat("Challenging " + player.getUsername() + " to a game of snap!");
                stop();
            }
        });

        TaskManager.submit(new Task(2) {
            @Override
            protected void execute() {
                player.forceChat("3");
                player2.forceChat("3");
                stop();
            }
        });

        TaskManager.submit(new Task(3) {
            @Override
            protected void execute() {
                player.forceChat("2");
                player2.forceChat("2");
                stop();
            }
        });

        TaskManager.submit(new Task(4) {
            @Override
            protected void execute() {
                player.forceChat("1");
                player2.forceChat("1");
                stop();
            }
        });

        TaskManager.submit(new Task(5) {
            @Override
            protected void execute() {
                player.forceChat("GO!");
                player2.forceChat("GO!");
                gameActive = true;

                stop();
            }
        });

        TaskManager.submit(new Task(6) {
            @Override
            protected void execute() {
                readNumbers(player, player2);
                stop();
            }
        });
    }

    public static void readNumbers(Player player, Player player2) {
        gambler1 = player;
        gambler2 = player2;
        if (opportunitiesToWin >= 3) {
            player.forceChat("Draw! We missed 3 chances to win!");
            player2.forceChat("Draw! We missed 3 chances to win!");
            opportunitiesToWin = 0;
            gameActive = false;
        }

            if (gambler1Attempts >= 3) {
                player.forceChat("I lost after 3 failed attempts!");
                player2.forceChat("I won!");
                gameActive = false;
            }
            if (gambler2Attempts >= 3) {
                player2.forceChat("I lost after 3 failed attempts!");
                player.forceChat("I won!");
                gameActive = false;
            } else if (gameActive) {


                TaskManager.submit(new Task(3) {
                    @Override
                    protected void execute() {

                        numberOne = (Misc.random(0, 10));
                        numberTwo = (Misc.random(0, 10));
                        System.out.println("NUMBER ONE " + numberOne + " | NUMBER TWO " + numberTwo);
                        drawNumber++;
                        System.out.println("Draw " + drawNumber);
                        player.forceChat(String.valueOf(numberOne));
                        player2.forceChat(String.valueOf(numberTwo));

                        if (numberOne == numberTwo && gameActive) {
                            System.out.println("DUPLICATE NUMBER");
                            opportunitiesToWin++;
                            System.out.println(opportunitiesToWin);
                            player.getPacketSender().sendMessage("Opportunity to win! :" + opportunitiesToWin + "/3");
                            player2.getPacketSender().sendMessage("Opportunity to win! :" + opportunitiesToWin + "/3");
                        }


                        if (gameActive) {
                            readNumbers(player, player2);
                        }
                        stop();
                    }
                });
            }
        }

    public static void stopAndCheck(Player player) {
        System.out.println("STOP AND CHECK STARTED");
        Player winner = null;
        gameActive = false;

            if (numberOne != numberTwo) {

                if (player.getUsername().equals(gambler1.getUsername())) {
                    gambler1Attempts++;
                    System.out.println("Attempt made by " + player.getUsername() + " now : " + gambler1Attempts + "/3");
                } else if (player.getUsername().equals(gambler2.getUsername())) {
                    gambler2Attempts++;
                    System.out.println("Attempt made by " + player.getUsername() + " now : " + gambler2Attempts + "/3");
                }

                TaskManager.submit(new Task(1) {
                    @Override
                    protected void execute() {
                        if (gambler1Attempts <= 2 ||gambler2Attempts <= 2) {
                            player.forceChat("I didn't win! Continuing...");
                        }

                        TaskManager.submit(new Task(1) {
                            @Override
                            protected void execute() {
                                gameActive = true;
                                readNumbers(gambler1, gambler2);
                                stop();
                            }
                        });
                        stop();
                    }
                });
            }



        if (numberOne == numberTwo && drawNumber > 1) {
            gameActive = false;

            if (player == gambler1) {
                winner = gambler1;
            } else if (player == gambler2) {
                winner = gambler2;
            }

            if (winner != null) {
                Player finalWinner = winner;
                TaskManager.submit(new Task(2) {
                    @Override
                    protected void execute() {
                        finalWinner.forceChat("I win!");
                        gambler1Attempts = 0;
                        gambler2Attempts = 0;
                        opportunitiesToWin = 0;
                        drawNumber = 0;
                        stop();
                    }
                });

            }

        }
    }
}
