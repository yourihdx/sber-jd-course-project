package ru.sberbank.coursework.demo.data;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AnswerData {
    private String id;
    private int res;
    private String comment;
}