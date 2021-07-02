package ru.sberbank.coursework.demo.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "agreement")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;

    @Column(name = "bank_id")
    private int bankId;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "loan_id")
    private int loanId;

    @Column(name = "loan_sum")
    private BigDecimal loanSum;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "period")
    private int period;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Agreement() {
    }

    public Agreement(int bankId, int clientId, int loanId, BigDecimal loanSum, LocalDate startDate, int period) {
        this.bankId = bankId;
        this.clientId = clientId;
        this.loanId = loanId;
        this.loanSum = loanSum;
        this.startDate = startDate;
        this.period = period;
    }

    public int getId() {
        return id;
    }

    public int getBankId() {
        return bankId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getLoanId() {
        return loanId;
    }

    public BigDecimal getLoanSum() {
        return loanSum;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getPeriod() {
        return period;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public void setLoanSum(BigDecimal loanSum) {
        this.loanSum = loanSum;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(this.getId()).append(" period: ").append(this.getPeriod()).append("\n");
        sb.append("client_id: ").append(this.getClientId()).append(" bank_id: ").append(this.getBankId()).append("\n");
        sb.append("loan sum: ").append(this.getLoanSum()).append(" start date: ").append(this.getStartDate().toString()).append("\n");
        sb.append("period: ").append(this.getPeriod()).append(" is deleted: ").append(this.getIsDeleted()).append("\n");

        return sb.toString();
    }
}
