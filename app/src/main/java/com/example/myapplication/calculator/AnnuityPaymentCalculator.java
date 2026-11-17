package com.example.myapplication.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AnnuityPaymentCalculator implements PaymentCalculator {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    @Override
    public CreditSchedule calculate(CreditRequest request) {
        BigDecimal principal = request.getAmount();
        BigDecimal monthlyRate = request.getAnnualRate()
                .divide(BigDecimal.valueOf(12 * 100L), 12, ROUNDING);

        int months = request.getTermMonths();

        BigDecimal payment;

        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            payment = principal.divide(BigDecimal.valueOf(months), 12, ROUNDING);
        } else {
            double rate = monthlyRate.doubleValue();
            double coefficient = rate * Math.pow(1 + rate, months)
                    / (Math.pow(1 + rate, months) - 1);
            payment = principal.multiply(BigDecimal.valueOf(coefficient));
        }

        payment = money(payment);

        List<Payment> payments = new ArrayList<>();
        BigDecimal remainingDebt = principal;
        BigDecimal totalInterest = BigDecimal.ZERO;

        for (int month = 1; month <= months; month++) {
            BigDecimal interest = money(remainingDebt.multiply(monthlyRate));
            BigDecimal principalPart = money(payment.subtract(interest));

            if (month == months) {
                principalPart = remainingDebt;
                payment = money(principalPart.add(interest));
            }

            remainingDebt = money(remainingDebt.subtract(principalPart));
            totalInterest = totalInterest.add(interest);

            payments.add(new Payment(month, principalPart, interest, payment, remainingDebt));
        }

        return new CreditSchedule(
                money(totalInterest),
                money(payments.stream().map(Payment::getTotalPayment).reduce(BigDecimal.ZERO, BigDecimal::add)),
                payments
        );
    }

    private BigDecimal money(BigDecimal value) {
        return value.setScale(SCALE, ROUNDING);
    }
}