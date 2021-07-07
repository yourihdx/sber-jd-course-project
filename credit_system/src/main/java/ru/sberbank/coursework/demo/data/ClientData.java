package ru.sberbank.coursework.demo.data;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientData {
    String login;
    String password;
    long id;
    String fio;
    String birthDate;
    String email;
    String phone;
    String passport;

    public ClientData(ClientData client) {
        this.login = client.getLogin();
        this.password = client.getPassword();
        this.id = client.getId();
        this.fio = client.getFio();
        this.birthDate = client.getBirthDate();
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.passport = client.getPassport();
    }
}
