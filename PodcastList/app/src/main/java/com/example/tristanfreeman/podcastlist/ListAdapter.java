package com.example.tristanfreeman.podcastlist;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tristanfreeman on 1/20/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    public ArrayList<RssItem> items;
    private int rowLayout;
    private Context context;

    public ListAdapter(ArrayList<RssItem> list, int rowLayout, Context context) {
        this.items = list;
        this.rowLayout = rowLayout;
        this.context = context;
    }
    public interface ListItemClickListener {
        public void onListItemClick(RssItem item);
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

        Picasso.with(context).load("http://storage.googleapis.com/androiddevelopers/android_developers_backstage/adb.png").into(holder.thumbnail);
        //holder.thumbnail.setImageBitmap(Picasso.with(context).load(currentItem.getThumbnailUri()));
        holder.title.setText(currentItem.getTitle());
        holder.pubDate.setText(currentItem.getPubDate());

        Spanned result = Html.fromHtml(currentItem.getSummary());
        holder.description.setText(result);
        //holder.link.setText(currentItem.getPageLink());
//        holder.link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent playIntent = new Intent(Intent.ACTION_VIEW);
//                playIntent.setData(Uri.parse(currentItem.getLink()));
//                context.startActivity(playIntent);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        ImageView thumbnail;
        TextView link;
        TextView pubDate;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
            title = (TextView)itemView.findViewById(R.id.title);
            pubDate = (TextView)itemView.findViewById(R.id.pubDate);
            //link = (TextView)itemView.findViewById(R.id.link);
            description = (TextView)itemView.findViewById(R.id.description);
            description.setMovementMethod(new ScrollingMovementMethod());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            RssItem item = items.get(position);

            Intent playIntent = new Intent(Intent.ACTION_VIEW);
            playIntent.setDataAndType(Uri.parse(item.getAudioUrl()), "audio/mp3");
            context.startActivity(playIntent);
        }
    }

}
