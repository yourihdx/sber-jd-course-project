package ru.sberbank.coursework.demo.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Credit {

    private int id;
    private int bankId;
    private BigDecimal maxSum;
    private BigDecimal maxPeriod;
    private BigDecimal percentRate;
    private boolean isDeleted;

}
