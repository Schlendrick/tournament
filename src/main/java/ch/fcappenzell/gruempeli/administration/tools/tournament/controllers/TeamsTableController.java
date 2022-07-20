package ch.fcappenzell.gruempeli.administration.tools.tournament.controllers;

import ch.fcappenzell.gruempeli.administration.tools.tournament.dao.team.TeamDAO;
import ch.fcappenzell.gruempeli.administration.tools.tournament.model.team.Team;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TeamsTableController {

    AnnotationConfigApplicationContext context;

    @FXML
    private TextField filterField;
    @FXML
    private TableView<Team> personTable;

    @FXML
    private TableColumn<Team, String> teamNameColumn;
    @FXML
    private TableColumn<Team, String> firstNameColumn;
    @FXML
    private TableColumn<Team, String> lastNameColumn;
    @FXML
    private TableColumn<Team, Boolean> disqualifiedColumn;
    @FXML
    private TableColumn<Team, Boolean> paidColumn;

    private ObservableList<Team> masterData = FXCollections.observableArrayList();

    @Autowired
    public TeamsTableController(AnnotationConfigApplicationContext context) {
        this.context = context;
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * Initializes the table columns and sets up sorting and filtering.
     */
    @FXML
    private void initialize() {
        // 0. Initialize the columns.
        teamNameColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getName()));
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCaptain().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCaptain().getLastName()));
        disqualifiedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(disqualifiedColumn)); // show as checkbox
        disqualifiedColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty((cellData.getValue().getDisqualified())));
        paidColumn.setCellFactory(CheckBoxTableCell.forTableColumn(paidColumn)); // show as checkbox
        paidColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty((cellData.getValue().getPaid())));

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Team> filteredData = new FilteredList<>(masterData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(team -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (team.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (team.getCaptain().getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (team.getCaptain().getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Team> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(personTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        personTable.setItems(sortedData);
    }

    public void updateTeams() {

        clearView();

        TeamDAO teamDAO = context.getBean(TeamDAO.class);

        masterData.addAll(teamDAO.getAllTeams());
    }

    public void clearView(){
        masterData.clear();
    }
}
