package com.AscalonPS.net.packet.impl;

import com.AscalonPS.model.Skill;
import com.AscalonPS.model.definitions.ItemDefinition;
import com.AscalonPS.net.packet.Packet;
import com.AscalonPS.net.packet.PacketListener;
import com.AscalonPS.util.Misc;
import com.AscalonPS.world.entity.impl.player.Player;

public class ExamineItemPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int item = packet.readShort();
        ItemDefinition itemDef = ItemDefinition.forId(item);
        int itemAmount = player.getInventory().getAmount(item);
        //int totalPrice = ItemDefinition.getDefinition().getValue() * itemAmount;
        int value = itemDef.getValue();
        if (item == 995 || item == 18201) {
            //player.getPacketSender().sendMessage("Mhmm... Shining coins...");
            player.getPacketSender().sendMessage("Mhmm... Shiny coins... " + Misc.insertCommasToNumber(Integer.toString(value * itemAmount)) + " of them!");

            return;
        }

        if (itemDef != null) {
            if (itemDef.getValue() == 0) {
                player.getPacketSender().sendMessage(itemDef.getDescription());
            } else {
                player.getPacketSender().sendMessage(itemDef.getDescription() + " Worth " + Misc.insertCommasToNumber(Integer.toString(value)) + " GP.");

            }
            for (Skill skill : Skill.values()) {
                if (itemDef.getRequirement()[skill.ordinal()] > player.getSkillManager().getMaxLevel(skill)) {
                    player.getPacketSender().sendMessage("@red@WARNING: You need " + new StringBuilder().append(skill.getName().startsWith("a") || skill.getName().startsWith("e") || skill.getName().startsWith("i") || skill.getName().startsWith("o") || skill.getName().startsWith("u") ? "an " : "a ").toString() + Misc.formatText(skill.getName()) + " level of at least " + itemDef.getRequirement()[skill.ordinal()] + " to wear this.");
                }
            }
        }
    }

}
