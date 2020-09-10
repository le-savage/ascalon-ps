package mysql;

import com.janus.world.content.discord.DiscordMessenger;
import com.janus.world.entity.impl.player.Player;

import java.sql.*;

public class ResetBossKills {


    public static class ResetKills implements Runnable {

        public static final String HOST = "199.192.31.129";
        public static final String USER = "bosscounter";
        public static final String PASS = "IN28nndn2AqpLamq3491";
        public static final String DATABASE = "bosscounter";

        private Player player;
        private Connection conn;
        private Statement stmt;

        public ResetKills(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            try {
                if (!connect(HOST, DATABASE, USER, PASS)) {
                    return;
                }

                ResultSet rs = executeQuery("SELECT * FROM counter WHERE kills > 1");




                while (rs.next()) {

                    rs.updateInt("kills", 0); // Increment Kills
                    rs.updateRow();
                }

                destroy();
            } catch (Exception e) {
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
