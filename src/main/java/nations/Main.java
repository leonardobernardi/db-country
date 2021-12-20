package nations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	
	private final static String DB_URL = "jdbc:mysql://localhost:3306/nations";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "rootpassword";

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.print("Search: ");
		String searchScanner = scan.nextLine();
		String search = "%" + searchScanner + "%";
		try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
			String query1 = "select c.name, c.country_id, r.name, c2.name from countries c join regions r on r.region_id = c.region_id join continents c2 on c2.continent_id = r.continent_id where c.name like ? order by c.name;";
			try(PreparedStatement psNations = con.prepareStatement(query1)){
				psNations.setString(1, search);
				try(ResultSet rsNations = psNations.executeQuery()){
					if(!rsNations.next()) {
						System.out.println("Nessun risultato");
					} else {
						String space = "     ";
						System.out.print(format("ID"));
						System.out.print(format("COUNTRY"));
						System.out.print(format("REGION"));
						System.out.println(format("CONTINENT"));
						do {
							System.out.print(format(rsNations.getInt(2)));
							System.out.print(format(rsNations.getString(1)));
							System.out.print(format(rsNations.getString(3)));
							System.out.println(format(rsNations.getString(4)));
						}while(rsNations.next());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scan.close();
		
	}
	
	public static String format(String s) {
		return String.format("%1$"+25+ "s", s);
	}
	public static String format(int i) {
		return String.format("%1$"+25+ "s", i);
	}
	

}
