package ch.fcappenzell.gruempeli.administration.tools.tournament.model.arrangements;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.GameModelVisitor;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Round;

import java.util.ArrayList;
import java.util.List;

public class Arrangement {

    private Category category;
    private List<Round> rounds = new ArrayList<>();

    public Arrangement() {
    }

    public Arrangement(Category category) {
        this.category = category;
        rounds = new ArrayList<>();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public void accept(GameModelVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Arrangement{" +
                "category=" + category +
                ", rounds=" + rounds +
                '}';
    }
}
