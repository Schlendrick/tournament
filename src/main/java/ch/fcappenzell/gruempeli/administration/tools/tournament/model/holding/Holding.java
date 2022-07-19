package ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.GameModelVisitor;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Round;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Holding {

    private Long id;
    private Long order;

    private String roundCode;
    private Round round;
    private final SimpleStringProperty code = new SimpleStringProperty();
    private final SimpleStringProperty bookName = new SimpleStringProperty();
    private List<Match> matches = new ArrayList<>();

    public String getRoundCode() {
        return roundCode;
    }

    public void setRoundCode(String roundCode) {
        this.roundCode = roundCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getBookName() {
        return bookName.get();
    }

    public SimpleStringProperty bookNameProperty() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void accept(GameModelVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (getBookName() != null) {
            return getBookName();
        }
        return super.toString();
    }
}
