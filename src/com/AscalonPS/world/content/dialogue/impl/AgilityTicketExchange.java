package com.AscalonPS.world.content.dialogue.impl;

import com.AscalonPS.model.Difficulty;
import com.AscalonPS.model.input.impl.BuyAgilityExperience;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.content.dialogue.Dialogue;
import com.AscalonPS.world.content.dialogue.DialogueExpression;
import com.AscalonPS.world.content.dialogue.DialogueType;
import com.AscalonPS.world.entity.impl.player.Player;

public class AgilityTicketExchange {


    public static Dialogue getDialogue(Player player, Difficulty difficulty) {

        int tokenXP = 7680;

        switch (difficulty) {
            case Easy:
                tokenXP *= 3;
            case Medium:
            case Default:
                break;
            case Hard:
                tokenXP *= 0.5;
                break;
            case Insane:
                tokenXP *= 0.25;
                break;
            case Zezima:
                tokenXP *= 0.1;
                break;
        }

        String tokenXPString = (Misc.format(tokenXP));

        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.NORMAL;
            }

            @Override
            public int npcId() {
                return 437;
            }


            @Override
            public String[] dialogue() {
                return new String[]{"@bla@How many tickets would you like to exchange", "for experience? One ticket currently grants", "@red@" + tokenXPString + "@bla@ Agility experience."};
            }

            public Dialogue nextDialogue() {
                return new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public int npcId() {
                        return 437;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"@bla@How many tickets would you like to exchange", "for experience? One ticket currently grants", "@red@" + tokenXPString + "@bla@ Agility experience."};
                    }

                    @Override
                    public void specialAction() {
                        player.getPacketSender().sendInterfaceRemoval();
                        player.setInputHandling(new BuyAgilityExperience());
                        player.getPacketSender().sendEnterAmountPrompt("How many tickets would you like to exchange?");
                    }
                };

            }
        };
    }

}
