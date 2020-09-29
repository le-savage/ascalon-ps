package com.janus.world.entity.impl.player;

import com.google.common.collect.Multiset.Entry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.janus.GameServer;
import com.janus.util.Misc;
import com.janus.world.content.ClueScrolls;
import com.janus.world.content.discord.DiscordMessenger;
import com.janus.world.content.skill.impl.construction.ConstructionSave;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;


public class PlayerSaving {

    public static void save(Player player) {
        if (player.newPlayer())
            return;
        // Create the path and file objects.
        Path path = Paths.get("./data/saves/characters/", player.getUsername() + ".json");
        File file = path.toFile();
        file.getParentFile().setWritable(true);

        // Attempt to make the player save directory if it doesn't
        // exist.
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
            } catch (SecurityException e) {
                System.out.println("Unable to create directory for player data!");
            }
        }
        try (FileWriter writer = new FileWriter(file)) {

            Gson builder = new GsonBuilder().setPrettyPrinting().create();
            JsonObject object = new JsonObject();
            object.addProperty("total-play-time-ms", player.getTotalPlayTime());
            object.addProperty("username", player.getUsername().trim());
            object.addProperty("password", player.getPassword().trim());
            object.addProperty("email", player.getEmailAddress() == null ? "null" : player.getEmailAddress().trim());
            object.addProperty("staff-rights", player.getRights().name());
            object.addProperty("game-mode", player.getGameMode().name());
            object.addProperty("difficulty", player.getDifficulty().toString());
            object.addProperty("boss-points", player.getBossPoints());
            object.addProperty("janus-points", player.getJanusPoints());
            object.addProperty("loyalty-title", player.getLoyaltyTitle().name());
            object.add("position", builder.toJsonTree(player.getPosition()));
            object.addProperty("online-status", player.getRelations().getStatus().name());
            object.addProperty("given-starter", player.didReceiveStarter());
            object.addProperty("has-played-new-barrows", player.hasPlayedNewBarrows());
            object.addProperty("has-used-boss-tier-tp", player.hasUsedBossTierTP());
            object.addProperty("should-give-boss-reward", player.shouldGiveBossReward());
            object.addProperty("kbd-tier", player.getKbdTier());
            object.addProperty("instances-kc", player.getInstanceKC());
            object.addProperty("barrows-kc", player.getBarrowsKC());
            object.addProperty("money-pouch", player.getMoneyInPouch());
            object.addProperty("donated", (long) player.getAmountDonated());

            object.addProperty("next-reward", player.getDailyReward().getNextRewardTime());
            object.addProperty("reward-day", player.getDailyReward().getDay());
            object.addProperty("claimed-todays-reward", player.getClaimedTodays());

            object.addProperty("allow-snap", player.allowSnap());
            object.addProperty("allow-rps", player.allowRps());
            object.addProperty("show-tips", player.showTips());
            object.addProperty("show-trivia", player.showTrivia());
            object.addProperty("show-world-messages", player.showWorldMessages());
            object.addProperty("skill-task-ordinal", player.getSkillTaskOrdinal());
            object.addProperty("skill-task-points", player.getSkillTaskPoints());
            object.addProperty("task-difficulty", player.getTaskDifficulty());

            object.addProperty("minutes-bonus-exp", player.getMinutesBonusExp());
            object.addProperty("pickupValue", player.getPickupValue());
            object.addProperty("total-gained-exp", player.getSkillManager().getTotalGainedExp());
            object.addProperty("prestige-points", player.getPointsHandler().getPrestigePoints());
            object.addProperty("achievement-points", player.getPointsHandler().getAchievementPoints());
            object.addProperty("dung-tokens", player.getPointsHandler().getDungeoneeringTokens());
            object.addProperty("commendations", player.getPointsHandler().getCommendations());
            object.addProperty("loyalty-points", player.getPointsHandler().getLoyaltyPoints());
            object.addProperty("total-loyalty-points",
                    player.getAchievementAttributes().getTotalLoyaltyPointsEarned());
            object.addProperty("voting-points", player.getPointsHandler().getVotingPoints());
            object.addProperty("slayer-points", player.getPointsHandler().getSlayerPoints());


            object.addProperty("pk-points", player.getPointsHandler().getPkPoints());
            object.addProperty("donation-points", player.getPointsHandler().getDonationPoints());
            object.addProperty("afk-points", player.getPointsHandler().getAfkPoints());
            object.addProperty("trivia-points", player.getPointsHandler().getTriviaPoints());
            object.addProperty("cluescompleted", ClueScrolls.getCluesCompleted());

            object.addProperty("player-kills", player.getPlayerKillingAttributes().getPlayerKills());
            object.addProperty("player-killstreak",
                    player.getPlayerKillingAttributes().getPlayerKillStreak());
            object.addProperty("player-deaths", player.getPlayerKillingAttributes().getPlayerDeaths());
            object.addProperty("target-percentage",
                    player.getPlayerKillingAttributes().getTargetPercentage());
            object.addProperty("bh-rank", player.getAppearance().getBountyHunterSkull());
            object.addProperty("gender", player.getAppearance().getGender().name());
            object.addProperty("spell-book", player.getSpellbook().name());
            object.addProperty("shop-updated", player.isShopUpdated());
            object.addProperty("shop-earnings", player.getPlayerOwnedShopManager().getEarnings());
            object.addProperty("prayer-book", player.getPrayerbook().name());
            object.addProperty("running", player.isRunning());
            object.addProperty("run-energy", player.getRunEnergy());
            object.addProperty("music", player.musicActive());
            object.addProperty("sounds", player.soundsActive());
            object.addProperty("auto-retaliate", player.isAutoRetaliate());
            object.addProperty("xp-locked", player.experienceLocked());
            object.addProperty("veng-cast", player.hasVengeance());
            object.addProperty("last-veng", player.getLastVengeance().elapsed());
            object.addProperty("fight-type", player.getFightType().name());
            object.addProperty("sol-effect", player.getStaffOfLightEffect());
            object.addProperty("skull-timer", player.getSkullTimer());
            object.addProperty("accept-aid", player.isAcceptAid());
            object.addProperty("poison-damage", player.getPoisonDamage());
            object.addProperty("poison-immunity", player.getPoisonImmunity());
            object.addProperty("overload-timer", player.getOverloadPotionTimer());
            object.addProperty("fire-immunity", player.getFireImmunity());
            object.addProperty("fire-damage-mod", player.getFireDamageModifier());
            object.addProperty("prayer-renewal-timer", player.getPrayerRenewalPotionTimer());
            object.addProperty("teleblock-timer", player.getTeleblockTimer());
            object.addProperty("special-amount", player.getSpecialPercentage());
            object.addProperty("entered-gwd-room",
                    player.getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom());
            object.addProperty("gwd-altar-delay",
                    player.getMinigameAttributes().getGodwarsDungeonAttributes().getAltarDelay());
            object.add("gwd-killcount",
                    builder.toJsonTree(player.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount()));
            object.addProperty("effigy", player.getEffigy());
            object.addProperty("summon-npc", player.getSummoning().getFamiliar() != null
                    ? player.getSummoning().getFamiliar().getSummonNpc().getId() : -1);
            object.addProperty("summon-death", player.getSummoning().getFamiliar() != null
                    ? player.getSummoning().getFamiliar().getDeathTimer() : -1);
            object.addProperty("process-farming", player.shouldProcessFarming());
            object.addProperty("clanchat", player.getClanChatName() == null ? "null" : player.getClanChatName().trim());
            object.addProperty("autocast", player.isAutocast());
            object.addProperty("autocast-spell",
                    player.getAutocastSpell() != null ? player.getAutocastSpell().spellId() : -1);
            object.addProperty("dfs-charges", player.getDfsCharges());
            object.add("kills", builder.toJsonTree(player.getKillsTracker().toArray()));
            object.add("drops", builder.toJsonTree(player.getDropLog().toArray()));
            object.addProperty("coins-gambled", player.getAchievementAttributes().getCoinsGambled());
            object.addProperty("slayer-master", player.getSlayer().getSlayerMaster().name());
            object.addProperty("slayer-task", player.getSlayer().getSlayerTask().name());
            object.addProperty("prev-slayer-task", player.getSlayer().getLastTask().name());
            object.addProperty("task-amount", player.getSlayer().getAmountToSlay());
            object.addProperty("task-streak", player.getSlayer().getTaskStreak());
            object.addProperty("duo-partner",
                    player.getSlayer().getDuoPartner() == null ? "null" : player.getSlayer().getDuoPartner());
            object.addProperty("double-slay-xp", player.getSlayer().doubleSlayerXP);
            object.addProperty("recoil-deg", player.getRecoilCharges());
            object.add("brawler-deg", builder.toJsonTree(player.getBrawlerChargers()));
            object.add("killed-players", builder.toJsonTree(player.getPlayerKillingAttributes().getKilledPlayers()));
            object.add("killed-gods", builder.toJsonTree(player.getAchievementAttributes().getGodsKilled()));
            object.add("barrows-brother",
                    builder.toJsonTree(player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()));
            object.addProperty("random-coffin",
                    player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin());
            object.addProperty("barrows-killcount",
                    player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount());
            object.add("nomad",
                    builder.toJsonTree(player.getMinigameAttributes().getNomadAttributes().getQuestParts()));
            object.add("recipe-for-disaster", builder
                    .toJsonTree(player.getMinigameAttributes().getRecipeForDisasterAttributes().getQuestParts()));
            object.addProperty("recipe-for-disaster-wave",
                    player.getMinigameAttributes().getRecipeForDisasterAttributes().getWavesCompleted());
            object.add("dung-items-bound",
                    builder.toJsonTree(player.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()));
            object.addProperty("rune-ess", player.getStoredRuneEssence());
            object.addProperty("pure-ess", player.getStoredPureEssence());
            object.addProperty("has-bank-pin", player.getBankPinAttributes().hasBankPin());
            object.addProperty("last-pin-attempt", player.getBankPinAttributes().getLastAttempt());
            object.addProperty("invalid-pin-attempts", player.getBankPinAttributes().getInvalidAttempts());
            object.add("bank-pin", builder.toJsonTree(player.getBankPinAttributes().getBankPin()));
            object.add("appearance", builder.toJsonTree(player.getAppearance().getLook()));
            object.add("agility-obj", builder.toJsonTree(player.getCrossedObstacles()));
            object.add("skills", builder.toJsonTree(player.getSkillManager().getSkills()));
            object.add("inventory", builder.toJsonTree(player.getInventory().getItems()));
            object.add("equipment", builder.toJsonTree(player.getEquipment().getItems()));
            object.add("bank-0", builder.toJsonTree(player.getBank(0).getValidItems()));
            object.add("bank-1", builder.toJsonTree(player.getBank(1).getValidItems()));
            object.add("bank-2", builder.toJsonTree(player.getBank(2).getValidItems()));
            object.add("bank-3", builder.toJsonTree(player.getBank(3).getValidItems()));
            object.add("bank-4", builder.toJsonTree(player.getBank(4).getValidItems()));
            object.add("bank-5", builder.toJsonTree(player.getBank(5).getValidItems()));
            object.add("bank-6", builder.toJsonTree(player.getBank(6).getValidItems()));
            object.add("bank-7", builder.toJsonTree(player.getBank(7).getValidItems()));
            object.add("bank-8", builder.toJsonTree(player.getBank(8).getValidItems()));

            object.add("ge-slots", builder.toJsonTree(player.getGrandExchangeSlots()));

            if (player.getSummoning().getBeastOfBurden() != null) {
                object.add("store", builder.toJsonTree(player.getSummoning().getBeastOfBurden().getValidItems()));
            }
            object.add("charm-imp", builder.toJsonTree(player.getSummoning().getCharmImpConfigs()));

            for (Entry<Integer> dartItem : player.getBlowpipeLoading().getContents().entrySet()) {
                object.addProperty("blowpipe-charge-item", dartItem.getElement());
                object.addProperty("blowpipe-charge-amount",
                        player.getBlowpipeLoading().getContents().count(dartItem.getElement()));
            }

            object.add("friends", builder.toJsonTree(player.getRelations().getFriendList().toArray()));
            object.add("ignores", builder.toJsonTree(player.getRelations().getIgnoreList().toArray()));
            object.add("loyalty-titles", builder.toJsonTree(player.getUnlockedLoyaltyTitles()));
            object.add("achievements-completion",
                    builder.toJsonTree(player.getAchievementAttributes().getCompletion()));
            object.add("achievements-progress", builder.toJsonTree(player.getAchievementAttributes().getProgress()));
            object.add("max-cape-colors", builder.toJsonTree(player.getMaxCapeColors()));
            object.addProperty("donorMessages", player.getDonorMessages());
            object.addProperty("notificationPreference", player.getNotificationPreference());
            object.addProperty("player-title", player.getTitle());
            object.add("collection-data", builder.toJsonTree(player.getCollectionLogData()));
            writer.write(builder.toJson(object));
        } catch (Exception e) {
            // An error happened while saving.
            DiscordMessenger.sendErrorLog("An error has occured while saving a character file!");
            GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a character file!", e);
        }
        try {

            File file2 = new File("./data/saves/construction/" + player.getUsername() + ".obj");

            if (!file2.exists()) {
                file2.createNewFile();
            }

            FileOutputStream fileOut = new FileOutputStream(file2);
            ConstructionSave save = new ConstructionSave();
            save.supply(player);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(save);
            out.close();
            fileOut.close();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static boolean playerExists(String p) {
        p = Misc.formatPlayerName(p.toLowerCase());
        return new File("./data/saves/characters/" + p + ".json").exists();
    }
}
