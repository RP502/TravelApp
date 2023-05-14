package com.example.travelapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelapp.Interface.ImageClickListener;
import com.example.travelapp.R;
import com.example.travelapp.model.Cart;
import com.example.travelapp.model.EventBus.SubTotalEventBus;
import com.example.travelapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.tv_item_title_cart.setText(cart.getCart_name());
        holder.item_quantity_cart.setText(String.valueOf(cart.getCart_quantity()));
        Glide.with(context).load(cart.getImg_cart()).into(holder.img_item_cart);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_item_price_cart.setText( "From: "+ decimalFormat.format((cart.getCart_price())));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                Utils.arrGetCar.add(cart);
                    EventBus.getDefault().postSticky(new SubTotalEventBus());
                }else {
                    for (int i = 0 ; i < Utils.arrGetCar.size();i++){
                        if (Utils.arrGetCar.get(i).getId()== cart.getId()){
                            Utils.arrGetCar.remove(i);
                            EventBus.getDefault().postSticky(new SubTotalEventBus());
                        }
                    }
                }
            }
        });


//        long price = cart.getCart_quantity() * cart.getCart_price();
//        holder.tv_item_price_cart2.setText(decimalFormat.format(price));

        holder.setListener(new ImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int value) {
                Log.d("TAG","onImageClick" + pos + " "+ value);
                if (value == 1) {
                    if (cartList.get(pos).getCart_quantity() > 1) {
                        int newQuantity = cartList.get(pos).getCart_quantity() - 1;
                        cartList.get(pos).setCart_quantity(newQuantity);
                    }else if(cartList.get(pos).getCart_quantity() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Notification");
                        builder.setMessage("Do you want to remove tour of car?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.arrCart.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new SubTotalEventBus());
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }

                }
                else if (value == 2) {
                    int newQuantity = cartList.get(pos).getCart_quantity() + 1;
                    cartList.get(pos).setCart_quantity(newQuantity);
                }
                holder.item_quantity_cart.setText(String.valueOf(cartList.get(pos).getCart_quantity()));
//                long price = cartList.get(pos).getCart_quantity() * cartList.get(pos).getCart_price();
//                holder.tv_item_price_cart2.setText(decimalFormat.format(price));
                EventBus.getDefault().postSticky(new SubTotalEventBus());

            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_item_cart;
        TextView tv_item_title_cart, tv_item_price_cart,item_quantity_cart,tv_item_price_cart2;
        ImageButton minus_button_cart,plus_button_cart;
        ImageClickListener listener;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item_cart = itemView.findViewById(R.id.item_image_cart);
            tv_item_title_cart = itemView.findViewById(R.id.item_title_cart);
            tv_item_price_cart = itemView.findViewById(R.id.item_price_cart);
//            tv_item_price_cart2 = itemView.findViewById(R.id.item_price_cart2);
            item_quantity_cart = itemView.findViewById(R.id.item_quantity_cart);
            minus_button_cart = itemView.findViewById(R.id.minus_button_cart);
            plus_button_cart = itemView.findViewById(R.id.plus_button_cart);
            checkBox = itemView.findViewById(R.id.cartCheck);

            //eventClcik
            minus_button_cart.setOnClickListener(this);
            plus_button_cart.setOnClickListener(this);
        }

        public void setListener(ImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View view) {
            if (view == minus_button_cart){
                    listener.onImageClick(view, getAdapterPosition(),1);
            }else if (view== plus_button_cart){
                listener.onImageClick(view, getAdapterPosition(),2);
            }
        }
    }
}
