package ru.sberbank.coursework.demo.pojo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "loan_offer")
public class LoanOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "loan_id")
    private int loanId;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "product_type_id")
    private int productTypeId; // дифференцированный / аннуитетный

    @Column(name = "bank_id")
    private int bankId;

    @Column(name = "credit_sum")
    private BigDecimal creditSum;

    @Column(name = "period")
    private int period;

    @Column(name = "percent_rate")
    private double percent;

    @Column(name = "payment_day")
    private int paymentDay;

    @Column(name = "payment")
    private BigDecimal payment;

    @Column(name = "principal_payment_amount")
    private BigDecimal principalPaymentAmount;

    @Column(name = "percent_payment_amount")
    private BigDecimal percentPaymentAmount;

    @Column(name = "status")
    private int status;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public LoanOffer() {
    }

    public LoanOffer(int clientId, int loanId, int productTypeId, int bankId, BigDecimal creditSum,
                     int period, double percent, int paymentDay,
             BigDecimal payment, BigDecimal principalPaymentAmount, BigDecimal percentPaymentAmount, int status) {

        this.loanId = loanId;
        this.clientId = clientId;
        this.productTypeId = productTypeId;
        this.bankId = bankId;
        this.period = period;
        this.percent = percent;
        this.creditSum = creditSum;
        this.paymentDay = paymentDay;
        this.payment = payment;
        this.principalPaymentAmount = principalPaymentAmount;
        this.percentPaymentAmount = percentPaymentAmount;
        this.status = status;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public int getBankId() {
        return bankId;
    }

    public BigDecimal getCreditSum() {
        return creditSum;
    }

    public int getPaymentDay() {
        return paymentDay;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public BigDecimal getPrincipalPaymentAmount() {
        return principalPaymentAmount;
    }

    public BigDecimal getPercentPaymentAmount() {
        return percentPaymentAmount;
    }

    public int getStatus() {
        return status;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setCreditSum(BigDecimal creditSum) {
        this.creditSum = creditSum;
    }

    public void setPaymentDay(int paymentDay) {
        this.paymentDay = paymentDay;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public void setPrincipalPaymentAmount(BigDecimal principalPaymentAmount) {
        this.principalPaymentAmount = principalPaymentAmount;
    }

    public void setPercentPaymentAmount(BigDecimal percentPaymentAmount) {
        this.percentPaymentAmount = percentPaymentAmount;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: ").append(this.getId()).append(" loanId: ").append(this.getLoanId());
                sb.append(" client_id: ").append(this.getClientId()) .append("\n");
        sb.append("product_type_id: ").append(this.getProductTypeId()).append(" bank_id: ").append(this.getBankId()).append("\n");
        sb.append("credit sum: ").append(this.getCreditSum()).append(" payment_day: ").append(this.getPaymentDay()).append("\n");
        sb.append("payment: ").append(this.getPayment()).append(" principal payment amount: ").append(this.getPrincipalPaymentAmount()).append("\n");
        sb.append("period: ").append(this.getPeriod());
        sb.append(" percent: ").append(this.getPercent()).append(" status: ").append(this.getStatus()).append("\n");
        sb.append("is deleted: ").append(this.isDeleted).append("\n");

        return sb.toString();
    }
}
