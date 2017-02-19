package com.markdevelopers.rakshak.data.remote;

import com.markdevelopers.rakshak.data.remote.models.AssignmentWrapper;
import com.markdevelopers.rakshak.data.remote.models.NewsFeedWrapper;
import com.markdevelopers.rakshak.data.remote.models.NgoDetail;
import com.markdevelopers.rakshak.data.remote.models.NgoWrapper;
import com.markdevelopers.rakshak.data.remote.models.SubscribeWrapper;
import com.markdevelopers.rakshak.data.remote.models.UpdateWrapper;
import com.markdevelopers.rakshak.data.remote.models.UserResponse;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Archish on 1/10/2017.
 */

public interface NewsFeedRestService {
    @FormUrlEncoded
    @POST("disaster/show")
    Observable<NewsFeedWrapper> getRecentFeed(@Field("accessToken") String accessToken);


    @FormUrlEncoded
    @POST("disaster/subscribe")
    Observable<UserResponse> markAsSubscribe(@Field("accessToken") String accessToken, @Field("nid") int nid);

    @FormUrlEncoded
    @POST("disaster/show/subscribed")
    Observable<SubscribeWrapper> getSubscribeFeed(@Field("accessToken") String accessToken);

    @FormUrlEncoded
    @POST("disaster/updates")
    Observable<UpdateWrapper> getUpdates(@Field("accessToken") String accessToken, @Field("did") int did);

    @FormUrlEncoded
    @POST("disaster/assignment")
    Observable<AssignmentWrapper> getAssignments(@Field("accessToken") String accessToken);

    @FormUrlEncoded
    @POST("ngo/show")
    Observable<NgoWrapper> getNgos(@Field("accessToken") String accessToken);

    @FormUrlEncoded
    @POST("ngo/show/mission")
    Observable<NgoDetail> getNgoMission(@Field("accessToken") String accessToken, @Field("id") int id);

    @FormUrlEncoded
    @POST("ngo/show/stories")
    Observable<NgoDetail> getNgoStories(@Field("accessToken") String accessToken, @Field("id") int id);

    @FormUrlEncoded
    @POST("ngo/show/contact")
    Observable<NgoDetail> getNgoContact(@Field("accessToken") String accessToken, @Field("id") int id);

    @Multipart
    @POST("disaster/update/new")
    Observable<UserResponse> setNewsPost(@Query("accessToken") String accessToken
            , @Query("title") String title
            , @Query("description") String description
            , @Part("image\"; filename=\"image.jpg\" ") RequestBody postimage
            , @Query("did") int did
    );

    @FormUrlEncoded
    @POST("disaster/update/new")
    Observable<UserResponse> setNewsPostNoImage(@Field("accessToken") String accessToken
            , @Field("title") String title
            , @Field("description") String description
            , @Field("did") int did
    );

}
