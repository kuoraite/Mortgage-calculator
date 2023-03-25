package com.example.lab2;

import calculations.Annuity;
import calculations.Calculations;
import calculations.Linear;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Table {

    double sum, percentage, paid, postponedPercentage;
    int term, monthCount, postponeStart, postponeTime;
    int start, end;
    private String loan_type;
    Linear linear;
    Annuity annuity;

    public Table(double sum, double percentage, int term, String loan_type){
        this.sum = sum;
        this.percentage = percentage;
        this.term = term;
        this.loan_type = loan_type;
    }

    public Table(double sum, int term, double percentage, double paid, int monthCount, int postponeStart, int postponeTime, double postponedPercentage, String loan_type){
        this.sum = sum;
        this.percentage = percentage;
        this.term = term;
        this.paid = paid;
        this.monthCount = monthCount;
        this.postponeStart = postponeStart;
        this.postponeTime = postponeTime;
        this.postponedPercentage = postponedPercentage;

        this.loan_type = loan_type;
    }

    public void newTable(Stage stage, Scene scene){

        TableView<Calculations> table = new TableView();

        stage.setWidth(600);
        stage.setHeight(650);

        TableColumn<Calculations, String> monthCountColumn = new TableColumn<>("Month");
        monthCountColumn.setCellValueFactory(new PropertyValueFactory<Calculations, String>("monthCount"));

        TableColumn sumColumn = new TableColumn<>("Left to pay: ");
        sumColumn.setMinWidth(150);
        sumColumn.setCellValueFactory(new PropertyValueFactory<Calculations, String>("sum"));

        TableColumn<Calculations, String> monthlyPaymentColumn = new TableColumn<>("Monthly payment");
        monthlyPaymentColumn.setMinWidth(150);
        monthlyPaymentColumn.setCellValueFactory(new PropertyValueFactory<Calculations, String>("monthlyPayment"));

        TableColumn<Calculations, String> interestColumn = new TableColumn<>("Interest");
        interestColumn.setCellValueFactory(new PropertyValueFactory<Calculations, String>("interest"));

        TableColumn<Calculations, String> creditColumn = new TableColumn<>("Credit");
        creditColumn.setCellValueFactory(new PropertyValueFactory<Calculations, String>("credit"));

        table.getColumns().addAll(monthCountColumn, sumColumn, monthlyPaymentColumn, interestColumn, creditColumn);

        ChoiceBox<Integer> choiceBoxStart = new ChoiceBox<>();
        ChoiceBox<Integer> choiceBoxEnd = new ChoiceBox<>();

        choiceBoxStart.setValue(1);
        choiceBoxEnd.setValue(term);

        for(int i = 1; i <= term; ++i){
            choiceBoxStart.getItems().addAll(i);
            choiceBoxEnd.getItems().addAll(i);
        }

        Button okButton = new Button("Continue");
        okButton.setTranslateX(80);
        okButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent actionEvent) {

                start = getChoice(choiceBoxStart);
                end = getChoice(choiceBoxEnd);
                table.setItems(getData(start, end));
            }
        });

        Button backButton = new Button("Cancel");
        backButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(scene);
            }
        });

        Button toFile = new Button("Export to .txt file");
        toFile.setTranslateX(190);
        toFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                toFile(start, end);
            }
        });

        Label periodLabel = new Label("Choose a time period:");
        Label startLabel = new Label("From:");
        Label endLabel = new Label("To:");

        final VBox vbox = new VBox(backButton, periodLabel, startLabel, choiceBoxStart, endLabel, choiceBoxEnd, okButton, toFile, table);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        Scene tableView = new Scene(vbox);
        stage.setTitle("Table");

        stage.setScene(tableView);
        stage.show();
    }

    public ObservableList<Calculations> getData(int start, int end){
        double paid = 0;

        ObservableList<Calculations> calculations = FXCollections.observableArrayList();

        for(int monthCount = 1; monthCount <= term; monthCount++){
            if(loan_type == "Annuity"){
                Annuity annuity;

                if(main.postpone == true){
                    annuity = new Annuity(sum, term, percentage, paid, monthCount, postponeStart, postponeTime, postponedPercentage);
                }
                else{
                    annuity = new Annuity(sum, term, percentage, paid, monthCount);
                }

                if(monthCount >= start && end >= monthCount){
                    calculations.add(annuity);
                }
                paid = paid + annuity.getCredit();
            }
            else if(loan_type == "Linear"){
                Linear linear;

                if(main.postpone){
                    linear = new Linear(sum, term, percentage, paid, monthCount, postponeStart, postponeTime, postponedPercentage);
                }
                else{
                    linear = new Linear(sum, term, percentage, paid, monthCount);
                }

                if(monthCount >= start && end >= monthCount){
                    calculations.add(linear);
                }
                paid += linear.getCredit();
            }
        }

        return calculations;
    }

    public String toString(){
        return "Left to pay: " + sum + " ";
    }

    public void toFile(int start, int end) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text documents (*.txt)", "*.txt"));

        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);

        try {
            FileWriter fileWriter = new FileWriter(file);

            ObservableList <Calculations> calculations = getData(start, end);

            PrintWriter pw = new PrintWriter(fileWriter);

            for(int i = 0; i <= end - start; i++){
                pw.print("Month: " + calculations.get(i).getMonthCount() + " " + "Left to pay: " + calculations.get(i).getSum() + " " + "Credit: " + calculations.get(i).getCredit() + " " + "Interest: " + calculations.get(i).getInterest() + "\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer getChoice(ChoiceBox<Integer> choiceBox){
        int num = choiceBox.getValue();
        return num;
    }
}


