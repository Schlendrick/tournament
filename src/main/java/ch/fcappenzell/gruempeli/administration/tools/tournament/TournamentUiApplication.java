/*
 * Created by Marco on 21.05.2018.
 */
package ch.fcappenzell.gruempeli.administration.tools.tournament;

import ch.fcappenzell.gruempeli.administration.tools.tournament.controllers.MainController;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TournamentUiApplication {

    public static void main(final String[] args) {
        Application.launch(ChartApplication.class, args);
    }

}
