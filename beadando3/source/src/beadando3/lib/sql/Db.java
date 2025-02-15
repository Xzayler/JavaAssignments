package beadando3.lib.sql;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to perform database actions
 */
public class Db {

    private Connection connection;

    /**
     * Creates a connection and the table if it doesn't exist
     */
    public Db() {
        String url = "jdbc:sqlite:scores.db";
        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                System.out.println("Database created successfully!");
                createTable(connection);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Inserts a row in the score table
     * @param name The name of the player
     * @param score The achieved score
     */
    public void setHighscore(String name, int score) {
        String sql = "INSERT INTO scores(name, score) VALUES(?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, score);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Queries the names and scores of the best 10 players
     * @return The results of the query
     */
    public ResultSet viewScores() {
        ResultSet res = null;
        String q = "SELECT name,score FROM scores ORDER BY score DESC LIMIT 10";
        try {
            PreparedStatement stmt = connection.prepareStatement(q);
            res = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    private static void createTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS scores ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "score INTEGER NOT NULL);";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created or already exists.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
