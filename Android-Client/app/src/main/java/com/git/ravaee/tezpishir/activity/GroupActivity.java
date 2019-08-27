package com.git.ravaee.tezpishir.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.git.ravaee.tezpishir.R;
import com.git.ravaee.tezpishir.adapter.group.DaggerGroupAdapterComponent;
import com.git.ravaee.tezpishir.adapter.group.GroupAdapter;
import com.git.ravaee.tezpishir.adapter.group.GroupAdapterComponent;
import com.git.ravaee.tezpishir.adapter.group.GroupAdapterModule;
import com.git.ravaee.tezpishir.model.Food;
import com.git.ravaee.tezpishir.model.Group;
import com.git.ravaee.tezpishir.model.response.FoodResponse;
import com.git.ravaee.tezpishir.model.response.GroupResponse;
import com.git.ravaee.tezpishir.root.App;
import com.git.ravaee.tezpishir.viewModel.food.FoodViewModel;
import com.git.ravaee.tezpishir.viewModel.food.FoodViewModelFactory;
import com.git.ravaee.tezpishir.viewModel.group.GroupViewModel;
import com.git.ravaee.tezpishir.viewModel.group.GroupViewModelFactory;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.List;
import javax.inject.Inject;

public class GroupActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "GROUP_ACTIVITY";
    private App app = null;
    protected RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.refresh_layout)
    protected SwipeRefreshLayout refreshLayout;

    @BindView(R.id.drawerLayout)
    protected DrawerLayout drawerLayout;

    @BindView(R.id.menu_button)
    protected AppCompatButton menuButton;

    @BindView((R.id.navigation_view))
    protected NavigationView navigationView;

    @Inject
    GroupAdapter groupAdapter;

    List<Group> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        app = (App)this.getApplication();
        ButterKnife.bind(this);
        refreshLayout.setOnRefreshListener(this::getGroupList);

        navigationView.setNavigationItemSelectedListener(this);
        View navigationHeader = navigationView.getHeaderView(0);

        TextView profileName = navigationHeader.findViewById(R.id.profile_name);
        TextView profileEmail = navigationHeader.findViewById(R.id.profile_email);
        CircleImageView profileImage = navigationHeader.findViewById(R.id.profile_image);

        profileName.setText(app.getSession().getName());
        profileEmail.setText(app.getSession().getEmail());
        Picasso.with(app.getApplicationContext()).load(app.getSession().getImage())
                .into(profileImage);


        getGroupList();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_fav) {

        } else if (id == R.id.nav_add) {

        } else if (id == R.id.nav_main) {

        } else if (id == R.id.nav_edit) {

            Intent intent = new Intent(app.getApplicationContext(),UserEditActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @OnClick(R.id.menu_button)
    protected void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    void updateUI(List<Group> groups){

        this.groups = groups;

        GroupAdapterComponent groupAdapterComponent = DaggerGroupAdapterComponent.builder()
                .groupAdapterModule(new GroupAdapterModule(groups, app.getApplicationContext(), this::getFoodList))
                .build();
        groupAdapterComponent.inject(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groupAdapter);
        groupAdapter.notifyDataSetChanged();
    }

    void updateActivity(FoodResponse.FoodBind foodBind, Group group){

        String foodBindAsJsonString = new Gson().toJson(foodBind);
        String groupAsJsonString = new Gson().toJson(group);

        Intent intent = new Intent(this, FoodsActivity.class);
        intent.putExtra("Foods", foodBindAsJsonString);
        intent.putExtra("Group", groupAsJsonString);
        startActivity(intent);
    }

    void getGroupList(){

        GroupViewModel groupViewModel = ViewModelProviders.of(this, new GroupViewModelFactory(app)).get(GroupViewModel.class);
        groupViewModel.getGroupList().observe(this,groupResponse -> {

            refreshLayout.setRefreshing(false);
            updateUI(groupResponse.getGroups());

        });
    }

    void getFoodList(Group group){

        FoodViewModel foodViewModel = ViewModelProviders.of(this, new FoodViewModelFactory(app)).get(FoodViewModel.class);
        foodViewModel.getFoodList(group,1,10).observe(this,foodResponse -> updateActivity(foodResponse.getFoodBind(),group));
    }

}
