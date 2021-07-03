package ru.sberbank.coursework.demo.pojo;

import java.math.BigDecimal;

public interface LoanList {
    String getBankName();
    int getBankId();
    int getId();
    double getLimit();
    int getPeriod();
    double getPercent();
    String getPayment();
    int getPaymentId();
}
