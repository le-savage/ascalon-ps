package com.janus.world.content.skill.impl.fishing;

import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.Animation;
import com.janus.model.Direction;
import com.janus.model.Skill;
import com.janus.model.definitions.ItemDefinition;
import com.janus.util.Misc;
import com.janus.world.content.Achievements;
import com.janus.world.content.Achievements.AchievementData;
import com.janus.world.entity.impl.player.Player;

public class Fishing {

    public static Spot forSpot(int npcId, boolean secondClick) {
        for (Spot spot : Spot.values()) {
            if (secondClick) {
                if (spot.getSecond()) {
                    if (spot.getNPCId() == npcId) {
                        return spot;
                    }
                }
            } else {
                if (spot.getNPCId() == npcId && !spot.getSecond()) {
                    return spot;
                }
            }
        }
        return null;
    }

    public static void setupFishing(Player player, Spot spot) {
        if (player.getInteractingObject() != null) {
            player.setPositionToFace(player.getInteractingObject().getPosition().copy());
        }
        if (spot == null)
            return;
        if (player.getInventory().getFreeSlots() <= 0) {
            player.getPacketSender().sendMessage("You do not have any free inventory space.");
            player.getSkillManager().stopSkilling();
            return;
        }
        if (player.getSkillManager().getCurrentLevel(Skill.FISHING) >= spot.getLevelReq()[0]) {
            if (player.getInventory().contains(spot.getEquipment())) {
                if (spot.getBait() != -1) {
                    if (player.getInventory().contains(spot.getBait())) {
                        startFishing(player, spot);
                    } else {
                        String baitName = ItemDefinition.forId(spot.getBait()).getName();
                        if (baitName.contains("Feather") || baitName.contains("worm"))
                            baitName += "s";
                        player.getPacketSender().sendMessage("You need some " + baitName + " to fish here.");
                        player.performAnimation(new Animation(65535));
                    }
                } else {
                    startFishing(player, spot);
                }
            } else {
                String def = ItemDefinition.forId(spot.getEquipment()).getName().toLowerCase();
                player.getPacketSender().sendMessage("You need " + Misc.anOrA(def) + " " + def + " to fish here.");
            }
        } else {
            player.getPacketSender().sendMessage("You need a fishing level of at least " + spot.getLevelReq()[0] + " to fish here.");
        }
    }

    public static void startFishing(final Player player, final Spot spot) {
        player.getPacketSender().sendRichPresenceState("Catching Fish!");
        player.getPacketSender().sendSmallImageKey("fishing");
        player.getPacketSender().sendRichPresenceSmallPictureText("Lvl: " + player.getSkillManager().getCurrentLevel(Skill.FISHING));
        player.getSkillManager().stopSkilling();
        final int fishIndex = Misc.getRandom(100) >= 70 ? getMax(player, spot.fishingReqs) : (getMax(player, spot.fishingReqs) != 0 ? getMax(player, spot.fishingReqs) - 1 : 0);
        if (player.getInteractingObject() != null && player.getInteractingObject().getId() != 8702)
            player.setDirection(spot == Spot.MONK_FISH ? Direction.WEST : Direction.NORTH);
        player.performAnimation(new Animation(spot.getAnim()));
        player.setCurrentTask(new Task(2, player, false) {
            int cycle = 0, reqCycle = Fishing.getDelay(spot.getLevelReq()[fishIndex]);

            @Override
            public void execute() {
                if (player.getInventory().getFreeSlots() == 0) {
                    player.getPacketSender().sendMessage("You have run out of inventory space.");
                    stop();
                    return;
                }
                if (!player.getInventory().contains(spot.getBait())) {
                    stop();
                    return;
                }
                if (ItemDefinition.forId(spot.getRawFish()[fishIndex]).getName().contains("AFK")) {
                    player.getInventory().add(spot.getRawFish()[fishIndex], 1);
                    player.getSkillManager().addExperience(Skill.FISHING, spot.getXp()[fishIndex]);
                    setupFishing(player, spot);
                    setEventRunning(false);
                    player.setDirection(Direction.EAST);
                }
                cycle++;
                player.performAnimation(new Animation(spot.getAnim()));
                if (cycle >= Misc.getRandom(1) + reqCycle) {
                    String def = ItemDefinition.forId(spot.getRawFish()[fishIndex]).getName();
                    if (def.contains("AFK")) {
                        return;
                    } else if (def.endsWith("s")) def = def.substring(0, def.length() - 1);
                    {
                        player.getPacketSender().sendMessage("You catch " + Misc.anOrA(def) + " " + def.toLowerCase().replace("_", " ") + ".");
                        if (spot.getBait() != -1)
                            player.getInventory().delete(spot.getBait(), 1);
                        player.getInventory().add(spot.getRawFish()[fishIndex], 1);
                        if (spot.getRawFish()[fishIndex] == 331) {
                            Achievements.finishAchievement(player, AchievementData.FISH_A_SALMON);
                        } else if (spot.getRawFish()[fishIndex] == 15270) {
                            Achievements.doProgress(player, AchievementData.FISH_25_ROCKTAILS);
                            Achievements.doProgress(player, AchievementData.FISH_2000_ROCKTAILS);
                        } else if (spot.getRawFish()[fishIndex] == -1) {

                        }
                        player.getSkillManager().addExperience(Skill.FISHING, spot.getXp()[fishIndex]);
                        setupFishing(player, spot);
                        setEventRunning(false);
                    }
                }
            }

            @Override
            public void stop() {
                setEventRunning(false);
                player.performAnimation(new Animation(65535));
            }
        });

        TaskManager.submit(player.getCurrentTask());
    }

    public static int getMax(Player p, int[] reqs) {
        int tempInt = -1;
        for (int i : reqs) {
            if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= i) {
                tempInt++;
            }
        }
        return tempInt;
    }

    private static int getDelay(int req) {
        int timer = 1;
        timer += (int) req * 0.08;
        return timer;
    }

    public enum Spot {

        LURE(318, new int[]{335, 331}, 309, 314, new int[]{20, 30}, true, new int[]{2325, 2120}, 623),

        CAGE(312, new int[]{377}, 301, -1, new int[]{40}, false, new int[]{4470}, 619),

        BIGNET(313, new int[]{353, 341, 363}, 305, -1, new int[]{16, 23, 46}, false, new int[]{1170, 1130, 2120}, 620),

        SMALLNET(316, new int[]{317, 321}, 303, -1, new int[]{1, 15}, false, new int[]{435, 550}, 621),

        MONK_FISH(318, new int[]{7944, 389}, 305, -1, new int[]{62, 81}, false, new int[]{10477, 10122}, 621),

        HARPOON(312, new int[]{359, 371}, 311, -1, new int[]{35, 50}, true, new int[]{3525, 5100}, 618),

        HARPOON2(313, new int[]{383}, 311, -1, new int[]{76}, true, new int[]{12820}, 618),

        BAIT(316, new int[]{327, 345}, 307, 313, new int[]{5, 10}, true, new int[]{975, 980}, 623),

        AFK_FISHING(10091, new int[]{12852}, -1, -1, new int[]{50}, false, new int[]{250}, 623),

        ROCKTAIL(10091, new int[]{15270}, 309, 25, new int[]{91}, false, new int[]{27015}, 623);

        int npcId, equipment, bait, anim;
        int[] rawFish, fishingReqs, xp;
        boolean second;

        private Spot(int npcId, int[] rawFish, int equipment, int bait, int[] fishingReqs, boolean second, int[] xp, int anim) {
            this.npcId = npcId;
            this.rawFish = rawFish;
            this.equipment = equipment;
            this.bait = bait;
            this.fishingReqs = fishingReqs;
            this.second = second;
            this.xp = xp;
            this.anim = anim;
        }

        public int getNPCId() {
            return npcId;
        }

        public int[] getRawFish() {
            return rawFish;
        }

        public int getEquipment() {
            return equipment;
        }

        public int getBait() {
            return bait;
        }

        public int[] getLevelReq() {
            return fishingReqs;
        }

        public boolean getSecond() {
            return second;
        }

        public int[] getXp() {
            return xp;
        }

        public int getAnim() {
            return anim;
        }
    }

}
