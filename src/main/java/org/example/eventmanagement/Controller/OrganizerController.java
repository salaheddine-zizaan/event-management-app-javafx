package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.eventmanagement.Model.Organizer;
import org.example.eventmanagement.utils.Session;

public class OrganizerController {
    @FXML private TextField FirstnameField;
    @FXML private TextField LastnameField;
    @FXML private TextField EmailField;
    @FXML private TextField CompanyField;
    @FXML private TextField PhoneField;
    @FXML private TextField UsernameField;
    @FXML private TextField FieldField;


    Organizer organizer = Session.getInstance().getLoggedInOrganizer();

    public void initialize() {
        System.out.println(organizer);
        FirstnameField.setText(organizer.getFirstName());
        LastnameField.setText(organizer.getLastName());
        EmailField.setText(organizer.getEmail());
        CompanyField.setText(organizer.getName());
        PhoneField.setText(organizer.getPhone());
        UsernameField.setText(organizer.getUsername());
        FieldField.setText(organizer.getField());
    }
}


