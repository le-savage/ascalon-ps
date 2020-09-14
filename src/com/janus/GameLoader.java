package com.janus;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.janus.engine.GameEngine;
import com.janus.engine.task.TaskManager;
import com.janus.engine.task.impl.ServerTimeUpdateTask;
import com.janus.model.container.impl.Shop.ShopManager;
import com.janus.model.definitions.ItemDefinition;
import com.janus.model.definitions.NPCDrops;
import com.janus.model.definitions.NpcDefinition;
import com.janus.model.definitions.WeaponInterfaces;
import com.janus.net.PipelineFactory;
import com.janus.net.security.ConnectionHandler;
import com.janus.world.clip.region.RegionClipping;
import com.janus.world.content.*;
import com.janus.world.content.clan.ClanChatManager;
import com.janus.world.content.combat.effect.CombatPoisonEffect.CombatPoisonData;
import com.janus.world.content.combat.strategy.CombatStrategies;
import com.janus.world.content.combat.weapon.effects.impl.weapon.ItemEffect;
import com.janus.world.content.dialogue.DialogueManager;
import com.janus.world.content.pos.PlayerOwnedShopManager;
import com.janus.world.content.teleport.TeleportRepository;
import com.janus.world.entity.impl.npc.NPC;
import mysql.MySQLController;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Credit: lare96, Gabbe
 */
public final class GameLoader {
    /*
     * Daily events
     * Handles the checking of the day to represent
     * which event will be active on such day
     */
    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    private final ExecutorService serviceLoader = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("GameLoadingThread").build());
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("GameThread").build());
    private final GameEngine engine;
    private final int port;
    protected GameLoader(int port) {
        this.port = port;
        this.engine = new GameEngine();
    }

    //public static Object getSpecialDay;
    //Double EXP days
    public static int getDoubleEXPWeekend() {
        return (getDay() == FRIDAY || getDay() == SUNDAY || getDay() == SATURDAY) ? 2 : 1;
    }

    //Finds the day of the week
    public static int getDay() {
        Calendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //events for each day
    public static String getSpecialDay() {
        switch (getDay()) {
            case MONDAY:
                return "X2 Votes (Donor+)";
            case TUESDAY:
                return "X2 PK Points";
            case WEDNESDAY:
                return "X2 Slayer Points";
            case THURSDAY:
                return "X2 PC Points";
            case FRIDAY:
            case SATURDAY:
            case SUNDAY:
                return "X2 Exp.";
        }
        return "X2 Exp.";
    }

    public void init() {
        Preconditions.checkState(!serviceLoader.isShutdown(), "The bootstrap has been bound already!");
        executeServiceLoad();
        serviceLoader.shutdown();
    }

    public void finish() throws IOException, InterruptedException {
        if (!serviceLoader.awaitTermination(15, TimeUnit.MINUTES))
            throw new IllegalStateException("The background service load took too long!");
        ExecutorService networkExecutor = Executors.newCachedThreadPool();
        ServerBootstrap serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(networkExecutor, networkExecutor));
        serverBootstrap.setPipelineFactory(new PipelineFactory(new HashedWheelTimer()));
        serverBootstrap.bind(new InetSocketAddress(port));
        executor.scheduleAtFixedRate(engine, 0, GameSettings.ENGINE_PROCESSING_CYCLE_RATE, TimeUnit.MILLISECONDS);
        TaskManager.submit(new ServerTimeUpdateTask());
    }

    private void executeServiceLoad() {
        if (GameSettings.MYSQL_ENABLED) {
            serviceLoader.execute(() -> MySQLController.init());
        }

        serviceLoader.execute(() -> ConnectionHandler.init());
        serviceLoader.execute(() -> PlayerPunishment.init());
        serviceLoader.execute(() -> RegionClipping.init());
        serviceLoader.execute(() -> CustomObjects.init());
        serviceLoader.execute(() -> ItemDefinition.init());
        serviceLoader.execute(() -> Scoreboards.init());
        serviceLoader.execute(() -> WellOfGoodwill.init());
        serviceLoader.execute(() -> ClanChatManager.init());
        serviceLoader.execute(() -> CombatPoisonData.init());
        serviceLoader.execute(() -> CombatStrategies.init());
        serviceLoader.execute(() -> NpcDefinition.parseNpcs().load());
        serviceLoader.execute(() -> NPCDrops.parseDrops().load());
        serviceLoader.execute(() -> WeaponInterfaces.parseInterfaces().load());
        serviceLoader.execute(() -> ShopManager.parseShops().load());
        serviceLoader.execute(() -> DialogueManager.parseDialogues().load());
        serviceLoader.execute(() -> NPC.init());
        serviceLoader.execute(() -> ProfileViewing.init());
        serviceLoader.execute(() -> PlayerOwnedShopManager.loadShops());
        serviceLoader.execute(() -> MonsterDrops.initialize());
        serviceLoader.execute(() -> ItemEffect.loadEffects());
        serviceLoader.execute(() -> TeleportRepository.loadData());
        serviceLoader.execute(() -> WildyWyrmEvent.initialize());

    }

    public GameEngine getEngine() {
        return engine;
    }
}