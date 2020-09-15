package mysql.impl.FoxSystems;

import com.janus.GameLoader;
import com.janus.world.World;
import com.janus.world.content.Achievements;
import com.janus.world.content.discord.DiscordMessenger;
import com.janus.world.entity.impl.player.Player;

import java.sql.*;

public class FoxVoting {


    public static class FoxVote implements Runnable {

        public static final String HOST = "199.192.31.129";
        public static final String USER = "janugswd_votingd";
        public static final String PASS = "tVPIoSrc8jVxkj4W0";
        public static final String DATABASE = "janugswd_votingd";

        private Player player;
        private Connection conn;
        private Statement stmt;

        public FoxVote(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            try {
                if (!connect(HOST, DATABASE, USER, PASS)) {
                    return;
                }


                String name = player.getUsername();
                String message = (name + " has been awarded for voting! ::vote to get yours too!");
                World.sendMessage(message);
                System.out.println(name);
                ResultSet rs = executeQuery("SELECT * FROM votes WHERE username='" + name + "' AND claimed=0 AND voted_on != -1");

                while (rs.next()) {
                    String ipAddress = rs.getString("ip_address");
                    int siteId = rs.getInt("site_id");


                    // -- ADD CODE HERE TO GIVE TOKENS OR WHATEVER
                    if ((GameLoader.getDay() == GameLoader.MONDAY) && (player.getRights().isMember() || player.getRights().isStaff())) {
                        player.getInventory().add(19670, 2);
                    } else {
                        player.getInventory().add(19670, 1);
                    }

                    Achievements.doProgress(player, Achievements.AchievementData.VOTE_50_TIMES);

                    //DiscordMessenger.sendErrorLog("VOTES CLAIMED BY: "+name);

                    System.out.println("[Vote] Vote claimed by " + name + ". (sid: " + siteId + ", ip: " + ipAddress + ")");

                    rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
                    rs.updateRow();
                }

                destroy();
            } catch (Exception e) {
                //	DiscordMessenger.sendErrorLog("VOTING ERROR");
                e.printStackTrace();
            }
        }


        public boolean connect(String host, String database, String user, String pass) {
            try {
                this.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, user, pass);
                System.out.println("Connected !!!!!");
                return true;
            } catch (SQLException e) {
                DiscordMessenger.sendErrorLog("VOTING - FAILED TO CONNECT TO DB");
                System.out.println("Failing connecting to database!");
                return false;
            }
        }

        public void destroy() {
            try {
                conn.close();
                conn = null;
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public int executeUpdate(String query) {
            try {
                this.stmt = this.conn.createStatement(1005, 1008);
                int results = stmt.executeUpdate(query);
                return results;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return -1;
        }

        public ResultSet executeQuery(String query) {
            try {
                this.stmt = this.conn.createStatement(1005, 1008);
                ResultSet results = stmt.executeQuery(query);
                return results;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }

}
