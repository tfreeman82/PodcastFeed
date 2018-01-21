package com.example.tristanfreeman.podcastlist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tristanfreeman on 1/20/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<RssItem> items;
    private int rowLayout;
    private Context context;

    public ListAdapter(ArrayList<RssItem> list, int rowLayout, Context context) {
        this.items = list;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public long getItemId(int item) {
        return item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RssItem currentItem = items.get(position);

        holder.title.setText(currentItem.getTitle());
        holder.link.setText(currentItem.getLink());
        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent playIntent = new Intent(Intent.ACTION_VIEW);
                playIntent.setData(Uri.parse(currentItem.getLink()));
                context.startActivity(playIntent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView image;
        TextView link;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.title);
            link = (TextView)itemView.findViewById(R.id.link);
        }
    }
}
