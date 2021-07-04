package ru.sberbank.coursework.bank_answer.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class AnswerData {
    private String id;
    private int res;
    private String comment;
}