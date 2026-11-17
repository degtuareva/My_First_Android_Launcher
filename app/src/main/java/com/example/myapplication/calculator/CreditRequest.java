package com.example.myapplication.calculator;

import java.math.BigDecimal;

public class CreditRequest {
    private BigDecimal amount;
    private int termMonths;
    private BigDecimal annualRate;

    public CreditRequest(BigDecimal amount,int termMonths,BigDecimal annualRate){
        this.amount=amount;
        this.termMonths=termMonths;
        this.annualRate=annualRate;
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
}
