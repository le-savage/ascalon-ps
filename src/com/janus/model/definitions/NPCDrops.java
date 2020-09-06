package com.janus.model.definitions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.janus.model.*;
import com.janus.model.Locations.Location;
import com.janus.model.container.impl.Bank;
import com.janus.model.container.impl.Equipment;
import com.janus.util.JsonLoader;
import com.janus.util.Misc;
import com.janus.util.Rand;
import com.janus.util.RandomUtility;
import com.janus.world.World;
import com.janus.world.content.CoinCollector;
import com.janus.world.content.DropLog;
import com.janus.world.content.PlayerLogs;
import com.janus.world.content.clan.ClanChatManager;
import com.janus.world.content.collectionlog.CollectionLogEntry;
import com.janus.world.content.discord.DiscordMessenger;
import com.janus.world.content.minigames.impl.WarriorsGuild;
import com.janus.world.content.skill.impl.prayer.BonesData;
import com.janus.world.content.skill.impl.summoning.CharmingImp;
import com.janus.world.entity.impl.GroundItemManager;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;

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

    private static final int[] CLUESBOY = new int[]{2677, 2678, 2679, 2680, 2681, 2682, 2683, 2684, 2685};
    /**
     * The map containing all the npc drops.
     */
    private static Map<Integer, NPCDrops> dropControllers = new HashMap<Integer, NPCDrops>();
    /**
     * The id's of the NPC's that "owns" this class.
     */
    private int[] npcIds;

    /**
     * All the drops that belongs to this class.
     */
    private NpcDropItem[] drops;

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
                if (shouldDrop(dropsReceived, p, dropChance)) {
                    drop(p, drops.getDropList()[i].getItem(), npc, npcPos, goGlobal);
                    dropsReceived[dropChance.ordinal()] = true;
                }
            }
        }

    }

    public static boolean shouldDrop(boolean[] b, Player player, DropChance chance) {
        int random = chance.getRandom();
        double drBoost = NPCDrops.getDroprate(player);
        double variable = ((drBoost));
        double percentage = random / 100;
        random = (int) (chance.getRandom() - (percentage * variable));
        if (Math.toIntExact(Math.round(random)) <= 1)
            return true;
        return Rand.hit(Math.toIntExact(Math.round(random)));
    }

    public static double getDroprate(Player p) {
        double drBoost = 0;
        drBoost += 5; // this is 5%
        drBoost += p.getGameMode().getDropRateModifier();
        drBoost += p.getDifficulty().getDropRateModifier();
        if (ringOfWealth(p)) drBoost += 2;
        if (ringOfCoins(p)) drBoost += 2;
        return drBoost;
    }

    public static boolean ringOfWealth(Player p) {
        return (p.getEquipment().get(Equipment.RING_SLOT).getId() == 2572);
    }

    public static boolean ringOfCoins(Player p) {
        return (p.getEquipment().get(Equipment.RING_SLOT).getId() == 21026);
    }

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
            /*if (player.getNotificationPreference()) {
                player.getPacketSender().minimisedTrayMessage(3, player.getUsername() + " - You received a medium drop!");
            }*/
        }


        if (itemId == CharmingImp.GOLD_CHARM
                || itemId == CharmingImp.GREEN_CHARM
                || itemId == CharmingImp.CRIM_CHARM
                || itemId == CharmingImp.BLUE_CHARM) {
            if (player.getInventory().contains(6500)
                    && CharmingImp.handleCharmDrop(player, itemId, amount)) {
                return;
            }
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

        if (ItemDropAnnouncer.announce(item) && player.getLocation() != Location.BOSS_TIER_LOCATION) {
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
                    + " has just received @red@" + itemMessage + "@blu@ from " + npcName + "!";
            World.sendFilteredMessage(message);
            DiscordMessenger.sendRareDrop(message);
            /*if (toGive.getNotificationPreference()) {
                toGive.getPacketSender().trayMessage(3, toGive.getUsername() + " - Rare Drop: " + itemMessage + "!");
            }*/
            if (ccAnnounce) {
                ClanChatManager.sendMessage(player.getCurrentClanChat(), "<col=16777215>[<col=255>Lootshare<col=16777215>]<col=3300CC> " + toGive.getUsername() + " received " + itemMessage + " from " + npcName + "!");
            }

            PlayerLogs.log(toGive.getUsername(), "" + toGive.getUsername() + " received " + itemMessage + " from " + npcName + "");
        }


        /** Disable drops from ::boss minigame **/
        if (player.getLocation().equals(Location.BOSS_TIER_LOCATION)) {
            return;
        }



        /** Begin methods to implement the ::pickup value **/

        /** Pickup method applies to any player donator+
         * We then check for the player.getPickupValue()
         * The first method covers stackable items
         *
         * The second method covers the non-stackable
         * items. They are separated to ensure mulitple
         * drops are not obtained.
         */
        if (item.getDefinition().isStackable() && player.getRights() != PlayerRights.PLAYER && player.getRights() != PlayerRights.DONATOR) {
            if (item.getDefinition().getValue() >= player.getPickupValue() && (player.getInventory().getFreeSlots() >= 1)) {
                player.getInventory().add(itemId, item.getAmount());
                System.out.println("Add stackable item to inventory: "+item.getDefinition().getName()+" for "+toGive.getUsername());
                player.getPacketSender().sendMessage("@red@We picked up @blu@" + item.getAmount() + "@red@ x @blu@" + item.getDefinition().getName() + "@red@ worth @blu@" + Misc.setupMoney(item.getDefinition().getValue()));
            } else {
                GroundItemManager.spawnGroundItem(toGive, new GroundItem(item, pos, toGive.getUsername(), false, 150, goGlobal, 200));
                DropLog.submit(toGive, new DropLog.DropLogEntry(itemId, item.getAmount()));
                System.out.println("Spawn stackable item : "+item.getDefinition().getName()+" for "+toGive.getUsername());
            }
        }

        if (!item.getDefinition().isStackable() && player.getRights() != PlayerRights.PLAYER && player.getRights() != PlayerRights.DONATOR) {
            if ((player.getInventory().getFreeSlots() >= item.getAmount()) && (item.getDefinition().getValue() >= player.getPickupValue())) {
                player.getInventory().add(itemId, item.getAmount());
                System.out.println("Add non stackable item to inventory : "+item.getDefinition().getName()+" for "+toGive.getUsername());
                player.getPacketSender().sendMessage("@red@We picked up @blu@" + item.getAmount() + "@red@ x @blu@" + item.getDefinition().getName() + "@red@ worth @blu@" + Misc.setupMoney(item.getDefinition().getValue()));
            } else {
                GroundItemManager.spawnGroundItem(toGive, new GroundItem(item, pos, toGive.getUsername(), false, 150, goGlobal, 200));
                DropLog.submit(toGive, new DropLog.DropLogEntry(itemId, item.getAmount()));
                System.out.println("Spawn non stackable item : "+item.getDefinition().getName()+" for "+toGive.getUsername());
            }
        }

        if (player.getRights() == PlayerRights.PLAYER || player.getRights() == PlayerRights.DONATOR) {
            GroundItemManager.spawnGroundItem(toGive, new GroundItem(item, pos, toGive.getUsername(), false, 150, goGlobal, 200));
            DropLog.submit(toGive, new DropLog.DropLogEntry(itemId, item.getAmount()));
        }

        new CollectionLogEntry(npc.getId(), item.getId(), item.getAmount()).submit(player);
    }

    public static void casketDrop(Player player, int combat, Position pos) {
        int chance = (6 + (combat / 2));
        if (RandomUtility.getRandom(combat <= 50 ? 1300 : 1000) < chance) {
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(7956), pos, player.getUsername(), false, 150, true, 200));
        }
    }

    public static void clueDrop(Player player, int combat, Position pos) {
        int chance = (6 + (combat / 4));
        if (RandomUtility.getRandom(combat <= 80 ? 1300 : 1000) < chance) {
            int clueId = CLUESBOY[Misc.getRandom(CLUESBOY.length - 1)];
            GroundItemManager.spawnGroundItem(player, new GroundItem(new Item(clueId), pos, player.getUsername(), false, 150, true, 200));
            player.getPacketSender().sendMessage("@or2@You have received a clue scroll!");
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

    public enum DropChance {
        ALWAYS(0), ALMOST_ALWAYS(2), VERY_COMMON(5), COMMON(15), UNCOMMON(40), NOTTHATRARE(
                150), RARE(400), LEGENDARY(500), LEGENDARY_2(650), LEGENDARY_3(800), LEGENDARY_4(890), LEGENDARY_5(1000);


        private int random;

        DropChance(int randomModifier) {
            this.random = randomModifier;
        }

        public int getRandom() {
            return this.random;
        }
    }

    /**
     * Represents a npc drop item
     */
    public static class NpcDropItem {

        /**
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

    /*public static class ItemDropAnnouncer {

        private static List<Integer> ITEM_LIST;

        private static final int[] TO_ANNOUNCE = new int[]{21051, 18899, 4453, 21026, 962, 21000, 20999, 13274, 13275, 13276, 13279, 1543, 6829, 13047, 4453, 13015, 19780, 20555, 12926, 12284, 14018, 17291, 18896, 18898, 18895, 14008, 14009, 14010, 14011, 14012, 14013, 14014, 14015, 14016, 10887, 19780, 2581, 2577, 14472, 14474, 14476,
                6571, 11286, 11732, 4087, 4585, 11335, 3140, 15501, 15259, 12282, 6573, 17291, 12601, 13748, 13750, 13752, 13746,
                20555, 12926, 11235, 13045, 13047, 13239, 12708, 13235, 20057, 20058, 20059, 15126, 19335, 15241, 18337,
                11730, 20000, 20001, 20002, 18778, 15486, 11924, 6889, 11926, 6914, 11724, 11726, 11728, 11718, 11720, 11722, 11710, 11712, 11714,
                11702, 11704, 11706, 4706, 12284, 13051, 11708, 11716, 6739, 2570, 6562, 15220, 15018, 15020, 15019, 6731, 6733, 6735, 6737,
                11981, 11982, 11983, 11984, 11985, 11986, 11987, 11988, 11989, 11990, 11991, 11992, 11993, 11994, 11995, 11996, 11997, 18896, 18898, 20998, 13045};//All Rare Boss Drops};


        private static void init() {
            ITEM_LIST = new ArrayList<Integer>();
            for (int i : TO_ANNOUNCE) {
                ITEM_LIST.add(i);
            }
        }

        public static boolean announce(int item) {

            return ITEM_LIST.contains(item);
        }
    }*/

    public static class ItemDropAnnouncer {

        public static boolean announce(Item item) {
            return ((item.getDefinition().getValue() > 1000000) || item.getDefinition().getName().contains("pet"));
        }

    }

}