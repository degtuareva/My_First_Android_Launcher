package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.calculator.Payment;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private List<Payment> payments;
    private final NumberFormat format;

    public PaymentAdapter(List<Payment> payments) {
        this.payments = payments;
        // Форматтер для денег (без копеек, чтобы влезло в экран)
        this.format = NumberFormat.getInstance(new Locale("ru", "RU"));
        this.format.setMaximumFractionDigits(0);
    }

    // Метод для обновления данных, если пользователь пересчитал кредит
    public void updateData(List<Payment> newPayments) {
        this.payments = newPayments;
        notifyDataSetChanged(); // Говорим списку: "Перерисуйся!"
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        Payment payment = payments.get(position);

        holder.tvMonth.setText(String.valueOf(payment.getMonth()));
        holder.tvPayment.setText(format.format(payment.getTotalPayment()));
        holder.tvPrincipal.setText(format.format(payment.getPrincipal()));
        holder.tvInterest.setText(format.format(payment.getInterest()));
        holder.tvRemaining.setText(format.format(payment.getRemainingDebt()));
    }

    @Override
    public int getItemCount() {
        return payments != null ? payments.size() : 0;
    }

    static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonth, tvPayment, tvPrincipal, tvInterest, tvRemaining;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvPayment = itemView.findViewById(R.id.tvPayment);
            tvPrincipal = itemView.findViewById(R.id.tvPrincipal);
            tvInterest = itemView.findViewById(R.id.tvInterest);
            tvRemaining = itemView.findViewById(R.id.tvRemaining);
        }
    }
}