package com.markdevelopers.rakshak.di;

import com.markdevelopers.rakshak.common.Config;
import com.markdevelopers.rakshak.data.implementation.NewsFeedRepositoryImpl;
import com.markdevelopers.rakshak.data.implementation.UserRepositoryImpl;
import com.markdevelopers.rakshak.data.remote.NewsFeedRestService;
import com.markdevelopers.rakshak.data.remote.UserRestService;
import com.markdevelopers.rakshak.data.repository.NewsFeedRepository;
import com.markdevelopers.rakshak.data.repository.UserRepository;
import com.markdevelopers.rakshak.network.RxErrorHandlingCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Archish on 1/10/2017.
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    UserRepository provideUserRepository(UserRestService userRestService) {
        return new UserRepositoryImpl(userRestService);
    }

    @Provides
    @Singleton
    NewsFeedRepository provideNewsFeedRepository(NewsFeedRestService newsFeedRestService) {
        return new NewsFeedRepositoryImpl(newsFeedRestService);
    }


    @Provides
    @Singleton
    UserRestService provideUserRestService(Retrofit retrofit) {
        return retrofit.create(UserRestService.class);
    }

    @Provides
    @Singleton
    NewsFeedRestService provideNewsFeedRestService(Retrofit retrofit) {
        return retrofit.create(NewsFeedRestService.class);
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
    }
}
