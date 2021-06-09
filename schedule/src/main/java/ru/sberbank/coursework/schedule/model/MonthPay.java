package ru.sberbank.coursework.schedule.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Класс для хранения данных о ежемесячном платеже.
 */
public class MonthPay {
    private LocalDateTime paymentDate;
    private BigDecimal principal;
    private BigDecimal percent;
    private BigDecimal payment;
    private BigDecimal balance;


    /**
     * Конструктор:
     * @param paymentDate - дата ежемесячного платежа
     * @param principal - сумма основного долга
     * @param percent - сумма процентов
     * @param payment - сумма платежа
     * @param balance - остаток основного долга
     */
    public MonthPay(LocalDateTime paymentDate, BigDecimal principal, BigDecimal percent, BigDecimal payment, BigDecimal balance) {
        this.paymentDate = paymentDate;
        this.principal = principal;
        this.percent = percent;
        this.payment = payment;
        this.balance = balance;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "[" +
                paymentDate.getDayOfMonth() + "." + paymentDate.getMonth() + "."
                + paymentDate.getYear() +
                ", principal=" + principal +
                ", percent=" + percent +
                ", payment=" + payment +
                ", balance=" + balance +
                ']';
    }
}
