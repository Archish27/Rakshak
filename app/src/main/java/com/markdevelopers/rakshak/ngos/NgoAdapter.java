package com.markdevelopers.rakshak.ngos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.markdevelopers.rakshak.R;
import com.markdevelopers.rakshak.common.Config;
import com.markdevelopers.rakshak.data.remote.models.Ngo;
import com.markdevelopers.rakshak.ui.widgets.BaseTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Archish on 12/19/2016.
 */

public class NgoAdapter extends RecyclerView.Adapter<NgoAdapter.NgoViewHolder> {

    ArrayList<Ngo> data;
    private LikeItemUpdateListener commander;

    public NgoAdapter(ArrayList<Ngo> data, LikeItemUpdateListener commander) {
        this.data = data;
        this.commander = commander;
    }

    public interface LikeItemUpdateListener {
        void onItemStatusChanged(Ngo ngo);

        void onItemCardClicked(Ngo ngo);
    }

    @Override
    public NgoAdapter.NgoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ngo, parent, false);
        NgoViewHolder holder = new NgoViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NgoAdapter.NgoViewHolder holder, final int position) {
        Picasso.with(holder.itemView.getContext()).load(Config.BASE_URL + data.get(position).getIcon()).into(holder.ivImage);
        holder.tvName.setText(data.get(position).getNgoname());
        holder.tvDescription.setText(data.get(position).getCity() + ", " + data.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NgoViewHolder extends RecyclerView.ViewHolder {
        BaseTextView tvDescription, tvName, tvLocation;
        ImageView ivImage;
        LinearLayout cdNgo;

        public NgoViewHolder(final View itemView) {
            super(itemView);
            tvName = (BaseTextView) itemView.findViewById(R.id.tvName);
            tvDescription = (BaseTextView) itemView.findViewById(R.id.tvDescription);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            cdNgo = (LinearLayout) itemView.findViewById(R.id.cdNgo);
            cdNgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (commander != null) {
                        commander.onItemCardClicked(data.get(getAdapterPosition()));
                    }
                }
            });

        }

    }

    public void animateTo(List<Ngo> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Ngo> newModels) {
        for (int i = data.size() - 1; i >= 0; i--) {
            final Ngo model = data.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Ngo> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Ngo model = newModels.get(i);
            if (!data.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Ngo> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Ngo model = newModels.get(toPosition);
            final int fromPosition = data.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Ngo removeItem(int position) {
        final Ngo model = data.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Ngo model) {
        data.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Ngo model = data.remove(fromPosition);
        data.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }


}