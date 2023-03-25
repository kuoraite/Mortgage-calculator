package calculations;

import static java.lang.Math.pow;

public class Annuity extends Calculations {

    static double monthlyPayment;

    public Annuity(double sum, int term, double percentage, double paid, int monthCount){
        super(sum, term, percentage, paid, monthCount);

        getMonthlyPayment();
    }

    public Annuity(double sum, int term, double percentage, double paid, int monthCount, int postponeStart, int postponeTime, double postponedPercentage){
        super(sum, term, percentage, paid, monthCount, postponeStart, postponeTime, postponedPercentage);

        getMonthlyPayment();
    }

    public double getInterest(){
        if (monthCount >= postponeStart && monthCount < postponeStart + postponeTime){
            interest = sumEntered * postponedPercentage / 12;
        }
        else{
            interest = sumEntered * percentage / 12;
        }

        return Math.round(interest * 100.0) / 100.0;
    }
    public double getCredit(){
        if(monthCount >= postponeStart && monthCount < postponeStart + postponeTime){
            credit = 0;
        }
        else{
            credit = this.getMonthlyPayment() - getInterest();
        }
        return Math.round(credit * 100.0) / 100.0;
    }

    public double getMonthlyPayment(){

        monthlyPayment = ((sum - paid) * (percentage/12))/(1-(1/pow(1+percentage/12, (term-postponeTime))));

        return  Math.round(monthlyPayment * 100.0) / 100.0;
    }
}