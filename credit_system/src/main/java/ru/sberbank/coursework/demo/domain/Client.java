package ru.sberbank.coursework.demo.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Client {

    private int id;
    private String fullName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String email;
    private String passportSeriesNum;
    private boolean isDeleted;
}
