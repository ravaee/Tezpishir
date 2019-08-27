package com.git.ravaee.tezpishir.api;

import com.git.ravaee.tezpishir.model.response.FoodResponse;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface Food {

    @GET("food/list/{group_id}")
    Observable<FoodResponse> list(@Header("x-access-token") String token,
                                  @Path(value = "group_id", encoded = true) String groupId,
                                  @Query("no") int pageNumber,
                                  @Query("count") int count);

    @GET("food/find/{food_id}")
    Observable<FoodResponse> single(@Header("x-access-token") String token,
                                    @Path(value = "food_id", encoded = true) String foodId);

}
