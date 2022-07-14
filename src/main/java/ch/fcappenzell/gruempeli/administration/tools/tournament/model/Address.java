package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Address {
    private int id;
    private final SimpleStringProperty name1 = new SimpleStringProperty();
    private final SimpleStringProperty name2 = new SimpleStringProperty();
    private final SimpleStringProperty street = new SimpleStringProperty();
    private final SimpleIntegerProperty plz = new SimpleIntegerProperty();
    private final SimpleStringProperty place = new SimpleStringProperty();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName1() {
        return name1.get();
    }

    public void setName1(String name1) {
        this.name1.set(name1);
    }

    public String getName2() {
        return name2.get();
    }

    public void setName2(String name2) {
        this.name2.set(name2);
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public int getPlz() {
        return plz.get();
    }

    public void setPlz(int plz) {
        this.plz.set(plz);
    }

    public String getPlace() {
        return place.get();
    }

    public void setPlace(String place) {
        this.place.set(place);
    }
}
