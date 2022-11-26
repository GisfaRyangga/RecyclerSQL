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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private final ArrayList<Character> bleach_char;
    private final LayoutInflater inflater;
    private OnClickListener onClickListener;

    public RecyclerViewAdapter(Context context, ArrayList<Character> bleach_char){
        this.bleach_char = bleach_char;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.character_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Character character = bleach_char.get(position);
        holder.txtName.setText(character.getName());
        holder.txtPower.setText(character.getPower());
    }

    @Override
    public int getItemCount() {
        return bleach_char.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName, txtPower;
        private Button update, delete;
        public ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txt_name);
            txtPower = view.findViewById(R.id.txt_power);
            update = view.findViewById(R.id.btn_update);
            delete = view.findViewById(R.id.btn_delete);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClickListener.onBtnClick(bleach_char.get(position), v);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClickListener.onBtnClick(bleach_char.get(position), v);
                }
            });

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClickListener.onBtnClick(bleach_char.get(position), v);
                }
            });
        }
    }

    public interface OnClickListener{
        void onBtnClick(Character character, View v);
        void onItemClick(Character character);
    }

    public void setOnItemClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
}
