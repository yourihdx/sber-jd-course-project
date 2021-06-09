package ru.sberbank.coursework.schedule.model;

import java.math.BigDecimal;

public class CreditMainParam {
    private BigDecimal totalPercent;
    private BigDecimal monthPayment;

    public CreditMainParam(BigDecimal totalPercent, BigDecimal monthPayment) {
        this.totalPercent = totalPercent;
        this.monthPayment = monthPayment;
    }

    public BigDecimal getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(BigDecimal totalPercent) {
        this.totalPercent = totalPercent;
    }

    public BigDecimal getMonthPayment() {
        return monthPayment;
    }

    public void setMonthPayment(BigDecimal monthPayment) {
        this.monthPayment = monthPayment;
    }
}
