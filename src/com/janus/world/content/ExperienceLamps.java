package com.janus.world.content;

import com.janus.model.Skill;
import com.janus.util.Misc;
import com.janus.world.content.skill.SkillManager;
import com.janus.world.entity.impl.player.Player;

public class ExperienceLamps {

    public static boolean handleLamp(Player player, int item) {
        LampData lamp = LampData.forId(item);
        if (lamp == null)
            return false;
        if (player.getInterfaceId() > 0) {
            player.getPacketSender().sendMessage("Please close the interface you have open before doing this.");
        } else {
            player.getPacketSender().sendString(46006, "Choose XP type...").sendString(46090, "What sort of XP would you like?");
            player.getPacketSender().sendInterface(46000);
            player.setUsableObject(new Object[3]).setUsableObject(0, "xp").setUsableObject(2, lamp);
        }
        return true;
    }

    public static boolean handleButton(Player player, int button) {
        if (button == -19451) {
            try {
                player.getPacketSender().sendInterfaceRemoval();
                player.getPacketSender().sendString(46006, "Choose XP type...");
                if (player.getUsableObject()[0] != null) {
                    Skill skill = (Skill) player.getUsableObject()[1];
                    switch (((String) player.getUsableObject()[0]).toLowerCase()) {
                        case "reset":
                            player.getSkillManager().resetSkill(skill, false);
                            break;
                        case "prestige":
                            player.getSkillManager().resetSkill(skill, true);
                            break;
                        case "xp":
                            LampData lamp = (LampData) player.getUsableObject()[2];
                            if (!player.getInventory().contains(lamp.getItemId()))
                                return true;
                            int exp = getExperienceReward(player, lamp, skill);
                            player.getInventory().delete(lamp.getItemId(), 1);
                            player.getSkillManager().addExperience(skill, exp);
                            player.getPacketSender().sendMessage("You've received some experience in " + Misc.formatText(skill.toString().toLowerCase()) + ".");
                            break;
                    }
                }
            } catch (Exception e) {
            }
            return true;
        } else {
            Interface_Buttons interfaceButton = Interface_Buttons.forButton(button);
            if (interfaceButton == null)
                return false;
            Skill skill = Skill.forName(interfaceButton.toString());
            if (skill == null)
                return true;
            player.setUsableObject(1, skill);
            player.getPacketSender().sendString(46006, Misc.formatText(interfaceButton.toString().toLowerCase()));
            boolean prestige = player.getUsableObject()[0] != null && player.getUsableObject()[0] instanceof String && ((String) (player.getUsableObject()[0])).equals("prestige");
            if (prestige) {
                int pts = SkillManager.getPrestigePoints(player, skill);
                player.getPacketSender().sendMessage("<img=10> <col=996633>You will receive " + pts + " Prestige point" + (pts > 1 ? "s" : "") + " if you prestige in " + skill.getFormatName() + ".");
            }
        }
        return false;
    }

    public static int getExperienceReward(Player player, LampData lamp, Skill skill) {
        int base = lamp == LampData.DRAGONKIN_LAMP ? 150000 : 2000;
        int maxLvl = player.getSkillManager().getMaxLevel(skill);
        if (SkillManager.isNewSkill(skill))
            maxLvl = maxLvl / 10;
        return (int) (base + 10 * (Math.pow(maxLvl, 2.5)));
    }

    public static boolean selectingExperienceReward(Player player) {
        return player.getInterfaceId() == 46000;
    }

    enum LampData {
        NORMAL_XP_LAMP(11137),
        DRAGONKIN_LAMP(18782);

        private int itemId;

        LampData(int itemId) {
            this.itemId = itemId;
        }

        public static LampData forId(int id) {
            for (LampData lampData : LampData.values()) {
                if (lampData != null && lampData.getItemId() == id)
                    return lampData;
            }
            return null;
        }

        public int getItemId() {
            return this.itemId;
        }
    }

    enum Interface_Buttons {

        ATTACK(-19529),
        MAGIC(-19526),
        MINING(-19523),
        WOODCUTTING(-19520),
        AGILITY(-19517),
        FLETCHING(-19514),
        THIEVING(-19511),
        STRENGTH(-19508),
        RANGED(-19505),
        SMITHING(-19502),
        FIREMAKING(-19499),
        HERBLORE(-19496),
        SLAYER(-19493),
        CONSTRUCTION(-19490),
        DEFENCE(-19487),
        PRAYER(-19484),
        FISHING(-19481),
        CRAFTING(-19478),
        FARMING(-19475),
        HUNTER(-19472),
        SUMMONING(-19469),
        CONSTITUTION(-19466),
        DUNGEONEERING(-19463),
        COOKING(-19460),
        RUNECRAFTING(-19457);

        private int button;

        Interface_Buttons(int button) {
            this.button = button;
        }

        public static Interface_Buttons forButton(int button) {
            for (Interface_Buttons skill : Interface_Buttons.values()) {
                if (skill != null && skill.button == button) {
                    return skill;
                }
            }
            return null;
        }
    }
}
