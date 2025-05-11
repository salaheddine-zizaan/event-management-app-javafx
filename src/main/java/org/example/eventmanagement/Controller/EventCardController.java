package org.example.eventmanagement.Controller;

import com.sun.jdi.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventCardController {
    @FXML private Text titleLabel;
    @FXML private Text dateLabel;
    @FXML private Text locationLabel;
    @FXML private Text timeLabel;
    @FXML private Text priceLabel;


    public void setEventData(String title, String description, String date,
                             String location, String city, String country,
                             String startTime, String endTime, String price) {
        titleLabel.setText(title);
        dateLabel.setText(date);
        locationLabel.setText(location + ", " + city + ", " + country);
        priceLabel.setText(price + " MAD");
    }

}
