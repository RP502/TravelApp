package com.example.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelapp.R;
import com.example.travelapp.model.TypeService;

import java.util.List;

public class TypeServiceAdapter extends BaseAdapter {
    List<TypeService> array;
    Context  context;

    public TypeServiceAdapter( Context context,List<TypeService> array){
        this.array = array;
        this.context = context;
    }


    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class  ViewHolder{
        TextView tvNameService;
        ImageView imgService;

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_service,null);
            viewHolder.tvNameService = view.findViewById(R.id.item_nameService);
            viewHolder.imgService = view.findViewById(R.id.item_imageService);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();

        }
        viewHolder.tvNameService.setText(array.get(position).getNameService());
        Glide.with(context).load(array.get(position).getImgService()).into(viewHolder.imgService);
        return view;
    }
}
