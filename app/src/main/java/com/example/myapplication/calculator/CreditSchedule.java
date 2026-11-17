package com.example.myapplication.calculator;

import java.math.BigDecimal;
import java.util.List;

public class CreditSchedule {
    private BigDecimal totalInterest;
    private BigDecimal totalPayment;
    private List<Payment> payments;

    public CreditSchedule(BigDecimal totalInterest,BigDecimal totalPayment,
                          List<Payment>payments){
        this.totalInterest=totalInterest;
        this.totalPayment=totalPayment;
        this.payments=payments;
    }
    public BigDecimal getTotalInterest(){return totalInterest;}
    public BigDecimal getTotalPayment(){return totalPayment;}
    public List<Payment>getPayments(){return payments;}
}
