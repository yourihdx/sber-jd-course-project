package ru.sberbank.coursework.schedule.model;


import java.math.MathContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class Schedule {
    private BigDecimal percentRate;
    private int creditTerm;
    private BigDecimal creditAmount;
    private boolean isAnnuityPayment;
    private LocalDateTime begin;
    private List<MonthPay> payments = new ArrayList<>();

    /**
     * Конструктор:
     *
     * @param creditAmount Сумма кредита
     * @param creditTerm Срок кредита в месяцах
     * @param percentRate Процентная ставка (годовых)
     * @param isAnnuityPayment при значении true строит график аннуитетных платежей. При
     * значении false строит график дифференцированных платежей
     */
    public Schedule(BigDecimal creditAmount, int creditTerm, BigDecimal percentRate, boolean isAnnuityPayment){
        this.creditAmount = creditAmount;
        this.creditTerm = creditTerm;
        this.percentRate = percentRate;
        this.isAnnuityPayment = isAnnuityPayment;
        this.begin = LocalDateTime.now();
        calculateSchedule();
    }

    public Schedule(GetSchedule getSchedule){
        this.creditAmount = getSchedule.getCreditAmount();
        this.creditTerm = getSchedule.getCreditTerm();
        this.percentRate = getSchedule.getPercentRate();
        this.isAnnuityPayment = getSchedule.isAnnuityPayment();
        this.begin = LocalDateTime.now();
        calculateSchedule();
    }

    /**
     *
     * @return сумму переплаты по кредиту
     */
    public BigDecimal calculateTotalPercent(){
        BigDecimal totalPercent = new BigDecimal(0);
        for(MonthPay payment : payments){
            Double percent = Double.valueOf(payment.getPercent());
            totalPercent = totalPercent.add(BigDecimal.valueOf(percent));
        }
        return totalPercent;
    }

    private void calculateSchedule(){
        if(isAnnuityPayment){
            int negateTerm = creditTerm * (-1);
            BigDecimal payment = creditAmount
                    .multiply(
                            percentRate.divide(new BigDecimal("1200"), 10, BigDecimal.ROUND_HALF_EVEN))
                    .divide(new BigDecimal("1").subtract(
                            new BigDecimal("1").add(
                                    percentRate.divide(new BigDecimal("1200"), 10, BigDecimal.ROUND_HALF_EVEN))
                                    .pow(negateTerm, MathContext.DECIMAL64)
                            )
                            ,10, BigDecimal.ROUND_HALF_EVEN
                    ).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_EVEN);

            LocalDateTime anotherMonths = begin;
            BigDecimal balance = creditAmount;
            for (int i = 0; i < creditTerm; i++){
                Duration yearDuration = Duration.between(anotherMonths, anotherMonths.plusYears(1));
                long yearDays = yearDuration.toDays();
                Duration duration =  Duration.between(anotherMonths, anotherMonths.plusMonths(1));
                long seconds = duration.getSeconds();
                long days = seconds / 86400;

                BigDecimal percentPay = percentRate
                        .divide(new BigDecimal("100"), 20, BigDecimal.ROUND_HALF_EVEN)
                        .divide(new BigDecimal(yearDays), 20, BigDecimal.ROUND_HALF_EVEN)
                        .multiply(new BigDecimal(days))
                        .multiply(balance)
                        .divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_EVEN);
                BigDecimal principal = payment.subtract(percentPay);
                if(i == creditTerm - 1){
                    principal = balance;
                }
                balance = balance.subtract(principal);
                anotherMonths = anotherMonths.plusMonths(1);

                payment = percentPay.add(principal);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String newDate = anotherMonths.format(formatter);
                MonthPay monthPay = new MonthPay(
                        newDate,
                        principal.toString(),
                        percentPay.toString(),
                        payment.toString(),
                        balance.toString());
                this.payments.add(monthPay);
            }
        }

        else {
            BigDecimal principal = creditAmount
                    .divide(new BigDecimal(creditTerm), 2, BigDecimal.ROUND_HALF_EVEN);
            LocalDateTime anotherMonths = begin;
            BigDecimal balance = creditAmount;
            for (int i = 0; i < creditTerm; i++){
                Duration yearDuration = Duration.between(anotherMonths, anotherMonths.plusYears(1));
                long yearDays = yearDuration.toDays();
                Duration duration =  Duration.between(anotherMonths, anotherMonths.plusMonths(1));
                long seconds = duration.getSeconds();
                long days = seconds / 86400;

                BigDecimal percentPay = percentRate
                        .divide(new BigDecimal("100"), 20, BigDecimal.ROUND_HALF_EVEN)
                        .divide(new BigDecimal(yearDays), 20, BigDecimal.ROUND_HALF_EVEN)
                        .multiply(new BigDecimal(days))
                        .multiply(balance)
                        .divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_EVEN);

                if(i == creditTerm - 1){
                    principal = balance;
                }
                balance = balance.subtract(principal);
                anotherMonths = anotherMonths.plusMonths(1);

                BigDecimal payment = percentPay.add(principal);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String newDate = anotherMonths.format(formatter);

                MonthPay monthPay = new MonthPay(
                        newDate,
                        principal.toString(),
                        percentPay.toString(),
                        payment.toString(),
                        balance.toString());
                this.payments.add(monthPay);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ Payment Schedule:");   //for client: " + client
        sb.append("\n Credit amount: " + creditAmount + ";");
        sb.append("\n Credit term: " + creditTerm + " months;");
        sb.append("\n Percent Rate: " + percentRate + "%;");
        sb.append("\n Total percent payment: " + calculateTotalPercent());
        sb.append("\n Payments: ");
        if(isAnnuityPayment){
            sb.append("annuity;");
        }else{
            sb.append("differentiated;");
        }
        sb.append("\n payments: \n");
        for(MonthPay monthPay : payments){
            sb.append(monthPay + "\n");

        }

        return sb.toString();
    }


    public BigDecimal getPercentRate() {
        return percentRate;
    }

    public void setPercentRate(BigDecimal percentRate) {
        this.percentRate = percentRate;
    }

    public int getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(int creditTerm) {
        this.creditTerm = creditTerm;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public boolean isAnnuityPayment() {
        return isAnnuityPayment;
    }

    public void setAnnuityPayment(boolean annuityPayment) {
        isAnnuityPayment = annuityPayment;
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public List<MonthPay> getPayments() {
        return payments;
    }

    public void setPayments(List<MonthPay> payments) {
        this.payments = payments;
    }
}
