package ch.fcappenzell.gruempeli.administration.tools.tournament.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public class Round {

    private Arrangement arrangement;
    private final SimpleStringProperty code = new SimpleStringProperty();
    private final SimpleStringProperty bookName = new SimpleStringProperty();
    private List<Holding> holdings = new ArrayList<>();

    public Arrangement getArrangement() {
        return arrangement;
    }

    public void setArrangement(Arrangement arrangement) {
        this.arrangement = arrangement;
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

    public List<Holding> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<Holding> holdings) {
        this.holdings = holdings;
    }

    public int getIndex() {
        return getArrangement().getRounds().indexOf(this);
    }

    public void accept(GameModelVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (bookName != null && bookName.get() != null) {
            return bookName.get();
        }
        if (code != null && code.get() != null) {
            return code.get();
        }
        return super.toString();
    }
}