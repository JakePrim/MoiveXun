package com.moive.sus.library.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moive.sus.library.R;

/**
 * Created by linksus on 5/18 0018.
 */

public class DefaultSuggestionsAdapter extends SuggestionsAdapter<String, DefaultSuggestionsAdapter.SuggestionHolder> {
    private SuggestionsAdapter.OnItemViewClickListener listener;

    public DefaultSuggestionsAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    public void setListener(SuggestionsAdapter.OnItemViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getSingleViewHeight() {
        return 50;
    }

    @Override
    public DefaultSuggestionsAdapter.SuggestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.item_last_request, parent, false);
        return new DefaultSuggestionsAdapter.SuggestionHolder(view);
    }

    @Override
    public void onBindSuggestionHolder(String suggestion, SuggestionHolder holder, int position) {
        holder.text.setText(getSuggestions().get(position));
    }

    class SuggestionHolder extends RecyclerView.ViewHolder {
        private TextView text;
        private ImageView iv_delete;

        public SuggestionHolder(final View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(getSuggestions().get(getAdapterPosition()));
                    listener.OnItemClickListener(getAdapterPosition(), v);
                }
            });
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag(getSuggestions().get(getAdapterPosition()));
                    listener.OnItemDeleteListener(getAdapterPosition(), v);
                }
            });
        }
    }

    public interface OnItemViewClickListener {
        void OnItemClickListener(int position, View v);

        void OnItemDeleteListener(int position, View v);
    }
}
