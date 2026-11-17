package com.example.myapplication.calculator;

import java.math.BigDecimal;

public class Payment {
    private int month;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal totalPayment;
    private BigDecimal remainingDebt;

    public Payment(int month, BigDecimal principal, BigDecimal interest, BigDecimal totalPayment, BigDecimal remainingDebt) {
        this.month = month;
        this.principal = principal;
        this.interest = interest;
        this.totalPayment = totalPayment;
        this.remainingDebt = remainingDebt;
    }

    public int getMonth() {
        return month;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public BigDecimal getRemainingDebt() {
        return remainingDebt;
    }
}
