package mysql;

import com.janus.world.entity.impl.player.Player;

import java.sql.*;

public class BossKillTracker {


    public static class CountKills implements Runnable {

        public static final String HOST = "199.192.31.129";
        public static final String USER = "bosscounter";
        public static final String PASS = "IN28nndn2AqpLamq3491";
        public static final String DATABASE = "bosscounter";

        private Player player;
        private Connection conn;
        private Statement stmt;

        public CountKills(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            try {
                if (!connect(HOST, DATABASE, USER, PASS)) {
                    return;
                }


                String username = player.getUsername();
                String difficulty = player.getDifficulty().toString().toUpperCase();
                ResultSet rs = executeQuery("SELECT * FROM counter WHERE player='" + username + "'");


                if (rs.next()) { //TODO - FIND OUT HOW TO AUTO ADD NEW USERS
                        username = rs.getString("player");
                }



                while (rs.next()) {
                    rs.updateString("player", username);
                    rs.updateString("difficulty", difficulty);
                    rs.updateInt("kills", rs.getInt("kills")+1); // Increment Kills
                    rs.updateRow();
                }

                destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void newPlayer() {
            try {
                if (!connect(HOST, DATABASE, USER, PASS)) {
                    return;
                }


                String username = player.getUsername();
                String difficulty = player.getDifficulty().toString().toUpperCase();


                        Statement newPlayer = conn.createStatement();
                        newPlayer.executeUpdate("INSERT INTO counter (player, difficulty, kills) "
                                +"VALUES ('"+username+"', '"+difficulty+"', 0)");


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
