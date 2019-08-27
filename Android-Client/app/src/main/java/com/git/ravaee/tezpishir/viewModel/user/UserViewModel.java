package com.git.ravaee.tezpishir.viewModel.user;

import android.net.Uri;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.git.ravaee.tezpishir.model.response.UserResponse;
import com.git.ravaee.tezpishir.repositories.UserRepository;
import com.git.ravaee.tezpishir.root.App;


public class UserViewModel extends ViewModel {

    private MutableLiveData<UserResponse> data;
    private UserRepository userRepository;


    UserViewModel(App app) {

        userRepository = new UserRepository(app);
    }


    public MutableLiveData<UserResponse> getUsers(){

        data = userRepository.getUsers();
        return this.data;
    }

    public MutableLiveData<UserResponse> updateUser(String id, String phoneNumber, String areaOfService,
                                                    String experience, String fullname, Uri imageUri){

        data = userRepository.updateUser(id,phoneNumber,experience,areaOfService,fullname,imageUri);
        return this.data;
    }
}
