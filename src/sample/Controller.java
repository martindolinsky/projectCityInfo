package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.util.List;

public class Controller {
    public Label lblPop;
    public Label lblTemp;
    public Label lblHum;
    public Label lblDis;
    public Label lblCity;

    public ComboBox comboCountry;
    public ComboBox comboCity;

    public Button btnShow;

    List countries;
    String country;
    List<City> cities;


    public Controller() {
        Database database = new Database();
        countries = database.getCountries();
        try {
            cities = database.getCity(country);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickComboCountry(Event event) {
        comboCountry.getItems().setAll(countries);
        comboCity.setOpacity(1);
        comboCity.setDisable(false);
        lblCity.setOpacity(1);
    }

    public void clickComboCity(Event event) {

        System.out.println();
        country = (String) comboCountry.getValue();
        System.out.println(country);

        try {
            cities = new Database().getCity(country);
            btnShow.setOpacity(1);
            comboCity.getItems().clear();
            for (City c:cities) {
                comboCity.getItems().add(c.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickBtnShow(ActionEvent actionEvent) {
        String city = (String) comboCity.getValue();
        System.out.println(city);

        try {
            for (City c:cities) {
                if (c.getName().equals(city)) {
                    lblPop.setText("Population: " + formatPopString(c.getPopulation()));
                    System.out.println(formatPopString((c.getPopulation())));
                    lblTemp.setText("Temperature: " + new WebWeather().getData(city, c.getTwoCode()).getTemp());
                    lblHum.setText("Humidity: " + new WebWeather().getData(city, c.getTwoCode()).getHumidity());
                    lblDis.setText("Coord: " + new WebWeather().getData(city, c.getTwoCode()).getLon() + " " + new WebWeather().getData(city, c.getTwoCode()).getLat());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatPopString(int population) {
        String data = String.valueOf(population);
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(population);
    }
}
