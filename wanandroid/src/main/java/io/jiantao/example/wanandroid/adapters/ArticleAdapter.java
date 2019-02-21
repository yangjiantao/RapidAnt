package io.jiantao.example.wanandroid.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.jiantao.example.wanandroid.ArticleDetailActivity;
import io.jiantao.example.wanandroid.R;
import io.jiantao.example.wanandroid.model.IArticle;

/**
 * 精选文件列表adapter
 *
 * @author Created by jiantaoyang
 * @date 2019/1/7
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<? extends IArticle> mArticles;

    public ArticleAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IArticle iArticle = mArticles.get(position);
        holder.mTitle.setText(iArticle.getTitle());
        holder.mAuthor.setText(iArticle.getAuthor());
        holder.articleUrl = iArticle.getLink();
    }

    @Override
    public int getItemCount() {
        return mArticles == null ? 0 : mArticles.size();
    }

    /**
     * refresh data
     *
     * @param articles
     */
    public void refreshArticles(List<? extends IArticle> articles) {
        if (articles == null || articles.isEmpty()) {
            return;
        }
        mArticles = articles;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        String articleUrl;

        TextView mTitle;
        TextView mAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.item_search_pager_title);
            mAuthor = itemView.findViewById(R.id.item_search_pager_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ArticleDetailActivity.class);
                    intent.putExtra(ArticleDetailActivity.KEY_ARTICLE_URL, articleUrl);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
