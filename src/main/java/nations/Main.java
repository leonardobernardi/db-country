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
		boolean cycle = true;
		boolean repeat;
		
		try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
			do {
				System.out.print("Search: ");
				String searchScanner = scan.nextLine();
				String search = "%" + searchScanner + "%";
				String query1 = "select c.name, c.country_id, r.name, c2.name from countries c join regions r on r.region_id = c.region_id join continents c2 on c2.continent_id = r.continent_id where c.name like ? order by c.name;";
				try (PreparedStatement psNations = con.prepareStatement(query1)) {
					psNations.setString(1, search);
					try (ResultSet rsNations = psNations.executeQuery()) {
						if (!rsNations.next()) {
							System.out.println("Nessun risultato");
							do {
								System.out.print("Do another search? y/n");
								String input = scan.nextLine();
								repeat = true;
								if (input.equalsIgnoreCase("n")) {
									cycle = false;
									System.out.println("Thanks for using our program");
									repeat = false;
								} else if(!input.equalsIgnoreCase("y")) {
									System.out.println("Please type y to do another search or n to terminate the program");
								} else {
									repeat = false;
								}
							} while (repeat == true);
						} else {
							System.out.print(format("ID"));
							System.out.print(format("COUNTRY"));
							System.out.print(format("REGION"));
							System.out.println(format("CONTINENT"));
							do {
								System.out.print(format(rsNations.getInt(2)));
								System.out.print(format(rsNations.getString(1)));
								System.out.print(format(rsNations.getString(3)));
								System.out.println(format(rsNations.getString(4)));
							} while (rsNations.next());

							System.out.println("Choose a country id: ");
							String id = scan.nextLine();
							String query2 = "select l.`language`, c.name from languages l join country_languages cl on l.language_id = cl.language_id join countries c on c.country_id = cl.country_id where c.country_id = ? order by `language`;";
							try (PreparedStatement psLang = con.prepareStatement(query2)) {
								psLang.setString(1, id);
								try (ResultSet rsLang = psLang.executeQuery()) {
									if (!rsLang.next()) {
										System.out.println("Nessun risultato");
										do {
											System.out.print("Do another search? y/n");
											String input = scan.nextLine();
											repeat = true;
											if (input.equalsIgnoreCase("n")) {
												cycle = false;
												System.out.println("Thanks for using our program");
												repeat = false;
											} else if(!input.equalsIgnoreCase("y")) {
												System.out.println("Please type y to do another search or n to terminate the program");
											} else {
												repeat = false;
											}
										} while (repeat == true);
									} else {
										System.out.println("Details for country: " + rsLang.getString(2));
										System.out.print("Languages: ");
										do {
											System.out.print(rsLang.getString(1));
											if (!rsLang.isLast()) {
												System.out.print(", ");
											}
										} while (rsLang.next());
										String query3 = "select cs.* from country_stats cs join countries c on c.country_id = cs.country_id join (select max(`year`) `year` , country_id from country_stats group by country_id) my on cs.country_id = my.country_id and my.`year` = cs.`year` where c.country_id = ?;";
										try (PreparedStatement psStats = con.prepareStatement(query3)) {
											psStats.setString(1, id);
											try (ResultSet rsStats = psStats.executeQuery()) {
												System.out.println("\nMost recent stats");
												if (!rsStats.next()) {
													System.out.println("Stats not found");									
													do {
														System.out.print("Do another search? y/n");
														String input = scan.nextLine();
														repeat = true;
														if (input.equalsIgnoreCase("n")) {
															cycle = false;
															System.out.println("Thanks for using our program");
															repeat = false;
														} else if(!input.equalsIgnoreCase("y")) {
															System.out.println("Please type y to do another search or n to terminate the program");
														} else {
															repeat = false;
														}
													} while (repeat == true);
												} else {
													System.out.println("Year: " + rsStats.getInt("year"));
													System.out
															.println("Population: " + rsStats.getString("population"));
													System.out.println("GDP: " + rsStats.getString("gdp"));
												}								
												do {
													System.out.print("Do another search? y/n");
													String input = scan.nextLine();
													repeat = true;
													if (input.equalsIgnoreCase("n")) {
														cycle = false;
														System.out.println("Thanks for using our program");
														repeat = false;
													} else if(!input.equalsIgnoreCase("y")) {
														System.out.println("Please type y to do another search or n to terminate the program");
													} else {
														repeat = false;
													}
												} while (repeat == true);
												}
											}
										}
									}
								}
							}
						}
					}	 
			} while (cycle);
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
