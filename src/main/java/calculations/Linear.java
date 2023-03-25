package calculations;

import static java.lang.Math.pow;

public class Linear extends Calculations {

    double monthlyPayment;

    public Linear(double sum, int term, double percentage, double paid, int monthCount){
        super(sum, term, percentage, paid, monthCount);

        getMonthlyPayment();
    }

    public Linear(double sum, int term, double percentage, double paid, int monthCount, int postponeStart, int postponeTime, double postponedPercentage){
        super(sum, term, percentage, paid, monthCount, postponeStart, postponeTime, postponedPercentage);
    }

    public double getInterest(){
        if (monthCount >= postponeStart && monthCount < postponeStart + postponeTime){
            interest = sum * postponedPercentage / 12;
        }
        else{
            interest = sum * percentage / 12;
        }

        return Math.round(interest * 100.0) / 100.0;
    }
    public double getCredit(){
        if(monthCount >= postponeStart && monthCount < postponeStart + postponeTime){
            credit = 0;
        }
        else{
            credit = sum / (term - postponeTime);
        }
        return Math.round(credit * 100.0) / 100.0;
    }

    public double getMonthlyPayment() {
        monthlyPayment = getCredit() + getInterest();

        return Math.round(monthlyPayment * 100.0) / 100.0;
    }
}