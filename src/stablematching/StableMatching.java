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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class StableMatching extends Application {
    
    protected TableView<Pair<Person, Person>> TABLE = new TableView<>();
    protected LinkedList<Person> ProposerList = new LinkedList<>();
    protected LinkedList<Person> AcceptorList = new LinkedList<>();
    protected ObservableList<Pair<Person, Person>> Solution = FXCollections.observableArrayList();
    int numCouples;

    @Override
    public void start(Stage primaryStage) {
        updateTable();
        Button btn = new Button();
        btn.setText("Output People");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("List of People");
                try {
                    startProblem();
                } catch(Exception e) {
                }
            }
        });

        BorderPane root = new BorderPane();
        root.setTop(btn);
        root.setCenter(TABLE);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void updateTable() {
        TableColumn acceptors;
        TableColumn proposers;
        
        TABLE.getColumns().clear();
        acceptors = new TableColumn("Name");
        acceptors.setCellValueFactory(new PropertyValueFactory<>("name"));
        proposers = new TableColumn("Name");
        proposers.setCellValueFactory(new PropertyValueFactory<>("name"));

        TABLE.setItems(Solution);
        TABLE.getColumns().addAll(acceptors, proposers);
        TABLE.setPlaceholder(new Label("No entries found"));
    }
    
    public void startProblem() throws Exception {
        File f = new File("preferences.txt");
        Scanner fileInput = new Scanner(f);
        numCouples = fileInput.nextInt();
        fileInput.nextLine();
        for (int i = 0; i < numCouples*2; i=i+2) {
            String name = fileInput.nextLine();
            fileInput.nextLine();
            //LinkedList preferences = stringToLinkedList(fileInput.nextLine());
            Person p = new Proposer(name);
            ProposerList.add(p);
        }
        for (int i = 0; i < numCouples*2; i=i+2) {
            String name = fileInput.nextLine();
            fileInput.nextLine();
            //LinkedList preferences = stringToLinkedList(fileInput.nextLine());
            Person p = new Acceptor(name);
            AcceptorList.add(p);
        }
        fileInput = new Scanner(f);
        fileInput.nextLine();
        for (int i = 0; i < numCouples; i++) {
            String name = fileInput.nextLine();
            LinkedList preferences = stringToLinkedList(fileInput.nextLine(), AcceptorList);
            for (int j = 0; j < numCouples; j++) {
                if (ProposerList.get(j).getName().equals(name)) {
                    ProposerList.get(j).setPreferences(preferences);
                    break;
                }
            }
        }
        for (int i = 0; i < numCouples; i++) {
            String name = fileInput.nextLine();
            LinkedList preferences = stringToLinkedList(fileInput.nextLine(), ProposerList);
            for (int j = 0; j < numCouples; j++) {
                if (AcceptorList.get(j).getName().equals(name)) {
                    AcceptorList.get(j).setPreferences(preferences);
                    break;
                }
            }
        }
        for (int i = 0; i < numCouples; i++) {
            System.out.println(ProposerList.get(i).getName());
            System.out.println(ProposerList.get(i).outputPreferences());
            System.out.println(AcceptorList.get(i).getName());
            System.out.println(AcceptorList.get(i).outputPreferences());
        }
    }
    
    private LinkedList stringToLinkedList(String s, LinkedList<Person> list) {
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
