package com.duphungcong.newyorktimes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.duphungcong.newyorktimes.R;
import com.duphungcong.newyorktimes.model.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by udcun on 2/22/2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {
    private Context mContext;
    private List<Article> mArticles;

    public ArticlesAdapter(Context mContext, List<Article> mArticles) {
        this.mContext = mContext;
        this.mArticles = mArticles;
    }

    public Context getmContext() {
        return mContext;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvHeadline;
        public ImageView ivThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);

            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
        }
    }

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View articleView = LayoutInflater.from(getmContext()).inflate(R.layout.article_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(articleView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticlesAdapter.ViewHolder holder, int position) {
        Article article = mArticles.get(position);

        TextView tvHeadline = holder.tvHeadline;
        ImageView ivThumbnail = holder.ivThumbnail;

        tvHeadline.setText(article.getHeadline().getMain());
        String thumbnailUrl = article.getThumbnalUrl();
        if (thumbnailUrl.equals("url not found")) {
            ivThumbnail.setVisibility(View.INVISIBLE);
        } else {
            Picasso.with(mContext)
                    .load(article.getThumbnalUrl())
                    .fit()
                    .placeholder(R.drawable.image_loading)
                    .error(R.drawable.image_loading)
                    .into(ivThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void updateList(List<Article> newArticles) {
        if (mArticles != null) {
            mArticles.addAll(newArticles);
        }
        else {
            mArticles = newArticles;
        }

        notifyDataSetChanged();
    }

    public void clearList() {
        if(mArticles != null) {
            mArticles.clear();
        }
        notifyDataSetChanged();
    }
}
