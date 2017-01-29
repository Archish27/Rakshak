package com.markdevelopers.rakshak.di;


import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;
import com.markdevelopers.rakshak.data.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Archish on 1/10/2017.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    UserRepository userRepository();

    NewsFeedRepository newsFeedRepository();

}
