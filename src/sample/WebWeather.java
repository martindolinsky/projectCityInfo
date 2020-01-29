package sample;

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
		try {
			URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city +
					"," + code2 + "&units=metric&&APPID=cc5a4588efb2b78dbb431880e398c31d");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept","application/json");

			if (conn.getResponseCode() == 200) {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String output = br.readLine();
				System.out.println(output);
				System.out.println(getTemperatureFromJSON(output));
				System.out.println(getHumidityFromJSON(output));

			}



		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getTemperatureFromJSON(String output) {
		String[] parts = output.split(":");
		String[] one= parts[11].split(",");
		return one[0];
	}

	public String getHumidityFromJSON(String output) {
		String[] parts = output.split(":");
		String[] one= parts[16].split(",");
		return one[0].substring(0,2);

	}
}
