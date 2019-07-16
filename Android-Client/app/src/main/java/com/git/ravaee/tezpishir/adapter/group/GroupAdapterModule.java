package com.git.ravaee.tezpishir.adapter.group;

import android.content.Context;
import com.git.ravaee.tezpishir.model.Group;
import java.util.List;
import dagger.Module;
import dagger.Provides;

@Module
public class GroupAdapterModule {

    private List<Group> dataset;
    private Context context;
    private GroupAdapter.OnItemClickListener onItemClickListener;

    public GroupAdapterModule(List<Group> dataset, Context context, GroupAdapter.OnItemClickListener onItemClickListener) {
        this.dataset = dataset;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides
    GroupAdapter provideGroupAdapter(){
        return new GroupAdapter(dataset,context,onItemClickListener);
    }
}
