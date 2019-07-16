package com.git.ravaee.tezpishir.api;

import com.git.ravaee.tezpishir.model.response.GroupResponse;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

public interface Group {

    @GET("group/list")
    Observable<GroupResponse> list(@Header("x-access-token") String token);


}
