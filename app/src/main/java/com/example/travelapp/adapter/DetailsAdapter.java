package com.example.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelapp.R;
import com.example.travelapp.model.Item;

import java.text.DecimalFormat;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {
   Context context;
    List<Item> itemList;

    public DetailsAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public DetailsAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtTitle.setText(item.getT_title()+ " ");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPrice.setText("Total: " + decimalFormat.format(item.getB_number_adults() * item.getB_price_adults())+ "đ");

        holder.txtQuantity.setText("Quantity: "+item.getB_number_adults()+ " ");

        Glide.with(context).load(item.getT_image()).into(holder.img_book);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView img_book;
        TextView txtTitle, txtQuantity, txtPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_book = itemView.findViewById(R.id.item_image_book);
            txtTitle = itemView.findViewById(R.id.item_title_book);
            txtQuantity = itemView.findViewById(R.id.item_quantity_book);
            txtPrice = itemView.findViewById(R.id.item_price_book);
        }
    }
}
