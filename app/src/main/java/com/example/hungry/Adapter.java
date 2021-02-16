package com.example.hungry;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Restaurant> restaurantList;
    Context mContext;

    public Adapter(List<Restaurant> restaurantList, Context mContext) {
        this.restaurantList = restaurantList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.single_restaurant_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter.ViewHolder holder, final int position) {

        String name = restaurantList.get(position).getName();
        String image = restaurantList.get(position).getImage();
        String costForTwo = restaurantList.get(position).getCostForTwo();

        holder.restaurantName.setText(name);
        Glide.with(holder.itemView.getContext()).load(image).apply(new RequestOptions().placeholder(R.drawable.ic_baseline_insert_photo_24))
                .into(holder.restaurantImage);
        holder.costForTwo.setText("cost for two = "+ costForTwo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent restaurantInfo = new Intent(mContext, SearchActivity.class);
                restaurantInfo.putExtra("res_id", "");
                restaurantInfo.putExtra("ress_name", restaurantList.get(position).getName());
                restaurantInfo.putExtra("res_location", "");

                holder.itemView.getContext().startActivity(restaurantInfo);

            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantName;
        ImageView restaurantImage;
        TextView costForTwo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantName = itemView.findViewById(R.id.restaurantName);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
            costForTwo = itemView.findViewById(R.id.costForTwo);
        }
    }
}
