package ch.fcappenzell.gruempeli.administration.tools.tournament.model.arrangements;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class Category {

    public StringProperty code = new SimpleStringProperty();
    public StringProperty name = new SimpleStringProperty();
    public StringProperty description = new SimpleStringProperty();
    public List<Team> teams;
    private String color;

    public Category(String code, String name, String description) {
        setCode(code);
        setName(name);
        setDescription(description);
    }

    public Category() {
    }

    public String getCode() {
        return code.getValue();
    }

    public void setCode(String code) {
        this.code.setValue(code);
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public String getDescription() {
        return description.getValue();
    }

    public void setDescription(String description) {
        this.description.setValue(description);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return code.get().equals(category.code.get());
    }

    @Override
    public int hashCode() {
        return code.get().hashCode();
    }

    @Override
    public String toString() {
        return "Category{" +
                "code=" + code +
                ", name=" + name +
                ", description=" + description +
                ", teams=" + teams +
                ", color='" + color + '\'' +
                '}';
    }
}
