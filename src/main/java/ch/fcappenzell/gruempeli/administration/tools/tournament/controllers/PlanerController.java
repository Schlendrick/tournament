package ch.fcappenzell.gruempeli.administration.tools.tournament.controllers;

import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.AvailableMatchesView;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

@Component
public class PlanerController {

    @FXML
    private VBox openMatchesPane;

    @FXML
    private TabPane scheduleTabPane;

}
