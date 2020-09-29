package com.janus.world.content.skillingtasks;

import com.janus.world.content.dialogue.Dialogue;
import com.janus.world.content.dialogue.DialogueExpression;
import com.janus.world.content.dialogue.DialogueType;
import com.janus.world.entity.impl.player.Player;

public class TaskDialogue {

    public static Dialogue skillingTaskTutorial(Player player, int stage) {
        Dialogue dialogue = null;
        String username = player.getUsername();
        switch (stage) {
            case 0:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"Hey " + username + "! Thanks for talking with me!","I've come here with a few challenges for you..","Simply choose a skill and I'll assign you a task!"};
                    }

                    ;

                    @Override
                    public int npcId() {
                        return 945;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return skillingTaskTutorial(player, stage + 1);
                    }
                };
                break;
            case 1:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"In return, you'll receive XP in your chosen", "skill, as well as points to use in my store!"};
                    }

                    ;

                    @Override
                    public int npcId() {
                        return 945;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return skillingTaskTutorial(player, stage + 1);
                    }
                };
                break;
            case 2:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.NORMAL;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"If you want to cancel or change your task,","simply speak with me, use ::stoptask or you","can also use the option in the tools quest tab!"};
                    }

                    ;

                    @Override
                    public int npcId() {
                        return 945;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return skillingTaskTutorial(player, stage + 1);
                    }
                };
                break;
            case 3:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.CONFUSED;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"If you like skilling, you'll love these tasks!","Have fun!"};
                    }

                    ;

                    @Override
                    public int npcId() {
                        return 945;
                    }

                    @Override
                    public Dialogue nextDialogue() {
                        return skillingTaskTutorial(player, stage + 1);
                    }
                };
                break;
            case 4:
                dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.OPTION;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return null;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"Request Task", "View Rewards", "Close"};
                    }

                    @Override
                    public void specialAction() {
                        player.setDialogueActionId(72);
                    }

                };
                break;
        }
        return dialogue;
    }

    public static Dialogue assignTask(Player player) {
        String username = player.getUsername();

        Dialogue dialogue = new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.UNSURE;
            }

            @Override
            public String[] dialogue() {
                return new String[]{"Your task is to "+SkillingTasks.currentTask.getDescription()+" @blu@"+SkillingTasks.requiredAmountToCompleteTask(player, SkillingTasks.getCurrentTask())+"@bla@ times!"};
            }

            @Override
            public int npcId() {
                return 945;
            }
        };
        return dialogue;
    }

    public static Dialogue finishedTask(Player player) {
        String username = player.getUsername();

                Dialogue dialogue = new Dialogue() {

                    @Override
                    public DialogueType type() {
                        return DialogueType.NPC_STATEMENT;
                    }

                    @Override
                    public DialogueExpression animation() {
                        return DialogueExpression.WIDEN_EYES;
                    }

                    @Override
                    public String[] dialogue() {
                        return new String[]{"Wow " + username + ", you actually did it!","Enjoy your reward!"};
                    }

                    @Override
                    public int npcId() {
                        return 945;
                    }
                };
        return dialogue;
    }
}
