package bombermanGui;

//Imports
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ConnectToDatabase {

	// Objekte initialisieren
	static String createTable = null;
	static String sql = null;
	static PreparedStatement pstm = null;

	// Methode zum Aufbau eines Connection zur SQL-DB fuer
	// Remote-Speichern/-Laden
	public static Connection buildConnection() throws SQLException {
		final String url = "jdbc:mysql://localhost/bombermandatabase";
		final String user = "root";
		final String pass = "";

		Connection con = (Connection) DriverManager.getConnection(url, user, pass);
		con.setAutoCommit(false);
		;
		return con;
	}

	// Methode zum Speichern der Player-Positionen in der Datenbank
	public static void writeToDatabase(int playerX, int playerY, String playerID) {

		try {
			Connection con = buildConnection();

			// Neue Tabelle anlegen
			createTable = "CREATE TABLE IF NOT EXISTS bombermandatabase.PlayerPosition (" + "PlayerX INT, "
					+ "PlayerY INT, " + "PlayerID VARCHAR (7), " + "PRIMARY KEY (PlayerID));";

			pstm = (PreparedStatement) con.prepareStatement(createTable);
			pstm.executeUpdate();

			// Player-Positionen speichern
			sql = ("UPDATE `bombermandatabase`.`playerposition` SET `PlayerX` = '" + playerX + "', `PlayerY` = '"
					+ playerY + "' WHERE `playerposition`.`PlayerID` = '" + playerID + "'");
			pstm = (PreparedStatement) con.prepareStatement(sql);
			pstm.executeUpdate();

			con.commit();
			pstm.close();
			con.close();

			System.out.println("Database updated successfully");

		} catch (SQLException ex) {
			System.out.println(ex);
		} catch (NullPointerException npe) {
			System.out.println(npe);
		}
	}

	// Methode zum Auslesen der Player-Positionen aus der Datenbank
	public static ArrayList<Integer> getPositionFromDatabase(String playerID) {
		ArrayList<Integer> position = new ArrayList<Integer>();

		try {
			Connection con = buildConnection();

			sql = ("SELECT  `PlayerX` , `PlayerY`FROM `bombermandatabase`.`playerposition` WHERE `playerposition`.`PlayerID` = '"
					+ playerID + "'");
			pstm = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				position.add(rs.getInt("PlayerX"));
				position.add(rs.getInt("PlayerY"));
			}

			con.commit();
			pstm.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return position;
	}

}
