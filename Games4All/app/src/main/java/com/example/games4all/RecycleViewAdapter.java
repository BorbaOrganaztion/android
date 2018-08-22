package com.example.games4all;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Game>mData;

    public RecycleViewAdapter(Context mContext, List<Game> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view =mInflater.inflate(R.layout.game_card_view,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tv_game_title.setText(mData.get(position).getTitle());
        // holder.img_game_thumbnail.setImageResource(mData.get(position).getThumbnail());


        //  Picasso.get().load("https://upload.wikimedia.org/wikipedia/en/thumb/5/52/Assassin%27s_Creed.jpg/220px-Assassin%27s_Creed.jpg").into(holder.img_game_thumbnail);
        Picasso.get().load(mData.get(position).getImgUrl()).into(holder.img_game_thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,Game_detail.class);
                intent.putExtra("Title",mData.get(position).getTitle());
                intent.putExtra("Price",mData.get(position).getPrice());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Console",mData.get(position).getConsole());
                intent.putExtra("Thumbnail",mData.get(position).getImgUrl());

                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_game_title;
        ImageView img_game_thumbnail;

        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_game_title=(TextView)itemView.findViewById(R.id.game_title_id);
            img_game_thumbnail=(ImageView)itemView.findViewById(R.id.game_image_id);
            cardView=(CardView)itemView.findViewById(R.id.cardview_id);





        }
    }
}
