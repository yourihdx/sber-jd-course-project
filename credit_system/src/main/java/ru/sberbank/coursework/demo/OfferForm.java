package ru.sberbank.coursework.demo;

;

public class OfferForm {
    // bank, limit, period, percent, id, insurance, approve

    String bank;
    int bankId;
    long limit;
    int period;
    double percent;
    long reqLimit;
    int reqPeriod;
    double reqPercent;
    long id;
    String payment;
    int paymentId;
    boolean insurance;
    int statusId;
    String status;
    boolean selected;

    public OfferForm() {};

    public OfferForm(String bank, int bankId, long limit, int period, double percent,
                     long reqLimit, int reqPeriod, double reqPercent, long id,
                     String payment, int paymentId, boolean insurance, int statusId, String status, boolean selected) {
        this.bank = bank;
        this.bankId = bankId;
        this.limit = limit;
        this.period = period;
        this.percent = percent;
        this.reqLimit = reqLimit;
        this.reqPeriod = reqPeriod;
        this.reqPercent = reqPercent;
        this.id = id;
        this.payment = payment;
        this.paymentId = paymentId;
        this.insurance = insurance;
        this.statusId = statusId;
        this.status = status;
        this.selected = selected;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getBankId() {
        return bankId;
    }

    public long getReqLimit() {
        return reqLimit;
    }

    public void setReqLimit(long reqLimit) {
        this.reqLimit = reqLimit;
    }

    public int getReqPeriod() {
        return reqPeriod;
    }

    public void setReqPeriod(int reqPeriod) {
        this.reqPeriod = reqPeriod;
    }

    public double getReqPercent() {
        return reqPercent;
    }

    public void setReqPercent(double reqPercent) {
        this.reqPercent = reqPercent;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public boolean isInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

public boolean isSelected() {
        return selected;
        }

public void setSelected(boolean selected) {
        this.selected = selected;
}

    @Override
    public String toString() {
        return "OfferForm{" +
                "bank='" + bank + '\'' +
                ", limit=" + limit +
                ", period=" + period +
                ", percent=" + percent +
                ", id=" + id +
                ", payment=" + payment +
                ", insurance=" + insurance +
                ", status=" + status +
                ", selected=" + selected +
                '}';
    }
}