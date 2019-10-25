package iesserpis.ad;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaMySql {
	
	public static void main(String[] args) throws SQLException {
		
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/dbprueba?serverTimezone=UTC", "root", "sistemas");
		
		Statement statement = connection.createStatement();
		statement.executeQuery("select * from categoria order by id");
		
	}

}
