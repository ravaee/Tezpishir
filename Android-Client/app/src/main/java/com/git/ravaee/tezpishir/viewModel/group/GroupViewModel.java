package com.git.ravaee.tezpishir.viewModel.group;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.git.ravaee.tezpishir.model.response.GroupResponse;
import com.git.ravaee.tezpishir.repositories.GroupRepository;
import com.git.ravaee.tezpishir.root.App;

public class GroupViewModel extends ViewModel {

    private MutableLiveData<GroupResponse> data;
    private GroupRepository groupRepository;

    public GroupViewModel(App app) {
        this.groupRepository = new GroupRepository(app);
    }

    public MutableLiveData<GroupResponse> getGroupList(){
        data = groupRepository.getGroupList();
        return data;
    }



}
