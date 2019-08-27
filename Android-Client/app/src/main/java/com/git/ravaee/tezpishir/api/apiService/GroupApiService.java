package com.git.ravaee.tezpishir.api.apiService;

import com.git.ravaee.tezpishir.api.Group;
import com.git.ravaee.tezpishir.model.response.GroupResponse;
import com.google.gson.Gson;
import rx.Observable;
import retrofit2.Retrofit;

public class GroupApiService extends ApiService{

    public GroupApiService(Gson gson, Retrofit retrofit) {
        super(gson, retrofit);
    }

    public Observable<GroupResponse> getGroupList(String token){
        Group groupApi = retrofit.create(Group.class);
        return groupApi.list(token);
    }
}
