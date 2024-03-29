package ru.sberbank.coursework.demo.data;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    private String full_name;
    private String birth_date;
    private String phone_Number;
    private String email;
    private String passport;
}


