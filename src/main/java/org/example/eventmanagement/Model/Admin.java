package org.example.eventmanagement.Model;

public class Admin extends Person {
    private int id_admin;

    public Admin(int id_admin, int id_person, String firstname, String lastname,
                 String email, String phone, String password, String role, String username) {
        super(id_person, firstname, lastname, email, phone, password, role, username);
        this.id_admin = id_admin;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }
}
