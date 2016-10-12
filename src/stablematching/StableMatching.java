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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class StableMatching extends Application {
    
    protected LinkedList<Proposer> ProposerList = new LinkedList<>();
    protected LinkedList<Acceptor> AcceptorList = new LinkedList<>();
    protected LinkedList<Pair<Proposer, Acceptor>> Solution = new LinkedList<>();
    Problem problem = new Problem(ProposerList, AcceptorList, Solution);
    int numCouples;

    @Override
    public void start(Stage primaryStage) {
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

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
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
        for (int i = 0; i < numCouples; i++) {
            String name = fileInput.nextLine();
            LinkedList preferences = stringToLinkedList(fileInput.nextLine());
            for (int j = 0; j < numCouples; j++) {
                if (ProposerList.get(j).getName().equals(name)) {
                    ProposerList.get(j).setPreferences(preferences);
                    break;
                }
            }
        }
        for (int i = 0; i < numCouples; i++) {
            String name = fileInput.nextLine();
            LinkedList preferences = stringToLinkedList(fileInput.nextLine());
            for (int j = 0; j < numCouples; j++) {
                if (AcceptorList.get(j).getName().equals(name)) {
                    AcceptorList.get(j).setPreferences(preferences);
                    break;
                }
            }
        }
        for (int i = 0; i < numCouples; i++) {
            System.out.println(ProposerList.get(i).getName());
            System.out.println(AcceptorList.get(i).getName());
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
    
    private LinkedList stringToLinkedList(String s) {
        LinkedList<Person> preferences = new LinkedList<>();
        String[] rawPreferences = s.split("\\s+");
        for (int i = 0; i < rawPreferences.length; i++) {
            for (int j = 0; j < numCouples; j++) {
                if (ProposerList.get(j).getName().equals(rawPreferences[i])) {
                    preferences.add(ProposerList.get(j));
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
