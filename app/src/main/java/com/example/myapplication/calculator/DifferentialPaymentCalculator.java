package com.example.myapplication.calculator;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Калькулятор дифференцированных платежей.
 * <p>
 * Рассчитывает график погашения кредита по дифференцированной схеме,
 * где основная часть долга делится равномерно, а проценты начисляются
 * на остаток задолженности, что приводит к уменьшению платежа со временем.
 * </p>
 *
 * @author degtuareva
 * @version 1.0
 * @see PaymentCalculator
 */

public class DifferentialPaymentCalculator implements PaymentCalculator {

    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    @Override
    public CreditSchedule calculate(CreditRequest request) {
        BigDecimal amount = request.getAmount();
        int months = request.getTermMonths();

        BigDecimal monthlyRate = request.getAnnualRate()
                .divide(BigDecimal.valueOf(12 * 100L), 12, ROUNDING);

        BigDecimal principalPart = amount.divide(
                BigDecimal.valueOf(months), 12, ROUNDING
        );

        BigDecimal remainingDebt = amount;
        BigDecimal totalInterest = BigDecimal.ZERO;
        List<Payment> payments = new ArrayList<>();

        for (int month = 1; month <= months; month++) {
            BigDecimal interest = money(remainingDebt.multiply(monthlyRate));
            BigDecimal currentPrincipal = principalPart;

            if (month == months) {
                currentPrincipal = remainingDebt;
            }

            BigDecimal totalPayment = money(currentPrincipal.add(interest));
            remainingDebt = money(remainingDebt.subtract(currentPrincipal));
            totalInterest = totalInterest.add(interest);

            payments.add(new Payment(
                    month,
                    money(currentPrincipal),
                    interest,
                    totalPayment,
                    remainingDebt
            ));
        }

        BigDecimal totalPayment = payments.stream()
                .map(Payment::getTotalPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CreditSchedule(
                money(totalInterest),
                money(totalPayment),
                payments
        );
    }

    private BigDecimal money(BigDecimal value) {
        return value.setScale(SCALE, ROUNDING);
    }
}