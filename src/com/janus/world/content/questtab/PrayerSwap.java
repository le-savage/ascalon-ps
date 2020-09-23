package com.janus.world.content.questtab;

import com.janus.GameSettings;
import com.janus.model.Locations;
import com.janus.model.Prayerbook;
import com.janus.util.Misc;
import com.janus.world.content.combat.prayer.CurseHandler;
import com.janus.world.content.combat.prayer.PrayerHandler;
import com.janus.world.entity.impl.player.Player;

public class PrayerSwap {

    public static void swap(Player player) {

        if (player.getRights().ordinal() < 6 && !player.getRights().isStaff()) {// Stop if the player is below super donor and isn't staff
            player.getPA().sendMessage("You must be at least a super donator to use this feature");
            return;
        }

        if (player.getLocation().equals(Locations.Location.BOSS_TIER_LOCATION) || player.getLocation().equals(Locations.Location.WILDERNESS)) {
            player.getPA().sendMessage("You can't do this here!");
            return;
        }

        if (player.getCombatBuilder().isBeingAttacked()) {
            player.getPA().sendMessage("You can't do this whilst under attack!");
            return;
        }


            if (player.getPrayerbook().equals(Prayerbook.NORMAL))
            player.setPrayerbook(Prayerbook.CURSES);
        else if (player.getPrayerbook().equals(Prayerbook.CURSES))
            player.setPrayerbook(Prayerbook.NORMAL);

        refreshInterface(player);

    }

    public static void refreshInterface(Player player) {
        player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, player.getPrayerbook().getInterfaceId()).sendMessage("Your prayer book was swapped to @blu@"+ Misc.formatText(player.getPrayerbook().name()));
        PrayerHandler.deactivateAll(player);
        CurseHandler.deactivateAll(player);
    }
}
