package com.janus.world.content.dialogue.impl;

import com.janus.model.Difficulty;
import com.janus.model.input.impl.BuyAgilityExperience;
import com.janus.world.content.dialogue.Dialogue;
import com.janus.world.content.dialogue.DialogueExpression;
import com.janus.world.content.dialogue.DialogueType;
import com.janus.world.entity.impl.player.Player;

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

        int finalTokenXP = tokenXP;
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
                return new String[]{"@bla@How many tickets would you like to exchange", "for experience? One ticket currently grants", "@red@" + finalTokenXP + "@bla@ Agility experience."};
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
                        return new String[]{"@bla@How many tickets would you like to exchange", "for experience? One ticket currently grants", "@red@" + finalTokenXP + "@bla@ Agility experience."};
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
