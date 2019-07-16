package com.git.ravaee.tezpishir.utility;

import com.git.ravaee.tezpishir.api.Auth;

public class Apis {

    public static Auth authApiService() {

        return RetrofitClient.getClient(Config.SERVER_API_URL()).create(Auth.class);
    }
}
