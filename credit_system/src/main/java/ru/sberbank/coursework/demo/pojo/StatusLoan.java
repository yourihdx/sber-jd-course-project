package ru.sberbank.coursework.demo.pojo;

import javax.persistence.*;

@Entity
@Table(name = "dict_loan_offer_status")
public class StatusLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "short_description")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
