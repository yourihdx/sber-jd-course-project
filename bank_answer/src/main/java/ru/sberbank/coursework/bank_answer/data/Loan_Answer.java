package ru.sberbank.coursework.bank_answer.data;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Loan_Answer {
    private Loan_request loan_request;
    private int status;
    private String comment;
    private double percent_rate;
    private Date dateStart;
    private Date dateEnd;
    private String sender;
}
