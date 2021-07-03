package ru.sberbank.coursework.demo.data;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Loan_Offer {
    private double credit_sum;
    private int paymant_day;
    private double payment;

}
