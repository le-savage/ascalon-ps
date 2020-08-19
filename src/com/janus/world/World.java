package com.janus.world;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.janus.GameSettings;
import com.janus.engine.task.Task;
import com.janus.engine.task.TaskManager;
import com.janus.model.PlayerRights;
import com.janus.util.Misc;
import com.janus.world.content.*;
import com.janus.world.content.discord.DiscordMessenger;
import com.janus.world.content.minigames.impl.FreeForAll;
import com.janus.world.content.minigames.impl.PestControl;
import com.janus.world.entity.Entity;
import com.janus.world.entity.EntityHandler;
import com.janus.world.entity.impl.CharacterList;
import com.janus.world.entity.impl.npc.NPC;
import com.janus.world.entity.impl.player.Player;
import com.janus.world.entity.impl.player.PlayerHandler;
import com.janus.world.entity.updating.NpcUpdateSequence;
import com.janus.world.entity.updating.PlayerUpdateSequence;
import com.janus.world.entity.updating.UpdateSequence;

import java.util.Iterator;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @author Gabriel Hannason
 * Thanks to lare96 for help with parallel updating system
 */
public class World {

    /**
     * All of the registered players.
     */
    private static CharacterList<Player> players = new CharacterList<>(2000);

    /**
     * All of the registered NPCs.
     */
    private static CharacterList<NPC> npcs = new CharacterList<>(2027);

    /**
     * Used to block the game thread until updating has completed.
     */
    private static Phaser synchronizer = new Phaser(1);

    /**
     * A thread pool that will update players in parallel.
     */
    private static ExecutorService updateExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactoryBuilder().setNameFormat("UpdateThread").setPriority(Thread.MAX_PRIORITY).build());

    /**
     * The queue of {@link Player}s waiting to be logged in.
     **/
    private static Queue<Player> logins = new ConcurrentLinkedQueue<>();

    /**
     * The queue of {@link Player}s waiting to be logged out.
     **/
    private static Queue<Player> logouts = new ConcurrentLinkedQueue<>();

    /**
     * The queue of {@link Player}s waiting to be given their vote reward.
     **/
    private static Queue<Player> voteRewards = new ConcurrentLinkedQueue<>();

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static void register(Entity entity) {
        EntityHandler.register(entity);
    }

    public static void deregister(Entity entity) {
        EntityHandler.deregister(entity);
    }

    public static Player getPlayerByName(String username) {
        Optional<Player> op = players.search(p -> p != null && p.getUsername().equals(Misc.formatText(username)));
        return op.isPresent() ? op.get() : null;
    }

    public static Player getPlayerByIndex(int username) {
        Optional<Player> op = players.search(p -> p != null && p.getIndex() == username);
        return op.isPresent() ? op.get() : null;
    }

    public static Player getPlayerByLong(long encodedName) {
        Optional<Player> op = players.search(p -> p != null && p.getLongUsername().equals(encodedName));
        return op.isPresent() ? op.get() : null;
    }

	/*public static void sendMessage(String message) {
		players.forEach(p -> p.getPacketSender().sendMessage(message));
	}*/


    public static void sendMessage(String message) {
        players.forEach(p -> p.getPacketSender().sendMessage(message));
        if (message.contains("[Yell]")) {
            DiscordMessenger.sendYellMessage(message);
        } else if (message.contains("[New Player]")) {
            DiscordMessenger.sendNewPlayer(message);
        } else {
            DiscordMessenger.sendInGameMessage(message);
        }
    }

    public static void sendFilteredMessage(String message) {
        /*players.stream().filter(p -> p != null ).forEach(p -> p.getPacketSender().sendMessage(message));*/
        players.forEach(p -> p.getPacketSender().sendMessage(message));

    }

    public static void sendStaffMessage(String message) {
        players.stream().filter(p -> p != null && (p.getRights() == PlayerRights.OWNER || p.getRights() == PlayerRights.DEVELOPER || p.getRights() == PlayerRights.ADMINISTRATOR || p.getRights() == PlayerRights.MODERATOR || p.getRights() == PlayerRights.SUPPORT || p.getRights() == PlayerRights.COMMUNITYMANAGER)).forEach(p -> p.getPacketSender().sendMessage(message));
        DiscordMessenger.sendStaffMessage(message);
    }

    public static void updateServerTime() {
        //players.forEach(p-> p.getPacketSender().sendString(39172, PlayerPanel.LINE_START + "@or1@Server Time: @yel@"+Misc.getCurrentServerTime()));
    }

    public static void updatePlayersOnline() {
        //players.forEach(p-> p.getPacketSender().sendString(39173, PlayerPanel.LINE_START + "@or1@Players Online: @yel@"+players.size()));
        players.forEach(p -> p.getPacketSender().sendString(26608, "@or2@Players Online: @gre@" + (27 + players.size()) + ""));
        players.forEach(p -> p.getPacketSender().sendString(57003, "Players:  @gre@" + (int) (World.getPlayers().size()) + ""));
        updateStaffList();
    }

    public static void updateStaffList() {
        TaskManager.submit(new Task(false) {
            @Override
            protected void execute() {
                players.forEach(p -> StaffList.updateInterface(p));
                stop();
            }
        });
    }

    public static void savePlayers() {
        players.forEach(p -> p.save());
    }

    public static CharacterList<Player> getPlayers() {
        return players;
    }

    public static CharacterList<NPC> getNpcs() {
        return npcs;
    }

    public static void sequence() {

        // Handle queued logins.
        for (int amount = 0; amount < GameSettings.LOGIN_THRESHOLD; amount++) {
            Player player = logins.poll();
            if (player == null)
                break;
            PlayerHandler.handleLogin(player);
        }

        // Handle queued logouts.
        int amount = 0;
        Iterator<Player> $it = logouts.iterator();
        while ($it.hasNext()) {
            Player player = $it.next();
            if (player == null || amount >= GameSettings.LOGOUT_THRESHOLD)
                break;
            if (PlayerHandler.handleLogout(player)) {
                $it.remove();
                amount++;
            }
        }

        // FightPit.sequence();
        //Cows.sequence();
        Reminders.sequence();
        //Cows.spawnMainNPCs();
        PestControl.sequence();
        ShootingStar.sequence();
        EvilTrees.sequence();
        TriviaBot.sequence();
        //ShopRestocking.sequence();
        FreeForAll.sequence();
        // First we construct the update sequences.
        UpdateSequence<Player> playerUpdate = new PlayerUpdateSequence(synchronizer, updateExecutor);
        UpdateSequence<NPC> npcUpdate = new NpcUpdateSequence();
        // Then we execute pre-updating code.
        players.forEach(playerUpdate::executePreUpdate);
        npcs.forEach(npcUpdate::executePreUpdate);
        // Then we execute parallelized updating code.
        synchronizer.bulkRegister(players.size());
        players.forEach(playerUpdate::executeUpdate);
        synchronizer.arriveAndAwaitAdvance();
        // Then we execute post-updating code.
        players.forEach(playerUpdate::executePostUpdate);
        npcs.forEach(npcUpdate::executePostUpdate);
    }

    public static Queue<Player> getLoginQueue() {
        return logins;
    }

    public static Queue<Player> getLogoutQueue() {
        return logouts;
    }

    public static Queue<Player> getVoteRewardingQueue() {
        return voteRewards;
    }
}
