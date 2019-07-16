package com.git.ravaee.tezpishir.adapter.group;

import com.git.ravaee.tezpishir.activity.GroupActivity;
import javax.inject.Singleton;
import dagger.Component;

@Component(modules = {GroupAdapterModule.class})
@Singleton
public interface GroupAdapterComponent {
    void inject(GroupActivity target);
}
