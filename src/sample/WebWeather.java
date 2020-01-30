package sample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
		JSONParser parse = new JSONParser();
		JSONArray jsonArr = new JSONArray();
		try {
			URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + code2 +
					"&units=metric&appid=cc5a4588efb2b78dbb431880e398c31d");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				String output = br.readLine();

				//System.out.println(output);
				System.out.println(output);

				JSONObject jsonObj = (JSONObject) parse.parse(output);

				String name = "";
				String country = "";
				long humidity = 0;
				double temp = 0;
				double lon = 0;
				double lat = 0;

				jsonArr.add(jsonObj.get("main"));
				for (int i = 0; i < jsonArr.size(); i++) {
					JSONObject jsonObjTemp = (JSONObject) jsonArr.get(i);

					System.out.println("Elements under results array");
					System.out.println("Humidity:" + jsonObjTemp.get("humidity"));
					System.out.println("\nTemperature: " + jsonObjTemp.get("temp"));
					System.out.println("Feels like: " + jsonObjTemp.get("feels_like"));

					temp = (double) jsonObjTemp.get("temp");
					double number = Double.valueOf(temp);
					temp = number;
					humidity = (long) jsonObjTemp.get("humidity");
					long number1 = Long.valueOf(humidity);
				}

				jsonArr.add(jsonObj.get("coord"));
				for (int i = 1; i < jsonArr.size(); i++) {
					JSONObject jsonObjTemp = (JSONObject) jsonArr.get(i);

					System.out.println("Lat: " + jsonObjTemp.get("lat"));
					System.out.println("Lon: " + jsonObjTemp.get("lon"));

					lat = (double) jsonObjTemp.get("lat");
					double number = Double.valueOf(lat);
					lat = number;
					lon = (double) jsonObjTemp.get("lon");
					double number1 = Double.valueOf(lon);
					lon = number1;
				}

				jsonArr.add(jsonObj.get("sys"));
				for (int i = 2; i < jsonArr.size(); i++) {
					JSONObject jsonObjTemp = (JSONObject) jsonArr.get(i);

					country = (String) jsonObjTemp.get("country");
					System.out.println(country);
				}

				name = (String) jsonObj.get("name");
				System.out.println(name);

				weather = new Weather(name, country, temp, humidity, lon, lat);

				System.out.println(weather.getName() + " " + weather.getCountry() + " " + weather.getTemp() + " "
						+ weather.getHumidity() + " " + weather.getLat() + " " + weather.getLon());

				return weather;
			}

			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTemperatureFromJSON(String output) {
		String[] parts = output.split(":");
		String[] one = parts[11].split(",");
		return one[0];
	}

	public String getHumidityFromJSON(String output) {
		String[] parts = output.split(":");
		String[] one = parts[16].split(",");
		return one[0].substring(0, 2);
	}
}
