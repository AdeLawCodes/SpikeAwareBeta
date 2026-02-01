package com.example.spikeawarebeta;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.VH> {

    private final ArrayList<ResourceItem> items;
    private final Context context;

    public ResourceAdapter(Context context, ArrayList<ResourceItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resource, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ResourceItem item = items.get(position);
        holder.tvTitle.setText(item.title);
        holder.tvDesc.setText(item.desc);

        holder.itemView.setOnClickListener(v -> {
            String url = item.url;

            if ("__OPEN_GRAPH__".equals(url)) {
                context.startActivity(new Intent(context, GraphActivity.class));
                return;
            }

            if (url == null || url.trim().isEmpty()) return;

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;

        VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
