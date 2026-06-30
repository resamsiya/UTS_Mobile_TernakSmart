package com.example.ternaksmart;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class VaksinAdapter extends RecyclerView.Adapter<VaksinAdapter.VaksinViewHolder> {

    private List<Vaksin> vaksinList;

    public VaksinAdapter(List<Vaksin> vaksinList) {
        this.vaksinList = vaksinList;
    }

    @NonNull
    @Override
    public VaksinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vaksin, parent, false);
        return new VaksinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VaksinViewHolder holder, int position) {
        Vaksin vaksin = vaksinList.get(position);

        // Set data ke view
        holder.tvNamaVaksin.setText(vaksin.getNama());
        holder.tvUmurTernak.setText("Target: " + vaksin.getTargetUmurFormatted());
        holder.cbStatusVaksin.setChecked(vaksin.isDone());

        // Update warna indikator berdasarkan status
        if (vaksin.isDone()) {
            holder.statusIndicator.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.primary_green));
        } else {
            holder.statusIndicator.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.accent_gold));
        }

        holder.cbStatusVaksin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vaksin.setDone(isChecked);
            if (isChecked) {
                holder.statusIndicator.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.primary_green));
            } else {
                holder.statusIndicator.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.accent_gold));
            }
        });
    }

    @Override
    public int getItemCount() {
        return vaksinList.size();
    }

    static class VaksinViewHolder extends RecyclerView.ViewHolder {
        android.widget.TextView tvNamaVaksin, tvUmurTernak;
        View statusIndicator;
        MaterialCheckBox cbStatusVaksin;

        public VaksinViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaVaksin = itemView.findViewById(R.id.tvNamaVaksin);
            tvUmurTernak = itemView.findViewById(R.id.tvUmurTernak);
            statusIndicator = itemView.findViewById(R.id.statusIndicator);
            cbStatusVaksin = itemView.findViewById(R.id.cbStatusVaksin);
        }
    }
}