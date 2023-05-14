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
import com.example.travelapp.activity.DetailsTourActivity;
import com.example.travelapp.model.NewTour;

import java.text.DecimalFormat;
import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<NewTour> array;
    private static final int View_Type_Data = 0;
    private static final int View_Type_Loading = 1;


    public TourAdapter(Context context, List<NewTour> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == View_Type_Data){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour,parent,false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new loadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            NewTour tour = array.get(position);
            if (tour != null) {
                myViewHolder.titleTour.setText(tour.getT_title());
//                myViewHolder.descTour.setText(newTour.getT_description());
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                myViewHolder.priceTour.setText("From đ " + decimalFormat.format(Double.parseDouble(tour.getT_price_adults())));
                Glide.with(context).load(tour.getT_image()).into(myViewHolder.imgTour);
                myViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int pos, boolean isLongClick) {
                        if (!isLongClick) {
                            Intent intent = new Intent(context, DetailsTourActivity.class);
                            intent.putExtra("details",tour);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });
            }
        } else {
            loadingViewHolder loadingViewHolder = (TourAdapter.loadingViewHolder) holder;
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
        TextView titleTour, priceTour, descTour;
        ImageView imgTour;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTour = itemView.findViewById(R.id.tour_title_text_view);
            priceTour = itemView.findViewById(R.id.tour_price_text_view);
//            descTour = itemView.findViewById(R.id.item_DescTour);
            imgTour = itemView.findViewById(R.id.tour_image_view);
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
