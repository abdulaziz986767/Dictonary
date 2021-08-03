package com.example.dictonary.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictonary.R;
import com.example.dictonary.db.Entity;

import java.util.List;

public class VpAdapter extends RecyclerView.Adapter<VpAdapter.ViewHolder> {

    public interface OnClickNextListener {
        void setOnCLickNext(int position, boolean bool);
        void setOnClickPervious();
    }

    private final OnClickNextListener listener;
    private final List<com.example.dictonary.db.Entity> list;

    public VpAdapter(OnClickNextListener listener, List<Entity> list) {
        this.listener = listener;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vp_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));

        if (position == (list.size()-1)) {
            holder.setText();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView engEt;
        private final EditText uEt;
        private final Button nBtn;

        private final OnClickNextListener listener;

        private com.example.dictonary.db.Entity model;

        public ViewHolder(@NonNull View itemView, OnClickNextListener listener) {
            super(itemView);

            this.listener = listener;

            engEt = itemView.findViewById(R.id.enghEt);
            uEt = itemView.findViewById(R.id.uEt);

            nBtn = itemView.findViewById(R.id.btnNext);
            Button pBtn = itemView.findViewById(R.id.btnPervious);

            nBtn.setOnClickListener(this);
            pBtn.setOnClickListener(this);
        }

        public void onBind(com.example.dictonary.db.Entity e) {
            model = e;
            engEt.setText(e.getEng());
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnNext) {
                listener.setOnCLickNext(getAdapterPosition(), getResult());
            }
            else if (id == R.id.btnPervious) {
                listener.setOnClickPervious();
            }
        }

        public boolean getResult() {
            String word = uEt.getText().toString().trim();
            return word.toLowerCase().equals(model.getUzb().toLowerCase());
        }

        @SuppressLint("SetTextI18n")
        public void setText() {
            nBtn.setText("Finish");
        }
    }
}
