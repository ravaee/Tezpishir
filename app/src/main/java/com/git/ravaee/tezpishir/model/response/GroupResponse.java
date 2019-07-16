package com.git.ravaee.tezpishir.model.response;

import com.git.ravaee.tezpishir.model.Group;
import com.git.ravaee.tezpishir.utility.Response;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GroupResponse extends Response {

    @SerializedName("models")
    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }
}
