package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
            if (checkDetails.isSelected()) {
                clickCheckDetails(actionEvent);
            }
            for (City c:cities) {
                if (c.getName().equals(city)) {
                    lblPop.setText("Population: " + formatPopString(c.getPopulation()));
                    System.out.println(formatPopString((c.getPopulation())));
                    lblTemp.setText("Temperature: " + new WebWeather().getData(city, c.getTwoCode()).getTemp() + " ºC");
                    lblHum.setText("Humidity: " + new WebWeather().getData(city, c.getTwoCode()).getHumidity() + " %");
                    lblDis.setText("Coord: " + new WebWeather().getData(city, c.getTwoCode()).getLon() + ", "
                            + new WebWeather().getData(city, c.getTwoCode()).getLat());
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
                        // 2.77777778 × 10-7
                        double hour = new WebWeather().getData(city, c.getTwoCode()).getSunrise() / (2.77777778 * Math.pow(10, -7));
                        double minute = hour / 60;

                        lblVis.setText("Visibility: " + new WebWeather().getData(city, c.getTwoCode()).getVisibility());
                        lblSet.setText("Sunset: " + new WebWeather().getData(city, c.getTwoCode()).getSunset());
                        lblRise.setText("Sunrise: " + new WebWeather().getData(city, c.getTwoCode()).getSunrise());
                        System.out.println(getTime(1580365391L));
                        System.out.println(getTime(1580399167L));
                        getTime3("1580365391");
                        getTime3("1580399167");
                        System.out.println();
                        getTime4(1580365391L);
                        getTime4(1580399167L);
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

    public String getTime(long millis) {
        millis = millis / 1000;
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));


    }

    public String getTime2(long ms) {
        long totalSecs = ms / 1000;
        long hours = (totalSecs / 3600);
        long mins = (totalSecs / 60) % 60;
        long secs = totalSecs % 60;
        String minsString = (mins == 0)
                ? "00"
                : ((mins < 10)
                ? "0" + mins
                : "" + mins);
        String secsString = (secs == 0)
                ? "00"
                : ((secs < 10)
                ? "0" + secs
                : "" + secs);
        if (hours > 0)
            return hours + ":" + minsString + ":" + secsString;
        else if (mins > 0)
            return mins + ":" + secsString;
        else return ":" + secsString;
    }

    public void getTime3(String x) {

        long foo = Long.parseLong(x);
        System.out.println(x + "\n" + foo);

        Date date = new Date(foo);
        DateFormat formatter = new SimpleDateFormat("HH:mm:SS");
        System.out.println(formatter.format(date));
    }


    public void getTime4(long currentDateTime) {


        //creating Date from millisecond
        Date currentDate = new Date(currentDateTime);

        //printing value of Date
        System.out.println("current Date: " + currentDate);

        DateFormat df = new SimpleDateFormat("HH:mm:ss");

        //formatted value of current Date
        System.out.println("Milliseconds to Date: " + df.format(currentDate));

        //Converting milliseconds to Date using Calendar
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentDateTime);
        System.out.println("Milliseconds to Date using Calendar:"
                + df.format(cal.getTime()));

        //copying one Date's value into another Date in Java
        Date now = new Date();
        Date copiedDate = new Date(now.getTime());

        System.out.println("original Date: " + df.format(now));
    }
}
