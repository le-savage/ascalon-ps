/*
package mysql.impl.FoxSystems;


import java.sql.*;

public class Database {

    private Connection conn;
    private Statement stmt;

    private String host;
    private String user;
    private String pass;
    private String database;

    public Database(String host, String user, String pass, String database) {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.database = database;
    }

    public Connection getConnection() {
        return conn;
    }

    public Statement getStatement() {
        return stmt;
    }

    public boolean init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database, user, pass);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Failing connecting to database!");
            return false;
        }
    }

    public boolean initBatch() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?rewriteBatchedStatements=true", user, pass);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Failing connecting to database!");
            return false;
        }
    }

    public int executeUpdate(String query) {
        try {
            this.stmt = this.conn.createStatement();
            return stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
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

    public PreparedStatement prepare(String query) throws SQLException {
        return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    public void destroyAll() {
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


}

*/
