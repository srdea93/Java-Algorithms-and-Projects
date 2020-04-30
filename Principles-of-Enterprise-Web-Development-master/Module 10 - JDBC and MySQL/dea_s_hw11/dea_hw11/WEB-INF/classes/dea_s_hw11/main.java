package dea_s_hw11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main {
	// Change this to web6.jhuep.com before submission
			private final static String url = "jdbc:mysql://web6.jhuep.com:3306/";
			// The qualified class name for the mysql driver
			private final static String driver = "com.mysql.jdbc.Driver";
			// The user we wish to connect as. for internal testing we can make a new account on our database
			private final static String user = "johncolter";
			// The password associated with the particular account we are connecting with
			private final static String password = "LetMeIn";
			// Name of the schema we are connecting to
			private final static String db = "class";
			
			// Do not need to use this with web6 connections
//			private final static String options = "?useSSL=false";
			
			// Appears to be some strange issue with this version of JDBC driver where have to specify timezone
//			private final static String options = "?serverTimezone=UTC";
			
			private final static String options = "";

	public static void main(String[] args) {
		int year = 2006;
		int month = 5;
		int day = 1;
		try (Connection conn = DriverManager.getConnection(url + db + options, user, password);
				Statement statement = conn.createStatement()) {
			String query = "SELECT reservation.First, reservation.Last, StartDay, NumberOfDays, locations.location, guides.First, guides.Last \n" + 
					"FROM reservation\n" + 
					"	LEFT JOIN locations on reservation.location = idlocations\n" + 
					"   LEFT JOIN guides on guide = idguides\n" + 
					"WHERE StartDay >= " + "'" + year + "-" + month + "-" + day + "'";
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				System.out.println(rs.getString("reservation.First") +" " +rs.getString("reservation.Last"));
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
