package calculations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Calculations {
    double sum, percentage, monthlyPayment, credit, paid, interest, sumEntered, postponedPercentage;
    int term, monthCount, postponeStart, postponeTime;

    public ObservableList<Calculations> payments;

    //JEI NERA ATIDEJIMO
    public Calculations(double sum, int term, double percentage, double paid, int monthCount){
        this.sum = sum - paid;
        this.term = term;
        this.percentage = percentage / 100;
        this.paid = paid;
        this.monthCount = monthCount;

        this.sumEntered = sum;

        this.postponeStart = 0;
        this.postponeTime = 0;
        this.postponedPercentage = percentage / 100;

        payments = FXCollections.observableArrayList();
    }

    //JEI YRA ATIDEJIMAS
    public Calculations(double sum, int term, double percentage, double paid, int monthCount, int postponeStart, int postponeTime, double postponedPercentage){
        this.sum = sum - paid;
        this.term = term;
        this.percentage = percentage / 100;
        this.paid = paid;
        this.monthCount = monthCount;

        this.postponeStart = postponeStart;
        this.postponeTime = postponeTime;
        this.postponedPercentage = postponedPercentage / 100;
    }

    public ObservableList<Calculations> getPayments() {
        return payments;
    }

    public int getMonthCount(){
        return monthCount;
    }

    //getter
    public double getSum() {
        return sum;
    }

    //setter
    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getPostponedPercentage() {
        return postponedPercentage;
    }

    public void setPostponedPercentage(double postponedPercentage) {
        this.postponedPercentage = postponedPercentage;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public double getCredit() {
        return credit;
    }

    public double getInterest() {
        return interest;
    }

    public double getSumEntered() {
        return sumEntered;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

    public int getPostponeStart() {
        return postponeStart;
    }

    public void setPostponeStart(int postponeStart) {
        this.postponeStart = postponeStart;
    }

    public int getPostponeTime() {
        return postponeTime;
    }

    public void setPostponeTime(int postponeTime) {
        this.postponeTime = postponeTime;
    }
}
