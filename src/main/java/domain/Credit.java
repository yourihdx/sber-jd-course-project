package domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "loan")
@Data
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "type_id")
    private int id;
    @Column(name = "bank_id")
    private int bank_id;
    @Column(name = "max_sum")
    private int maxSum;
    @Column(name = "max_period")
    private int maxPeriod;
    @Column(name = "percent_rate")
    private double percentRate;
    @Column(name = "is_deleted")
    private boolean isDeleted;

}
