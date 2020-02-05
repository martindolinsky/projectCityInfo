package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;

import java.text.DecimalFormat;
import java.util.List;

public class Controller {
    public Label lblPop;
    public Label lblTemp;
    public Label lblHum;
    public Label lblDis;
    public Label lblCity;
    public Label lblRise;
    public Label lblSet;
    public Label lblVis;

    public ComboBox comboCountry;
    public ComboBox comboCity;

    public Button btnShow;
    public CheckBox checkDetails;
    public Button btnOpenWeb;
    public TextField txtFldSearch;


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
            btnOpenWeb.setOpacity(1);
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
            if (checkDetails.isSelected()) {
                clickCheckDetails(actionEvent);
            }
            for (City c:cities) {

                if (c.getName().equals(city)) {
                    Weather weather = new WebWeather().getData(city, c.getTwoCode());
                    lblPop.setText("Population: " + formatPopString(c.getPopulation()));
                    System.out.println(formatPopString((c.getPopulation())));
                    if (weather != null) {
                        lblTemp.setText("Temperature: " + new WebWeather().getData(city, c.getTwoCode()).getTemp() + " ºC");
                        lblHum.setText("Humidity: " + new WebWeather().getData(city, c.getTwoCode()).getHumidity() + " %");
                        lblDis.setText("Coord: " + new WebWeather().getData(city, c.getTwoCode()).getLon() + ", "
                                + new WebWeather().getData(city, c.getTwoCode()).getLat());
                    } else {
                        lblTemp.setText("Temperature: --- ºC");
                        lblHum.setText("Humidity: --- %");
                        lblDis.setText("Coord: ---");
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickCheckDetails(ActionEvent actionEvent) {
        String city = (String) comboCity.getValue();
        System.out.println(city);

        try {
            if (checkDetails.isSelected()) {
                lblVis.setOpacity(1);
                lblRise.setOpacity(1);
                lblSet.setOpacity(1);
                for (City c : cities) {
                    if (c.getName().equals(city)) {
                        Weather weather = new WebWeather().getData(city, c.getTwoCode());
                        if (weather != null) {

                            lblVis.setText("Visibility: " + (new WebWeather().getData(city, c.getTwoCode()).getVisibility() / 1000) + " km");
                            lblSet.setText("Sunset: " + getTimeFormat(new WebWeather().getData(city, c.getTwoCode()).getSunset()));
                            lblRise.setText("Sunrise: " + getTimeFormat(new WebWeather().getData(city, c.getTwoCode()).getSunrise()));
                        } else {
                            lblVis.setText("Visibility: ---");
                            lblSet.setText("Sunset: ---");
                            lblRise.setText("Sunrise: ---");
                        }
                    }
                }
            } else {
                lblVis.setOpacity(0);
                lblRise.setOpacity(0);
                lblSet.setOpacity(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatPopString(int population) {
        return new DecimalFormat("#,###").format(population);
    }

    public String getTimeFormat(long number) {
        return new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date(number * 1000));
    }

    public void clickBtnOpenWeb(ActionEvent actionEvent) {
        String url = "";
        String city = (String) comboCity.getValue();
        try {
            for (City c : cities) {
                if (c.getName().equals(city)) {
                    url = "https://www.google.sk/maps/@" +
                            new WebWeather().getData(city, c.getTwoCode()).getLat() + "," + new WebWeather().getData(city, c.getTwoCode()).getLon() +
                            "," + "13z";
                }
            }
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickTxtFldSearch(ActionEvent actionEvent) {

    }
}
