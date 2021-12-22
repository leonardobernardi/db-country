package nations;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;


public class Main {
	
	private final static String DB_URL = "jdbc:mysql://localhost:3306/nations";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "rootpassword";

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
			Country Italy = selectCountryById(107, scan, con);
			if(Italy.getNationalDay() == null) {
				Italy.setNationalDay(LocalDate.of(1861, 3, 17));	
			}
			updateCountry(Italy, con);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scan.close();
		
	}
	
	public static Country selectCountryById(int id, Scanner scan, Connection con) throws SQLException {
		String query = "select * from countries c where country_id = ?;";
		try(PreparedStatement psCountry = con.prepareStatement(query)){
			psCountry.setInt(1, id);
			try(ResultSet rsCountry = psCountry.executeQuery()){
				if(!rsCountry.next()) {
					return null;
				}else {
					LocalDate nationalDay;
					if(rsCountry.getString(4) == null) {
						nationalDay = null;
					}else {
						nationalDay = LocalDate.parse(rsCountry.getString(4));
					}
					Country country = new Country(rsCountry.getInt(1), rsCountry.getString(2), rsCountry.getBigDecimal(3), nationalDay, rsCountry.getString(5), rsCountry.getString(6), rsCountry.getInt(7));
					return country;
				}
			}
		}
	}
	
	public static void updateCountry(Country c, Connection con) throws SQLException {
		String query = "update countries set name = ? where country_id = ?";
		try(PreparedStatement psUpdate = con.prepareStatement(query)){
			psUpdate.setString(1, c.getName());
			psUpdate.setInt(2, c.getCountryId());
			psUpdate.execute();

		}
		query = "update countries set area = ? where country_id = ?";
		try(PreparedStatement psUpdate = con.prepareStatement(query)){
			psUpdate.setBigDecimal(1, c.getArea());
			psUpdate.setInt(2, c.getCountryId());
			psUpdate.execute();
		}
		query = "update countries set national_day = ? where country_id = ?";
		try(PreparedStatement psUpdate = con.prepareStatement(query)){
			psUpdate.setDate(1, Date.valueOf(c.getNationalDay()));
			psUpdate.setInt(2, c.getCountryId());
			psUpdate.execute();
		}
		query = "update countries set country_code2 = ? where country_id = ?";
		try(PreparedStatement psUpdate = con.prepareStatement(query)){
			psUpdate.setString(1, c.getCountryCode2());
			psUpdate.setInt(2, c.getCountryId());
			psUpdate.execute();
		}
		query = "update countries set country_code3 = ? where country_id = ?";
		try(PreparedStatement psUpdate = con.prepareStatement(query)){
			psUpdate.setString(1, c.getCountryCode3());
			psUpdate.setInt(2, c.getCountryId());
			psUpdate.execute();
		}
		
	}


}
