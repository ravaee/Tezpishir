package com.git.ravaee.tezpishir.viewModel.food;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.git.ravaee.tezpishir.root.App;

public class FoodViewModelFactory implements ViewModelProvider.Factory {

    private App app;

    public FoodViewModelFactory(App app) {
        this.app = app;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FoodViewModel(app);
    }
}
