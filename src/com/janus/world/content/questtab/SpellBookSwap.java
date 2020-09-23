package com.janus.world.content.questtab;

import com.janus.GameSettings;
import com.janus.model.Locations;
import com.janus.model.MagicSpellbook;
import com.janus.util.Misc;
import com.janus.world.content.combat.magic.Autocasting;
import com.janus.world.entity.impl.player.Player;

public class SpellBookSwap {

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

        player.setSpellbook(player.getSpellbook().equals(MagicSpellbook.NORMAL)
                ? MagicSpellbook.ANCIENT
                : player.getSpellbook().equals(MagicSpellbook.ANCIENT)
                ? MagicSpellbook.LUNAR
                : MagicSpellbook.NORMAL);
        refreshInterface(player);

    }

    public static void refreshInterface(Player player) {
        player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId())
                .sendMessage("Your magic spellbook has been changed to @blu@"+ Misc.formatText(player.getSpellbook().name()));

        Autocasting.resetAutocast(player, true);
    }
}
