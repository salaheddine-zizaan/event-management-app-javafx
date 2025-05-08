package org.example.eventmanagement.Model;

public class Organizer extends Person {
    private int idOrganizer;
    private String name;
    private String field;

    public Organizer() {}

    public Organizer(int idPerson, String firstName, String lastName, String email,
                     String phone, String password, String role, String username,
                     String name, String field) {
        super(idPerson, firstName, lastName, email, phone, password, role, username);
        this.name = name;
        this.field = field;
    }

    public int getIdOrganizer() {
        return idOrganizer;
    }

    public void setIdOrganizer(int idOrganizer) {
        this.idOrganizer = idOrganizer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
