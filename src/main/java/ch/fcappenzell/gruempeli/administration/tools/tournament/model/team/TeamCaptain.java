package ch.fcappenzell.gruempeli.administration.tools.tournament.model.team;

public class TeamCaptain {
    private String title;
    private String firstName;
    private String lastName;
    private String street;
    private String plzPlace;
    private String email;
    private String phone;

    public TeamCaptain() {
    }

    public TeamCaptain(String title, String firstName, String lastName, String street, String plzPlace, String email, String phone) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.plzPlace = plzPlace;
        this.email = email;
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPlzPlace() {
        return plzPlace;
    }

    public void setPlzPlace(String plzPlace) {
        this.plzPlace = plzPlace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "TeamCaptain{" +
                "title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", street='" + street + '\'' +
                ", plzPlace='" + plzPlace + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
