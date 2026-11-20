package com.example.myapplication.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "calculations")
public class CalculationEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String amount;        // BigDecimal -> String
    private int termMonths;
    private String annualRate;    // BigDecimal -> String
    private String paymentType;   // Enum -> String (ANNUITY / DIFFERENTIAL)
    private long createdAt;       // LocalDateTime -> long (timestamp)

    // Пустой конструктор нужен для Room
    public CalculationEntity() {
    }

    public CalculationEntity(String amount, int termMonths, String annualRate, String paymentType, long createdAt) {
        this.amount = amount;
        this.termMonths = termMonths;
        this.annualRate = annualRate;
        this.paymentType = paymentType;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры (Room использует их для доступа к полям)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public int getTermMonths() { return termMonths; }
    public void setTermMonths(int termMonths) { this.termMonths = termMonths; }

    public String getAnnualRate() { return annualRate; }
    public void setAnnualRate(String annualRate) { this.annualRate = annualRate; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}