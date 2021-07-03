package ru.sberbank.coursework.demo;

import java.time.LocalDate;

public class ClientData {
    String login;
    String password;
    long id;
    String fio;
    String birthDate;
    String email;
    String phone;
    String passport;

    public ClientData() {
    }

    public ClientData(long id, String login, String password, String fio, String birthDate, String email, String phone, String passport) {
        this.login = login;
        this.password = password;
        this.id = id;
        this.fio = fio;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.passport = passport;
    }

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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "ClientData{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", fio='" + fio + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
}
