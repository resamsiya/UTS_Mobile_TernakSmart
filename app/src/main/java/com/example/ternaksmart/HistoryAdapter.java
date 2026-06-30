package com.example.ternaksmart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<TernakData> ternakDataList;

    public HistoryAdapter(List<TernakData> ternakDataList) {
        this.ternakDataList = ternakDataList;
    }

    public void setData(List<TernakData> newData) {
        this.ternakDataList = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TernakData data = ternakDataList.get(position);
        holder.tvName.setText(data.getNama());
        holder.tvWeight.setText("Berat: " + data.getBerat() + " Kg");
        // Gunakan placeholder tanggal atau format dari data jika ada
        holder.tvDate.setText("ID: " + data.getId());
    }

    @Override
    public int getItemCount() {
        return ternakDataList != null ? ternakDataList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvWeight, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvHistoryName);
            tvWeight = itemView.findViewById(R.id.tvHistoryWeight);
            tvDate = itemView.findViewById(R.id.tvHistoryDate);
        }
    }
}