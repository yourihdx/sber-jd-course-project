package ru.sberbank.coursework.schedule.model;

import java.math.BigDecimal;

public class CreditMainParam {
    private BigDecimal totalPercent;
    private String monthPayment;

    public CreditMainParam(BigDecimal totalPercent, String monthPayment) {
        this.totalPercent = totalPercent;
        this.monthPayment = monthPayment;
    }

    public BigDecimal getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(BigDecimal totalPercent) {
        this.totalPercent = totalPercent;
    }

    public String getMonthPayment() {
        return monthPayment;
    }

    public void setMonthPayment(String monthPayment) {
        this.monthPayment = monthPayment;
    }
}
