package ch.fcappenzell.gruempeli.administration.tools.tournament.model.arrangements;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;

public class Category {

    public StringProperty code = new SimpleStringProperty();
    public StringProperty name = new SimpleStringProperty();
    public StringProperty description = new SimpleStringProperty();
    public List<Team> teams;
    private String color;

    List<Color> colors = FXCollections.observableArrayList( //todo color picker
    Color.MOCCASIN,
    Color.NAVAJOWHITE,
    Color.NAVY,
    Color.OLDLACE,
    Color.OLIVE,
    Color.OLIVEDRAB,
    Color.ORANGE,
    Color.ORANGERED,
    //Color.ORCHID,
    Color.PALEGOLDENROD,
    Color.PALEGREEN,
    Color.PALETURQUOISE,
    Color.PALEVIOLETRED,
    Color.PAPAYAWHIP,
    Color.PEACHPUFF,
    Color.PERU,
    Color.PINK,
    Color.PLUM,
    Color.POWDERBLUE,
    Color.PURPLE,
    Color.RED,
    Color.ROSYBROWN,
    Color.ROYALBLUE,
    Color.SADDLEBROWN,
    Color.SALMON,
    Color.SANDYBROWN,
    Color.SEAGREEN,
    Color.SEASHELL,
    Color.SIENNA,
    Color.SILVER,
    Color.SKYBLUE,
    Color.SLATEBLUE,
    Color.SLATEGRAY,
    Color.SLATEGREY,
    Color.SNOW,
    Color.SPRINGGREEN,
    Color.STEELBLUE,
    Color.TAN,
    Color.TEAL,
    Color.THISTLE,
    Color.TOMATO

    );


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
        Random rand = new Random(stringToSeed(this.name));
        return colors.get(rand.nextInt(colors.size())).toString();
    }

    public void setColor(String color) {
        this.color = color;
    }

    static long stringToSeed(StringProperty s) {
        if (s == null) {
            return 0;
        }
        long hash = 0;
        for (char c : s.toString().toCharArray()) {
            hash = 31L*hash + c;
        }
        return hash;
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
