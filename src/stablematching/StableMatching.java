/*
 *
 * Project 3: Stable Matching
 * Caleb Davenport
 * Roan Martin-Hayden
 * EECS 2500-091: Dr. Ledgard
 *
 * Description:
 *
 */
package stablematching;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StableMatching extends Application {

    protected TableView<Proposer> TABLE = new TableView<>();
    protected VBox MENU = new VBox();
    protected LinkedList<Proposer> ProposerList = new LinkedList<>();
    protected LinkedList<Acceptor> AcceptorList = new LinkedList<>();
    protected Problem problem;
    protected ObservableList<Proposer> Solution = FXCollections.observableArrayList();
    protected int numCouples;
    protected String FILE_NAME;

    public StableMatching() {
        this.problem = new Problem(ProposerList, AcceptorList, Solution);
    }

    @Override
    public void start(Stage primaryStage) {
        updateTable();
        updateMenu();

        BorderPane root = new BorderPane();
        root.setTop(MENU);
        root.setCenter(TABLE);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Stable Marriage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateMenu() {
        HBox textField = new HBox();
        Label text = new Label("Filename: ");
        TextField inputFileTextField = new TextField();
        Button textButton = new Button("Load");
        textButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                FILE_NAME = inputFileTextField.getText();
            }
        });
        textField.getChildren().addAll(text, inputFileTextField, textButton);

        final ToggleGroup groupSelect = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Group 1 Selects");
        rb1.setToggleGroup(groupSelect);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Group 2 Selects");
        rb2.setToggleGroup(groupSelect);

        Button startButton = new Button();
        startButton.setText("Generate Marriages");
        startButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("List of People");
                try {
                    startProblem(false);
                    displayMarriages();
                } catch (Exception e) {
                }
            }
        });
        MENU.getChildren().addAll(textField, rb1, rb2, startButton);
    }

    private void displayMarriages() {
        StackPane root = new StackPane();
        root.getChildren().add(TABLE);
        Stage stage = new Stage();
        stage.setTitle("Final Marriages");
        stage.setScene(new Scene(root, 300, 250));
        stage.show();
    }

    private void updateTable() {
        TableColumn acceptors;
        TableColumn proposers;

        TABLE.getColumns().clear();
        acceptors = new TableColumn("Name");
        acceptors.setCellValueFactory(new PropertyValueFactory<>("name"));
        proposers = new TableColumn("Partner");
        proposers.setCellValueFactory(new PropertyValueFactory<>("partnerName"));

        acceptors.prefWidthProperty().bind(TABLE.widthProperty()
                .multiply(0.5));
        proposers.prefWidthProperty().bind(TABLE.widthProperty()
                .multiply(0.5).subtract(2));

        TABLE.setItems(Solution);
        TABLE.getColumns().addAll(acceptors, proposers);
        TABLE.setPlaceholder(new Label("No marriages generated yet."));
    }

    public void startProblem(boolean firstProposes) throws Exception {
        File f = new File(FILE_NAME);
        Scanner fileInput = new Scanner(f);
        LinkedList preferences;
        numCouples = fileInput.nextInt();
        fileInput.nextLine();
        for (int i = 0; i < numCouples * 2; i = i + 2) {
            String name = fileInput.nextLine();
            fileInput.nextLine();
            if (firstProposes) {
                Proposer p = new Proposer(name);
                ProposerList.add(p);
            } else {
                Acceptor p = new Acceptor(name);
                AcceptorList.add(p);
            }
        }
        for (int i = 0; i < numCouples * 2; i = i + 2) {
            String name = fileInput.nextLine();
            fileInput.nextLine();
            if (!firstProposes) {
                Proposer p = new Proposer(name);
                ProposerList.add(p);
            } else {
                Acceptor p = new Acceptor(name);
                AcceptorList.add(p);
            }
        }
        fileInput = new Scanner(f);
        fileInput.nextLine();
        for (int i = 0; i < numCouples; i++) {
            String name = fileInput.nextLine();
            if (firstProposes) {
                preferences = stringToAcceptorLinkedList(fileInput.nextLine(),
                        AcceptorList);
            } else {
                preferences = stringToProposerLinkedList(fileInput.nextLine(),
                        ProposerList);
            }
            for (int j = 0; j < numCouples; j++) {
                if (firstProposes) {
                    if (ProposerList.get(j).getName().equals(name)) {
                        ProposerList.get(j).setPreferences(preferences);
                        break;
                    }
                } else if (AcceptorList.get(j).getName().equals(name)) {
                    AcceptorList.get(j).setPreferences(preferences);
                    break;
                }
            }
        }
        for (int i = 0; i < numCouples; i++) {
            String name = fileInput.nextLine();
            if (!firstProposes) {
                preferences = stringToAcceptorLinkedList(fileInput.nextLine(),
                        AcceptorList);
            } else {
                preferences = stringToProposerLinkedList(fileInput.nextLine(),
                        ProposerList);
            }
            for (int j = 0; j < numCouples; j++) {
                if (!firstProposes) {
                    if (ProposerList.get(j).getName().equals(name)) {
                        ProposerList.get(j).setPreferences(preferences);
                        break;
                    }
                } else {
                    if (AcceptorList.get(j).getName().equals(name)) {
                        AcceptorList.get(j).setPreferences(preferences);
                        break;
                    } 
                }
            }
        }
        for (int i = 0; i < numCouples; i++) {
            System.out.println(ProposerList.get(i).getName());
            System.out.println(ProposerList.get(i).outputPreferences());
            System.out.println(AcceptorList.get(i).getName());
            System.out.println(AcceptorList.get(i).outputPreferences());
            Solution.add(ProposerList.get(i));
        }

        //Proposal Loop
        LinkedList<Proposer> unmatchedProposers = problem.getUnmatchedProposers(ProposerList);
        Acceptor a;
        while (!unmatchedProposers.isEmpty()) {
            for (Proposer p : unmatchedProposers) {
                a = (Acceptor) p.nextPref();
                if (a.isPreferable(p)) {
                    if (a.isEngaged) {
                        a.getPartner().removePartner();
                    }
                    a.setPartner(p);
                    a.isEngaged = true;
                    p.setPartner(a);
                    p.isEngaged = true;
                }
            }
            unmatchedProposers = problem.getUnmatchedProposers(ProposerList);
        }
    }

    private LinkedList stringToAcceptorLinkedList(String s, LinkedList<Acceptor> list) {
        LinkedList<Person> preferences = new LinkedList<>();
        String[] rawPreferences = s.split("\\s+");
        for (int i = 0; i < rawPreferences.length; i++) {
            for (int j = 0; j < numCouples; j++) {
                Person p = list.get(j);
                if (p.getName().equals(rawPreferences[i])) {
                    preferences.add(p);
                    break;
                }
            }
        }
        return preferences;
    }

    private LinkedList stringToProposerLinkedList(String s, LinkedList<Proposer> list) {
        LinkedList<Person> preferences = new LinkedList<>();
        String[] rawPreferences = s.split("\\s+");
        for (int i = 0; i < rawPreferences.length; i++) {
            for (int j = 0; j < numCouples; j++) {
                Person p = list.get(j);
                if (p.getName().equals(rawPreferences[i])) {
                    preferences.add(p);
                    break;
                }
            }
        }
        return preferences;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
