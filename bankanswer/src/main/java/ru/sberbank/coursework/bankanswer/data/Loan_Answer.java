package ru.sberbank.coursework.bankanswer.data;

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
    private Date dateStart;
    private Date dateEnd;
}
