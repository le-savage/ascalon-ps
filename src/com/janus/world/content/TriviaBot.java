package com.janus.world.content;

import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.entity.impl.player.Player;


public class TriviaBot {

    public static final int TIMER = 1000; //1800
    private static final String[][] Trivia_DATA = {
            {"<col=AD096E>[Trivia] </col>@bla@ What level is needed to wear Scythe of Vitur?", "75"},
            {"<col=AD096E>[Trivia] </col>@bla@ What woodcutting level is needed to cut magic trees", "75"},//
            {"<col=AD096E>[Trivia] </col>@bla@ True or false u can melt all armour in Janus?", "false"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Wich slayer master requires 92 slayer?", "sumona"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What attack level is needed to wield a whip", "70"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What summoning level is needed to infuse Granite lobster", "74"},//
            {"<col=AD096E>[Trivia] </col>@bla@ how many boss points does it take to buy  an entire nex set?", "9000"},//
            {"<col=AD096E>[Trivia] </col>@bla@ When was instances first released to Janus(DD/MM/YYYY)?", "25/07/2020"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Where is gambling located?", "castle wars"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What is the droprate of twisted bow from Crazy Man?", "1/900"},//
            {"<col=AD096E>[Trivia] </col>@bla@ How many variations of Nightmare staves are available on Janus?", "4"},
            {"<col=AD096E>[Trivia] </col>@bla@ How much does a primal weapon cost in Janus from dungeoneering", "250000"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What is the most expensive drop from KBD in Janus?", "dragon hunter crossbow"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Where is the Janus PK store located?", "edgeville"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Who is the only magic barrows brother?", "Ahrims"},//
            {"<col=AD096E>[Trivia] </col>@bla@ How many donation points is it for a party hat?", "20"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Which NPC sells teleport tabs?", "Explorer Jack"},//
            {"<col=AD096E>[Trivia] </col>@bla@ How many Player type NPCs are located at home?", "18"},//
            {"<col=AD096E>[Trivia] </col>@bla@ How much droprate boost is provided by Ultimate Ironman?", "10%"},
            {"<col=AD096E>[Trivia] </col>@bla@ How many cluescroll locations are available in Janus?", "9"},
            {"<col=AD096E>[Trivia] </col>@bla@ What is the BIS ring in Janus?", "Ring of the Gods"},
            {"<col=AD096E>[Trivia] </col>@bla@ What is the magic attack bonus offered by the Nightmare staff?", "200"},
            {"<col=AD096E>[Trivia] </col>@bla@ What is the High-alch value of Armadyl pieces?", "250m"},
            {"<col=AD096E>[Trivia] </col>@bla@ What is the highest level fish you can fish in Janus?", "Rocktail"},
            {"<col=AD096E>[Trivia] </col>@bla@ What do you get from the zombies at the Zombie minigame?", "Zombie Fragment"},
            {"<col=AD096E>[Trivia] </col>@bla@ What is the most expensive thing you can get from vote store?", "$5 scroll"}
    };
    public static int botTimer = TIMER;
    public static int answerCount;
    public static String firstPlace;
    public static String secondPlace;

    //public static List<String> attempts = new ArrayList<>();
    public static String thirdPlace;
    public static boolean didSend = false;
    public static String currentQuestion;
    private static String currentAnswer;

    public static void sequence() {

        if (botTimer > 0)
            botTimer--;
        if (botTimer <= 0) {
            botTimer = TIMER;
            didSend = false;
            askQuestion();
        }
    }

    public static void attemptAnswer(Player p, String attempt) {

        if (!currentQuestion.equals("") && attempt.replaceAll("_", " ").equalsIgnoreCase(currentAnswer)) {

            if (answerCount == 0) {
                answerCount++;
                p.getPointsHandler().incrementTriviaPoints(10);
                p.getPacketSender().sendMessage("You received 10 trivia points for @red@1st Place.");
                p.getPointsHandler().refreshPanel();
                firstPlace = p.getUsername();
                return;
            }
            if (answerCount == 1) {
                if (p.getUsername().equalsIgnoreCase(firstPlace)) {
                    p.getPacketSender().sendMessage("Already answered");
                    return;
                }
                answerCount++;
                p.getPointsHandler().incrementTriviaPoints(6);
                p.getPacketSender().sendMessage("You received 6 trivia points for @red@2nd Place.");
                p.getPointsHandler().refreshPanel();
                secondPlace = p.getUsername();
                return;

            }
            if (answerCount == 2) {
                if (p.getUsername().equalsIgnoreCase(firstPlace) || p.getUsername().equalsIgnoreCase(secondPlace)) {
                    p.getPacketSender().sendMessage("Already answered");
                    return;
                }
                p.getPointsHandler().incrementTriviaPoints(4);
                p.getPacketSender().sendMessage("You received 4 trivia points for @red@3rd Place.");
                p.getPointsHandler().refreshPanel();
                thirdPlace = p.getUsername();
                World.sendMessage("@blu@[Trivia] @bla@[1st:@blu@" + firstPlace + "@red@ (10 pts)@bla@] @bla@[2nd:@blu@" + secondPlace + "@red@ (6 pts)@bla@] [3rd:@blu@" + thirdPlace + "@red@  (4 pts)@bla@]");
                //String[] s = Arrays.asList(attempts);
                //World.sendMessage("@blu@[Trivia] @red@Failed attempts: "+s);
                currentQuestion = "";
                didSend = false;
                botTimer = TIMER;
                answerCount = 0;
                return;
            }
        } else {
            if (attempt.contains("question") || attempt.contains("repeat")) {
                p.getPacketSender().sendMessage("<col=800000>" + (currentQuestion));
                return;
            }

            //attempts.add(attempt); // need to add a filter for bad strings (advs, curses)
            p.getPacketSender().sendMessage("@blu@[Trivia]@red@ Sorry! Wrong answer! The current question is: +");
            p.getPacketSender().sendMessage("@blu@[Trivia]@red@ " + (currentQuestion));
            return;
        }

    }

    public static boolean acceptingQuestion() {
        return !currentQuestion.equals("");
    }

    private static void askQuestion() {
        for (int i = 0; i < Trivia_DATA.length; i++) {
            if (Misc.getRandom(Trivia_DATA.length - 1) == i) {
                if (!didSend) {
                    didSend = true;
                    currentQuestion = Trivia_DATA[i][0];
                    currentAnswer = Trivia_DATA[i][1];
                    World.sendMessage(currentQuestion);


                }
            }
        }
    }
}