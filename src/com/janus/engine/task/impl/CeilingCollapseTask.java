package com.janus.engine.task.impl;


import com.janus.engine.task.Task;
import com.janus.model.CombatIcon;
import com.janus.model.Graphic;
import com.janus.model.Hit;
import com.janus.model.Hitmask;
import com.janus.model.Locations.Location;
import com.janus.util.RandomUtility;
import com.janus.world.entity.impl.player.Player;

/**
 * Barrows
 *
 * @author Gabriel Hannason
 */
public class CeilingCollapseTask extends Task {

    private Player player;

    public CeilingCollapseTask(Player player) {
        super(9, player, false);
        this.player = player;
    }

    @Override
    public void execute() {
        if (player == null || !player.isRegistered() || player.getLocation() != Location.BARROWS && player.getLocation() != Location.KRAKEN || player.getLocation() != Location.ZULRAH || player.getLocation() == Location.BARROWS && player.getPosition().getY() < 8000) {
            player.getPacketSender().sendCameraNeutrality();
            stop();
            return;
        }
        player.performGraphic(new Graphic(60));
        player.getPacketSender().sendMessage("Some rocks fall from the ceiling and hit you.");
        player.forceChat("Ouch!");
        player.dealDamage(new Hit(30 + RandomUtility.getRandom(20), Hitmask.RED, CombatIcon.BLOCK));
    }
}
