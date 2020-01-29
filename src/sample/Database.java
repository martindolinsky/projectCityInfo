package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Martin Dolinsky
 */
public class Database {
	private final String JDBC = "com.mysql.cj.jdbc.Driver";
	private final String URL =
			"jdbc:mysql://itsovy.sk:3306/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private Connection connection;

	private final String SELECT_POPULATION =
			"SELECT json_extract(Info, '$.Population') AS pop FROM city JOIN country ON" +
					" country.code = city.countrycode WHERE city.Name LIKE ? AND country.Name like ?";
	private final String SELECT_COUNTRY = "SELECT * FROM country order by Name ASC";
	private final String SELECT_CITY =
			"SELECT country.Name, city.name, city.CountryCode, country.Code2, json_extract(Info, '$.Population') AS Info " +
					"FROM country JOIN city ON country.code = city.countrycode where country.name like ? order by city.name ASC";


	public Connection getConnection() throws Exception {
		Class.forName(JDBC);
		connection = DriverManager.getConnection(URL, "student", "kosice2019");
		return connection;
	}

	public List getCity(String country) throws Exception {
		try {
			PreparedStatement statement = getConnection().prepareStatement(SELECT_CITY);
			statement.setString(1,country);
			ResultSet rs = statement.executeQuery();
			String city;
			List<City> list = new ArrayList();
			while (rs.next()) {
				String name = rs.getString("city.Name");
				String twoCode = rs.getString("country.Code2");
				String threeCode = rs.getString("city.CountryCode");
				int population = rs.getInt("Info");
				String countryName = rs.getString("country.Name");
				City newCity = new City(name,population,threeCode,twoCode,countryName);
				list.add(newCity);
			}
			connection.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List getCountries() {
		try {
			PreparedStatement statement = getConnection().prepareStatement(SELECT_COUNTRY);
			ResultSet rs = statement.executeQuery();
			String country;
			List<String> list = new ArrayList();
			while (rs.next()) {
				country = (rs.getString("Name"));
				list.add(country);
			}
			connection.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getPopulation(String city, String country) {
		try {
			PreparedStatement statement = getConnection().prepareStatement(SELECT_POPULATION);
			statement.setString(1, city);
			statement.setString(2, country);
			ResultSet rs = statement.executeQuery();
			String population;
			if (rs.next()) {
				population = (rs.getString(1));
				return population;
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
