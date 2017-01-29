package com.markdevelopers.rakshak.subscriptions;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.Config;
import com.markdevelopers.rakshak.data.remote.models.Updates;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Archish on 12/19/2016.
 */

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.NewsFeedViewHolder> {

    ArrayList<Updates> data;
    private LikeItemUpdateListener commander;

    public UpdateAdapter(ArrayList<Updates> data, LikeItemUpdateListener commander) {
        this.data = data;
        this.commander = commander;
    }

    public interface LikeItemUpdateListener {
        void onItemStatusChanged(Updates updates);

        void onItemCardClicked(Updates updates);
    }

    @Override
    public UpdateAdapter.NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_update, parent, false);
        NewsFeedViewHolder holder = new NewsFeedViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UpdateAdapter.NewsFeedViewHolder holder, final int position) {
        if (!data.get(position).getImage().isEmpty()) {
            Picasso.with(holder.itemView.getContext()).load(Config.BASE_URL + data.get(position).getImage())
                    .into(holder.ivImage);
        }
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvDescription.setText(data.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        BaseTextView tvTitle, tvDescription;
        ImageView ivImage;
        LinearLayout cdNewsFeed;
        LinearLayout llDoctor;

        public NewsFeedViewHolder(final View itemView) {
            super(itemView);
            tvTitle = (BaseTextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (BaseTextView) itemView.findViewById(R.id.tvDescription);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            cdNewsFeed = (LinearLayout) itemView.findViewById(R.id.cdNewsFeed);
            cdNewsFeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (commander != null) {
                        commander.onItemCardClicked(data.get(getAdapterPosition()));
                    }
                }
            });
        }

    }


}