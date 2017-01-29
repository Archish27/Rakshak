package com.markdevelopers.rakshak.subscriptions;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.data.remote.models.Subscribe;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;

import java.util.ArrayList;


/**
 * Created by Archish on 12/19/2016.
 */

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.NewsFeedViewHolder> {

    ArrayList<Subscribe> data;
    private LikeItemUpdateListener commander;

    public SubscriptionAdapter(ArrayList<Subscribe> data, LikeItemUpdateListener commander) {
        this.data = data;
        this.commander = commander;
    }

    public interface LikeItemUpdateListener {
        void onItemStatusChanged(Subscribe subscribe);

        void onItemCardClicked(Subscribe subscribe);
    }

    @Override
    public SubscriptionAdapter.NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_subscription, parent, false);
        NewsFeedViewHolder holder = new NewsFeedViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SubscriptionAdapter.NewsFeedViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.tvDescription.setText(data.get(position).getDescription());
        if (data.get(position).isSubscribed())
            holder.ivLike.setImageResource(R.drawable.subscribe_blue);
        else
            holder.ivLike.setImageResource(R.drawable.subscribe);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        BaseTextView tvDescription, tvName, tvLocation;
        RelativeLayout rlSubscribe;
        ImageView ivLike;
        LinearLayout cdNewsFeed;
        LinearLayout llDoctor;

        public NewsFeedViewHolder(final View itemView) {
            super(itemView);
            tvName = (BaseTextView) itemView.findViewById(R.id.tvName);
            tvDescription = (BaseTextView) itemView.findViewById(R.id.tvDescription);
            rlSubscribe = (RelativeLayout) itemView.findViewById(R.id.rlSubscribe);
            ivLike = (ImageView) itemView.findViewById(R.id.ivSubscribe);
            cdNewsFeed = (LinearLayout) itemView.findViewById(R.id.cdNewsFeed);
            cdNewsFeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (commander != null) {
                        commander.onItemCardClicked(data.get(getAdapterPosition()));
                    }
                }
            });
            rlSubscribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!data.get(getAdapterPosition()).isSubscribed()) {
                        data.get(getAdapterPosition()).setSubscribed(true);
                        ivLike.setImageResource(R.drawable.subscribe_blue);
                    } else {
                        data.get(getAdapterPosition()).setSubscribed(false);
                        ivLike.setImageResource(R.drawable.subscribe);

                    }
                    if (commander != null) {
                        commander.onItemStatusChanged(data.get(getAdapterPosition()));
                    }

                }
            });
        }

    }


}