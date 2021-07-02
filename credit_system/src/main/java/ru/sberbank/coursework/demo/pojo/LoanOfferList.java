package ru.sberbank.coursework.demo.pojo;

public interface LoanOfferList {
    String getBankName();
    int getBankId();
    int getId();
    double getLimit();
    int getPeriod();
    double getPercent();
    String getPayment();
    int getStatusId();
    String getStatus();
    boolean getInsurance();
}
