package org.example.eventmanagement.utils;

import org.example.eventmanagement.Model.*;

public class Session {
    private static Session instance;

    private Person loggedInPerson;
    private Admin loggedInAdmin;
    private Organizer loggedInOrganizer;
    private User loggedInUser;

    private Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // === Person ===
    public void setLoggedInPerson(Person person) {
        this.loggedInPerson = person;
    }

    public Person getLoggedInPerson() {
        return loggedInPerson;
    }

    // === Admin ===
    public void setLoggedInAdmin(Admin admin) {
        this.loggedInAdmin = admin;
    }

    public Admin getLoggedInAdmin() {
        return loggedInAdmin;
    }

    // === Organizer ===
    public void setLoggedInOrganizer(Organizer organizer) {
        this.loggedInOrganizer = organizer;
    }

    public Organizer getLoggedInOrganizer() {
        return loggedInOrganizer;
    }

    // === User ===
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    // === Logout ===
    public void logout() {
        loggedInPerson = null;
        loggedInAdmin = null;
        loggedInOrganizer = null;
        loggedInUser = null;
        instance = null;
    }
}
