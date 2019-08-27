package com.git.ravaee.tezpishir.adapter.group;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.git.ravaee.tezpishir.R;
import com.git.ravaee.tezpishir.model.Group;
import com.squareup.picasso.Picasso;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private List<Group> dataset;
    private Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Group group);
    }

    GroupAdapter(List<Group> dataset, Context context, OnItemClickListener listener) {
        this.dataset = dataset;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.delegate_group_item,viewGroup,false);
        return new GroupViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder groupViewHolder, int i){

        groupViewHolder.bind(dataset.get(i), listener);

        groupViewHolder.name.setText(dataset.get(i).getName());
        groupViewHolder.count.setText(dataset.get(i).getCount() + " order");
        Picasso.with(context).load(dataset.get(i)
                .getImage())
                .into(groupViewHolder.groupImage);
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView count;
        ImageView groupImage;

        GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.group_name_text);
            this.count = itemView.findViewById(R.id.group_food_count_text);
            this.groupImage = itemView.findViewById(R.id.group_image);
        }

        void bind(final Group group, final OnItemClickListener listener) {

            itemView.setOnClickListener(v -> listener.onItemClick(group));
        }
    }
}
