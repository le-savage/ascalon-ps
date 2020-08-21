package com.janus.model;

import com.janus.GameSettings;
import com.janus.model.definitions.ItemDefinition;
import com.janus.world.content.Effigies;


/**
 * Represents an item which is owned by a player.
 *
 * @author relex lawl
 */

public class Item {

    /**
     * ITEM RARITY
     **/
    public ItemRarity rarity;
    /**
     * The item id.
     */
    private int id;
    /**
     * Amount of the item.
     */
    private int amount;
    private int slot;

    /**
     * An Item object constructor.
     *
     * @param id     Item id.
     * @param amount Item amount.
     */
    public Item(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    /**
     * An Item object constructor.
     *
     * @param id Item id.
     */
    public Item(int id) {
        this(id, 1);
    }

    public static Item getNoted(int id, int amount) {
        int notedItem = id + 1;
        if (ItemDefinition.forId(notedItem).getName().equals(ItemDefinition.forId(id).getName())) {
            return new Item(notedItem, amount);
        }
        return new Item(id, amount);
    }

    public static int getNoted(int id) {
        int noted = id + 1;
        if (id == 11283 || id == 11284) {
            noted = 11285;
        }
        if (ItemDefinition.forId(noted).getName().equals(ItemDefinition.forId(id).getName())) {
            return noted;
        }
        return id;
    }

    public static int getUnNoted(int id) {
        int unNoted = id - 1;
        if (id == 11284 || id == 11285) {
            unNoted = 11283;
        }
        if (ItemDefinition.forId(unNoted).getName().equals(ItemDefinition.forId(id).getName())) {
            return unNoted;
        }
        return id;
    }

    public static boolean tradeable(int item) {
        return new Item(item).tradeable();
    }

    public static boolean sellable(int item) {
        return new Item(item).sellable();
    }

    /**
     * Gets the item's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the item's id.
     *
     * @param id New item id.
     */
    public Item setId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Gets the amount of the item.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the item.
     */
    public Item setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Gets item's definition.
     */
    public ItemDefinition getDefinition() {
        return ItemDefinition.forId(id);
    }

    public boolean tradeable() {
        String name = getDefinition().getName().toLowerCase();

        if (name.toLowerCase().contains("(deg)") || name.toLowerCase().contains("brawling"))
            return false;
        if (name.toLowerCase().contains("Primal"))
            return false;

        for (int i : GameSettings.UNTRADEABLE_ITEMS) {
            if (id == i)
                return false;
        }
        if (Effigies.isEffigy(id))
            return false;
        return true;
    }

    public boolean sellable() {
        String name = getDefinition().getName().toLowerCase();

        if (name.contains("overload") || name.contains("extreme"))
            return false;
        if (name.toLowerCase().contains("(deg)") || name.toLowerCase().contains("brawling"))
            return false;
        if (name.toLowerCase().contains("Primal"))
            return false;
        for (int i : GameSettings.UNSELLABLE_ITEMS) {
            if (id == i)
                return false;
        }
        if (Effigies.isEffigy(id))
            return false;
        return true;
    }

    /**
     * Copying the item by making a new item with same values.
     */
    public Item copy() {
        return new Item(id, amount);
    }

    /**
     * Increment the amount by 1.
     */
    public void incrementAmount() {
        if ((amount + 1) > Integer.MAX_VALUE) {
            return;
        }
        amount++;
    }

    /**
     * Decrement the amount by 1.
     */
    public void decrementAmount() {
        if ((amount - 1) < 0) {
            return;
        }
        amount--;
    }

    /**
     * Increment the amount by the specified amount.
     */
    public void incrementAmountBy(int amount) {
        if ((this.amount + amount) > Integer.MAX_VALUE) {
            this.amount = Integer.MAX_VALUE;
        } else {
            this.amount += amount;
        }
    }

    /**
     * Decrement the amount by the specified amount.
     */
    public void decrementAmountBy(int amount) {
        if ((this.amount - amount) < 1) {
            this.amount = 0;
        } else {
            this.amount -= amount;
        }
    }

    public Item setRarity(ItemRarity rarity) {
        this.rarity = rarity;
        return this;
    }
}