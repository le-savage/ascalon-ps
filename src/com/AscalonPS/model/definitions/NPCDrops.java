package com.AscalonPS.model.definitions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.AscalonPS.model.*;
import com.AscalonPS.model.Locations.Location;
import com.AscalonPS.model.container.impl.Bank;
import com.AscalonPS.model.container.impl.Equipment;
import com.AscalonPS.util.JsonLoader;
import com.AscalonPS.util.Misc;
import com.AscalonPS.util.Rand;
import com.AscalonPS.util.RandomUtility;
import com.AscalonPS.world.World;
import com.AscalonPS.world.content.CoinCollector;
import com.AscalonPS.world.content.DropLog;
import com.AscalonPS.world.content.PlayerLogs;
import com.AscalonPS.world.content.clan.ClanChatManager;
import com.AscalonPS.world.content.discord.DiscordMessenger;
import com.AscalonPS.world.content.minigames.impl.WarriorsGuild;
import com.AscalonPS.world.content.skill.impl.prayer.BonesData;
import com.AscalonPS.world.content.skill.impl.summoning.CharmingImp;
import com.AscalonPS.world.entity.impl.GroundItemManager;
import com.AscalonPS.world.entity.impl.npc.NPC;
import com.AscalonPS.world.entity.impl.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Controls the npc drops
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>, Gabbe &
 * Samy
 */
public class NPCDrops {

    /**
     * The map containing all the npc drops.
     */
    private static Map<Integer, NPCDrops> dropControllers = new HashMap<Integer, NPCDrops>();

    public static JsonLoader parseDrops() {

        //ItemDropAnnouncer.init();

        return new JsonLoader() {

            @Override
            public void load(JsonObject reader, Gson builder) {
                int[] npcIds = builder.fromJson(reader.get("npcIds"),
                        int[].class);
                NpcDropItem[] drops = builder.fromJson(reader.get("drops"),
                        NpcDropItem[].class);

                NPCDrops d = new NPCDrops();
                d.npcIds = npcIds;
                d.drops = drops;
                for (int id : npcIds) {
                    dropControllers.put(id, d);
//					System.out.println("put: "+id+" "+d);
                }
            }

            @Override
            public String filePath() {
                return "./data/def/json/drops.json";
            }
        };
    }

    private static final int[] CLUESBOY = new int[]{2677, 2678, 2679, 2680, 2681, 2682, 2683, 2684, 2685};
    /**
     * The id's of the NPC's that "owns" this class.
     */
    private int[] npcIds;

    /**
     * Gets the NPC drop controller by an id.
     *
     * @return The NPC drops associated with this id.
     */
    public static NPCDrops forId(int id) {
        return dropControllers.get(id);
    }

    public static Map<Integer, NPCDrops> getDrops() {
        return dropControllers;
    }
    /**
     * All the drops that belongs to this class.
     */
    private NpcDropItem[] drops;

    public static void drop(Player player, Item item, NPC npc, Position pos,
                            boolean goGlobal) {


        if (player.getInventory().contains(18337)
                && BonesData.forId(item.getId()) != null) {
            player.getPacketSender().sendGlobalGraphic(new Graphic(777), pos);
            player.getSkillManager().addExperience(Skill.PRAYER,
                    BonesData.forId(item.getId()).getBuryingXP());
            return;
        }
        int itemId = item.getId();
        int amount = item.getAmount();

        if (itemId == CoinCollector.COINS) {
            if ((player.getInventory().contains(21026) || (player.getEquipment().getItems()[Equipment.RING_SLOT].getId() == 21026))
                    && CoinCollector.handleCoinDrop(player, itemId, amount)) {
                return;
            }
        }


        if (itemId == 6731 || itemId == 6914 || itemId == 7158 || itemId == 6889 || itemId == 6733 || itemId == 15019 || itemId == 11235 || itemId == 15020 || itemId == 15018 || itemId == 15220 || itemId == 6735 || itemId == 6737 || itemId == 6585 || itemId == 4151 || itemId == 4087 || itemId == 2577 || itemId == 2581 || itemId == 11732 || itemId == 18782) {
            player.getPacketSender().sendMessage("@red@ YOU HAVE RECEIVED A MEDIUM DROP, CHECK THE GROUND!");
        }




        Player toGive = player;

        boolean ccAnnounce = false;
        if (Location.inMulti(player)) {
            if (player.getCurrentClanChat() != null && player.getCurrentClanChat().getLootShare()) {
                CopyOnWriteArrayList<Player> playerList = new CopyOnWriteArrayList<Player>();
                for (Player member : player.getCurrentClanChat().getMembers()) {
                    if (member != null) {
                        if (member.getPosition().isWithinDistance(player.getPosition())) {
                            playerList.add(member);
                        }
                    }
                }
                if (playerList.size() > 0) {
                    toGive = playerList.get(RandomUtility.getRandom(playerList.size() - 1));
                    if (toGive == null || toGive.getCurrentClanChat() == null || toGive.getCurrentClanChat() != player.getCurrentClanChat()) {
                        toGive = player;
                    }
                    ccAnnounce = true;
                }
            }
        }

        if (itemId == CharmingImp.GOLD_CHARM
                || itemId == CharmingImp.GREEN_CHARM
                || itemId == CharmingImp.CRIM_CHARM
                || itemId == CharmingImp.BLUE_CHARM) {


            if (player.getInventory().contains(6500) && CharmingImp.handleCharmDrop(player, itemId, amount)) {
                return;
            } else {
                GroundItemManager.spawnGroundItem(toGive, new GroundItem(item, pos, toGive.getUsername(), false, 150, goGlobal, 200));

            }
        }

        if (itemId == 18778) { //Effigy, don't drop one if player already has one
            if (toGive.getInventory().contains(18778) || toGive.getInventory().contains(18779) || toGive.getInventory().contains(18780) || toGive.getInventory().contains(18781)) {
                return;
            }
            for (Bank bank : toGive.getBanks()) {
                if (bank == null) {
                    continue;
                }
                if (bank.contains(18778) || bank.contains(18779) || bank.contains(18780) || bank.contains(18781)) {
                    return;
                }
            }
        }

        if (ItemDropAnnouncer.announce(item)) {
            String itemName = item.getDefinition().getName();
            String itemMessage = Misc.anOrA(itemName) + " " + itemName;
            String npcName = Misc.formatText(npc.getDefinition().getName());

            switch (itemId) {
                case 14484:
                    itemMessage = "a pair of Dragon Claws";
                    break;
                case 20000:
                case 20001:
                case 20002:
                    itemMessage = itemName;
                    break;
            }
            switch (npc.getId()) {
                case 81:
                    npcName = "a Cow";
                    break;
                case 50:
                case 3200:
                case 8133:
                case 4540:
                case 1160:
                case 8549:
                    npcName = "The " + npcName + "";
                    break;
                case 51:
                case 54:
                case 5363:
                case 8349:
                case 1592:
                case 1591:
                case 1590:
                case 1615:
                case 9463:
                case 9465:
                case 9467:
                case 1382:
                case 13659:
                case 11235:
                    npcName = "" + Misc.anOrA(npcName) + " " + npcName + "";
                    break;
            }
            String message = "@blu@[RARE DROP] " + toGive.getUsername()
                    + " has just received @red@" + itemMessage + "@blu@ from " + npcName
                    + "!";
            World.sendFilteredMessage(message);
            DiscordMessenger.sendRareDrop(message);
            if (ccAnnounce) {
                ClanChatManager.sendMessage(player.getCurrentClanChat(), "<col=16777215>[<col=255>Lootshare<col=16777215>]<col=3300CC> " + toGive.getUsername() + " received " + itemMessage + " from " + npcName + "!");
            }

            PlayerLogs.log(toGive.getUsername(), "" + toGive.getUsername() + " received " + itemMessage + " from " + npcName + "");
        }


        if (item.getDefinition().isStackable() && (player.getRights() != PlayerRights.PLAYER || player.getRights() != PlayerRights.DONATOR)) {
            if (item.getDefinition().getValue() >= player.getPickupValue() && (player.getInventory().getFreeSlots() >= 1)) {
                player.getInventory().add(itemId, item.getAmount());
                //System.out.println("Add stackable item to inventory: "+item.getDefinition().getName()+" for "+toGive.getUsername());
                player.getPacketSender().sendMessage("@red@We picked up @blu@" + item.getAmount() + "@red@ x @blu@" + item.getDefinition().getName() + "@red@ worth @blu@" + Misc.setupMoney(item.getDefinition().getValue()));
            } else {
                GroundItemManager.spawnGroundItem(toGive, new GroundItem(item, pos, toGive.getUsername(), false, 150, goGlobal, 200));
                DropLog.submit(toGive, new DropLog.DropLogEntry(itemId, item.getAmount()));
                //System.out.println("Spawn stackable item : "+item.getDefinition().getName()+" for "+toGive.getUsername());
            }
        }

        if (!item.getDefinition().isStackable() && (player.getRights() != PlayerRights.PLAYER || player.getRights() != PlayerRights.DONATOR)) {
            if ((player.getInventory().getFreeSlots() >= item.getAmount()) && (item.getDefinition().getValue() >= player.getPickupValue())) {
                player.getInventory().add(itemId, item.getAmount());
                //System.out.println("Add non stackable item to inventory : "+item.getDefinition().getName()+" for "+toGive.getUsername());
                player.getPacketSender().sendMessage("@red@We picked up @blu@" + item.getAmount() + "@red@ x @blu@" + item.getDefinition().getName() + "@red@ worth @blu@" + Misc.setupMoney(item.getDefinition().getValue()));
            } else {
                GroundItemManager.spawnGroundItem(toGive, new GroundItem(item, pos, toGive.getUsername(), false, 150, goGlobal, 200));
                DropLog.submit(toGive, new DropLog.DropLogEntry(itemId, item.getAmount()));
                //System.out.println("Spawn non stackable item : "+item.getDefinition().getName()+" for "+toGive.getUsername());
            }
        }


        if ((player.getRights() == PlayerRights.PLAYER || player.getRights() == PlayerRights.DONATOR) && (player.getLocation() == Location.INSTANCE_ARENA)) {
            //System.out.println("Player or reg donor in instance arena");
        }
    }

    /**
     * Gets the drop list
     *
     * @return the list
     */
    public NpcDropItem[] getDropList() {
        return drops;
    }

    /**
     * Gets the npcIds
     *
     * @return the npcIds
     */
    public int[] getNpcIds() {
        return npcIds;
    }


    /**
     * Drops items for a player after killing an npc. A player can max receive
     * one item per drop chance.
     *
     * @param p   Player to receive drop.
     * @param npc NPC to receive drop FROM.
     */
    public static void dropItems(Player p, NPC npc) {
        if (npc.getLocation() == Location.WARRIORS_GUILD)
            WarriorsGuild.handleDrop(p, npc);

        NPCDrops drops = NPCDrops.forId(npc.getId());

        if (drops == null)
            return;

        final boolean goGlobal = p.getPosition().getZ() >= 0 && p.getPosition().getZ() < 4;
        Position npcPos = npc.getPosition().copy();
        if (npc.getId() == 2044 || npc.getId() == 2043 || npc.getId() == 2042 || npc.getId() == 2005)
            npcPos = p.getPosition().copy();
        boolean[] dropsReceived = new boolean[12];


        if (drops.getDropList().length > 0 && p.getPosition().getZ() >= 0 && p.getPosition().getZ() < 4) {

            casketDrop(p, npc.getDefinition().getCombatLevel(), npcPos);
        }

        if (drops.getDropList().length > 0 && p.getPosition().getZ() >= 0 && p.getPosition().getZ() < 4) {
            clueDrop(p, npc.getDefinition().getCombatLevel(), npcPos);

        }


        for (int i = 0; i < drops.getDropList().length; i++) {
            if (drops.getDropList()[i].getItem().getId() <= 0 || drops.getDropList()[i].getItem().getId() > ItemDefinition.getMaxAmountOfItems() || drops.getDropList()[i].getItem().getAmount() <= 0) {
                continue;
            }

            DropChance dropChance = drops.getDropList()[i].getChance();

            if (dropChance == DropChance.ALWAYS) {
                drop(p, drops.getDropList()[i].getItem(), npc, npcPos, goGlobal);
            } else {
                //System.out.println("Should drop? "+shouldDrop(dropsReceived, p, dropChance));
                if (shouldDrop(dropsReceived, p, dropChance)) {
                    drop(p, drops.getDropList()[i].getItem(), npc, npcPos, goGlobal);
                    dropsReceived[dropChance.ordinal()] = true;
                }
            }
        }

    }


    public static boolean shouldDrop(boolean[] b, Player player, DropChance chance) {
        int random = chance.getRandom();
        //System.out.println("Random: "+random);
        double drBoost = NPCDrops.getDroprate(player);
        double percentage = random / 100;
        random = (int) (chance.getRandom() - (percentage * drBoost)); //TODO UPDATE THIS TO CHECK FOR EACH DROP NOT JUST ONE
        if (Math.toIntExact(Math.round(random)) <= 1) {
            System.out.println(Math.toIntExact(Math.round(random)));
            return true;
        }
        return Rand.hit(Math.toIntExact(Math.round(random)));
    }

    public static double getDroprate(Player p) {
        double drBoost = 0;
        drBoost += p.getGameMode().getDropRateModifier();
        drBoost += p.getDifficulty().getDropRateModifier();
        if (ringOfWealth(p)) drBoost += 1;
        if (ringOfCoins(p)) drBoost += 1;
        return drBoost;
    }

    public static boolean ringOfWealth(Player p) {
        return (p.getEquipment().get(Equipment.RING_SLOT).getId() == 2572);
    }

    public static boolean ringOfCoins(Player p) {
        return (p.getEquipment().get(Equipment.RING_SLOT).getId() == 21026);
    }


    public enum DropChance {
        ALWAYS(0), ALMOST_ALWAYS(3), VERY_COMMON(7), COMMON(20), UNCOMMON(60), NOTTHATRARE(
                120), RARE(360), LEGENDARY(550), LEGENDARY_2(770), LEGENDARY_3(990), LEGENDARY_4(1400), LEGENDARY_5(1900);


        private int random;

        DropChance(int randomModifier) {
            this.random = randomModifier;
        }

        public int getRandom() {
            return this.random;
        }
    }

    public static void casketDrop(Player player, int combat, Position pos) {
        int chance = (6 + (combat / 2));
        int casketID = 7956;
        if (RandomUtility.getRandom(combat <= 50 ? 1300 : 1000) < chance) {

            if (player.getPickupValue() > ItemDefinition.forId(casketID).getValue()) {
                player.getInventory().add(casketID, 1);
            } else {
                player.getPacketSender().sendMessage("A casket has been dropped!");
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(casketID), pos, player.getUsername(), false, 150, true, 200));
            }
        }
    }

    /**
     * Represents a npc drop item
     */
    public static class NpcDropItem {

        /**::empty
         * The id.
         */
        private final int id;

        /**
         * Array holding all the amounts of this item.
         */
        private final int[] count;

        /**
         * The chance of getting this item.
         */
        private final int chance;

        /**
         * New npc drop item
         *
         * @param id     the item
         * @param count  the count
         * @param chance the chance
         */
        public NpcDropItem(int id, int[] count, int chance) {
            this.id = id;
            this.count = count;
            this.chance = chance;
        }

        /**
         * Gets the item id.
         *
         * @return The item id.
         */
        public int getId() {
            return id;
        }

        /**
         * Gets the chance.
         *
         * @return The chance.
         */
        public int[] getCount() {
            return count;
        }

        /**
         * Gets the chance.
         *
         * @return The chance.
         */
        public DropChance getChance() {
            switch (chance) {
                case 1:
                    return DropChance.ALMOST_ALWAYS; // 50% <-> 1/2
                case 2:
                    return DropChance.VERY_COMMON; // 20% <-> 1/5
                case 3:
                    return DropChance.COMMON; // 5% <-> 1/20
                case 4:
                    return DropChance.UNCOMMON; // 2% <-> 1/50
                case 5:
                    return DropChance.RARE; // 0.5% <-> 1/200
                case 6:
                    return DropChance.LEGENDARY; // 0.2% <-> 1/500
                case 7:
                    return DropChance.LEGENDARY_2;
                case 8:
                    return DropChance.LEGENDARY_3;
                case 9:
                    return DropChance.LEGENDARY_4;
                case 10:
                    return DropChance.LEGENDARY_5;
                default:
                    return DropChance.ALWAYS; // 100% <-> 1/1
            }
        }

        /**
         * Gets the item
         *
         * @return the item
         */
        public Item getItem() {
            int amount = 0;
            for (int i = 0; i < count.length; i++)
                amount += count[i];
            if (amount > count[0])
                amount = count[0] + RandomUtility.getRandom(count[1]);
            return new Item(id, amount);
        }
    }


    public static void clueDrop(Player player, int combat, Position pos) {
        int chance = (6 + (combat / 4));
        if (RandomUtility.getRandom(combat <= 80 ? 1300 : 1000) < chance) {
            int clueId = CLUESBOY[Misc.getRandom(CLUESBOY.length - 1)];
            ItemDefinition clueDefinition = ItemDefinition.forId(clueId);
            int clueValue = clueDefinition.getValue();

            if ((player.getPickupValue() >= clueValue) && player.getInventory().getFreeSlots() >= 1) {
                player.getInventory().add(clueId, 1);
                player.getPacketSender().sendMessage("We picked up a " + clueDefinition.getName() + "!");
            } else {
                GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(clueId), pos, player.getUsername(), false, 150, true, 200));
                player.getPacketSender().sendMessage("@or2@You have received a clue scroll!");
            }
        }
    }


    public static class ItemDropAnnouncer {

        public static boolean announce(Item item) {
            return ((item.getDefinition().getValue() > 1000000) || item.getDefinition().getName().contains("pet"));
        }

    }

}