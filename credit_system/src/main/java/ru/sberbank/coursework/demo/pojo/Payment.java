package ru.sberbank.coursework.demo.pojo;

import javax.persistence.*;

@Entity
@Table(name = "dict_product_type")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "short_description")
    private String name;


    public Payment() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
