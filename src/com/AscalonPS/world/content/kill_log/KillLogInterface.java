package com.AscalonPS.world.content.kill_log;

import com.AscalonPS.util.Misc;
import com.AscalonPS.world.content.KillsTracker;
import com.AscalonPS.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class KillLogInterface {
    private static List<KillsTracker.KillsEntry> currentlyViewing = new ArrayList<>();

    private static enum Type {
        MONSTERS,
        BOSSES,
        OTHER;
    }

    public static void open(Player player) {
        player.getPA().sendString(33310, "Total NPC Kills:" + Misc.insertCommasToNumber(String.valueOf(KillsTracker.getTotalKills(player))));
        sendNames(player, Type.MONSTERS);
        if (currentlyViewing.size() > 0) {
            sendData(player, currentlyViewing.get(0));
        }
        player.getPA().sendActiveWidget(33304, false);
        player.getPA().sendInterface(33300);
    }

    private static void sendNames(Player player, Type type) {
        for (int i = 33321; i <= 33372; i++) {
            player.getPA().sendActiveWidget(i, false);
        }
        loadType(player, type);
        int index = 0;
        for (KillsTracker.KillsEntry entry : currentlyViewing) {
            if (33321 + index >= 33372)
                break;

            player.getPacketSender().sendString(33321 + index, entry.npcName);
            player.getPA().sendActiveWidget(33321 + index, true);
            index++;

            for (int i = 33321 + index; i <= 33372; i++) {
                player.getPA().sendActiveWidget(i, false);
            }
        }
    }
    private static void loadType(Player player, Type type) {
        currentlyViewing.clear();
        switch (type) {
            case MONSTERS:
                Collections.sort(player.getKillsTracker(), (kill1, kill2) -> {
                    if (kill1.boss && !kill2.boss) {
                        return -1;
                    }
                    if (kill2.boss && !kill1.boss) {
                        return 1;
                    }
                    if (kill1.boss && kill2.boss || !kill1.boss && !kill2.boss) {
                        if (kill1.amount > kill2.amount) {
                            return -1;
                        } else if (kill2.amount > kill1.amount) {
                            return 1;
                        } else {
                            if (kill1.npcName.compareTo(kill2.npcName) > 0) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    }
                    return 0;
                });
                for (KillsTracker.KillsEntry entry : player.getKillsTracker()) {
                    if (!entry.boss) {
                        currentlyViewing.add(entry);
                    }
                }
                break;
            case BOSSES:
                Collections.sort(player.getKillsTracker(), (kill1, kill2) -> {
                    if (kill1.boss && !kill2.boss) {
                        return -1;
                    }
                    if (kill2.boss && !kill1.boss) {
                        return 1;
                    }
                    if (kill1.boss && kill2.boss || !kill1.boss && !kill2.boss) {
                        if (kill1.amount > kill2.amount) {
                            return -1;
                        } else if (kill2.amount > kill1.amount) {
                            return 1;
                        } else {
                            if (kill1.npcName.compareTo(kill2.npcName) > 0) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }
                    }
                    return 0;
                });

                for (KillsTracker.KillsEntry entry : player.getKillsTracker()) {
                    if (entry.boss) {
                        currentlyViewing.add(entry);
                    }
                }
                break;
            case OTHER:

                break;
        }
    }

    private static void sendData(Player player, KillsTracker.KillsEntry entry) {
        player.getPA().sendString(33308, "Currently Viewing: " + entry.npcName)
                .sendString(33309, entry.npcName + " kills: " + Misc.insertCommasToNumber(String.valueOf(entry.amount)))
                .sendNpcOnInterface(33311, entry.npcId);
        System.out.println(entry.toString());
    }

    public static boolean handleButton(Player player, int id) {
        if (id >= -32215 && id <= -32165) {
            try {
                if (Objects.nonNull(currentlyViewing.get(id + 32215)))
                    sendData(player, currentlyViewing.get(id + 32215));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            return true;
        }
        switch (id) {
            case 14650:
                player.getPA().sendInterfaceRemoval();
                return true;
            case -32234:
                sendNames(player, Type.MONSTERS);
                return true;
            case -32233:
                sendNames(player, Type.BOSSES);
                return true;
        }
        return false;
    }
}


