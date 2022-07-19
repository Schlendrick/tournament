package ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer;

import ch.fcappenzell.gruempeli.administration.tools.tournament.model.*;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.arrangements.Category;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.holding.Holding;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.match.Round;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OrganizerEntry extends VBox {

    private final Label cat;
    private final Label groupContext;
    private final Label teams;
    private Match match;

    OrganizerEntry() {
        paddingProperty().set(new Insets(0, 2, 0, 2));
        cat = new Label();
        cat.setFont(Font.font(cat.getFont().getName(), FontWeight.BOLD, cat.getFont().getSize()));
        HBox.setMargin(cat, new Insets(0, 2, 0, 0));
        groupContext = new Label();
        getChildren().add(new HBox(2, cat, groupContext));
        teams = new Label();
        HBox teamsContainer = new HBox(2);
        teamsContainer.setAlignment(Pos.CENTER);
        teamsContainer.getChildren().add(teams);
        getChildren().add(teamsContainer);
    }

    public void set(Match match) {
        this.match = match;
        if (match == null) {
            setColor(Color.DIMGRAY);
            cat.setText("-");
            groupContext.setText("-");
            teams.setText("-");
        } else {
            teams.setText(match.getHomeTeam().getTeamNr() + " - " + match.getVisitorTeam().getTeamNr());
            Holding holding = match.getHolding();
            Round round = holding.getRound();
            groupContext.setText(ModelHelper.toName(match));
            Category category = round.getArrangement().getCategory();
            cat.setText(category.getName());
            if (category.getColor() != null) {
                setColor(Color.valueOf(category.getColor()));
            }
        }
    }

    private void setColor(Color color) {
        setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), Insets.EMPTY)));
    }

    public Match get() {
        return match;
    }
}
