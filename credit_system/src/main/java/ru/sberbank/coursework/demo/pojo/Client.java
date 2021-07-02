package ru.sberbank.coursework.demo.pojo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name ="phone_number")
    private String phoneNumber;

    @Column(name ="email")
    private String eMail;

    @Column(name ="passport_series_num")
    private String passportSeriesNum;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Client() {
    }

    public Client(String fullName, LocalDate birthDate, String phoneNumber, String eMail,
                  String passportSeriesNum, String login, String password) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.login = login;
        this.password = password;
        this.passportSeriesNum = passportSeriesNum;
        this.isDeleted = false;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEMail() {
        return eMail;
    }

    public String getPassportSeriesNum() {
        return passportSeriesNum;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName.trim();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber.trim();
    }

    public void setEMail(String eMail) {
        this.eMail = eMail.trim().toLowerCase();
    }

    public void setPassportSeriesNum(String passportSeriesNum) {
        this.passportSeriesNum = passportSeriesNum;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setLogin(String login) { this.login = login; }

    public void setPassword(String password) { this.password = password; }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String geteMail() {
        return eMail;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.getId()).append(" ").append(this.getFullName()).append("\n");
        sb.append("Date of birth: ").append(this.getBirthDate().toString()).append(" email: ").append(this.getEMail());
        sb.append(" Passport: ").append(this.getPassportSeriesNum()).append(" phone: ").append(this.getPhoneNumber());

        return sb.toString();
    }
}
