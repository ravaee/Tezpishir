package com.git.ravaee.tezpishir.viewModel.group;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.git.ravaee.tezpishir.root.App;

public class GroupViewModelFactory implements ViewModelProvider.Factory {

    private App app;

    public GroupViewModelFactory(App app) {

        this.app = app;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GroupViewModel(app);
    }
}
