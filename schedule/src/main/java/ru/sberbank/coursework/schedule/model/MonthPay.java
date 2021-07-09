package ru.sberbank.coursework.schedule.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MonthPay {
    private String paymentDate;
    private String principal;
    private String percent;
    private String payment;
    private String balance;

    public MonthPay(String paymentDate, String principal, String percent, String payment, String balance) {
        this.paymentDate = paymentDate;
        this.principal = principal;
        this.percent = percent;
        this.payment = payment;
        this.balance = balance;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "MonthPay{" +
                "paymentDate='" + paymentDate + '\'' +
                ", principal='" + principal + '\'' +
                ", percent='" + percent + '\'' +
                ", payment='" + payment + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
