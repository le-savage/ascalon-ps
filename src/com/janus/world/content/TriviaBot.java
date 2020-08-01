package com.janus.world.content;

import com.janus.util.Misc;
import com.janus.world.World;
import com.janus.world.content.clan.ClanChatManager;
import com.janus.world.content.grandexchange.GrandExchangeOffers;
import com.janus.world.entity.impl.player.Player;

/*
 * @author Arlania - Arlania rsps
 */
public class TriviaBot {

    public static final int TIMER = 1000; //1800
    public static int botTimer = TIMER;

    public static int answerCount;
    public static String firstPlace;
    //public static String secondPlace;
    //public static String thirdPlace;

    //public static List<String> attempts = new ArrayList<>();

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
                p.getPointsHandler().refreshPanel();
                firstPlace = p.getUsername();
                World.sendFilteredMessage("<col=AD096E>[Trivia] </col>" + firstPlace + "@bla@ answered first and received 10 points!");
                currentQuestion = "";
                didSend = false;
                botTimer = TIMER;
                answerCount = 0;
                return;

			/*if (answerCount == 1) {
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
			/*if (answerCount == 2) {
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
				return;*/
            }
        } else {
            if (attempt.contains("question") || attempt.contains("repeat")) {
                p.getPacketSender().sendMessage("@bla@" + (currentQuestion));
                return;
            }

            //attempts.add(attempt); // need to add a filter for bad strings (advs, curses)
            p.getPacketSender().sendMessage("<col=AD096E>[Trivia] </col>@bla@ Sorry! Wrong answer! The current question is:");
            p.getPacketSender().sendMessage("@bla@ " + (currentQuestion));
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
                    World.sendFilteredMessage(currentQuestion);
                }
            }
        }
    }

    public static boolean didSend = false;

    private static final String[][] Trivia_DATA = {
            {"<col=AD096E>[Trivia] </col>@bla@ Yes or No - Can you melt armour at the smithing area?", "Yes"},
            {"<col=AD096E>[Trivia] </col>@bla@ What key is used to open the burnt chest?", "Wilderness"},//
            {"<col=AD096E>[Trivia] </col>@bla@ How much exp. do you need for level 99?", "13M"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What command teleports you home?", "::home"},//
            {"<col=AD096E>[Trivia] </col>@bla@ How much GP does it take to trigger the Fountain of Goodwill?", "100m"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Finish the name of this boss. Corporeal...", "beast"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What enemy drops dragon claws?", "Tormented Demon"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What attack style is missing? Accurate, Longrange, ", "Rapid"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Will you lose items if you die whilst bossing?", "No"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What is the highest level of HP obtainable?", "99"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What is our website address?", "www.janus.rip"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What NPC sells food at home?", "Healer"},//
            {"<col=AD096E>[Trivia] </col>@bla@ True or False - Janus has summoning familiars", "True"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What is the color of a 10M+ money stack?", "Green"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What cape is obtained after killing Jad?", "Fire Cape"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What's the highest level of poison for weapons?", "P++"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Name a City in Janus beginning with V", "Varrock"},//
            {"<col=AD096E>[Trivia] </col>@bla@ What can be rolled to gamble?", "Dice"},//
            {"<col=AD096E>[Trivia] </col>@bla@ True or False - Every donor item can be found in game", "True"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Finish the weapon name: Twisted ***", "Bow"},//
            {"<col=AD096E>[Trivia] </col>@bla@ Where are Frost Dragons found for regular players?", "Wilderness"},
            {"<col=AD096E>[Trivia] </col>@bla@ True or False - Janus has the dwarf cannon", "True"},
            {"<col=AD096E>[Trivia] </col>@bla@ What is player killing often shortened to?", "pking"},
            {"<col=AD096E>[Trivia] </col>@bla@ What defence level is required to wear Vanguard?", "85"},
            {"<col=AD096E>[Trivia] </col>@bla@ What's the best in slot shield for range?", "Twisted Buckler"},
            {"<col=AD096E>[Trivia] </col>@bla@ What is the most powerful crossbow?", "Armadyl"},
            {"<col=AD096E>[Trivia] </col>@bla@ What magic level is required to cast Wind Strike?", "1"}
    };

    public static String currentQuestion;
    private static String currentAnswer;
}