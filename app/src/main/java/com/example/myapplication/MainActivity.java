package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.calculator.AnnuityPaymentCalculator;
import com.example.myapplication.calculator.CreditRequest;
import com.example.myapplication.calculator.CreditSchedule;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private EditText etAmount,etTerm,etRate;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1Находим элементы на экране
        etAmount=findViewById(R.id.etAmount);
        etTerm=findViewById(R.id.etTerm);
        etRate=findViewById(R.id.etRate);
        tvResult=findViewById(R.id.tvResult);
        Button btnCalculate=findViewById(R.id.btnCalculate);

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
        String amountStr=etAmount.getText().toString().replace(",",".");
        String termStr=etTerm.getText().toString();
        String rateStr=etRate.getText().toString().replace(",",".");
        //Простая проверка чтобы приложение не упало на пустых полях
        if ((amountStr.isEmpty() || termStr.isEmpty() || rateStr.isEmpty())) {

            tvResult.setText("ЗАПОЛНИТЕ ВСЕ ПОЛЯ!");
            return;
        }
        try {
            BigDecimal amount=new BigDecimal(amountStr);
            int termMonths=Integer.parseInt(termStr);
            BigDecimal rate=new BigDecimal(rateStr);

            CreditRequest request=new CreditRequest(amount,termMonths,rate);

            AnnuityPaymentCalculator calculator=new AnnuityPaymentCalculator();
            CreditSchedule schedule=calculator.calculate(request);

            String resultText="Переплата: "+schedule.getTotalInterest()+"P\n"+
                    "Общая сумма: "+schedule.getTotalInterest()+"P";
            tvResult.setText(resultText);
        }catch (Exception e){
            tvResult.setText("Ошибка ввода данных");
        }
    }
}













