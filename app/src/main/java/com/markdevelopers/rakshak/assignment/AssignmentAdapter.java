package com.markdevelopers.rakshak.assignment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.data.remote.models.Assignment;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;

import java.util.ArrayList;


/**
 * Created by Archish on 12/19/2016.
 */

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.NewsFeedViewHolder> {

    ArrayList<Assignment> data;
    private LikeItemUpdateListener commander;

    public AssignmentAdapter(ArrayList<Assignment> data, LikeItemUpdateListener commander) {
        this.data = data;
        this.commander = commander;
    }

    public interface LikeItemUpdateListener {
        void onItemStatusChanged(Assignment assignment);

        void onItemCardClicked(Assignment assignment);
    }

    @Override
    public AssignmentAdapter.NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_assignment, parent, false);
        NewsFeedViewHolder holder = new NewsFeedViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AssignmentAdapter.NewsFeedViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.tvDescription.setText(data.get(position).getDescription());
        holder.tvLocation.setText(data.get(position).getLocation());
        int severity = Integer.parseInt(data.get(position).getSeverity());
        if (severity > 0 && severity <= 25)
            holder.ivSeverity.setImageResource(R.drawable.lowdanger);
        else if (severity > 25 && severity <= 50)
            holder.ivSeverity.setImageResource(R.drawable.mediumdanger);
        else if (severity > 51 && severity <= 75)
            holder.ivSeverity.setImageResource(R.drawable.highdanger);
        else if (severity > 76)
            holder.ivSeverity.setImageResource(R.drawable.veryhighdanger);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        BaseTextView tvDescription, tvName, tvLocation;
        RelativeLayout rlSubscribe;
        ImageView ivSeverity;
        LinearLayout cdNewsFeed;


        public NewsFeedViewHolder(final View itemView) {
            super(itemView);
            tvName = (BaseTextView) itemView.findViewById(R.id.tvName);
            tvDescription = (BaseTextView) itemView.findViewById(R.id.tvDescription);
            tvLocation = (BaseTextView) itemView.findViewById(R.id.tvLocation);
            rlSubscribe = (RelativeLayout) itemView.findViewById(R.id.rlSubscribe);
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
        }

    }


}