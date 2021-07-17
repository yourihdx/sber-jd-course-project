package ru.sberbank.coursework.demo.pojo;

import javax.persistence.*;

import lombok.*;


@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int id;

    @Column(name = "product_type_id")
    private int productTypeId; // дифференцированный / аннуитетный

    @Column(name = "bank_id")
    private int bankId;

    @Column(name = "max_sum")
    private double creditSum;

    @Column(name = "max_period")
    private int period;

    @Column(name = "min_percent_rate")
    private double percent;

    @Column(name = "max_percent_rate")
    private double max_percent;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_deleted")
    private boolean isDeleted;

/**
    public Loan() {
    }
*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public double getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(double creditSum) {
        this.creditSum = creditSum;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    /**
    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
//                    ", productTypeId=" + productTypeId +
                ", bankId=" + bankId +
                ", creditSum=" + creditSum +
                ", period=" + period +
                ", percent=" + percent +
                ", comment='" + comment + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
    */
}
