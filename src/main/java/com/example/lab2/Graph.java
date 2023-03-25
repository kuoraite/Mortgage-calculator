package com.example.lab2;

import calculations.Annuity;
import calculations.Calculations;
import calculations.Linear;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Graph {
    int term, monthCount, postpone_start, postpone_time;
    double sum, percentage, paidAnnuity, paidLinear, postponed_percentage;

    public Graph(double sum, int term, double percentage){
        this.sum = sum;
        this.term = term;
        this.percentage = percentage;
    }

    public void newGraph(Stage stage, Scene scene){

        NumberAxis xAxis = new NumberAxis("Month", 0, term, 1);
        NumberAxis yAxis = new NumberAxis("Sum", 0, newMonthlyPayment() * 2, newMonthlyPayment() / 5);

        ObservableList<XYChart.Series> series = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> annuityList = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> linearList = FXCollections.observableArrayList();

        LineChart lineChart = new LineChart(xAxis, yAxis, series);

        series.addAll(new XYChart.Series("Annuity", annuityList), new XYChart.Series("Linear", linearList));

        Button backButton = new Button("Cancel");
        backButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(scene);
            }
        });

        final VBox vbox = new VBox(backButton);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        for(monthCount = 1; monthCount <= term; monthCount++){
            Annuity annuity;
            Linear linear;

            if(main.postpone){
                annuity = new Annuity(sum, term, percentage, paidAnnuity, monthCount, postpone_start, postpone_time, postponed_percentage);
                annuityList.add(new XYChart.Data(monthCount, annuity.getMonthlyPayment()));
                paidAnnuity += annuity.getCredit();

                linear = new Linear(sum, term, percentage, paidLinear, monthCount, postpone_start, postpone_time, postponed_percentage);
                linearList.add(new XYChart.Data(monthCount, linear.getMonthlyPayment()));
                paidLinear +=linear.getCredit();
            }
            else{
                annuity = new Annuity(sum, term, percentage, paidAnnuity, monthCount);
                annuityList.add(new XYChart.Data(monthCount, annuity.getMonthlyPayment()));
                paidAnnuity += annuity.getCredit();

                linear = new Linear(sum, term, percentage, paidLinear, monthCount);
                linearList.add(new XYChart.Data(monthCount, linear.getMonthlyPayment()));
                paidLinear += linear.getCredit();
            }
        }

        Group root = new Group(lineChart, backButton);
        Scene graphScene = new Scene(root, 600, 500);

        stage.setScene(graphScene);
        stage.show();
    }

    public double newMonthlyPayment(){
        double paid = 0;
        double monthlyPayment;

        Annuity annuity = new Annuity(sum, term, percentage, paid, monthCount);
        monthlyPayment = annuity.getMonthlyPayment();

        return monthlyPayment;
    }
}
