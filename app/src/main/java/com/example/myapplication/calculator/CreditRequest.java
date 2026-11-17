package com.example.myapplication.calculator;

import java.math.BigDecimal;

public class CreditRequest {
    private BigDecimal amount;
    private int termMonths;
    private BigDecimal annualRate;

    private PaymentType paymentType;

    public CreditRequest(BigDecimal amount,int termMonths,BigDecimal annualRate,PaymentType paymentType){
        this.amount=amount;
        this.termMonths=termMonths;
        this.annualRate=annualRate;
        this.paymentType=paymentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getTermMonths() {
        return termMonths;
    }

    public BigDecimal getAnnualRate() {
        return annualRate;
    }

    public PaymentType getPaymentType(){return paymentType;}
}
