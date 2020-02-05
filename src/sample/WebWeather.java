package sample;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Martin Dolinsky
 */
public class WebWeather {

	public Weather getData(String city, String code2) {
		Weather weather = null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + code2 +
					"&units=metric&appid=cc5a4588efb2b78dbb431880e398c31d");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String output = br.readLine();
				System.out.println(output);
				JSONObject jsonObject = new JSONObject(output);
				String name = jsonObject.getString("name");
				String country = jsonObject.getJSONObject("sys").getString("country");
				double temp = jsonObject.getJSONObject("main").getDouble("temp");
				int humidity = jsonObject.getJSONObject("main").getInt("humidity");
				double lon = jsonObject.getJSONObject("coord").getDouble("lon");
				double lat = jsonObject.getJSONObject("coord").getDouble("lat");
				int visibility = jsonObject.getInt("visibility");
				long sunrise = jsonObject.getJSONObject("sys").getLong("sunrise");
				long sunset = jsonObject.getJSONObject("sys").getLong("sunset");

				System.out.println("\n" + "Country: " + country + "\n" + "City: "
						+ name + "\n" + "Temperature: " + temp + "\n" + "Humidity: " + humidity
						+ "\n" + "Coordinates: " + lon + " " + lat + "\n" + "Visibility: " + visibility + "\n"
						+ "Sunrise: " + sunrise + "\n" + "Sunset: " + sunset);

				return new Weather(name, country, temp, humidity, lon, lat, visibility, sunrise, sunset);

			} else throw new NoSuchCityException("City info has not been found");


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return null;
	}
}
