package mysql.impl.FoxSystems;


import com.janus.model.Skill;
import com.janus.world.World;
import com.janus.world.entity.impl.player.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

public class Hiscores implements Runnable {

    /**
     * Just for testing the code.
     *
     * @param args
     */
    public static void main(String[] args) {
        Player player = World.getPlayerByName("Flub");
        new Thread(new Hiscores(player)).start();
    }

    public static final String HOST = "199.192.31.129"; // website ip address
    public static final String USER = "janugswd_scores";
    public static final String PASS = "52tlfsX3eMknPlb";
    public static final String DATABASE = "janugswd_scores";

    /**
     * Skills array, should be in order of their id.
     * If you have divination/invention just add it after dungeoneering
     */
    public static final String[] SKILLS = {
            "Attack", "Defence", "Strength", "Constitution", "Ranged", "Prayer", "Magic", "Cooking",
            "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining",
            "Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting", "Construction",
            "Hunter", "Summoning", "Dungeoneering"
    };

    private Player player;

    public Hiscores(Player player) {
        this.player = player;
    }

    /**
     * Runs on a new thread. to run this, use this code anywhere:
     * new Thread(new Highscores(player)).start();
     */
    @Override
    public void run() {
        try {
            Database db = new Database(HOST, USER, PASS, DATABASE);

            if (!db.init()) {
                return;
            }

            String username = player.getUsername().toLowerCase();

            long overall_xp = player.getSkillManager().getTotalExp();
            long stored_xp = 0;
            int generatedKey = -1;

            Timestamp updatedAt = new Timestamp(Calendar.getInstance().getTimeInMillis());

            ResultSet rs = db.executeQuery(
                    "SELECT * FROM hs_stats WHERE username='" + username + "' ORDER BY overall_xp DESC LIMIT 1");

            if (rs.next()) {
                stored_xp = rs.getLong("overall_xp");
                generatedKey = rs.getInt("id");
            }

            HashMap<String, String> map = new HashMap<>();

            map.put("username", username);
            map.put("rights", "" + player.getRights().ordinal());

            // only update exp if there's a change in overall exp.
            if (stored_xp != overall_xp) {
                map.put("mode", "regular"); // fetch game mode
                map.put("exp_rate", "normal"); // fetch exp rate

                map.put("total_level", "" + player.getSkillManager().getTotalLevel());
                map.put("cmb_level", "" + player.getSkillManager().getCombatLevel());

                map.put("last_update", updatedAt.toString());
            }

            // prepared statement so we can get generated keys
            PreparedStatement ps = db.prepare(this.buildQuery("hs_users", true, map));
            ps.execute();

            if (generatedKey == -1) {
                // get generated keys
                ResultSet keyset = ps.getGeneratedKeys();

                // grab last inserted uid column
                if (keyset.next()) {
                    generatedKey = keyset.getInt(1);
                }
            }

            // reset map, and build map and query for stats table.
            map = new HashMap<>();

            //map.put("id", ""+generatedKey);
            map.put("username", username);
            map.put("last_update", updatedAt.toString());
            map.put("overall_xp", "" + overall_xp);

            // string array of skill names.
            for (int i = 0; i < SKILLS.length; i++) {

                // get exp for given skill
                int skill_xp = player.getSkillManager().getExperience(Skill.forId(i));
                map.put(SKILLS[i].toLowerCase() + "_xp", "" + skill_xp);
            }

            db.executeUpdate(this.buildQuery("hs_stats", false, map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds a query from a given HashMap. shouldn't be a need to alter this.
     *
     * @param map
     * @return
     */
    private String buildQuery(String table, boolean updateDuplicate, HashMap<String, String> map) {
        String query = "INSERT INTO " + table + " (";

        Object[] keys = map.keySet().toArray();
        Object[] values = map.values().toArray();

        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            query += "" + key + (i == keys.length - 1 ? ") " : ", ");
        }

        query += "VALUES(";

        for (int i = 0; i < keys.length; i++) {
            Object value = values[i];
            query += "'" + value + "'" + (i == keys.length - 1 ? ") " : ", ");
        }

        if (updateDuplicate) {
            query += "ON DUPLICATE KEY UPDATE ";

            for (int i = 0; i < keys.length; i++) {
                Object key = keys[i];
                Object value = values[i];

                query += "" + key + " = '" + value + "'" + (i == keys.length - 1 ? "" : ", ");
            }
        }
        return query;
    }

}

