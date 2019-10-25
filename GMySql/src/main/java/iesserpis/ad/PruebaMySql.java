package iesserpis.ad;

import java.sql.Statement;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PruebaMySql {
	
	private static Connection connection;
	
	public static void main(String[] args) throws SQLException {
		
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/dbprueba?serverTimezone=UTC", "root", "sistemas");
		insert();
		showAll();
	
		connection.close();
	}
	
	private static void showAll() throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from categoria order by id");
		
		while(resultSet.next()) {
			System.out.printf("id=%s nombre=%s %n", resultSet.getLong("id"), resultSet.getString("nombre"));
		}
		
		statement.close();
	}
	
	private static void insert() throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("insert into categoria(nombre) values (?)");
		preparedStatement.setObject(1, "cat " + Calendar.getInstance().getTime());
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
}
