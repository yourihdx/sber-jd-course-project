package ru.sberbank.coursework.bank_answer.data;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {
    private double max_sum;
    private int max_period;
    private double min_percent_rate;
    private double max_percent_rate;
    private String product_type;
}
