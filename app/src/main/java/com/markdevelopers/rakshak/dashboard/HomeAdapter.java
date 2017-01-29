package com.markdevelopers.rakshak.dashboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.Config;
import com.markdevelopers.rakshak.data.remote.models.Home;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Archish on 12/19/2016.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.NewsFeedViewHolder> {

    ArrayList<Home> data;
    private LikeItemUpdateListener commander;

    public HomeAdapter(ArrayList<Home> data, LikeItemUpdateListener commander) {
        this.data = data;
        this.commander = commander;
    }

    public interface LikeItemUpdateListener {
        void onItemStatusChanged(Home home);

        void onItemCardClicked(Home home);
    }

    @Override
    public HomeAdapter.NewsFeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home, parent, false);
        NewsFeedViewHolder holder = new NewsFeedViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final HomeAdapter.NewsFeedViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getHtitle());
        holder.tvDescription.setText(data.get(position).getHdesc());
        if (!data.get(position).getHimage().isEmpty())
            Picasso.with(holder.itemView.getContext()).load(Config.BASE_URL + data.get(position).getHimage()).into(holder.ivImage);
        else
            holder.ivImage.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        BaseTextView tvDescription, tvTitle;
        ImageView ivImage, ivShare;

        public NewsFeedViewHolder(final View itemView) {
            super(itemView);
            tvTitle = (BaseTextView) itemView.findViewById(R.id.tvName);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvDescription = (BaseTextView) itemView.findViewById(R.id.tvDescription);
            ivShare = (ImageView) itemView.findViewById(R.id.ivShare);
            ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (commander != null) {
                        commander.onItemStatusChanged(data.get(getAdapterPosition()));
                    }
                }
            });

        }

    }


}