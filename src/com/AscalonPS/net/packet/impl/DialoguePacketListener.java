package com.AscalonPS.net.packet.impl;

import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.world.content.dialogue.DialogueManager;
import com.AscalonPS.world.entity.impl.player.Player;

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
