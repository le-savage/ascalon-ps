package com.janus.model;

import com.janus.world.entity.Entity;


public class GroundItem extends Entity {

    private Item item;
    private String owner, fromIP;
    private boolean isGlobal;
    private int showDelay;
    private boolean goGlobal;
    private int globalTimer;
    private boolean hasBeenPickedUp;
    private boolean refreshNeeded;
    private boolean shouldProcess = true;
    public GroundItem(Item item, Position pos, String owner, boolean isGlobal, int showDelay, boolean goGlobal, int globalTimer) {
        super(pos);
        this.setItem(item);
        this.owner = owner;
        this.fromIP = "";
        this.isGlobal = isGlobal;
        this.showDelay = showDelay;
        this.goGlobal = goGlobal;
        this.globalTimer = globalTimer;
    }
    public GroundItem(Item item, Position pos, String owner, String fromIP, boolean isGlobal, int showDelay, boolean goGlobal, int globalTimer) {
        super(pos);
        this.setItem(item);
        this.owner = owner;
        this.fromIP = fromIP;
        this.isGlobal = isGlobal;
        this.showDelay = showDelay;
        this.goGlobal = goGlobal;
        this.globalTimer = globalTimer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFromIP() {
        return this.fromIP;
    }

    public void setFromIP(String IP) {
        this.fromIP = IP;
    }

    public void setGlobalStatus(boolean l) {
        this.isGlobal = l;
    }

    public boolean isGlobal() {
        return this.isGlobal;
    }

    public int getShowDelay() {
        return this.showDelay;
    }

    public void setShowDelay(int l) {
        this.showDelay = l;
    }

    public void setGoGlobal(boolean l) {
        this.goGlobal = l;
    }

    public boolean shouldGoGlobal() {
        return this.goGlobal;
    }

    public int getGlobalTimer() {
        return this.globalTimer;
    }

    public void setGlobalTimer(int l) {
        this.globalTimer = l;
    }

    public void setPickedUp(boolean s) {
        this.hasBeenPickedUp = s;
    }

    public boolean hasBeenPickedUp() {
        return this.hasBeenPickedUp;
    }

    public boolean isRefreshNeeded() {
        return this.refreshNeeded;
    }

    public void setRefreshNeeded(boolean s) {
        this.refreshNeeded = s;
    }

    public boolean shouldProcess() {
        return shouldProcess;
    }

    public void setShouldProcess(boolean shouldProcess) {
        this.shouldProcess = shouldProcess;
    }
}