package com.example.gymapplication3.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapplication3.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.Inflater;

public class RecyclerViewAdapterInventory extends RecyclerView.Adapter<RecyclerViewAdapterInventory.ViewHolder> {

    ArrayList<String> inventory;


    public RecyclerViewAdapterInventory(ArrayList<String> inventory) {
        this.inventory = inventory;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterInventory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout_inventory,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterInventory.ViewHolder holder, int position) {
        String[] mats = inventory.get(position).split(",");
        Log.d("TAG_P", "onBindViewHolder: "+ Arrays.toString(mats));
        holder.textViewNumber.setText(mats[1]);
        holder.textViewMaterial.setText(mats[0]);
    }

    @Override
    public int getItemCount() {
        return inventory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber;
        TextView textViewMaterial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.inventoryCount);
            textViewMaterial = itemView.findViewById(R.id.inventoryType);
        }
    }
}
