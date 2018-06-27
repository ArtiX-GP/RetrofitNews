package com.nikkorejz.retrofitnews;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private List<PostModel> mPosts;

    // Конструктор.
    public NewsAdapter(List<PostModel> posts) {
        this.mPosts = posts;
    }

    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Override
    public PostModel getItem(int i) {
        return mPosts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item, viewGroup, false);
        }

        ((TextView) v.findViewById(R.id.textView))
                .setText(String.valueOf(i + 1) + ". " + Html.fromHtml(mPosts.get(i).getElementPureHtml()));

        ((TextView) v.findViewById(R.id.textViewSiteName))
                .setText(mPosts.get(i).getSite());

        return v;
    }
}
