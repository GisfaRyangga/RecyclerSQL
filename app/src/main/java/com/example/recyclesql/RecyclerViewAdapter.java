package com.example.recyclesql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private final ArrayList<Product> values;
    private final LayoutInflater inflater;
    private OnClickListener onClickListener;

    public RecyclerViewAdapter(Context context, ArrayList<Product> values){
        this.values = values;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_product,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Product product = values.get(position);
        holder.txtName.setText(product.getName());
        String strPrice = "Rp "+String.valueOf(product.getPrice());
        holder.txtPrice.setText(strPrice);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtPrice;
        private Button update, delete;
        public ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txt_nama);
            txtPrice = view.findViewById(R.id.txt_price);
            update = view.findViewById(R.id.btn_update);
            delete = view.findViewById(R.id.btn_delete);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClickListener.onBtnClick(values.get(position), v);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClickListener.onBtnClick(values.get(position), v);
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClickListener.onBtnClick(values.get(position), v);
                }
            });
        }
    }

    public interface OnClickListener{
        void onBtnClick(Product product, View v);
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
