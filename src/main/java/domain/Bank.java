package domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "bank")
@Data
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "bank_name")
    private String bankName;
}
