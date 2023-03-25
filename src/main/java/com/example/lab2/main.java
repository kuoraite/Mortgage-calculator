//Simona Kuoraitė, 5 grupė

package com.example.lab2;

import calculations.Annuity;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    double sum, percentage, postponed_percentage, paid;
    int term, monthCount, postpone_start, postpone_time;
    String loan_type;
    public static boolean postpone = false;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Loan counter");
        stage.setResizable(false);

        Group root = new Group();
        Scene scene = new Scene(root, 650, 400);

        Label label_sum = new Label("Loan size: ");
        label_sum.setTranslateX(60);
        label_sum.setTranslateY(40);

        Label label_term = new Label("Term: ");
        label_term.setTranslateX(60);
        label_term.setTranslateY(100);

        Label label_year = new Label("Year: ");
        label_year.setTranslateX(60);
        label_year.setTranslateY(120);

        Label label_month = new Label("Month: ");
        label_month.setTranslateX(130);
        label_month.setTranslateY(120);

        Label label_percentage = new Label("Percentage (every year): ");
        label_percentage.setTranslateX(60);
        label_percentage.setTranslateY(180);

        Label label_choiceBox = new Label("Loan type: ");
        label_choiceBox.setTranslateX(250);
        label_choiceBox.setTranslateY(40);

        Label label_postpone_start = new Label("Postpone start: ");
        label_postpone_start.setTranslateX(250);
        label_postpone_start.setTranslateY(100);

        Label label_postpone_time = new Label("Postpone period: ");
        label_postpone_time.setTranslateX(250);
        label_postpone_time.setTranslateY(150);

        Label label_postpone_percentage = new Label("Postpone percentage: ");
        label_postpone_percentage.setTranslateX(250);
        label_postpone_percentage.setTranslateY(200);

        root.getChildren().addAll(label_sum, label_term, label_year, label_month, label_percentage, label_postpone_start, label_postpone_time, label_postpone_percentage, label_choiceBox);

        TextField textField_sum = new TextField();
        textField_sum.setTranslateX(60);
        textField_sum.setTranslateY(60);

        TextField textField_year = new TextField();
        textField_year.setTranslateX(60);
        textField_year.setTranslateY(140);
        textField_year.setMaxSize(50, 20);

        TextField textField_month = new TextField();
        textField_month.setTranslateX(130);
        textField_month.setTranslateY(140);
        textField_month.setMaxSize(70, 20);

        TextField textField_percentage = new TextField();
        textField_percentage.setTranslateX(60);
        textField_percentage.setTranslateY(200);

        TextField textField_postponed_sum = new TextField();
        textField_postponed_sum.setTranslateX(250);
        textField_postponed_sum.setTranslateY(120);

        TextField textField_postponed_time = new TextField();
        textField_postponed_time.setTranslateX(250);
        textField_postponed_time.setTranslateY(170);

        TextField textField_postponed_percentage = new TextField();
        textField_postponed_percentage.setTranslateX(250);
        textField_postponed_percentage.setTranslateY(220);

        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setLayoutX(250);
        choiceBox.setLayoutY(60);
        choiceBox.getItems().addAll("Linear", "Annuity");
        choiceBox.getSelectionModel().select(0);

        Button buttonPostpone = new Button("Postpone");
        buttonPostpone.setTranslateX(250);
        buttonPostpone.setTranslateY(300);

        Button button_graph = new Button("Graph");
        button_graph.setTranslateX(50);
        button_graph.setTranslateY(300);

        Button button_table = new Button("Table");
        button_table.setTranslateX(120);
        button_table.setTranslateY(300);

        root.getChildren().addAll(textField_sum, textField_year, textField_month, textField_percentage, textField_postponed_sum,
                textField_postponed_time, textField_postponed_percentage, buttonPostpone, button_graph, button_table,  choiceBox);

        buttonPostpone.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getPostponeInput(textField_postponed_sum, textField_postponed_time, textField_postponed_percentage);

                postpone = true;
            }
        });

        button_graph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getInput(textField_sum, textField_month, textField_year, textField_percentage);

                if(sum > 0 && percentage > 0 && term > 0){
                    Graph graph;
                    graph = new Graph(sum, term, percentage);

                    graph.newGraph(stage, scene);
                }
            }
        });

        button_table.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                getInput(textField_sum, textField_month, textField_year, textField_percentage);

                if(sum > 0 && percentage > 0 && term >0){
                    Table table;

                    if(postpone == true){
                        table = new Table(sum, term, percentage, paid,  monthCount, postpone_start, postpone_time, postponed_percentage, getLoanChoice(choiceBox));
                    }
                    else{
                        table = new Table(sum, percentage, term, getLoanChoice(choiceBox));

                    }

                    table.newTable(stage, scene);
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public String getLoanChoice(ChoiceBox<String> choiceBox){
        loan_type = choiceBox.getValue();

        return loan_type;
    }

    public void getPostponeInput(TextField textField_postpone_start, TextField textField_postpone_time, TextField textField_postponed_percentage){
        if(isNumber(textField_postpone_start)){
            postpone_start = Integer.parseInt(textField_postpone_start.getText());
        }
        if(isNumber(textField_postpone_time)){
            postpone_time = Integer.parseInt(textField_postpone_time.getText());
        }
        if(isNumber(textField_postponed_percentage)){
            postponed_percentage = Double.parseDouble(textField_postponed_percentage.getText());
        }
    }
    public void getInput(TextField textField_sum, TextField textField_month, TextField textField_year, TextField textField_percentage){
        if(isNumber(textField_sum)){
            sum = Double.parseDouble(textField_sum.getText());
        }
        if(isNumber(textField_month) && isNumber(textField_year)){
            term = Integer.parseInt(textField_year.getText()) * 12 + Integer.parseInt(textField_month.getText());
        }
        if(isNumber(textField_percentage)){
            percentage = Double.parseDouble(textField_percentage.getText());
        }
    }
    private boolean isNumber(TextField input){
        try{
            double data = Double.valueOf(input.getText());
            return true;

        }
        catch (NumberFormatException e){
            System.out.println("ERROR. Wrong symbols entered.");
            return false;
        }
    }

    public static void main(String[] args) {
        launch();
    }

}