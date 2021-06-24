/*
package mysql.impl.FoxSystems;

import com.janus.world.entity.impl.player.Player;

import java.sql.*;

*/
/**
 * Using this class:
 * To call this class, it's best to make a new thread. You can do it below like so:
 * new Thread(new Donation(player)).start();
 *//*

public class FoxDonating implements Runnable {

    public static final String HOST = "199.192.31.129"; // website ip address
    public static final String USER = "janugswd_storedb";
    public static final String PASS = "bsn90oQpGFUS8u1ni";
    public static final String DATABASE = "janugswd_storedb";

    private Player player;
    private Connection conn;
    private Statement stmt;

    */
/**
     * The constructor
     *
     * @param player
     *//*

    public FoxDonating(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            if (!connect(HOST, DATABASE, USER, PASS)) {
                return;
            }

            String name = player.getUsername();
            ResultSet rs = executeQuery("SELECT * FROM payments WHERE player_name='" + name + "' AND status='Complete' AND claimed=0");

            while (rs.next()) {
                int item_number = rs.getInt("item_number");
                //double paid = rs.getDouble("amount");
                int quantity = rs.getInt("quantity");

                switch (item_number) {// add products according to their ID in the ACP

                    case 1: // example
                        player.getInventory().add(10025, quantity);
                        break;
                    case 2:
                        player.getInventory().add(15356, quantity);
                        break;
                    case 3:
                        player.getInventory().add(15355, quantity);
                        break;
                    case 4:
                        player.getInventory().add(15359, quantity);
                        break;
                    case 5:
                        player.getInventory().add(15358, quantity);
                        break;
                    case 6:
                        player.getInventory().add(10934, quantity);
                        break;

                }

                rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
                rs.updateRow();
            }

            destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
/**
     * @param host     the host ip address or url
     * @param database the name of the database
     * @param user     the user attached to the database
     * @param pass     the users password
     * @return true if connected
     *//*

    public boolean connect(String host, String database, String user, String pass) {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, user, pass);
            return true;
        } catch (SQLException e) {
            System.out.println("Failing connecting to database!");
            return false;
        }
    }

    */
/**
     * Disconnects from the MySQL server and destroy the connection
     * and statement instances
     *//*

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

    */
/**
     * Executes an update query on the database
     *
     * @param query
     * @see {@link Statement#executeUpdate}
     *//*

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

    */
/**
     * Executres a query on the database
     *
     * @param query
     * @return the results, never null
     * @see {@link Statement#executeQuery(String)}
     *//*

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
*/
