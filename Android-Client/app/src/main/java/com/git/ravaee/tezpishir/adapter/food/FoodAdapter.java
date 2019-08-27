package com.git.ravaee.tezpishir.adapter.food;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.git.ravaee.tezpishir.R;
import com.git.ravaee.tezpishir.components.ResizeTextView;
import com.git.ravaee.tezpishir.model.Food;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Food> dataset;
    private Context context;
    private final FoodAdapter.OnItemClickListener listener;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public interface OnItemClickListener {
        void onItemClick(Food food);
    }

    FoodAdapter(List<Food> dataset, Context context, OnItemClickListener listener) {
        this.dataset = dataset;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }

    public int getItemViewType(int position) {
        return dataset.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){

        if (viewType == VIEW_TYPE_ITEM) {
            
            View itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.delegate_food_item,viewGroup,false);
            return new FoodAdapter.FoodViewHolder(itemView);
            
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.delegate_loading_item, viewGroup, false);
            return new LoadingViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i){

        if (viewHolder instanceof FoodViewHolder) {
            
            populateItemRows((FoodViewHolder) viewHolder, i);
            
        } else if (viewHolder instanceof LoadingViewHolder) {
            
            showLoadingView((LoadingViewHolder) viewHolder, i);
            
        }
    }

    private class FoodViewHolder extends RecyclerView.ViewHolder{
        
        ResizeTextView name;
        TextView user;
        TextView time;
        ImageView foodImage;

        FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.food_name_text);
            this.user = itemView.findViewById(R.id.user_name_text);
            this.foodImage = itemView.findViewById(R.id.food_image);
            this.time = itemView.findViewById(R.id.time_text);
        }

        void bind(final Food food, final FoodAdapter.OnItemClickListener listener) {

            itemView.setOnClickListener(v -> listener.onItemClick(food));
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    private void populateItemRows(FoodViewHolder foodViewHolder, int i) {

        foodViewHolder.bind(dataset.get(i), listener);

        foodViewHolder.name.setText(dataset.get(i).getName());
        foodViewHolder.time.setText(dataset.get(i).getTime() + " Min");
        foodViewHolder.user.setText(dataset.get(i).getUser().getNickName());
        Picasso.with(context).load(dataset.get(i)
                .getImage())
                .into(foodViewHolder.foodImage);

    }

}
