package com.git.ravaee.tezpishir.repositories;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.git.ravaee.tezpishir.model.response.GroupResponse;
import com.git.ravaee.tezpishir.root.App;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GroupRepository {

    protected App app;
    private static final String TAG = "GROUP_REPOSITORY";

    public GroupRepository(App app) {
        this.app = app;
    }

    public MutableLiveData<GroupResponse> getGroupList(){

        final MutableLiveData<GroupResponse> groupResponseMutableLiveData = new MutableLiveData<>();

        app.getGroupService().getGroupList(app.getSession().getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupResponse>() {

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Unable to submit post to API." + e);
                    }

                    @Override
                    public void onNext(GroupResponse groupResponse) {
                        Log.i(TAG, "post submitted to API." );
                        groupResponseMutableLiveData.setValue(groupResponse);
                    }
                });

        return groupResponseMutableLiveData;
    }
}
