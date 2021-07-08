package ru.sberbank.coursework.schedule.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GetSchedule {
    private BigDecimal creditAmount;
    private BigDecimal percentRate;
    private int creditTerm;
    private boolean annuityPayment;

    public GetSchedule(){}
    public GetSchedule(BigDecimal creditAmount, BigDecimal percentRate, int creditTerm, boolean annuityPayment) {
        this.creditAmount = creditAmount;
        this.percentRate = percentRate;
        this.creditTerm = creditTerm;
        this.annuityPayment = annuityPayment;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
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

    public boolean isAnnuityPayment() {
        return annuityPayment;
    }

    public void setAnnuityPayment(boolean annuityPayment) {
        this.annuityPayment = annuityPayment;
    }
}
