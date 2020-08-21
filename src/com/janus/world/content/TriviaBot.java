package com.janus.world.content;

import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.entity.impl.player.Player;


public class TriviaBot {

    public static final int TIMER = 1000; //1800
    public static int botTimer = TIMER;

    public static int answerCount;
    public static String firstPlace;
    public static String secondPlace;
    public static String thirdPlace;

    //public static List<String> attempts = new ArrayList<>();

    public static void sequence() {

        if(botTimer > 0)
            botTimer--;
        if(botTimer <= 0) {
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
                World.sendMessage("@blu@[Trivia] @bla@[1st:@blu@" +firstPlace+"@red@ (10 pts)@bla@] @bla@[2nd:@blu@" +secondPlace+"@red@ (6 pts)@bla@] [3rd:@blu@" +thirdPlace+"@red@  (4 pts)@bla@]");
                //String[] s = Arrays.asList(attempts);
                //World.sendMessage("@blu@[Trivia] @red@Failed attempts: "+s);
                currentQuestion = "";
                didSend = false;
                botTimer = TIMER;
                answerCount = 0;
                return;
            }
        } else {
            if(attempt.contains("question") || attempt.contains("repeat")){
                p.getPacketSender().sendMessage("<col=800000>"+(currentQuestion));
                return;
            }

            //attempts.add(attempt); // need to add a filter for bad strings (advs, curses)
            p.getPacketSender().sendMessage("@blu@[Trivia]@red@ Sorry! Wrong answer! The current question is: +");
            p.getPacketSender().sendMessage("@blu@[Trivia]@red@ "+(currentQuestion));
            return;
        }

    }

    public static boolean acceptingQuestion() {
        return !currentQuestion.equals("");
    }

    private static void askQuestion() {
        for (int i = 0; i < Trivia_DATA.length; i++) {
            if (Misc.getRandom(Trivia_DATA.length - 1) == i) {
                if(!didSend) {
                    didSend = true;
                    currentQuestion = Trivia_DATA[i][0];
                    currentAnswer = Trivia_DATA[i][1];
                    World.sendMessage(currentQuestion);


                }
            }
        }
    }

    public static boolean didSend = false;

    private static final String[][] Trivia_DATA = {
            {"<col=AD096E>[Trivia] </col>@bla@ What slayer level is needed to kill Abyssal demons", "85"},
            {"<col=AD096E>[Trivia] </col>@bla@ What woodcutting level is needed to cut magic trees", "75"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What mining level is needed to mine Amethyst?", "92"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What quest do you need to complete to wield a Dragon battleaxe?", "::heroes quest"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What attack level is needed to wield a whip", "70"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What quest do you need to complete for Barrows gloves", "Recipe for disaster"},//
            {"<col=AD096E>[Trivia] </col>@bla@ During what age did the Barrows brothers rise to power?", "4th age"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Dragons were created by who?", "The Dragonkin"},//
            {"<col=AD096E>[Trivia] </col>@bla@ True or False? Zamorak was once a Human.", "False"},//
            {"<col=AD096E>[Trivia] </col>@bla@ How much xp do you get for cooking a shark?", "210"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What increases your odds of getting a bird's nest while cutting a tree?", "Woodcutting cape"},
            {"<col=AD096E>[Trivia] </col>@bla@ What country is Jagex's Games Studio?", "uk"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What was the first ever holiday event item?", "Fire Cape"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What town is most known for its fishing spots?", "Draynor Village"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Who is the only magic barrows brother?", "Ahrims"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Which key allows you access to hill giants and Edgeville dungeon??", "Brass key"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Who is the most famous RuneScape player throughout history?", "Zezima"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What weapon can be wielded as a reward from monkey madness?", "Dragon scimitar"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Who do you speak to in order to access the runecrafting abyss?", "Zamorakian Mage"},
            {"<col=AD096E>[Trivia] </col>@bla@ What skill allows you to make bowstrings?", "Crafting"},
            {"<col=AD096E>[Trivia] </col>@bla@ How many arrow shafts do you get per log?", "15"},
            {"<col=AD096E>[Trivia] </col>@bla@ What is the maximum length of a RuneScape name?", "12"},
            {"<col=AD096E>[Trivia] </col>@bla@ What colours does the Lumbridge castle kitchen floor contain?", "black and white"},
            {"<col=AD096E>[Trivia] </col>@bla@ What does 'Noob' translate to?", "new player"},
            {"<col=AD096E>[Trivia] </col>@bla@ What can you catch with level 1 fishing?", "shrimps"}
    };

    public static String currentQuestion;
    private static String currentAnswer;
}