package ch.fcappenzell.gruempeli.administration.tools.tournament.controllers;

import ch.fcappenzell.gruempeli.administration.tools.tournament.WindowPreferencesSupport;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.Match;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.XMLMatch;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.XMLTournamentSchedule;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.assignment.AssignmentView;
import ch.fcappenzell.gruempeli.administration.tools.tournament.organizer.planer.PlanerView;
import ch.fcappenzell.gruempeli.administration.tools.tournament.persistence.DbHandler;
import ch.fcappenzell.gruempeli.administration.tools.tournament.service.DocumentService;
import ch.fcappenzell.gruempeli.administration.tools.tournament.service.impl.FileUploadServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ApplicationContext context;

    @Autowired
    WindowPreferencesSupport preferencesSupport;

    @Autowired
    DocumentService documentService;

    @Autowired
    FileUploadServiceImpl fileUploadService;

    @Autowired
    DbHandler dbHandler;

    @FXML
    private TabPane tabPane;

    @FXML
    private MenuItem connect;

    @FXML
    private MenuItem clear;

    @FXML
    private MenuItem upload;

    @FXML
    private MenuItem preferences;

    @FXML
    private Tab plan;

    @FXML
    private Tab assignment;

    @FXML
    private Label version;

    @FXML
    private Label path;

    private Stage stage;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        connect.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Turnier Datenbank öffnen");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Access Db", "*.mdb", "*.accdb"));
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                String absolutePath = file.getAbsolutePath();
                preferencesSupport.setDbPatch(absolutePath);
                dbHandler.connect(absolutePath);
            } else {
                preferencesSupport.setDbPatch(null);
            }
            path.setText(String.format("%s ", preferencesSupport.getDbPath()));

        });

        // todo - während des Spiels gefährlich
        clear.setDisable(true);

        clear.setOnAction(e -> {
            logger.info("clear pressed");
            dbHandler.clearPlan();
        });

        upload.setOnAction(e -> {
            logger.info("upload pressed");
            List<Match> matchList = dbHandler.getMatches();
            this.writeMatches(matchList);
        });
        
        //plan.setContent(context.getBean(PlanerView.class).getView());

        //assignment.setContent(context.getBean(AssignmentView.class).getView());

        preferences.setOnAction(e -> {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(this.stage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text("This is a Dialog"));
            Scene dialogScene = new Scene(dialogVbox, 600, 600);
            dialog.setScene(dialogScene);
            dialog.show();
        });

        version.setText("V 1.0.0");

        /*
        String dbPath = preferencesSupport.getDbPath();
        if(dbPath != null){
            dbHandler.connect(dbPath);
        }

        path.setText(String.format("%s ", preferencesSupport.getDbPath()));

         */

    }

    private void writeMatches(List<Match> matches) {
        logger.info("writeMatches");

        XMLTournamentSchedule schedule = new XMLTournamentSchedule();
        schedule.matches = matches.stream()
                .map(m -> {
                    XMLMatch xmlMatch = new XMLMatch();
                    xmlMatch.dateTime = m.getTime().toString();
                    xmlMatch.field = Integer.toString(m.getField());
                    xmlMatch.game = m.getHolding().toString();
                    xmlMatch.homeTeam = m.getHomeTeam().getName();
                    xmlMatch.visitorTeam = m.getVisitorTeam().getName();
                    xmlMatch.homeScore = m.getHomeScore();
                    xmlMatch.visitorScore = m.getVisitorScore();
                    xmlMatch.penalty = m.isPenalty();
                    return xmlMatch;
                })
                .toArray(XMLMatch[]::new);

        Class[] clazzes = new Class[2];
        clazzes[0] = XMLTournamentSchedule.class;
        clazzes[1] = XMLMatch.class;

        File file = documentService.createXML(clazzes, schedule);

        fileUploadService.handleFileUpload(file);

    }
}