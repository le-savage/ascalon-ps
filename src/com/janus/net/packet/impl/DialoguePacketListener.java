package com.janus.net.packet.impl;

import com.janus.net.packet.Packet;
import com.janus.net.packet.PacketListener;
import com.janus.world.content.dialogue.DialogueManager;
import com.janus.world.entity.impl.player.Player;

/**
 * This packet listener handles player's mouse click on the
 * "Click here to continue" option, etc.
 *
 * @author relex lawl
 */

public class DialoguePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        switch (packet.getOpcode()) {
            case DIALOGUE_OPCODE:
                DialogueManager.next(player);
                break;
        }
    }

    public static final int DIALOGUE_OPCODE = 40;
}
