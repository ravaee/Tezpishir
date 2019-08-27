package com.git.ravaee.tezpishir.model.response;

import com.git.ravaee.tezpishir.model.User;
import com.git.ravaee.tezpishir.utility.Response;
import com.google.gson.annotations.SerializedName;

public class UserResponse extends Response {

    @SerializedName("models")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
