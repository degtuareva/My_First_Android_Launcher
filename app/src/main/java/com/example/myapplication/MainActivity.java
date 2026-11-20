package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.calculator.AnnuityPaymentCalculator;
import com.example.myapplication.calculator.CreditRequest;
import com.example.myapplication.calculator.CreditRequestValidator;
import com.example.myapplication.calculator.CreditSchedule;
import com.example.myapplication.calculator.DifferentialPaymentCalculator;
import com.example.myapplication.calculator.PaymentCalculator;
import com.example.myapplication.calculator.PaymentCalculatorFactory;
import com.example.myapplication.calculator.PaymentType;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.CalculationEntity;


import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private EditText etAmount, etTerm, etRate;
    private TextView tvResult;
    private Spinner spinnerType;
    private RecyclerView recyclerView;
    private PaymentAdapter adapter;
    private AppDatabase db;

    private final CreditRequestValidator validator=new CreditRequestValidator();

    PaymentCalculatorFactory factory = new PaymentCalculatorFactory(
            new AnnuityPaymentCalculator(),
            new DifferentialPaymentCalculator()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);

        //1Находим элементы на экране
        etAmount = findViewById(R.id.etAmount);
        etTerm = findViewById(R.id.etTerm);
        etRate = findViewById(R.id.etRate);
        tvResult = findViewById(R.id.tvResult);
        spinnerType = findViewById(R.id.spinnerType);

        recyclerView = findViewById(R.id.recyclerViewPayments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Инициализируем адаптер пустым списком
        adapter = new PaymentAdapter(new java.util.ArrayList<>());
        recyclerView.setAdapter(adapter);

        String[] types = {"Аннуитентный", "Дифференцированный"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, types);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(spinnerAdapter);

        Button btnCalculate = findViewById(R.id.btnCalculate);

        //2 Вешаем обработчик на кнопку
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCredit();
            }
        });
    }

    //Пролучаем строки из полей
    private void calculateCredit() {
        String amountStr = etAmount.getText().toString().replace(",", ".");
        String termStr = etTerm.getText().toString();
        String rateStr = etRate.getText().toString().replace(",", ".");

        //Простая проверка чтобы приложение не упало на пустых полях
        if ((amountStr.isEmpty() || termStr.isEmpty() || rateStr.isEmpty())) {

            tvResult.setText("ЗАПОЛНИТЕ ВСЕ ПОЛЯ!");
            return;
        }

        int termMonths = 0;
        PaymentType paymentType = null;
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            termMonths = Integer.parseInt(termStr);
            BigDecimal rate = new BigDecimal(rateStr);

            if (spinnerType.getSelectedItemPosition() == 0) {
                paymentType = PaymentType.ANNUITY;
            } else {
                paymentType = PaymentType.DIFFERENTIAL;
            }
            CreditRequest request = new CreditRequest(amount, termMonths, rate,
                    paymentType);
            validator.validate(request);

            PaymentCalculator calculator = factory.getCalculator(paymentType);

            CreditSchedule schedule = calculator.calculate(request);

            //создаем форматтер для рублей
            java.text.NumberFormat format = java.text.NumberFormat.getInstance(
                    new java.util.Locale("ru", "RU")
            );
            format.setMaximumFractionDigits(2);
            format.setMinimumFractionDigits(2);
            String resultText = "Переплата: " + format.format(schedule.
                    getTotalInterest()) + "P\n" +
                    "Общая сумма: " + format.format(
                    schedule.getTotalPayment()) + "P";
            tvResult.setText(resultText);

            // ... предыдущий код с выводом tvResult ...

            // Обновляем список (таблицу)
            adapter.updateData(schedule.getPayments());

            // Прячем клавиатуру после нажатия (опционально, для удобства)
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(tvResult.getWindowToken(), 0);
        } catch (IllegalArgumentException e) {
            // Ошибки валидации (например, сумма < 100)
            tvResult.setText("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            tvResult.setText("Ошибка ввода данных.");
        }
        // Сохраняем в локальную базу
        CalculationEntity entity = new CalculationEntity(
                amountStr,
                termMonths,
                rateStr,
                paymentType.name(), // "ANNUITY" или "DIFFERENTIAL"
                System.currentTimeMillis()
        );
        db.calculationDao().insert(entity);

    }
}













