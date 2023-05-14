package com.example.travelapp.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp.R;
import com.example.travelapp.model.BookOrder;

import java.util.List;

public class BookOrderAdapter extends RecyclerView.Adapter<BookOrderAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<BookOrder> listBook;

    public BookOrderAdapter(Context context, List<BookOrder> listBook) {
        this.context = context;
        this.listBook = listBook;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookOrder bookOrder = listBook.get(position);
//        holder.txtBook.setText("Order Id: "+bookOrder.getId());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                holder.rcViewBook.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        linearLayoutManager.setInitialPrefetchItemCount(bookOrder.getItem().size());

        DetailsAdapter detailsAdapter = new DetailsAdapter(context,bookOrder.getItem());
        holder.rcViewBook.setLayoutManager(linearLayoutManager);
        holder.rcViewBook.setAdapter(detailsAdapter);
        holder.rcViewBook.setRecycledViewPool(viewPool);
    }


    @Override
    public int getItemCount() {
        return listBook.size();
    }

    public  class  MyViewHolder extends  RecyclerView.ViewHolder{
        TextView txtBook;
        RecyclerView rcViewBook;

        public MyViewHolder(@NonNull View itemView ) {
            super(itemView);
//            txtBook = itemView.findViewById(R.id.idBook);
            rcViewBook = itemView.findViewById(R.id.rcViewDetailsBook);
        }


    }
}
