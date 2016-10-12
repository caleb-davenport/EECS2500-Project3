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
    
    protected TableView<Pair<Proposer, Acceptor>> TABLE = new TableView<>();
    protected LinkedList<Proposer> ProposerList = new LinkedList<>();
    protected LinkedList<Acceptor> AcceptorList = new LinkedList<>();
    protected Problem problem;
    protected ObservableList<Pair<Proposer, Acceptor>> Solution = FXCollections.observableArrayList();
    int numCouples;

    public StableMatching() {
        this.problem = new Problem(ProposerList, AcceptorList, Solution);
    }

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
            Proposer p = new Proposer(name);
            ProposerList.add(p);
        }
        for (int i = 0; i < numCouples*2; i=i+2) {
            String name = fileInput.nextLine();
            fileInput.nextLine();
            //LinkedList preferences = stringToLinkedList(fileInput.nextLine());
            Acceptor p = new Acceptor(name);
            AcceptorList.add(p);
        }
        fileInput = new Scanner(f);
        fileInput.nextLine();
        for (int i = 0; i < numCouples; i++) {
            String name = fileInput.nextLine();
            LinkedList preferences = stringToAcceptorLinkedList(fileInput.nextLine(), AcceptorList);
            for (int j = 0; j < numCouples; j++) {
                if (ProposerList.get(j).getName().equals(name)) {
                    ProposerList.get(j).setPreferences(preferences);
                    break;
                }
            }
        }
        for (int i = 0; i < numCouples; i++) {
            String name = fileInput.nextLine();
            LinkedList preferences = stringToProposerLinkedList(fileInput.nextLine(), ProposerList);
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
 
        //Proposal Loop
        LinkedList<Proposer> unmatchedProposers = problem.getUnmatchedProposers(ProposerList);
        Acceptor a;
        while (!unmatchedProposers.isEmpty()) {
            for (Proposer p : ProposerList) {
                a = p.nextPref();
                if (a.isPreferable(p)) {
                    a.setPartner(p);
                    p.setPartner(a);
                } else {
                    p.setPartner(null);
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
