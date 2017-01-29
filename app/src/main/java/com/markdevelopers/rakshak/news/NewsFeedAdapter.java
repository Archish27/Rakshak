package com.markdevelopers.rakshak.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.data.remote.models.NewsFeed;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Archish on 12/19/2016.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder> {

    ArrayList<NewsFeed> data;
    private LikeItemUpdateListener commander;

    public NewsFeedAdapter(ArrayList<NewsFeed> data, LikeItemUpdateListener commander) {
        this.data = data;
        this.commander = commander;
    }

    public interface LikeItemUpdateListener {
        void onItemStatusChanged(NewsFeed newsFeed);

        void onItemCardClicked(NewsFeed newsFeed);
    }

    @Override
    public NewsFeedAdapter.NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news, parent, false);
        NewsFeedViewHolder holder = new NewsFeedViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NewsFeedAdapter.NewsFeedViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.tvDescription.setText(data.get(position).getDescription());
        holder.tvLocation.setText(data.get(position).getLocation());

        if (data.get(position).isSubscribed())
            holder.ivLike.setImageResource(R.drawable.subscribe_blue);
        else
            holder.ivLike.setImageResource(R.drawable.subscribe);
        int severity = Integer.parseInt(data.get(position).getSeverity());
        if (severity > 0 && severity <= 25)
            holder.ivSeverity.setImageResource(R.drawable.lowdanger);
        else if (severity > 25 && severity <= 50)
            holder.ivSeverity.setImageResource(R.drawable.mediumdanger);
        else if (severity > 51 && severity <= 75)
            holder.ivSeverity.setImageResource(R.drawable.highdanger);
        else if (severity > 75)
            holder.ivSeverity.setImageResource(R.drawable.veryhighdanger);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        BaseTextView tvDescription, tvName, tvLocation;
        RelativeLayout rlSubscribe;
        ImageView ivLike, ivSeverity;
        LinearLayout cdNewsFeed;
        LinearLayout llDoctor;

        public NewsFeedViewHolder(final View itemView) {
            super(itemView);
            tvName = (BaseTextView) itemView.findViewById(R.id.tvName);
            tvDescription = (BaseTextView) itemView.findViewById(R.id.tvDescription);
            tvLocation = (BaseTextView) itemView.findViewById(R.id.tvLocation);
            rlSubscribe = (RelativeLayout) itemView.findViewById(R.id.rlSubscribe);
            ivLike = (ImageView) itemView.findViewById(R.id.ivSubscribe);
            ivSeverity = (ImageView) itemView.findViewById(R.id.ivSeverity);
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
    public void animateTo(List<NewsFeed> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<NewsFeed> newModels) {
        for (int i = data.size() - 1; i >= 0; i--) {
            final NewsFeed model = data.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<NewsFeed> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final NewsFeed model = newModels.get(i);
            if (!data.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<NewsFeed> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final NewsFeed model = newModels.get(toPosition);
            final int fromPosition = data.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public NewsFeed removeItem(int position) {
        final NewsFeed model = data.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, NewsFeed model) {
        data.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final NewsFeed model = data.remove(fromPosition);
        data.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }



}