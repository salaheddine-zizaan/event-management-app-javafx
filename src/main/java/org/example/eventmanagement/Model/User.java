package org.example.eventmanagement.Model;

public class User extends Person {
    private int id_user;
    private String address;

    public User(int id_user, int id_person, String firstname, String lastname,
                String email, String phone, String password, String role,
                String username, String address) {
        super(id_person, firstname, lastname, email, phone, password, role, username);
        this.id_user = id_user;
        this.address = address;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
