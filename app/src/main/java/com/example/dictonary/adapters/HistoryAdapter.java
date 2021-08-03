package com.example.dictonary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictonary.R;
import com.example.dictonary.db.HistoryModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<HistoryModel> list;

    public HistoryAdapter(List<HistoryModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView englishTv;
        private final TextView uzbTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            englishTv = itemView.findViewById(R.id.englishTv);
            uzbTv = itemView.findViewById(R.id.uzbTv);
        }

        public void onBind(HistoryModel model) {
            englishTv.setText(model.getEng());
            uzbTv.setText(model.getUzb());
        }
    }
}
