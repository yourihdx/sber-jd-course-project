package ru.sberbank.coursework.bankanswer.data;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan_request {
    private String id;
    private String bank;
    private Client client;
    private Loan loan;
    private Loan_Offer loan_offer;
}
