package com.example.travelapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelapp.Interface.ItemClickListener;
import com.example.travelapp.R;
import com.example.travelapp.activity.DetailsHotelActivity;
import com.example.travelapp.activity.DetailsTourActivity;
import com.example.travelapp.model.Hotel;
import com.example.travelapp.model.NewTour;

import java.text.DecimalFormat;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    Context context;
    List<Hotel> array;
    private static final int View_Type_Data = 0;
    private static final int View_Type_Loading = 1;


    public HotelAdapter(Context context, List<Hotel> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == View_Type_Data){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel,parent,false);
            return new HotelAdapter.MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new HotelAdapter.loadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HotelAdapter.MyViewHolder) {
            HotelAdapter.MyViewHolder myViewHolder = (HotelAdapter.MyViewHolder) holder;
            Hotel hotel = array.get(position);
            if (hotel != null) {
                myViewHolder.h_name.setText(hotel.getH_name());
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                myViewHolder.h_price.setText("From đ " + decimalFormat.format(Double.parseDouble(hotel.getH_price())));
                Glide.with(context).load(hotel.getH_image()).into(myViewHolder.img_hotel);
                myViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongClick) {
                        if (!isLongClick) {
                            Intent intent = new Intent(context, DetailsHotelActivity.class);
                            intent.putExtra("detailsHotel",hotel);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });
            }
        } else {
            HotelAdapter.loadingViewHolder loadingViewHolder = (HotelAdapter.loadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? View_Type_Loading:View_Type_Data;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public  class  loadingViewHolder extends RecyclerView.ViewHolder{

        ProgressBar progressBar;
        public loadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView h_name, h_price;
        ImageView img_hotel;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            h_name = itemView.findViewById(R.id.hotel_name);
            h_price = itemView.findViewById(R.id.hotel_price);
            img_hotel = itemView.findViewById(R.id.hotel_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(),false);
        }
    }
}
