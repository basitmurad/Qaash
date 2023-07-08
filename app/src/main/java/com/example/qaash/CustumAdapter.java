package com.example.qaash;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaash.model.ItemDetail;
import com.example.qaash.ui.ItemDetailActivity;

import java.io.File;
import java.util.ArrayList;


public class CustumAdapter extends RecyclerView.Adapter<CustumAdapter.CustomViewHolder> {
    private ArrayList<ItemDetail> list;
    private Context context;
    String cityName;

    public CustumAdapter(ArrayList<ItemDetail> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void filterList(ArrayList<ItemDetail> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view1 = LayoutInflater.from(context).inflate(R.layout.custum_item_layout, parent, false);

        return new CustomViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        ItemDetail item = list.get(position);
        holder.textViewPrice.setText(item.getPrice());
        holder.textViewName.setText(item.getName());

        holder.imageView.setImageResource(item.getImageUri());





        holder.orderBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context1 = v.getContext();
                Intent intent = new Intent(context1, ItemDetailActivity.class);
                intent.putExtra("Name", (holder.textViewName.getText()));
                intent.putExtra("itemPrice", (holder.textViewPrice.getText()));
                intent.putExtra("image", item.getImageUri());

                context1.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewPrice;
        ImageView imageView;
        AppCompatButton orderBUtton;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.cigerateName);
            textViewPrice = itemView.findViewById(R.id.cigeratePrice);
            imageView = itemView.findViewById(R.id.cigerateImage);

            orderBUtton = itemView.findViewById(R.id.btnOrder);


        }
    }

}
