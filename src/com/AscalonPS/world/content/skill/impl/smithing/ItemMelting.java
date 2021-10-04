package com.AscalonPS.world.content.skill.impl.smithing;

import com.AscalonPS.engine.task.Task;
import com.AscalonPS.engine.task.TaskManager;
import com.AscalonPS.model.Animation;
import com.AscalonPS.model.Skill;
import com.AscalonPS.model.definitions.ItemDefinition;
import com.AscalonPS.model.input.impl.EnterAmountToMelt;
import com.AscalonPS.world.entity.impl.player.Player;

public class ItemMelting {

    public static int meltBronzeXP = 100;
    public static int meltIronXP = 150;
    public static int meltSteelXP = 200;
    public static int meltMithrilXP = 300;
    public static int meltAdamantXP = 450;
    public static int meltRuneXP = 600;
    public static int meltDragonXP = 1000;
    public static int meltBarrowsXP = 2000;
    public static int meltOtherXP = 3000;


    public static int bronzeBar = 2349;
    public static int ironBar = 2351;
    public static int steelBar = 2353;
    public static int mithrilBar = 2359;
    public static int adamantBar = 2361;
    public static int runeBar = 2363;
    public static int dragonBar = 21061;
    public static int barrowsBar = 21062;
    public static int armadylBar = 21063;
    public static int bandosBar = 21064;
    public static int thirdAgeBar = 21065;
    public static int torvaBar = 21066;


    public static void selectionInterface(Player player, ItemMeltData meltData) {
        ItemDefinition barItemDef = ItemDefinition.forId(meltData.getMeltedBar());
        ItemDefinition itemName = ItemDefinition.forId(meltData.getOriginalItem());
        if (meltData == null)
            return;
        player.setSelectedSkillingItem(meltData.getOriginalItem());
        player.setInputHandling(new EnterAmountToMelt());

        final ItemMeltData item = ItemMeltData.forItem(meltData.originalItem);

        String oneBarString = ("@bla@You'll receive @red@" + meltData.getBarAmount() + "@bla@ " + barItemDef.getName() + " per item");
        String twoBarString = ("@bla@You'll receive @red@" + meltData.getBarAmount() + "@bla@ " + barItemDef.getName() + "s per item");
        player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");

        if (meltData.getBarAmount() == 1) {
            player.getPacketSender().sendString(2799, oneBarString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);

        } else if (meltData.getBarAmount() >= 2) {
            player.getPacketSender().sendString(2799, twoBarString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);

        } else if (meltData.getBarAmount() == 0) {
            if (item.getMeltedBar() == bronzeBar) {
                String onlyXPString = ("@bla@You'll only receive XP For melting this. XP Given: " + meltBronzeXP);

                player.getPacketSender().sendString(2799, onlyXPString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);
            } else if (item.getMeltedBar() == ironBar) {
                String onlyXPString = ("@bla@You'll only receive XP For melting this. XP Given: " + meltIronXP);
                player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");
                player.getPacketSender().sendString(2799, onlyXPString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);
            } else if (item.getMeltedBar() == steelBar) {
                String onlyXPString = ("@bla@You'll only receive XP For melting this. XP Given: " + meltSteelXP);
                player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");
                player.getPacketSender().sendString(2799, onlyXPString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);
            } else if (item.getMeltedBar() == mithrilBar) {
                String onlyXPString = ("@bla@You'll only receive XP For melting this. XP Given: " + meltMithrilXP);
                player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");
                player.getPacketSender().sendString(2799, onlyXPString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);
            } else if (item.getMeltedBar() == adamantBar) {
                String onlyXPString = ("@bla@You'll only receive XP For melting this. XP Given: " + meltAdamantXP);
                player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");
                player.getPacketSender().sendString(2799, onlyXPString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);
            } else if (item.getMeltedBar() == runeBar) {
                String onlyXPString = ("@bla@You'll only receive XP For melting this. XP Given: " + meltRuneXP);
                player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");
                player.getPacketSender().sendString(2799, onlyXPString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);
            } else if (item.getMeltedBar() == dragonBar) {
                String onlyXPString = ("@bla@You'll only receive XP For melting this. XP Given: " + meltDragonXP);
                player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");
                player.getPacketSender().sendString(2799, onlyXPString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);
            } else if (item.getMeltedBar() == barrowsBar) {
                String onlyXPString = ("@bla@You'll only receive XP For melting this. XP Given: " + meltBarrowsXP);
                player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");
                player.getPacketSender().sendString(2799, onlyXPString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);
            } else if ((item.getMeltedBar() == armadylBar || item.getMeltedBar() == bandosBar || item.getMeltedBar() == thirdAgeBar || item.getMeltedBar() == torvaBar)) {
                String onlyXPString = ("@bla@You'll only receive XP For melting this. XP Given: " + meltOtherXP);
                player.getPacketSender().sendString(2799, onlyXPString).sendInterfaceModel(1746, meltData.getOriginalItem(), 150).sendChatboxInterface(4429);
                player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");
            }
            player.getPacketSender().sendString(2800, "How many " + itemName.getName() + "'s should we melt?");


        }
    }

    public static void melt(final Player player, final int originalItem, final int amount) {
        final ItemMeltData item = ItemMeltData.forItem(originalItem);


        if (item == null)
            return;
        player.getSkillManager().stopSkilling();
        player.getPacketSender().sendInterfaceRemoval();
        if (!ItemMeltData.canMelt(player, originalItem))
            return;
        player.performAnimation(new Animation(883));
        player.setCurrentTask(new Task(2, player, false) {
            int amountMelted = 0;

            @Override
            public void execute() {
                player.getPacketSender().sendRichPresenceState("Melting Items!");
                player.getPacketSender().sendSmallImageKey("smithing");
                player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.SMITHING));
                if (ItemMeltData.canMelt(player, originalItem)) {
                    player.performAnimation(new Animation(883));
                    player.getInventory().delete(originalItem, 1);
                    player.getInventory().add(item.getMeltedBar(), item.getBarAmount());
                    /*player.getSkillManager().addExperience(Skill.SMITHING, (10000 * item.barAmount));*/

                    if (item.getMeltedBar() == bronzeBar) {
                        if (item.barAmount == 0) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltBronzeXP));
                        } else if (item.barAmount > 1) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltBronzeXP * item.barAmount));
                        }
                    } else if (item.getMeltedBar() == ironBar) {
                        if (item.barAmount == 0) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltIronXP));
                        } else if (item.barAmount > 1) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltIronXP * item.barAmount));
                        }
                    } else if (item.getMeltedBar() == steelBar) {
                        if (item.barAmount == 0) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltSteelXP));
                        } else if (item.barAmount > 1) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltSteelXP * item.barAmount));
                        }
                    } else if (item.getMeltedBar() == mithrilBar) {
                        if (item.barAmount == 0) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltMithrilXP));
                        } else if (item.barAmount > 1) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltMithrilXP * item.barAmount));
                        }
                    } else if (item.getMeltedBar() == adamantBar) {
                        if (item.barAmount == 0) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltAdamantXP));
                        } else if (item.barAmount > 1) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltAdamantXP * item.barAmount));
                        }
                    } else if (item.getMeltedBar() == runeBar) {
                        if (item.barAmount == 0) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltRuneXP));
                        } else if (item.barAmount > 1) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltRuneXP * item.barAmount));
                        }
                    } else if (item.getMeltedBar() == dragonBar) {
                        if (item.barAmount == 0) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltDragonXP));
                        } else if (item.barAmount > 1) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltDragonXP * item.barAmount));
                        }
                    } else if (item.getMeltedBar() == barrowsBar) {
                        if (item.barAmount == 0) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltBarrowsXP));
                        } else if (item.barAmount > 1) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltBarrowsXP * item.barAmount));
                        }
                    } else if ((item.getMeltedBar() == armadylBar || item.getMeltedBar() == bandosBar || item.getMeltedBar() == thirdAgeBar || item.getMeltedBar() == torvaBar)) {
                        if (item.barAmount == 0) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltOtherXP));
                        } else if (item.barAmount > 1) {
                            player.getSkillManager().addExperience(Skill.SMITHING, (meltOtherXP * item.barAmount));
                        }
                    }

                } else {
                    stop();
                    return;
                }
                amountMelted++;
                if (amountMelted >= amount)
                    stop();
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.setSelectedSkillingItem(-1);
                player.performAnimation(new Animation(65535));
            }
        });
        TaskManager.submit(player.getCurrentTask());
    }
}
