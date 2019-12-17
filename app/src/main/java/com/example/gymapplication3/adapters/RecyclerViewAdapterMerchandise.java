package com.example.gymapplication3.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapplication3.R;

import java.util.ArrayList;

public class RecyclerViewAdapterMerchandise extends RecyclerView.Adapter<RecyclerViewAdapterMerchandise.ViewHolder>{


    public interface MerchandiseDelegate{
        void onClickMerchandise(String merch);
    }

    ArrayList<String> gymMaterials;
    MerchandiseDelegate merchandiseDelegate;

    public RecyclerViewAdapterMerchandise(ArrayList<String> gymMaterials, MerchandiseDelegate merchandiseDelegate) {
        this.gymMaterials = gymMaterials;
        this.merchandiseDelegate = merchandiseDelegate;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterMerchandise.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout_merchandise,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterMerchandise.ViewHolder holder, final int position){
        holder.material.setText(gymMaterials.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                merchandiseDelegate.onClickMerchandise(gymMaterials.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return gymMaterials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView material;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            material = itemView.findViewById(R.id.gymMaterialTextView);
        }
    }
}
