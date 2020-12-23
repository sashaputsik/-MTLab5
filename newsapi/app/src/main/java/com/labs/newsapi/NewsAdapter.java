package com.labs.newsapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder>{

    Context context ;
    List<Article> mList;
    NewsClickListener newsItemClickListener;

    public NewsAdapter(Context context, List<Article> mList, NewsClickListener newsItemClickListener) {
        this.context = context;
        this.mList = mList;
        this.newsItemClickListener = newsItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(mList.get(position).getTitle());
        Glide.with(context)
                .load(mList.get(position).getUrlToImage())
                .placeholder(R.drawable.loading)
                .into(holder.background);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView background;
        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            background = itemView.findViewById(R.id.slide_img);
            title = itemView.findViewById(R.id.slide_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    newsItemClickListener.onNewsClick(mList.get(getAdapterPosition()));


                }
            });
        }
    }
}
