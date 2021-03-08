package com.demoapp.demo.HelperClass.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.demo.R;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {

    ArrayList<RecentHelperClass> RecentLocation;

    public RecentAdapter(ArrayList<RecentHelperClass> recentLocation) {
        RecentLocation = recentLocation;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_search_design,parent,false);
       RecentViewHolder recentViewHolder = new RecentViewHolder(view);

        return recentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {

        RecentHelperClass recentHelperClass = RecentLocation.get(position);

        holder.image.setImageResource(recentHelperClass.getImage());
        holder.title.setText(recentHelperClass.getTitle());
        holder.desc.setText(recentHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return RecentLocation.size();
    }


    public static class RecentViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title,desc;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);

            //hooks
            image = itemView.findViewById(R.id.Recent_image);
            title = itemView.findViewById(R.id.Recent_title);
            desc = itemView.findViewById(R.id.Recent_desc);

        }
    }
}
