package com.example.android.dailynews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsAdapterViewHolder>{
    private ArrayList<word> mText= new ArrayList();
    final private NewsAdapterOnClickHandler mClickHandler;
    public interface  NewsAdapterOnClickHandler{


        void onClick(String newsDay);
    }
    public NewsAdapter(NewsAdapterOnClickHandler ClickHandler) {
        mClickHandler=ClickHandler;
    }


    @NonNull
    @Override
    public NewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=    LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list,parent,false);

        return new NewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapterViewHolder holder, int position) {
         word mdata= mText.get(position);
        holder.test.setText(mdata.getmTitle());
Glide.with(holder.itemView.getContext()).load(mdata.getmUrltoString()).into(holder.imag);

    }

    @Override
    public int getItemCount() {
        if (null == mText) return 0;
        return mText.size();
    }

    public void setNewsData(List<word> data) {

        mText.addAll(data);
        notifyDataSetChanged();
    }


    public class NewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView test;
       public final ImageView imag;
        public NewsAdapterViewHolder( View itemView) {
            super(itemView);

            test=(TextView) itemView.findViewById(R.id.txt);
            imag=(ImageView) itemView.findViewById(R.id.img_android);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int mdata= getAdapterPosition();
            word mdat= mText.get(mdata);
String url=mdat.getMurl();
        mClickHandler.onClick(url);
        }
    }

}

