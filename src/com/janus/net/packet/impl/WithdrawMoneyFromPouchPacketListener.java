package com.janus.net.packet.impl;

import com.janus.net.packet.Packet;
import com.janus.net.packet.PacketListener;
import com.janus.world.content.MoneyPouch;
import com.janus.world.entity.impl.player.Player;

public class WithdrawMoneyFromPouchPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int amount = packet.readInt();
        //long amount = packet.readLong();
        if (player.getTrading().inTrade() || player.getDueling().inDuelScreen) {
            player.getPacketSender().sendMessage("You cannot withdraw money at the moment.");
        } else {
            MoneyPouch.withdrawMoney(player, amount);
        }
    }

}
