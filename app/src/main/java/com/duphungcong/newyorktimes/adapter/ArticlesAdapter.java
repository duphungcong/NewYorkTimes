package com.duphungcong.newyorktimes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duphungcong.newyorktimes.R;
import com.duphungcong.newyorktimes.model.Article;

import java.util.List;

/**
 * Created by udcun on 2/22/2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

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

        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    private Context mContext;
    private List<Article> mArticles;

    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View articleView = LayoutInflater.from(getmContext()).inflate(R.layout.article_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(articleView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticlesAdapter.ViewHolder holder, int position) {
        Article article = mArticles.get(position);

        TextView tvTitle = holder.tvTitle;
        tvTitle.setText(article.getSnippet());
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
