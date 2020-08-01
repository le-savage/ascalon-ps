package com.janus.net.packet.impl;

import com.janus.model.Flag;
import com.janus.net.packet.Packet;
import com.janus.net.packet.PacketListener;
import com.janus.world.entity.impl.player.Player;

public class ItemColorCustomization implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {

        int itemId = packet.readUnsignedShort();
        int size = packet.readUnsignedByte();

        System.out.println(itemId);

        switch (itemId) {
            case 14019:
            case 14022:

                int[] colors = new int[size];

                for (int i = 0; i < size; i++) {
                    colors[i] = packet.readInt();
                }

                player.setMaxCapeColors(colors);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                player.getPacketSender().sendInterfaceRemoval();

                break;

        }

    }

}
