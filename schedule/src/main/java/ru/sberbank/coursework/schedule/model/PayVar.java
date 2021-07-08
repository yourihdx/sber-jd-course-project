package ru.sberbank.coursework.schedule.model;

public class PayVar {
    boolean isAnnuityPayment;
    String payName;

    public PayVar(boolean isAnnuityPayment, String payName) {
        this.isAnnuityPayment = isAnnuityPayment;
        this.payName = payName;
    }

    public boolean isAnnuityPayment() {
        return isAnnuityPayment;
    }

    public void setAnnuityPayment(boolean annuityPay) {
        isAnnuityPayment = annuityPay;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }
}
