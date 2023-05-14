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
import com.example.travelapp.activity.DetailsArticlesActivity;
import com.example.travelapp.activity.DetailsHotelActivity;
import com.example.travelapp.activity.DetailsTourActivity;
import com.example.travelapp.model.Articles;
import com.example.travelapp.model.Hotel;
import com.example.travelapp.model.NewTour;

import java.text.DecimalFormat;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    Context context;
    List<Articles> array;
    private static final int View_Type_Data = 0;
    private static final int View_Type_Loading = 1;


    public ArticlesAdapter(Context context, List<Articles> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == View_Type_Data){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articles,parent,false);
            return new ArticlesAdapter.MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new ArticlesAdapter.loadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            Articles articles = array.get(position);
            if (articles != null) {
                myViewHolder.a_title.setText(articles.getA_title());

                Glide.with(context).load(articles.getA_avatar()).into(myViewHolder.img_Articles);
                myViewHolder.setItemClickListener((view, pos, isLongClick) -> {
                    if (!isLongClick) {
                        Intent intent = new Intent(context, DetailsArticlesActivity.class);
                        intent.putExtra("detailsArticles", articles);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
            }
        } else if (holder instanceof loadingViewHolder) {
            loadingViewHolder loadingViewHolder = (loadingViewHolder) holder;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView a_title;
        ImageView img_Articles;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            a_title = itemView.findViewById(R.id.txtArticleTitle);
            img_Articles = itemView.findViewById(R.id.imgArticles);

            itemView.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onClick(view, getAdapterPosition(), false);
                }
            });
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
